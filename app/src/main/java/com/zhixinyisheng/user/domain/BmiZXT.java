package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * bmi趋势
 * Created by 焕焕 on 2016/11/2.
 */
public class BmiZXT{

    /**
     * result : 0000
     * retMessage : 查询成功
     * bmiList : [{"num":24.4473,"bmiId":"fe5dd3b94be947d19d602f47b67d5ddc","USER_ID":"2c0fcf1171e1423097814250c23edceb","rangeValue":1,"byTime":"2016-11-02","colorFlag":1}]
     */

    private String result;
    private String retMessage;
    /**
     * num : 24.4473
     * bmiId : fe5dd3b94be947d19d602f47b67d5ddc
     * USER_ID : 2c0fcf1171e1423097814250c23edceb
     * rangeValue : 1
     * byTime : 2016-11-02
     * colorFlag : 1
     */

    private List<BmiListBean> bmiList;

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

    public List<BmiListBean> getBmiList() {
        return bmiList;
    }

    public void setBmiList(List<BmiListBean> bmiList) {
        this.bmiList = bmiList;
    }

    public static class BmiListBean {
        private double num;
        private String bmiId;
        private String USER_ID;
        private int rangeValue;
        private String byTime;
        private int colorFlag;

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        public String getBmiId() {
            return bmiId;
        }

        public void setBmiId(String bmiId) {
            this.bmiId = bmiId;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public int getRangeValue() {
            return rangeValue;
        }

        public void setRangeValue(int rangeValue) {
            this.rangeValue = rangeValue;
        }

        public String getByTime() {
            return byTime;
        }

        public void setByTime(String byTime) {
            this.byTime = byTime;
        }

        public int getColorFlag() {
            return colorFlag;
        }

        public void setColorFlag(int colorFlag) {
            this.colorFlag = colorFlag;
        }
    }
}
