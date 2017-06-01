package com.zhixinyisheng.user.domain;

/**
 * 通讯录实体类
 * Created by 焕焕 on 2016/12/22.
 */
public class PhoneInfo {
    private String phoneName;
    private String phoneNumber;

    public PhoneInfo() {
    }

    public PhoneInfo(String phoneName, String phoneNumber) {
        this.phoneName = phoneName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
