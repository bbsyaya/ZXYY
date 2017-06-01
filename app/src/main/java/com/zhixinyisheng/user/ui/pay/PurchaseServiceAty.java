package com.zhixinyisheng.user.ui.pay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.and.yzy.frame.application.EaseConstant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.doctor.Doctor;
import com.zhixinyisheng.user.domain.pay.PersonlizedCharge;
import com.zhixinyisheng.user.http.Pay;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.GlideUtil;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 购买服务
 * Created by 焕焕 on 2017/1/11.
 */
public class PurchaseServiceAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.psitem_tv_name)
    TextView psitemTvName;
    @Bind(R.id.psitem_iv_job)
    TextView psitemIvJob;
    @Bind(R.id.psitem_tv_hospital)
    TextView psitemTvHospital;
    @Bind(R.id.psitem_tv_section)
    TextView psitemTvSection;
    @Bind(R.id.cb_day)
    CheckBox cbDay;
    @Bind(R.id.cb_week)
    CheckBox cbWeek;
    @Bind(R.id.cb_month)
    CheckBox cbMonth;
    @Bind(R.id.cb_year)
    CheckBox cbYear;
    @Bind(R.id.iv_reduce)
    ImageView ivReduce;
    @Bind(R.id.tv_purchase_num)
    TextView tvPurchaseNum;
    @Bind(R.id.rl_day)
    RelativeLayout rlDay;
    @Bind(R.id.rl_week)
    RelativeLayout rlWeek;
    @Bind(R.id.rl_month)
    RelativeLayout rlMonth;
    @Bind(R.id.rl_year)
    RelativeLayout rlYear;
    @Bind(R.id.psitem_tv_disease)
    TextView psitemTvDisease;
    @Bind(R.id.tv_charge_people)
    TextView tvChargePeople;
    @Bind(R.id.tv_perday)
    TextView tvPerday;
    @Bind(R.id.tv_perweek)
    TextView tvPerweek;
    @Bind(R.id.tv_permonth)
    TextView tvPermonth;
    @Bind(R.id.tv_peryear)
    TextView tvPeryear;
    @Bind(R.id.iv_doctor_avatar)
    ImageView ivDoctorAvatar;
    private DoctorHomePage.UserPdBean doctorInfo;
    private Doctor.ListBean doctorInfoFromPay;
    private int purchaseNum = 1;//购买的数量
    private String total_amount;//支付金额，元
    private String type;//支付类型
    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.e("resultStatus 118", resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PurchaseServiceAty.this, R.string.zhifuchenggong, Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        Logger.e("doctor info 0115",doctorInfo.getUserId());
                        if (doctorInfo!=null){
                            bundle.putString("payingDoctorId",doctorInfo.getUserId());
                        }else{
                            bundle.putString("payingDoctorId",doctorInfoFromPay.getId());
                        }
                        startActivity(PaySuccessAty.class, bundle);
                        finish();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PurchaseServiceAty.this, R.string.zhifushibai, Toast.LENGTH_SHORT).show();
                        startActivity(PayFailureAty.class, null);

                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };

    @Override
    public int getLayoutId() {
        return R.layout.aty_purchaseservice;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.goumaifuwu);
        ivBack.setVisibility(View.VISIBLE);
        if (tvPurchaseNum.getText().toString().equals("1")) {
            ivReduce.setImageResource(R.mipmap.gmfw_btn_reduce_dis);
            ivReduce.setClickable(false);
        } else {
            ivReduce.setImageResource(R.mipmap.gmfw_btn_agg_def);
            ivReduce.setClickable(true);
        }
        try {
            doctorInfo = (DoctorHomePage.UserPdBean) getIntent().getSerializableExtra(EaseConstant.EXTRA_DOCTOR_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            doctorInfo = null;
        }
        try {
            doctorInfoFromPay = (Doctor.ListBean) getIntent().getSerializableExtra(EaseConstant.EXTRA_DOCTOR_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            doctorInfoFromPay = null;
        }
        if (null != doctorInfo) {
            Logger.e("doctoere ", doctorInfo.getUserId());
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(Pay.class).searchMoneyRule(doctorInfo.getUserId(), phone, secret), HttpIdentifier.SEARCH_PERSONALIZED_CHARGE_DOCTOR);
            setUIData();
        } else {
            Logger.e("doctoere 222222", doctorInfoFromPay.getId());
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(Pay.class).searchMoneyRule(doctorInfoFromPay.getId(), phone, secret), HttpIdentifier.SEARCH_PERSONALIZED_CHARGE_DOCTOR);
            setUIDataFromPay();
        }


    }

    private void setUIDataFromPay() {
        tvChargePeople.setText( doctorInfoFromPay.getNum()+ getString(R.string.renyigoumai));
        psitemTvName.setText(doctorInfoFromPay.getName() + getString(R.string.tdaifu));
        psitemIvJob.setText(doctorInfoFromPay.getPhysician());
        psitemTvHospital.setText(doctorInfoFromPay.getOrganization());
        psitemTvSection.setText(doctorInfoFromPay.getDepartment());
        psitemTvDisease.setText(doctorInfoFromPay.getBeGoodAt());
        GlideUtil.loadCircleAvatar(this, doctorInfoFromPay.getAvatar(), ivDoctorAvatar);
    }

    /**
     * 设置数据
     */
    private void setUIData() {
//        Logger.e("psa num", getIntent().getStringExtra(EaseConstant.EXTRA_BUY_NUM));
        tvChargePeople.setText(doctorInfo.getNum() + getString(R.string.renyigoumai));
        psitemTvName.setText(doctorInfo.getName() + getString(R.string.tdaifu));
        psitemIvJob.setText(doctorInfo.getJob());
        psitemTvHospital.setText(doctorInfo.getHospital());
        psitemTvSection.setText(doctorInfo.getSection());
        psitemTvDisease.setText(doctorInfo.getDisease());
        GlideUtil.loadCircleAvatar(this, doctorInfo.getHeadUrl(), ivDoctorAvatar);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.SEARCH_PERSONALIZED_CHARGE_DOCTOR:
                PersonlizedCharge personlizedCharge = JSON.parseObject(result, PersonlizedCharge.class);
                if (personlizedCharge.getDb().getDayMoney().equals("")) {
                    rlDay.setVisibility(View.GONE);
                }
                if (personlizedCharge.getDb().getWeekMoney().equals("")) {
                    rlWeek.setVisibility(View.GONE);
                }
                if (personlizedCharge.getDb().getMonthMoney().equals("")) {
                    rlMonth.setVisibility(View.GONE);
                }
                if (personlizedCharge.getDb().getYearMoney().equals("")) {
                    rlYear.setVisibility(View.GONE);
                }
                tvPerday.setText(personlizedCharge.getDb().getDayMoney());
                tvPerweek.setText(personlizedCharge.getDb().getWeekMoney());
                tvPermonth.setText(personlizedCharge.getDb().getMonthMoney());
                tvPeryear.setText(personlizedCharge.getDb().getYearMoney());
                break;
            case HttpIdentifier.PURCHASE_SERVICE:
                Logger.e("purchase", result);
                JSONObject object = JSONObject.parseObject(result);
                if (object.getString("result").equals("0000")) {
                    final String orderInfo = object.getString("returnContent");
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(PurchaseServiceAty.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }
                break;
            case HttpIdentifier.ZERO_RECORD:
                Logger.e("zero",result);
                showToast(getString(R.string.goumaichenggong));
                finish();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        showToast(getString(R.string.fuwuqiyichang));
    }

    @OnClick({R.id.btn_purchase, R.id.rl_day, R.id.rl_week, R.id.rl_month, R.id.rl_year, R.id.cjs_rlb, R.id.iv_reduce, R.id.iv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_purchase://75b8e7253dbc42759f506b280e39de6c  侯卫嘉的测试userid
                if (type != null) {
                    if (total_amount.equals("0")||total_amount.equals("0.0")||total_amount.equals("0.00")) {
                        if (null != doctorInfo) {
                            doHttp(RetrofitUtils.createApi(Pay.class).zeroRecord(phone, secret, userId, doctorInfo.getUserId(), type), HttpIdentifier.ZERO_RECORD);
                        }else{
                            doHttp(RetrofitUtils.createApi(Pay.class).zeroRecord(phone, secret, userId, doctorInfoFromPay.getId(), type), HttpIdentifier.ZERO_RECORD);
                        }
                    }else{
                        Bundle bundle = new Bundle();
                        if (null != doctorInfo) {
                            bundle.putString("toUserId", doctorInfo.getUserId());
                        } else {
                            bundle.putString("toUserId", doctorInfoFromPay.getId());
                        }
                        bundle.putString("total_amount", total_amount);
                        bundle.putString("type", type);
                        bundle.putString("times", tvPurchaseNum.getText().toString().trim());
                        startActivity(ConfirmOrderAty.class, bundle);
                    }
                } else {
                    showToast(getString(R.string.qingxuanzefuwuleixing));
                }
                break;
            case R.id.rl_day:
                cbDay.setChecked(false);
                cbWeek.setChecked(false);
                cbMonth.setChecked(false);
                cbYear.setChecked(false);
                cbDay.setChecked(true);
                total_amount = tvPerday.getText().toString();
                type = "4";
                break;
            case R.id.rl_week:
                cbDay.setChecked(false);
                cbWeek.setChecked(false);
                cbMonth.setChecked(false);
                cbYear.setChecked(false);
                cbWeek.setChecked(true);
                total_amount = tvPerweek.getText().toString();
                type = "3";
                break;
            case R.id.rl_month:
                cbDay.setChecked(false);
                cbWeek.setChecked(false);
                cbMonth.setChecked(false);
                cbYear.setChecked(false);
                cbMonth.setChecked(true);
                total_amount = tvPermonth.getText().toString();
                type = "2";
                break;
            case R.id.rl_year:
                cbDay.setChecked(false);
                cbWeek.setChecked(false);
                cbMonth.setChecked(false);
                cbYear.setChecked(false);
                cbYear.setChecked(true);
                total_amount = tvPeryear.getText().toString();
                type = "1";
                break;
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.iv_reduce:
                purchaseNum--;
                tvPurchaseNum.setText(purchaseNum + "");
                if (tvPurchaseNum.getText().toString().equals("1")) {
                    ivReduce.setImageResource(R.mipmap.gmfw_btn_reduce_dis);
                    ivReduce.setClickable(false);
                }
                break;
            case R.id.iv_add:
                purchaseNum++;
                tvPurchaseNum.setText(purchaseNum + "");
                ivReduce.setImageResource(R.mipmap.gmfw_btn_reduce);
                ivReduce.setClickable(true);
                break;
        }
    }

}
