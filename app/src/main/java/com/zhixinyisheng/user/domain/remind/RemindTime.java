package com.zhixinyisheng.user.domain.remind;

/**
 * Created by Administrator on 2017/4/20.
 */

public class RemindTime {
    private boolean isAdd=false;
    private String title="提醒时间";
    private String time="";
    private boolean isAmend=false;

    public boolean isAmend() {
        return isAmend;
    }

    public void setAmend(boolean amend) {
        isAmend = amend;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isDiplayCheck() {
        return isDiplayCheck;
    }

    public void setDiplayCheck(boolean diplayCheck) {
        isDiplayCheck = diplayCheck;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    private boolean isDiplayCheck;
}
