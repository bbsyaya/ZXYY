package com.zhixinyisheng.user.ui.data.BLE.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by USER on 2016/8/18.
 */
public class MyActivityStack {
    private static Stack<Activity> mStack;

    public MyActivityStack(){
        mStack =new Stack<Activity>();
    }

    public void add(Activity activity){

        mStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity current() {
        Activity activity = mStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finish() {
        Activity activity = mStack.lastElement();
        if (activity != null) {
            mStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public void finish(Class<?> cls) {
        List<Activity> list = new ArrayList<Activity>();
        for (Activity activity : mStack) {
            if (activity.getClass().equals(cls)) {
                list.add(activity);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).finish();
        }



    }

    //获取activity数量
    public int getAcNO(){
        return mStack.size();
    }

    /**
     * 结束所有Activity
     */
    public void finishAll() {
        for (int i = 0, size = mStack.size(); i < size; i++) {
            if (null != mStack.get(i)) {
                mStack.get(i).finish();
            }
        }
        mStack.clear();
    }

}


