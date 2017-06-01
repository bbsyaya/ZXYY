package com.zhixinyisheng.user.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 登录的测试类public
 * Created by jujing on 2016/10/13.
 */
public class LoginAty extends BaseAty implements TextWatcher {
    @Bind(R.id.et_acount)
    EditText et_acount;//账号
    @Bind(R.id.et_pwd)
    EditText et_pwd;//密码
    String imei;
    @Bind(R.id.rl_phonenum)
    RelativeLayout rlPhonenum;
    @Bind(R.id.rl_pswlook)
    RelativeLayout rlPswlook;
    @Bind(R.id.iv_eyes)
    ImageView iv_eyes;
    @Bind(R.id.btn_commit)
    Button btn_commit;
    boolean flagLook = false;

    @Override
    public int getLayoutId() {
        return R.layout.aty_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        iv_eyes.setImageResource(R.mipmap.login_btn_by);
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
//        resultId = JPushInterface.getRegistrationID(this);
//        Logger.e("resultId",resultId);
        et_acount.addTextChangedListener(this);
        et_pwd.addTextChangedListener(this);
        et_pwd.setTypeface(Typeface.DEFAULT);
        et_pwd.setTransformationMethod(new PasswordTransformationMethod());
    }


    @OnClick({R.id.btn_commit, R.id.btn_register, R.id.btn_forgetpassword, R.id.rl_phonenum, R.id.rl_pswlook})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.btn_commit:
                if (et_acount.getText().toString().equals("")) {
                    showToast(getResources().getString(R.string.AccountNotEmpty));
                    et_acount.requestFocus();
                    return;
                }
                if (!MatchStingUtil.isMobile(et_acount.getText().toString().trim())
                        && !MatchStingUtil.isEmail(et_acount.getText().toString().trim())) {
                    showToast(getResources().getString(R.string.AccountIncorrect));
                    et_acount.requestFocus();
                    return;
                }

                if (et_pwd.getText().toString().equals("")) {
                    showToast(getResources().getString(R.string.PasswordNotEmpty));
                    et_pwd.requestFocus();
                    return;
                }
//                Logger.e("登录传的参数",et_acount.getText().toString().trim()+"$$$"+et_pwd.getText().toString().trim()+"$$$"+imei+"$$$"+MyApplication.resultId);

                if (null == MyApplication.resultId || "".equals(MyApplication.resultId)) {//是空的话，随便赋一个值
                    MyApplication.resultId = "1";
                }
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(RegisterLog.class).Login(
                        et_acount.getText().toString().trim(), et_acount.getText().toString().trim(),
                        et_pwd.getText().toString().trim(), "android", imei, MyApplication.resultId, LanguageUtil.judgeLanguage()), 1);


                break;
            case R.id.btn_register:
                startActivity(RegisterAty.class, null);
                break;
            case R.id.btn_forgetpassword:
                startActivity(ForgetPassword.class, null);
                break;
            case R.id.rl_phonenum:
                et_acount.setText("");
                break;
            case R.id.rl_pswlook:
                if (!flagLook) {
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eyes.setImageResource(R.mipmap.login_btn_zy);
                    flagLook = true;
                } else {
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eyes.setImageResource(R.mipmap.login_btn_by);
                    flagLook = false;
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        Logger.e("登录成功返回", result);

        switch (what) {
            case 1:
                UserManager.setIsLogin(true);
                UserManager.setUserInfo(AppJsonUtil.getObject(result, UserInfo.class));
                if (UserManager.getUserInfo().getPhone().equals("")) {
                    UserInfo userInfo = UserManager.getUserInfo();
                    userInfo.setPhone(UserManager.getUserInfo().getEmail());
                    UserManager.setUserInfo(userInfo);
                }
                if (UserManager.getUserInfo().getCity().equals("")||null==UserManager.getUserInfo().getCity()){
                    UserInfo userInfo = UserManager.getUserInfo();
                    userInfo.setCity("石家庄市");
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
        }
        super.onSuccess(result, call, response, what);

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        ResultInfo resultInfo = AppJsonUtil.getResultInfo(result);
        showToast(resultInfo.getReturnMessage());
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


    private long oldTime;

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        long newtime = System.currentTimeMillis();
        if (newtime - oldTime > 3000) {
            oldTime = newtime;
            showToast(getResources().getString(R.string.ExitAgain));
        } else {
            AppManger.getInstance().killAllActivity();
//            setHasAnimiation(false);
//            AppManger.getInstance().AppExit(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if ((MatchStingUtil.isEmail(et_acount.getText().toString())
                || MatchStingUtil.isMobile(et_acount.getText().toString()))
                && !TextUtils.isEmpty(et_pwd.getText().toString())) {
            btn_commit.setBackgroundResource(R.drawable.shape_main_color);
        }else{
            btn_commit.setBackgroundResource(R.drawable.shape_gray_color);
        }
    }
}
