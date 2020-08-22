---------------说明文档---------------
主要功能
    模拟银行ATM机，实现银行账户的存钱、取钱、转账等事务
主要技术
    JavaSE + JDBC + MySQL
主界面
    客户看到的界面，操作界面
Dao层操作
    1、查找用户
    2、存钱
    3、取钱
    4、查询余额
    5、判断账户是否存在
Service层操作
    1、登录
    2、存钱
    3、取钱
    4、转账
    5、余额查询
JDBC工具类
    1、静态代码块注册驱动
    2、创建连接：使用ThreadLocal，保证一个事务只使用一个连接
    3、释放资源：事务未开启时才会关闭连接
    4、增删改操作
    5、开启事务
    6、提交事务
    7、回滚事务
实体类
    private Integer id;
    private String name;
    private String cardNo;
    private String password;
    private BigDecimal money;
数据库
    表名：account
    字段：
        id (INT--integer)：用户id，主键、自增
        name (VARCHAR(25)--String)：用户姓名
        cardNo (VARCHAR(25)--String)：银行卡号
        password (VARCHAR(25)--String)：密码
        money (decimal(10)--BigDecimal)：余额
    版本：mysql8.0
