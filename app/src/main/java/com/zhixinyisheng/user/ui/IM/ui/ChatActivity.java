package com.zhixinyisheng.user.ui.IM.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.SelInfoEntity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseIMUrl;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.runtimepermissions.PermissionsManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * chat activity，EaseChatFragment was used {@link #}
 */
public class ChatActivity extends BaseAty {
    public static ChatActivity activityInstance;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    private EaseChatFragment chatFragment;
    String userId;

    //判断是否发分享消息
    private boolean isShare = false;
    //医生信息，用于分享医生名片
    public DoctorHomePage.UserPdBean docotrInfo;

    @Override
    public int getLayoutId() {
        return R.layout.em_activity_chat;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //分享到知心医生好友用到
        isShare = getIntent().getBooleanExtra(FriendsAty.EXTRA_IS_SHARE, false);
        docotrInfo = (DoctorHomePage.UserPdBean) getIntent().getSerializableExtra(FriendsAty.EXTRA_MODEL);
        activityInstance = this;
        //get user id or group id
        userId = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();

        //用于控制发消息的类型
        getIntent().getExtras().putBoolean(FriendsAty.EXTRA_IS_SHARE, isShare);
        docotrInfo = (DoctorHomePage.UserPdBean) getIntent().getSerializableExtra(FriendsAty.EXTRA_MODEL);

        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_chat, chatFragment).commit();

        //标题栏名称
        EaseUser easeUser = EaseUserUtils.getUserInfo(userId);
        if (easeUser.getNick().equals("单项好友")) {
            getMyinfo(easeUser);
        } else {
            cjsTvt.setText(easeUser.getNick());
        }
        ivBack.setVisibility(View.VISIBLE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (userId.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        hideSoftKeyboard();
//        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
    }

    public String getToChatUsername() {
        return userId;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        hideSoftKeyboard();
        finish();
    }

    /**
     * 获取所有好友
     *
     * @param easeUser
     */
    public void getMyinfo(EaseUser easeUser) {
        RetrofitUtils.createApi(EaseIMUrl.class)
                .getMyinfo(BaseApplication.phone, BaseApplication.secret, easeUser.getUsername())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Logger.e("接口地址 单向好友", call.request().url().toString());
                        try {
                            String result = response.body().string();
                            Logger.e("接口返回结果 单向好友", result);

                            SelInfoEntity sfe = JSON.parseObject(result, SelInfoEntity.class);
                            String name = sfe.getDb().getUsername();

                            if (TextUtils.isEmpty(name) || name.equals("") || name.equals("匿名")) {
                                cjsTvt.setText(sfe.getDb().getPhone());
                            } else {
                                cjsTvt.setText(name);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Logger.e("联网onFailure", t.getMessage() + "  @  " + call.request().url().toString());

//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });


    }


}
