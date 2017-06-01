package com.zhixinyisheng.user.ui.data.BLE.zhenghe;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.and.yzy.frame.view.switchbutton.SwitchButton;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 手环的提醒设置
 * Created by 焕焕 on 2016/8/13.
 */
public class SHTxszActivity extends BaseAty {
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
    @Bind(R.id.shtxsz_sb_ld)
    SwitchButton shtxszSbLd;
    @Bind(R.id.shtxsz_sb_dx)
    SwitchButton shtxszSbDx;
    @Bind(R.id.shtxsz_sb_qq)
    SwitchButton shtxszSbQq;
    @Bind(R.id.shtxsz_sb_wx)
    SwitchButton shtxszSbWx;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shtxsz;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        TestService.myActivityStack.add(this);
        cjsTvt.setText(R.string.tixingshezhi);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.confirm);
        ivBack.setVisibility(View.VISIBLE);
        shtxszSbLd.setChecked(true);
        shtxszSbDx.setChecked(true);
        shtxszSbQq.setChecked(true);
        shtxszSbWx.setChecked(true);
        initListener();

    }

    private void initListener() {
        shtxszSbLd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {//true为开启
                    TestService.is_ld = true;
//                    MyToast.showToast(SHTxszActivity.this, "开启来电提醒");
                } else {
                    TestService.is_ld = false;
//                    MyToast.showToast(SHTxszActivity.this, "关闭来电提醒");
                }
            }
        });
        shtxszSbDx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {//true为开启
                    TestService.is_dx = true;
//                    MyToast.showToast(SHTxszActivity.this, "开启短信提醒");

                } else {
                    TestService.is_dx = false;
//                    MyToast.showToast(SHTxszActivity.this, "关闭短信提醒");

                }
            }
        });

        shtxszSbQq.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {//true为开启
                    TestService.is_qq = true;
//                    MyToast.showToast(SHTxszActivity.this, "开启QQ提醒");

                } else {
                    TestService.is_qq = false;
//                    MyToast.showToast(SHTxszActivity.this, "关闭QQ提醒");

                }
            }
        });

        shtxszSbWx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {//true为开启
                    TestService.is_wx = true;
//                    MyToast.showToast(SHTxszActivity.this, "开启微信提醒");

                } else {
                    TestService.is_wx = false;
//                    MyToast.showToast(SHTxszActivity.this, "关闭微信提醒");

                }
            }
        });
    }

    @Override
    public void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断基础服务是否打开
        boolean is_jcfw = checkStealFeature1(
                "com.zhixinyisheng.user/com.zhixinyisheng.user.ui.data.BLE.QQ.MyNotificationService");
        if (!is_jcfw) {
//            MyDialogTS mdg_ts = new MyDialogTS(SHTxszActivity.this,"开启知心医生基础服务!",1);
//            mdg_ts.showDialog();

            new MaterialDialog(this)
                    .setMDTitle(getString(R.string.notice))
                    .setMDMessage(getString(R.string.ninbixukaiqizhixinyishengjichufuwu))
                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
                            //开启基础服务
                            Intent intent1 = new Intent(
                                    Settings.ACTION_ACCESSIBILITY_SETTINGS);
                            startActivityForResult(intent1, 0);

                        }
                    })
                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {

                        }
                    })
                    .show();

        }
    }

    //判断基础服务是否打开
    private boolean checkStealFeature1(String service) {
        int ok = 0;
        try {
            ok = Settings.Secure.getInt(getApplicationContext()
                            .getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
        }

        TextUtils.SimpleStringSplitter ms = new TextUtils.SimpleStringSplitter(
                ':');
        if (ok == 1) {
            String settingValue = Settings.Secure.getString(
                    getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                ms.setString(settingValue);
                while (ms.hasNext()) {
                    String accessibilityService = ms.next();
                    Log.e("基础服务是否打开 172", accessibilityService);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TestService.myActivityStack.finish(SHTxszActivity.class);
    }

    @OnClick({R.id.iv_back,R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.title_btn:
                finish();
                break;

        }
    }
}
