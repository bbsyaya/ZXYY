package com.zhixinyisheng.user.domain.doctor;

/**
 * Created by 风度侠客 on 2017/1/21.
 */

public class ChatRecord {

    /**
     * result : 0000
     * db : {"num":7,"buyNum":10,"payedUserID":1,"canSayNum":3,"endTime":"","type":6,"isPayPeople":1,"payRecordId":"6e01737fe9d3444c86fa1a7d8e2e9736"}
     * retMessage : 查询成功
     */

    private String result;
    /**
     * num : 7
     * buyNum : 10
     * payedUserID : 1
     * canSayNum : 3
     * endTime :
     * type : 6
     * isPayPeople : 1
     * payRecordId : 6e01737fe9d3444c86fa1a7d8e2e9736
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
        private int num;
        private int buyNum;
        private int payedUserID;
        private int canSayNum;
        private String endTime;
        private int type;
        private int isPayPeople;
        private String payRecordId;
        private String isAppraise;

        public String getIsAppraise() {
            return isAppraise;
        }

        public void setIsAppraise(String isAppraise) {
            this.isAppraise = isAppraise;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(int buyNum) {
            this.buyNum = buyNum;
        }

        public int getPayedUserID() {
            return payedUserID;
        }

        public void setPayedUserID(int payedUserID) {
            this.payedUserID = payedUserID;
        }

        public int getCanSayNum() {
            return canSayNum;
        }

        public void setCanSayNum(int canSayNum) {
            this.canSayNum = canSayNum;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIsPayPeople() {
            return isPayPeople;
        }

        public void setIsPayPeople(int isPayPeople) {
            this.isPayPeople = isPayPeople;
        }

        public String getPayRecordId() {
            return payRecordId;
        }

        public void setPayRecordId(String payRecordId) {
            this.payRecordId = payRecordId;
        }
    }
}
