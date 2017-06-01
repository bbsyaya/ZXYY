package com.zhixinyisheng.user.domain.doctor;

import java.util.List;

/**
 * Created by Yuanyx on 2017/1/8.
 */

public class DoctorDynamic {


    /**
     * result : 0000
     * list : [{"createtime":1483242799000,"content":"3333","picUrl":"","doctorsInfoId":"1","zan":2}]
     * retMessage : 查询成功
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
         * createtime : 1483242799000
         * content : 3333
         * picUrl :
         * doctorsInfoId : 1
         * zan : 2
         */

        private long createtime;
        private String content;
        private String picUrl;
        private String doctorsInfoId;
        private int zan;
        private int isZan;

        public int getIsZan() {
            return isZan;
        }

        public void setIsZan(int isZan) {
            this.isZan = isZan;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getDoctorsInfoId() {
            return doctorsInfoId;
        }

        public void setDoctorsInfoId(String doctorsInfoId) {
            this.doctorsInfoId = doctorsInfoId;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
