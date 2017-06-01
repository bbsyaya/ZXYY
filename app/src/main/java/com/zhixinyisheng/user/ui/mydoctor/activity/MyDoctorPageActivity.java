package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayout;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.FormBotomDialogBuilder;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.DoctorDynamicAdapter2;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.DoctorDynamic;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.ui.FriendsAty;
import com.zhixinyisheng.user.ui.pay.MyIncomeAty;
import com.zhixinyisheng.user.ui.pay.PersonalizedChargeAty;
import com.zhixinyisheng.user.util.ShareUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_AMEND_INFO_DYNAMIC;
import static com.zhixinyisheng.user.config.HttpIdentifier.sIsSaveInfo;

/**
 * 我的医生主页  认证后有
 */
public class MyDoctorPageActivity extends BaseAty implements
        SwipyRefreshLayout.OnRefreshListener, OnSubitemClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;

    @Bind(R.id.lv_dynamic)
    RecyclerView lvDynamic;
    @Bind(R.id.srl)
    SwipyRefreshLayout srl;


    DoctorHomePage doctorHomePage;

    private DoctorDynamicAdapter2 mDoctorDynamicAdapter2;
    private int page = 1;
    private DoctorHomePage.UserPdBean docotrInfo;
    private String intr, honor;
    String title;
    LoadingDialog mLoadingDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_doctor_page;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sIsSaveInfo) {
            loadDoctorInfo();
        }
        if (IS_AMEND_INFO_DYNAMIC) {
            page = 1;
            loadDoctorDynamic();
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userId = UserManager.getUserInfo().getUserId();
        if (UserManager.getUserInfo().getName().equals("")){
            title = "我是" + UserManager.getUserInfo().getUsername() + ",快来找我吧！";
        }else{
            title = "我是" + UserManager.getUserInfo().getName() + ",快来找我吧！";
        }
        lvDynamic.setFocusable(false);
        tvTitle.setText("我的主页");
        tvTitleRight.setText("发布");
        tvTitleRight.setVisibility(View.VISIBLE);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(Color.parseColor("#25CDBC"));
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        initAdapter();
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvDynamic.setLayoutManager(layoutManager);

        mDoctorDynamicAdapter2 = new DoctorDynamicAdapter2(this);
        mDoctorDynamicAdapter2.setMe(true);
        lvDynamic.setAdapter(mDoctorDynamicAdapter2);
        mDoctorDynamicAdapter2.setOnSubitemClickListener(this);
    }

    /**
     * 加载医生信息接口
     */
    private void loadDoctorInfo() {
        mLoadingDialog.showLoadingDialog(null);
        //自己就是医生  所以doctorId也就是userId;
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).doctorInfo(phone, secret, userId, userId),
                HttpIdentifier.DOCTOR_INFO);
    }

    /**
     * 加载医生动态接口
     */
    private void loadDoctorDynamic() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).doctorDynamic(phone, secret, userId, userId,
                HttpIdentifier.PAGECOUNT, String.valueOf(page)), HttpIdentifier.DYNAMIC_DOCTOR);
    }

    /**
     * 分享的弹窗
     */
    private void shareDialog() {
        final FormBotomDialogBuilder builder = new FormBotomDialogBuilder(this);
        View v = getLayoutInflater().inflate(R.layout.dialog_share, null);
        builder.setFB_AddCustomView(v);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_share_qq:
                        ShareUtils.shareToQQ(MyDoctorPageActivity.this, title, UserManager.getUserInfo().getHeadUrl());
                        break;
                    case R.id.ll_share_weixin:
                        ShareUtils.sendToWeiXin(MyDoctorPageActivity.this, title, UserManager.getUserInfo().getHeadUrl());
                        break;
                    case R.id.ll_share_pengyouquan:
                        ShareUtils.sendToFriends(MyDoctorPageActivity.this, title, UserManager.getUserInfo().getHeadUrl());
                        break;
                    case R.id.ll_share_zxys:
                        if (docotrInfo == null) {
                            showToast("数据未获取，请稍后分享");
                            return;
                        }
                        Intent intent = new Intent(MyDoctorPageActivity.this, FriendsAty.class);
                        intent.putExtra(FriendsAty.EXTRA_IS_SHARE, true);
                        intent.putExtra(FriendsAty.EXTRA_MODEL, docotrInfo);
                        startActivity(intent);
                        activityAnimation();
                        break;
                }
                builder.dismiss();
            }
        };

        v.findViewById(R.id.ll_share_qq).setOnClickListener(listener);
        v.findViewById(R.id.ll_share_weixin).setOnClickListener(listener);
        v.findViewById(R.id.ll_share_pengyouquan).setOnClickListener(listener);
        v.findViewById(R.id.ll_share_zxys).setOnClickListener(listener);
        builder.show();
    }


    @Override
    public void requestData() {
        loadDoctorInfo();
        loadDoctorDynamic();
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case HttpIdentifier.DOCTOR_INFO://医生信息
                mLoadingDialog.dismiss();
                doctorHomePage = JSON.parseObject(result, DoctorHomePage.class);
                docotrInfo = doctorHomePage.getUserPd();
                intr = docotrInfo.getIntro();
                honor = docotrInfo.getHonor();
                mDoctorDynamicAdapter2.setDoctorInfo(doctorHomePage);
                break;
            case HttpIdentifier.DYNAMIC_DOCTOR://医生动态
                srl.setRefreshing(false);
                DoctorDynamic dynamic = JSON.parseObject(result, DoctorDynamic.class);
                List<DoctorDynamic.ListBean> dynamics = dynamic.getList();
                if (page == 1) {
                    mDoctorDynamicAdapter2.setData(dynamics);
                    return;
                }
                if (dynamics != null && dynamics.size() != 0) {
                    mDoctorDynamicAdapter2.addData(dynamics);
                } else {
                    showToast("暂无数据");
                    page--;
                }
                break;
            case HttpIdentifier.CLICK_PRAISE://点赞
                loadDoctorDynamic();
                break;
            case HttpIdentifier.DELETE_DOCTOR_DYNAMIC:
                loadDoctorDynamic();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        try {
            JsonResponse response1 = JSON.parseObject(result, JsonResponse.class);
            showToast(response1.getRetMessage());
        } catch (Exception e) {

        }
    }

    @OnClick({R.id.iv_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        Intent intent = null;
        sIsSaveInfo = false;
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.tv_title_right:
                startActivity(PublishActivity.class, null);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            activityAnimation();
        }
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        page++;
        loadDoctorDynamic();
    }


    @Override
    public void onSubitemClick(int position, View v) {
        if (position == 0) {
            sIsSaveInfo = false;
            Intent intent = null;
            switch (v.getId()) {
                case R.id.iv_share:
                    shareDialog();
                    break;
                case R.id.tv_introduce2://医生简介
                    intent = new Intent(this, EditDoctorInfoActivity.class);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE, "0");
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE_TEXT, intr);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_ID, userId);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_NAME, docotrInfo.getName());
                    break;
                case R.id.tv_honor2://荣誉
                    intent = new Intent(this, EditDoctorInfoActivity.class);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE, "1");
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE_TEXT, honor);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_ID, userId);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_NAME, docotrInfo.getName());
                    break;
                case R.id.rl_personalizedcharge:
                    if (docotrInfo.getName().equals("")) {
                        new MaterialDialog(this)
                                .setMDNoTitle(true)
                                .setMDMessage("请您先完善信息！")
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        startActivity(WanShanXinXiAty.class, null);
                                    }
                                })
                                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {

                                    }
                                })
                                .show();
                    } else {
                        startActivity(PersonalizedChargeAty.class, null);
                    }
                    break;
                case R.id.rl_myincome:
                    startActivity(MyIncomeAty.class, null);
                    break;
                case R.id.iv_avatar:
                    startActivity(WanShanXinXiAty.class, null);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
                activityAnimation();
            }
        } else {
            final DoctorDynamic.ListBean bean = mDoctorDynamicAdapter2.getItem(position);
            final String dynamicId = bean.getDoctorsInfoId();
            switch (v.getId()) {
                case R.id.rl_ctrl:
                    IS_AMEND_INFO_DYNAMIC = false;
                    Intent intent = new Intent(this, DynamicDetailActivity.class);
                    intent.putExtra(DynamicDetailActivity.EXTRA_ID, dynamicId);
                    intent.putExtra(DynamicDetailActivity.EXTRA_DOCTOR_ID, userId);
                    startActivity(intent);
                    activityAnimation();
                    break;
                case R.id.tv_zan:
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).clickPraise(phone, secret, userId, docotrInfo.getUserId(), dynamicId),
                            HttpIdentifier.CLICK_PRAISE);
                    break;
                case R.id.iv_delete:
                    new MaterialDialog(this)
                            .setMDNoTitle(true)
                            .setMDMessage("是否删除该动态？")
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    showLoadingDialog(null);
                                    doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).deleteDynamic(phone, secret, userId, dynamicId),
                                            HttpIdentifier.DELETE_DOCTOR_DYNAMIC);
                                }
                            })
                            .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                }
                            })
                            .show();

                    break;
            }
        }

    }


}
