package com.zhixinyisheng.user.domain.datas;

import java.util.List;

/**
 * Created by 焕焕 on 2017/4/19.
 */

public class StepSort {

    /**
     * STEPS : 0
     * card : 5992
     * frinedsRemark : 匆匆那年
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2017-02/42482a1197ea4823b72878664098ffd5.png
     * name : 匆匆那年
     * sort : 7
     * userId : 4949162d803642eeb8f5ac81c683a8c4
     * username : 那兔
     */

    private DbBean db;
    /**
     * db : {"STEPS":0,"card":5992,"frinedsRemark":"匆匆那年","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/42482a1197ea4823b72878664098ffd5.png","name":"匆匆那年","sort":7,"userId":"4949162d803642eeb8f5ac81c683a8c4","username":"那兔"}
     * list : [{"STEPS":4126,"card":5997,"frinedsRemark":"","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-03/1d0838dd077e7487cb5fcb0865e8b515a.jpg","name":"侯weijia","sort":1,"userId":"17211a90d6a24d688fb651c8bf0c18b5","username":"侯卫嘉Laos"},{"STEPS":0,"card":6013,"frinedsRemark":"","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-03/3f9a16bf315748438740324b1f3ddc0f.png","name":"","sort":2,"userId":"dc60c824b46d454899275f588e7d45d4","username":"jujing"},{"STEPS":0,"card":6008,"frinedsRemark":"","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/1a1ff81fffd2b43159a6caa6f79444394.jpg","name":"李四光","sort":3,"userId":"8034fdfd9aa440a89c07337d3058ee7d","username":"李四光"},{"STEPS":0,"card":5999,"frinedsRemark":"","headUrl":"http://192.168.42.165:8080/zxys_user/api/images/tongue/2017-03/e2fff00dcb5d4074b1f3dc72f091762b.png","name":"董大夫","sort":4,"userId":"61bf534a669b4742baefba926da3a576","username":"小女孩"},{"STEPS":0,"card":5996,"frinedsRemark":"","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/1a854fce421c483485dda461aec4fbd2.png","name":"陈焕","sort":5,"userId":"6053fb1a32d34adfb941d35a89fbbaf1","username":"追逐太阳"},{"STEPS":0,"card":5994,"frinedsRemark":"亚新","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/42482a1197ea4823b72878664098ffd5.png","name":"苑亚欣","sort":6,"userId":"27e1800f13a34698b58e4ad2d9b16c02","username":"老苑"},{"STEPS":0,"card":5992,"frinedsRemark":"匆匆那年","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/42482a1197ea4823b72878664098ffd5.png","name":"匆匆那年","sort":7,"userId":"4949162d803642eeb8f5ac81c683a8c4","username":"那兔"},{"STEPS":0,"card":5001,"frinedsRemark":"","headUrl":"http://222.222.24.133:8099/picResource/sbs/user/2017-02/e5237a4ba52b455a93383d3b35f11fce.png","name":"Doctor.Jin","sort":8,"userId":"e4bb0bce8ceb47c0a4aeae42f5f69ada","username":"老金"}]
     * result : 0000
     * retMessage : 操作成功！
     * size : 8
     */

    private String result;
    private String retMessage;
    private int size;
    /**
     * STEPS : 4126
     * card : 5997
     * frinedsRemark :
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2017-03/1d0838dd077e7487cb5fcb0865e8b515a.jpg
     * name : 侯weijia
     * sort : 1
     * userId : 17211a90d6a24d688fb651c8bf0c18b5
     * username : 侯卫嘉Laos
     */

    private List<ListBean> list;

    public DbBean getDb() {
        return db;
    }

    public void setDb(DbBean db) {
        this.db = db;
    }

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

    public static class DbBean {
        private int STEPS;
        private int card;
        private String frinedsRemark;
        private String headUrl;
        private String name;
        private int sort;
        private String userId;
        private String username;

        public int getSTEPS() {
            return STEPS;
        }

        public void setSTEPS(int STEPS) {
            this.STEPS = STEPS;
        }

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class ListBean {
        private int STEPS;
        private int card;
        private String frinedsRemark;
        private String headUrl;
        private String name;
        private int sort;
        private String userId;
        private String username;

        public int getSTEPS() {
            return STEPS;
        }

        public void setSTEPS(int STEPS) {
            this.STEPS = STEPS;
        }

        public int getCard() {
            return card;
        }

        public void setCard(int card) {
            this.card = card;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
