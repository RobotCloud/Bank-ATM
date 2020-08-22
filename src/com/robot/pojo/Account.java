package com.robot.pojo;

import java.math.BigDecimal;

/**
 * 实体类，对应数据库的账户表。
 *
 * @author 张宝旭
 */
public class Account {
    private Integer id;
    private String name;
    private String cardNo;
    private String password;
    private BigDecimal money;

    public Account() {
    }

    public Account(Integer id, String name, String cardNo, String password, BigDecimal money) {
        this.id = id;
        this.name = name;
        this.cardNo = cardNo;
        this.password = password;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                '}';
    }
}
