package com.zhixinyisheng.user.domain;

import java.util.List;

/**
 * 根据用户ID查询有数据的日期 的实体类
 * Created by 焕焕 on 2016/11/23.
 */
public class FindByPidEntity {

    /**
     * list : [{"BYTIME":"2016-11-21"},{"BYTIME":"2016-11-18"}]
     * result : 0000
     * retMessage : 操作成功
     */

    private String result;
    private String retMessage;
    /**
     * BYTIME : 2016-11-21
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
        private String BYTIME;

        public String getBYTIME() {
            return BYTIME;
        }

        public void setBYTIME(String BYTIME) {
            this.BYTIME = BYTIME;
        }
    }
}
