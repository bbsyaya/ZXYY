package com.zhixinyisheng.user.domain.doctor;

import java.util.List;

/**
 * Created by 风度侠客 on 2017/1/15.
 */

public class Service {

    /**
     * result : 0000
     * list : [{"content":"","createtime":1484357479000,"title":"未付费","toUserId":"159d7d91fd7a43dcac63c7f795b72791","name":"12","isAppraise":0,"section":"肾病科室","payRecordId":"8a76039c5584479cb91cd90a10a744a5"}]
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
         * content :
         * createtime : 1484357479000
         * title : 未付费
         * toUserId : 159d7d91fd7a43dcac63c7f795b72791
         * name : 12
         * isAppraise : 0
         * section : 肾病科室
         * payRecordId : 8a76039c5584479cb91cd90a10a744a5
         */

        private String content;
        private long createtime;
        private String title;
        private String toUserId;
        private String name;
        private int isAppraise;
        private String section;
        private String payRecordId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsAppraise() {
            return isAppraise;
        }

        public void setIsAppraise(int isAppraise) {
            this.isAppraise = isAppraise;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getPayRecordId() {
            return payRecordId;
        }

        public void setPayRecordId(String payRecordId) {
            this.payRecordId = payRecordId;
        }
    }
}
