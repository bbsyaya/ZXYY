package com.zhixinyisheng.user.ui.data.shuimian;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.util.SPUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.MainActivity;
import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.common.StringUtils;
import com.zhixinyisheng.user.ui.data.BLE.control.BtSerializeation;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEReceiver;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEService;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.blood.MagicProgressCircle1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 睡眠质量
 * Created by 焕焕 on 2016/11/1.
 */
public class ShuiMianFgt extends BaseFgt {
    @Bind(R.id.iv_tongbu)
    ImageView ivTongbu;
    @Bind(R.id.iv_qushi)
    ImageView ivQushi;
    @Bind(R.id.tv_shuimian)
    TextView tvShuimian;
    @Bind(R.id.tv_shuimian_zong)
    TextView tvShuimianZong;
    @Bind(R.id.tv_shuimian_shen)
    TextView tvShuimianShen;
    @Bind(R.id.tv_shuimian_qian)
    TextView tvShuimianQian;
    @Bind(R.id.ll_shuimian)
    LinearLayout llShuimian;
    @Bind(R.id.sv_shuimian)
    ScrollView svShuimian;

    @Bind(R.id.tv_bottom_ngc)
    TextView tv_bottom_ngc;
    @Bind(R.id.mpc_shuimian)
    MagicProgressCircle1 mpc_shuimian;
    @Bind(R.id.tv_time)
    TextView tv_time;
    //手环广播
    BLEReceiver bleReceiver;
    String bleStr = "";//手环返回数据
    // 睡眠
    List<String> list_sm_rq = new ArrayList<String>();
    List<Integer> list_sm_num = new ArrayList<Integer>();
    boolean isAnimActive1 = false;
    SPUtils spUtils;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_shuimian;
    }

    @Override
    public void initData() {
        Content.sleep++;
        linkDevice();


    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        linkDevice();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onUserInvisible() {
        super.onUserInvisible();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bleReceiver);
    }

    private void linkDevice() {
        TestService.no_shsz = 1;
        bleStr = "";
        tv_bottom_ngc.setHeight(MainActivity.di_height - 10);
        if (TestService.mDevice != null) {
            onReRandomPercent(0);
            //注册广播
            bleReceiver = new BLEReceiver() {
                @Override
                public void bleReceive(Intent intent) {
                    String action = intent.getAction();
//                    if (action.equals(BLEService.ACTION_DATA_AVAILABLE)) {//接受数据
                    //解析手环返回数据
                    try {
                        jiexiSHShuju(intent);
                    } catch (Exception e) {
//                        handler.sendEmptyMessage(10);
                    }
//                    }

                }
            };
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bleReceiver,
                    TestService.makeGattUpdateIntentFilter());
        } else {
            onReRandomPercent(0);
        }
        spUtils = new SPUtils("shuimiantime");
        String time = (String) spUtils.get("shuimiantime", getResources().getString(R.string.NoUpdate));
        if (!time.equals(getResources().getString(R.string.NoUpdate))) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(Long.parseLong(time));
            tv_time.setText(dateString + getResources().getString(R.string.update));
        } else {
            tv_time.setText(getResources().getString(R.string.NoUpdate));
        }

        if (TestService.mDevice != null) {
            //同步手环时间获取数据
            tongbuSJ();
            spUtils.put("shuimiantime", System.currentTimeMillis() + "");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(System.currentTimeMillis());
            tv_time.setText(dateString + getResources().getString(R.string.update));
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
        jiexiSM();
    }

    public void jiexiSM() {
        list_sm_rq.clear();
        list_sm_num.clear();
        String[] strc = bleStr.split("@");
        int time3 = 0, time2 = 0, xljc = 0;
        String str_kssm = "", str_tcsm = "", str_xlsj = "";
        for (int i = 1; i < strc.length; i++) {
            if (String.valueOf(strc[i].charAt(17)).equals("3")
                    && String.valueOf(strc[i].charAt(21)).equals("3")) {
                Log.e("睡眠数据", strc[i]);
                String str1 = strc[i].substring(32, strc[i].length());
                String str_rq = strc[i].substring(26, 30);
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
                //日期
                String str_zgrq = "20" + str_nian
                        + "-" + str_yue
                        + "-" + str_ri;
                list_sm_rq.add(str_zgrq);
                Log.e("睡眠数据日期", str_zgrq);
                List<Integer> list_xs = new ArrayList<Integer>();
                for (int j = 0; j < str1.length() / 4; j++) {
                    String strje = zhuanhuaSM(str1, j);
                    list_xs.add(Integer.valueOf(strje.substring(12, 16), 2));
                }

                // 判断是不是经过0点
                boolean isLD = false;
                for (int k = 0; k < list_xs.size() - 1; k++) {
                    if (list_xs.get(k + 1) < list_xs.get(k)) {
                        isLD = true;
                    }
                }
                MyLog.showLog("是不是经过0点", isLD + "");

                for (int j = 0; j < str1.length() / 4; j++) {
                    String strje = zhuanhuaSM(str1, j);
                    // 开始睡眠时间
                    if (Integer.valueOf(strje.substring(12, 16), 2) == 1) {//进入睡眠
                        int h1 = Integer.valueOf(strje.substring(0, 5), 2);
                        int m1 = Integer.valueOf(strje.substring(5, 11), 2);
                        str_kssm = h1 + "点" + m1 + "分";
                        MyLog.showLog("进入睡眠", str_kssm);
                    }
                    if (Integer.valueOf(strje.substring(12, 16), 2) == 5) {//退出睡眠
                        int h1 = Integer.valueOf(strje.substring(0, 5), 2);
                        int m1 = Integer.valueOf(strje.substring(5, 11), 2);
                        str_tcsm = h1 + "点" + m1 + "分";
                        MyLog.showLog("退出睡眠", str_tcsm);
                    }
                    if (Integer.valueOf(strje.substring(12, 16), 2) == 4) {//睡醒
                        int h1 = Integer.valueOf(strje.substring(0, 5), 2);
                        int m1 = Integer.valueOf(strje.substring(5, 11), 2);
                        str_xlsj += h1 + "点" + m1 + "分    ";
                        MyLog.showLog("睡醒", str_xlsj);
                        xljc++;
                    }
                    // 熟睡时间
                    if (Integer.valueOf(strje.substring(12, 16), 2) == 3) {//熟睡
                        int h1 = Integer.valueOf(strje.substring(0, 5), 2);
                        int m1 = Integer.valueOf(strje.substring(5, 11), 2);
                        String str_zh = zhuanhuaSM(str1, j + 1);
                        int h2 = Integer.valueOf(str_zh.substring(0, 5), 2);
                        int m2 = Integer.valueOf(str_zh.substring(5, 11), 2);

                        if (h1 == 0 && h2 == 0) {
                            h1 = 24;
                        }
                        if (h2 == 0) {
                            h2 = 24;
                        }

                        if (isLD) {//经过0点
                            if (h2 >= h1) {
                                if ((h2 - h1) == 0) {
                                    time3 += m2 - m1;
                                } else {
                                    time3 += (h2 - h1 - 1) * 60 + (60 - m1) + m2;
                                }
                            } else if (h2 < h1) {
                                time3 += (24 - h1 - 1) * 60 + (60 - m1) + h1
                                        * 60 + m2;
                            }
                        } else {//不经过0点
                            if ((h2 - h1) == 0) {
                                time3 += m2 - m1;
                            } else {
                                time3 += (h2 - h1 - 1) * 60 + (60 - m1) + m2;
                            }
                        }


                    }
                    // 浅睡时间
                    if (Integer.valueOf(strje.substring(12, 16), 2) == 2) {//浅睡眠
                        int h1 = Integer.valueOf(strje.substring(0, 5), 2);
                        int m1 = Integer.valueOf(strje.substring(5, 11), 2);

                        String str_zh = zhuanhuaSM(str1, j + 1);
                        int h2 = Integer.valueOf(str_zh.substring(0, 5), 2);
                        int m2 = Integer.valueOf(str_zh.substring(5, 11), 2);

                        if (h1 == 0 && h2 == 0) {
                            h1 = 24;
                        }
                        if (h2 == 0) {
                            h2 = 24;
                        }


                        if (isLD) {
                            if (h2 >= h1) {
                                if ((h2 - h1) == 0) {
                                    time2 += m2 - m1;
                                } else {
                                    time2 += (h2 - h1 - 1) * 60 + (60 - m1) + m2;
                                }
                            } else if (h2 < h1) {
                                time2 += (24 - h1 - 1) * 60 + (60 - m1) + h1
                                        * 60 + m2;
                            }
                        } else {
                            if ((h2 - h1) == 0) {
                                time2 += m2 - m1;
                            } else {
                                time2 += (h2 - h1 - 1) * 60 + (60 - m1) + m2;
                            }
                        }

                    }

                }

            }
        }
        int time3_xs = time3 / 60;
        Log.e("熟睡时间", time3_xs + "小时" + (time3 - time3_xs * 60) + "分钟");
        int time2_xs = time2 / 60;
        Log.e("浅睡时间", time2_xs + "小时" + (time2 - time2_xs * 60) + "分钟");
        int timeSum = time2 + time3;
        int timeSum_xs = timeSum / 60;
        Log.e("睡眠总时间", timeSum_xs + "小时" + (timeSum - timeSum_xs * 60) + "分钟");
        MyLog.showLog("睡眠质量", (time2 + time3) * 0.2 + "" + "@" + (time2 + time3) * 0.3);
        tvShuimianZong.setText(timeSum_xs + "\u0020" + getResources().getString(R.string.hours) + "\u0020" + (timeSum - timeSum_xs * 60) + "\u0020" + getResources().getString(R.string.minutes));
        tvShuimianQian.setText(time2_xs + "\u0020" + getResources().getString(R.string.hours) + "\u0020" + (time2 - time2_xs * 60) + "\u0020" + getResources().getString(R.string.minutes));
        tvShuimianShen.setText(time3_xs + "\u0020" + getResources().getString(R.string.hours) + "\u0020" + (time3 - time3_xs * 60) + "\u0020" + getResources().getString(R.string.minutes));
        if (timeSum != 0 && time3 >= (time2 + time3) * 0.2 && time3 <= (time2 + time3) * 0.4) {
            tvShuimian.setText(getResources().getString(R.string.Excellent));
            tvShuimian.setTextColor(0xff5ACACB);
        } else if (timeSum == 0) {

        } else {
            tvShuimian.setText(getResources().getString(R.string.Poor));
            tvShuimian.setTextColor(0xffff0000);
        }
        list_sm_num.add(time2 + time3);
        if (str_xlsj.equals("")) {
            str_xlsj = "无";
        }

        onReRandomPercent(timeSum_xs);
        if (timeSum == 0) {
            return;
        }

        doHttp(RetrofitUtils.createApi(DataUrl.class).addShuiMian(
                userId, tvShuimian.getText().toString(), (double) (timeSum / 60) + "", time, phone, secret), 0);
    }

    private void onReRandomPercent(int timeSum_xs) {
        if (isAnimActive1) {
            return;
        }
        anim1(timeSum_xs);
    }

    private void anim1(int timeSum_xs) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mpc_shuimian, "percent", 0, timeSum_xs / (24f * (290 / 360f))));
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(2000);
        set.start();
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
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

    private String zhuanhuaSM(String str1, int j) {
        String strj = str1.substring(j * 4, j * 4 + 4);// 0000
        String strje = "";
        for (int k = 0; k < strj.length(); k++) {
            String strje_d = Integer.toBinaryString(Integer.valueOf(
                    strj.charAt(k) + "", 16));
            if (strje_d.length() == 1) {
                strje_d = "000" + strje_d;
            } else if (strje_d.length() == 2) {
                strje_d = "00" + strje_d;
            } else if (strje_d.length() == 3) {
                strje_d = "0" + strje_d;
            }
            strje += strje_d;
        }
        return strje;
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bleReceiver);
    }

    @Override
    public void requestData() {

    }

    // 同步数据
    private void tongbuSJ() {
        //清空
        bleStr = "";
        TestService.mService.writeRXCharacteristic(BtSerializeation
                .syncTime());


    }

    @OnClick({R.id.iv_tongbu, R.id.iv_qushi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tongbu:
                if (TestService.mDevice == null) {
                    TestService.no_shsz = 5;
                    startActivity(BLEActivity.class, null);
                } else {
                    //同步手环时间获取数据
                    tongbuSJ();
                    spUtils.put("shuimiantime", System.currentTimeMillis() + "");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(System.currentTimeMillis());
                    tv_time.setText(dateString + getResources().getString(R.string.update));
                }
                break;
            case R.id.iv_qushi:
                startActivity(ShuiMianQuShiAty.class, null);
                break;
        }
    }
}
