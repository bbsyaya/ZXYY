package com.zhixinyisheng.user.ui.data.niaodanbai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas.NdbdlDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Colors;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 尿蛋白定量（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class NiaoDanBaiLabFgt extends BaseFgt{
    @Bind(R.id.tv_niaodanbaidingliang)
    TextView tv_niaodanbaidingliang;//尿蛋白定量
    @Bind(R.id.et_niaodanbaidingliang)
    EditText et_niaodanbaidingliang;//尿蛋白定量输入
    private static String UPVALUEFLAG = "1";
    @Override
    public int getLayoutId() {
        return R.layout.fgt_niaodanbai_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        findData();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_MYQDB_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_MYQDB_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            findData();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            ndbSubmitData();
        }
    };
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            if (myBroadcast != null) {
                getActivity().unregisterReceiver(myBroadcast);
            }
            if (myBroadcast_time != null) {
                getActivity().unregisterReceiver(myBroadcast_time);
            }
        }else{
            registerReceiver();
        }
    }
    /**
     * 获取参数
     */
    private void findData() {
        tv_niaodanbaidingliang.setTextColor(0xff5e5e5e);
        et_niaodanbaidingliang.setText("");
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).ndbDetail(phone, secret, userId, Content.time_lab), 1);
    }
    /**
     * 参数数值提交
     */
    private void ndbSubmitData() {
        if (!et_niaodanbaidingliang.getText().toString().equals("")) {
            Double data = Double.valueOf(et_niaodanbaidingliang.getText().toString());
            if (data > 2) {
                UPVALUEFLAG = "3";
            } else if (data > 0.16 && data <= 2) {
                UPVALUEFLAG = "2";
            }else{
                UPVALUEFLAG = "1";
            }
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(DataDetail.class).ndbdlDatas(phone, secret, userId, Content.time_lab, UPVALUEFLAG,
                    et_niaodanbaidingliang.getText().toString(), LanguageUtil.judgeLanguage()), 0);
        }else{
            showToast(getString(R.string.qingshuruniaodanbaidingliangzhi));
        }

    }
    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:
                try {
                    NdbdlDatas ndbdlDatas = JSON.parseObject(result, NdbdlDatas.class);
                    if (!ndbdlDatas.getList().getUPVALUE().equals("")) {
                        Double data = Double.valueOf(ndbdlDatas.getList().getUPVALUE());
                        if (data >= 0.16 && data <= 2) {
                            tv_niaodanbaidingliang.setTextColor(0xffccb400);
                        } else if (data > 2) {
                            tv_niaodanbaidingliang.setTextColor(0xffff3c3c);
                        }else{
                            tv_niaodanbaidingliang.setTextColor(Colors.textColor);
                        }
                    }
                    et_niaodanbaidingliang.setText(ndbdlDatas.getList().getUPVALUE());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).ndbDetail(phone, secret, userId, Content.time_lab), 1);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }
}
