package com.zhixinyisheng.user.ui.setting;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.update.AppUpdateUtil;
import com.and.yzy.frame.update.UpdateCallBack;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.FormBody;

/**
 * 版本信息
 * Created by 焕焕 on 2016/11/10.
 */
public class VersionAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_versionname)
    TextView tv_versionname;
    String versionName;
    String url = "http://222.222.12.186:8081//api/version/update";//暂时用自动导入系统的测试
    @Override
    public int getLayoutId() {
        return R.layout.aty_version;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.banbenxinxi);
        ivBack.setVisibility(View.VISIBLE);
        try {
            versionName = getPackageManager().getPackageInfo("com.zhixinyisheng.user", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tv_versionname.setText(getString(R.string.banbenanzhuo)+versionName);
    }

    @Override
    public void requestData() {

    }


    @OnClick({R.id.cjs_rlb, R.id.rl_checkversion})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.rl_checkversion:
                FormBody formBody = new FormBody.Builder()
                        .add("version", versionName)
                        .add("zxys_userName",phone)
                        .add("zxys_encrypt",secret)
                        .build();

                AppUpdateUtil updateUtil = new AppUpdateUtil(this, HttpConfig.UPDATE_URL, formBody);

                updateUtil.checkUpdate(new UpdateCallBack() {
                    @Override
                    public void onError() {
                        Looper.prepare();
                        showToast(getString(R.string.fuwuqiyichang));
                        Looper.loop();
                    }

                    @Override
                    public void isUpdate(String result) {

                    }

                    @Override
                    public void isNoUpdate() {
                        Looper.prepare();
                        showToast(getString(R.string.yishizuixinban));
                        Looper.loop();
                    }
                });
                break;
        }
    }
}
