package com.zhixinyisheng.user.ui.data.shengzangcaichao;

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
import com.zhixinyisheng.user.domain.datas.SzccDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 肾脏彩超（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class ShenZangCaiChaoLabFgt extends BaseFgt{
    @Bind(R.id.shenzang_rl1_tv1)
    TextView shenzang_rl1_tv1;//左肾体积
    @Bind(R.id.shenzang_rl1_ed)
    EditText shenzang_rl1_ed;//左肾——长
    @Bind(R.id.shenzang_rl1_tv2)
    EditText shenzang_rl1_tv2;//左肾——宽
    @Bind(R.id.shenzang_rl1_ed1)
    EditText shenzang_rl1_ed1;//左肾——高
    @Bind(R.id.shenzang_rl2_tv1)
    TextView shenzang_rl2_tv1;//右肾体积
    @Bind(R.id.shenzang_rl2_tv2)
    EditText shenzang_rl2_tv2;//右肾--长
    @Bind(R.id.shenzang_rl2_ed)
    EditText shenzang_rl2_ed;//右肾——宽
    @Bind(R.id.shenzang_rl2_ed1)
    EditText shenzang_rl2_ed1;//右肾——高
    @Bind(R.id.shenzang_rl3_tv1)
    TextView shenzang_rl3_tv1;//肾实质厚度
    @Bind(R.id.shenzang_rl3_tv2)
    EditText shenzang_rl3_tv2;//肾实质厚度
    @Bind(R.id.shenzang_rl4_tv1)
    TextView shenzang_rl4_tv1;//最大无回声区域
    @Bind(R.id.shenzang_rl4_tv2)
    EditText shenzang_rl4_tv2;//最大无回声区域
    @Bind(R.id.shenzang_rl5_tv1)
    TextView shenzang_rl5_tv1;//最大强回声斑
    @Bind(R.id.shenzang_rl5_tv2)
    EditText shenzang_rl5_tv2;//最大强回声斑
    @Bind(R.id.shenzang_rl6_tv1)
    TextView shenzang_rl6_tv1;//左肾血流情况
    @Bind(R.id.shenzang_rl6_tv3)
    EditText shenzang_rl6_tv3;//左肾血流情况
    @Bind(R.id.shenzang_rl7_tv1)
    TextView shenzang_rl7_tv1;//右肾血流情况
    @Bind(R.id.shenzang_rl7_tv3)
    EditText shenzang_rl7_tv3;//右肾血流情况
    @Bind(R.id.shenzang_rl8_tv1)
    TextView shenzang_rl8_tv1;//集合系统情况
    @Bind(R.id.shenzang_rl8_tv3)
    EditText shenzang_rl8_tv3;//集合系统情况
    private static String LEFTKIDNEY;//左肾体积
    private static String LEFTKIDNEYFLAG = "1";//左肾体积 1:黑 2：黄3：红
    private static String RIGHTKIDNEY;//右肾体积
    private static String RIGHTKIDNEYFLAG = "1";//右肾体积   1:黑 2：黄3：红
    @Override
    public int getLayoutId() {
        return R.layout.fgt_shenzangcaichao_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        szccgetdata();
    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_SZCC_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_SZCC_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            szccgetdata();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            szccSubmit();
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
                    getresult(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).szccdatas(phone, secret, userId, Content.time_lab), 1);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }

    /**
     * 获取数据
     */

    private void szccgetdata() {
        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).szccdatas(phone, secret, userId, Content.time_lab), 1);
    }
    private void getresult(String string) {
        SzccDatas szccDatas = JSON.parseObject(string, SzccDatas.class);
        String str1 = szccDatas.getList().getLEFTKIDNEY();
        String str2 = szccDatas.getList().getRIGHTKIDNEY();
        String str3 = szccDatas.getList().getRCTK();
        String str4 = szccDatas.getList().getTBAA();
        String str5 = szccDatas.getList().getTBSES();
        String str6 = szccDatas.getList().getTLKBF();
        String str7 = szccDatas.getList().getTRKBF();
        String str8 = szccDatas.getList().getCSYSTEM();

        if (str1.split("\\*")[1].equals("")) {
        } else {
            Double data = Double.valueOf(str1.split("\\*")[1]);
            if (data > 120) {
                shenzang_rl1_tv1.setTextColor(0xffccb400);
            } else if (data < 70) {
                shenzang_rl1_tv1.setTextColor(0xffff3c3c);
            }else{
                shenzang_rl1_tv1.setTextColor(0xff5e5e5e);
            }
        }

        if (str2.split("\\*")[1].equals("")) {
        } else {
            Double data = Double.valueOf(str2.split("\\*")[1]);
            if (data > 120) {
                shenzang_rl2_tv1.setTextColor(0xffccb400);
            } else if (data < 70) {
                shenzang_rl2_tv1.setTextColor(0xffff3c3c);
            }else{
                shenzang_rl2_tv1.setTextColor(0xff5e5e5e);
            }
        }

        if (str3.equals("")) {
        } else {
            Double data = Double.valueOf(str3);
            if (data < 1.5 && data > 2) {
                shenzang_rl3_tv1.setTextColor(0xffccb400);
            }else{
                shenzang_rl3_tv1.setTextColor(0xff5e5e5e);
            }
        }

        if (str4.equals("")) {
        } else {
            Double data = Double.valueOf(str4);
            if (data <= 3 && data > 0) {
                shenzang_rl4_tv1.setTextColor(0xffccb400);
            } else if (data > 3) {
                shenzang_rl4_tv1.setTextColor(0xffff3c3c);
            }else{
                shenzang_rl4_tv1.setTextColor(0xff5e5e5e);
            }
        }
        if (str5.equals("")) {
        } else {
            Double data = Double.valueOf(str5);
            if (data > 0) {
                shenzang_rl5_tv1.setTextColor(0xffccb400);
            }else{
                shenzang_rl5_tv1.setTextColor(0xff5e5e5e);
            }
        }

        shenzang_rl1_ed.setText(str1.split("\\*")[0]);
        shenzang_rl1_tv2.setText(str1.split("\\*")[1]);
        shenzang_rl1_ed1.setText(str1.split("\\*")[2]);

        shenzang_rl2_tv2.setText(str2.split("\\*")[0]);
        shenzang_rl2_ed.setText(str2.split("\\*")[1]);
        shenzang_rl2_ed1.setText(str2.split("\\*")[2]);

        shenzang_rl3_tv2.setText(str3);
        shenzang_rl4_tv2.setText(str4);
        shenzang_rl5_tv2.setText(str5);
        shenzang_rl6_tv3.setText(str6);
        shenzang_rl7_tv3.setText(str7);
        shenzang_rl8_tv3.setText(str8);

    }
    /**
     * 提交数据
     */
    private void szccSubmit() {
        showLoadingDialog(null);
        if (shenzang_rl1_ed.getText().toString().equals("") || shenzang_rl1_tv2.getText().toString().equals("") ||
                shenzang_rl1_ed1.getText().toString().equals("") || shenzang_rl2_tv2.getText().toString().equals("") ||
                shenzang_rl2_ed.getText().toString().equals("") || shenzang_rl2_ed1.getText().toString().equals("")) {
            dismissLoadingDialog();
            showToast(getString(R.string.shentijibunengweikong));
        } else {
            LEFTKIDNEY = shenzang_rl1_ed.getText().toString() + "*" + shenzang_rl1_tv2.getText().toString() + "*" + shenzang_rl1_ed1.getText().toString();
            RIGHTKIDNEY = shenzang_rl2_tv2.getText().toString() + "*" + shenzang_rl2_ed.getText().toString() + "*" + shenzang_rl2_ed1.getText().toString();
            int left = Integer.parseInt(shenzang_rl1_ed.getText().toString());
            int right = Integer.parseInt(shenzang_rl2_tv2.getText().toString());
            if (left>=70&&left<=119){
                LEFTKIDNEYFLAG = "1";
            }else if (left>120){
                LEFTKIDNEYFLAG="2";
            }else{
                LEFTKIDNEYFLAG = "3";
            }
            if (right>=70&&right<=119){
                RIGHTKIDNEYFLAG = "1";
            }else if (right>120){
                RIGHTKIDNEYFLAG="2";
            }else{
                RIGHTKIDNEYFLAG = "3";
            }
            doHttp(RetrofitUtils.createApi(DataDetail.class).szccSubmit(phone, secret, userId, Content.time_lab, LEFTKIDNEYFLAG, RIGHTKIDNEYFLAG,
                    LEFTKIDNEY, RIGHTKIDNEY, shenzang_rl3_tv2.getText().toString(), shenzang_rl4_tv2.getText().toString(),
                    shenzang_rl5_tv2.getText().toString(), shenzang_rl6_tv3.getText().toString(),
                    shenzang_rl7_tv3.getText().toString(), shenzang_rl8_tv3.getText().toString(), LanguageUtil.judgeLanguage()), 0);

        }
    }
    private void initView() {
        shenzang_rl1_ed.setText("");
        shenzang_rl1_tv2.setText("");
        shenzang_rl1_ed1.setText("");

        shenzang_rl2_tv2.setText("");
        shenzang_rl2_ed.setText("");
        shenzang_rl2_ed1.setText("");

        shenzang_rl3_tv2.setText("");
        shenzang_rl4_tv2.setText("");
        shenzang_rl5_tv2.setText("");
        shenzang_rl6_tv3.setText("");
        shenzang_rl7_tv3.setText("");
        shenzang_rl8_tv3.setText("");

        shenzang_rl3_tv1.setTextColor(0xff5e5e5e);
        shenzang_rl4_tv1.setTextColor(0xff5e5e5e);
        shenzang_rl5_tv1.setTextColor(0xff5e5e5e);
    }
}
