package com.robot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC 工具类：创建连接、关闭连接、增删改操作
 *
 * @author 张宝旭
 */
public class JDBCUtils {
    private static String driver;
    private static String user;
    private static String password;
    private static String url;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            // 加载配置文件
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);
            // 读取配置文件并赋值
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            // 注册驱动
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建连接
     * @return Connection连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        // 从本地线程中获取连接，如果连接存在，就直接用，如果不存在，就新建一个连接并绑定到ThreadLocal
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 关闭连接
     * @param result：结果集
     * @param pst：执行
     * @param con：连接
     */
    public static void close(ResultSet result, PreparedStatement pst, Connection con) {
        try {
            if (result != null) {
                result.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                if (threadLocal.get().getAutoCommit()) {
                    con.close();
                    threadLocal.remove();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("释放资源异常");
        }
    }

    /**
     * 增、删、改
     * @param sql sql操作语句
     * @param params 注入执行参数
     * @return 返回执行结果
     */
    public static int executeUpdate(String sql, Object...params) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null) {
                for(int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            return preparedStatement.executeUpdate();
        }finally {
            close(null, preparedStatement, connection);
        }
    }

    /**
     * 查询
     * @param sql SQL查询语句
     * @param params 查询条件参数
     * @return 查询状态
     */
    public static ResultSet executeQuery(String sql, Object...params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null) {
                for(int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
           // close(resultSet, preparedStatement, connection);
        }
        return null;
    }

    /**
     * 开启事务
     */
    public static void startTransaction() {
        Connection connection = threadLocal.get();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commit() {
        Connection connection = threadLocal.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 事务回滚
     */
    public static void rollback() {
        Connection connection = threadLocal.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
