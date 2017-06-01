package com.zhixinyisheng.user.ui.data.BLE.zhenghe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhixinyisheng.user.ui.data.BLE.MyLog;

/**
 * Created by USER on 2016/8/11.
 */
public abstract class BLEReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
//        MyLog.showLog("手环情况 jujing", action);
        //连接之前
        if (action.equals(BLEService.ACTION_GATT_CONNECTED)) {
            MyLog.showLog("手环情况", action);
        }

        else if (action.equals(BLEService.ACTION_GATT_SERVICES_DISCOVERED)) {
            MyLog.showLog("手环情况", action);
            bleReceive(intent);

        }

        else if (action.equals(BLEService.ACTION_DATA_AVAILABLE)) {
//            String strf = intent.getStringExtra(BLEService.EXTRA_DATA);
//            Log.e("strf jujing",strf+"@@@@");
//
//            final byte[] txValue = intent.getByteArrayExtra(BLEService.EXTRA_DATA);
//            Log.e("txValue",txValue[0]+"@@"+txValue[1]);

            //回调方法
            bleReceive(intent);
        }

        else if (action.equals(BLEService.ACTION_GATT_DISCONNECTED)) {
//            MyLog.showLog("手环情况", action);
            bleReceive(intent);
        }

        else if (action.equals(BLEService.RSSI_DATA)) {
            String myrssi = intent.getStringExtra(BLEService.EXTRA_DATA);
//            Log.e("strf jujing 弄弄弄",myrssi);
            bleReceive(intent);
        }

        else  if (action.equals(BLEService.DEVICE_DOES_NOT_SUPPORT_UART)){

        }



    }


    public abstract void bleReceive(Intent intent);

//    // 回调接口
//    public interface MessageListener {
//        public void OnReceived(Intent intent);
//    }
//    public void setOnReceivedMessageListener(MessageListener messageListener) {
//        this.mMessageListener = messageListener;
//    }








}
