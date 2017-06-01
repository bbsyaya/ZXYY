package com.zhixinyisheng.user.ui.data.shengongneng;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas.SgnDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 焕焕 on 2017/2/24.
 */
public class ShenGongNengLabFgt extends BaseFgt {
    @Bind(R.id.tv_niaosudan)
    TextView tv_niaosudan;//尿素氮
    @Bind(R.id.et_niaosudan)
    EditText et_niaosudan;//尿素氮输入框
    @Bind(R.id.tv_jigan)
    TextView tv_jigan;//肌酐
    @Bind(R.id.et_jigan)
    EditText et_jigan;//肌酐输入框
    @Bind(R.id.tv_niaosuan)
    TextView tv_niaosuan;//尿酸
    @Bind(R.id.et_niaosuan)
    EditText et_niaosuan;//尿酸输入框
    @Bind(R.id.tv_2weiqiudanbai)
    TextView tv_2weiqiudanbai;//β2微球蛋白
    @Bind(R.id.et_weiqiudanbai)
    EditText et_weiqiudanbai;//β2微球蛋白输入框
    @Bind(R.id.tv_guangyisu)
    TextView tv_guangyisu;//胱抑素C
    @Bind(R.id.et_guangyisu)
    EditText et_guangyisu;//胱抑素C输入框
    @Bind(R.id.tv_tongxingbanguangansuan)
    TextView tv_tongxingbanguangansuan;//同型半胱氨酸
    @Bind(R.id.et_tongxingbanguangansuan)
    EditText et_tongxingbanguangansuan;//同型半胱氨酸输入框
    private static String UREANITROGENFLAG = "1";//尿素氮色值 1:黑 2：黄3：红
    private static String CREATININEFLAG = "1";//肌酐色值 1:黑 2：黄3：红
    @Override
    public int getLayoutId() {
        return R.layout.fgt_shengongneng_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        getsgndatas();
    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_SGN_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_SGN_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            getsgndatas();
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
     * 获得数据
     */
    private void getsgndatas() {

        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).sgndatas(phone, secret, userId, Content.time_lab), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:
                try {
                    SgnDatas sgndatas = JSON.parseObject(result, SgnDatas.class);
                    Log.e("result","result-->"+result);
                    if (!sgndatas.getList().getUREANITROGEN().equals("")) {
                        Double nsd_num = Double.valueOf(sgndatas.getList().getUREANITROGEN());
                        if (nsd_num >= 8.3 && nsd_num <= 19) {
                            tv_niaosudan.setTextColor(0xffccb400);
                        } else if (nsd_num < 2.9 || nsd_num > 19) {
                            tv_niaosudan.setTextColor(0xffff3c3c);
                        }else{
                            tv_niaosudan.setTextColor(0xff5e5e5e);
                        }
                    }

                    if (!sgndatas.getList().getCREATININE().equals("")) {
                        Double jg_num = Double.valueOf(sgndatas.getList().getCREATININE());
                        if (jg_num >= 107 && jg_num <= 442) {
                            tv_jigan.setTextColor(0xffccb400);
                        } else if (jg_num > 442 || jg_num < 44) {
                            tv_jigan.setTextColor(0xffff3c3c);
                        }else{
                            tv_jigan.setTextColor(0xff5e5e5e);
                        }
                    }

                    if (!sgndatas.getList().getUA().equals("")) {
                        Double ns_num = Double.valueOf(sgndatas.getList().getUA());
                        if (ns_num > 428 || ns_num < 208) {
                            tv_niaosuan.setTextColor(0xffccb400);
                        }else{
                            tv_niaosuan.setTextColor(0xff5e5e5e);
                        }
                    }

                    if (!sgndatas.getList().getMICROGLOBULIN().equals("")) {
                        Double wqdb_num = Double.valueOf(sgndatas.getList().getMICROGLOBULIN());
                        if (wqdb_num < 0 || wqdb_num > 2.7) {
                            tv_2weiqiudanbai.setTextColor(0xffccb400);
                        }else{
                            tv_2weiqiudanbai.setTextColor(0xff5e5e5e);
                        }
                    }

                    if (!sgndatas.getList().getCYSTATINC().equals("")) {
                        Double ygs_num = Double.valueOf(sgndatas.getList().getCYSTATINC());
                        if (ygs_num > 1.03 || ygs_num < 0.59) {
                            tv_guangyisu.setTextColor(0xffccb400);
                        }else{
                            tv_guangyisu.setTextColor(0xff5e5e5e);
                        }
                    }

                    if (!sgndatas.getList().getHCY().equals("")) {
                        Double txbgas_num = Double.valueOf(sgndatas.getList().getHCY());
                        if (txbgas_num > 20) {
                            tv_tongxingbanguangansuan.setTextColor(0xffccb400);
                        }else{
                            tv_tongxingbanguangansuan.setTextColor(0xff5e5e5e);
                        }
                    }
                    et_niaosudan.setText(sgndatas.getList().getUREANITROGEN());
                    et_jigan.setText(sgndatas.getList().getCREATININE());
                    et_niaosuan.setText(sgndatas.getList().getUA());
                    et_weiqiudanbai.setText(sgndatas.getList().getMICROGLOBULIN());
                    et_guangyisu.setText(sgndatas.getList().getCYSTATINC());
                    et_tongxingbanguangansuan.setText(sgndatas.getList().getHCY());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).sgndatas(phone, secret, userId, Content.time_lab), 1);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }

    /**
     * 提交数据
     */
    private void submitDatas() {
        if (et_niaosudan.getText().toString().equals("")) {
            showToast(getString(R.string.qingtianxieniaosudan));
            return;
        } else {
            Double doub_nsd = Double.valueOf(et_niaosudan.getText().toString());
            if (doub_nsd >= 8.3 && doub_nsd <= 19) {
                UREANITROGENFLAG = "2";
            } else if (doub_nsd < 2.9 || doub_nsd > 19) {
                UREANITROGENFLAG = "3";
            }
        }

        if (et_jigan.getText().toString().equals("")) {
            showToast(getString(R.string.qingtianxieniaosudan));
            return;
        } else {
            Double doub_jg = Double.valueOf(et_jigan.getText().toString());
            if (doub_jg >= 107 && doub_jg <= 442) {
                CREATININEFLAG = "2";
            } else if (doub_jg < 44 || doub_jg > 442) {
                CREATININEFLAG = "3";
            }
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).sgnSubmit(phone, secret, userId, Content.time_lab, UREANITROGENFLAG, CREATININEFLAG,
                et_niaosudan.getText().toString(), et_jigan.getText().toString(), et_niaosuan.getText().toString(),
                et_weiqiudanbai.getText().toString(), et_guangyisu.getText().toString(), et_tongxingbanguangansuan.getText().toString(), LanguageUtil.judgeLanguage()), 0);
    }
    private void initView() {
        et_niaosudan.setText("");
        et_jigan.setText("");
        et_niaosuan.setText("");
        et_weiqiudanbai.setText("");
        et_guangyisu.setText("");
        et_tongxingbanguangansuan.setText("");

        tv_niaosudan.setTextColor(0xff5e5e5e);
        tv_jigan.setTextColor(0xff5e5e5e);
        tv_niaosuan.setTextColor(0xff5e5e5e);
        tv_2weiqiudanbai.setTextColor(0xff5e5e5e);
        tv_guangyisu.setTextColor(0xff5e5e5e);
        tv_tongxingbanguangansuan.setTextColor(0xff5e5e5e);
    }
}
