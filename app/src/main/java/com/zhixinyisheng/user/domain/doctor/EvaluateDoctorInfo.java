package com.zhixinyisheng.user.domain.doctor;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Yuanyx on 2017/1/15.
 */

public class EvaluateDoctorInfo {

    /**
     * result : 0000
     * db : {"num":"9","title":"付费30元/月","name":"12","userId":"159d7d91fd7a43dcac63c7f795b72791","job":"主治医师","headUrl":"http://222.222.24.133:8099/picResource/sbs/common/HEADURL.png","disease":"肾病","section":"肾病科室","hospital":"石家庄肾病医院"}
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

    public static class DbBean {
        @JSONField(name = "userId")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String name;
        //医师
        @JSONField(name = "job")
        private String physician;
        @JSONField(name = "hospital")
        private String organization;
        @JSONField(name = "section")
        private String department;
        @JSONField(name = "disease")
        private String beGoodAt;

        @JSONField(name = "num")
        private String num;
        private String messageCount;
        @JSONField(name = "headUrl")
        private String avatar;

        private String yearMoney;
        private String monthMoney;
        private String dayMoney;
        private String weekMoney;

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

        public String getMonthMoney() {
            return monthMoney;
        }

        public void setMonthMoney(String monthMoney) {
            this.monthMoney = monthMoney;
        }

        public String getDayMoney() {
            return dayMoney;
        }

        public void setDayMoney(String dayMoney) {
            this.dayMoney = dayMoney;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(String messageCount) {
            this.messageCount = messageCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhysician() {
            return physician;
        }

        public void setPhysician(String physician) {
            this.physician = physician;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getBeGoodAt() {
            return beGoodAt;
        }

        public void setBeGoodAt(String beGoodAt) {
            this.beGoodAt = beGoodAt;
        }


        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
