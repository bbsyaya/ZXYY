package com.flyco.dialog.entity;

public class DialogMenuItem {
    public String mOperName;
    public int mResId;

    public DialogMenuItem(String mOperName) {
        this.mOperName = mOperName;
    }

    public DialogMenuItem(String operName, int resId) {
        mOperName = operName;
        mResId = resId;
    }

    public String getmOperName() {
        return mOperName;
    }

    public void setmOperName(String mOperName) {
        this.mOperName = mOperName;
    }

    public int getmResId() {
        return mResId;
    }

    public void setmResId(int mResId) {
        this.mResId = mResId;
    }
}
