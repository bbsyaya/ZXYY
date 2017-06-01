package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.mydoctor.fragment.DoctorServiceFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 找医生    现在为医生服务
 */
public class FoundDoctorActivity extends BaseAty {

    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    private FragmentManager mFragmentManager;
    private Fragment[] fragments = { new DoctorServiceFragment()};

    @Override
    public int getLayoutId() {
        return R.layout.activity_found_doctor;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText(R.string.yishengfuwu);
        mFragmentManager = getSupportFragmentManager();
        fragments[0].setUserVisibleHint(true);
        mFragmentManager.beginTransaction().add(R.id.ll_control, fragments[0]).commit();
    }


    @Override
    public void requestData() {
    }

    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        this.finish();
    }


}
