package com.zhixinyisheng.user.ui.data.yiganwuxiang;

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
import com.zhixinyisheng.user.domain.datas.YgwxDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 乙肝五项（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class YiGanWuXiangLabFgt extends BaseFgt{
    @Bind(R.id.ygwx_rl1_tvn)
    TextView ygwx_rl1_tvn;//乙肝表面抗原
    @Bind(R.id.ygwx_rl1_tvd)
    TextView ygwx_rl1_tvd;//乙肝表面抗原(+ -)
    @Bind(R.id.ygwx_rl1_ivd)
    ImageView ygwx_rl1_ivd;//乙肝表面抗原

    @Bind(R.id.ygwx_rl2_tvn)
    TextView ygwx_rl2_tvn;//乙肝表面抗体
    @Bind(R.id.ygwx_rl2_tvd)
    TextView ygwx_rl2_tvd;
    @Bind(R.id.ygwx_rl2_ivd)
    ImageView ygwx_rl2_ivd;

    @Bind(R.id.ygwx_rl3_tvn)
    TextView ygwx_rl3_tvn;//乙肝e抗原
    @Bind(R.id.ygwx_rl3_tvd)
    TextView ygwx_rl3_tvd;
    @Bind(R.id.ygwx_rl3_ivd)
    ImageView ygwx_rl3_ivd;

    @Bind(R.id.ygwx_rl4_tvn)
    TextView ygwx_rl4_tvn;//乙肝e抗体
    @Bind(R.id.ygwx_rl4_tvd)
    TextView ygwx_rl4_tvd;
    @Bind(R.id.ygwx_rl4_ivd)
    ImageView ygwx_rl4_ivd;

    @Bind(R.id.ygwx_rl5_tvn)
    TextView ygwx_rl5_tvn;//乙肝核心抗体
    @Bind(R.id.ygwx_rl5_tvd)
    TextView ygwx_rl5_tvd;
    @Bind(R.id.ygwx_rl5_ivd)
    ImageView ygwx_rl5_ivd;

    @Bind(R.id.ll_ygwx_rl1_ivd)
    RelativeLayout ll_ygwx_rl1_ivd;
    @Bind(R.id.ll_ygwx_rl2_ivd)
    RelativeLayout ll_ygwx_rl2_ivd;
    @Bind(R.id.ll_ygwx_rl3_ivd)
    RelativeLayout ll_ygwx_rl3_ivd;
    @Bind(R.id.ll_ygwx_rl4_ivd)
    RelativeLayout ll_ygwx_rl4_ivd;
    @Bind(R.id.ll_ygwx_rl5_ivd)
    RelativeLayout ll_ygwx_rl5_ivd;
    int no = 0;
    private String colorflag = "-1";// 颜色值
    public PopupWindow pop;
    private TextView tv11,tv21;//pop的TextView
    @Override
    public int getLayoutId() {
        return R.layout.fgt_yiganwuxiang_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        onlick();
        initPop();
        getygwxdatas();

    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_YGWX_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_YGWX_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            getygwxdatas();
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            ygwxSubmit();
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
                getresultygwx(result);
                break;
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).ygwxdatas(phone,secret,userId,Content.time_lab),1);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }
    /**
     * 获取数据接口解析数据
     */
    private void getresultygwx(String string){
        YgwxDatas ygwxDatas = JSON.parseObject(string,YgwxDatas.class);
        String str1 = ygwxDatas.getDataDB().getHBSAG();
        String str2 = ygwxDatas.getDataDB().getHBS();
        String str3 = ygwxDatas.getDataDB().getHBEAG();
        String str4 = ygwxDatas.getDataDB().getHBE();
        String str5 = ygwxDatas.getDataDB().getHBC();

        if (str1.equals("+")){
            ygwx_rl1_tvn.setTextColor(0xffccb400);
        }else{
            ygwx_rl1_tvn.setTextColor(0xff5e5e5e);
        }
        if (str2.equals("+")){
            ygwx_rl2_tvn.setTextColor(0xffccb400);
        }else{
            ygwx_rl2_tvn.setTextColor(0xff5e5e5e);
        }
        if (str3.equals("+")){
            ygwx_rl3_tvn.setTextColor(0xffccb400);
        }else{
            ygwx_rl3_tvn.setTextColor(0xff5e5e5e);
        }
        if (str4.equals("+")){
            ygwx_rl4_tvn.setTextColor(0xffccb400);
        }else{
            ygwx_rl4_tvn.setTextColor(0xff5e5e5e);
        }
        if (str5.equals("+")){
            ygwx_rl5_tvn.setTextColor(0xffccb400);
        }else{
            ygwx_rl5_tvn.setTextColor(0xff5e5e5e);
        }
        ygwx_rl1_tvd.setText(str1);
        ygwx_rl2_tvd.setText(str2);
        ygwx_rl3_tvd.setText(str3);
        ygwx_rl4_tvd.setText(str4);
        ygwx_rl5_tvd.setText(str5);
    }

    /**
     * 获得数据
     */
    private void getygwxdatas(){

        ygwx_rl1_tvd.setText("");
        ygwx_rl2_tvd.setText("");
        ygwx_rl3_tvd.setText("");
        ygwx_rl4_tvd.setText("");
        ygwx_rl5_tvd.setText("");

        ygwx_rl1_tvn.setTextColor(0xff5e5e5e);
        ygwx_rl2_tvn.setTextColor(0xff5e5e5e);
        ygwx_rl3_tvn.setTextColor(0xff5e5e5e);
        ygwx_rl4_tvn.setTextColor(0xff5e5e5e);
        ygwx_rl5_tvn.setTextColor(0xff5e5e5e);
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).ygwxdatas(phone,secret,userId,Content.time_lab),1);
    }
    /**
     * 提交数据
     */
    private void ygwxSubmit(){
        if(ygwx_rl1_tvd.getText().toString().equals("")&&ygwx_rl2_tvd.getText().toString().equals("")&&
                ygwx_rl3_tvd.getText().toString().equals("")&&ygwx_rl4_tvd.getText().toString().equals("")&&
                ygwx_rl5_tvd.getText().toString().equals("")){
            showToast(getString(R.string.qingzhishaotianxieyitiaoshuju));
            return;
        }

        String strAll = ygwx_rl1_tvd.getText().toString()+ygwx_rl2_tvd.getText().toString()+ygwx_rl3_tvd.getText().toString()
                +ygwx_rl4_tvd.getText().toString()+ygwx_rl5_tvd.getText().toString();
        if (strAll.equals("----+")
                ||strAll.equals("-+-++")
                ||strAll.equals("-+---")
                ||strAll.equals("-+--+")
                ||strAll.equals("---++")
                ||strAll.equals("-----")){
            colorflag = "1";
        }else if (strAll.equals("+----")
                ||strAll.equals("+-+++")
                ||strAll.equals("+--+-")){
            colorflag="2";
        }else if (strAll.equals("+-+-+")
                ||strAll.equals("+--++")
                ||strAll.equals("+-+--")
                ||strAll.equals("+---+")){
            colorflag="3";
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).ygwxSubmit(phone,secret,userId, Content.time_lab,colorflag,
                ygwx_rl1_tvd.getText().toString(), ygwx_rl2_tvd.getText().toString(), ygwx_rl3_tvd.getText().toString(),
                ygwx_rl4_tvd.getText().toString(), ygwx_rl5_tvd.getText().toString(), LanguageUtil.judgeLanguage()),0);

    }
    private void onlick(){
        ll_ygwx_rl1_ivd.setOnClickListener(listener);
        ll_ygwx_rl2_ivd.setOnClickListener(listener);
        ll_ygwx_rl3_ivd.setOnClickListener(listener);
        ll_ygwx_rl4_ivd.setOnClickListener(listener);
        ll_ygwx_rl5_ivd.setOnClickListener(listener);
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
            } else if (v == ll_ygwx_rl1_ivd) {
                no = 1;
                if (pop != null && pop.isShowing()) {
                    pop.dismiss();
                    ygwx_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                } else {
                    pop.showAsDropDown(ygwx_rl1_tvd, 0, 0);
//                    ygwx_rl1_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ygwx_rl2_ivd) {
                no = 2;
                if (pop != null && pop.isShowing()) {
                    ygwx_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(ygwx_rl2_tvd, 0, 0);
//                    ygwx_rl2_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ygwx_rl3_ivd) {
                no = 3;
                if (pop != null && pop.isShowing()) {
                    ygwx_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(ygwx_rl3_tvd, 0, 0);
//                    ygwx_rl3_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ygwx_rl4_ivd) {
                no = 4;
                if (pop != null && pop.isShowing()) {
                    ygwx_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(ygwx_rl4_tvd, 0, 0);
//                    ygwx_rl4_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ygwx_rl5_ivd) {
                no = 5;
                if (pop != null && pop.isShowing()) {
                    ygwx_rl5_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(ygwx_rl5_tvd, 0, 0);
//                    ygwx_rl5_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            }
        }

        // 设置显示数据
        private void setData(TextView tv1) {
            pop.dismiss();
            if (no == 1) {
                ygwx_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ygwx_rl1_tvd.setText(tv1.getText().toString());
            } else if (no == 2) {
                ygwx_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ygwx_rl2_tvd.setText(tv1.getText().toString());
            } else if (no == 3) {
                ygwx_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ygwx_rl3_tvd.setText(tv1.getText().toString());
            } else if (no == 4) {
                ygwx_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ygwx_rl4_tvd.setText(tv1.getText().toString());
            } else if (no == 5) {
                ygwx_rl5_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ygwx_rl5_tvd.setText(tv1.getText().toString());
            }
        }
    };

}
