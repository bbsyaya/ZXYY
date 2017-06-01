package com.zhixinyisheng.user.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.and.yzy.frame.view.dialog.MaterialDialogForEditText;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.datas.TemperatureDatas;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.util.LanguageUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 体温的适配器
 * Created by 焕焕 on 2017/1/7.
 */
public class TemperatureAdapter extends CommonAdapter<TemperatureDatas.ListBean> {
    Context context;
    private LoadingDialog mLoadingDialog;
    private boolean isAllItemEnable = true;
    public TemperatureAdapter(Context context, List<TemperatureDatas.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context =context;
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(context);
        }
    }
    //根据体温值判断状态
    public String judgeColor(String s){
        String color = "";
        if (null!=s||!"".equals(s)) {

            double n = 0;
            try {
                n = Double.valueOf(s.toString());
            } catch (Exception e) {
                e.printStackTrace();
                n=0;
            }
            //int num =Integer.parseInt(s);
            if (n >= 35 && n <= 37.2) {
                color = "黑";
            } else if (n >= 37.3 && n <= 38.4) {
                color = "黄";
            } else {
                color = "红";
            }
        }

        return color;

    }

//    @Override
//    public boolean isEnabled(int position) {
//        return isAllItemEnable;
//    }
//    public void disableAllItemChoser() {
//        isAllItemEnable = false;
//        notifyDataSetChanged();
//    }
//
//    public void enableItemChoser() {
//        isAllItemEnable = true;
//        notifyDataSetChanged();
//    }
    @Override
    public void convert(ViewHolder holder, final TemperatureDatas.ListBean item, final int positon) {
        holder.setTextViewText(R.id.item_date,item.getBYTIME());
        holder.setTextViewText(R.id.item_mor,item.getMORNINGVALUE());
        holder.setTextViewText(R.id.item_after,item.getAFTERNOONVALUE());
        holder.setTextViewText(R.id.item_night,item.getEVENINGVALUE());
//        if (!isAllItemEnable) {
//            holder.getView(R.id.item_date).setEnabled(false);
//            holder.getView(R.id.item_mor).setEnabled(false);
//            holder.getView(R.id.item_after).setEnabled(false);
//            holder.getView(R.id.item_night).setEnabled(false);
//        } else {
//            holder.getView(R.id.item_date).setEnabled(true);
//            holder.getView(R.id.item_mor).setEnabled(true);
//            holder.getView(R.id.item_after).setEnabled(true);
//            holder.getView(R.id.item_night).setEnabled(true);
//        }
        if (judgeColor(item.getMORNINGVALUE()).equals("黑")){
            holder.setTextViewTextColor(R.id.item_mor,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (judgeColor(item.getMORNINGVALUE()).equals("黄")){
            holder.setTextViewTextColor(R.id.item_mor,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_mor,context.getResources().getColor(R.color.bloodsugar_danger));
        }
        if (judgeColor(item.getAFTERNOONVALUE()).equals("黑")){
            holder.setTextViewTextColor(R.id.item_after,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (judgeColor(item.getAFTERNOONVALUE()).equals("黄")){
            holder.setTextViewTextColor(R.id.item_after,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_after,context.getResources().getColor(R.color.bloodsugar_danger));
        }
        if (judgeColor(item.getEVENINGVALUE()).equals("黑")){
            holder.setTextViewTextColor(R.id.item_night,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (judgeColor(item.getEVENINGVALUE()).equals("黄")){
            holder.setTextViewTextColor(R.id.item_night,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_night,context.getResources().getColor(R.color.bloodsugar_danger));
        }
        holder.getView(R.id.rl_mor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getMORNINGVALUE()==null){
                    value = "";
                }else{
                    value=item.getMORNINGVALUE();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseTemperature))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double data = 0;
                                try {
                                    data = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    data=0;
                                    e.printStackTrace();
                                }
                                if (data>=35&&data<=42){
                                    mLoadingDialog.showLoadingDialog(null);
                                    postAddData(item,item.getBYTIME(),degree,"1");
                                }else{
                                    Toast.makeText(context, context.getResources().getString(R.string.qingshuruzhengquetiwenzhi), Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String str) {

                            }
                        })
                        .show();
            }
        });


        holder.getView(R.id.rl_after).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getAFTERNOONVALUE()==null){
                    value = "";
                }else{
                    value=item.getAFTERNOONVALUE();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseTemperature))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double data = 0;
                                try {
                                    data = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    data=0;
                                    e.printStackTrace();
                                }
                                if (data>=35&&data<=42){
                                    mLoadingDialog.showLoadingDialog(null);
                                    postAddData(item,item.getBYTIME(),degree,"2");
                                }else{
                                    Toast.makeText(context, context.getResources().getString(R.string.qingshuruzhengquetiwenzhi), Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String str) {

                            }
                        })
                        .show();
            }
        });


        holder.getView(R.id.rl_night).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getEVENINGVALUE()==null){
                    value = "";
                }else{
                    value=item.getEVENINGVALUE();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseTemperature))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double data = 0;
                                try {
                                    data = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    data=0;
                                    e.printStackTrace();
                                }
                                if (data>=35&&data<=42){
                                    mLoadingDialog.showLoadingDialog(null);
                                    postAddData(item,item.getBYTIME(),degree,"3");
                                }else{
                                    Toast.makeText(context, context.getResources().getString(R.string.qingshuruzhengquetiwenzhi), Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String str) {

                            }
                        })
                        .show();
            }
        });
    }
    /**
     * 调体温数据添加接口
     * */
    private void postAddData(final TemperatureDatas.ListBean item, String time, final String degree, final String timelimit) {
        RetrofitUtils.createApi(DataUrl.class)
                .addTiWen(UserManager.getUserInfo().getUserId(),degree,time,timelimit,
                        UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(), LanguageUtil.judgeLanguage())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            JSONObject object = JSONObject.parseObject(result);
                            mLoadingDialog.dismiss();
                            if (object.getString("result").equals("0000")){
                                if (timelimit.equals("1")){
                                    item.setMORNINGVALUE(degree);
                                }else if (timelimit.equals("2")){
                                    item.setAFTERNOONVALUE(degree);
                                }else if (timelimit.equals("3")){
                                    item.setEVENINGVALUE(degree);
                                }

                                notifyDataSetChanged();
                            }else{
                                Toast.makeText(context, context.getResources().getString(R.string.DataNotEmpty), Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
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
//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });
    }
}
