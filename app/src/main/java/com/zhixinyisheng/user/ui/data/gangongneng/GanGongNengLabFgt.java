package com.zhixinyisheng.user.ui.data.gangongneng;

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
import com.zhixinyisheng.user.domain.datas.GgnDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 肝功能（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class GanGongNengLabFgt extends BaseFgt{
    @Bind(R.id.tv_gubingzhuananmei)
    TextView tv_gubingzhuananmei;//谷丙转氨酶
    @Bind(R.id.et_gubingzhuananmei)
    EditText et_gubingzhuananmei;//谷丙转氨酶输入
    @Bind(R.id.tv_gubingxianjizhuananmei)
    TextView tv_gubingxianjizhuananmei;//谷丙酰基转氨酶
    @Bind(R.id.et_gubingxianjizhuananmei)
    EditText et_gubingxianjizhuananmei;//谷丙酰基转氨酶输入
    @Bind(R.id.tv_gucaozhuananmei)
    TextView tv_gucaozhuananmei;//谷草转氨酶
    @Bind(R.id.et_gucaozhuananmei)
    EditText et_gucaozhuananmei;//谷草转氨酶输入
    @Bind(R.id.tv_zongdanbai)
    TextView tv_zongdanbai;//总蛋白
    @Bind(R.id.et_zongdanbai)
    EditText et_zongdanbai;//总蛋白输入
    @Bind(R.id.tv_baidanbai)
    TextView tv_baidanbai;//白蛋白
    @Bind(R.id.et_baidanbai)
    EditText et_baidanbai;//白蛋白输入
    @Bind(R.id.tv_qiudanbai)
    TextView tv_qiudanbai;//球蛋白
    @Bind(R.id.et_qiudanbai)
    EditText et_qiudanbai;//球蛋白输入
    @Bind(R.id.tv_zongdanhongsu)
    TextView tv_zongdanhongsu;//总胆红素
    @Bind(R.id.et_zongdanhongsu)
    EditText et_zongdanhongsu;//总胆红素输入
    @Bind(R.id.tv_ganyousanzhi)
    TextView tv_ganyousanzhi;//甘油三酯
    @Bind(R.id.et_ganyousanzhi)
    EditText et_ganyousanzhi;//甘油三酯输入
    @Bind(R.id.tv_zongdanguchun)
    TextView tv_zongdanguchun;//总胆固醇
    @Bind(R.id.et_zongdanguchun)
    EditText et_zongdanguchun;//总胆固醇输入
    @Bind(R.id.tv_gaomiduzhidanbai)
    TextView tv_gaomiduzhidanbai;//高密度脂蛋白
    @Bind(R.id.et_gaomiduzhidanbai)
    EditText et_gaomiduzhidanbai;//高密度脂蛋白输入
    private static String ALTFLAG = "1";//谷丙转氨酶色值 1:黑 2：黄3：红
    @Override
    public int getLayoutId() {
        return R.layout.fgt_gangongneng_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        getggnDatas();
    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_GGN_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_GGN_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            getggnDatas();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            ggnSubmitDatas();
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

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:
                try {
                    GgnDatas ggndatas = JSON.parseObject(result,GgnDatas.class);
                    if(!ggndatas.getList().getALT().equals("")){
                        Double gbzam_num = Double.valueOf(ggndatas.getList().getALT());
                        if(gbzam_num>40){
                            tv_gubingzhuananmei.setTextColor(0xffccb400);
                        }else{
                            tv_gubingzhuananmei.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getPAT().equals("")){
                        Double gbxjzam_num = Double.valueOf(ggndatas.getList().getPAT());
                        if(gbxjzam_num>50){
                            tv_gubingxianjizhuananmei.setTextColor(0xffccb400);
                        }else{
                            tv_gubingxianjizhuananmei.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getAST().equals("")){
                        Double gczam_num = Double.valueOf(ggndatas.getList().getAST());
                        if(gczam_num>40){
                            tv_gucaozhuananmei.setTextColor(0xffccb400);
                        }else{
                            tv_gucaozhuananmei.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getTPO().equals("")){
                        Double zdb_num = Double.valueOf(ggndatas.getList().getTPO());
                        if(zdb_num>83||(zdb_num>=40&&zdb_num<60)){
                            tv_zongdanbai.setTextColor(0xffccb400);
                        }else if(zdb_num<40){
                            tv_zongdanbai.setTextColor(0xffff3c3c);
                        }else{
                            tv_zongdanbai.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getRICIM().equals("")){
                        Double bdb_num = Double.valueOf(ggndatas.getList().getRICIM());
                        if(bdb_num>56||(bdb_num>=25&&bdb_num<35)){
                            tv_baidanbai.setTextColor(0xffccb400);
                        }else if(bdb_num<25){
                            tv_baidanbai.setTextColor(0xffff3c3c);
                        }else{
                            tv_baidanbai.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getGLOBULOSE().equals("")){
                        Double qdb_num = Double.valueOf(ggndatas.getList().getGLOBULOSE());
                        if(qdb_num<20||qdb_num>35){
                            tv_qiudanbai.setTextColor(0xffccb400);
                        }else{
                            tv_qiudanbai.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getTBIL().equals("")){
                        Double zdhs_num  = Double.valueOf(ggndatas.getList().getTBIL());
                        if(zdhs_num>22){
                            tv_zongdanhongsu.setTextColor(0xffccb400);
                        }else{
                            tv_zongdanhongsu.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getTRIGLYCERIDE().equals("")){
                        Double gysz_num = Double.valueOf(ggndatas.getList().getTRIGLYCERIDE());
                        if(gysz_num<0.56||gysz_num>1.71){
                            tv_ganyousanzhi.setTextColor(0xffccb400);
                        }else{
                            tv_ganyousanzhi.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getCHOLESTEROL().equals("")){
                        Double zdgc_num = Double.valueOf(ggndatas.getList().getCHOLESTEROL());
                        if(zdgc_num<0.92||zdgc_num>5.72){
                            tv_zongdanguchun.setTextColor(0xffccb400);
                        }else{
                            tv_zongdanguchun.setTextColor(0xff5e5e5e);
                        }
                    }

                    if(!ggndatas.getList().getHDL().equals("")){
                        Double gmdzdb_num = Double.valueOf(ggndatas.getList().getHDL());
                        if(gmdzdb_num<0.8){
                            tv_gaomiduzhidanbai.setTextColor(0xffccb400);
                        }else{
                            tv_gaomiduzhidanbai.setTextColor(0xff5e5e5e);
                        }
                    }

                    et_gubingzhuananmei.setText(ggndatas.getList().getALT());
                    et_gubingxianjizhuananmei.setText(ggndatas.getList().getPAT());
                    et_gucaozhuananmei.setText(ggndatas.getList().getAST());
                    et_zongdanbai.setText(ggndatas.getList().getTPO());
                    et_baidanbai.setText(ggndatas.getList().getRICIM());
                    et_qiudanbai.setText(ggndatas.getList().getGLOBULOSE());
                    et_zongdanhongsu.setText(ggndatas.getList().getTBIL());
                    et_ganyousanzhi.setText(ggndatas.getList().getTRIGLYCERIDE());
                    et_zongdanguchun.setText(ggndatas.getList().getCHOLESTEROL());
                    et_gaomiduzhidanbai.setText(ggndatas.getList().getHDL());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).ggndatas(phone,secret,userId,Content.time_lab),1);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }

    /**
     *获取数据
     */
    private void getggnDatas(){
        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).ggndatas(phone,secret,userId, Content.time_lab),1);
    }
    /**
     * 提交数据
     */
    private void ggnSubmitDatas(){
        if(et_gubingzhuananmei.getText().toString().equals("")){
            showToast(getString(R.string.qingninshurugubingzhuananmeizhi));
            return;
        }else {
            Double doub_gbzam = Double.valueOf(et_gubingzhuananmei.getText().toString());
            if(doub_gbzam>=0&&doub_gbzam<=40){
                ALTFLAG = "1";
            }else if(doub_gbzam>40){
                ALTFLAG = "3";
            }
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).ggnSubmit(phone,secret,userId, Content.time_lab,ALTFLAG,
                et_gubingzhuananmei.getText().toString(),et_gubingxianjizhuananmei.getText().toString(),
                et_gucaozhuananmei.getText().toString(),et_zongdanbai.getText().toString(),
                et_baidanbai.getText().toString(),et_qiudanbai.getText().toString(),
                et_zongdanhongsu.getText().toString(),et_ganyousanzhi.getText().toString(),
                et_zongdanguchun.getText().toString(),et_gaomiduzhidanbai.getText().toString(), LanguageUtil.judgeLanguage()),0);
    }
    private void initView() {
        et_gubingzhuananmei.setText("");
        et_gubingxianjizhuananmei.setText("");
        et_gucaozhuananmei.setText("");
        et_zongdanbai.setText("");
        et_baidanbai.setText("");
        et_qiudanbai.setText("");
        et_zongdanhongsu.setText("");
        et_ganyousanzhi.setText("");
        et_zongdanguchun.setText("");
        et_gaomiduzhidanbai.setText("");

        tv_gubingzhuananmei.setTextColor(0xff5e5e5e);
        tv_gubingxianjizhuananmei.setTextColor(0xff5e5e5e);
        tv_gucaozhuananmei.setTextColor(0xff5e5e5e);
        tv_zongdanbai.setTextColor(0xff5e5e5e);
        tv_baidanbai.setTextColor(0xff5e5e5e);
        tv_qiudanbai.setTextColor(0xff5e5e5e);
        tv_zongdanhongsu.setTextColor(0xff5e5e5e);
        tv_ganyousanzhi.setTextColor(0xff5e5e5e);
        tv_zongdanguchun.setTextColor(0xff5e5e5e);
        tv_gaomiduzhidanbai.setTextColor(0xff5e5e5e);
    }
}
