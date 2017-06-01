package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * 心率折线图
 * Created by 焕焕 on 2016/10/27.
 */
public class XinLvZXT {

    /**
     * list : [{"ADDTIME":"2016-10-27","BYTIME":"2016-10-27","HEARTRATE_ID":"858d865294624b8fabd0db72ac6e2cf6","HIGH":0,"LOW":0,"USER_ID":"2c0fcf1171e1423097814250c23edceb","num":91,"state":1},{"ADDTIME":"2016-10-27","BYTIME":"2016-10-26","HEARTRATE_ID":"7587f311fcb3469aa05aaaf5ef49aced","HIGH":0,"LOW":0,"USER_ID":"2c0fcf1171e1423097814250c23edceb","num":88,"state":1}]
     * result : 0000
     * size : 2
     */

    private String result;
    private int size;
    /**
     * ADDTIME : 2016-10-27
     * BYTIME : 2016-10-27
     * HEARTRATE_ID : 858d865294624b8fabd0db72ac6e2cf6
     * HIGH : 0.0
     * LOW : 0.0
     * USER_ID : 2c0fcf1171e1423097814250c23edceb
     * num : 91
     * state : 1
     */

    private List<ListBean> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String ADDTIME;
        private String BYTIME;
        private String HEARTRATE_ID;
        private double HIGH;
        private double LOW;
        private String USER_ID;
        private int num;
        private int state;

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public String getHEARTRATE_ID() {
            return HEARTRATE_ID;
        }

        public void setHEARTRATE_ID(String HEARTRATE_ID) {
            this.HEARTRATE_ID = HEARTRATE_ID;
        }

        public double getHIGH() {
            return HIGH;
        }

        public void setHIGH(double HIGH) {
            this.HIGH = HIGH;
        }

        public double getLOW() {
            return LOW;
        }

        public void setLOW(double LOW) {
            this.LOW = LOW;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
