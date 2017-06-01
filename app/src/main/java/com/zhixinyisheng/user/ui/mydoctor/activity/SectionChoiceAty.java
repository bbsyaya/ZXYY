package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.JuniorSectionChoiceAdapter;
import com.zhixinyisheng.user.adapter.doctor.SectionChoiceAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.doctor.SectionChoice;
import com.zhixinyisheng.user.http.DoctorUrl;
import com.zhixinyisheng.user.ui.BaseAty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 选择科室(新)
 * Created by 焕焕 on 2017/1/24.
 */
public class SectionChoiceAty extends BaseAty implements AdapterView.OnItemClickListener {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.lv_major)
    ListView lvMajor;
    @Bind(R.id.lv_junior)
    ListView lvJunior;
    SectionChoiceAdapter majorAdapter;
    JuniorSectionChoiceAdapter juniorAdapter;
    List<SectionChoice.ListBean> list_major = new ArrayList<>();
    List<SectionChoice.ListBean> list_juior = new ArrayList<>();
    SectionChoice sectionChoice;
    String major, junior, sectionId;

    @Override
    public int getLayoutId() {
        return R.layout.aty_departmentchoice;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("选择科室");
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText("确定");
        lvMajor.setOnItemClickListener(this);
        lvJunior.setOnItemClickListener(this);
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorUrl.class).sectionList(phone, secret), HttpIdentifier.SEARCH_SECTION_LIST);
        try {
            majorAdapter = new SectionChoiceAdapter(this, list_major, R.layout.item_section_choice);
            lvMajor.setAdapter(majorAdapter);

            juniorAdapter = new JuniorSectionChoiceAdapter(this, list_juior, R.layout.item_section_choice);
            lvJunior.setAdapter(juniorAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        sectionChoice = JSON.parseObject(result, SectionChoice.class);
        for (int i = 0; i < sectionChoice.getList().size(); i++) {
            if (sectionChoice.getList().get(i).getIsRoot() == 1) {
                list_major.add(sectionChoice.getList().get(i));
            }
        }
        majorAdapter.setSelectedPosition(0);
        majorAdapter.notifyDataSetChanged();
        for (int i = 0; i < sectionChoice.getList().size(); i++) {
            if (sectionChoice.getList().get(i).getIsRoot() == 0) {
                if (sectionChoice.getList().get(i).getLastId().equals(list_major.get(0).getSectionId())) {
                    list_juior.add(sectionChoice.getList().get(i));
                }
            }
        }
        juniorAdapter.setSelectedPosition(0);
        juniorAdapter.notifyDataSetChanged();
        sectionId = list_juior.get(0).getSectionId();
    }


    @OnClick({R.id.title_btn, R.id.cjs_rlb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_btn:
                Intent intent = new Intent();
                if (major==null){
                    major = list_major.get(0).getName();
                }
                if (junior==null){
                    junior = list_juior.get(0).getName();
                }
                intent.putExtra("section", major+" "+junior);
                intent.putExtra("sectionId", sectionId);
                this.setResult(0, intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_major:
                list_juior.clear();
                majorAdapter.setSelectedPosition(position);
                juniorAdapter.setSelectedPosition(0);
                for (int i = 0; i < sectionChoice.getList().size(); i++) {
                    if (sectionChoice.getList().get(i).getIsRoot() == 0) {
                        if (sectionChoice.getList().get(i).getLastId().equals(list_major.get(position).getSectionId())) {
                            list_juior.add(sectionChoice.getList().get(i));
                        }
                    }
                }

                majorAdapter.notifyDataSetChanged();
                juniorAdapter.notifyDataSetChanged();
                major = list_major.get(position).getName();
                break;
            case R.id.lv_junior:
                juniorAdapter.setSelectedPosition(position);
                juniorAdapter.notifyDataSetChanged();
                junior = list_juior.get(position).getName();
                sectionId = list_juior.get(position).getSectionId();
                break;
        }
    }
}
