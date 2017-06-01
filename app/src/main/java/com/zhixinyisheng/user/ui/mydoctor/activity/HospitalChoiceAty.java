package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.HospitalChoiceAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.domain.doctor.HospitalChoice;
import com.zhixinyisheng.user.http.DoctorUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.view.ClearEditText;
import com.zhixinyisheng.user.view.mydoctor.CityPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 所在医院选择界面
 * Created by 焕焕 on 2017/1/8.
 */
public class HospitalChoiceAty extends BaseAty implements AdapterView.OnItemClickListener,TextWatcher{
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.title_btn_hosptal)
    Button titleBtn;
    @Bind(R.id.et_search_hospital)
    ClearEditText etSearchHospital;
    @Bind(R.id.lv_hospital_choice)
    ListView lvHospitalChoice;
    private CityPicker mCityPicker;
    private HospitalChoiceAdapter hospitalChoiceAdapter;
    HospitalChoice hospitalChoice;
    List<HospitalChoice.ListBean> list_hospital = new ArrayList<HospitalChoice.ListBean>();
    List<HospitalChoice.ListBean> list_hospital_changed = new ArrayList<HospitalChoice.ListBean>();
    private String pro,ci;
    @Override
    public int getLayoutId() {
        return R.layout.aty_hospitalchoice;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText("石家庄");
        cjsTvt.setText("选择医院");
        ivBack.setVisibility(View.VISIBLE);
        pro = UserManager.getUserInfo().getProvince();
        ci = UserManager.getUserInfo().getCity();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorUrl.class).searchHospital(phone, secret, "河北","石家庄"), HttpIdentifier.SEARCH_HOSPITAL);
        etSearchHospital.addTextChangedListener(this);
        //注意此Aty的EditText要用苑亚欣的 ************************************************
        try {
            hospitalChoiceAdapter = new HospitalChoiceAdapter(this,list_hospital,R.layout.item_hospital_choice);
            lvHospitalChoice.setAdapter(hospitalChoiceAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lvHospitalChoice.setOnItemClickListener(this);

    }

    @OnClick({R.id.title_btn_hosptal, R.id.cjs_rlb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_btn_hosptal:
                if (mCityPicker == null) {
                    mCityPicker = new CityPicker(this, findViewById(R.id.rl_container))
                            .setOnCitySelectListener(new CityPicker.OnCitySelectListener() {
                                @Override
                                public void onCitySelect(String province, String city) {
                                    pro = province;
                                    ci = city;
                                    titleBtn.setText(city);
                                    showLoadingDialog(null);
                                    doHttp(RetrofitUtils.createApi(DoctorUrl.class).searchHospital(phone, secret, province,city), HttpIdentifier.SEARCH_HOSPITAL);
                                }
                            });
                }
                mCityPicker.show();
                break;
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
        }

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case HttpIdentifier.SEARCH_HOSPITAL:
                list_hospital.clear();
                hospitalChoice = JSON.parseObject(result,HospitalChoice.class);
                list_hospital.addAll(hospitalChoice.getList());
                hospitalChoiceAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        if (list_hospital_changed.size()==0){
            intent.putExtra("hospitalName", list_hospital.get(position).getName());
            intent.putExtra("hospitalId", list_hospital.get(position).getHospitalId());
        }else{
            intent.putExtra("hospitalName", list_hospital_changed.get(position).getName());
            intent.putExtra("hospitalId", list_hospital_changed.get(position).getHospitalId());
        }
        this.setResult(0, intent);
        UserInfo userInfo = UserManager.getUserInfo();
        userInfo.setProvince(pro);
        userInfo.setCity(ci);
        UserManager.setUserInfo(userInfo);

        hideSoftKeyboard();
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        list_hospital_changed.clear();
        for (int i = 0; i < list_hospital.size(); i++) {
            if (list_hospital.get(i).getName().contains(s.toString())){
                list_hospital_changed.add(list_hospital.get(i));
            }
        }
        hospitalChoiceAdapter = new HospitalChoiceAdapter(this,list_hospital_changed,R.layout.item_hospital_choice);
        lvHospitalChoice.setAdapter(hospitalChoiceAdapter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
