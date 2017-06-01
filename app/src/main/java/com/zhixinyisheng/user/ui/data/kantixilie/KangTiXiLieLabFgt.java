package com.zhixinyisheng.user.ui.data.kantixilie;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas.KtiDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 抗体系列（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class KangTiXiLieLabFgt extends BaseFgt{
    /**
     * 抗核抗体
     */
    @Bind(R.id.kangti_rl1_tvn)
    TextView kangti_rl1_tvn;
    @Bind(R.id.kangti_rl1_tvd)
    TextView kangti_rl1_tvd;
    @Bind(R.id.kangti_rl1_ivd)
    ImageView kangti_rl1_ivd;
    /**
     * 抗PM-1抗体
     */
    @Bind(R.id.kangti_rl2_tvn)
    TextView kangti_rl2_tvn;
    @Bind(R.id.kangti_rl2_tvd)
    TextView kangti_rl2_tvd;
    @Bind(R.id.kangti_rl2_ivd)
    ImageView kangti_rl2_ivd;
    /**
     * 抗Sa抗体
     */
    @Bind(R.id.kangti_rl3_tvn)
    TextView kangti_rl3_tvn;
    @Bind(R.id.kangti_rl3_tvd)
    TextView kangti_rl3_tvd;
    @Bind(R.id.kangti_rl3_ivd)
    ImageView kangti_rl3_ivd;
    /**
     * 抗中性粒细胞胞浆
     */
    @Bind(R.id.kangti_rl4_tvn)
    TextView kangti_rl4_tvn;
    @Bind(R.id.kangti_rl4_tvd)
    TextView kangti_rl4_tvd;
    @Bind(R.id.kangti_rl4_ivd)
    ImageView kangti_rl4_ivd;
    /**
     * 抗Scl-70抗体
     */
    @Bind(R.id.kangti_rl5_tvn)
    TextView kangti_rl5_tvn;
    @Bind(R.id.kangti_rl5_tvd)
    TextView kangti_rl5_tvd;
    @Bind(R.id.kangti_rl5_ivd)
    ImageView kangti_rl5_ivd;
    /**
     * 抗SS-A抗体
     */
    @Bind(R.id.kangti_rl6_tvn)
    TextView kangti_rl6_tvn;
    @Bind(R.id.kangti_rl6_tvd)
    TextView kangti_rl6_tvd;
    @Bind(R.id.kangti_rl6_ivd)
    ImageView kangti_rl6_ivd;
    /**
     * 抗SS-B抗体
     */
    @Bind(R.id.kangti_rl7_tvn)
    TextView kangti_rl7_tvn;
    @Bind(R.id.kangti_rl7_tvd)
    TextView kangti_rl7_tvd;
    @Bind(R.id.kangti_rl7_ivd)
    ImageView kangti_rl7_ivd;
    /**
     * 抗U1RNP抗体
     */
    @Bind(R.id.kangti_rl8_tvn)
    TextView kangti_rl8_tvn;
    @Bind(R.id.kangti_rl8_tvd)
    TextView kangti_rl8_tvd;
    @Bind(R.id.kangti_rl8_ivd)
    ImageView kangti_rl8_ivd;
    /**
     * 抗双链DNA抗体
     */
    @Bind(R.id.kangti_rl9_tvn)
    TextView kangti_rl9_tvn;
    @Bind(R.id.kangti_rl9_tvd)
    TextView kangti_rl9_tvd;
    @Bind(R.id.kangti_rl9_ivd)
    ImageView kangti_rl9_ivd;
    /**
     * 抗着丝点抗体
     */
    @Bind(R.id.kangti_rl10_tvn)
    TextView kangti_rl10_tvn;
    @Bind(R.id.kangti_rl10_tvd)
    TextView kangti_rl10_tvd;
    @Bind(R.id.kangti_rl10_ivd)
    ImageView kangti_rl10_ivd;

    @Bind(R.id.ll_kangti_rl1_ivd)
    RelativeLayout ll_kangti_rl1_ivd;
    @Bind(R.id.ll_kangti_rl2_ivd)
    RelativeLayout ll_kangti_rl2_ivd;
    @Bind(R.id.ll_kangti_rl3_ivd)
    RelativeLayout ll_kangti_rl3_ivd;
    @Bind(R.id.ll_kangti_rl4_ivd)
    RelativeLayout ll_kangti_rl4_ivd;
    @Bind(R.id.ll_kangti_rl5_ivd)
    RelativeLayout ll_kangti_rl5_ivd;
    @Bind(R.id.ll_kangti_rl6_ivd)
    RelativeLayout ll_kangti_rl6_ivd;
    @Bind(R.id.ll_kangti_rl7_ivd)
    RelativeLayout ll_kangti_rl7_ivd;
    @Bind(R.id.ll_kangti_rl8_ivd)
    RelativeLayout ll_kangti_rl8_ivd;
    @Bind(R.id.ll_kangti_rl9_ivd)
    RelativeLayout ll_kangti_rl9_ivd;
    @Bind(R.id.ll_kangti_rl10_ivd)
    RelativeLayout ll_kangti_rl10_ivd;
    private String colorFlag = "-1";

    public PopupWindow pop;
    int no = 0;

    TextView tv11, tv21;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_kangtixilie_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        onclick();
        initPop();
        getktdatas();
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
            getktdatas();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            ktSubmit();
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
    private void onclick() {
        ll_kangti_rl1_ivd.setOnClickListener(listener);
        ll_kangti_rl2_ivd.setOnClickListener(listener);
        ll_kangti_rl3_ivd.setOnClickListener(listener);
        ll_kangti_rl4_ivd.setOnClickListener(listener);
        ll_kangti_rl5_ivd.setOnClickListener(listener);
        ll_kangti_rl6_ivd.setOnClickListener(listener);
        ll_kangti_rl7_ivd.setOnClickListener(listener);
        ll_kangti_rl8_ivd.setOnClickListener(listener);
        ll_kangti_rl9_ivd.setOnClickListener(listener);
        ll_kangti_rl10_ivd.setOnClickListener(listener);

    }
    private void getktdatas() {
        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).ktxldatas(phone, secret, userId, Content.time_lab), 1);
    }

    private void initView() {
        kangti_rl1_tvd.setText("");
        kangti_rl2_tvd.setText("");
        kangti_rl3_tvd.setText("");
        kangti_rl4_tvd.setText("");
        kangti_rl5_tvd.setText("");
        kangti_rl6_tvd.setText("");
        kangti_rl7_tvd.setText("");
        kangti_rl8_tvd.setText("");
        kangti_rl9_tvd.setText("");
        kangti_rl10_tvd.setText("");

        kangti_rl1_tvn.setTextColor(0xff5e5e5e);
        kangti_rl2_tvn.setTextColor(0xff5e5e5e);
        kangti_rl3_tvn.setTextColor(0xff5e5e5e);
        kangti_rl4_tvn.setTextColor(0xff5e5e5e);
        kangti_rl5_tvn.setTextColor(0xff5e5e5e);
        kangti_rl6_tvn.setTextColor(0xff5e5e5e);
        kangti_rl7_tvn.setTextColor(0xff5e5e5e);
        kangti_rl8_tvn.setTextColor(0xff5e5e5e);
        kangti_rl9_tvn.setTextColor(0xff5e5e5e);
        kangti_rl10_tvn.setTextColor(0xff5e5e5e);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).ktxldatas(phone, secret, userId, Content.time_lab), 1);
                showToast(getString(R.string.tijiaochenggong));
                break;
            case 1:
                getresultkt(result);

                break;
        }
    }
    /**
     * 解析获取数据接口
     */
    private void getresultkt(String string) {
        KtiDatas ktiDatas = JSON.parseObject(string, KtiDatas.class);
        String str1 = ktiDatas.getDataDB().getAna();
        String str2 = ktiDatas.getDataDB().getAntiPM1();
        String str3 = ktiDatas.getDataDB().getAntiSa();
        String str4 = ktiDatas.getDataDB().getZxlxbbj();
        String str5 = ktiDatas.getDataDB().getAntscl70();
        String str6 = ktiDatas.getDataDB().getAntssA();
        String str7 = ktiDatas.getDataDB().getAntssB();
        String str8 = ktiDatas.getDataDB().getU1rnp();
        String str9 = ktiDatas.getDataDB().getDna();
        String str10 = ktiDatas.getDataDB().getAca();

        if (str1.equals("+")) {
            kangti_rl1_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl1_tvn.setTextColor(0xff5e5e5e);
        }
        if (str2.equals("+")) {
            kangti_rl2_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl2_tvn.setTextColor(0xff5e5e5e);
        }
        if (str3.equals("+")) {
            kangti_rl3_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl3_tvn.setTextColor(0xff5e5e5e);
        }
        if (str4.equals("+")) {
            kangti_rl4_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl4_tvn.setTextColor(0xff5e5e5e);
        }
        if (str5.equals("+")) {
            kangti_rl5_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl5_tvn.setTextColor(0xff5e5e5e);
        }
        if (str6.equals("+")) {
            kangti_rl6_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl6_tvn.setTextColor(0xff5e5e5e);
        }
        if (str7.equals("+")) {
            kangti_rl7_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl7_tvn.setTextColor(0xff5e5e5e);
        }
        if (str8.equals("+")) {
            kangti_rl8_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl8_tvn.setTextColor(0xff5e5e5e);
        }
        if (str9.equals("+")) {
            kangti_rl9_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl9_tvn.setTextColor(0xff5e5e5e);
        }
        if (str10.equals("+")) {
            kangti_rl10_tvn.setTextColor(0xffccb400);
        }else{
            kangti_rl10_tvn.setTextColor(0xff5e5e5e);
        }
        kangti_rl1_tvd.setText(str1);
        kangti_rl2_tvd.setText(str2);
        kangti_rl3_tvd.setText(str3);
        kangti_rl4_tvd.setText(str4);
        kangti_rl5_tvd.setText(str5);
        kangti_rl6_tvd.setText(str6);
        kangti_rl7_tvd.setText(str7);
        kangti_rl8_tvd.setText(str8);
        kangti_rl9_tvd.setText(str9);
        kangti_rl10_tvd.setText(str10);
    }
    /**
     * 提交数据
     */
    private void ktSubmit() {
        String tv_str1 = kangti_rl1_tvd.getText().toString();
        String tv_str2 = kangti_rl2_tvd.getText().toString();
        String tv_str3 = kangti_rl3_tvd.getText().toString();
        String tv_str4 = kangti_rl4_tvd.getText().toString();
        String tv_str5 = kangti_rl5_tvd.getText().toString();
        String tv_str6 = kangti_rl6_tvd.getText().toString();
        String tv_str7 = kangti_rl7_tvd.getText().toString();
        String tv_str8 = kangti_rl8_tvd.getText().toString();
        String tv_str9 = kangti_rl9_tvd.getText().toString();
        String tv_str10 = kangti_rl10_tvd.getText().toString();

        if (tv_str1.equals("") && tv_str2.equals("") && tv_str3.equals("") && tv_str4.equals("") && tv_str5.equals("") &&
                tv_str6.equals("") && tv_str7.equals("") && tv_str8.equals("") && tv_str9.equals("") && tv_str10.equals("")) {
            showToast(getString(R.string.qingzhishaotianxieyitiaoshuju));
        } else {
            if (tv_str1.equals("+") || tv_str2.equals("+") || tv_str3.equals("+") || tv_str4.equals("+") || tv_str5.equals("+") ||
                    tv_str6.equals("+") || tv_str7.equals("+") || tv_str8.equals("+") || tv_str9.equals("+") || tv_str10.equals("+")) {
                colorFlag = "2";
            } else {
                colorFlag = "1";
            }
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(DataDetail.class).ktxlSubmit(phone, secret, userId, Content.time_lab, kangti_rl1_tvd.getText().toString(),
                    kangti_rl2_tvd.getText().toString(), kangti_rl3_tvd.getText().toString(), kangti_rl4_tvd.getText().toString(),
                    kangti_rl5_tvd.getText().toString(), kangti_rl6_tvd.getText().toString(), kangti_rl7_tvd.getText().toString(),
                    kangti_rl8_tvd.getText().toString(), kangti_rl9_tvd.getText().toString(), kangti_rl10_tvd.getText().toString(), LanguageUtil.judgeLanguage()), 0);
        }

    }
    private void initPop() {
        View view = getActivity().getLayoutInflater().inflate(
                R.layout.kangti_pop, null);
        pop = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(false);
        tv11 = (TextView) view.findViewById(R.id.np_tv11);
        tv21 = (TextView) view.findViewById(R.id.np_tv21);
        tv11.setOnClickListener(listener);
        tv21.setOnClickListener(listener);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();


                if ((event.getAction() == MotionEvent.ACTION_DOWN)
                        && ((x < 0) || (x >= v.getWidth()) || (y < 0) || (y >= v
                        .getHeight()))) {
                    pop.dismiss();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pop.dismiss();
                    return true;
                } else {
                    return v.onTouchEvent(event);
                }
            }
        });
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == tv11) {
                setData(tv11);
            } else if (v == tv21) {
                setData(tv21);
            } else if (v == ll_kangti_rl1_ivd) {
                no = 1;
                if (pop != null && pop.isShowing()) {
                    pop.dismiss();
                    kangti_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                } else {
                    pop.showAsDropDown(kangti_rl1_tvd, 0, 0);
//                    kangti_rl1_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl2_ivd) {
                no = 2;
                if (pop != null && pop.isShowing()) {
                    kangti_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl2_tvd, 0, 0);
//                    kangti_rl2_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl3_ivd) {
                no = 3;
                if (pop != null && pop.isShowing()) {
                    kangti_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl3_tvd, 0, 0);
//                    kangti_rl3_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl4_ivd) {
                no = 4;
                if (pop != null && pop.isShowing()) {
                    kangti_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl4_tvd, 0, 0);
//                    kangti_rl4_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl5_ivd) {
                no = 5;
                if (pop != null && pop.isShowing()) {
                    kangti_rl5_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl5_tvd, 0, 0);
//                    kangti_rl5_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl6_ivd) {
                no = 6;
                if (pop != null && pop.isShowing()) {
                    kangti_rl6_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl6_tvd, 0, 0);
//                    kangti_rl6_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl7_ivd) {
                no = 7;
                if (pop != null && pop.isShowing()) {
                    kangti_rl7_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl7_tvd, 0, 0);
//                    kangti_rl7_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl8_ivd) {
                no = 8;
                if (pop != null && pop.isShowing()) {
                    kangti_rl8_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl8_tvd, 0, 0);
//                    kangti_rl8_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl9_ivd) {
                no = 9;
                if (pop != null && pop.isShowing()) {
                    kangti_rl9_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl9_tvd, 0, 0);
//                    kangti_rl9_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_kangti_rl10_ivd) {
                no = 10;
                if (pop != null && pop.isShowing()) {
                    kangti_rl10_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(kangti_rl10_tvd, 0, 0);
//                    kangti_rl10_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            }
        }

        // 设置显示数据
        private void setData(TextView tv1) {
            pop.dismiss();
            if (no == 1) {
                kangti_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl1_tvd.setText(tv1.getText().toString());
            } else if (no == 2) {
                kangti_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl2_tvd.setText(tv1.getText().toString());
            } else if (no == 3) {
                kangti_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl3_tvd.setText(tv1.getText().toString());
            } else if (no == 4) {
                kangti_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl4_tvd.setText(tv1.getText().toString());
            } else if (no == 5) {
                kangti_rl5_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl5_tvd.setText(tv1.getText().toString());
            } else if (no == 6) {
                kangti_rl6_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl6_tvd.setText(tv1.getText().toString());
            } else if (no == 7) {
                kangti_rl7_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl7_tvd.setText(tv1.getText().toString());
            } else if (no == 8) {
                kangti_rl8_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl8_tvd.setText(tv1.getText().toString());
            } else if (no == 9) {
                kangti_rl9_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl9_tvd.setText(tv1.getText().toString());
            } else if (no == 10) {
                kangti_rl10_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                kangti_rl10_tvd.setText(tv1.getText().toString());
            }
        }
    };

}
