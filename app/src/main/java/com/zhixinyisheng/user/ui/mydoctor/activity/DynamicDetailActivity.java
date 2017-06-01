package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.ImageAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.DynamicDetail;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.DateUtils;
import com.zhixinyisheng.user.util.GlideUtil;
import com.zhixinyisheng.user.view.MyGridView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_AMEND_INFO_DYNAMIC;

/**
 * 动态详情
 */
public class DynamicDetailActivity extends BaseAty implements AdapterView.OnItemClickListener{
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_DOCTOR_ID = "extra_doctor_id";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_doctor)
    TextView tvDoctor;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.gv_pic)
    MyGridView gvPic;
    @Bind(R.id.tv_zan)
    TextView tvZan;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    private ImageAdapter mImageAdapter;

    private String dynamicId,doctorId,picUrl;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userId=UserManager.getUserInfo().getUserId();
        tvTitle.setText(R.string.xiangqing);
        mImageAdapter = new ImageAdapter(this);
        gvPic.setAdapter(mImageAdapter);
        dynamicId = getIntent().getStringExtra(EXTRA_ID);
        doctorId = getIntent().getStringExtra(EXTRA_DOCTOR_ID);
        userId= UserManager.getUserInfo().getUserId();
        if (doctorId == userId) {
            tvDelete.setVisibility(View.VISIBLE);
        } else {
            tvDelete.setVisibility(View.GONE);
        }
        gvPic.setOnItemClickListener(this);
    }

    @Override
    public void requestData() {
        loadData();
    }

    private void loadData() {
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).doctorDynamicDetail(phone, secret, userId, dynamicId),
                HttpIdentifier.DYNAMIC_DOCTOR_DETAIL);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.DYNAMIC_DOCTOR_DETAIL:

                DynamicDetail dynamicDetail = JSON.parseObject(result, DynamicDetail.class);
                DynamicDetail.DbBean bean = dynamicDetail.getDb();
                loadViewData(bean);
                break;
            case HttpIdentifier.CLICK_PRAISE:
                loadData();
                IS_AMEND_INFO_DYNAMIC = true;
                break;
            case HttpIdentifier.DELETE_DOCTOR_DYNAMIC:
                IS_AMEND_INFO_DYNAMIC = true;
                this.finish();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        showToast(result);
    }

    /**
     * view 赋值
     *
     * @param bean
     */
    private void loadViewData(DynamicDetail.DbBean bean) {
        tvDoctor.setText(bean.getName()+"\t大夫");

        long createTime = bean.getCreatetime() / (long) 1000;
        String strTime = DateUtils.convertTimeToFormat(createTime);
        tvTime.setText(strTime);

        GlideUtil.loadCircleAvatar(this, bean.getHeadUrl(), ivAvatar);
        tvContent.setText(bean.getContent());
        int isZan=bean.getIsZan();
        if(isZan==1){
            tvZan.setSelected(true);
        }else{
            tvZan.setSelected(false);
        }
        tvZan.setText(String.valueOf(bean.getZan()));


        picUrl = bean.getPicUrl();
        if (picUrl != null && !"".equals(picUrl)) {
            String[] arrayStr = picUrl.split(",");
            List<String> stringList = Arrays.asList(arrayStr);
            mImageAdapter.setData(stringList);
        }
    }

    @OnClick({R.id.iv_title_left, R.id.tv_zan, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_zan:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).clickPraise(phone, secret, userId, doctorId, dynamicId),
                        HttpIdentifier.CLICK_PRAISE);
                break;
            case R.id.tv_delete:
                doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).deleteDynamic(phone, secret, userId, dynamicId),
                        HttpIdentifier.DELETE_DOCTOR_DYNAMIC);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,LookBigPicActivity.class);
        intent.putExtra(LookBigPicActivity.EXTRA_LIST,picUrl);
        startActivity(intent);
        activityAnimation();
    }
}
