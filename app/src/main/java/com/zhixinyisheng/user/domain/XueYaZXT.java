package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * 血压的折线图
 * Created by 焕焕 on 2016/10/30.
 */
public class XueYaZXT {

    /**
     * list : [{"ADDTIME":"2016-10-30","BLOOD_ID":"953dab79c0aa4cb8954913374ac82c1f","BYTIME":"2016-10-30","HIGHPRESSURE":109,"LOWPRESSURE":73,"USER_ID":"2c0fcf1171e1423097814250c23edceb","state":1}]
     * result : 0000
     * size : 1
     */

    private String result;
    private int size;
    /**
     * ADDTIME : 2016-10-30
     * BLOOD_ID : 953dab79c0aa4cb8954913374ac82c1f
     * BYTIME : 2016-10-30
     * HIGHPRESSURE : 109.0
     * LOWPRESSURE : 73.0
     * USER_ID : 2c0fcf1171e1423097814250c23edceb
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
        private String BLOOD_ID;
        private String BYTIME;
        private double HIGHPRESSURE;
        private double LOWPRESSURE;
        private String USER_ID;
        private int state;

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getBLOOD_ID() {
            return BLOOD_ID;
        }

        public void setBLOOD_ID(String BLOOD_ID) {
            this.BLOOD_ID = BLOOD_ID;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public double getHIGHPRESSURE() {
            return HIGHPRESSURE;
        }

        public void setHIGHPRESSURE(double HIGHPRESSURE) {
            this.HIGHPRESSURE = HIGHPRESSURE;
        }

        public double getLOWPRESSURE() {
            return LOWPRESSURE;
        }

        public void setLOWPRESSURE(double LOWPRESSURE) {
            this.LOWPRESSURE = LOWPRESSURE;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
