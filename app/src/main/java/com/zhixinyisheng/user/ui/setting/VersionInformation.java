package com.zhixinyisheng.user.ui.setting;

import android.os.Bundle;
import android.view.View;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.OnClick;

/**
 * 版本信息
 * Created by gjj on 2016/10/21.
 */
public class VersionInformation extends BaseAty{
    @Override
    public int getLayoutId() {
        return R.layout.versioninfoemation;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.check_new_version,R.id.opinion_feedback})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()){
            case R.id.check_new_version:
                break;
            case R.id.opinion_feedback:
                break;
        }


    }
}
