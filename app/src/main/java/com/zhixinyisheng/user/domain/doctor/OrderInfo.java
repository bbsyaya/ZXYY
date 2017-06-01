package com.zhixinyisheng.user.domain.doctor;

import java.io.Serializable;

/**
 * Created by 风度侠客 on 2017/1/19.
 */

public class OrderInfo implements Serializable {

    /**
     * result : 0000
     * db : {"createtime":1484357479000,"unit":"元","title":"未付费","flag":1,"toUserId":"159d7d91fd7a43dcac63c7f795b72791","nums":20,"toAccount":"","money":"0","myUserId":"c5f5197aed78453d84588c5feed2c3bf","type":5,"endTime":1484362927000,"payRecordId":"8a76039c5584479cb91cd90a10a744a5"}
     * retMessage : 查询成功
     */

    private String result;
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

    public static class DbBean implements Serializable{
        /**
         * createtime : 1484357479000
         * unit : 元
         * title : 未付费
         * flag : 1
         * toUserId : 159d7d91fd7a43dcac63c7f795b72791
         * nums : 20
         * toAccount :
         * money : 0
         * myUserId : c5f5197aed78453d84588c5feed2c3bf
         * type : 5
         * endTime : 1484362927000
         * payRecordId : 8a76039c5584479cb91cd90a10a744a5
         */

        private long createtime;
        private String unit;
        private String title;
        private int flag;
        private String toUserId;
        private int nums;
        private String toAccount;
        private String money;
        private String myUserId;
        private int type;
        private long endTime;
        private String payRecordId;

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getToAccount() {
            return toAccount;
        }

        public void setToAccount(String toAccount) {
            this.toAccount = toAccount;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMyUserId() {
            return myUserId;
        }

        public void setMyUserId(String myUserId) {
            this.myUserId = myUserId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getPayRecordId() {
            return payRecordId;
        }

        public void setPayRecordId(String payRecordId) {
            this.payRecordId = payRecordId;
        }
    }
}
