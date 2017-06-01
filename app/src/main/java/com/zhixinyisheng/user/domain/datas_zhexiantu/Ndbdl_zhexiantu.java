package com.zhixinyisheng.user.domain.datas_zhexiantu;

import java.util.List;

/**
 * 尿蛋白定量折线图
 * Created by gjj on 2016/11/10.
 */
public class Ndbdl_zhexiantu {
    /**
     * result : 0000
     * list : [{"BYTIME":"2016-11-10","NUM":"1"}]
     * retMessage : 成功！
     * size : 1
     */

    private String result;
    private String retMessage;
    private int size;
    /**
     * BYTIME : 2016-11-10
     * NUM : 1
     */

    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String BYTIME;
        private String NUM;

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }

        public String getNUM() {
            return NUM;
        }

        public void setNUM(String NUM) {
            this.NUM = NUM;
        }
    }
}
