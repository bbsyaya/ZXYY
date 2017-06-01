package com.zhixinyisheng.user.ui.mydoctor.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.view.listview.ListViewForScrollView;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.DoctorDynamicAdapter;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医生发布动态
 */
public class PublishInfoActivity extends BaseAty implements AdapterView.OnItemClickListener{

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.lv_dynamic)
    ListViewForScrollView lvDynamic;

    private DoctorDynamicAdapter mDoctorDynamicAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_dynamic;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        lvDynamic.setFocusable(false);
        tvTitle.setText("我的发布");
        tvTitleRight.setText("发布");
        tvTitleRight.setVisibility(View.VISIBLE);
        initAdapter();
    }

    private void initAdapter() {
        mDoctorDynamicAdapter = new DoctorDynamicAdapter(this);
        mDoctorDynamicAdapter.setMe(true);
        lvDynamic.setAdapter(mDoctorDynamicAdapter);
        lvDynamic.setOnItemClickListener(this);
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.iv_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.tv_title_right:
                Intent intent = new Intent(this, PublishActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DynamicDetailActivity.class);
        startActivity(intent);
    }
}
