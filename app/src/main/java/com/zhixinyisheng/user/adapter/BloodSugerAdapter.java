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
import com.zhixinyisheng.user.domain.datas.BloodSugerDatas;
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
 * 血糖适配器
 * Created by 焕焕 on 2017/1/13.
 */
public class BloodSugerAdapter extends CommonAdapter<BloodSugerDatas.ListBean>{
    Context context;
    private LoadingDialog mLoadingDialog;
    //血糖值的颜色
    private int rangeValue,colorFlag;
    public BloodSugerAdapter(Context context, List<BloodSugerDatas.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context =context;
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(context);
        }
    }

    @Override
    public void convert(ViewHolder holder, final BloodSugerDatas.ListBean item, int positon) {
        holder.setTextViewText(R.id.item_date_blood,item.getByTime());
        holder.setTextViewText(R.id.item_mor_befor,item.getBREAKFASTBEFORE());
        holder.setTextViewText(R.id.item_mor_after,item.getBREAKFASTAFTER());
        holder.setTextViewText(R.id.item_lunch_before,item.getLUNCHBEFORE());
        holder.setTextViewText(R.id.item_lunch_after,item.getLUNCHAFTER());
        holder.setTextViewText(R.id.item_dinner_before,item.getDINNERBEFORE());
        holder.setTextViewText(R.id.item_dinner_after,item.getDINNERAFTER());
        holder.setTextViewText(R.id.item_sleebefore,item.getSLEEPBEFORE());
        if (item.getBREAKFASTBEFOREC()==1){
            holder.setTextViewTextColor(R.id.item_mor_befor,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (item.getBREAKFASTBEFOREC()==2){
            holder.setTextViewTextColor(R.id.item_mor_befor,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_mor_befor,context.getResources().getColor(R.color.bloodsugar_danger));
        }

        if (item.getBREAKFASTAFTERC()==1){
            holder.setTextViewTextColor(R.id.item_mor_after,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (item.getBREAKFASTAFTERC()==2){
            holder.setTextViewTextColor(R.id.item_mor_after,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_mor_after,context.getResources().getColor(R.color.bloodsugar_danger));
        }

        if (item.getLUNCHBEFOREC()==1){
            holder.setTextViewTextColor(R.id.item_lunch_before,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (item.getLUNCHBEFOREC()==2){
            holder.setTextViewTextColor(R.id.item_lunch_before,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_lunch_before,context.getResources().getColor(R.color.bloodsugar_danger));
        }

        if (item.getLUNCHAFTERC()==1){
            holder.setTextViewTextColor(R.id.item_lunch_after,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (item.getLUNCHAFTERC()==2){
            holder.setTextViewTextColor(R.id.item_lunch_after,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_lunch_after,context.getResources().getColor(R.color.bloodsugar_danger));
        }

        if (item.getDINNERBEFOREC()==1){
            holder.setTextViewTextColor(R.id.item_dinner_before,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (item.getDINNERBEFOREC()==2){
            holder.setTextViewTextColor(R.id.item_dinner_before,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_dinner_before,context.getResources().getColor(R.color.bloodsugar_danger));
        }

        if (item.getDINNERAFTERC()==1){
            holder.setTextViewTextColor(R.id.item_dinner_after,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (item.getDINNERAFTERC()==2){
            holder.setTextViewTextColor(R.id.item_dinner_after,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_dinner_after,context.getResources().getColor(R.color.bloodsugar_danger));
        }

        if (item.getSLEEPBEFOREC()==1){
            holder.setTextViewTextColor(R.id.item_sleebefore,context.getResources().getColor(R.color.bloodsugar_normal));
        }else if (item.getSLEEPBEFOREC()==2){
            holder.setTextViewTextColor(R.id.item_sleebefore,context.getResources().getColor(R.color.bloodsugar_warning));
        }else{
            holder.setTextViewTextColor(R.id.item_sleebefore,context.getResources().getColor(R.color.bloodsugar_danger));
        }
        holder.getView(R.id.rl_mor_befor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getBREAKFASTBEFORE()==null){
                    value = "";
                }else{
                    value=item.getBREAKFASTBEFORE();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseGlucose))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double edg = 0;
                                try {
                                    edg = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    edg = 0;
                                    e.printStackTrace();
                                }
                                if (edg>0&&edg<=30){
                                    mLoadingDialog.showLoadingDialog(null);
                                    if (edg>=3.9 && edg<=5.6){
                                        rangeValue=1;
                                        colorFlag=1;
                                    }else if(edg>=2.8 && edg<=3.8 || edg>=5.7 && edg<=6.7){
                                        rangeValue=2;
                                        colorFlag=2;
                                    }
                                    else if(edg>=6.7 || edg<=2.8){
                                        rangeValue=3;
                                        colorFlag=3;
                                    }
                                    postAddData(item,item.getByTime(),degree,"1",rangeValue,colorFlag);
                                }else{
                                    Toast.makeText(context, R.string.qingtianxiezhengquedexuetangzhi, Toast.LENGTH_SHORT).show();
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

        holder.getView(R.id.rl_mor_after).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getBREAKFASTAFTER()==null){
                    value = "";
                }else{
                    value=item.getBREAKFASTAFTER();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseGlucose))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double edg = 0;
                                try {
                                    edg = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    edg = 0;
                                    e.printStackTrace();
                                }
                                if (edg>0&&edg<=30){
                                    mLoadingDialog.showLoadingDialog(null);
                                    if (edg>=6.7 && edg<=9.4){
                                        rangeValue=1;
                                        colorFlag=1;
                                    }else if(edg>=3.8 && edg<=6.6 || edg>=9.5 && edg<=11.1){
                                        rangeValue=2;
                                        colorFlag=2;
                                    }
                                    else if(edg>=11.1 || edg<=3.8){
                                        rangeValue=3;
                                        colorFlag=3;
                                    }
                                    postAddData(item,item.getByTime(),degree,"2",rangeValue,colorFlag);
                                }else{
                                    Toast.makeText(context, R.string.qingtianxiezhengquedexuetangzhi, Toast.LENGTH_SHORT).show();
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

        holder.getView(R.id.rl_lunch_before).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getLUNCHBEFORE()==null){
                    value = "";
                }else{
                    value=item.getLUNCHBEFORE();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseGlucose))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double edg = 0;
                                try {
                                    edg = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    edg = 0;
                                    e.printStackTrace();
                                }
                                if (edg>0&&edg<=30){
                                    mLoadingDialog.showLoadingDialog(null);
                                    if (edg>=3.9 && edg<=5.6){
                                        rangeValue=1;
                                        colorFlag=1;
                                    }else if(edg>=2.8 && edg<=3.8 || edg>=5.7 && edg<=6.7){
                                        rangeValue=2;
                                        colorFlag=2;
                                    }
                                    else if(edg>=6.7 || edg<=2.8){
                                        rangeValue=3;
                                        colorFlag=3;
                                    }
                                    postAddData(item,item.getByTime(),degree,"3",rangeValue,colorFlag);
                                }else{
                                    Toast.makeText(context, R.string.qingtianxiezhengquedexuetangzhi, Toast.LENGTH_SHORT).show();
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

        holder.getView(R.id.rl_lunch_after).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getLUNCHAFTER()==null){
                    value = "";
                }else{
                    value=item.getLUNCHAFTER();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseGlucose))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double edg = 0;
                                try {
                                    edg = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    edg = 0;
                                    e.printStackTrace();
                                }
                                if (edg>0&&edg<=30){
                                    mLoadingDialog.showLoadingDialog(null);
                                    if (edg>=6.7 && edg<=9.4){
                                        rangeValue=1;
                                        colorFlag=1;
                                    }else if(edg>=3.8 && edg<=6.6 || edg>=9.5 && edg<=11.1){
                                        rangeValue=2;
                                        colorFlag=2;
                                    }
                                    else if(edg>=11.1 || edg<=3.8){
                                        rangeValue=3;
                                        colorFlag=3;
                                    }
                                    postAddData(item,item.getByTime(),degree,"4",rangeValue,colorFlag);
                                }else{
                                    Toast.makeText(context, R.string.qingtianxiezhengquedexuetangzhi, Toast.LENGTH_SHORT).show();
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

        holder.getView(R.id.rl_dinner_before).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getDINNERBEFORE()==null){
                    value = "";
                }else{
                    value=item.getDINNERBEFORE();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseGlucose))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double edg = 0;
                                try {
                                    edg = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    edg = 0;
                                    e.printStackTrace();
                                }
                                if (edg>0&&edg<=30){
                                    mLoadingDialog.showLoadingDialog(null);
                                    if (edg>=3.9 && edg<=5.6){
                                        rangeValue=1;
                                        colorFlag=1;
                                    }else if(edg>=2.8 && edg<=3.8 || edg>=5.7 && edg<=6.7){
                                        rangeValue=2;
                                        colorFlag=2;
                                    }
                                    else if(edg>=6.7 || edg<=2.8){
                                        rangeValue=3;
                                        colorFlag=3;
                                    }
                                    postAddData(item,item.getByTime(),degree,"5",rangeValue,colorFlag);
                                }else{
                                    Toast.makeText(context, R.string.qingtianxiezhengquedexuetangzhi, Toast.LENGTH_SHORT).show();
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

        holder.getView(R.id.rl_dinner_after).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getDINNERAFTER()==null){
                    value = "";
                }else{
                    value=item.getDINNERAFTER();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseGlucose))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double edg = 0;
                                try {
                                    edg = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    edg = 0;
                                    e.printStackTrace();
                                }
                                if (edg>0&&edg<=30){
                                    mLoadingDialog.showLoadingDialog(null);
                                    if (edg>=6.7 && edg<=9.4){
                                        rangeValue=1;
                                        colorFlag=1;
                                    }else if(edg>=3.8 && edg<=6.6 || edg>=9.5 && edg<=11.1){
                                        rangeValue=2;
                                        colorFlag=2;
                                    }
                                    else if(edg>=11.1 || edg<=3.8){
                                        rangeValue=3;
                                        colorFlag=3;
                                    }
                                    postAddData(item,item.getByTime(),degree,"6",rangeValue,colorFlag);
                                }else{
                                    Toast.makeText(context, R.string.qingtianxiezhengquedexuetangzhi, Toast.LENGTH_SHORT).show();
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

        holder.getView(R.id.rl_sleepbefore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if (item.getSLEEPBEFORE()==null){
                    value = "";
                }else{
                    value=item.getSLEEPBEFORE();
                }
                new MaterialDialogForEditText(context)
                        .setMDNoTitle(false)
                        .setMDTitle(context.getResources().getString(R.string.pleaseGlucose))
                        .setMDMessage(value)
                        .setMDConfirmBtnClick(new MaterialDialogForEditText.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick(String degree) {
                                double edg = 0;
                                try {
                                    edg = Double.parseDouble(degree);
                                } catch (NumberFormatException e) {
                                    edg = 0;
                                    e.printStackTrace();
                                }
                                if (edg>0&&edg<=30){
                                    mLoadingDialog.showLoadingDialog(null);
                                    if (edg>=3.9 && edg<=5.6){
                                        rangeValue=1;
                                        colorFlag=1;
                                    }else if(edg>=2.8 && edg<=3.8 || edg>=5.7 && edg<=6.7){
                                        rangeValue=2;
                                        colorFlag=2;
                                    }
                                    else if(edg>=6.7 || edg<=2.8){
                                        rangeValue=3;
                                        colorFlag=3;
                                    }
                                    postAddData(item,item.getByTime(),degree,"7",rangeValue,colorFlag);
                                }else{
                                    Toast.makeText(context, R.string.qingtianxiezhengquedexuetangzhi, Toast.LENGTH_SHORT).show();
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
    private void postAddData(final BloodSugerDatas.ListBean item, String time, final String degree, final String type, int rangeValue, final int colorFlag) {
        RetrofitUtils.createApi(DataUrl.class)
                .addXueTang(UserManager.getUserInfo().getUserId(),degree,type,rangeValue+"",time,colorFlag+"",
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
                                if (type.equals("1")){
                                    item.setBREAKFASTBEFORE(degree);
                                    item.setBREAKFASTBEFOREC(colorFlag);
                                }else if (type.equals("2")){
                                    item.setBREAKFASTAFTER(degree);
                                    item.setBREAKFASTAFTERC(colorFlag);
                                }else if (type.equals("3")){
                                    item.setLUNCHBEFORE(degree);
                                    item.setLUNCHBEFOREC(colorFlag);
                                }else if (type.equals("4")){
                                    item.setLUNCHAFTER(degree);
                                    item.setLUNCHAFTERC(colorFlag);
                                }else if (type.equals("5")){
                                    item.setDINNERBEFORE(degree);
                                    item.setDINNERBEFOREC(colorFlag);
                                }else if (type.equals("6")){
                                    item.setDINNERAFTER(degree);
                                    item.setDINNERAFTERC(colorFlag);
                                }else if (type.equals("7")){
                                    item.setSLEEPBEFORE(degree);
                                    item.setSLEEPBEFOREC(colorFlag);
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
