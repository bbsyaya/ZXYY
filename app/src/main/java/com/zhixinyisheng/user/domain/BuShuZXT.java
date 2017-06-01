package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * 步数
 * Created by 焕焕 on 2016/11/1.
 */
public class BuShuZXT {

    /**
     * list : [{"ADDTIME":"2016-11-01","BYTIME":"2016-11-01","STEPNUMBER_ID":"31d3b3f7cf824fcd9ba2371b58898482","STEPS":672,"USER_ID":"2c0fcf1171e1423097814250c23edceb","state":1}]
     * result : 0000
     * size : 1
     */

    private String result;
    private int size;
    /**
     * ADDTIME : 2016-11-01
     * BYTIME : 2016-11-01
     * STEPNUMBER_ID : 31d3b3f7cf824fcd9ba2371b58898482
     * STEPS : 672
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
        private String BYTIME;
        private String STEPNUMBER_ID;
        private int STEPS;
        private String USER_ID;
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

        public String getSTEPNUMBER_ID() {
            return STEPNUMBER_ID;
        }

        public void setSTEPNUMBER_ID(String STEPNUMBER_ID) {
            this.STEPNUMBER_ID = STEPNUMBER_ID;
        }

        public int getSTEPS() {
            return STEPS;
        }

        public void setSTEPS(int STEPS) {
            this.STEPS = STEPS;
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
