package com.zhixinyisheng.user.ui;

import android.view.View;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.base.BaseFrameFgt;
import com.zhixinyisheng.user.config.UserManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by yzy on 2016/4/18.
 */
public abstract class BaseFgt extends BaseFrameFgt {
    public  boolean isShowToast=true;
//    public String userId= UserManager.getUserInfo().getUserId();//用户ID
//    public String userId = Content.USERID;
    public String userId = BaseApplication.userId;
    public String phone = UserManager.getUserInfo().getPhone();//登录手机号
    public String secret =UserManager.getUserInfo().getSecret();//登录返回秘钥
    public String time =   new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    @Override
    public void btnClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if ("".equals(userId)||null==userId){
            userId = UserManager.getUserInfo().getUserId();
        }
    }


    @Override
    public boolean setIsInitRequestData() {
        return false;
    }
    @Override
    public void onUserVisible() {

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
//        if (isShowToast){
//            ResultInfo resultInfo= AppJsonUtil.getResultInfo(result);
//            if (!resultInfo.getReturnMessage().equals("")){
//                showToast(resultInfo.getReturnMessage());
//            }
//
//        }
        super.onFailure(result, call, response, what);
    }
}
