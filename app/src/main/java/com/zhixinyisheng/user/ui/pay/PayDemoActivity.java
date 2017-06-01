package com.zhixinyisheng.user.ui.pay;

import android.os.Bundle;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;

/**
 * 支付demo（最后删除）
 * Created by 焕焕 on 2017/1/5.
 */
public class PayDemoActivity extends BaseAty {
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2017010104782293";
    public static final String RSA2_PRIVATE = Content.RSA2_PRIVATE;
    @Override
    public int getLayoutId() {
        return R.layout.first_layout;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void requestData() {

    }
}
