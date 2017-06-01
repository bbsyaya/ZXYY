package com.zhixinyisheng.user.ui.mydoctor.activity;


import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.sIsSaveInfo;

/**
 * 医生荣誉 和 医生简介
 */
public class EditDoctorInfoActivity extends BaseAty {

    public static final String EXTRA_HONOR_INTRODUCE = "honor_interoduce";
    public static final String EXTRA_HONOR_INTRODUCE_TEXT = "honor_interoduce_text";
    public static final String EXTRA_DOCTOR_ID = "doctorId";
    public static final String EXTRA_DOCTOR_NAME = "doctorname";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    // 0简介  1荣誉
    private String mTag, mContent, mDoctorId, mDoctorName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_doctor_info;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        userId = UserManager.getUserInfo().getUserId();
        mTag = getIntent().getStringExtra(EXTRA_HONOR_INTRODUCE);
        mContent = getIntent().getStringExtra(EXTRA_HONOR_INTRODUCE_TEXT);
        mDoctorId = getIntent().getStringExtra(EXTRA_DOCTOR_ID);
        mDoctorName = getIntent().getStringExtra(EXTRA_DOCTOR_NAME);
        if (userId.equals(mDoctorId)) {
            setViewState(true);
        } else {
            setViewState(false);
        }
        if (mContent != null && !"".equals(mContent)) {
            etContent.setText(mContent);
        } else {
            etContent.setHint(R.string.wu);

        }

        etContent.setSelection(mContent.length());
        if ("1".equals(mTag)) {
            tvTitle.setText(mDoctorName + getString(R.string.daifuderongyu));
        } else if ("0".equals(mTag)) {
            tvTitle.setText(mDoctorName + getString(R.string.daifudejianjie));
        }

    }

    /**
     * 设置view的状态
     */
    private void setViewState(boolean viewState) {
        btnSubmit.setEnabled(viewState);
        //光标
        etContent.setCursorVisible(viewState);
        //不可编辑效果
        etContent.setFocusableInTouchMode(viewState);
        etContent.setFocusable(viewState);
        if (viewState) {
            etContent.requestFocus();
        }
        if (!viewState) {
            btnSubmit.setVisibility(View.GONE);
        }else{
            showSoftInputFromWindow(this,etContent);
        }
    }
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(EditDoctorInfoActivity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        JsonResponse jsonResponse = JSON.parseObject(result, JsonResponse.class);
        switch (what) {
            case HttpIdentifier.INTRODUCE_DOCTOR:
                break;
            case HttpIdentifier.HONOR_DOCTOR:
                break;
        }
        showToast(jsonResponse.getRetMessage());
        sIsSaveInfo = true;
        this.finish();
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        JsonResponse jsonResponse = JSON.parseObject(result, JsonResponse.class);
        showToast(jsonResponse.getRetMessage());
    }

    @OnClick({R.id.iv_title_left, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                hideSoftKeyboard();
                break;
            case R.id.btn_submit:

                String content = etContent.getText().toString();
                if("".equals(content)){
                    showToast(getString(R.string.qingshuruneirong));
                    return;
                }
                showLoadingDialog(null);
                if ("1".equals(mTag)) {
                    doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).honorApi(phone, secret, userId, content), HttpIdentifier.HONOR_DOCTOR);
                } else if ("0".equals(mTag)) {
                    doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).introduceApi(phone, secret, userId, content), HttpIdentifier.INTRODUCE_DOCTOR);
                }
                break;
        }
    }
}
