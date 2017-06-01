package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * 睡眠质量
 * Created by 焕焕 on 2016/11/2.
 */
public class ShuiMianZXT {

    /**
     * result : 0000
     * list : [{"HOUR":"0","BYTIME":"2016-11-02"}]
     * retMessage : 查询成功
     */

    private String result;
    private String retMessage;
    /**
     * HOUR : 0
     * BYTIME : 2016-11-02
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String HOUR;
        private String BYTIME;

        public String getHOUR() {
            return HOUR;
        }

        public void setHOUR(String HOUR) {
            this.HOUR = HOUR;
        }

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }
    }
}
