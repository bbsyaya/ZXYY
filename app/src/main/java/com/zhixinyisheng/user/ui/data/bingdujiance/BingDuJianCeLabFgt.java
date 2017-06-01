package com.zhixinyisheng.user.ui.data.bingdujiance;

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
import com.zhixinyisheng.user.domain.datas.BduDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 病毒检测（新）
 * Created by 焕焕 on 2017/2/24.
 */
public class BingDuJianCeLabFgt extends BaseFgt{
    @Bind(R.id.bingdu_rl1_tvn)
    TextView bingdu_rl1_tvn;
    @Bind(R.id.bingdu_rl1_tvd)
    TextView bingdu_rl1_tvd;
    @Bind(R.id.bingdu_rl1_ivd)
    ImageView bingdu_rl1_ivd;

    @Bind(R.id.bingdu_rl2_tvn)
    TextView bingdu_rl2_tvn;
    @Bind(R.id.bingdu_rl2_tvd)
    TextView bingdu_rl2_tvd;
    @Bind(R.id.bingdu_rl2_ivd)
    ImageView bingdu_rl2_ivd;

    @Bind(R.id.bingdu_rl3_tvn)
    TextView bingdu_rl3_tvn;
    @Bind(R.id.bingdu_rl3_tvd)
    TextView bingdu_rl3_tvd;
    @Bind(R.id.bingdu_rl3_ivd)
    ImageView bingdu_rl3_ivd;

    @Bind(R.id.bingdu_rl4_tvn)
    TextView bingdu_rl4_tvn;
    @Bind(R.id.bingdu_rl4_tvd)
    TextView bingdu_rl4_tvd;
    @Bind(R.id.bingdu_rl4_ivd)
    ImageView bingdu_rl4_ivd;

    @Bind(R.id.ll_bingdu_rl1_ivd)
    RelativeLayout ll_bingdu_rl1_ivd;
    @Bind(R.id.ll_bingdu_rl2_ivd)
    RelativeLayout ll_bingdu_rl2_ivd;
    @Bind(R.id.ll_bingdu_rl3_ivd)
    RelativeLayout ll_bingdu_rl3_ivd;
    @Bind(R.id.ll_bingdu_rl4_ivd)
    RelativeLayout ll_bingdu_rl4_ivd;
    private static String colorFlag = "1";

    public PopupWindow pop;
    int no = 0;

    TextView tv11,tv21;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_bingdujiance_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        initListener();
        try {
            initPop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bdjcdatas();
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
            bdjcdatas();        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            commitData();
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
    private void bdjcdatas(){
        bingdu_rl1_tvn.setTextColor(0xff5e5e5e);
        bingdu_rl1_tvd.setText("");
        bingdu_rl2_tvn.setTextColor(0xff5e5e5e);
        bingdu_rl2_tvd.setText("");
        bingdu_rl3_tvn.setTextColor(0xff5e5e5e);
        bingdu_rl3_tvd.setText("");
        bingdu_rl4_tvn.setTextColor(0xff5e5e5e);
        bingdu_rl4_tvd.setText("");
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).bdjcdatas(phone,secret,userId, Content.time_lab),1);
    }
    private void commitData() {
        if (bingdu_rl1_tvd.getText().toString().equals("")
                &&bingdu_rl2_tvd.getText().toString().equals("")
                &&bingdu_rl3_tvd.getText().toString().equals("")
                &&bingdu_rl4_tvd.getText().toString().equals("")){
            showToast(getString(R.string.qingzhishaotianxieyitiaoshuju));
        }else {
            bdjcSubmit();
        }
    }
    /**
     * 提交数据
     */
    private void bdjcSubmit(){

        if (bingdu_rl1_tvd.getText().toString().equals("+")
                ||bingdu_rl2_tvd.getText().toString().equals("+")
                ||bingdu_rl3_tvd.getText().toString().equals("+")
                ||bingdu_rl4_tvd.getText().toString().equals("+")){
            colorFlag = "2";
        }else {
            colorFlag = "1";
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).bdjcSubmit(phone,secret,userId, Content.time_lab,colorFlag,
                bingdu_rl1_tvd.getText().toString(),bingdu_rl2_tvd.getText().toString(),
                bingdu_rl3_tvd.getText().toString(),bingdu_rl4_tvd.getText().toString(), LanguageUtil.judgeLanguage()),0);
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 0:
                doHttp(RetrofitUtils.createApi(DataDetail.class).bdjcdatas(phone,secret,userId,Content.time_lab),1);
                showToast(getString(R.string.tijiaochenggong));
                break;
            case 1:
                try {
                    BduDatas bdudatas = JSON.parseObject(result,BduDatas.class);

                    if(bdudatas.getDataDB().getEB().equals("+")){
                        bingdu_rl1_tvn.setTextColor(0xffccb400);
                    }else{
                        bingdu_rl1_tvn.setTextColor(0xff5e5e5e);
                    }
                    if(bdudatas.getDataDB().getSH().equals("+")){
                        bingdu_rl2_tvn.setTextColor(0xffccb400);
                    }else{
                        bingdu_rl2_tvn.setTextColor(0xff5e5e5e);
                    }
                    if(bdudatas.getDataDB().getAIDS().equals("+")){
                        bingdu_rl3_tvn.setTextColor(0xffccb400);
                    }else{
                        bingdu_rl3_tvn.setTextColor(0xff5e5e5e);
                    }
                    if(bdudatas.getDataDB().getCELL().equals("+")){
                        bingdu_rl4_tvn.setTextColor(0xffccb400);
                    }else{
                        bingdu_rl4_tvn.setTextColor(0xff5e5e5e);
                    }
                    bingdu_rl1_tvd.setText(bdudatas.getDataDB().getEB());
                    bingdu_rl2_tvd.setText(bdudatas.getDataDB().getSH());
                    bingdu_rl3_tvd.setText(bdudatas.getDataDB().getAIDS());
                    bingdu_rl4_tvd.setText(bdudatas.getDataDB().getCELL());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void initListener() {
        ll_bingdu_rl1_ivd.setOnClickListener(listener);
        ll_bingdu_rl2_ivd.setOnClickListener(listener);
        ll_bingdu_rl3_ivd.setOnClickListener(listener);
        ll_bingdu_rl4_ivd.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == tv11) {
                setData(tv11);
            } else if (v == tv21) {
                setData(tv21);
            } else if (v == ll_bingdu_rl1_ivd) {
                no = 1;
                if (pop != null && pop.isShowing()) {
                    pop.dismiss();
                    bingdu_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                } else {
                    pop.showAsDropDown(bingdu_rl1_tvd, 0, 0);
//                    bingdu_rl1_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_bingdu_rl2_ivd) {
                no = 2;
                if (pop != null && pop.isShowing()) {
                    bingdu_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(bingdu_rl2_tvd, 0, 0);
//                    bingdu_rl2_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_bingdu_rl3_ivd) {
                no = 3;
                if (pop != null && pop.isShowing()) {
                    bingdu_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(bingdu_rl3_tvd, 0, 0);
//                    bingdu_rl3_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_bingdu_rl4_ivd) {
                no = 4;
                if (pop != null && pop.isShowing()) {
                    bingdu_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(bingdu_rl4_tvd, 0, 0);
//                    bingdu_rl4_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            }
        }

        // 设置显示数据
        public void setData(TextView tv1) {
            pop.dismiss();
            if (no == 1) {
                bingdu_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                bingdu_rl1_tvd.setText(tv1.getText().toString());
            } else if (no == 2) {
                bingdu_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                bingdu_rl2_tvd.setText(tv1.getText().toString());
            } else if (no == 3) {
                bingdu_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                bingdu_rl3_tvd.setText(tv1.getText().toString());
            } else if (no == 4) {
                bingdu_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                bingdu_rl4_tvd.setText(tv1.getText().toString());
            }
        }
    };

    private void initPop()  throws Exception{

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
                    bingdu_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pop.dismiss();
                    bingdu_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    return true;
                } else {
                    bingdu_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    bingdu_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    return v.onTouchEvent(event);
                }
            }
        });


    }
}
