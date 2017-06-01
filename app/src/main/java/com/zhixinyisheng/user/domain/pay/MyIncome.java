package com.zhixinyisheng.user.domain.pay;

/**
 * 我的收入
 * Created by 焕焕 on 2017/1/18.
 */
public class MyIncome {

    /**
     * result : 0000
     * db : {"balance":136.2,"income":144.2}
     * retMessage : 查询成功
     */

    private String result;
    /**
     * balance : 136.2
     * income : 144.2
     */

    private DbBean db;
    private String retMessage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public static class DbBean {
        private double balance;
        private double income;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }
    }
}
