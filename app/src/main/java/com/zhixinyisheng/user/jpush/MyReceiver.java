package com.zhixinyisheng.user.jpush;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.SosEntity;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.interfaces.OnFriendsLoadedListener;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.IM.ui.ConversationAty;
import com.zhixinyisheng.user.ui.MyApplication;
import com.zhixinyisheng.user.ui.data.BLE.common.CommonSHML;
import com.zhixinyisheng.user.ui.data.BLE.common.Utils;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.ui.mine.RenZhengAty;
import com.zhixinyisheng.user.ui.mydoctor.activity.WanShanXinXiAty;
import com.zhixinyisheng.user.ui.sos.HelpReceivedAty;
import com.zhixinyisheng.user.util.tool.ActivityController;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的广播
 * Created by 焕焕 on 2016/10/20.
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private NotificationManager nm;
    private String type;//推送的类型
    Activity activity;
    @Override
    public void onReceive(final Context context, Intent intent) {
        activity = ActivityController.getCurrentActivity();
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            MyApplication.resultId = JPushInterface.getRegistrationID(context);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");
            receivingNotification(context, bundle);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Logger.e("extras 54", extras);
            try {
                JSONObject object = new JSONObject(extras);
                type = object.getString("type");
                BaseFrameAty.showLog("jpush 111111", type);
                switch (type) {
                    case "1": {//好友申请
                        //小红点
                        Intent intent1 = new Intent(Constant.MYBROADCASTACTION_XIAOHONGDIAN);
                        context.sendBroadcast(intent1);
                        break;
                    }
                    case "13": //收到好友同意加好友的推送
                        DemoHelper.getInstance().asyncFetchContactsFromServer(null);
                        DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
                            @Override
                            public void friendsLoaded() {
                                //刷新界面
                                Intent intent2 = new Intent(Constant.MYRECEIVER_TYPE13);
                                context.sendBroadcast(intent2);
                            }

                            @Override
                            public void LoadedError() {
                            }
                        });
                        break;
                    case "2": {//sos呼救
                        //提示手环有人向您呼救    剧京：先注释
                        if (TestService.mDevice != null) {
                            Utils.sendSHML(CommonSHML.SHML_YRHJ);
                        }
                        SosEntity sosEntity = JSON.parseObject(extras, SosEntity.class);
                        Intent intent1 = new Intent("sosaccept.broadcast.action");//接受sos的广播

                        intent1.putExtra("sosEntity", sosEntity);
                        context.sendBroadcast(intent1);
                        break;
                    }
                    case "3": //好友消息推送
                        return;
                    case "4": {//重要通知
                        //小红点
                        Intent intent1 = new Intent(Constant.MYBROADCASTACTION_XIAOHONGDIAN);
                        context.sendBroadcast(intent1);
                        break;
                    }
                    case "10": // 对方已同意查看健康数据
                        Intent intent10 = new Intent(Constant.MYRECEIVER_TYPE_10);
                        context.sendBroadcast(intent10);

                        break;
                    case "11": //日报

                        break;
                    case "12": // 周报

                        break;
                    case "6": {//异常数据
                        //小红点
                        Intent intent1 = new Intent(Constant.MYBROADCASTACTION_XIAOHONGDIAN);
                        context.sendBroadcast(intent1);
                        break;
                    }
                    case "7": //医生认证成功
                        Intent intent1 = new Intent(Constant.MYBROADCASTACTION_XIAOHONGDIAN);
                        context.sendBroadcast(intent1);
                        UserInfo userInfo = UserManager.getUserInfo();
                        userInfo.setIsDoctor(1);
                        UserManager.setUserInfo(userInfo);
                        if (isRunningForeground(context)) {
                            new MaterialDialog(activity)
                                    .setMDNoTitle(true)
                                    .setMDMessage(context.getString(R.string.ninyitongguoyishirenzheng))
                                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {
                                            Intent intent = new Intent(activity, WanShanXinXiAty.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            activity.startActivity(intent);
                                        }
                                    })
                                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {

                                        }
                                    })
                                    .show();
                        }
                        break;
                    case "8": //医生认证失败
//                    UserManager.getUserInfo().setAttpStates(3);


                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            openNotification(context, bundle);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
    }

    public boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (currentPackageName != null && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 用户打开了通知
     *
     * @param context
     * @param bundle
     */
    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Logger.e("推送 extras", extras);
        try {
            JSONObject object = new JSONObject(extras);
            type = object.getString("type");
            if (type.equals("1")) {//好友申请
                Intent intent = new Intent(context, ConversationAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (type.equals("13")){
                Intent intent = new Intent(context, ConversationAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (type.equals("2")) {//sos呼救
                SosEntity sosEntity = JSON.parseObject(extras, SosEntity.class);
                if (sosEntity.getLat().equals("")) {
                    //考虑放弃救援的情况
                } else if (sosEntity.getFromuserId().equals(UserManager.getUserInfo().getUserId())){

                }else {
                    Intent intent = new Intent(context, HelpReceivedAty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("sosEntity", sosEntity);
                    context.startActivity(intent);
                }
            } else if (type.equals("3")) {//好友消息推送
                Intent intent = new Intent(context, ConversationAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } else if (type.equals("4")) {//重要通知 (TAP TAP)
                Intent intent = new Intent(context, ConversationAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (type.equals("6")) {//异常数据
                Intent intent = new Intent(context, ConversationAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (type.equals("11")) {//日报

            } else if (type.equals("12")) {// 周报

            }else if (type.equals("7")){//医师认证成功
                Intent intent = new Intent(context, WanShanXinXiAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (type.equals("8")){//医师认证失败
                Intent intent = new Intent(context, RenZhengAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
