package com.zhixinyisheng.user.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.AppConfig;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.ResultInfo;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.http.RegisterLog;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.MainAty;
import com.zhixinyisheng.user.ui.MyApplication;
import com.zhixinyisheng.user.util.AppJsonUtil;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.MatchStingUtil;
import com.zhixinyisheng.user.util.TimeCount;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 注册界面
 * Created by gjj on 2016/10/17.
 */
public class RegisterAty extends BaseAty implements TextWatcher {
    @Bind(R.id.cjs_tvt)
    TextView cjs_tvt;//标题栏
    @Bind(R.id.iv_back)
    ImageView iv_back;//返回键
    @Bind(R.id.et_re_phone)
    EditText et_re_phone;//注册输入手机号
    @Bind(R.id.et_re_code)
    EditText et_re_code;//注册输入验证码
    @Bind(R.id.tv_re_getcode)
    TextView tv_re_getcode;//注册获取验证
    @Bind(R.id.et_re_password)
    EditText et_re_password;//注册输入密码
    @Bind(R.id.et_re_password_next)
    EditText et_re_password_next;//注册再次输入密码
    @Bind(R.id.btn_re_queren)
    Button btn_re_queren;//注册确认
    @Bind(R.id.xieyi)
    TextView xieyi;
    MyCount mMyCount;
    String imei;
    @Override
    public int getLayoutId() {
        return R.layout.register_aty;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjs_tvt.setText(getResources().getString(R.string.register));
        iv_back.setVisibility(View.VISIBLE);
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
//        if (LanguageUtil.judgeLanguage().equals("zh")) {
//            et_re_phone.setInputType(InputType.TYPE_CLASS_PHONE);
//        }
        xieyi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        et_re_phone.addTextChangedListener(this);
        et_re_code.addTextChangedListener(this);
        et_re_password.addTextChangedListener(this);
        et_re_password_next.addTextChangedListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyCount != null) {
            mMyCount.cancel();
        }
    }

    @OnClick({R.id.btn_re_queren, R.id.tv_re_getcode, R.id.cjs_rlb, R.id.xieyi})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.xieyi:
                startActivity(XieYiAty.class, null);
                break;
            case R.id.btn_re_queren:
                if (et_re_phone.getText().toString().equals("")) {
                    showToast(getResources().getString(R.string.AccountNotEmpty));
                    et_re_phone.requestFocus();
                    return;
                }
                if (LanguageUtil.judgeLanguage().equals("zh")) {
                    if (!MatchStingUtil.isMobile(et_re_phone.getText().toString().trim())) {
                        showToast(getResources().getString(R.string.AccountIncorrect));
                        et_re_phone.requestFocus();
                        return;
                    }
                } else {
                    if (!MatchStingUtil.isEmail(et_re_phone.getText().toString().trim())) {
                        showToast(getString(R.string.youxianggeshicuowu));
                        et_re_phone.requestFocus();
                        return;
                    }
                }

                if (et_re_code.getText().toString().trim().length() < 4) {
                    showToast(getResources().getString(R.string.IncorrectCodeLength));
                    et_re_code.requestFocus();
                    return;
                }
                if (et_re_code.getText().toString().equals("")) {
                    showToast(getResources().getString(R.string.VerificationEmpty));
                    et_re_code.requestFocus();
                    return;
                }
                if (et_re_password.getText().toString().trim().length() < 6) {
                    showToast(getResources().getString(R.string.PasswordLess));
                    et_re_code.requestFocus();
                    return;
                }
                if (!et_re_password.getText().toString().equals(et_re_password_next.getText().toString())) {
                    showToast(getResources().getString(R.string.TwoNotEqual));
                    et_re_code.requestFocus();
                    return;
                }
//                doHttp(RetrofitUtils.createApi(RegisterLog.class).yanzhengVerify(
//                        et_re_phone.getText().toString(), "zhixinyisheng",1,et_re_code.getText().toString() ), 1);


                showLoadingDialog(null);
                if (LanguageUtil.judgeLanguage().equals("zh")) {
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).register(
                            et_re_phone.getText().toString(),
                            "2", et_re_password.getText().toString(),
                            1,
                            et_re_code.getText().toString(),
                            LanguageUtil.judgeLanguage()), 3);
                } else {
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).registerE(
                            et_re_phone.getText().toString(),
                            "2", et_re_password.getText().toString(),
                            1,
                            et_re_code.getText().toString(),
                            LanguageUtil.judgeLanguage()), 3);
                }


                break;
            case R.id.tv_re_getcode:
                if (et_re_phone.getText().toString().equals("")) {
                    showToast(getResources().getString(R.string.AccountNotEmpty));
                    et_re_phone.requestFocus();
                    return;
                }
                if (LanguageUtil.judgeLanguage().equals("zh")) {
                    if (!MatchStingUtil.isMobile(et_re_phone.getText().toString().trim())) {
                        showToast(getResources().getString(R.string.AccountIncorrect));
                        et_re_phone.requestFocus();
                        return;
                    }
                } else {
                    if (!MatchStingUtil.isEmail(et_re_phone.getText().toString().trim())) {
                        showToast(getString(R.string.youxianggeshicuowu));
                        et_re_phone.requestFocus();
                        return;
                    }
                }
                showLoadingDialog(null);
                if (LanguageUtil.judgeLanguage().equals("zh")) {
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).phoneNull(et_re_phone.getText().toString()), 0);
                } else {
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).sendEmail(
                            et_re_phone.getText().toString(),
                            "zhixinyisheng",
                            1), HttpIdentifier.SEND_EMARL);
                }
                break;
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case HttpIdentifier.LOGIN:
                UserManager.setIsLogin(true);
                UserManager.setUserInfo(AppJsonUtil.getObject(result, UserInfo.class));
                if (UserManager.getUserInfo().getPhone().equals("")) {
                    UserInfo userInfo = UserManager.getUserInfo();
                    userInfo.setPhone(UserManager.getUserInfo().getEmail());
                    UserManager.setUserInfo(userInfo);
                }
                /**
                 * 登录人信息保存到baseapplication
                 */
                BaseApplication.userId = UserManager.getUserInfo().getUserId();
                BaseApplication.phone = UserManager.getUserInfo().getPhone();
                BaseApplication.secret = UserManager.getUserInfo().getSecret();
                //IM 登录
                register();

                Intent intent = new Intent(AppConfig.LOGIN_ACTION);
                sendBroadcast(intent);
                if (AppManger.getInstance().isAddActivity(MainAty.class)) {
                    setResult(RESULT_OK);
                    finish();
                } else {
//                    setHasAnimiation(false);
                    startActivity(MainAty.class, null);
                    finish();
                }
                break;
            case HttpIdentifier.SEND_EMARL:
                TimeCount timeCount = new TimeCount(60 * 1000, 1000, tv_re_getcode);// 实例化TimeCount类
                timeCount.start();
                break;
            case 0:
                Logger.e("isNull", result);
                break;
            case 1:
//                doHttp(RetrofitUtils.createApi(RegisterLog.class).register(et_re_phone.getText().toString(),"2",et_re_password.getText().toString()),3);
//                finish();
                break;
            case 2:
                showToast(getResources().getString(R.string.VerificationSuccessfully));
                break;
            case 3:
                /**
                 *
                 * {result": "0000",
                 "db": "82ce3bd175984373a1ba3668da16f619",
                 "returnMessage": "用户注册成功"}
                 *
                 *
                 * 环信注册
                 */

                JSONObject object = JSONObject.parseObject(result);
                register(object.getString("db"));
                showToast(getResources().getString(R.string.LoginSucceed));
                dohttpForLogin();

                finish();
                break;
        }

        super.onSuccess(result, call, response, what);
    }
    /**
     * IM 登录
     */
    public void login() {

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(UserManager.getUserInfo().getUserId());

        EMClient.getInstance().login(UserManager.getUserInfo().getUserId(), Constant.IM_PASSWORD, new EMCallBack() {

            @Override
            public void onSuccess() {

                showLog("IM 登录", "成功");

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        MyApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                /**
                 * 保存当钱登录人信息
                 */
                DemoHelper.getInstance().getUserProfileManager().setCurrentUserNick(UserManager.getUserInfo().getName());
                DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(UserManager.getUserInfo().getHeadUrl());


            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("IM", "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                showLog("IM 登录", getString(R.string.Login_failed) + message);
            }
        });
    }

    /**
     * IM 注册
     */
    public void register() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // call method in SDK
                    EMClient.getInstance().createAccount(UserManager.getUserInfo().getUserId(), Constant.IM_PASSWORD);
                    showLog("IM 注册", "成功");
                    login();

                } catch (final HyphenateException e) {
                    int errorCode = e.getErrorCode();
                    if (errorCode == EMError.NETWORK_ERROR) {
                        showLog("IM 注册", getResources().getString(R.string.network_anomalies));
                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                        showLog("IM 注册", getResources().getString(R.string.User_already_exists));
                        login();
                    } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
//                        showLog("IM 注册", getResources().getString(R.string.registration_failed_without_permission));
                    } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                        showLog("IM 注册", getResources().getString(R.string.illegal_user_name));
                    } else {
//                        showLog("IM 注册", getResources().getString(R.string.Registration_failed));
                    }
                }
            }
        }).start();

    }

    /**
     * 注册成功后调登录接口
     * */
    private void dohttpForLogin() {
        if (null == MyApplication.resultId || "".equals(MyApplication.resultId)) {//是空的话，随便赋一个值
            MyApplication.resultId = "1";
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(RegisterLog.class).Login(
                et_re_phone.getText().toString().trim(), et_re_phone.getText().toString().trim(),
                et_re_password.getText().toString().trim(), "android", imei, MyApplication.resultId, LanguageUtil.judgeLanguage()), HttpIdentifier.LOGIN);

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case HttpIdentifier.SEND_EMARL:
                JSONObject object1 = JSONObject.parseObject(result);
                if (object1.getString("result").equals("1010")) {
                    showToast(getResources().getString(R.string.UserExists));
                }
                break;
            case 0:
                JSONObject object = JSONObject.parseObject(result);
                if (object.getString("result").equals("1010")) {
                    showToast(getResources().getString(R.string.UserExists));
                } else if (object.getString("result").equals("1011")) {
                    TimeCount timeCount = new TimeCount(60 * 1000, 1000, tv_re_getcode);// 实例化TimeCount类
                    timeCount.start();
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).sendVerify(et_re_phone.getText().toString(), "zhixinyisheng", 1), 2);
                }

                break;
            case 1:
                ResultInfo resultInfo = AppJsonUtil.getResultInfo(result);
                showToast(resultInfo.getReturnMessage());
                break;
            case 2:
                showToast(getResources().getString(R.string.AuthenticationFailed));
                break;
            case 3:
                JSONObject jbo = JSONObject.parseObject(result);
                if (jbo.getString("result").equals("1043")) {
                    showToast(getResources().getString(R.string.SentOutToday));
                } else if (jbo.getString("result").equals("1044")) {
                    showToast(getResources().getString(R.string.VerificationExpired));
                } else if (jbo.getString("result").equals("1045")) {
                    showToast(getResources().getString(R.string.VerificationError));
                }
                break;

        }
        super.onFailure(result, call, response, what);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (judgeBright()) {
            btn_re_queren.setBackgroundResource(R.drawable.shape_main_color);
        }else{
            btn_re_queren.setBackgroundResource(R.drawable.shape_gray_color);
        }
    }

    /**
     * 判断按钮是否亮
     */
    private boolean judgeBright() {
        if ((MatchStingUtil.isMobile(et_re_phone.getText().toString())
                || MatchStingUtil.isEmail(et_re_phone.getText().toString()))
                && !TextUtils.isEmpty(et_re_code.getText().toString())
                && !TextUtils.isEmpty(et_re_password.getText().toString())
                && !TextUtils.isEmpty(et_re_password_next.getText().toString())
                && (et_re_password.getText().toString().equals(et_re_password_next.getText().toString()))) {
            return true;
        } else {
            return false;
        }
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
            if (tv_re_getcode != null) {
                tv_re_getcode.setEnabled(false);
//                tv_obtion_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gray_round_bg));
                tv_re_getcode.setText(millisUntilFinished / 1000 + getResources().getString(R.string.second));
            }

        }

        @Override
        public void onFinish() {
            if (tv_re_getcode != null) {
                tv_re_getcode.setEnabled(true);
//                tv_obtion_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_orange_round_bg));
                tv_re_getcode.setText(getResources().getString(R.string.GetCode));
            }

        }
    }


    /**
     * IM 注册
     */
    public void register(final String userID) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // call method in SDK
                    EMClient.getInstance().createAccount(userID, Constant.IM_PASSWORD);
                    showLog("IM 注册", "成功");

                } catch (final HyphenateException e) {
                    int errorCode = e.getErrorCode();
                    if (errorCode == EMError.NETWORK_ERROR) {
                        showLog("IM 注册", getResources().getString(R.string.network_anomalies));
                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                        showLog("IM 注册", getResources().getString(R.string.User_already_exists));

                    } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
//                        showLog("IM 注册", getResources().getString(R.string.registration_failed_without_permission));
                    } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                        showLog("IM 注册", getResources().getString(R.string.illegal_user_name));
                    } else {
//                        showLog("IM 注册", getResources().getString(R.string.Registration_failed));
                    }
                }
            }
        }).start();

    }


}
