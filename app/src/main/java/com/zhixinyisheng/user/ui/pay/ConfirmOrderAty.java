package com.zhixinyisheng.user.ui.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.http.Pay;
import com.zhixinyisheng.user.ui.BaseAty;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 确认订单
 * Created by 焕焕 on 2017/1/7.
 */
public class ConfirmOrderAty extends BaseAty {
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
    @Bind(R.id.imageView4)
    ImageView imageView4;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    @Bind(R.id.tv_money)
    TextView tvMoney;

    String toUserId, total_amount, type, times;
    public static ConfirmOrderAty instance;
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
                        Bundle bundle = new Bundle();
                        bundle.putString("payingDoctorId",toUserId);
                        Logger.e("toUserID",toUserId);
                        startActivity(PaySuccessAty.class, bundle);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        startActivity(PayFailureAty.class, null);
//                        startActivity(PaySuccessAty.class, null);//剧京测试用
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
        return R.layout.aty_confirmorder;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        instance = this;
        cjsTvt.setText(R.string.querendingdan);
        ivBack.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        toUserId = intent.getStringExtra("toUserId");
        Logger.e("toUserID init",toUserId);
        total_amount = intent.getStringExtra("total_amount");
        type = intent.getStringExtra("type");
        times = intent.getStringExtra("times");
        double money = Double.parseDouble(total_amount);
        double per = Double.parseDouble(times);
        tvMoney.setText(money*per+"");

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        Logger.e("purchase", result);
        JSONObject object = JSONObject.parseObject(result);
        if (object.getString("result").equals("0000")) {
            final String orderInfo = object.getString("returnContent");
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(ConfirmOrderAty.this);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Log.e("msp 179", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }

    @OnClick({R.id.btn_buy, R.id.cjs_rlb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                doHttp(RetrofitUtils.createApi(Pay.class).toPay(phone, secret, userId, total_amount, toUserId, type, times), HttpIdentifier.PURCHASE_SERVICE);
                break;
            case R.id.cjs_rlb:
                finish();
                break;
        }
    }
}
