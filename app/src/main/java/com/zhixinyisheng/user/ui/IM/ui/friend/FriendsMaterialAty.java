package com.zhixinyisheng.user.ui.IM.ui.friend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.ActivityRequestCode;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.domain.FriendDetialEntity;
import com.zhixinyisheng.user.ui.mine.RenZhengImageDetailAty;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 好友资料
 * Created by 焕焕 on 2017/1/5.
 */
public class FriendsMaterialAty extends BaseAty {
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
    @Bind(R.id.iv_fm_head)
    ImageView ivFmHead;
    @Bind(R.id.tv_fm_name)
    TextView tvFmName;
    @Bind(R.id.tv_fm_num)
    TextView tvFmNum;
    @Bind(R.id.rl_fm_setremark)
    RelativeLayout rlFmSetremark;
    @Bind(R.id.tv_fm_sex)
    TextView tvFmSex;
    @Bind(R.id.tv_fm_old)
    TextView tvFmOld;
    @Bind(R.id.tv_fm_job)
    TextView tvFmJob;
    @Bind(R.id.tv_fm_hospital)
    TextView tvFmHospital;
    @Bind(R.id.tv_fm_address)
    TextView tvFmAddress;
    @Bind(R.id.tv_fm_gxqm)
    TextView tvFmGxqm;
    @Bind(R.id.tv_fm_signature)
    TextView tvFmSignature;
    @Bind(R.id.tv_fm_section)
    TextView tvFmSection;//科室
    @Bind(R.id.tv_fm_disease)
    TextView tvFmDisease;//擅长疾病
    @Bind(R.id.title_btn_hosptal)
    Button titleBtnHosptal;
    @Bind(R.id.title_line)
    View titleLine;
    @Bind(R.id.rl_fm_job)
    RelativeLayout rlFmJob;
    @Bind(R.id.rl_fm_section)
    RelativeLayout rlFmSection;
    @Bind(R.id.rl_fm_disease)
    RelativeLayout rlFmDisease;
    @Bind(R.id.rl_fm_hospital)
    RelativeLayout rlFmHospital;


    String result_detial = "";
    FriendDetialEntity friendDetialEntity;

    @Override
    public int getLayoutId() {
        return R.layout.aty_friendsmeterial;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("好友资料");
        Bundle bundle = getIntent().getExtras();
        result_detial = bundle.getString("result_detial");
        friendDetialEntity = JSON.parseObject(result_detial, FriendDetialEntity.class);
        if (friendDetialEntity.getDb().getIsDoctor() == 0) {
            rlFmJob.setVisibility(View.GONE);
            rlFmSection.setVisibility(View.GONE);
            rlFmHospital.setVisibility(View.GONE);
            rlFmDisease.setVisibility(View.GONE);
        } else {
            rlFmJob.setVisibility(View.VISIBLE);
            rlFmSection.setVisibility(View.VISIBLE);
            rlFmHospital.setVisibility(View.VISIBLE);
            rlFmDisease.setVisibility(View.VISIBLE);
        }
        Glide.with(this).load(friendDetialEntity.getDb().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)//占位图
                .error(R.mipmap.ic_launcher2)//加载错误图
                .bitmapTransform(new CropCircleTransformation(this))//裁剪圆形
                .into(ivFmHead);
        tvFmName.setText(friendDetialEntity.getDb().getUsername());
        if (friendDetialEntity.getDb().getSex() == 0) {
            tvFmSex.setText("女");
        } else {
            tvFmSex.setText("男");
        }

        tvFmOld.setText(friendDetialEntity.getDb().getAge() + "");
        tvFmJob.setText(friendDetialEntity.getDb().getJob());
        tvFmHospital.setText(friendDetialEntity.getDb().getHospital());
        tvFmAddress.setText(friendDetialEntity.getDb().getAddress());
        tvFmSignature.setText("\u3000\u3000" + friendDetialEntity.getDb().getRemark());
        tvFmSection.setText(friendDetialEntity.getDb().getSection());
        tvFmDisease.setText(friendDetialEntity.getDb().getDisease());
        tvFmNum.setText("用户号 : "+friendDetialEntity.getDb().getCard());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ActivityRequestCode.SET_REMARK) {
            tvFmName.setText(data.getStringExtra("remark"));
            setResult(ActivityRequestCode.FRIENDS_MATERIAL, data);
        }
    }

    @OnClick({R.id.iv_fm_head, R.id.cjs_rlb, R.id.rl_fm_setremark})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fm_head:
                //以下是图片放大效果
                Intent intentDetail = new Intent(FriendsMaterialAty.this, RenZhengImageDetailAty.class);
                intentDetail.putExtra("images", friendDetialEntity.getDb().getHeadUrl());
                startActivity(intentDetail);
                overridePendingTransition(0, 0);
                break;
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.rl_fm_setremark:
                startActivityForResult(SetRemarkAty.class, null, ActivityRequestCode.SET_REMARK);
                break;
        }
    }

}
