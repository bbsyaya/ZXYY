package com.zhixinyisheng.user.util;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhixinyisheng.user.ui.BaseAty;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/10/19  16:46
 * <p>
 * 类说明: 联网操作工具类
 */
public class DoHttp extends BaseAty{

    Context context;
    public static final String MY_ACTION_GETMYFRIENDS = "MY_ACTION_GETMYFRIENDS";
    public static final String MYFRIENDS = "MYFRIENDS";

    public DoHttp(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {

        //获取好友成功,发送广播
        Intent intent = new Intent(MY_ACTION_GETMYFRIENDS);
        intent.putExtra(MYFRIENDS, result);
        context.sendBroadcast(intent);



        super.onSuccess(result, call, response, what);
    }




}
