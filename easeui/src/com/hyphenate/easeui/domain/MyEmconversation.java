package com.hyphenate.easeui.domain;


import android.util.Log;

import com.hyphenate.chat.EMConversation;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/11/2  15:20
 * <p>    系统消息
 * 类说明: 封装Emconversation类进行统一排序用
 */
public class MyEmconversation {
    private EMConversation emConversation;
    private SysMessageEntity.ListBean listBean;
    private long time;


    public EMConversation getEmConversation() {
        return emConversation;
    }

    public void setEmConversation(EMConversation emConversation) {
        this.emConversation = emConversation;
        if (emConversation.getLastMessage()!= null){
            Log.d("MyEmconversation", "emConversation.getLastMessage().getMsgTime():" + emConversation.getLastMessage().getMsgTime());
            time = emConversation.getLastMessage().getMsgTime();
            Log.d("MyEmconversation", emConversation.getLastMessage().getBody().toString());
        }

    }

    public SysMessageEntity.ListBean getListBean() {
        return listBean;
    }

    public void setListBean(SysMessageEntity.ListBean listBean) {
        this.listBean = listBean;
        String time = listBean.getCreateTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
            this.time = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("MyEmconversation", String.valueOf(this.time));

    }


    public long getTime() {
        return time;
    }


//    @Override
//    public int compareTo(Object o) {
//        time = (long) o;
//        if (time>this.time){
//            return 1;
//        }else if (time < this.time){
//            return -1;
//        }else {
//            return 0;
//        }
//
//    }
}
