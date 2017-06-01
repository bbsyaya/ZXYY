package com.zhixinyisheng.user.ui.data.xueya;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.view.blood.MagicProgressCircle;
import com.zhixinyisheng.user.view.blood.MagicProgressCircle1;

import butterknife.Bind;
import butterknife.OnClick;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_BLOOD_DATA;

/**
 * 血压测试结果
 * Created by 焕焕 on 2016/12/25.
 */
public class XueYaJieGuoAty extends BaseAty {
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
    @Bind(R.id.main_mpc_gy)
    MagicProgressCircle mainMpcGy;
    @Bind(R.id.main_mpc_dy)
    MagicProgressCircle1 mainMpcDy;
    @Bind(R.id.tv_xueya_below)
    TextView tvXueyaBelow;
    @Bind(R.id.tv_xueyayuanxin_gaoya)
    TextView tvXueyayuanxinGaoya;
    @Bind(R.id.tv_xueyayuanxin_diya)
    TextView tvXueyayuanxinDiya;
    @Bind(R.id.ll_xueya_below)
    LinearLayout llXueyaBelow;
    @Bind(R.id.xinlvzhi_tv1)
    TextView xinlvzhiTv1;
    @Bind(R.id.xinlvzhi_tv2)
    TextView xinlvzhiTv2;
    @Bind(R.id.tv_person_signature2)
    TextView tvPersonSignature2;
    @Bind(R.id.textView13)
    TextView textView13;
    @Bind(R.id.textView14)
    TextView textView14;
    @Bind(R.id.xueya_fanwei)
    LinearLayout xueyaFanwei;
    private String  gao, di;
    int current1 = 0;
    int current2 = 0;
    boolean isAnimActive1= false;
    boolean isAnimActive2 = false;
    @Override
    public int getLayoutId() {
        return R.layout.aty_xueyajieguo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(getResources().getString(R.string.rapidTestResults));
        ivBack.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        di = intent.getStringExtra("diYa");
        gao = intent.getStringExtra("gaoYa");
        tvXueyayuanxinGaoya.setText(gao);
        tvXueyayuanxinDiya.setText(di);
        current1 = Integer.valueOf(gao);
        onReRandomPercent1();
        current2 = Integer.valueOf(di);
        onReRandomPercent2();
    }
    // 刷新外圈
    public void onReRandomPercent1() {
        if (isAnimActive1) {
            return;
        }
        anim1();
    }
    // 刷新内圈
    public void onReRandomPercent2() {
        if (isAnimActive2) {
            return;
        }
        anim2();
    }
    // 改变progress
    private void anim1() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mainMpcGy, "percent", 0, current1 / (279f * (290 / 360f))));
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(2000);
        set.start();
    }

    // 改变内部progress
    private void anim2() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mainMpcDy, "percent", 0, current2
                / (178f * (290 / 360f))));
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(2000);
        set.start();
    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        IS_CHANGE_BLOOD_DATA = true;
        finish();
    }
}
