package com.zhixinyisheng.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.PhoneValuesInfo;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.IM.ui.AddFriendDetialAty;
import com.zhixinyisheng.user.util.LanguageUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 通讯录适配器
 * Created by 焕焕 on 2016/12/22.
 */
public class PhoneInfoAdapter extends CommonAdapter<PhoneValuesInfo> {
    Context context;

    public PhoneInfoAdapter(Context context, List<PhoneValuesInfo> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(final ViewHolder holder, final PhoneValuesInfo item, int positon) {
        holder.setTextViewText(R.id.name, item.getFriendRemark());
        holder.setTextViewText(R.id.mentioned, item.getPhone());
        holder.setImageByUrl(R.id.avatar, item.getHeadUrl());
        Glide.with(context).load(item.getHeadUrl()).
                placeholder(R.mipmap.ic_launcher2).
                error(R.mipmap.ic_launcher2).
                bitmapTransform(new CropCircleTransformation(context)).
                into((ImageView) holder.getView(R.id.avatar));
        if (item.getPhone().equals(UserManager.getUserInfo().getPhone())){
            return;
        }

        if (item.getIsFriend().equals("0")){
            holder.getView(R.id.chathis_tv_ty).setVisibility(View.VISIBLE);
            holder.getView(R.id.chathis_tv_ty_yitianjia).setVisibility(View.GONE);
            holder.setTextViewText(R.id.chathis_tv_ty,context.getResources().getString(R.string.tianjia));
        }else {
            holder.getView(R.id.chathis_tv_ty).setVisibility(View.GONE);
            holder.getView(R.id.chathis_tv_ty_yitianjia).setVisibility(View.VISIBLE);
            if (item.getIsFriend().equals("1")){
                holder.setTextViewText(R.id.chathis_tv_ty_yitianjia,context.getResources().getString(R.string.yitianjia));
            }else{
                holder.setTextViewText(R.id.chathis_tv_ty_yitianjia,context.getResources().getString(R.string.yifasong));
            }

        }
        holder.getView(R.id.chathis_tv_ty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LoadingDialog mLoadingDialog = new LoadingDialog(context);
                mLoadingDialog.showLoadingDialog(null);
                RetrofitUtils.createApi(IMUrl.class)
                        .findFrined(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                item.getPhone(),item.getPhone(), LanguageUtil.judgeLanguage())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Logger.e("接口地址 main", call.request().url().toString());
                                try {
                                    String result = response.body().string();
                                    mLoadingDialog.dismiss();
                                    Logger.e("接口返回结果 phoneinfo", result);
                                    JSONObject object = JSONObject.parseObject(result);
                                    if (object.getString("result").equals("3334")) {
                                        Toast.makeText(context,context.getResources().getString(R.string.ninbunengjiaziji), Toast.LENGTH_SHORT).show();
                                    } else if(object.getString("result").equals("0000")){
                                        Bundle bundle = new Bundle();
                                        bundle.putString("a", result);

                                        startActivity(AddFriendDetialAty.class, bundle);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    e.printStackTrace(new PrintStream(baos));
                                    String exception = baos.toString();
                                    Logger.e("接口解析错误 phoneinfo", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        BaseFrameAty.showToast("服务器错误!");
                            }
                        });
            }
        });


    }
    /**
     * 启动一个Activity
     *  @param className 将要启动的Activity的类名
     * @param options   传到将要启动Activity的Bundle，不传时为null
     */
    public float startActivity(Class<?> className, Bundle options) {
        Intent intent = new Intent(context, className);
        if (options != null) {
            intent.putExtras(options);
        }
        context.startActivity(intent);
        return 0;
    }
}
