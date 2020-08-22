package com.robot.service;

import com.robot.pojo.Account;
import java.math.BigDecimal;

/**
 * Service层接口，处理业务逻辑。
 *
 * @author 张宝旭
 */
public interface AccountService {

    /**
     * 登录。
     * @param cardNo 银行卡号
     * @param password 密码
     * @return 返回登录成功的用户
     */
    Account login(String cardNo, String password);

    /**
     * 存钱。
     * @param cardNo 银行卡号
     * @param money 存的钱
     */
    void saveMoney(String cardNo, BigDecimal money);

    /**
     * 取钱。
     * @param cardNo 银行卡号
     * @param money 取的钱
     */
    void takeMoney(String cardNo, BigDecimal money);

    /**
     * 转账。
     * @param myCardNo 甲方的银行卡号
     * @param targetCardNo 乙方的银行卡号
     * @param money 转账金额
     */
    void transMoney(String myCardNo, String targetCardNo, BigDecimal money);


    /**
     * 余额查询。
     * @param cardNo 银行卡号
     */
    BigDecimal balanceInquiry(String cardNo);

}
