package com.zhixinyisheng.user.domain;

/**
 * 通讯录值
 * Created by 焕焕 on 2016/12/22.
 */
public class PhoneValuesInfo {
    private String phone;
    private String friendRemark;
    private String isAdded;
    private String isFriend;
    private String headUrl;
    public PhoneValuesInfo() {
    }

    public PhoneValuesInfo(String phone, String friendRemark, String isAdded, String isFriend, String headUrl) {
        this.phone = phone;
        this.friendRemark = friendRemark;
        this.isAdded = isAdded;
        this.isFriend = isFriend;
        this.headUrl = headUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
