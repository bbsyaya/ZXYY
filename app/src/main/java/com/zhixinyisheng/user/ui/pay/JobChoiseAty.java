package com.zhixinyisheng.user.ui.pay;

import android.content.Intent;
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
 * 职称选择
 * Created by 焕焕 on 2017/1/11.
 */
public class JobChoiseAty extends BaseAty {
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
    @Bind(R.id.rl_zhuren)
    RelativeLayout rlZhuren;
    @Bind(R.id.rl_fuzhuren)
    RelativeLayout rlFuzhuren;
    @Bind(R.id.rl_zhuzhi)
    RelativeLayout rlZhuzhi;
    @Bind(R.id.rl_zhuyuan)
    RelativeLayout rlZhuyuan;

    @Override
    public int getLayoutId() {
        return R.layout.aty_jobchoise;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("选择职称");
    }


    @OnClick({R.id.cjs_rlb, R.id.rl_zhuren, R.id.rl_fuzhuren, R.id.rl_zhuzhi, R.id.rl_zhuyuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.rl_zhuren:
                Intent mIntent1 = new Intent();
                mIntent1.putExtra("job", "主任医师");
                mIntent1.putExtra("grade", 4);
                this.setResult(0, mIntent1);
                finish();
                break;
            case R.id.rl_fuzhuren:
                Intent mIntent2 = new Intent();
                mIntent2.putExtra("job", "副主任医师");
                mIntent2.putExtra("grade", 3);
                this.setResult(0, mIntent2);
                finish();
                break;
            case R.id.rl_zhuzhi:
                Intent mIntent3 = new Intent();
                mIntent3.putExtra("job", "主治医师");
                mIntent3.putExtra("grade", 2);
                this.setResult(0, mIntent3);
                finish();
                break;
            case R.id.rl_zhuyuan:
                Intent mIntent4 = new Intent();
                mIntent4.putExtra("job", "住院医师");
                mIntent4.putExtra("grade", 1);
                this.setResult(0, mIntent4);
                finish();
                break;
        }
    }
}
