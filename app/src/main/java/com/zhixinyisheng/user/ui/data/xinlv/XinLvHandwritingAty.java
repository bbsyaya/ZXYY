package com.zhixinyisheng.user.ui.data.xinlv;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.view.BatteryView2;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.R.id.btn_xinlvshuru;
import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_HEART_DATA;

/**
 * 心率的手写界面
 * Created by 焕焕 on 2016/10/30.
 */
public class XinLvHandwritingAty extends BaseAty {
    //心的高宽
    public static int xxwidth;
    public static int xxheight;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.mybatteryview2)
    BatteryView2 mybatteryview2;
    @Bind(R.id.xinlv_xin)
    ImageView xinlvXin;
    @Bind(R.id.tv_xinlvshoudongshuru)
    TextView tvXinlvshoudongshuru;
    @Bind(R.id.et_xinlvshoudonshuru)
    EditText etXinlvshoudonshuru;
    @Bind(R.id.btn_xinlvshuru)
    Button btnCommit;
    float num;
    float no = 0;

    @Override
    public int getLayoutId() {
        return R.layout.aty_xinlvhandwriting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(getResources().getString(R.string.home_heartRate));
        ivBack.setVisibility(View.VISIBLE);
        //得到ImageView宽度和高度
        ViewTreeObserver vto = xinlvXin.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                xinlvXin.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                xxwidth = xinlvXin.getWidth();
                xxheight = xinlvXin.getHeight();
            }
        });
        etXinlvshoudonshuru.addTextChangedListener(textWatcher);
        Logger.e("23232", isZh());
    }

    private String isZh() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

            tvXinlvshoudongshuru.setText(etXinlvshoudonshuru.getText().toString());

            if (etXinlvshoudonshuru.getText().toString().equals("")) {
                mybatteryview2.setPower(0);
            } else {
                num = Integer.parseInt(etXinlvshoudonshuru.getText().toString());

                final Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        if (no > num) {
//							t.cancel();
                            no--;
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        mybatteryview2.setPower(no);//调用绘画方法
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else if (no < num) {
                            no++;
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        mybatteryview2.setPower(no);//调用绘画方法
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }
                };
                t.schedule(tt, 0, 50);

            }

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            // batteryView2.setPower(500);
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            if (!arg0.toString().trim().equals("")){
                btnCommit.setBackgroundResource(R.drawable.shape_main_color);
            }else{
                btnCommit.setBackgroundResource(R.drawable.shape_gray_color);
            }
        }
    };

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case 0:
                showToast(getResources().getString(R.string.submitSuccess));
                IS_CHANGE_HEART_DATA = true;
                finish();
                break;
        }
        super.onSuccess(result, call, response, what);

    }

    @OnClick({btn_xinlvshuru, R.id.ll_xinlvshoudong, R.id.cjs_rlb})
    public void onClick(View view) {
        switch (view.getId()) {
            case btn_xinlvshuru:
                String ave = etXinlvshoudonshuru.getText().toString();
                if (ave == null || ave.equals("")) {
                    showToast(getResources().getString(R.string.pleaseInputData));
                    return;
                }
                double data = Double.parseDouble(ave);
                if (data < 30 || data > 210) {
                    showToast(getResources().getString(R.string.PleaseEnterCorrectHeartRate));
                    return;
                }
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(DataUrl.class).addXinLv(
                        userId, ave + "", time, phone, secret), 0);

                break;
            case R.id.ll_xinlvshoudong:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.cjs_rlb:
                finish();
                break;
        }
    }

}
