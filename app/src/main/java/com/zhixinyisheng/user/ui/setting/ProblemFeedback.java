package com.zhixinyisheng.user.ui.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.LeftUrl;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 问题反馈
 * Created by gjj on 2016/10/26.
 */
public class ProblemFeedback extends BaseAty implements TextWatcher {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.cjs_tvt)
    TextView cjs_tvt;
    @Bind(R.id.et_problem)
    EditText et_problem;
    @Bind(R.id.btn_problem_tijiao)
    Button btnCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_problemfeedback;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        iv_back.setVisibility(View.VISIBLE);
        cjs_tvt.setText(R.string.fankuizhongxin);
        et_problem.addTextChangedListener(this);
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.cjs_rlb, R.id.btn_problem_tijiao})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
            case R.id.btn_problem_tijiao:
                if (et_problem.getText().toString().equals("")) {
                    showToast(getString(R.string.qingshuruyijian));
                } else {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(LeftUrl.class).feedback(phone, secret, userId, et_problem.getText().toString()), 1);
                }
                break;
        }


    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        showToast(getString(R.string.ganxienindefankui));
        finish();
        super.onSuccess(result, call, response, what);

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        showToast(getString(R.string.qingshuruyijian));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().trim().equals("")) {
            btnCommit.setBackgroundResource(R.drawable.shape_main_color);
        } else {
            btnCommit.setBackgroundResource(R.drawable.shape_gray_color);
        }
    }
}
