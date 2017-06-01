package com.zhixinyisheng.user.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.MyApplication;

/**
 * Created by gjj on 2016/7/22.
 * 验证码倒计时
 */
public class TimeCount extends CountDownTimer{

    public TextView textView;

    public TimeCount(long totalTime, long interval, TextView textView){
        super (totalTime,interval);
        this.textView = textView;
    }
    @Override
    public void onTick(long millisUntilFinished) {
        textView.setEnabled(false);//按钮不可点击
        //参数millisUntilFinished表示剩余时间
        textView.setText(millisUntilFinished/1000+ MyApplication.getApp().getString(R.string.miao));
//        textView.setTextColor(Color.parseColor("#c0c0c0"));
    }

    @Override
    public void onFinish() {
        textView.setEnabled(true);//恢复按钮可点击
        textView.setText(R.string.chongxinhuoqu);//修改按钮提示文字
//        textView.setTextColor(Color.parseColor("#000000"));
    }
}
