package com.zhixinyisheng.user.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 关于
 * Created by 焕焕 on 2017/3/22.
 */

public class AboutAty extends BaseAty {
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
    @Bind(R.id.setting_ll_problem)
    RelativeLayout settingLlProblem;
    @Bind(R.id.setting_ll_version)
    RelativeLayout settingLlVersion;
    @Bind(R.id.setting_ll_about)
    RelativeLayout settingLlAbout;

    @Override
    public int getLayoutId() {
        return R.layout.aty_about;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        cjsTvt.setText(R.string.guanyu);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.cjs_rlb,R.id.setting_ll_problem, R.id.setting_ll_version, R.id.setting_ll_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.setting_ll_problem:
                startActivity(ProblemFeedback.class, null);
                break;
            case R.id.setting_ll_version:
                startActivity(VersionAty.class, null);
                break;
            case R.id.setting_ll_about://功能介绍
                startActivity(FuncationAty.class, null);
//                startActivity(DuanDianAty.class,null);
//                startActivity(FriendsDemoAty.class,null);//TODO 以后优化的时候用
//                startActivity(HorizontalWheelAty.class,null);//横向demo
                break;
        }
    }
}
