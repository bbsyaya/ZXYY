package com.zhixinyisheng.user.ui.messure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;

import butterknife.Bind;
import butterknife.OnClick;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_HEART_DATA;

/**
 * 测试结果界面
 * Created by 焕焕 on 2016/10/20.
 */
public class KsCeShiJieGuo extends BaseAty {
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
    @Bind(R.id.xin_iv)
    ImageView xinIv;
    @Bind(R.id.xinlvzhi_tv)
    TextView tvXinLv;
    @Bind(R.id.xinlv_fanwei)
    LinearLayout xinlv_fanwei;
    @Bind(R.id.xinlvzhi_tv1)
    TextView tvXinlv1;
    @Bind(R.id.xinlvzhi_tv2)
    TextView tvXinlv2;
    @Bind(R.id.xueya_fanwei)
    LinearLayout xueya_fanwei;
    private String value, gao, di;

    @Override
    public int getLayoutId() {
        return R.layout.ksceshijieguo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(getResources().getString(R.string.rapidTestResults));
        ivBack.setVisibility(View.VISIBLE);
        if (Content.XVORXY.equals("0")) {
            xinlv_fanwei.setVisibility(View.VISIBLE);
            xueya_fanwei.setVisibility(View.GONE);
            Intent intent = getIntent();
            value = intent.getStringExtra("xinLv");
            tvXinLv.setText(getResources().getString(R.string.home_heartRate)+":" + value);

        } else if (Content.XVORXY.equals("1")) {
            xinlv_fanwei.setVisibility(View.GONE);
            xueya_fanwei.setVisibility(View.VISIBLE);
            Intent intent = getIntent();
            di = intent.getStringExtra("diYa");
            gao = intent.getStringExtra("gaoYa");
            tvXinlv1.setText(getResources().getString(R.string.maximumPressure)+":" + gao);
            tvXinlv2.setText(getResources().getString(R.string.minimumPressure)+":" + di);
        }
    }

    @Override
    public void requestData() {

    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        IS_CHANGE_HEART_DATA = true;
        finish();
    }
}
