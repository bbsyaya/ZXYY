package com.zhixinyisheng.user.view.mydoctor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.view.dialog.WheelRecyclerView;
import com.zhixinyisheng.user.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 城市选择器
 * Created by 焕焕 on 2017/1/8.
 */
public class CityPicker implements PopupWindow.OnDismissListener, View.OnClickListener{
    private OnCitySelectListener mOnCitySelectListener;
    private PopupWindow mPickerWindow;

    private View mParent;

    private WheelRecyclerView mProvinceWheel;

    private WheelRecyclerView mCityWheel;
    private Activity mContext;
    private List<Province> mDatas;
    public CityPicker(Activity context, View parent) {
        mContext = context;
        mParent = parent;
        //初始化选择器
        View pickerView = LayoutInflater.from(context).inflate(R.layout.city_picker, null);
        mProvinceWheel = (WheelRecyclerView) pickerView.findViewById(R.id.wheel_province);
        mCityWheel = (WheelRecyclerView) pickerView.findViewById(R.id.wheel_city);
        pickerView.findViewById(R.id.tv_exit).setOnClickListener(this);
        pickerView.findViewById(R.id.tv_ok).setOnClickListener(this);

        mPickerWindow = new PopupWindow(pickerView, ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPickerWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mPickerWindow.setFocusable(true);
        mPickerWindow.setAnimationStyle(R.style.CityPickerAnim);
        mPickerWindow.setOnDismissListener(this);

        initData();
    }
    /**
     * 从assets下读取文本
     * @param context
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(Context context, String fileName){
        String result = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            result = new String(buffer,"utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 初始化城市数据
     */
    private void initData() {
        mDatas = JSON.parseArray(getTextFromAssets(mContext, "city.json"),Province.class);

        mProvinceWheel.setData(getProvinceNames());
        mCityWheel.setData(getCityNames(0));
//        mCountyWheel.setData(getCountyNames(0, 0));

        mProvinceWheel.setOnSelectListener(new WheelRecyclerView.OnSelectListener() {
            @Override
            public void onSelect(int position, String data) {
                onProvinceWheelRoll(position);
            }
        });
        mCityWheel.setOnSelectListener(new WheelRecyclerView.OnSelectListener() {
            @Override
            public void onSelect(int position, String data) {
//                onCityWheelRoll(position);
            }
        });
    }
    private void onProvinceWheelRoll(int position) {
        mCityWheel.setData(getCityNames(position));
//        mCountyWheel.setData(getCountyNames(position, 0));
    }

//    private void onCityWheelRoll(int position) {
//        mCountyWheel.setData(getCountyNames(mProvinceWheel.getSelected(), position));
//    }
    /**
     * 获取省份名称列表
     *
     * @return
     */
    private List<String> getProvinceNames() {
        List<String> provinces = new ArrayList<>();
        for (Province province : mDatas) {
            provinces.add(province.getAreaName());
        }
        return provinces;
    }
    /**
     * 获取某个省份的城市名称列表
     *
     * @param provincePos
     * @return
     */
    private List<String> getCityNames(int provincePos) {
        List<String> cities = new ArrayList<>();
        for (Province.CitiesBean city : mDatas.get(provincePos).getCities()) {
            cities.add(city.getAreaName());
        }
        return cities;
    }
    /**
     * 弹出Window时使背景变暗
     *
     * @param alpha
     */
    private void backgroundAlpha(float alpha) {
        Window window = mContext.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }

    public CityPicker setOnCitySelectListener(OnCitySelectListener listener) {
        mOnCitySelectListener = listener;
        return this;
    }

    public void show() {
        backgroundAlpha(0.8f);
        mPickerWindow.showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                if (mOnCitySelectListener != null) {
                    Province province = mDatas.get(mProvinceWheel.getSelected());
                    Province.CitiesBean city = province.getCities().size() > 0 ? province.getCities().get(mCityWheel.getSelected()) : null;
                    String provinceName = province.getAreaName();
                    String cityName = city == null ? "" : city.getAreaName();
//                    String countyName = city == null ? "" : city.getCounties().get(mCountyWheel.getSelected()).getAreaName();
                    mOnCitySelectListener.onCitySelect(provinceName, cityName);
                    mPickerWindow.dismiss();
                }
                break;
            case R.id.tv_exit:
                mPickerWindow.dismiss();
                break;
        }
    }


    public interface OnCitySelectListener {
        void onCitySelect(String province, String city);
    }
}
