package com.zhixinyisheng.user.ui.pay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.pay.MyIncome;
import com.zhixinyisheng.user.http.Pay;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 我的收入
 * Created by 焕焕 on 2017/1/4.
 */
public class MyIncomeAty extends BaseAty {
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
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.rl_myincome_tixian)
    RelativeLayout rlMyincomeTixian;
    @Bind(R.id.rl_myincome_mingxi)
    RelativeLayout rlMyincomeMingxi;
    @Bind(R.id.title_btn_hosptal)
    Button titleBtnHosptal;
    @Bind(R.id.tv_income)
    TextView tvIncome;
    @Bind(R.id.tv_balance)
    TextView tvBalance;

    @Override
    public int getLayoutId() {
        return R.layout.aty_myincome;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("我的收入");

    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(Pay.class).myIncome(phone, secret, userId), HttpIdentifier.MYINCOME);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.MYINCOME:
                MyIncome myIncome = JSON.parseObject(result, MyIncome.class);
                tvIncome.setText(myIncome.getDb().getIncome()+"");
                tvBalance.setText(myIncome.getDb().getBalance()+"");
                break;
        }
    }

    @OnClick({R.id.cjs_rlb, R.id.rl_myincome_tixian, R.id.rl_myincome_mingxi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.rl_myincome_tixian://申请提现
                double yuE = Double.parseDouble(tvBalance.getText().toString());
                if (yuE>=5){
                    Bundle bundle = new Bundle();
                    bundle.putString("balance",tvBalance.getText().toString());
                    startActivity(ApplyCashAty.class, bundle);
                }else{
                    showToast("余额不足5元，不能提现！");
                }
                break;
            case R.id.rl_myincome_mingxi://收支明细(账单)
                startActivity(BillAty.class, null);
                break;
        }
    }

}
