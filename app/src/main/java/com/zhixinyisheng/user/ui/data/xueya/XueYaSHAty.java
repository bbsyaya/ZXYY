package com.zhixinyisheng.user.ui.data.xueya;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.util.SPUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.data.BLE.common.StringUtils;
import com.zhixinyisheng.user.ui.data.BLE.control.BtSerializeation;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEReceiver;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEService;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.view.blood.MagicProgressCircle;
import com.zhixinyisheng.user.view.blood.MagicProgressCircle1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_BLOOD_DATA;

/**
 * 血压的手环界面
 * Created by 焕焕 on 2016/10/30.
 */
public class XueYaSHAty extends BaseAty {
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
    @Bind(R.id.tv_time)
    TextView tv_time;
    SPUtils spUtils;
    //手环广播
    BLEReceiver bleReceiver;
    String bleStr = "";//手环返回数据
    // 血压
    List<String> list_xy_rq = new ArrayList<String>();
    List<Integer> list_xy_gnum = new ArrayList<Integer>();
    List<Integer> list_xy_dnum = new ArrayList<Integer>();
    int int_xy_num = 0;
    int current1 = 0;
    int current2 = 0;
    boolean isAnimActive1 = false;
    boolean isAnimActive2 = false;

    @Override
    public int getLayoutId() {
        return R.layout.aty_xueyash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tongbuSJ();
        cjsTvt.setText(R.string.home_bloodPressure);
        ivBack.setVisibility(View.VISIBLE);

    }

    // 同步数据
    private void tongbuSJ() {
        //清空
        bleStr = "";
        TestService.mService.writeRXCharacteristic(BtSerializeation
                .syncTime());
    }

    @Override
    protected void onResume() {
        super.onResume();
        bleReceiver = new BLEReceiver() {
            @Override
            public void bleReceive(Intent intent) {
                try {
                    jiexiSHShuju(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //注册广播
        LocalBroadcastManager.getInstance(this).registerReceiver(bleReceiver,
                TestService.makeGattUpdateIntentFilter());
        spUtils = new SPUtils("xueyatime");
        String time = (String) spUtils.get("xueyatime", getString(R.string.NoUpdate));
        if (!time.equals(getString(R.string.NoUpdate))) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(Long.parseLong(time));
            tv_time.setText(dateString + getString(R.string.update));
        } else {
            tv_time.setText(getString(R.string.NoUpdate));
        }
        if (TestService.mDevice != null) {
            //同步手环时间获取数据
            tongbuSJ();
            spUtils.put("xueyatime", System.currentTimeMillis() + "");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(System.currentTimeMillis());
            tv_time.setText(dateString + getString(R.string.update));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(
                    bleReceiver);
        } catch (Exception e) {
        }
    }

    //解析手环返回数据
    private void jiexiSHShuju(Intent intent) {
//        String strf = intent.getStringExtra(BLEService.EXTRA_DATA);

        final byte[] txValue = intent.getByteArrayExtra(BLEService.EXTRA_DATA);
        if (txValue == null) {
            return;
        }
        String strf = StringUtils.bytesToHexString(txValue);

        if (strf == null) {
            return;
        }
        strf = strf.replace(" ", "");
        if (String.valueOf(strf.charAt(0)).toUpperCase()
                .equals("A")) {
            if (String.valueOf(strf.charAt(17)).equals("3")
                    && String.valueOf(strf.charAt(21)).equals("4")) {// 数据返回完毕
                shljcgTZ();
            }
        }


        if (strf.length() >= 1) {
            if (String.valueOf(strf.charAt(0)).toUpperCase()
                    .equals("A")) {
                bleStr += "@";
            }
            bleStr += strf;
        }


    }

    //接受手环返回数据
    private void shljcgTZ() {
        jiexiXY();
        if (list_xy_rq.size() == 0) {
            tvXueyayuanxinGaoya.setText("0");
            tvXueyayuanxinDiya.setText("0");
        } else {
            tvXueyayuanxinGaoya.setText(list_xy_gnum.get(list_xy_gnum.size() - 1) + "");
            tvXueyayuanxinDiya.setText(list_xy_dnum.get(list_xy_dnum.size() - 1) + "");
        }

        String gaoYa = tvXueyayuanxinGaoya.getText().toString();
        String diYa = tvXueyayuanxinDiya.getText().toString();
        //把EditText输入高压值传给圆圈内高压值
        if (!gaoYa.equals("")) {
            current1 = Integer.valueOf(gaoYa);
            onReRandomPercent1();
        }
        //把EditText输入低压值传给圆圈内低压值
        if (!diYa.equals("")) {
            current2 = Integer.valueOf(diYa);
            onReRandomPercent2();
        }
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

    public void jiexiXY() {
//        list_xl_rq.clear();
//        list_xl_num.clear();
        String[] strc = bleStr.split("@");
        for (int i = 1; i < strc.length; i++) {
            String str = strc[i];
            if (String.valueOf(str.charAt(17)).equals("3")
                    && String.valueOf(str.charAt(21)).equals("6")) {
                Log.e("返回血压字符串", str);
                String str_rq = str.substring(26, 30);
                String str_rqze = shiliuZhaunER(str_rq);
                String str_nian = Integer.valueOf(str_rqze.substring(1, 7), 2) + "";
                String str_yue = Integer.valueOf(str_rqze.substring(7, 11), 2) + "";
                String str_ri = Integer.valueOf(str_rqze.substring(11, 16), 2) + "";
                if (str_yue.length() == 1) {
                    str_yue = "0" + str_yue;
                }
                if (str_ri.length() == 1) {
                    str_ri = "0" + str_ri;
                }
                String str_zgrq = "20" + str_nian
                        + "-" + str_yue
                        + "-" + str_ri;
                Log.e("血压日期", str_zgrq);
                list_xy_rq.add(str_zgrq);
                int_xy_num = 0;
                String str1 = "";
                str1 = str.substring(30, 32);
                int_xy_num = Integer.valueOf(str1, 16);
                Log.e("高血压", int_xy_num + "");
                list_xy_gnum.add(int_xy_num);

                str1 = str.substring(32, 34);
                int_xy_num = Integer.valueOf(str1, 16);
                Log.e("低血压", int_xy_num + "");
                list_xy_dnum.add(int_xy_num);
            }
        }
    }

    // 十六进制转二进制
    public String shiliuZhaunER(String str) {
        String str_fh = "";
        for (int k = 0; k < str.length(); k++) {
            String strje_d = Integer.toBinaryString(Integer.valueOf(
                    str.charAt(k) + "", 16));
            if (strje_d.length() == 1) {
                strje_d = "000" + strje_d;
            } else if (strje_d.length() == 2) {
                strje_d = "00" + strje_d;
            } else if (strje_d.length() == 3) {
                strje_d = "0" + strje_d;
            }
            str_fh += strje_d;
        }
        return str_fh;
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case 0:
                showToast(getString(R.string.tijiaochenggong));
                IS_CHANGE_BLOOD_DATA = true;
                finish();
                break;
        }
        super.onSuccess(result, call, response, what);

    }

    @OnClick({R.id.cjs_rlb, R.id.btn_xueyashuru, R.id.iv_tongbu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.btn_xueyashuru:
                String gaoYa = tvXueyayuanxinGaoya.getText().toString();
                String diYa = tvXueyayuanxinDiya.getText().toString();
                if (gaoYa.equals("") || diYa.equals("")) {
                    showToast(getString(R.string.qingshuru));
                } else {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DataUrl.class).addXueYa(
                            userId, gaoYa, diYa, time, phone, secret, LanguageUtil.judgeLanguage()), 0);
                }
                break;
            case R.id.iv_tongbu:
                //同步手环时间获取数据
                tongbuSJ();
                spUtils.put("xueyatime", System.currentTimeMillis() + "");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(System.currentTimeMillis());
                tv_time.setText(dateString + getString(R.string.update));
                break;
        }
    }
}
