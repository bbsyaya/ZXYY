package com.zhixinyisheng.user.ui.data.xuechanggui;

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
import com.zhixinyisheng.user.domain.datas.XcgDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 血常规（新）
 * Created by 焕焕 on 2017/2/23.
 */
public class XueChangGuiLabFgt extends BaseFgt{
    @Bind(R.id.tv_baixibaojishu)
    TextView tv_baixibaojishu;//白细胞计数
    @Bind(R.id.et_baixibaojishu)
    EditText et_baixibaojishu;//白细胞输入框
    @Bind(R.id.tv_hongxibaojishu)
    TextView tv_hongxibaojishu;//红细胞计数
    @Bind(R.id.et_hongxibaojishu)
    EditText et_hongxibaojishu;//红细胞输入框
    @Bind(R.id.tv_zhongxinglixibaobaifshu)
    TextView tv_zhongxinglixibaobaifshu;//中性粒细胞百分数
    @Bind(R.id.et_zhongxinglibaifengshu)
    EditText et_zhongxinglibaifengshu;//中性粒细胞百分数输入框
    @Bind(R.id.tv_zhongxinglijueduishu)
    TextView tv_zhongxinglijueduishu;//中性粒细胞绝对数
    @Bind(R.id.et_zhongxinglijueduishu)
    EditText et_zhongxinglijueduishu;//中性粒细胞绝对数输入框
    @Bind(R.id.tv_xuehongdanbai)
    TextView tv_xuehongdanbai;//血红蛋白
    @Bind(R.id.et_xuehongdanbai)
    EditText et_xuehongdanbai;//血红蛋白输入框
    String OXYPHORASEFLAG;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_xuechanggui_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        findData();
    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_XCG_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_XCG_TIME);
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
            submitData();
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
     * 提交数据
     */
    private void submitData(){
        String niaodanbaiData = et_xuehongdanbai.getText().toString();
        if(!niaodanbaiData.equals("")){
            Double OXYPHORASE_num = Double.valueOf(niaodanbaiData);
            if(OXYPHORASE_num>=100&&OXYPHORASE_num<130){
                tv_xuehongdanbai.setTextColor(0xffccb400);
                OXYPHORASEFLAG ="2";
            }else if(OXYPHORASE_num<100||OXYPHORASE_num>175){
                tv_xuehongdanbai.setTextColor(0xffff3c3c);
                OXYPHORASEFLAG ="3";
            }else {
                tv_xuehongdanbai.setTextColor(0xff5e5e5e);
                OXYPHORASEFLAG ="1";
            }
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(DataDetail.class).xcgSubmit(phone,secret,userId,Content.time_lab,OXYPHORASEFLAG,
                    et_baixibaojishu.getText().toString(),et_hongxibaojishu.getText().toString(), et_zhongxinglibaifengshu.getText().toString(),
                    et_zhongxinglijueduishu.getText().toString(),
                    et_xuehongdanbai.getText().toString(), LanguageUtil.judgeLanguage()),1);
        }else {
            showToast(getString(R.string.qingshuruxuehongdanbai));
        }

    }
    /**
     * 获取参数
     */
    private void findData() {
        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).xcgDetail(phone, secret, userId, Content.time_lab), 0);
    }

    private void initView() {
        et_baixibaojishu.setText("");
        et_hongxibaojishu.setText("");
        et_zhongxinglibaifengshu.setText("");
        et_zhongxinglijueduishu.setText("");
        et_xuehongdanbai.setText("");

        tv_baixibaojishu.setTextColor(0xff5e5e5e);
        tv_hongxibaojishu.setTextColor(0xff5e5e5e);
        tv_zhongxinglixibaobaifshu.setTextColor(0xff5e5e5e);
        tv_zhongxinglijueduishu.setTextColor(0xff5e5e5e);
        tv_xuehongdanbai.setTextColor(0xff5e5e5e);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 0:
                try {
                    getResult(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                doHttp(RetrofitUtils.createApi(DataDetail.class).xcgDetail(phone, secret, userId, Content.time_lab), 0);
                showToast(getString(R.string.tijiaochenggong));
                break;
        }
    }

    private void getResult(String result) {
        XcgDatas xcgDatas = JSON.parseObject(result,XcgDatas.class);
        if(!xcgDatas.getList().getWBC().equals("")){
            Double wbc_num = Double.valueOf(xcgDatas.getList().getWBC());
            if(wbc_num<=3.5||wbc_num>=9.5){
                tv_baixibaojishu.setTextColor(0xffccb400);
            }else{
                tv_baixibaojishu.setTextColor(0xff5e5e5e);
            }
        }
        if(!xcgDatas.getList().getRBC().equals("")){
            Double rbc_num = Double.valueOf(xcgDatas.getList().getRBC());
            if(rbc_num<=4.3||rbc_num>=5.8){
                tv_hongxibaojishu.setTextColor(0xffccb400);
            }else{
                tv_hongxibaojishu.setTextColor(0xff5e5e5e);
            }
        }
        if (!xcgDatas.getList().getNEUTROPHIL().equals("")){
            Double NEUTROPHIL_num = Double.valueOf(xcgDatas.getList().getNEUTROPHIL());
            if(NEUTROPHIL_num<=40||NEUTROPHIL_num>=75){
                tv_zhongxinglixibaobaifshu.setTextColor(0xffccb400);
            }else{
                tv_zhongxinglixibaobaifshu.setTextColor(0xff5e5e5e);
            }
        }
        if (!xcgDatas.getList().getNEUT().equals("")){
            Double NEUT_num = Double.valueOf(xcgDatas.getList().getNEUT());
            if(NEUT_num<=1.8||NEUT_num>=6.3){
                tv_zhongxinglijueduishu.setTextColor(0xffccb400);
            }else{
                tv_zhongxinglijueduishu.setTextColor(0xff5e5e5e);
            }
        }
        if (!xcgDatas.getList().getOXYPHORASE().equals("")){
            Double OXYPHORASE_num = Double.valueOf(xcgDatas.getList().getOXYPHORASE());
            if(OXYPHORASE_num>=100&&OXYPHORASE_num<130){
                tv_xuehongdanbai.setTextColor(0xffccb400);
//                       OXYPHORASEFLAG ="2";
            }else if(OXYPHORASE_num<100||OXYPHORASE_num>175){
                tv_xuehongdanbai.setTextColor(0xffff3c3c);
//                       OXYPHORASEFLAG ="3";
            }else {
//                       OXYPHORASEFLAG ="1";
            }
        }

//                if(xcgDatas.getList().getRBC()){}
        et_baixibaojishu.setText(xcgDatas.getList().getWBC());
        et_hongxibaojishu.setText(xcgDatas.getList().getRBC());
        et_zhongxinglibaifengshu.setText(xcgDatas.getList().getNEUTROPHIL());
        et_zhongxinglijueduishu.setText(xcgDatas.getList().getNEUT());
        et_xuehongdanbai.setText(xcgDatas.getList().getOXYPHORASE());
    }
}
