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
import com.zhixinyisheng.user.domain.pay.OrderDetail;
import com.zhixinyisheng.user.http.Pay;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 订单详情
 * Created by 焕焕 on 2017/2/18.
 */
public class OrderDetailAty extends BaseAty {
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
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_payer_name)
    TextView tvPayerName;
    @Bind(R.id.tv_payee_name)
    TextView tvPayeeName;
    @Bind(R.id.tv_pay_state)
    TextView tvPayState;
    @Bind(R.id.tv_order_number)
    TextView tvOrderNumber;
    @Bind(R.id.tv_transfer_time)
    TextView tvTransferTime;
    @Bind(R.id.tv_order_validity)
    TextView tvOrderValidity;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    String ex_or_in="";
    @Override
    public int getLayoutId() {
        return R.layout.aty_order_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        long incomePayRecordId = bundle.getLong("incomePayRecordId");
        ex_or_in =  bundle.getString("ex_or_in");
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("订单详情");
        doHttp(RetrofitUtils.createApi(Pay.class).billDetail(phone, secret, userId, incomePayRecordId, "2"), HttpIdentifier.BILL_DETAIL_EXPEND);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.BILL_DETAIL_EXPEND:
                OrderDetail orderDetail = JSON.parseObject(result, OrderDetail.class);
                double money =  Math.abs(Double.parseDouble(orderDetail.getDb().getMoney())) ;
                tvMoney.setText("¥" + money);
                String payerName = "";
                if (orderDetail.getDb().getPayName().equals("")) {
                    payerName = orderDetail.getDb().getPayUserName();
                } else {
                    payerName = orderDetail.getDb().getPayName();
                }
                tvPayerName.setText(payerName);
                String payeeName = "";
                if (orderDetail.getDb().getIncomeName().equals("")) {
                    payeeName = orderDetail.getDb().getIncomeUserName();
                } else {
                    payeeName = orderDetail.getDb().getIncomeName();
                }
                tvPayeeName.setText(payeeName+" 大夫");
                if (orderDetail.getDb().getFlag().equals("")) {
                    if (ex_or_in.equals("in")){
                        tvPayState.setText("已收入");
                    }else {
                        tvPayState.setText("支付成功");
                    }

                } else if (orderDetail.getDb().getFlag().equals("0")) {
                    tvPayState.setText("正在处理");
                } else if (orderDetail.getDb().getFlag().equals("1")) {
                    tvPayState.setText("提现成功");
                } else if (orderDetail.getDb().getFlag().equals("2")) {
                    tvPayState.setText("提现失败");
                }
                tvOrderNumber.setText(orderDetail.getDb().getPayCard());
                tvTransferTime.setText(orderDetail.getDb().getCreatetime());
                tvOrderValidity.setText(orderDetail.getDb().getCreatetime() + "\n" + orderDetail.getDb().getEndTime());
                if (orderDetail.getDb().getReason().equals("")) {
                    tvRemark.setText("无");
                } else {
                    tvRemark.setText(orderDetail.getDb().getReason());
                }
                break;
        }
    }


    @OnClick({R.id.cjs_rlb})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cjs_rlb:
                finish();
                break;
        }
    }
}
