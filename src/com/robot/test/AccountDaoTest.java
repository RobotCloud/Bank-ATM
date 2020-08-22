package com.robot.test;

import com.robot.dao.AccountDao;
import com.robot.dao.impl.AccountDaoImpl;
import com.robot.pojo.Account;
import com.robot.service.AccountService;
import com.robot.service.impl.AccountServiceImpl;
import com.robot.utils.JDBCUtils;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * 单元测试。
 *
 * @author 张宝旭
 */
public class AccountDaoTest {
    @Test
    public void queryByCardNoAndPasswordTest() {
        AccountDao accountDao = new AccountDaoImpl();
        Account account = accountDao.queryByCardNoAndPassword("6250001111", "123456");
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("没有找到此用户");
        }
    }
    @Test
    public void saveMoneyTest() {
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.save("6250001111", BigDecimal.valueOf(1000));
        System.out.println("存钱成功");
    }
    @Test
    public void takeMoneyTest() {
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.take("6250002222", BigDecimal.valueOf(1000));
        System.out.println("取钱成功");
    }
    @Test
    public void transMoneyTest() {
        AccountService accountService = new AccountServiceImpl();
        Account account = accountService.login("6250001111", "123456");
        if (account != null) {
            try {
                // 开启事务
                JDBCUtils.startTransaction();
                accountService.transMoney("6250001111", "6250002222", new BigDecimal(1000));
                System.out.println("转账成功");
                // 提交事务
                JDBCUtils.commit();
            } catch (Exception e) {
                System.out.println("转账失败: " + e.getMessage());
                // 事务回滚
                JDBCUtils.rollback();
            }
        } else {
            System.out.println("登录失败");
        }
    }
}
