package com.zhixinyisheng.user.domain.doctor;

import java.util.List;

/**
 * Created by 风度侠客 on 2017/1/5.
 */

public class Section {

    /**
     * result : 0000
     * list : [{"sectionId":"1","name":"1"}]
     * retMessage : 科室列表查询成功
     * size : 1
     */

    private String result;
    private String retMessage;
    private int size;
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
        /**
         * sectionId : 1
         * name : 1
         */

        private String sectionId;
        private String name;

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
