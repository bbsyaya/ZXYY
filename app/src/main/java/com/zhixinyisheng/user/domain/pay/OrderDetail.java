package com.zhixinyisheng.user.domain.pay;

/**
 * 订单详情
 * Created by 焕焕 on 2017/2/20.
 */
public class OrderDetail {

    /**
     * createtime : 2017-02-12 10:08:58
     * endTime : 2017-02-19 10:09:04
     * flag :
     * incomeCard : 5996
     * incomeName : 陈焕
     * incomeUserName : 追逐太阳
     * money : -0.01
     * payCard : 5992
     * payName : 剧京
     * payRecordId : 1
     * payUserName : 那兔
     * reason :
     */

    private DbBean db;
    /**
     * db : {"createtime":"2017-02-12 10:08:58","endTime":"2017-02-19 10:09:04","flag":"","incomeCard":5996,"incomeName":"陈焕","incomeUserName":"追逐太阳","money":"-0.01","payCard":5992,"payName":"剧京","payRecordId":"1","payUserName":"那兔","reason":""}
     * result : 0000
     * retMessage : 查询成功
     */

    private String result;
    private String retMessage;

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public static class DbBean {
        private String createtime;
        private String endTime;
        private String flag;
        private String incomeCard;
        private String incomeName;
        private String incomeUserName;
        private String money;
        private String payCard;
        private String payName;
        private String payRecordId;
        private String payUserName;
        private String reason;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getIncomeCard() {
            return incomeCard;
        }

        public void setIncomeCard(String incomeCard) {
            this.incomeCard = incomeCard;
        }

        public String getIncomeName() {
            return incomeName;
        }

        public void setIncomeName(String incomeName) {
            this.incomeName = incomeName;
        }

        public String getIncomeUserName() {
            return incomeUserName;
        }

        public void setIncomeUserName(String incomeUserName) {
            this.incomeUserName = incomeUserName;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPayCard() {
            return payCard;
        }

        public void setPayCard(String payCard) {
            this.payCard = payCard;
        }

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
        }

        public String getPayRecordId() {
            return payRecordId;
        }

        public void setPayRecordId(String payRecordId) {
            this.payRecordId = payRecordId;
        }

        public String getPayUserName() {
            return payUserName;
        }

        public void setPayUserName(String payUserName) {
            this.payUserName = payUserName;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
