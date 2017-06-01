package com.zhixinyisheng.user.ui.data.tiwen;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
 * 体温添加的界面
 * Created by 焕焕 on 2016/11/3.
 */
public class TiWenAddAty extends BaseAty {

    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.edittext)
    EditText edittext;
    //    @Bind(R.id.tv_content)
//    TextView content;
//    @Bind(R.id.testtime)
//    RelativeLayout testtime;
    @Bind(R.id.temp_btn_sure)
    Button tempBtnSure;
    @Bind(R.id.tiwen_checkbox1)
    CheckBox checkBox1;
    @Bind(R.id.tiwen_checkbox2)
    CheckBox checkBox2;
    @Bind(R.id.tiwen_checkbox3)
    CheckBox checkBox3;


    private PopupWindow window;
    //选择的时间段
    private String result = "";

    @Override
    public int getLayoutId() {
        return R.layout.aty_tiwenadd;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText("体温");
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestData() {

    }


    @OnClick({R.id.cjs_rlb, R.id.temp_btn_sure, R.id.tiwen_ll_1, R.id.tiwen_ll_2, R.id.tiwen_ll_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.temp_btn_sure:
                double temp;
                //获取输入的体温值
                try {
                    temp = Double.parseDouble(edittext.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    temp = 0;
                }

                if (temp > 35 && temp < 45) {
                    if (!result.equals("")) {

                        //上传体温值及测量时间到服务器
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).addTiWen(
                                userId, edittext.getText().toString(), time, result, phone, secret, LanguageUtil.judgeLanguage()), 0);
//                        postTemp();
                    } else {
                        Toast.makeText(TiWenAddAty.this, "请选择测量时段", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TiWenAddAty.this, "请先填写正确的体温", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tiwen_ll_1:
                jianpan(view);
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                result = "1";
                break;
            case R.id.tiwen_ll_2:
                jianpan(view);
                checkBox1.setChecked(false);
                checkBox2.setChecked(true);
                checkBox3.setChecked(false);
                result = "2";
                break;
            case R.id.tiwen_ll_3:
                jianpan(view);
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(true);
                result = "3";
                break;

//            case R.id.testtime:
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
//                showPopWindow();
//                break;
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
