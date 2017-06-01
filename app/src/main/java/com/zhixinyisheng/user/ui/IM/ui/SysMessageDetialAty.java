package com.zhixinyisheng.user.ui.IM.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.SysMessageEntity;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/11/10  14:08
 * <p>
 * 类说明:
 */
public class SysMessageDetialAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.aty_ino_tv_n)
    TextView atyInoTvN;
    @Bind(R.id.aty_ino_tv_c)
    TextView atyInoTvC;
    @Bind(R.id.aty_ino_btn)
    Button atyInoBtn;

    SysMessageEntity.ListBean listBean = null;

    @Override
    public int getLayoutId() {
        return R.layout.aty_importnotice;
    }

    @Override
    public void requestData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        cjsTvt.setText(R.string.zhongyaotongzhi);
        ivBack.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        listBean = (SysMessageEntity.ListBean) bundle.getSerializable("a");

        EaseUser easeUser = EaseUserUtils.getUserInfo(listBean.getFromUserId());
        atyInoTvN.setText(easeUser.getNick()+getString(R.string.xiangninfalailezhongyaotongzhi));
        atyInoTvC.setText(listBean.getContent());





        /**
         * 更改为已读
         */
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(IMUrl.class).readSet(UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret(),
                listBean.getSysMessageId()),0);




    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);






    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        showToast(getString(R.string.fuwuqiyichang));
    }

    @OnClick({R.id.iv_back, R.id.aty_ino_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.aty_ino_btn:
                Intent intent = new Intent(SysMessageDetialAty.this,ChatActivity.class);
                intent.putExtra("userId", listBean.getFromUserId());
                startActivity(intent);
                finish();

                break;
        }
    }
}
