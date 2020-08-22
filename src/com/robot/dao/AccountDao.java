package com.robot.dao;

import com.robot.pojo.Account;

import java.math.BigDecimal;

/**
 * Dao层接口，操作数据库。
 *
 * @author 张宝旭
 */
public interface AccountDao {

    /**
     * 根据银行卡号和密码查找用户
     * @return 找到的用户
     */
    Account queryByCardNoAndPassword(String cardNo, String password);

    /**
     * 存钱
     * @param cardNo 银行卡号
     * @param money 存的钱
     */
    void save(String cardNo, BigDecimal money);

    /**
     * 取钱
     * @param cardNo 银行卡号
     * @param money 取的钱
     */
    void take(String cardNo, BigDecimal money);

    /**
     * 根据卡号查询余额
     * @param cardNo 银行卡号
     * @return 余额
     */
    BigDecimal balanceInquiryByCardNo(String cardNo);

    /**
     * 根据卡号查找账户是否存在
     * @param cardNo 银行卡号
     * @return 如果成功返回true，如果失败返回false
     */
    Boolean queryAccount(String cardNo);
}
