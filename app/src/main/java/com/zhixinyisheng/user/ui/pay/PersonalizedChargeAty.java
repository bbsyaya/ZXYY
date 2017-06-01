package com.zhixinyisheng.user.ui.pay;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.pay.PersonlizedCharge;
import com.zhixinyisheng.user.http.Pay;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Colors;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 个性化收费
 * Created by 焕焕 on 2017/1/10.
 */
public class PersonalizedChargeAty extends BaseAty implements TextWatcher {
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.main_unread_msg_number)
    TextView mainUnreadMsgNumber;
    @Bind(R.id.cjs_rl_xhd)
    RelativeLayout cjsRlXhd;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.title_btn_hosptal)
    Button titleBtnHosptal;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title_line)
    View titleLine;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.et_perday)
    EditText etPerday;
    @Bind(R.id.et_perweek)
    EditText etPerweek;
    @Bind(R.id.et_permonth)
    EditText etPermonth;
    @Bind(R.id.et_peryear)
    EditText etPeryear;
    @Bind(R.id.tv_freetips)
    TextView tvFreetips;
    @Bind(R.id.tv_10tips)
    TextView tv10tips;
    @Bind(R.id.rl_10tips)
    RelativeLayout rl10tips;
    @Bind(R.id.tv_20tips)
    TextView tv20tips;
    @Bind(R.id.rl_20tips)
    RelativeLayout rl20tips;
    @Bind(R.id.tv_50tips)
    TextView tv50tips;
    @Bind(R.id.rl_50tips)
    RelativeLayout rl50tips;
    @Bind(R.id.tv_100tips)
    TextView tv100tips;
    @Bind(R.id.rl_100tips)
    RelativeLayout rl100tips;
    @Bind(R.id.tv_200tips)
    TextView tv200tips;
    @Bind(R.id.rl_200tips)
    RelativeLayout rl200tips;
    @Bind(R.id.tv_500tips)
    TextView tv500tips;
    @Bind(R.id.rl_500tips)
    RelativeLayout rl500tips;
    boolean flag10tips, flag20tips, flag50tips, flag100tips, flag200tips, flag500tips;

    @Override
    public int getLayoutId() {
        return R.layout.aty_personalizedcharge;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("个性化收费");
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText("保存");
        etPerday.addTextChangedListener(this);
        etPermonth.addTextChangedListener(this);
        etPerweek.addTextChangedListener(this);
        etPeryear.addTextChangedListener(this);
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(Pay.class).searchMoneyRule(userId, phone, secret), HttpIdentifier.SEARCH_PERSONALIZED_CHARGE_DOCTOR);
    }

    /**
     * 初始化颜色的状态
     */
    private void defaultColorState() {
        flag10tips = false;
        flag20tips = false;
        flag50tips = false;
        flag100tips = false;
        flag200tips = false;
        flag500tips = false;
        rl10tips.setBackgroundResource(R.drawable.btn_white_shape);
        rl20tips.setBackgroundResource(R.drawable.btn_white_shape);
        rl50tips.setBackgroundResource(R.drawable.btn_white_shape);
        rl100tips.setBackgroundResource(R.drawable.btn_white_shape);
        rl200tips.setBackgroundResource(R.drawable.btn_white_shape);
        rl500tips.setBackgroundResource(R.drawable.btn_white_shape);
        tv10tips.setTextColor(Colors.textColor);
        tv20tips.setTextColor(Colors.textColor);
        tv50tips.setTextColor(Colors.textColor);
        tv100tips.setTextColor(Colors.textColor);
        tv200tips.setTextColor(Colors.textColor);
        tv500tips.setTextColor(Colors.textColor);
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what){
            case HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR:
                showToast("请输入正确格式金额！");
                finish();
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR:
                showToast("保存成功");
                finish();
                break;
            case HttpIdentifier.SEARCH_PERSONALIZED_CHARGE_DOCTOR:
                PersonlizedCharge personlizedCharge = JSON.parseObject(result, PersonlizedCharge.class);
                etPerday.setText(personlizedCharge.getDb().getDayMoney());
                etPerweek.setText(personlizedCharge.getDb().getWeekMoney());
                etPermonth.setText(personlizedCharge.getDb().getMonthMoney());
                etPeryear.setText(personlizedCharge.getDb().getYearMoney());
                tvFreetips.setText(personlizedCharge.getDb().getMessageCount() + "");
                if (personlizedCharge.getDb().getMessageCount() == 10) {
                    defaultColorState();
                    flag10tips = true;
                    rl10tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tv10tips.setTextColor(Colors.mainColor);
                } else if (personlizedCharge.getDb().getMessageCount() == 20) {
                    defaultColorState();
                    flag20tips = true;
                    rl20tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tv20tips.setTextColor(Colors.mainColor);
                } else if (personlizedCharge.getDb().getMessageCount() == 50) {
                    defaultColorState();
                    flag50tips = true;
                    rl50tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tv50tips.setTextColor(Colors.mainColor);
                } else if (personlizedCharge.getDb().getMessageCount() == 100) {
                    defaultColorState();
                    flag100tips = true;
                    rl100tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tv100tips.setTextColor(Colors.mainColor);
                } else if (personlizedCharge.getDb().getMessageCount() == 200) {
                    defaultColorState();
                    flag200tips = true;
                    rl200tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tv200tips.setTextColor(Colors.mainColor);
                } else if (personlizedCharge.getDb().getMessageCount() == 500) {
                    defaultColorState();
                    flag500tips = true;
                    rl500tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tv500tips.setTextColor(Colors.mainColor);
                }
                break;
        }
    }

    @OnClick({R.id.cjs_rlb, R.id.title_btn, R.id.rl_10tips, R.id.rl_20tips, R.id.rl_50tips, R.id.rl_100tips, R.id.rl_200tips, R.id.rl_500tips})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
            case R.id.title_btn:
                if (etPerday.getText().toString().equals("")
                        && etPerweek.getText().toString().equals("")
                        && etPermonth.getText().toString().equals("")
                        && etPeryear.getText().toString().equals("")) {
                    showToast("请输入信息~！");
                } else {
                    showLoadingDialog(null);
                    if (flag10tips) {
                        doHttp(RetrofitUtils.createApi(Pay.class).doctorRule(phone, secret, userId,
                                etPerday.getText().toString(),
                                etPerweek.getText().toString(),
                                etPermonth.getText().toString(),
                                etPeryear.getText().toString(), "10"), HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR);
                    } else if (flag20tips) {
                        doHttp(RetrofitUtils.createApi(Pay.class).doctorRule(phone, secret, userId,
                                etPerday.getText().toString(),
                                etPerweek.getText().toString(),
                                etPermonth.getText().toString(),
                                etPeryear.getText().toString(), "20"), HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR);
                    } else if (flag50tips) {
                        doHttp(RetrofitUtils.createApi(Pay.class).doctorRule(phone, secret, userId,
                                etPerday.getText().toString(),
                                etPerweek.getText().toString(),
                                etPermonth.getText().toString(),
                                etPeryear.getText().toString(), "50"), HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR);
                    } else if (flag100tips) {
                        doHttp(RetrofitUtils.createApi(Pay.class).doctorRule(phone, secret, userId,
                                etPerday.getText().toString(),
                                etPerweek.getText().toString(),
                                etPermonth.getText().toString(),
                                etPeryear.getText().toString(), "100"), HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR);
                    } else if (flag200tips) {
                        doHttp(RetrofitUtils.createApi(Pay.class).doctorRule(phone, secret, userId,
                                etPerday.getText().toString(),
                                etPerweek.getText().toString(),
                                etPermonth.getText().toString(),
                                etPeryear.getText().toString(), "200"), HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR);
                    } else if (flag500tips) {
                        doHttp(RetrofitUtils.createApi(Pay.class).doctorRule(phone, secret, userId,
                                etPerday.getText().toString(),
                                etPerweek.getText().toString(),
                                etPermonth.getText().toString(),
                                etPeryear.getText().toString(), "500"), HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR);
                    } else {
                        doHttp(RetrofitUtils.createApi(Pay.class).doctorRule(phone, secret, userId,
                                etPerday.getText().toString(),
                                etPerweek.getText().toString(),
                                etPermonth.getText().toString(),
                                etPeryear.getText().toString(), "20"), HttpIdentifier.PERSONALIZED_CHARGE_DOCTOR);
                    }

                }
                break;
            case R.id.rl_10tips:
                defaultColorState();
                flag10tips = true;
                rl10tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                tv10tips.setTextColor(Colors.mainColor);
                tvFreetips.setText("10");
                break;
            case R.id.rl_20tips:
                defaultColorState();
                flag20tips = true;
                rl20tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                tv20tips.setTextColor(Colors.mainColor);
                tvFreetips.setText("20");
                break;
            case R.id.rl_50tips:
                defaultColorState();
                flag50tips = true;
                rl50tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                tv50tips.setTextColor(Colors.mainColor);
                tvFreetips.setText("50");
                break;
            case R.id.rl_100tips:
                defaultColorState();
                flag100tips = true;
                rl100tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                tv100tips.setTextColor(Colors.mainColor);
                tvFreetips.setText("100");
                break;
            case R.id.rl_200tips:
                defaultColorState();
                flag200tips = true;
                rl200tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                tv200tips.setTextColor(Colors.mainColor);
                tvFreetips.setText("200");
                break;
            case R.id.rl_500tips:
                defaultColorState();
                flag500tips = true;
                rl500tips.setBackgroundResource(R.drawable.btn_maincolor_shape);
                tv500tips.setTextColor(Colors.mainColor);
                tvFreetips.setText("500");
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
    public void afterTextChanged(Editable edt) {
        String temp = edt.toString();
        int posDot = temp.indexOf(".");
        if (posDot <= 0) return;
        if (temp.length() - posDot - 1 > 2) {
            edt.delete(posDot + 3, posDot + 4);
        }
    }
}
