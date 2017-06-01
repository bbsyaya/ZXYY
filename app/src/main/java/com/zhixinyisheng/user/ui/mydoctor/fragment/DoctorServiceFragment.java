package com.zhixinyisheng.user.ui.mydoctor.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.EaseConstant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.MyServiceAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.domain.doctor.Service;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.interfaces.OnSubitemTouchListener;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorChatActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.EvaluateDoctorActivity;

import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorServiceFragment extends BaseFgt implements
        OnSubitemClickListener, OnSubitemTouchListener {

    @Bind(R.id.lv_service)
    ListView lvService;
    @Bind(R.id.rl_no_result)
    RelativeLayout rlNoResult;


    private MyServiceAdapter mMyServiceAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_doctor_service;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void initData() {
        initAdapter();
        showLoadingDialog(null);
        loadData();
    }


    private void initAdapter() {
        mMyServiceAdapter = new MyServiceAdapter(getActivity());
        lvService.setAdapter(mMyServiceAdapter);
        mMyServiceAdapter.setOnSubitemClickListener(this);
        mMyServiceAdapter.setOnSubitemTouchListener(this);
    }

    /**
     * 加载我的服务的网络数据
     */
    private void loadData() {
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).serviceList(phone, secret, userId),
                HttpIdentifier.DOCTOR_SERVICE);
    }

    private void deleteOrder(String payId) {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).deleteOrder(phone, secret, payId),
                HttpIdentifier.DELETE_ORDER);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case HttpIdentifier.DOCTOR_SERVICE:
                Service service = JSON.parseObject(result, Service.class);
                List<Service.ListBean> beanList = service.getList();
                if (beanList != null && beanList.size() != 0) {
                    mMyServiceAdapter.setData(beanList);
                } else {
                    rlNoResult.setVisibility(View.VISIBLE);
                    lvService.setVisibility(View.GONE);
                }
                break;
            case HttpIdentifier.DELETE_ORDER:
                loadData();
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

    @Override
    public void onSubitemClick(int position, View v) {
        Service.ListBean bean = mMyServiceAdapter.getItem(position);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_ctrl:
                intent = new Intent(getActivity(), DoctorChatActivity.class);
                String toUserId = bean.getToUserId();
                intent.putExtra(EaseConstant.EXTRA_USER_ID, toUserId);
                intent.putExtra(DoctorChatActivity.EXTRA_DOCTOR_NAME, bean.getName());
                intent.putExtra(DoctorChatActivity.EXTRA_RELATION_STATE, 1);
                startActivity(intent);
                activityAnimation();
                break;
            case R.id.tv_state:
                int isAppraise = bean.getIsAppraise();
                if (0 == isAppraise) {
                    intent = new Intent(getActivity(), EvaluateDoctorActivity.class);
                    intent.putExtra(EvaluateDoctorActivity.EXTRA_ORDER_ID, bean.getPayRecordId());
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, bean.getToUserId());
                    startActivity(intent);
                    activityAnimation();
                } else {
                    showToast("该订单已评价");
                }
                break;

        }

    }

    @Override
    public void onSubitemLongClick(int position, View v) {
        final Service.ListBean bean = mMyServiceAdapter.getItem(position);
        new MaterialDialog(getActivity())
                .setMDNoTitle(true)
                .setMDMessage("是否删除该服务？")
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        deleteOrder(bean.getPayRecordId());
                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                    }
                })
                .show();
    }
}
