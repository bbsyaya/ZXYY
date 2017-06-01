package com.zhixinyisheng.user.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.multidex.MultiDex;

import com.and.yzy.frame.application.BaseApplication;
import com.karumi.dexter.Dexter;
import com.zhixinyisheng.user.dao.SQLHelper;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.data.BLE.QQ.MyNotificationService;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.util.KeepAliveWatcher;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2016/6/28.
 */
public class MyApplication extends BaseApplication {
    private static MyApplication mAppApplication;

    private SQLHelper sqlHelper;
    public static String resultId;
    public static String currentUserNick = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }


    @Override
    public void onCreate() {
        super.onCreate();
//        Logger.e("myApplication","启动");
        mAppApplication = this;
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
        resultId = JPushInterface.getRegistrationID(this);
//        SDKInitializer.initialize(this);
        Dexter.initialize(this);
        //初始化环信IM SDK
        DemoHelper.getInstance().init(mAppApplication);
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                KeepAliveWatcher.keepAlive(MyApplication.this);
                startService(new Intent(MyApplication.this, TestService.class));
                startService(new Intent(MyApplication.this, MyNotificationService.class));
                return false;
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        5454
//        AppManger.getInstance().AppExit(this);
    }

    /** 获取Application */
    public static MyApplication getApp() {
        return mAppApplication;
    }


//    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(mAppApplication);
        return sqlHelper;
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
        //整体摧毁的时候调用这个方法
    }
}
