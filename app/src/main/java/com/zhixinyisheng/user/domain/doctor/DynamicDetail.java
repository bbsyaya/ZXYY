package com.zhixinyisheng.user.domain.doctor;

/**
 * Created by 风度侠客 on 2017/1/9.
 */

public class DynamicDetail {

    /**
     * result : 0000
     * db : {"createtime":1483242799000,"content":"3333","zan":2,"name":"12","headUrl":"http://222.222.24.133:8099/picResource/sbs/common/HEADURL.png"}
     * retMessage : 查询成功
     */

    private String result;
    private DbBean db;
    private String retMessage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public static class DbBean {
        /**
         * createtime : 1483242799000
         * content : 3333
         * zan : 2
         * name : 12
         * headUrl : http://222.222.24.133:8099/picResource/sbs/common/HEADURL.png
         */

        private long createtime;
        private String content;
        private int zan;
        private String name;
        private String headUrl;
        private String picUrl;
        private int isZan;

        public int getIsZan() {
            return isZan;
        }

        public void setIsZan(int isZan) {
            this.isZan = isZan;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
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

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }
    }
}
