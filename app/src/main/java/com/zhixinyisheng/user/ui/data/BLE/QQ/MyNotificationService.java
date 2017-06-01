package com.zhixinyisheng.user.ui.data.BLE.QQ;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.control.BtSerializeation;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;


/**
 * Created by USER on 2016/8/14.
 */
public class MyNotificationService extends AccessibilityService {
    private static String qqpimsecure = "com.tencent.qqpimsecure";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            if (event.getPackageName().equals("com.tencent.mm")){
                if (TestService.mDevice!=null&&TestService.is_wx){
                    TestService.mService.writeRXCharacteristic(BtSerializeation.shWXml());
                }

            }else if (event.getPackageName().equals("com.tencent.mobileqq")){
                MyLog.showLog("基础服务",event.toString());




                if (TestService.mDevice!=null&&TestService.is_qq){
                    TestService.mService.writeRXCharacteristic(BtSerializeation.shQQml());
                }

            }else if (event.getPackageName().equals("com.android.mms")){
                if (TestService.mDevice!=null&&TestService.is_dx){
                    TestService.mService.writeRXCharacteristic(BtSerializeation.shDXml());
                }
            }


        } else {
        }
    }

    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {

    }
}
