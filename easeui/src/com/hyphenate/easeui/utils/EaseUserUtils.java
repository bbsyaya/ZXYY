package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.util.RetrofitUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.SelInfoEntity;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){

    	user = getUserInfo(username);
        if (user.getNick().equals("单项好友")) {
            getMyinfo(username, imageView, null, context);
            return;
        }else{
            Glide.with(context).load(user.getAvatar())
                    .placeholder(R.drawable.ic_launcher2)//占位图
                    .error(R.drawable.ic_launcher2)//加载错误图
                    .bitmapTransform(new CropCircleTransformation(context))//裁剪圆形
                    .into(imageView);
        }




    }


    /**
     * set user's nickname
     */
    static EaseUser user = null;
    public static void setUserNick(String username,TextView textView){

        if(textView != null){
           user = getUserInfo(username);
//            Log.e("jujing",user.getNick()+"$$$");
            if (user.getNick().equals("单项好友")) {
                getMyinfo(username,null,textView,null);
                return;
            }

            if(user != null && user.getNick() != null){
                textView.setText(user.getNick());
//                Logger.e("userand",user.getNick()+"@@@");
            }else{
                textView.setText(username);
            }
        }
    }

    /**
     * 获取所有好友
     */
    public static void getMyinfo(final String userid, final ImageView imageView, final TextView textView,
                          final Context context) {
        RetrofitUtils.createApi(EaseIMUrl.class)
                .getMyinfo(BaseApplication.phone, BaseApplication.secret,userid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Logger.e("接口地址", call.request().url().toString());
                        try {
                            String result = response.body().string();
                            Logger.e("接口返回结果", result);

                            SelInfoEntity sfe = JSON.parseObject(result,SelInfoEntity.class);
                            EaseUser eu = new EaseUser(sfe.getDb().getUserId());
                            String name = sfe.getDb().getUsername();
                            if (TextUtils.isEmpty(name) || name.equals("") || name.equals("匿名")) {
                                eu.setNick(sfe.getDb().getCard()+"");
                            } else {
                                eu.setNick(name);
                            }
                            eu.setAvatar(sfe.getDb().getHeadUrl());

                            if (imageView != null) {
                                Glide.with(context).load(sfe.getDb().getHeadUrl())
                                        .placeholder(R.drawable.ic_launcher2)//占位图
                                        .error(R.drawable.ic_launcher2)//加载错误图
                                        .bitmapTransform(new CropCircleTransformation(context))//裁剪圆形
                                        .into(imageView);
                            }

                            if (textView != null) {
                                textView.setText(name);
                            }




                            user = eu;


                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Logger.e("联网onFailure",t.getMessage() +"  @  "+ call.request().url().toString());

//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });


    }




}
