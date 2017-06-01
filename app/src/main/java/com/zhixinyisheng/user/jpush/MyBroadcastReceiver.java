package com.zhixinyisheng.user.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * 透传
 * Created by 焕焕 on 2016/11/15.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    private static MessageListener mMessageListener;//医生端的监听
    public MyBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("cmd_value");
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");
//		Toast.makeText(context, "终于收到了广播"+intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
//		Log.e("终于收到了广播", intent.getStringExtra("cmd_value")+","+name+","+lat+","+lon);
//		Toast.makeText(context, "终于收到了广播"+intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
        //Log.e("终于收到了广播", intent.getStringExtra("cmd_value")+","+name+","+lat+","+lon);
        List<String> message = new ArrayList<String>();
        message.add(stringExtra);//action
        message.add(name);//姓名
        message.add(id);//ID
        message.add(lat);
        message.add(lon);
        try {
            mMessageListener.OnReceived(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
//		mMessageListener.OnReceived(na✔me);
//		mMessageListener.OnReceived(lat);
//		mMessageListener.OnReceived(lon);
    }


    // 回调接口
    public interface MessageListener {
        public void OnReceived(List<String> message);
    }

    public void setOnReceivedMessageListener(MessageListener messageListener) {
        this.mMessageListener=messageListener;
    }

}
