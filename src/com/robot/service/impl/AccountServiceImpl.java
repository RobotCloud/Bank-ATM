package com.robot.service.impl;

import com.robot.dao.AccountDao;
import com.robot.dao.impl.AccountDaoImpl;
import com.robot.pojo.Account;
import com.robot.service.AccountService;

import java.math.BigDecimal;

/**
 * Service层，处理业务逻辑实现类。
 *
 * @author 张宝旭
 */
public class AccountServiceImpl implements AccountService {
    /**
     * 创建Dao层对象
     */
    AccountDao accountDao = new AccountDaoImpl();

    @Override
    public Account login(String cardNo, String password) {
        return accountDao.queryByCardNoAndPassword(cardNo, password);
    }

    @Override
    public void saveMoney(String cardNo, BigDecimal money) {
        accountDao.save(cardNo, money);
    }

    @Override
    public void takeMoney(String cardNo, BigDecimal money) {
        if (accountDao.balanceInquiryByCardNo(cardNo).compareTo(money) < 0) {
            throw new RuntimeException("余额不足");
        }
        accountDao.take(cardNo, money);
    }

    @Override
    public void transMoney(String myCardNo, String targetCardNo, BigDecimal money) {
        // 1 检查对方账户是否存在
        if (!accountDao.queryAccount(targetCardNo)) {
            throw new RuntimeException("对方账户不存在");
        }
        // 2 检查自己账户余额是否足够
        if (accountDao.balanceInquiryByCardNo(myCardNo).compareTo(money) < 0) {
            throw new RuntimeException("余额不足");
        }
        // 3 自己账户取钱
        accountDao.take(myCardNo, money);
        // 4 对方账户存钱
        accountDao.save(targetCardNo, money);
    }

    @Override
    public BigDecimal balanceInquiry(String cardNo) {
        return accountDao.balanceInquiryByCardNo(cardNo);
    }
}
