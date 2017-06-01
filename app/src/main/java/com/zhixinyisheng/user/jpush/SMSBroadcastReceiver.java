package com.zhixinyisheng.user.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zhixinyisheng.user.ui.data.BLE.control.BtSerializeation;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;

/**
 * 接受短信的广播
 * Created by 焕焕 on 2017/2/5.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("jieshoudao le ","jiasjdoasj");
        if (TestService.mDevice!=null&&TestService.is_dx){
            TestService.mService.writeRXCharacteristic(BtSerializeation.shDXml());
        }
    }
}
