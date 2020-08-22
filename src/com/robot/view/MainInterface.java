package com.robot.view;

import com.robot.pojo.Account;
import com.robot.service.AccountService;
import com.robot.service.impl.AccountServiceImpl;
import com.robot.utils.JDBCUtils;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * 主界面。
 *
 * @author 张宝旭
 */
public class  MainInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountService accountService = new AccountServiceImpl();
        System.out.println("----------欢迎进入ATM系统----------");
        System.out.println("请登录！");
        System.out.println("输入银行卡号");
        String cardNo = scanner.next();
        System.out.println("输入密码");
        String password = scanner.next();
        Account account = accountService.login(cardNo, password);
        if (account != null) {
            System.out.println("登录成功");
            while (true) {
                System.out.println("-------【 0 退出  1 查询余额  2 存钱  3 取钱  4 转账  】-------");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("退出系统，欢迎下次再来");
                        System.exit(0);
                    case 1:
                        BigDecimal bigDecimal = accountService.balanceInquiry(cardNo);
                        System.out.println("余额: " + bigDecimal);
                        break;
                    case 2:
                        System.out.println("输入存款金额");
                        BigDecimal saveMoney = scanner.nextBigDecimal();
                        accountService.saveMoney(cardNo, saveMoney);
                        System.out.println("存钱成功");
                        break;
                    case 3:
                        System.out.println("输入取款金额");
                        BigDecimal takeMoney = scanner.nextBigDecimal();
                        try {
                            accountService.takeMoney(cardNo, takeMoney);
                            System.out.println("取钱成功");
                        } catch (Exception e) {
                            System.out.println("取款失败: " + e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            // 开启事务
                            JDBCUtils.startTransaction();
                            System.out.println("输入对方账户的银行卡号");
                            String targetCardNo = scanner.next();
                            System.out.println("输入转账金额");
                            BigDecimal transMoney = scanner.nextBigDecimal();
                            accountService.transMoney(cardNo, targetCardNo, transMoney);
                            // 提交事务
                            JDBCUtils.commit();
                            System.out.println("转账成功");
                        } catch (Exception e) {
                            System.out.println("转账失败: " + e.getMessage());
                            // 事务回滚
                            JDBCUtils.rollback();
                        }
                        break;
                    default:
                        System.out.println("输入操作序号错误");
                        break;
                }
            }
        } else {
            System.out.println("登录失败");
        }
    }
}
