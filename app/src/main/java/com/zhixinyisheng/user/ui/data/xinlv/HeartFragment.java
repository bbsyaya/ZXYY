package com.zhixinyisheng.user.ui.data.xinlv;

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
 * 心率（最新）
 * Created by 焕焕 on 2017/4/21.
 */

public class HeartFragment extends BaseFgt {
    FragmentManager fragmentManager;
    Fragment heartRateFgt, xinLvFgt;
    @Bind(R.id.iv_history_data)
    ImageView ivHistoryData;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_heart;
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
            if (xinLvFgt!=null){
                xinLvFgt.setUserVisibleHint(false);
            }
            if (heartRateFgt!=null){
                heartRateFgt.setUserVisibleHint(false);
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
                if (heartRateFgt == null) {
                    heartRateFgt = new HeartRateFgt();
                    ft.add(R.id.fl_content, heartRateFgt);
                } else {
                    ft.show(heartRateFgt);
                }
                heartRateFgt.setUserVisibleHint(true);
                if (xinLvFgt!=null){
                    xinLvFgt.setUserVisibleHint(false);
                }
                break;
            case "1":
                if (xinLvFgt == null) {
                    xinLvFgt = new HeartRateFragment();
                    ft.add(R.id.fl_content, xinLvFgt);
                } else {
                    ft.show(xinLvFgt);
                }
                xinLvFgt.setUserVisibleHint(true);
                if (heartRateFgt!=null){
                    heartRateFgt.setUserVisibleHint(false);
                }
                break;
        }

        ft.commit();
    }

    public void hideFragment(FragmentTransaction ft) {
        //如果不为空，就先隐藏起来
        if (xinLvFgt != null) {
            ft.hide(xinLvFgt);
        }
        if (heartRateFgt != null) {
            ft.hide(heartRateFgt);
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
//                if (heartRateFgt.getUserVisibleHint()) {
//                    showFragment("1");
//                }else{
//                    showFragment("0");
//                }
                break;
            case R.id.iv_device:
                Content.XVORXY = "0";
                if (TestService.mDevice == null) {
                    TestService.no_shsz = 3;
                    startActivity(BLEActivity.class, null);
                } else {
                    startActivity(XinLvSHAty.class, null);
                }
                break;
            case R.id.iv_handwriting:
                Content.XVORXY = "0";
                startActivity(XinLvHandwritingAty.class, null);
                break;
        }
    }
}
