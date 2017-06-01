package com.zhixinyisheng.user.domain.doctor;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 风度侠客 on 2016/12/28.
 */

public class Doctor {
    private String result;
    private String retMessage;
    private int size;
    private List<Doctor.ListBean> list;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Doctor.ListBean> getList() {
        return list;
    }

    public void setList(List<Doctor.ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
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
        private int payedUserID;
        private String messageCount;
        @JSONField(name = "headUrl")
        private String avatar;

        private String dayMoney;
        private String weekMoney;
        private String yearMoney;
        public int getPayedUserID() {
            return payedUserID;
        }

        public void setPayedUserID(int payedUserID) {
            this.payedUserID = payedUserID;
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

        public String getDayMoney() {
            return dayMoney;
        }

        public void setDayMoney(String dayMoney) {
            this.dayMoney = dayMoney;
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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
