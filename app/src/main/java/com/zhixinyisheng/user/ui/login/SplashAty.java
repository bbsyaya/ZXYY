package com.zhixinyisheng.user.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.and.yzy.frame.application.BaseApplication;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.MainAty;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;

/**
 * 开始的闪烁页
 * Created by 焕焕 on 2017/2/7.
 */
public class SplashAty extends BaseAty {
    @Bind(R.id.iv_splash)
    ImageView ivSplash;

    @Override
    public int getLayoutId() {
        return R.layout.first_layout;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (LanguageUtil.judgeLanguage().equals("zh")) {
            ivSplash.setImageResource(R.drawable.qidongye);
        } else if (LanguageUtil.judgeLanguage().equals("en")) {
            ivSplash.setImageResource(R.drawable.qidongye_en);
        }
        MyCount myCount = new MyCount(2000, 1000);
        myCount.start();
    }


    private class MyCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


        }

        @Override
        public void onFinish() {
            setHasAnimiation(false);
            if (UserManager.isLogin()) {// 如果登陆

                /**
                 * 登录人信息保存到baseapplication
                 */
                BaseApplication.userId = UserManager.getUserInfo().getUserId();
                BaseApplication.phone = UserManager.getUserInfo().getPhone();
                BaseApplication.secret = UserManager.getUserInfo().getSecret();


                startActivity(MainAty.class, null);
            } else {
                startActivity(LoginAty.class, null);
            }

            setHasAnimiation(false);
            finish();


        }
    }
}
