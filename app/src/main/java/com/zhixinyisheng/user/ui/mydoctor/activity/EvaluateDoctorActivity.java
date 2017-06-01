package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.EaseConstant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.doctor.EvaluateDoctorInfo;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.GlideUtil;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 评价医生
 */
public class EvaluateDoctorActivity extends BaseAty implements RadioGroup.OnCheckedChangeListener {
    public static final String EXTRA_ORDER_ID = "EXTRA_ORDER_ID";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_doctor)
    TextView tvDoctor;
    @Bind(R.id.tv_physician)
    TextView tvPhysician;
    @Bind(R.id.tv_organization)
    TextView tvOrganization;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.tv_be_good_at)
    TextView tvBeGoodAt;
    @Bind(R.id.tv_moeny)
    TextView tvMoeny;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_tag)
    TextView tvTag;
    @Bind(R.id.tb_one)
    RadioButton tbOne;
    @Bind(R.id.tb_two)
    RadioButton tbTwo;
    @Bind(R.id.tb_three)
    RadioButton tbThree;
    @Bind(R.id.rg)
    RadioGroup rg;
    //订单id
    private String payId, type = "1", toUserId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate_doctor;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        tvTitle.setText(R.string.pinglun);
        tbOne.setChecked(true);

        payId = getIntent().getStringExtra(DoctorChatActivity.EXTRA_ORDER_ID);
        toUserId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        rg.setOnCheckedChangeListener(this);
        loadEvaluateDoctorInfo();
    }

    /**
     * 评价医生查询接口
     */
    private void loadEvaluateDoctorInfo() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).evaluateDoctorSelect(phone, secret, payId),
                HttpIdentifier.EVALUATE_DOCTOR_INFO);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            //评价医生查询接口
            case HttpIdentifier.EVALUATE_DOCTOR_INFO:
                EvaluateDoctorInfo doctorInfo = JSON.parseObject(result, EvaluateDoctorInfo.class);
                loadDoctorInfoWithView(doctorInfo.getDb());
                break;
            case HttpIdentifier.EVALUATE_DOCTOR:
                this.finish();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        JsonResponse jsonResponse = JSON.parseObject(result, JsonResponse.class);
        showToast(jsonResponse.getRetMessage());
    }

    private void loadDoctorInfoWithView(EvaluateDoctorInfo.DbBean doctor) {
        if (doctor == null) {
            return;
        }
        tvBeGoodAt.setText(getString(R.string.shanchangjibing) + doctor.getBeGoodAt());
        tvDepartment.setText(doctor.getDepartment());
        tvDoctor.setText(doctor.getName());
        tvPhysician.setText(doctor.getPhysician());
        tvNum.setText(doctor.getNum() + getString(R.string.rengoumai));

        tvOrganization.setText(doctor.getOrganization());

        String day = doctor.getDayMoney();
        String week = doctor.getWeekMoney();
        String year = doctor.getYearMoney();
        String month = doctor.getMonthMoney();
        if (!"".equals(day)) {
            tvMoeny.setText("￥" + day + getString(R.string.qi));
        } else if ("".equals(day) && !"".equals(week)) {
            tvMoeny.setText("￥" + week + getString(R.string.qi));
        } else if("".equals(day) && "".equals(week) && !"".equals(month)){
            tvMoeny.setText("￥" + month + getString(R.string.qi));
        }else{
            tvMoeny.setText("￥" + year + getString(R.string.qi));
        }
        GlideUtil.loadCircleAvatar(this, doctor.getAvatar(), ivAvatar);
    }

    @OnClick({R.id.iv_title_left, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.btn_submit:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).evaluateDoctor
                        (phone, secret, userId, toUserId, payId, type), HttpIdentifier.EVALUATE_DOCTOR);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        //type*1非常满意 2满意 3不满意
        if (i == R.id.tb_one) {
            type = "1";
        } else if (i == R.id.tb_two) {
            type = "2";

        } else {
            type = "3";

        }
    }
}
