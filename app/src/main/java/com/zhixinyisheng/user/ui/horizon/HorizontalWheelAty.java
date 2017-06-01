package com.zhixinyisheng.user.ui.horizon;

import android.os.Bundle;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.view.HorizontalWheelRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 水平选择器
 * Created by 焕焕 on 2017/3/4.
 */
public class HorizontalWheelAty extends BaseAty {
    @Bind(R.id.wheel_province)
    HorizontalWheelRecyclerView mProvinceWheel;
    List<String> list  = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.aty_horizontal_wheel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list.add("111");
        list.add("222");
        list.add("333");
        list.add("444");
        list.add("555");
        list.add("666");
        list.add("777");
        list.add("888");
        mProvinceWheel.setData(list);

    }
}
