package com.zhixinyisheng.user.ui.data.xueya;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.util.Content;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 血压（最新）
 * Created by 焕焕 on 2017/4/21.
 */

public class BloodPressureFragment extends BaseFgt {
    FragmentManager fragmentManager;
    Fragment bloodPressureFgt, floodPressureFragment;
    @Bind(R.id.iv_history_data)
    ImageView ivHistoryData;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_heart;//跟心率一样，没必要再建
    }

    @Override
    public void initData() {
        fragmentManager = getChildFragmentManager();
        showFragment("0");
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (floodPressureFragment != null) {
                floodPressureFragment.setUserVisibleHint(false);
            }
            if (bloodPressureFgt != null) {
                bloodPressureFgt.setUserVisibleHint(false);
            }
        }
    }
    /**
     * 解决Fragment切换溢出问题
     */
    public void showFragment(String index) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        hideFragment(ft);
        switch (index) {
            case "0":
                if (bloodPressureFgt == null) {
                    bloodPressureFgt = new BloodPressureFgt();
                    ft.add(R.id.fl_content, bloodPressureFgt);
                } else {
                    ft.show(bloodPressureFgt);
                }
                bloodPressureFgt.setUserVisibleHint(true);
                if (floodPressureFragment != null) {
                    floodPressureFragment.setUserVisibleHint(false);
                }
                break;
            case "1":
                if (floodPressureFragment == null) {
                    floodPressureFragment = new FloodPressureFragment();
                    ft.add(R.id.fl_content, floodPressureFragment);
                } else {
                    ft.show(floodPressureFragment);
                }
                floodPressureFragment.setUserVisibleHint(true);
                if (bloodPressureFgt != null) {
                    bloodPressureFgt.setUserVisibleHint(false);
                }
                break;
        }

        ft.commit();
    }

    public void hideFragment(FragmentTransaction ft) {
        //如果不为空，就先隐藏起来
        if (floodPressureFragment != null) {
            ft.hide(floodPressureFragment);
        }
        if (bloodPressureFgt != null) {
            ft.hide(bloodPressureFgt);
        }

    }
    private boolean flag;
    @OnClick({R.id.iv_history_data, R.id.iv_device, R.id.iv_handwriting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_history_data:
                if (!flag){
                    ivHistoryData.setBackgroundResource(R.drawable.zice);
                    showFragment("1");
                    flag=true;
                }else{
                    ivHistoryData.setBackgroundResource(R.drawable.iv_zhexiantu);
                    showFragment("0");
                    flag=false;
                }
//                if (bloodPressureFgt.getUserVisibleHint()) {
//                    showFragment("1");
//                } else {
//                    showFragment("0");
//                }
                break;
            case R.id.iv_device:
                Content.XVORXY = "1";
                if (TestService.mDevice == null) {
                    TestService.no_shsz = 4;
                    startActivity(BLEActivity.class, null);
                } else {
                    startActivity(XueYaSHAty.class, null);
                }
                break;
            case R.id.iv_handwriting:
                Content.XVORXY = "1";
                startActivity(XueYaHandwritingAty.class, null);
                break;
        }
    }
}
