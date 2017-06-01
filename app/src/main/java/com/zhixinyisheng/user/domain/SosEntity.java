package com.zhixinyisheng.user.domain;

import java.io.Serializable;

/**
 * Created by 焕焕 on 2016/11/10.
 */
public class SosEntity implements Serializable{

    /**
     * address :
     * content : 您的好友向您发起了紧急呼救！
     * from : JPush
     * fromuserId : 2c0fcf1171e1423097814250c23edceb
     * headUrl : http://222.222.24.133:8099/picResource/sbs/user/2016-10/fac961bdfa074561ae8623544c8cea1a.png
     * helpId : 2c0fcf1171e1423097814250c23edceb
     * helpName :
     * lat : 39.90678
     * lon : 116.397124
     * name :
     * picUrl :
     * soundUrl :
     * sysMessage_ID : 0f2f2ddf4b5f4796b12e4dcdd3c46830
     * title : 石家庄肾病医院
     * toUserId : d9b5b203eb98458cb31337abad51b1c3
     * type : 2
     */

    private String address;
    private String content;
    private String from;
    private String fromuserId;
    private String headUrl;
    private String helpId;
    private String helpName;
    private String lat;
    private String lon;
    private String name;
    private String picUrl;
    private String soundUrl;
    private String sysMessage_ID;
    private String title;
    private String toUserId;
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromuserId() {
        return fromuserId;
    }

    public void setFromuserId(String fromuserId) {
        this.fromuserId = fromuserId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getHelpId() {
        return helpId;
    }

    public void setHelpId(String helpId) {
        this.helpId = helpId;
    }

    public String getHelpName() {
        return helpName;
    }

    public void setHelpName(String helpName) {
        this.helpName = helpName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public String getSysMessage_ID() {
        return sysMessage_ID;
    }

    public void setSysMessage_ID(String sysMessage_ID) {
        this.sysMessage_ID = sysMessage_ID;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
