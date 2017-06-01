package com.zhixinyisheng.user.ui.data.ningxuejiance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas.NxueDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 凝血（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class NingXueLabFgt extends BaseFgt {
    @Bind(R.id.ningxue_rl1_tv1)
    TextView ningxue_rl1_tv1;//凝血酶时间
    @Bind(R.id.ningxue_rl1_tv2)
    EditText ningxue_rl1_tv2;//凝血酶时间
    @Bind(R.id.ningxue_rl2_tv1)
    TextView ningxue_rl2_tv1;//凝血酶原时间
    @Bind(R.id.ningxue_rl2_tv2)
    EditText ningxue_rl2_tv2;//凝血酶原时间
    @Bind(R.id.ningxue_rl3_tv1)
    TextView ningxue_rl3_tv1;//纤维蛋白原
    @Bind(R.id.ningxue_rl3_tv2)
    EditText ningxue_rl3_tv2;//纤维蛋白原
    @Bind(R.id.ningxue_rl4_tv1)
    TextView ningxue_rl4_tv1;//活化部分凝血活酶时间
    @Bind(R.id.ningxue_rl4_tv2)
    EditText ningxue_rl4_tv2;//活化部分凝血活酶时间
    @Bind(R.id.ningxue_rl5_tv1)
    TextView ningxue_rl5_tv1;//血浆D-二聚体测定
    @Bind(R.id.ningxue_rl5_tv2)
    EditText ningxue_rl5_tv2;//血浆D-二聚体测定
    private static String colorFlag = "1";//* 1:黑 2：黄3：红

    private String colorf1="1", colorf2="1", colorf3="1", colorf4="1", colorf5="1";
    @Override
    public int getLayoutId() {
        return R.layout.fgt_ningxue_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        getDatas();
    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_NX_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_NX_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            getDatas();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            nxjcSubmitDatas();
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
     * 获取数据
     */
    private void getDatas(){
        ningxue_rl1_tv2.setText("");
        ningxue_rl2_tv2.setText("");
        ningxue_rl3_tv2.setText("");
        ningxue_rl4_tv2.setText("");
        ningxue_rl5_tv2.setText("");
        ningxue_rl1_tv1.setTextColor(0xff5e5e5e);
        ningxue_rl2_tv1.setTextColor(0xff5e5e5e);
        ningxue_rl3_tv1.setTextColor(0xff5e5e5e);
        ningxue_rl4_tv1.setTextColor(0xff5e5e5e);
        ningxue_rl5_tv1.setTextColor(0xff5e5e5e);
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).nxjcdatas(phone,secret,userId, Content.time_lab),1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).nxjcdatas(phone,secret,userId,Content.time_lab),1);
                showToast(getString(R.string.tijiaochenggong));
                break;
            case 1:
                try {
                    NxueDatas xuedatas  = JSON.parseObject(result,NxueDatas.class);
                    if(!xuedatas.getDataDB().getTt().equals("")){
                        Double data = Double.valueOf(xuedatas.getDataDB().getTt());
                        if((data >=0&&data<12)||(data>16&&data<=22)){
                            ningxue_rl1_tv1.setTextColor(0xffccb400);
                        }else if(data>22){
                            ningxue_rl1_tv1.setTextColor(0xffff3c3c);
                        }else{
                            ningxue_rl1_tv1.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!xuedatas.getDataDB().getPt().equals("")){
                        Double data = Double.valueOf(xuedatas.getDataDB().getPt());
                        if((data >=8&&data<11)||(data>14&&data<=17)){
                            ningxue_rl2_tv1.setTextColor(0xffccb400);
                        }else if(data <8 || data > 17){
                            ningxue_rl2_tv1.setTextColor(0xffff3c3c);
                        }else{
                            ningxue_rl2_tv1.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!xuedatas.getDataDB().getFib().equals("")){
                        Double data = Double.valueOf(xuedatas.getDataDB().getFib());
                        if((data >=1.5&&data<2)||(data>4&&data<=5)){
                            ningxue_rl3_tv1.setTextColor(0xffccb400);
                        }else if(data <1.5 || data > 5){
                            ningxue_rl3_tv1.setTextColor(0xffff3c3c);
                        }else{
                            ningxue_rl3_tv1.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!xuedatas.getDataDB().getAptt().equals("")){
                        Double data = Double.valueOf(xuedatas.getDataDB().getFib());
                        if((data >=15&&data<25)||(data>37&&data<=47)){
                            ningxue_rl4_tv1.setTextColor(0xffccb400);
                        }else if(data <15 || data > 47){
                            ningxue_rl4_tv1.setTextColor(0xffff3c3c);
                        }else{
                            ningxue_rl4_tv1.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!xuedatas.getDataDB().getDd().equals("")){
                        Double data = Double.valueOf(xuedatas.getDataDB().getFib());
                        if((data >0.5&&data<=1)){
                            ningxue_rl5_tv1.setTextColor(0xffccb400);
                        }else if(data> 1){
                            ningxue_rl5_tv1.setTextColor(0xffff3c3c);
                        }else{
                            ningxue_rl5_tv1.setTextColor(0xff5e5e5e);
                        }
                    }
                    ningxue_rl1_tv2.setText(xuedatas.getDataDB().getTt());
                    ningxue_rl2_tv2.setText(xuedatas.getDataDB().getPt());
                    ningxue_rl3_tv2.setText(xuedatas.getDataDB().getFib());
                    ningxue_rl4_tv2.setText(xuedatas.getDataDB().getAptt());
                    ningxue_rl5_tv2.setText(xuedatas.getDataDB().getDd());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 提交数据
     */
    private void nxjcSubmitDatas(){
        if (colorf1.equals("3") || colorf2.equals("3")
                || colorf3.equals("3") || colorf4.equals("3")
                || colorf5.equals("3")) {
            colorFlag = "3";
        } else if (colorf1.equals("1") && colorf2.equals("1")
                && colorf3.equals("1") && colorf4.equals("1")
                && colorf5.equals("1")) {
            colorFlag = "1";
        } else {
            colorFlag = "2";
        }
        Logger.e(phone+"---"+secret+"---"+userId+"---"+Content.NowDATA+"---"+colorFlag+"---"+
                ningxue_rl1_tv2.getText().toString()+"---"+ningxue_rl2_tv2.getText().toString()+"---"+
                ningxue_rl3_tv2.getText().toString()+"---"+ningxue_rl4_tv2.getText().toString()+"---"+ningxue_rl5_tv2.getText().toString());
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).nxjcSubmit(phone,secret,userId, Content.time_lab,colorFlag,
                ningxue_rl1_tv2.getText().toString(),ningxue_rl2_tv2.getText().toString(),
                ningxue_rl3_tv2.getText().toString(),ningxue_rl4_tv2.getText().toString(),ningxue_rl5_tv2.getText().toString(), LanguageUtil.judgeLanguage()),0);
    }
}
