package com.zhixinyisheng.user.domain.pay;

/**
 * 个性化收费
 * Created by 焕焕 on 2017/1/10.
 */
public class PersonlizedCharge {

    /**
     * createtime : 1484033138000
     * dayMoney : 1
     * doctorRuleId : 6
     * messageCount : 50
     * monthMoney : 1
     * timeMoney : 1
     * userid : a443872d4f474e3987bd774f5553be28
     * weekMoney : 1
     * yearMoney : 1
     */

    private DbBean db;
    /**
     * db : {"createtime":1484033138000,"dayMoney":"1","doctorRuleId":6,"messageCount":50,"monthMoney":"1","timeMoney":"1","userid":"a443872d4f474e3987bd774f5553be28","weekMoney":"1","yearMoney":"1"}
     * result : 0000
     * retMessage : 修改成功
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
        private long createtime;
        private String dayMoney;
        private int doctorRuleId;
        private int messageCount;
        private String monthMoney;
        private String timeMoney;
        private String userid;
        private String weekMoney;
        private String yearMoney;

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getDayMoney() {
            return dayMoney;
        }

        public void setDayMoney(String dayMoney) {
            this.dayMoney = dayMoney;
        }

        public int getDoctorRuleId() {
            return doctorRuleId;
        }

        public void setDoctorRuleId(int doctorRuleId) {
            this.doctorRuleId = doctorRuleId;
        }

        public int getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(int messageCount) {
            this.messageCount = messageCount;
        }

        public String getMonthMoney() {
            return monthMoney;
        }

        public void setMonthMoney(String monthMoney) {
            this.monthMoney = monthMoney;
        }

        public String getTimeMoney() {
            return timeMoney;
        }

        public void setTimeMoney(String timeMoney) {
            this.timeMoney = timeMoney;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getWeekMoney() {
            return weekMoney;
        }

        public void setWeekMoney(String weekMoney) {
            this.weekMoney = weekMoney;
        }

        public String getYearMoney() {
            return yearMoney;
        }

        public void setYearMoney(String yearMoney) {
            this.yearMoney = yearMoney;
        }
    }
}
