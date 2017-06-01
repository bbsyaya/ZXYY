package com.zhixinyisheng.user.ui.IM.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/10/18  9:06
 * <p/>
 * 类说明: SPF工具类
 */
public class SPFManager {
    /**
     * name of preference
     */
    public static final String PREFERENCE_NAME = "saveInfo";
    private static SharedPreferences mSharedPreferences;
    private static SPFManager mPreferencemManager;
    private static SharedPreferences.Editor editor;

    //临时只注册一遍
    private String SHARED_KEY_SETTING_MYZC = "shared_key_setting_myzc";



    @SuppressLint("CommitPrefEdits")
    private SPFManager(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context cxt){
        if(mPreferencemManager == null){
            mPreferencemManager = new SPFManager(cxt);
        }
    }

    /**
     *
     * @param
     * @return
     */
    public synchronized static SPFManager getInstance() {
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init first!");
        }

        return mPreferencemManager;
    }

    public void setSettingMsgMyzc(boolean paramBoolean) {
        editor.putBoolean(SHARED_KEY_SETTING_MYZC, paramBoolean);
        editor.apply();
    }

    public boolean getSettingMsgMyzc() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_MYZC, true);
    }



}
