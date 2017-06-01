package com.zhixinyisheng.user.ui.data.xuetang;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 血糖数据添加
 * Created by 焕焕 on 2016/11/7.
 */
public class XueTangAddAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.edittext_blood)
    EditText edtext;
   @Bind(R.id.xuetang_checkbox1)
    CheckBox xuetang_checkbox1;
    @Bind(R.id.xuetang_checkbox2)
    CheckBox xuetang_checkbox2;
    @Bind(R.id.xuetang_checkbox3)
    CheckBox xuetang_checkbox3;
    @Bind(R.id.xuetang_checkbox4)
    CheckBox xuetang_checkbox4;
    @Bind(R.id.xuetang_checkbox5)
    CheckBox xuetang_checkbox5;
    @Bind(R.id.xuetang_checkbox6)
    CheckBox xuetang_checkbox6;
    @Bind(R.id.xuetang_checkbox7)
    CheckBox xuetang_checkbox7;


    //测试时间的结果
    private String result="";
    //血糖值的颜色
    private int rangeValue,colorFlag;
    private int type;
    @Override
    public int getLayoutId() {
        return R.layout.aty_xuetangadd;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText("血糖");
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestData() {

    }



    @OnClick({R.id.cjs_rlb, R.id.temp_btn_sure_sugar, R.id.xuetang_ll_1, R.id.xuetang_ll_2, R.id.xuetang_ll_3,
            R.id.xuetang_ll_4, R.id.xuetang_ll_5, R.id.xuetang_ll_6, R.id.xuetang_ll_7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.temp_btn_sure_sugar:
                //int edg= 0;
                double edg;
                try {
                    //edg = Integer.parseInt(edtext.getText().toString());
                    edg =Double.parseDouble(edtext.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    edg= 0;
                }
                if (edg>0 && edg<30  ){
                    if (type>0) {
                        if (type==1||type==3 ||type==5|| type==7){
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
                        }else{
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
                        }

                        //上传血糖值及测量时间到服务器
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).addXueTang(
                                userId,edtext.getText().toString(),type+"",rangeValue+"",time,colorFlag+"",phone,secret, LanguageUtil.judgeLanguage()),0);
//                        postBlood();
                    }else {
                        Toast.makeText(XueTangAddAty.this, "请选择测量时段", Toast.LENGTH_SHORT).show();
                    }
                    //  postBlood();
                }else {
                    Toast.makeText(XueTangAddAty.this, "请填写正确的血糖值(0-30之间)", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.xuetang_ll_1:
                jianpan(view);
                xuetang_checkbox1.setChecked(true);
                xuetang_checkbox2.setChecked(false);
                xuetang_checkbox3.setChecked(false);
                xuetang_checkbox4.setChecked(false);
                xuetang_checkbox5.setChecked(false);
                xuetang_checkbox6.setChecked(false);
                xuetang_checkbox7.setChecked(false);
                type = 1;
                break;
            case R.id.xuetang_ll_2:
                jianpan(view);
                xuetang_checkbox1.setChecked(false);
                xuetang_checkbox2.setChecked(true);
                xuetang_checkbox3.setChecked(false);
                xuetang_checkbox4.setChecked(false);
                xuetang_checkbox5.setChecked(false);
                xuetang_checkbox6.setChecked(false);
                xuetang_checkbox7.setChecked(false);
                type = 2;
                break;
            case R.id.xuetang_ll_3:
                jianpan(view);
                xuetang_checkbox1.setChecked(false);
                xuetang_checkbox2.setChecked(false);
                xuetang_checkbox3.setChecked(true);
                xuetang_checkbox4.setChecked(false);
                xuetang_checkbox5.setChecked(false);
                xuetang_checkbox6.setChecked(false);
                xuetang_checkbox7.setChecked(false);
                type = 3;
                break;
            case R.id.xuetang_ll_4:
                jianpan(view);
                xuetang_checkbox1.setChecked(false);
                xuetang_checkbox2.setChecked(false);
                xuetang_checkbox3.setChecked(false);
                xuetang_checkbox4.setChecked(true);
                xuetang_checkbox5.setChecked(false);
                xuetang_checkbox6.setChecked(false);
                xuetang_checkbox7.setChecked(false);
                type = 4;
                break;
            case R.id.xuetang_ll_5:
                jianpan(view);
                xuetang_checkbox1.setChecked(false);
                xuetang_checkbox2.setChecked(false);
                xuetang_checkbox3.setChecked(false);
                xuetang_checkbox4.setChecked(false);
                xuetang_checkbox5.setChecked(true);
                xuetang_checkbox6.setChecked(false);
                xuetang_checkbox7.setChecked(false);
                type = 5;
                break;
            case R.id.xuetang_ll_6:
                jianpan(view);
                xuetang_checkbox1.setChecked(false);
                xuetang_checkbox2.setChecked(false);
                xuetang_checkbox3.setChecked(false);
                xuetang_checkbox4.setChecked(false);
                xuetang_checkbox5.setChecked(false);
                xuetang_checkbox6.setChecked(true);
                xuetang_checkbox7.setChecked(false);
                type = 6;
                break;
            case R.id.xuetang_ll_7:
                jianpan(view);
                xuetang_checkbox1.setChecked(false);
                xuetang_checkbox2.setChecked(false);
                xuetang_checkbox3.setChecked(false);
                xuetang_checkbox4.setChecked(false);
                xuetang_checkbox5.setChecked(false);
                xuetang_checkbox6.setChecked(false);
                xuetang_checkbox7.setChecked(true);
                type = 7;
                break;



        }
    }
    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        showToast("提交成功!");
        finish();
    }
    private void jianpan(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

}
