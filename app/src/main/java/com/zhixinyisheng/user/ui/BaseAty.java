package com.zhixinyisheng.user.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.SPUtils;
import com.hyphenate.easeui.controller.EaseUI;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.tool.ActivityController;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseAty extends BaseFrameAty {
    public boolean isShowToast = true;
    public String userId = BaseApplication.userId;
    //    public String userId= UserManager.getUserInfo().getUserId();
    public String phone = UserManager.getUserInfo().getPhone();//登录手机号
    public String secret = UserManager.getUserInfo().getSecret();//登录返回秘钥
    public String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//当前时间

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void btnClick(View view) {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    protected void switchLang(String newLang) {
        //TODO 更新Sharedpreferences中存储的app语言环境
        SPUtils spUtils = new SPUtils("language");
        spUtils.put("language", newLang);
        initLang();
        // finish app内存中的所有activity
        AppManger.getInstance().killAllActivity();
        // 跳转到app首页
        startActivity(MainAty.class, null);
    }
    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.BUTTON_BACK) {
//            View v = getCurrentFocus();
//            if (isShouldHideKeyboard(v, ev)) {
//                hideKeyboard(v.getWindowToken());
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    //IM
    protected InputMethodManager inputMethodManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //环信中基类
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ("".equals(userId) || null == userId) {
            userId = UserManager.getUserInfo().getUserId();
        }
        //IM
        EaseUI.getInstance().getNotifier().reset();
        ActivityController.addActivity(this);
        ActivityController.setCurrentActivity(this);
    }

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Content.DATA = time;
        ActivityController.removeActivity(this);
    }

    /**
     * back
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }


}
