package com.zhixinyisheng.user.ui.data.xueya;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.view.blood.MagicProgressCircle;
import com.zhixinyisheng.user.view.blood.MagicProgressCircle1;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_BLOOD_DATA;

/**
 * 血压的手写界面
 * Created by 焕焕 on 2016/10/30.
 */
public class XueYaHandwritingAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.main_mpc_gy)
    MagicProgressCircle mainMpcGy;
    @Bind(R.id.main_mpc_dy)
    MagicProgressCircle1 mainMpcDy;
    @Bind(R.id.tv_xueyayuanxin_gaoya)
    TextView tvXueyayuanxinGaoya;
    @Bind(R.id.tv_xueyayuanxin_diya)
    TextView tvXueyayuanxinDiya;
    @Bind(R.id.et_xueyashoudonggaoya)
    EditText etXueyashoudonggaoya;
    @Bind(R.id.et_xueyashoudongdiya)
    EditText etXueyashoudongdiya;
    @Bind(R.id.btn_xueyashuru)
    Button btnXueyashuru;
    boolean isAnimActive1 = false;
    boolean isAnimActive2 = false;


    int current1 = 0;
    int current2 = 0;

    @Override
    public int getLayoutId() {
        return R.layout.aty_xueyahandwriting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.home_bloodPressure);
        ivBack.setVisibility(View.VISIBLE);
        etXueyashoudonggaoya.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //把EditText输入高压值传给圆圈内高压值
                tvXueyayuanxinGaoya.setText(etXueyashoudonggaoya.getText().toString());
                if (!charSequence.toString().equals("")) {
                    current1 = Integer.valueOf(charSequence.toString());
                    onReRandomPercent1();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().equals("")){
                    btnXueyashuru.setBackgroundResource(R.drawable.shape_main_color);
                }else{
                    btnXueyashuru.setBackgroundResource(R.drawable.shape_gray_color);
                }

            }
        });

        etXueyashoudongdiya.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //把EditText输入低压值传给圆圈内低压值
                tvXueyayuanxinDiya.setText(etXueyashoudongdiya.getText().toString());
                if (!charSequence.toString().equals("")) {
                    current2 = Integer.valueOf(charSequence.toString());
                    onReRandomPercent2();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().equals("")){
                    btnXueyashuru.setBackgroundResource(R.drawable.shape_main_color);
                }else{
                    btnXueyashuru.setBackgroundResource(R.drawable.shape_gray_color);
                }
            }
        });
    }

    // 刷新外圈
    public void onReRandomPercent1() {
        if (isAnimActive1) {
            return;
        }
        anim1();
    }

    // 刷新内圈
    public void onReRandomPercent2() {
        if (isAnimActive2) {
            return;
        }
        anim2();
    }

    // 改变progress
    private void anim1() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mainMpcGy, "percent", 0, current1 / (279f * (290 / 360f))));
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(2000);
        set.start();
    }

    // 改变内部progress
    private void anim2() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mainMpcDy, "percent", 0, current2
                / (178f * (290 / 360f))));
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(2000);
        set.start();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case 0:
                showToast(getString(R.string.tijiaochenggong));
                IS_CHANGE_BLOOD_DATA  = true;
                finish();
                break;
        }
        super.onSuccess(result, call, response, what);

    }

    @OnClick({R.id.btn_xueyashuru, R.id.ll_xueyashoudong, R.id.cjs_rlb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_xueyashuru:
                String gaoYa = etXueyashoudonggaoya.getText().toString();
                String diYa = etXueyashoudongdiya.getText().toString();
                if (gaoYa.equals("") || diYa.equals("")) {
                    showToast(getString(R.string.qingshuru));
                    return;
                }
                double dataGao = Double.parseDouble(gaoYa);
                double dataDi = Double.parseDouble(diYa);
                if (dataGao > 250 || dataDi > 250) {
                    showToast(getString(R.string.qingshuruzhengque));
                    return;
                }
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(DataUrl.class).addXueYa(
                        userId, gaoYa, diYa, time, phone, secret, LanguageUtil.judgeLanguage()), 0);
                break;
            case R.id.ll_xueyashoudong:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.cjs_rlb:
                finish();
                break;
        }
    }
}
