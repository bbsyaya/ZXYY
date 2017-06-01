package com.zhixinyisheng.user.domain.datas_zhexiantu;

import java.util.List;

/**
 * 肺活量
 * Created by 焕焕 on 2017/1/14.
 */
public class PulmonaryZXT {

    /**
     * result : 0000
     * list : [{"USER_ID":"a443872d4f474e3987bd774f5553be28","BYTIME":"2017-01-14","NUM":874},{"USER_ID":"a443872d4f474e3987bd774f5553be28","BYTIME":"2017-01-14","NUM":1786},{"USER_ID":"a443872d4f474e3987bd774f5553be28","BYTIME":"2017-01-14","NUM":380},{"USER_ID":"a443872d4f474e3987bd774f5553be28","BYTIME":"2017-01-14","NUM":456},{"USER_ID":"a443872d4f474e3987bd774f5553be28","BYTIME":"2017-01-14","NUM":380},{"USER_ID":"a443872d4f474e3987bd774f5553be28","BYTIME":"2017-01-14","NUM":570},{"USER_ID":"a443872d4f474e3987bd774f5553be28","BYTIME":"2017-01-14","NUM":532}]
     * SIZE : 7
     */

    private String result;
    private int SIZE;
    /**
     * USER_ID : a443872d4f474e3987bd774f5553be28
     * BYTIME : 2017-01-14
     * NUM : 874.0
     */

    private List<ListBean> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSIZE() {
        return SIZE;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String USER_ID;
        private String BYTIME;
        private double NUM;

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public double getNUM() {
            return NUM;
        }

        public void setNUM(double NUM) {
            this.NUM = NUM;
        }
    }
}
