package com.zhixinyisheng.user.ui.data.dianjiezhi;

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
import com.zhixinyisheng.user.domain.datas.DianjzDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 电解质（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class DianJieZhiLabFgt extends BaseFgt{
    @Bind(R.id.tv_dianjiezhi_jia)
    TextView tv_dianjiezhi_jia;//钾
    @Bind(R.id.et_dianjiezhi_jia)
    EditText et_dianjiezhi_jia;//钾输入
    @Bind(R.id.tv_dianjiezhi_na)
    TextView tv_dianjiezhi_na;//钠
    @Bind(R.id.et_dianjiezhi_na)
    EditText et_dianjiezhi_na;//钠输入
    @Bind(R.id.tv_dianjiezhi_lv)
    TextView tv_dianjiezhi_lv;//氯
    @Bind(R.id.et_dianjiezhi_lv)
    EditText et_dianjiezhi_lv;//氯输入
    @Bind(R.id.tv_dianjiezhi_mei)
    TextView tv_dianjiezhi_mei;//镁
    @Bind(R.id.et_dianjiezhi_mei)
    EditText et_dianjiezhi_mei;//镁输入
    @Bind(R.id.tv_dianjiezhi_gai)
    TextView tv_dianjiezhi_gai;//钙
    @Bind(R.id.et_dianjiezhi_gai)
    EditText et_dianjiezhi_gai;//钙输入
    @Bind(R.id.tv_dianjiezhi_lin)
    TextView tv_dianjiezhi_lin;//磷
    @Bind(R.id.et_dianjiezhi_lin)
    EditText et_dianjiezhi_lin;//磷输入
    @Bind(R.id.tv_zongco2)
    TextView tv_zongco2;//总二氧化碳
    @Bind(R.id.et_dianjiezhi_zongeryanghuatan)
    EditText et_dianjiezhi_zongeryanghuatan;//总二氧化碳输入
    @Bind(R.id.tv_yinlizijianxi)
    TextView tv_yinlizijianxi;//阴离子间隙
    @Bind(R.id.et_dianjiezhi_yinlizijianxi)
    EditText et_dianjiezhi_yinlizijianxi;//阴离子间隙输入
    private static String calciumFlag = "1";// 钙色值
    private static String potassiumFlag = "1";//钾色值
    private static String phosphorusFlag = "1";//磷色值
    @Override
    public int getLayoutId() {
        return R.layout.fgt_dianjiezhi_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        getdatas();
    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_DJZ_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_DJZ_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            getdatas();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            submitDatas();
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
     * 获取电解质数据
     */
    private void getdatas(){
        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).djzdatas(phone,secret,userId, Content.time_lab),1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:
                try {
                    getresultdjz(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).djzdatas(phone,secret,userId,Content.time_lab),1);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }
    private void getresultdjz(String pathStr){
        DianjzDatas dianjzDatas = JSON.parseObject(pathStr,DianjzDatas.class);

        if(!dianjzDatas.getDataDB().getPotassium().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getPotassium());
            if((data > 3&&data<3.5)||(data>5.3&&data<=6)){
                tv_dianjiezhi_jia.setTextColor(0xffccb400);
            }else if(data <=3 || data > 6){
                tv_dianjiezhi_jia.setTextColor(0xffff3c3c);
            }else{
                tv_dianjiezhi_jia.setTextColor(0xff5e5e5e);
            }
        }

        if(!dianjzDatas.getDataDB().getSodium().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getSodium());
            if((data >=100&&data<=136)||(data>=148&&data<=160)){
                tv_dianjiezhi_na.setTextColor(0xffccb400);
            }else if(data <100 || data > 160){
                tv_dianjiezhi_na.setTextColor(0xffff3c3c);
            }else{
                tv_dianjiezhi_na.setTextColor(0xff5e5e5e);
            }
        }

        if(!dianjzDatas.getDataDB().getChlorin().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getChlorin());
            if((data >= 85&&data<=98)||(data>=111&&data<=125)){
                tv_dianjiezhi_lv.setTextColor(0xffccb400);
            }else if(data <85 || data > 125){
                tv_dianjiezhi_lv.setTextColor(0xffff3c3c);
            }else{
                tv_dianjiezhi_lv.setTextColor(0xff5e5e5e);
            }
        }

        if(!dianjzDatas.getDataDB().getMagnesium().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getMagnesium());
            if((data >= 0.4&&data<=0.6) || (data >= 1.2 && data <= 2.5)){
                tv_dianjiezhi_mei.setTextColor(0xffccb400);
            }else if(data < 0.4 || data > 1.5){
                tv_dianjiezhi_mei.setTextColor(0xffff3c3c);
            }else{
                tv_dianjiezhi_mei.setTextColor(0xff5e5e5e);
            }
        }

        if(!dianjzDatas.getDataDB().getCalcium().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getCalcium());
            if((data >= 1.95&&data<=2.02) || (data >= 2.55 && data < 2.75)){
                tv_dianjiezhi_gai.setTextColor(0xffccb400);
            }else if(data < 1.95 || data > 2.75){
                tv_dianjiezhi_gai.setTextColor(0xffff3c3c);
            }else{
                tv_dianjiezhi_gai.setTextColor(0xff5e5e5e);
            }
        }

        if(!dianjzDatas.getDataDB().getPhosphorus().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getPhosphorus());
            if((data <= 0.75 && data >= 0.89) || (data >= 1.35&&data<=1.45)){
                tv_dianjiezhi_lin.setTextColor(0xffccb400);
            }else if(data < 0.75 || data > 1.45){
                tv_dianjiezhi_lin.setTextColor(0xffff3c3c);
            }else{
                tv_dianjiezhi_lin.setTextColor(0xff5e5e5e);
            }
        }


        if(!dianjzDatas.getDataDB().getCarbonDioxide().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getCarbonDioxide());
            if((data <= 17 && data >= 21) || (data >= 30&&data<=35)){
                tv_zongco2.setTextColor(0xffccb400);
            }else if(data < 17 || data > 35){
                tv_zongco2.setTextColor(0xffff3c3c);
            }else{
                tv_zongco2.setTextColor(0xff5e5e5e);
            }
        }

        if(!dianjzDatas.getDataDB().getAG().equals("")){
            Double data  = Double.valueOf(dianjzDatas.getDataDB().getAG());
            if((data <= 5 && data >= 7) || (data >= 16&&data<=20)){
                tv_yinlizijianxi.setTextColor(0xffccb400);
            }else if(data < 5 || data > 20){
                tv_yinlizijianxi.setTextColor(0xffff3c3c);
            }else{
                tv_yinlizijianxi.setTextColor(0xff5e5e5e);
            }
        }

        et_dianjiezhi_jia.setText(dianjzDatas.getDataDB().getPotassium());
        et_dianjiezhi_na.setText(dianjzDatas.getDataDB().getSodium());
        et_dianjiezhi_lv.setText(dianjzDatas.getDataDB().getChlorin());
        et_dianjiezhi_mei.setText(dianjzDatas.getDataDB().getMagnesium());
        et_dianjiezhi_gai.setText(dianjzDatas.getDataDB().getCalcium());
        et_dianjiezhi_lin.setText(dianjzDatas.getDataDB().getPhosphorus());
        et_dianjiezhi_zongeryanghuatan.setText(dianjzDatas.getDataDB().getCarbonDioxide());
        et_dianjiezhi_yinlizijianxi.setText(dianjzDatas.getDataDB().getAG());

    }
    /**
     * 提交数据
     */
    private void submitDatas(){

        if(et_dianjiezhi_jia.getText().toString().equals("")){
            showToast(getString(R.string.jiashujubunengweikong));
            return;
        }else {
            Double doub_jia = Double.valueOf(et_dianjiezhi_jia.getText().toString());
            if((doub_jia>3&&doub_jia<3.5)||(doub_jia>5.3&&doub_jia<6)){
                potassiumFlag = "2";
            }else if (doub_jia<=3||doub_jia>6){
                potassiumFlag= "3";
            }
        }

        if(et_dianjiezhi_gai.getText().toString().equals("")){
            showToast(getString(R.string.gaishujubunengweikong));
            return;
        }else {
            Double doub_gai = Double.valueOf(et_dianjiezhi_gai.getText().toString());
            if((doub_gai >= 1.95&&doub_gai<=2.02) || (doub_gai >= 2.55 && doub_gai < 2.75)){
                calciumFlag = "2";
            }else if(doub_gai < 1.95 || doub_gai > 2.75){
                calciumFlag = "3";
            }
        }

        if(et_dianjiezhi_lin.getText().toString().equals("")){
            showToast(getString(R.string.linshujubunengweikong));
            return;
        }else {
            Double doub_lin = Double.valueOf(et_dianjiezhi_lin.getText().toString());
            if((doub_lin <= 0.75 && doub_lin >= 0.89) || (doub_lin >= 1.35&&doub_lin<=1.45)){
                phosphorusFlag = "2";
            }else if(doub_lin < 0.75 || doub_lin > 1.45){
                phosphorusFlag = "3";
            }
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).djzSubmit(phone,secret,userId,Content.time_lab,calciumFlag,potassiumFlag,
                phosphorusFlag,et_dianjiezhi_jia.getText().toString(),et_dianjiezhi_na.getText().toString(),
                et_dianjiezhi_lv.getText().toString(),et_dianjiezhi_mei.getText().toString(),et_dianjiezhi_gai.getText().toString(),
                et_dianjiezhi_lin.getText().toString(),et_dianjiezhi_zongeryanghuatan.getText().toString(),
                et_dianjiezhi_yinlizijianxi.getText().toString(), LanguageUtil.judgeLanguage()),0);
    }
    private void initView() {
        tv_dianjiezhi_jia.setTextColor(0xff5e5e5e);
        tv_dianjiezhi_jia.setTextColor(0xff5e5e5e);
        tv_dianjiezhi_na.setTextColor(0xff5e5e5e);
        tv_dianjiezhi_lv.setTextColor(0xff5e5e5e);
        tv_dianjiezhi_mei.setTextColor(0xff5e5e5e);
        tv_dianjiezhi_gai.setTextColor(0xff5e5e5e);
        tv_dianjiezhi_lin.setTextColor(0xff5e5e5e);
        tv_zongco2.setTextColor(0xff5e5e5e);
        tv_yinlizijianxi.setTextColor(0xff5e5e5e);

        et_dianjiezhi_jia.setText("");
        et_dianjiezhi_na.setText("");
        et_dianjiezhi_lv.setText("");
        et_dianjiezhi_mei.setText("");
        et_dianjiezhi_gai.setText("");
        et_dianjiezhi_lin.setText("");
        et_dianjiezhi_zongeryanghuatan.setText("");
        et_dianjiezhi_yinlizijianxi.setText("");
    }
}
