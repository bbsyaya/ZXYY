package com.zhixinyisheng.user.ui.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.EaseConstant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.pay.PaySuccessAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.Doctor;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.domain.doctor.OrderInfo;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorChatActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 支付成功
 * Created by 焕焕 on 2017/1/9.
 */
public class PaySuccessAty extends BaseAty implements AdapterView.OnItemClickListener {
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
    @Bind(R.id.lv_paysuccess)
    ListView lvPaysuccess;
    PaySuccessAdapter paySuccessAdapter;
    Doctor doctor;
    List<Doctor.ListBean> doctorInfo;
    String doctorId, doctorName;
    int positonNow;
    String payingDoctorId="1";
    private DoctorHomePage.UserPdBean docotrInfo;
    @Override
    public int getLayoutId() {
        return R.layout.aty_paysuccess;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText(R.string.zhifujieguo);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.finish);
        lvPaysuccess.setOnItemClickListener(this);
        Bundle bundle = getIntent().getExtras();
        try {
            payingDoctorId = bundle.getString("payingDoctorId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).recommendDoctor(phone, secret, userId,
                HttpIdentifier.PAGECOUNT, "1", UserManager.getUserInfo().getProvince(), UserManager.getUserInfo().getCity(), payingDoctorId),
                HttpIdentifier.RECOMMEND_DOCTOR);

    }
    /**
     * 加载医生信息接口
     */
    private void loadDoctorInfo() {
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).doctorInfo(phone, secret, userId, payingDoctorId),
                HttpIdentifier.DOCTOR_INFO);
    }
    @Override
    public void onSuccess(final String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.RECOMMEND_DOCTOR://查询医生
                Logger.e("PaySuccess", result);
                doctor = JSON.parseObject(result, Doctor.class);
                doctorInfo = doctor.getList();
                paySuccessAdapter = new PaySuccessAdapter(PaySuccessAty.this, doctor.getList(), R.layout.item_paysuccess);
                lvPaysuccess.setAdapter(paySuccessAdapter);
                showLoadingDialog(null);
                loadDoctorInfo();
                break;
            case HttpIdentifier.CREATE_ORDER://生成订单
                OrderInfo orderInfo = JSON.parseObject(result, OrderInfo.class);
                createOrderSuccess(positonNow);


//                showLoadingDialog(null);
//                DemoHelper.getInstance().init(this);//初始化好友
//                DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
//                    @Override
//                    public void friendsLoaded() {
//                        dismissLoadingDialog();
//                        OrderInfo orderInfo = JSON.parseObject(result, OrderInfo.class);
//                        createOrderSuccess(orderInfo,positonNow);
//                    }
//                });

                break;
            case HttpIdentifier.DOCTOR_INFO:
                DoctorHomePage  doctorHomePage = JSON.parseObject(result, DoctorHomePage.class);
                docotrInfo = doctorHomePage.getUserPd();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
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
                    createOrderSuccess(positonNow);

//                    showLoadingDialog(null);
//                    DemoHelper.getInstance().init(PaySuccessAty.this);
//                    DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
//                        @Override
//                        public void friendsLoaded() {
//                            dismissLoadingDialog();
//                            //1016已经是好友
//                            createOrderSuccess(orderInfo,positonNow);
//                        }
//                    });

                } else {
                    showToast(orderInfo.getRetMessage());
                }
                break;
            default:
                showToast(jsonResponse.getRetMessage());
                break;
        }
    }

    @OnClick({R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_btn:
                Intent intent = new Intent(this, DoctorChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, payingDoctorId);
                intent.putExtra(EaseConstant.EXTRA_DOCTOR_INFO, docotrInfo);
                startActivity(intent);
                activityAnimation();
                finish();
                break;
        }
    }

    /**
     * 订单生成后的操作
     */
    private void createOrderSuccess(int position) {
        Intent intent = new Intent(this, DoctorChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, doctorId);
        intent.putExtra(DoctorChatActivity.EXTRA_DOCTOR_NAME, doctorName);
//        intent.putExtra(EaseConstant.EXTRA_BUY_NUM, getIntent().getStringExtra(EaseConstant.EXTRA_BUY_NUM));
        intent.putExtra(EaseConstant.EXTRA_DOCTOR_INFO, doctorInfo.get(position));
        startActivity(intent);
        activityAnimation();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        doctorId = doctor.getList().get(position).getId();
        doctorName = doctor.getList().get(position).getName();
        positonNow = position;
        // 0好友 1自己是患者 2自己是医生 -2无关系
        if (0 == doctor.getList().get(position).getPayedUserID() || 2 == doctor.getList().get(position).getPayedUserID()) {
            createOrderSuccess(position);
        } else {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).createOrder(phone, secret, userId, doctorId),
                    HttpIdentifier.CREATE_ORDER);
        }
    }
}
