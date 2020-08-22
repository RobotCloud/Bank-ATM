package com.robot.dao.impl;

import com.robot.dao.AccountDao;
import com.robot.pojo.Account;
import com.robot.utils.JDBCUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dao层，操作数据库实现类。
 *
 * @author 张宝旭
 */
public class AccountDaoImpl implements AccountDao {
    @Override
    public Account queryByCardNoAndPassword(String cardNo, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCUtils.getConnection();
            String sql = "SELECT id, name, money FROM account WHERE cardNo=? AND password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cardNo);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal money = resultSet.getBigDecimal("money");
                account = new Account(id, name, cardNo, password, money);
            }
            return account;
        } catch (SQLException e) {
            throw new RuntimeException("查找失败", e);
        } finally {
            JDBCUtils.close(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void save(String cardNo, BigDecimal money) {
        String sql = "UPDATE account SET money=money+? WHERE cardNo=?";
        Object[] params = {money, cardNo};
        try {
            JDBCUtils.executeUpdate(sql, params);
        } catch (Exception e) {
            throw new RuntimeException("存款异常");
        }
    }

    @Override
    public void take(String cardNo, BigDecimal money) {
        String sql = "UPDATE account SET money=money-? WHERE cardNo=?";
        Object[] params = {money, cardNo};
        try {
            JDBCUtils.executeUpdate(sql, params);
        } catch (Exception e) {
            throw new RuntimeException("存款异常");
        }
    }

    @Override
    public BigDecimal balanceInquiryByCardNo(String cardNo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "SELECT money FROM account WHERE cardNo=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cardNo);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal("money");
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询余额失败");
        } finally {
            JDBCUtils.close(resultSet, preparedStatement, connection);
        }
        return null;
    }

    @Override
    public Boolean queryAccount(String cardNo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "SELECT id, name FROM account WHERE cardNo=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cardNo);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("查找失败");
        } finally {
            JDBCUtils.close(resultSet, preparedStatement, connection);
        }
        return false;
    }
}
