package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.EaseConstant;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayout;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.FormBotomDialogBuilder;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.DoctorDynamicAdapter2;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.DoctorDynamic;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.domain.doctor.OrderInfo;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.ui.FriendsAty;
import com.zhixinyisheng.user.ui.sidebar.HeadImageDetailAty;
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
 * 医生主页
 */
public class DoctorPageActivity extends BaseAty implements SwipyRefreshLayout.OnRefreshListener, OnSubitemClickListener {
    public final static String EXTRA_DOCTOR_ID = "doctorid";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;

    @Bind(R.id.lv_dynamic)
    RecyclerView lvDynamic;
    @Bind(R.id.srl)
    SwipyRefreshLayout srl;
    @Bind(R.id.btn_consult)
    Button btnConsult;
    private String doctorId, honor, intr, doctorName;
    private int page = 1;

    private DoctorDynamicAdapter2 mDoctorDynamicAdapter2;
    private DoctorHomePage doctorHomePage;
    private DoctorHomePage.UserPdBean docotrInfo;
    // 0好友 1自己是患者 2自己是医生 -2无关系
    private int relationSate = -100;
    String title;
    LoadingDialog mLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_doctor_page;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sIsSaveInfo) {
            loadDoctorInfo();
            return;
        }
        if (IS_AMEND_INFO_DYNAMIC) {
            loadDoctorDynamic();
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userId = UserManager.getUserInfo().getUserId();
        if (UserManager.getUserInfo().getName().equals("")) {
            title = getString(R.string.woshi) + UserManager.getUserInfo().getUsername() + getString(R.string.douhaokuailaizhaowoba);
        } else {
            title = getString(R.string.woshi) + UserManager.getUserInfo().getName() + getString(R.string.douhaokuailaizhaowoba);
        }
        doctorId = getIntent().getStringExtra(EXTRA_DOCTOR_ID);
        lvDynamic.setFocusable(false);
        lvDynamic.setNestedScrollingEnabled(false);
        tvTitleRight.setText(R.string.jubao);

        tvTitleRight.setVisibility(View.VISIBLE);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(Color.parseColor("#25CDBC"));
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }

        initAdapter();
        loadDoctorInfo();
        loadDoctorDynamic();
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvDynamic.setLayoutManager(layoutManager);

        mDoctorDynamicAdapter2 = new DoctorDynamicAdapter2(this);
        lvDynamic.setAdapter(mDoctorDynamicAdapter2);
        mDoctorDynamicAdapter2.setOnSubitemClickListener(this);
    }

    /**
     * 加载医生信息接口
     */
    private void loadDoctorInfo() {
        mLoadingDialog.showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).doctorInfo(phone, secret, userId, doctorId),
                HttpIdentifier.DOCTOR_INFO);
    }

    /**
     * 加载医生动态接口
     */
    private void loadDoctorDynamic() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).doctorDynamic(phone, secret, userId, doctorId,
                HttpIdentifier.PAGECOUNT, String.valueOf(page)), HttpIdentifier.DYNAMIC_DOCTOR);
    }

    @Override
    public void onSuccess(final String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.DOCTOR_INFO://医生信息
                mLoadingDialog.dismiss();
                doctorHomePage = JSON.parseObject(result, DoctorHomePage.class);
                drawDoctorData(doctorHomePage);
                mDoctorDynamicAdapter2.setDoctorInfo(doctorHomePage);
                break;
            case HttpIdentifier.DYNAMIC_DOCTOR://医生动态
                srl.setRefreshing(false);
                DoctorDynamic dynamic = JSON.parseObject(result, DoctorDynamic.class);
                final List<DoctorDynamic.ListBean> dynamics = dynamic.getList();
                if (page == 1) {
                    mDoctorDynamicAdapter2.setData(dynamics);
                    return;
                }
                if (dynamics != null && dynamics.size() != 0) {
                    mDoctorDynamicAdapter2.addData(dynamics);
                } else {
                    showToast(getString(R.string.zanwushuju));
                    page--;
                }
                srl.setRefreshing(false);
                break;
            case HttpIdentifier.CLICK_PRAISE://点赞
                loadDoctorDynamic();
                break;
            case HttpIdentifier.CREATE_ORDER://生成订单
//                OrderInfo orderInfo = JSON.parseObject(result, OrderInfo.class);
                createOrderSuccess();
                break;
        }
    }


    @Override
    public void onFailure(final String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        JsonResponse jsonResponse;
        try {
            jsonResponse = JSON.parseObject(result, JsonResponse.class);
        } catch (Exception e) {
            return;
        }
        switch (what) {
            case HttpIdentifier.CREATE_ORDER:
                final OrderInfo orderInfo = JSON.parseObject(result, OrderInfo.class);
                if ("1052".equals(orderInfo.getResult())) {//生成免费订单成功
                    createOrderSuccess();
                } else if ("1053".equals(orderInfo.getResult())) {//订单已到期，请付费!
//                    showToast(orderInfo.getRetMessage());
                    createOrderSuccess();
                } else {
                    showToast(orderInfo.getRetMessage());
                }
                break;
            default:
                showToast(jsonResponse.getRetMessage());
                break;
        }
    }

    /**
     * 订单生成后的操作
     */
    private void createOrderSuccess() {
        Intent intent = new Intent(this, DoctorChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, doctorId);
        intent.putExtra(EaseConstant.EXTRA_DOCTOR_INFO, docotrInfo);
        startActivity(intent);
        activityAnimation();
    }

    private String headUrl;

    /**
     * 绘制医生数据
     *
     * @param doctorHomePage
     */
    private void drawDoctorData(DoctorHomePage doctorHomePage) {

        docotrInfo = doctorHomePage.getUserPd();
        doctorName = docotrInfo.getName();

        headUrl = docotrInfo.getHeadUrl();

        tvTitle.setText(doctorName + getString(R.string.daifuzhuye));
        intr = docotrInfo.getIntro();
        honor = docotrInfo.getHonor();

        // 0好友 1自己是患者 2自己是医生 -2无关系
        relationSate = docotrInfo.getPayedUserID();
//        Logger.e("关系关系", relationSate + "**");
        if (0 == relationSate || 2 == relationSate) {
            btnConsult.setText(getString(R.string.fasongxiaoxi));
        }
    }

    @OnClick({R.id.btn_consult, R.id.iv_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        sIsSaveInfo = false;
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.tv_title_right:
                intent = new Intent(this, PublishActivity.class);
                intent.putExtra(PublishActivity.EXTRA_IS_PUBLISH_OR_APPEAL, 1);
                intent.putExtra(PublishActivity.EXTRA_DOCTOR_ID, doctorId);
                break;
            case R.id.btn_consult:
                //生成订单接口
                if (docotrInfo == null) {
                    showToast(getString(R.string.yishengxinxihuoqushibai));
                    return;
                }
                // 0好友 1自己是患者 2自己是医生 -2无关系
                if (0 == relationSate || 2 == relationSate) {
                    createOrderSuccess();
                } else {
                    showLoadingDialog(null);
                    String doctorId = docotrInfo.getUserId();
                    doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).createOrder(phone, secret, userId, doctorId),
                            HttpIdentifier.CREATE_ORDER);
                }

        }
        if (intent != null) {
            startActivity(intent);
            activityAnimation();
        }
    }


    /**
     * 分享的弹框
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
                        ShareUtils.shareToQQ(DoctorPageActivity.this, title, UserManager.getUserInfo().getHeadUrl());
                        break;
                    case R.id.ll_share_weixin:
                        ShareUtils.sendToWeiXin(DoctorPageActivity.this, title, UserManager.getUserInfo().getHeadUrl());
                        break;
                    case R.id.ll_share_pengyouquan:
                        ShareUtils.sendToFriends(DoctorPageActivity.this, title, UserManager.getUserInfo().getHeadUrl());
                        break;
                    case R.id.ll_share_zxys:
                        if (docotrInfo == null) {
                            showToast(getString(R.string.shujuweihuoqu));
                            return;
                        }
                        Intent intent = new Intent(DoctorPageActivity.this, FriendsAty.class);
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
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        page++;
        loadDoctorDynamic();
    }

    private int recordClickPos;

    @Override
    public void onSubitemClick(int position, View v) {
        if (position == 0) {
            sIsSaveInfo = false;
            Intent intent = null;
            switch (v.getId()) {
                case R.id.iv_share:
                    shareDialog();
                    break;
                case R.id.tv_introduce2:
                    intent = new Intent(this, EditDoctorInfoActivity.class);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE, "0");
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE_TEXT, intr);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_ID, doctorId);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_NAME, docotrInfo.getName());
                    break;
                case R.id.tv_honor2:
                    intent = new Intent(this, EditDoctorInfoActivity.class);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE, "1");
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_HONOR_INTRODUCE_TEXT, honor);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_ID, doctorId);
                    intent.putExtra(EditDoctorInfoActivity.EXTRA_DOCTOR_NAME, docotrInfo.getName());
                    break;
                case R.id.iv_avatar:
                    ImageView ivAvatar = (ImageView) v;
                    lookBigPic(ivAvatar);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
                activityAnimation();
            }
        } else {
            DoctorDynamic.ListBean bean = mDoctorDynamicAdapter2.getItem(position);
            String dynamicId = bean.getDoctorsInfoId();
            switch (v.getId()) {
                case R.id.rl_ctrl:
                    IS_AMEND_INFO_DYNAMIC = false;
                    Intent intent = new Intent(this, DynamicDetailActivity.class);
                    intent.putExtra(DynamicDetailActivity.EXTRA_ID, dynamicId);
                    intent.putExtra(DynamicDetailActivity.EXTRA_DOCTOR_ID, doctorId);
                    startActivity(intent);
                    activityAnimation();
                    break;
                case R.id.tv_zan:
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).clickPraise(phone, secret, userId, doctorId, dynamicId),
                            HttpIdentifier.CLICK_PRAISE);
                    break;
                case R.id.tv_delete:
                    break;
            }
        }

    }

    private void lookBigPic(ImageView ivAvatar) {
        Intent intent = new Intent(this, HeadImageDetailAty.class);
        int[] location = new int[2];
        ivAvatar.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);
        intent.putExtra("locationY", location[1]);
        intent.putExtra("width", ivAvatar.getWidth());
        intent.putExtra("height", ivAvatar.getHeight());
        intent.putExtra(HeadImageDetailAty.EXTRA_URL, headUrl);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
