package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.util.Toolkit;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.DepartmentAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.http.DoctorUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.view.mydoctor.Province;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 选择科室
 * Created by 焕焕 on 2017/1/9.
 */
public class DepartmentChoiceAty extends BaseAty implements AdapterView.OnItemClickListener {
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
    @Bind(R.id.main_unread_msg_number)
    TextView mainUnreadMsgNumber;
    @Bind(R.id.cjs_rl_xhd)
    RelativeLayout cjsRlXhd;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.title_btn_hosptal)
    Button titleBtnHosptal;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title_line)
    View titleLine;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.lv_major)
    ListView lvMajor;
    @Bind(R.id.lv_junior)
    ListView lvJunior;
    private List<Province> mDatas;//先获取假数据
    private List<String> majorList = new ArrayList<>();//大部门数据
    private List<String> juniorList = new ArrayList<>();//小部门数据
    private DepartmentAdapter departmentAdapter_major;
    private DepartmentAdapter departmentAdapter_junior;

    @Override
    public int getLayoutId() {
        return R.layout.aty_departmentchoice;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("选择科室");
        doHttp(RetrofitUtils.createApi(DoctorUrl.class).sectionList(phone,secret), HttpIdentifier.SEARCH_SECTION_LIST);

        mDatas = JSON.parseArray(Toolkit.getTextFromAssets(this, "city.json"), Province.class);
        majorList = getProvinceNames();
        juniorList = getCityNames(0);
        departmentAdapter_major = new DepartmentAdapter(this, majorList, R.layout.item_area);
        departmentAdapter_junior = new DepartmentAdapter(this, juniorList, R.layout.item_area);
        lvMajor.setAdapter(departmentAdapter_major);
        lvJunior.setAdapter(departmentAdapter_junior);
        lvMajor.setOnItemClickListener(this);
        lvJunior.setOnItemClickListener(this);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        Logger.e("keshi",result);
    }

    /**
     * 获取大部门列表
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
     * 获取某个大部门的小部门名称列表
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

    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()) {
            case R.id.lv_major:
                juniorList = getCityNames(position);
                departmentAdapter_junior = new DepartmentAdapter(this, juniorList, R.layout.item_area);
                lvJunior.setAdapter(departmentAdapter_junior);
                break;
            case R.id.lv_junior:
                break;
        }
    }
}
