package com.zhixinyisheng.user.config;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.config.UserInfoManger;
import com.and.yzy.frame.util.SPUtils;
import com.zhixinyisheng.user.domain.UserInfo;

/**
 * Created by Administrator on 2016/ic_02/27.
 * 用户管理类
 */
public class UserManager extends UserInfoManger {

  /*  *//**
     * 默认精度
     *//*
    public static final String DAFAULT_LNG="117.159644";
    *//**
     * 默认纬度
     *//*
    public static final String DAFAULT_LAT="39.098411";*/

    /**
     * 设置百度纬度
     *
     * @param
     */
    public static void setBaiDuLat(String lat) {

        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("BaiDuLat", lat);
    }

    /**
     * 设置高德城市
     *
     * @param
     */
    public static void setGaoDeCity(String city) {

        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("GaoDeCity", city);
    }

    /**
     * 得到高德城市
     *
     * @param
     */
    public static String getGaoDeCity() {

        SPUtils spUtils = new SPUtils("userConfig");
        return (String) spUtils.get("GaoDeCity", "石家庄市");
    }

    /**
     * 设置高德省份
     *
     * @param
     */
    public static void setGaoDeProvince(String province) {

        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("GaoDeProvince", province);
    }

    /**
     * 得到高德省份
     *
     * @param
     */
    public static String getGaoDeProvince() {

        SPUtils spUtils = new SPUtils("userConfig");
        return (String) spUtils.get("GaoDeProvince", "河北省");
    }

    /**
     * 设置百度经度
     *
     * @param
     */
    public static void setBaiDuLon(String lon) {

        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("BaiDuLon", lon);
    }


    /**
     * 得到百度纬度
     *
     * @param
     */
    public static String getBaiDuLat() {

        SPUtils spUtils = new SPUtils("userConfig");
        return (String) spUtils.get("BaiDuLat", "39.098411");
    }


    /**
     * 得到百度经度
     *
     * @param
     */
    public static String getBaiDuLon() {

        SPUtils spUtils = new SPUtils("userConfig");
        return (String) spUtils.get("BaiDuLon", "117.159644");
    }

    public static void setUserInfo(UserInfo userInfo) {
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("userInfo", JSON.toJSONString(userInfo));
    }

    //    public static void setIndex(IndexResult info){
//        SPUtils spUtils = new SPUtils("userConfig");
//        spUtils.put("indexResult", JSON.toJSONString(info));
//    }
//
//
    public static UserInfo getUserInfo() {
        SPUtils spUtils = new SPUtils("userConfig");
        String userdata = (String) spUtils.get("userInfo", "{}");
        return JSON.parseObject(userdata, UserInfo.class);
    }
//
//    public static IndexResult getIndexResult(){
//        SPUtils spUtils = new SPUtils("userConfig");
//        String userdata = (String) spUtils.get("indexResult", "{}");
//        if (userdata.equals("{}")){
//            return null;
//        }
//        return JSON.parseObject(userdata,IndexResult.class);
//    }
//
//    public static String getM_id(){
//
//        if (getUserInfo()==null){
//            return null;
//        }
//        return  getUserInfo().getM_id();
//    }

    /**
     * 设置登陆状态
     */
    public static void setIsLogin(boolean b) {
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("isLogin", b);
    }

    /**
     * 保存信息id
     */
    public static void saveMsgIdSp(String msgId) {
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("msgId", msgId);
    }

    /**
     * 获取信息id
     */
    public static String getMsgId() {
        SPUtils spUtils = new SPUtils("userConfig");
        String msgId = (String) spUtils.get("msgId", "");
        return msgId;
    }

    public static void setChatNum(int msgId){
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("msgNum", msgId);
    }
    public static int getChatNum(){
        SPUtils spUtils = new SPUtils("userConfig");
        int msgNum= (int) spUtils.get("msgNum",0);
        return msgNum;
    }

}
