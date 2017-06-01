package com.zhixinyisheng.user.ui.data.mianyiqiudanbai;

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
import com.zhixinyisheng.user.domain.datas.MyqdbDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 免疫球蛋白（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class MianYiQiuDanBaiLabFgt extends BaseFgt{
    @Bind(R.id.tv_mianyiqiudanbai_g)
    TextView tv_mianyiqiudanbai_g;//免疫球蛋白G
    @Bind(R.id.et_mianyiqiudanbai_g)
    EditText et_mianyiqiudanbai_g;//免疫球蛋白G输入框
    @Bind(R.id.tv_mianyiqiudanbai_a)
    TextView tv_mianyiqiudanbai_a;//免疫球蛋白A
    @Bind(R.id.et_mianyiqiudanbai_a)
    EditText et_mianyiqiudanbai_a;//免疫球蛋白A输入框
    @Bind(R.id.tv_mianyiqiudanbai_m)
    TextView tv_mianyiqiudanbai_m;//免疫球蛋白M
    @Bind(R.id.et_mianyiqiudanbai_m)
    EditText et_mianyiqiudanbai_m;//免疫球蛋白M输入框
    @Bind(R.id.tv_mianyiqiudanbai_k)
    TextView tv_mianyiqiudanbai_k;//免疫球蛋白K型轻链
    @Bind(R.id.et_mianyiqiudanbai_k)
    EditText et_mianyiqiudanbai_k;//免疫球蛋白K型轻链输入框
    @Bind(R.id.tv_mianyiqiudanbai_12)
    TextView tv_mianyiqiudanbai_12;//免疫球蛋白λ型轻链
    @Bind(R.id.et_mianyiqiudanbai_12)
    EditText et_mianyiqiudanbai_12;//免疫球蛋白λ型轻链输入框
    @Bind(R.id.tv_butic1q)
    TextView tv_butic1q;//补体C1q
    @Bind(R.id.et_butiC1q)
    EditText et_butiC1q;//补体C1q输入框
    @Bind(R.id.tv_butibyinzi)
    TextView tv_butibyinzi;//补体B因子
    @Bind(R.id.et_butiByinzi)
    EditText et_butiByinzi;//补体B因子输入框
    @Bind(R.id.tv_zongutich50)
    TextView tv_zongutich50;//总补体CH50
    @Bind(R.id.et_zongbutich50)
    EditText et_zongbutich50;//总补体CH50输入框
    @Bind(R.id.tv_butic3)
    TextView tv_butic3;//补体C3
    @Bind(R.id.et_butic3)
    EditText et_butic3;//补体C3输入框
    @Bind(R.id.tv_butic4)
    TextView tv_butic4;//补体C4
    @Bind(R.id.et_butic4)
    EditText et_butic4;//补体C4输入框
    @Bind(R.id.tv_c_fanyindanbai)
    TextView tv_c_fanyindanbai;//C-反应蛋白
    @Bind(R.id.et_c_fanyindanbai)
    EditText et_c_fanyindanbai;//C-反应蛋白输入框
    @Override
    public int getLayoutId() {
        return R.layout.fgt_mianyiqiudanbai_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        getmydatas();
    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_NDB_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_NDB_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            getmydatas();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            submitdatas();
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
    private void getmydatas() {
        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).myqdbdatas(phone, secret, userId, Content.time_lab), 1);
    }
    /**
     * 提交数据
     */
    private void submitdatas() {
        if (et_mianyiqiudanbai_g.getText().toString().equals("")
                &&et_mianyiqiudanbai_a.getText().toString().equals("")
                &&et_mianyiqiudanbai_m.getText().toString().equals("")
                &&et_mianyiqiudanbai_k.getText().toString().equals("")
                &&et_mianyiqiudanbai_12.getText().toString().equals("")
                &&et_butiC1q.getText().toString().equals("")
                &&et_butiByinzi.getText().toString().equals("")
                &&et_zongbutich50.getText().toString().equals("")
                &&et_butic3.getText().toString().equals("")
                &&et_butic4.getText().toString().equals("")
                &&et_c_fanyindanbai.getText().toString().equals("")){
            showToast(getString(R.string.qingzhishaotianxieyitiaoshuju));
        }else{
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(DataDetail.class).myqdbSubmit(phone, secret, userId, Content.time_lab,
                    et_mianyiqiudanbai_g.getText().toString(), et_mianyiqiudanbai_a.getText().toString(),
                    et_mianyiqiudanbai_m.getText().toString(), et_mianyiqiudanbai_k.getText().toString(),
                    et_mianyiqiudanbai_12.getText().toString(), et_butiC1q.getText().toString(), et_butiByinzi.getText().toString(),
                    et_zongbutich50.getText().toString(), et_butic3.getText().toString(), et_butic4.getText().toString(),
                    et_c_fanyindanbai.getText().toString(), LanguageUtil.judgeLanguage()), 0);
        }

    }
    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:
                try {
                    MyqdbDatas myqdbdatas = JSON.parseObject(result, MyqdbDatas.class);

                    if (!myqdbdatas.getList().getIGG().equals("")) {
                        Double icc_num = Double.valueOf(myqdbdatas.getList().getIGG());
                        if (icc_num < 7 || icc_num > 16) {
                            tv_mianyiqiudanbai_g.setTextColor(0xffccb400);
                        }else{
                            tv_mianyiqiudanbai_g.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getIGA().equals("")) {
                        Double ica_num = Double.valueOf(myqdbdatas.getList().getIGA());
                        if (ica_num < 0.7 || ica_num > 4) {
                            tv_mianyiqiudanbai_a.setTextColor(0xffccb400);
                        }else{
                            tv_mianyiqiudanbai_a.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getIGM().equals("")) {
                        Double icm_num = Double.valueOf(myqdbdatas.getList().getIGM());
                        if (icm_num < 0.4 || icm_num > 2.3) {
                            tv_mianyiqiudanbai_m.setTextColor(0xffccb400);
                        }else{
                            tv_mianyiqiudanbai_m.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getIGK().equals("")) {
                        Double ick_num = Double.valueOf(myqdbdatas.getList().getIGK());
                        if (ick_num < 1.7 || ick_num > 3.7) {
                            tv_mianyiqiudanbai_k.setTextColor(0xffccb400);
                        }else{
                            tv_mianyiqiudanbai_k.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getIGR().equals("")) {
                        Double icr_num = Double.valueOf(myqdbdatas.getList().getIGR());
                        if (icr_num < 0.9 || icr_num > 2.1) {
                            tv_mianyiqiudanbai_12.setTextColor(0xffccb400);
                        }else{
                            tv_mianyiqiudanbai_12.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getCOQ().equals("")) {
                        Double coq_num = Double.valueOf(myqdbdatas.getList().getCOQ());
                        if (coq_num < 159 || coq_num > 233) {
                            tv_butic1q.setTextColor(0xffccb400);
                        }else{
                            tv_butic1q.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getBTB().equals("")) {
                        Double btb_num = Double.valueOf(myqdbdatas.getList().getBTB());
                        if (btb_num < 100 || btb_num > 400) {
                            tv_butibyinzi.setTextColor(0xffccb400);
                        }else{
                            tv_butibyinzi.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getCH50().equals("")) {
                        Double ch50_num = Double.valueOf(myqdbdatas.getList().getCH50());
                        if (ch50_num < 31 || ch50_num > 54) {
                            tv_zongutich50.setTextColor(0xffccb400);
                        }else{
                            tv_zongutich50.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getBTC3().equals("")) {
                        Double btc3_num = Double.valueOf(myqdbdatas.getList().getBTC3());
                        if (btc3_num < 0.9 || btc3_num > 1.8) {
                            tv_butic3.setTextColor(0xffccb400);
                        }else{
                            tv_butic3.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getBTC4().equals("")) {
                        Double btc4_num = Double.valueOf(myqdbdatas.getList().getBTC4());
                        if (btc4_num < 0.1 || btc4_num > 0.4) {
                            tv_butic4.setTextColor(0xffccb400);
                        }else{
                            tv_butic4.setTextColor(0xff5e5e5e);
                        }
                    }
                    if (!myqdbdatas.getList().getCFYDB().equals("")) {
                        Double cfydb_num = Double.valueOf(myqdbdatas.getList().getCFYDB());
                        if (cfydb_num < 0 || cfydb_num > 8.2) {
                            tv_c_fanyindanbai.setTextColor(0xffff3c3c);
                        } else if (cfydb_num > 3 && cfydb_num <= 8.2) {
                            tv_c_fanyindanbai.setTextColor(0xffccb400);
                        }else{
                            tv_c_fanyindanbai.setTextColor(0xff5e5e5e);
                        }
                    }

                    et_mianyiqiudanbai_g.setText(myqdbdatas.getList().getIGG());
                    et_mianyiqiudanbai_a.setText(myqdbdatas.getList().getIGA());
                    et_mianyiqiudanbai_m.setText(myqdbdatas.getList().getIGM());
                    et_mianyiqiudanbai_k.setText(myqdbdatas.getList().getIGK());
                    et_mianyiqiudanbai_12.setText(myqdbdatas.getList().getIGR());
                    et_butiC1q.setText(myqdbdatas.getList().getCOQ());
                    et_butiByinzi.setText(myqdbdatas.getList().getBTB());
                    et_zongbutich50.setText(myqdbdatas.getList().getCH50());
                    et_butic3.setText(myqdbdatas.getList().getBTC3());
                    et_butic4.setText(myqdbdatas.getList().getBTC4());
                    et_c_fanyindanbai.setText(myqdbdatas.getList().getCFYDB());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).myqdbdatas(phone, secret, userId, Content.time_lab), 1);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }

    private void initView() {
        et_mianyiqiudanbai_g.setText("");
        et_mianyiqiudanbai_a.setText("");
        et_mianyiqiudanbai_m.setText("");
        et_mianyiqiudanbai_k.setText("");
        et_mianyiqiudanbai_12.setText("");
        et_butiC1q.setText("");
        et_butiByinzi.setText("");
        et_zongbutich50.setText("");
        et_butic3.setText("");
        et_butic4.setText("");
        et_c_fanyindanbai.setText("");

        tv_mianyiqiudanbai_g.setTextColor(0xff5e5e5e);
        tv_mianyiqiudanbai_a.setTextColor(0xff5e5e5e);
        tv_mianyiqiudanbai_m.setTextColor(0xff5e5e5e);
        tv_mianyiqiudanbai_k.setTextColor(0xff5e5e5e);
        tv_mianyiqiudanbai_12.setTextColor(0xff5e5e5e);
        tv_butic1q.setTextColor(0xff5e5e5e);
        tv_butibyinzi.setTextColor(0xff5e5e5e);
        tv_zongutich50.setTextColor(0xff5e5e5e);
        tv_butic3.setTextColor(0xff5e5e5e);
        tv_butic4.setTextColor(0xff5e5e5e);
        tv_c_fanyindanbai.setTextColor(0xff5e5e5e);
    }
}
