package com.zhixinyisheng.user.domain;


import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class FriendList {



    private String result;
    private int size;
    /**
     * agreeFlag : 0
     * createTime : 2016-10-26
     * datas : 0
     * friendsId : 7
     * frinedsRemark : 刘依依
     * fromUserId : d9b5b203eb98458cb31337abad51b1c3
     * headUrl : https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png
     * name : 李聪
     * phone : 15081826721
     * sos : 0
     * toUserId : 1
     * twoDimension : 二维码
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
        private int agreeFlag;
        private String createTime;
        private int datas;
        private String friendsId;
        private String frinedsRemark;
        private String fromUserId;
        private String headUrl;
        private String name;
        private String phone;
        private int sos;
        private String toUserId;
        private String twoDimension;

        public int getAgreeFlag() {
            return agreeFlag;
        }

        public void setAgreeFlag(int agreeFlag) {
            this.agreeFlag = agreeFlag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDatas() {
            return datas;
        }

        public void setDatas(int datas) {
            this.datas = datas;
        }

        public String getFriendsId() {
            return friendsId;
        }

        public void setFriendsId(String friendsId) {
            this.friendsId = friendsId;
        }

        public String getFrinedsRemark() {
            return frinedsRemark;
        }

        public void setFrinedsRemark(String frinedsRemark) {
            this.frinedsRemark = frinedsRemark;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSos() {
            return sos;
        }

        public void setSos(int sos) {
            this.sos = sos;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getTwoDimension() {
            return twoDimension;
        }

        public void setTwoDimension(String twoDimension) {
            this.twoDimension = twoDimension;
        }
    }
}
