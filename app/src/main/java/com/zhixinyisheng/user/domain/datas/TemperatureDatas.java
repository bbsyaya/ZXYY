package com.zhixinyisheng.user.domain.datas;

import java.util.List;

/**
 * 体温实体类
 * Created by 焕焕 on 2017/1/7.
 */
public class TemperatureDatas {

    /**
     * list : [{"ADDTIME":"2017-01-07","AFTERNOONVALUE":"39","ANIMALHEAT_ID":"32bb9174fcba48c49b69c81af572a915","BYTIME":"2017-01-07","DEGREE":0,"EVENINGVALUE":"37","MORNINGVALUE":"36","TIMELIMIT":1,"USER_ID":"ecd015dce7d14d5eb67a23d2a19a6a81","state":1},{"ADDTIME":"2016-12-18","ANIMALHEAT_ID":"181c1d7ed0684bc48e8605018e6e64eb","BYTIME":"2016-12-18","DEGREE":0,"MORNINGVALUE":"36","TIMELIMIT":1,"USER_ID":"ecd015dce7d14d5eb67a23d2a19a6a81","state":1}]
     * result : 0000
     * size : 2
     */

    private String result;
    private int size;
    /**
     * ADDTIME : 2017-01-07
     * AFTERNOONVALUE : 39
     * ANIMALHEAT_ID : 32bb9174fcba48c49b69c81af572a915
     * BYTIME : 2017-01-07
     * DEGREE : 0.0
     * EVENINGVALUE : 37
     * MORNINGVALUE : 36
     * TIMELIMIT : 1
     * USER_ID : ecd015dce7d14d5eb67a23d2a19a6a81
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
        private String AFTERNOONVALUE;
        private String ANIMALHEAT_ID;
        private String BYTIME;
        private double DEGREE;
        private String EVENINGVALUE;
        private String MORNINGVALUE;
        private int TIMELIMIT;
        private String USER_ID;
        private int state;

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getAFTERNOONVALUE() {
            return AFTERNOONVALUE;
        }

        public void setAFTERNOONVALUE(String AFTERNOONVALUE) {
            this.AFTERNOONVALUE = AFTERNOONVALUE;
        }

        public String getANIMALHEAT_ID() {
            return ANIMALHEAT_ID;
        }

        public void setANIMALHEAT_ID(String ANIMALHEAT_ID) {
            this.ANIMALHEAT_ID = ANIMALHEAT_ID;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public double getDEGREE() {
            return DEGREE;
        }

        public void setDEGREE(double DEGREE) {
            this.DEGREE = DEGREE;
        }

        public String getEVENINGVALUE() {
            return EVENINGVALUE;
        }

        public void setEVENINGVALUE(String EVENINGVALUE) {
            this.EVENINGVALUE = EVENINGVALUE;
        }

        public String getMORNINGVALUE() {
            return MORNINGVALUE;
        }

        public void setMORNINGVALUE(String MORNINGVALUE) {
            this.MORNINGVALUE = MORNINGVALUE;
        }

        public int getTIMELIMIT() {
            return TIMELIMIT;
        }

        public void setTIMELIMIT(int TIMELIMIT) {
            this.TIMELIMIT = TIMELIMIT;
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
