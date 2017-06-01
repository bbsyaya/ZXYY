package com.zhixinyisheng.user.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.http.RegisterLog;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.MatchStingUtil;
import com.zhixinyisheng.user.util.TimeCount;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Administrator on 2016/10/22.
 */
public class ForgetPassword extends BaseAty implements TextWatcher{
    @Bind(R.id.cjs_tvt)
    TextView cjs_tvt;//标题栏
    @Bind(R.id.iv_back)
    ImageView iv_back;//返回键
    @Bind(R.id.et_phone)
    EditText et_phone;//手机号
    @Bind(R.id.et_code)
    EditText et_code;//输入验证码
    @Bind(R.id.tv_getcode)
    TextView tv_getcode;//获得验证码
    @Bind(R.id.et_password)
    EditText et_password;//输入密码
    @Bind(R.id.et_password_next)
    EditText et_password_next;//再次输入密码
    @Bind(R.id.btn_queren)
    Button btn_queren;
    MyCount mMyCount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        iv_back.setVisibility(View.VISIBLE);
        cjs_tvt.setText(getResources().getString(R.string.wangjimima));
//        if (LanguageUtil.judgeLanguage().equals("zh")){
//            et_phone.setInputType(InputType.TYPE_CLASS_PHONE);
//        }
        et_phone.addTextChangedListener(this);
        et_code.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
        et_password_next.addTextChangedListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyCount != null) {
            mMyCount.cancel();
        }
    }


    @OnClick({R.id.cjs_rlb, R.id.tv_getcode, R.id.btn_queren})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
            case R.id.tv_getcode:
                if (et_phone.getText().toString().equals("")) {
                    showToast(getResources().getString(R.string.AccountNotEmpty));
                    et_phone.requestFocus();
                    return;
                }
                if (!MatchStingUtil.isMobile(et_phone.getText().toString().trim())
                        && !MatchStingUtil.isEmail(et_phone.getText().toString().trim())) {
                    showToast(getResources().getString(R.string.AccountIncorrect));
                    et_phone.requestFocus();
                    return;
                }
                showLoadingDialog(null);
                if (MatchStingUtil.isMobile(et_phone.getText().toString().trim())) {
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).phoneNull(et_phone.getText().toString()), 0);
                } else {
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).sendEmail(
                            et_phone.getText().toString(),
                            "zhixinyisheng",
                            2), HttpIdentifier.SEND_EMARL);
                }

                break;
            case R.id.btn_queren:
                if (et_phone.getText().toString().equals("")) {
                    showToast(getResources().getString(R.string.AccountNotEmpty));
                    et_phone.requestFocus();
                    return;
                }
                if (!MatchStingUtil.isMobile(et_phone.getText().toString().trim())
                        && !MatchStingUtil.isEmail(et_phone.getText().toString().trim())) {
                    showToast(getResources().getString(R.string.AccountIncorrect));
                    et_phone.requestFocus();
                    return;
                }
                if (et_code.getText().toString().trim().length() < 4) {
                    showToast(getResources().getString(R.string.IncorrectCodeLength));
                    et_code.requestFocus();
                    return;
                }
                if (et_password.getText().toString().trim().length() < 6) {
                    showToast(getResources().getString(R.string.PasswordLess));
                    et_password.requestFocus();
                    return;
                }
                if (et_password_next.getText().toString().trim().length() < 6) {
                    showToast(getResources().getString(R.string.PasswordLess));
                    et_password_next.requestFocus();
                    return;
                }
                if (!et_password_next.getText().toString().equals(et_password.getText().toString())) {
                    showToast(getResources().getString(R.string.TwoNotEqual));
                    et_password.requestFocus();
                    return;
                }
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(RegisterLog.class).resetPassword(
                        et_phone.getText().toString(),
                        et_phone.getText().toString(),
                        et_password.getText().toString(),
                        2,
                        et_code.getText().toString(),
                        LanguageUtil.judgeLanguage()), 3);

                break;
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
        if (judgeBright()) {
            btn_queren.setBackgroundResource(R.drawable.shape_main_color);
        }else{
            btn_queren.setBackgroundResource(R.drawable.shape_gray_color);
        }
    }
    /**
     * 判断按钮是否亮
     */
    private boolean judgeBright() {
        if ((MatchStingUtil.isMobile(et_phone.getText().toString())
                || MatchStingUtil.isEmail(et_phone.getText().toString()))
                && !TextUtils.isEmpty(et_code.getText().toString())
                && !TextUtils.isEmpty(et_password.getText().toString())
                && !TextUtils.isEmpty(et_password_next.getText().toString())
                && (et_password.getText().toString().equals(et_password_next.getText().toString()))) {
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
            if (tv_getcode != null) {
                tv_getcode.setEnabled(false);
                tv_getcode.setText(millisUntilFinished / 1000 + getResources().getString(R.string.second));
            }

        }

        @Override
        public void onFinish() {
            if (tv_getcode != null) {
                tv_getcode.setEnabled(true);
                tv_getcode.setText(getResources().getString(R.string.GetCode));
            }

        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what) {
            case HttpIdentifier.SEND_EMARL:
                JSONObject object1 = JSONObject.parseObject(result);
                if (object1.getString("result").equals("1010")) {
                    showToast(getResources().getString(R.string.UserExists));
                }else if (object1.getString("result").equals("1011")) {
                    showToast(getResources().getString(R.string.UserNotRegisted));
                }
                break;
            case 0:
                JSONObject object = JSONObject.parseObject(result);
                if (object.getString("result").equals("1010")) {
                    TimeCount timeCount = new TimeCount(60 * 1000, 1000, tv_getcode);// 实例化TimeCount类
                    timeCount.start();
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(RegisterLog.class).sendVerify(et_phone.getText().toString(), "zhixinyisheng", 2), 1);
                } else if (object.getString("result").equals("1011")) {
                    showToast(getResources().getString(R.string.UserNotRegisted));
                }
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
            default:
                showToast(getResources().getString(R.string.SystemAnomaly));
                break;
        }

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case HttpIdentifier.SEND_EMARL:
                TimeCount timeCount = new TimeCount(60 * 1000, 1000, tv_getcode);// 实例化TimeCount类
                timeCount.start();
                showToast(getResources().getString(R.string.VerificationSuccessfully));
                break;
            case 1:
//                if (mMyCount == null) {
//                    mMyCount = new MyCount(60000, 1000);
//                }
//                mMyCount.start();
                showToast(getResources().getString(R.string.VerificationSuccessfully));
                break;
            case 2:
//                showLoadingDialog(null);
//                doHttp(RetrofitUtils.createApi(RegisterLog.class).resetPassword(et_phone.getText().toString(), et_password.getText().toString()), 3);
//                finish();
                break;
            case 3:
                showToast(getResources().getString(R.string.ModifySuccess));
                finish();
                break;
        }
        super.onSuccess(result, call, response, what);
    }


}