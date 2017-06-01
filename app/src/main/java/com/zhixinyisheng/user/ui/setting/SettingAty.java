package com.zhixinyisheng.user.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.EMCallBack;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.AppConfig;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.IM.ui.SelectFriendsAty;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.SetSHActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.ui.login.LoginAty;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 设置
 * Created by gjj on 2016/10/19.
 */
public class SettingAty extends BaseAty {
    @Bind(R.id.iv_back)
    ImageView iv_back;//返回键
    @Bind(R.id.cjs_tvt)
    TextView cjs_tvt;//标题栏

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        iv_back.setVisibility(View.VISIBLE);
        cjs_tvt.setText(getResources().getString(R.string.left_settings));


    }

    @OnClick({R.id.setting_ll_guanyu,R.id.setting_ll_general, R.id.setting_ll_health, R.id.setting_ll_bracelet, R.id.setting_ll_sos,
            R.id.setting_btn_log_out})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.setting_ll_guanyu://关于
                startActivity(AboutAty.class,null);
                break;
            case R.id.setting_ll_general:
                startActivity(GeneralAty.class,null);//通用
                break;
            case R.id.setting_ll_health:
                Constant.GZCKSOS = 1;
                startActivity(SelectFriendsAty.class, null);
                break;
            case R.id.setting_ll_bracelet:
                if (TestService.mDevice == null) {
                    TestService.no_shsz = 0;
                    startActivity(BLEActivity.class, null);
                } else {
                    startActivity(SetSHActivity.class, null);
                }
                break;
            case R.id.setting_ll_sos:
                Constant.GZCKSOS = 2;
                startActivity(SelectFriendsAty.class, null);
                break;

            case R.id.setting_btn_log_out:
                new MaterialDialog(this).setMDMessage(getResources().getString(R.string.shifoulijituichu))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                /**
                                 * 退出IM
                                 */
                                imLogout();
                                //清除极光推送信息
                                JPushInterface.setAlias(SettingAty.this, "", null);
                                Set<String> set = new HashSet<>();
                                JPushInterface.setTags(SettingAty.this, set, null);
                                Intent sIntent = new Intent(AppConfig.ACTION_LOGOUT);
                                sendBroadcast(sIntent);
                                UserManager.setIsLogin(false);
//                                MainActivity.instance.finish();
                                startActivity(LoginAty.class, null);
                                AppManger.getInstance().killAllActivity();
//                                finish();
                            }
                        }).show();

                break;


        }
    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        finish();
    }

    /**
     * 退出IM
     */
    private void imLogout() {
        DemoHelper.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                showLog("IM 退出", "成功");
            }

            @Override
            public void onProgress(int progress, String status) {
                showLog("IM 退出", "进行");
            }

            @Override
            public void onError(int code, String message) {
                showLog("IM 退出", "失败");
            }
        });
    }
}
