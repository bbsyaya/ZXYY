package com.zhixinyisheng.user.ui.data.xinlv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zhixinyisheng.user.view.BatteryView2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_HEART_DATA;

/**
 * 心率已经连接手环的界面
 * Created by 焕焕 on 2016/10/30.
 */
public class XinLvSHAty extends BaseAty{
    //手环广播
    BLEReceiver bleReceiver;
    String bleStr = "";//手环返回数据
    List<String> list_xl_rq = new ArrayList<String>();
    List<Integer> list_xl_num = new ArrayList<Integer>();
    int int_xl_num = 0;
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.mybatteryview2)
    BatteryView2 mybatteryview2;
    @Bind(R.id.xinlv_xin)
    ImageView xinlvXin;
    @Bind(R.id.Lv)
    RelativeLayout Lv;
    @Bind(R.id.tv_xinlvshoudongshuru)
    TextView tv_xlsz;//心率的数值
    @Bind(R.id.xinlv3)
    TextView xinlv3;
    @Bind(R.id.cifen)
    TextView cifen;
    @Bind(R.id.ll_xinlvshoudong)
    LinearLayout llXinlvshoudong;
    //心的高宽
    public static int xxwidth;
    public static int xxheight;
    float num;
    float no = 0;
    @Bind(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.btn_xinlvshuru)
    Button btnXinlvshuru;
    @Bind(R.id.tv_time)
    TextView tv_time;
    SPUtils spUtils;

    @Override
    public int getLayoutId() {
        return R.layout.aty_xinlvsh;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tongbuSJ();

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


    }

    @Override
    protected void onResume() {
        super.onResume();
        bleReceiver = new BLEReceiver() {
            @Override
            public void bleReceive(Intent intent) {

                String action = intent.getAction();
                if (action.equals(BLEService.ACTION_DATA_AVAILABLE)) {//接受数据
                    Log.e("xinlvguangbo", "xinlvguangbo");
                    try {
                        jiexiSHShuju(intent);
                    } catch (Exception e) {
                        Log.e("xinlvguangbo eee", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        };
        //注册广播
        LocalBroadcastManager.getInstance(this).registerReceiver(bleReceiver,
                TestService.makeGattUpdateIntentFilter());
        spUtils = new SPUtils("xinlvtime");
        String time = (String) spUtils.get("xinlvtime", getResources().getString(R.string.NoUpdate));
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
            spUtils.put("xinlvtime", System.currentTimeMillis() + "");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(System.currentTimeMillis());
            tv_time.setText(dateString + getResources().getString(R.string.update));
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
//        showToast(strf);
        final byte[] txValue = intent.getByteArrayExtra(BLEService.EXTRA_DATA);
        if (txValue==null){
            return;
        }
        String strf = StringUtils.bytesToHexString(txValue);
        if (strf != null) {
            strf = strf.replace(" ", "");
            Log.e("sssstmp", strf + "**");
            //        Log.e("xinlvshouhuan", strf);
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


    }

    //接受手环返回数据
    private void shljcgTZ() {
        jiexiXL();
        if (list_xl_rq.size() == 0) {
            tv_xlsz.setText("0");
        } else {
            tv_xlsz.setText(list_xl_num.get(list_xl_num.size() - 1) + "");
        }

        if (tv_xlsz.getText().toString().equals("0")) {
            mybatteryview2.setPower(0);
        } else {
            num = Integer.parseInt(tv_xlsz.getText().toString());

            try {
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 心率
    public void jiexiXL() {
        list_xl_rq.clear();
        list_xl_num.clear();
        String[] strc = bleStr.split("@");
        List<Integer> list_xiaoshib = new ArrayList<Integer>();
        for (int i = 1; i < strc.length; i++) {
            String str = strc[i];
            if (String.valueOf(str.charAt(17)).equals("3")
                    && String.valueOf(str.charAt(21)).equals("5")) {
                Log.e("返回心率字符串", str);
                String str1 = str.substring(34, str.length());
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
                Log.e("心率日期", str_zgrq);
                list_xl_rq.add(str_zgrq);
                int_xl_num = 0;
                list_xiaoshib.clear();
                for (int j = 0; j < str1.length() / 2; j++) {
                    if (Integer.valueOf(str1.substring(j * 2, j * 2 + 2), 16) != 0) {
                        int_xl_num = Integer.valueOf(
                                str1.substring(j * 2, j * 2 + 2), 16);
                    }
                    list_xiaoshib.add(Integer.valueOf(
                            str1.substring(j * 2, j * 2 + 2), 16));

                }
                Log.e("心率", int_xl_num + "");
                list_xl_num.add(int_xl_num);
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

    // 同步数据
    private void tongbuSJ() {
        //清空
        bleStr = "";
        for (int i = 0; i < BtSerializeation.syncTime().length; i++) {
            byte[] a = BtSerializeation.syncTime();
            Log.e("aaaaajujing", a[i] + "$$$");
        }
        TestService.mService.writeRXCharacteristic(BtSerializeation
                .syncTime());
    }


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

    @OnClick({R.id.cjs_rlb, R.id.btn_xinlvshuru, R.id.iv_tongbu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.btn_xinlvshuru:
                String ave = tv_xlsz.getText().toString();
                if (ave == null || ave.equals("")) {
                    showToast(getResources().getString(R.string.pleaseInputData));
                } else {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DataUrl.class).addXinLv(
                            userId, ave + "", time, phone, secret), 0);
                }
                break;
            case R.id.iv_tongbu:
                //同步手环时间获取数据
                tongbuSJ();
                spUtils.put("xinlvtime", System.currentTimeMillis() + "");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(System.currentTimeMillis());
                tv_time.setText(dateString + getResources().getString(R.string.update));
                break;
        }
    }
}
