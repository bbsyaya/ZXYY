package com.zhixinyisheng.user.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.hyphenate.easeui.widget.photoview.EasePhotoView;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 认证图片的点击放大
 * Created by 焕焕 on 2016/12/29.
 */
public class RenZhengImageDetailAty extends BaseAty {
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
    @Bind(R.id.xy_rlc)
    RelativeLayout xyRlc;
    @Bind(R.id.epv_asi)
    EasePhotoView epvAsi;

    @Override
    public int getLayoutId() {
        return R.layout.activity_spaceimage;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.yulan);
        ivBack.setVisibility(View.VISIBLE);
        showLoadingDialog(getString(R.string.tupianjiezaizhong));
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("images");
        Glide.with(this).load(imagePath).into(new GlideDrawableImageViewTarget(epvAsi) {
            @Override
            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                super.onResourceReady(drawable, anim);
                //在这里添加一些图片加载完成的操作
                dismissLoadingDialog();
            }
        });
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f,
                1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        epvAsi.startAnimation(scaleAnimation);
    }

    @Override
    public void requestData() {

    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        finish();
    }
}
