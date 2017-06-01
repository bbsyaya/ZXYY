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

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.http.Pay;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 申请提现
 * Created by 焕焕 on 2017/1/6.
 */
public class ApplyCashAty extends BaseAty implements TextWatcher{
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
    @Bind(R.id.tv_cash_balance)
    TextView tvCashBalance;
    @Bind(R.id.et_aliaccount)
    EditText etAliaccount;
    @Bind(R.id.et_aliname)
    EditText etAliname;
    @Bind(R.id.et_cashmoney)
    EditText etCashmoney;

    @Override
    public int getLayoutId() {
        return R.layout.aty_applycash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("提现");
        Bundle bundle = getIntent().getExtras();
        String balance = bundle.getString("balance");
        tvCashBalance.setText(balance);
        etCashmoney.addTextChangedListener(this);
    }

    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        showToast("提交成功，预计三个工作日到账");
        finish();
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        JSONObject object = JSONObject.parseObject(result);
        if (object.getString("result").equals("1054")) {
            showToast("只有周二才能提现！");
        } else {
            showToast("服务器异常！");
        }
    }

    @OnClick({R.id.tv_cash_all,R.id.cjs_rlb, R.id.btn_cash})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cash_all:
                etCashmoney.setText(tvCashBalance.getText().toString());

                break;
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.btn_cash:
                if (etAliaccount.getText().toString().equals("")) {
                    showToast("支付宝账号不能为空！");
                    etAliaccount.requestFocus();
                    return;
                }
                if (etAliname.getText().toString().equals("")){
                    showToast("支付宝姓名不能为空！");
                    etAliname.requestFocus();
                    return;
                }
                if (etCashmoney.getText().toString().equals("")
                        ||etCashmoney.getText().toString().equals("0")
                        ||etCashmoney.getText().toString().equals("0.0")
                        ||etCashmoney.getText().toString().equals("0.00")){
                    showToast("提取金额不能为空！");
                    etCashmoney.requestFocus();
                    return;
                }
                double willBalance = 0;
                try {
                    willBalance = Double.parseDouble(etCashmoney.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    showToast("请输入正确金额！");
                    etCashmoney.requestFocus();
                    return;

                }
                double balance = Double.parseDouble(tvCashBalance.getText().toString());
                if (willBalance>balance){
                    showToast("超出可提取金额！");
                    etCashmoney.requestFocus();
                    return;
                }
                if (Double.parseDouble(etCashmoney.getText().toString())<5){
                    showToast("小于5元，不能提现！");
                    etCashmoney.requestFocus();
                    return;
                }
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(Pay.class).getMoney(phone, secret, userId,
                        etAliaccount.getText().toString(),etCashmoney.getText().toString(),
                        etAliname.getText().toString()),
                        HttpIdentifier.GETMONEY);

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
        if (temp.length() - posDot - 1 > 2)
        {
            edt.delete(posDot + 3, posDot + 4);
        }
    }
}
