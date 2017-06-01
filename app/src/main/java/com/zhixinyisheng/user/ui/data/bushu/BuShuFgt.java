package com.zhixinyisheng.user.ui.data.bushu;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.util.SPUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.data.BLE.common.StringUtils;
import com.zhixinyisheng.user.ui.data.BLE.control.BtSerializeation;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEReceiver;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEService;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.util.Content;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 步数
 * Created by 焕焕 on 2016/10/31.
 */
public class BuShuFgt extends BaseFgt {
    @Bind(R.id.tv_bushunum)
    TextView tvBushunum;
    @Bind(R.id.tv_bushu_licheng)
    TextView tvBushuLicheng;
    @Bind(R.id.tv_bushu_cal)
    TextView tvBushuCal;
    @Bind(R.id.iv_tongbu)
    ImageView ivTongbu;
    @Bind(R.id.iv_qushi)
    ImageView ivQushi;

    @Bind(R.id.tv_bottom_ngc)
    TextView tv_bottom_ngc;
    @Bind(R.id.tv_time)
    TextView tv_time;
    //手环广播
    BLEReceiver bleReceiver;
    String bleStr = "";//手环返回数据
    List<String> list_bs_rq = new ArrayList<String>();
    List<Integer> list_bs_num = new ArrayList<Integer>();
    List<Integer> list_kll_num = new ArrayList<Integer>();
    List<Double> list_jl_num = new ArrayList<Double>();
    int int_bs_num = 0;
    SPUtils spUtils;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_bushu;
    }

    @Override
    public void initData() {
        Content.stepnumber++;
        linkDevice();
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        linkDevice();
    }

    @Override
    public void onUserInvisible() {
        super.onUserInvisible();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bleReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void linkDevice() {
        TestService.no_shsz = 1;
        bleStr = "";
        if (TestService.mDevice != null) {
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
        }
        spUtils = new SPUtils("bushutime");
        String time = (String) spUtils.get("bushutime",getResources().getString(R.string.NoUpdate));
        if (!time.equals(getResources().getString(R.string.NoUpdate))) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(Long.parseLong(time));
            tv_time.setText(dateString+getResources().getString(R.string.update));
        }else{
            tv_time.setText(getResources().getString(R.string.NoUpdate));
        }

        if (TestService.mDevice != null) {
            Logger.e("wolaile","wolaile");
            //同步手环时间获取数据
            tongbuSJ();

            spUtils.put("bushutime", System.currentTimeMillis()+"");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(System.currentTimeMillis());
            tv_time.setText(dateString+getResources().getString(R.string.update));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bleReceiver);
    }

    //解析手环返回数据
    private void jiexiSHShuju(Intent intent) {
//        String strf = intent.getStringExtra(BLEService.EXTRA_DATA);
        final byte[] txValue = intent.getByteArrayExtra(BLEService.EXTRA_DATA);
        if (txValue==null){
            return;
        }
        String strf = StringUtils.bytesToHexString(txValue);

        if (strf==null){
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
        jieXiFoot();
        if (list_bs_rq.size() == 0) {
            tvBushunum.setText("0");
            tvBushuLicheng.setText("0");
            tvBushuCal.setText("0");
        } else {
            tvBushunum.setText(list_bs_num.get(list_bs_num.size() - 1) + "");
            tvBushuLicheng.setText(list_jl_num.get(list_jl_num.size() - 1) + "");
            tvBushuCal.setText(list_kll_num.get(list_kll_num.size() - 1) + "");
            if (list_bs_num.size()==0){
                return;
            }

            doHttp(RetrofitUtils.createApi(DataUrl.class).addBuShu(
                    userId, tvBushunum.getText().toString(), time, phone, secret), 0);
        }
    }

    public void jieXiFoot() {
        list_bs_rq.clear();
        list_bs_num.clear();
        String[] strc = bleStr.split("@");
        List<Integer> list_xiaoshib = new ArrayList<Integer>();
        for (int i = 1; i < strc.length; i++) {
            String str = strc[i];
            if (String.valueOf(str.charAt(17)).equals("3")
                    && String.valueOf(str.charAt(21)).equals("2")) {// 返回步数的数据
                Log.e("返回步数字符串", str);
                String str1 = str.substring(34, str.length());
                String str_rq = str.substring(26, 30);
                String str_rqze = shiliuZhaunER(str_rq);
                Log.e("bsbs11", str_rqze);
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
                Log.e("步数日期", str_zgrq);
                list_bs_rq.add(str_zgrq);
                int_bs_num = 0;
                list_xiaoshib.clear();
                for (int j = 0; j < str1.length() / 4; j++) {
                    int_bs_num += Integer.valueOf(str1.substring(j * 4, j * 4 + 4),
                            16);
                    list_xiaoshib.add(Integer.valueOf(
                            str1.substring(j * 4, j * 4 + 4), 16));

                }

                Log.e("步数", int_bs_num + "");
                list_bs_num.add(int_bs_num);
                list_kll_num.add((int) (60 * 1.036 * (170 * 0.32 * int_bs_num * 0.00001)));
                list_jl_num.add(new BigDecimal((170 * 32 * int_bs_num * 0.00001 / 100)).setScale(2,
                        BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        String str_zongde = "", str_sjzongde = "";

        for (int i = 0; i < list_bs_num.size(); i++) {
            str_zongde += list_bs_rq.get(i) + ":" + list_bs_num.get(i) + "步    ";
            str_sjzongde += new BigDecimal(list_bs_num.get(i) / 1600f).setScale(2,
                    BigDecimal.ROUND_HALF_UP).doubleValue() + "公里    ";
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
        TestService.mService.writeRXCharacteristic(BtSerializeation
                .syncTime());


    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.iv_tongbu, R.id.iv_qushi, R.id.tv_bushunum, R.id.tv_bushu_licheng, R.id.tv_bushu_cal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tongbu:
                if (TestService.mDevice == null) {
                    TestService.no_shsz = 5;
                    startActivity(BLEActivity.class, null);
                } else {
                    //同步手环时间获取数据
                    tongbuSJ();

                    spUtils.put("bushutime", System.currentTimeMillis()+"");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(System.currentTimeMillis());
                    tv_time.setText(dateString+getResources().getString(R.string.update));
                }
                break;
            case R.id.iv_qushi:
                startActivity(BuShuQuShiAty.class, null);
                break;
            case R.id.tv_bushunum:
                break;
            case R.id.tv_bushu_licheng:
                break;
            case R.id.tv_bushu_cal:
                break;
        }
    }
}
