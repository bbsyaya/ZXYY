package com.zhixinyisheng.user.ui.IM.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.domain.SearchFrendEntity;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/10/31  16:29
 * <p>
 * 类说明: 添加好友的好友详情界面
 */
public class AddFriendDetialAty extends BaseAty {
    // 用户ID
    private String allUser[] = new String[]{"1",
            "2c0fcf1171e1423097814250c23edceb",
            "2",
            "3",
            "546db053797d4b819fa321d4c42d26d2",
            "7566626570b04a24836ee8b0b7b17cf9",
            "75b8e7253dbc42759f506b280e39de6c",
            "7cb271b4d8864e0db943107f4cf49ac7",
            "82ce3bd175984373a1ba3668da16f619",
            "a0255593c31c4b829b054a3d39b06451",
            "d9b5b203eb98458cb31337abad51b1c3",
            "dbb38516fb8e49edb51fdd5f2d75b645",
            "e90e7efbde9442b1b4c0755a84b97cc4"};

    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.aty_addfd_ivname)
    ImageView atyAddfdIvname;
    @Bind(R.id.aty_addfd_tvname)
    TextView atyAddfdTvname;
    @Bind(R.id.aty_addf_llname)
    LinearLayout atyAddfLlname;

    @Bind(R.id.aty_addfd_etremark)
    EditText atyAddfdEtremark;
    @Bind(R.id.aty_addf_rlszbz)
    RelativeLayout atyAddfRlszbz;

    @Bind(R.id.aty_addfd_tvdq)
    TextView atyAddfdTvdq;
    @Bind(R.id.aty_addf_rldq)
    RelativeLayout atyAddfRldq;

    @Bind(R.id.aty_addfd_tvgxqm)
    TextView atyAddfdTvgxqm;
    @Bind(R.id.aty_addf_rlgxqm)
    RelativeLayout atyAddfRlgxqm;
    @Bind(R.id.aty_addf_btnaddf)
    Button atyAddfBtnaddf;

    SearchFrendEntity sfe = null;//传递来的好友对象

    @Override
    public int getLayoutId() {
        return R.layout.aty_addfrienddetal;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        cjsTvt.setText(R.string.haoyouxiangqing);
        ivBack.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        String result = bundle.getString("a");
        sfe = JSON.parseObject(result, SearchFrendEntity.class);
        Logger.e("sfe de",result);
        /**
         * 判断是本人
         */
        if (sfe.getDb().getUserId().equals(UserManager.getUserInfo().getUserId())) {
            atyAddfBtnaddf.setVisibility(View.GONE);
        }

        Glide.with(this).load(sfe.getDb().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)//占位图
                .error(R.mipmap.ic_launcher2)//加载错误图
                .bitmapTransform(new CropCircleTransformation(this))//裁剪圆形
                .into(atyAddfdIvname);
        if (sfe.getDb().getName().equals("")) {
            if (sfe.getDb().getUsername().equals("")) {
                atyAddfdTvname.setText(sfe.getDb().getCard()+"");
            } else {
                atyAddfdTvname.setText(sfe.getDb().getUsername()+"");
            }
        } else {
            atyAddfdTvname.setText(sfe.getDb().getName()+"");
        }

        atyAddfdTvdq.setText(sfe.getDb().getAddress());
        atyAddfdTvgxqm.setText(sfe.getDb().getRemark());

        if (sfe.getDb().getIsMyFrinds().equals("yes")) {
            atyAddfBtnaddf.setText(R.string.faxiaoxi);

        }

    }


    @OnClick({R.id.cjs_rlb, R.id.aty_addf_btnaddf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.aty_addf_btnaddf:
                if (sfe.getDb().getIsMyFrinds().equals("yes")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", sfe.getDb().getUserId());
                    startActivity(ChatActivity.class, bundle);
                } else {
                    // 正经加好友
                    String remarkName = atyAddfdEtremark.getText().toString().trim();
                    if (TextUtils.isEmpty(remarkName) || remarkName.equals("")) {
                        showLoadingDialog(null);

                        doHttp(RetrofitUtils.createApi(IMUrl.class).addFrined(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                sfe.getDb().getUserId(), LanguageUtil.judgeLanguage()), 0);
                    } else {
                        showLoadingDialog(null);

                        doHttp(RetrofitUtils.createApi(IMUrl.class).addFrined(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                sfe.getDb().getUserId(),
                                remarkName,LanguageUtil.judgeLanguage()), 0);
                    }
                }


                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        showToast(getString(R.string.yifasongshenqing));
        Content.TONG_XUN_LU = 1;
//        String remarkName = atyAddfdEtremark.getText().toString().trim();

//        EaseUser user = new EaseUser(sfe.getDb().getUserId());
//
//        if (TextUtils.isEmpty(remarkName) || remarkName.equals("")) {
//
//            if (sfe.getDb().getName().equals("") || sfe.getDb().getName().equals("匿名") || TextUtils.isEmpty(sfe.getDb().getName())) {
//                user.setNick(sfe.getDb().getPhone());
//            } else {
//                user.setNick(sfe.getDb().getUsername());
//            }
//        } else {
//            user.setNick(remarkName);
//        }
//
//        user.setAvatar(sfe.getDb().getHeadUrl());
//        user.setNo("0");
//        user.setSos("0");
//        user.setDatas("0");
//
//
//        /**
//         * 好友添加到数据库
//         */
//        DemoDBManager.getInstance().saveContact(user);

        /**
         * 返回到好友界面
         */
        AppManger.getInstance().killActivity(SearchFriendAty.class);
        AppManger.getInstance().killActivity(this);

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        JSONObject object = JSONObject.parseObject(result);
        if (object.getString("result").equals("1002")) {
            showToast(getString(R.string.haoyouyijingcunzai));
        } else {
            showToast(object.getString("retMessage"));
        }
    }
}
