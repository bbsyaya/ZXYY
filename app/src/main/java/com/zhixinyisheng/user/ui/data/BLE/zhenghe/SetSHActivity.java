package com.zhixinyisheng.user.ui.data.BLE.zhenghe;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.MyToast;
import com.zhixinyisheng.user.ui.data.BLE.common.CommonSHML;
import com.zhixinyisheng.user.ui.data.BLE.common.Utils;
import com.zhixinyisheng.user.ui.data.BLE.control.AlarmActivity;
import com.zhixinyisheng.user.ui.data.BLE.control.AlarmDAO;
import com.zhixinyisheng.user.ui.data.BLE.control.BtSerializeation;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 手环的设置界面
 * Created by 焕焕 on 2016/10/25.
 */
public class SetSHActivity extends BaseAty {

    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.sble_tv_shn)
    TextView tv_shn;
    @Bind(R.id.sble_tvblen)
    TextView sbleTvblen;
    @Bind(R.id.sble_tvdl)
    TextView tv_dl;
    @Bind(R.id.fble_iv1)
    ImageView fbleIv1;
    private LoadingDialog mLoadingDialog;//进度对话框
    MaterialDialog dg_qbsh;//提示对话框
    AlarmDAO dao;
    BLEReceiver bleReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.aty_set_sh;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        TestService.myActivityStack.add(this);
//        atst1.add(this);
        if (LanguageUtil.judgeLanguage().equals("zh")){
            fbleIv1.setImageResource(R.drawable.shouhuan);
        }else{
            fbleIv1.setImageResource(R.drawable.shouhuan_en);
        }
        initDialog();
        dao = AlarmDAO.getInstance(this);
        cjsTvt.setText(R.string.zhinengshouhuan);
        tv_dl.setText(TestService.int_shdl + "%");
        tv_shn.setText(TestService.deviceName);
        ivBack.setVisibility(View.VISIBLE);
        if (LanguageUtil.judgeLanguage().equals("zh")){
            fbleIv1.setBackgroundResource(R.drawable.shouhuan);
        }else{
            fbleIv1.setBackgroundResource(R.drawable.shouhuan_en);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyLog.showLog("SetSHActivity", "onResume");
        if (!TestService.is_cqsh) {
            bleReceiver = new BLEReceiver() {
                @Override
                public void bleReceive(Intent intent) {

                    String action = intent.getAction();
                    if (action.equals(BLEService.ACTION_DATA_AVAILABLE)) {
                        String strf = intent.getStringExtra(BLEService.EXTRA_DATA);
                        try {
                            //电量
                            if (String.valueOf(strf.charAt(0)).toUpperCase()
                                    .equals("A")) {
                                if (String.valueOf(strf.charAt(17)).equals("3")
                                        && String.valueOf(strf.charAt(21)).equals("1")) {
                                    String str1 = strf.substring(26, strf.length());
                                    int bleDL = Integer.valueOf(str1, 16);
                                    tv_dl.setText(bleDL + "%");
                                }
                            }
                        } catch (Exception e) {

                        }


                    } else if (action.equals(BLEService.ACTION_GATT_SERVICES_DISCOVERED)) {
//                        Log.e("laile 155",TestService.is_cqsh+"###");
                        if (TestService.is_cqsh) {//判断是不是重启手环
                            TestService.mService.enableTXNotification();
                            shLJCG();
                        }
                    } else if (action.equals(BLEService.ACTION_GATT_DISCONNECTED)) {//断开手环连接


                    } else if (action.equals(BLEService.RSSI_DATA)) {
                        String myrssi = intent.getStringExtra(BLEService.EXTRA_DATA);
                        try {
                            sbleTvblen.setText((230 + Integer.valueOf(myrssi)) / 2+"");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            //注册广播
            LocalBroadcastManager.getInstance(this).registerReceiver(bleReceiver,
                    TestService.makeGattUpdateIntentFilter());
        } else {
            //重启手环的变量回归原始
            TestService.is_cqsh = false;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLog.showLog("SetSHActivity", "onPause");
        //不是重启手环走这个
        if (!TestService.is_cqsh) {
            try {
                LocalBroadcastManager.getInstance(this).unregisterReceiver(
                        bleReceiver);
            } catch (Exception e) {
                Log.e("BLEActivity", e.toString());
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.showLog("SetSHActivity", "onDestroy");

        TestService.myActivityStack.finish(SetSHActivity.class);

        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

    }

    //手环连接成功的跳转
    private void shLJCG() {

        if (TestService.is_shlj) {
            TestService.is_shlj = false;

            //连接上手环做标记
            TestService.mDevice = BluetoothAdapter.getDefaultAdapter().
                    getRemoteDevice(TestService.deviceAddress);

            //保存手环地址
            TestService.editor.putString("SH_ADD", TestService.deviceAddress);
            TestService.editor.commit();

            if (TestService.no_shsz == 2) {//只是连接手环,不进行跳转

                //手环在后台自动连接进行通知
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }

                MyToast.showToast(SetSHActivity.this, getString(R.string.shouhuanchongqichenggong));

            }

            //销毁BLEActivity
            TestService.myActivityStack.finish(BLEActivity.class);
//            TestService.myActivityStack.finish(XiLvTatolActivity.class);


        }

    }

    //判断基础服务是否打开
    private boolean checkStealFeature1(String service) {
        int ok = 0;
        try {
            ok = Settings.Secure.getInt(getApplicationContext()
                            .getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
        }

        TextUtils.SimpleStringSplitter ms = new TextUtils.SimpleStringSplitter(
                ':');
        if (ok == 1) {
            String settingValue = Settings.Secure.getString(
                    getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                ms.setString(settingValue);
                while (ms.hasNext()) {
                    String accessibilityService = ms.next();
                    Log.e("基础服务是否打开 133", accessibilityService);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;

                    }

                }
            }
        }
        return false;
    }

    /**
     * 进度对话框
     */
    private void initDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
//        mLoadingDialog.showLoadingDialog("正在重启手环");

        dg_qbsh = new MaterialDialog(this);
        dg_qbsh.setMDMessage(getString(R.string.qingquebaoxitong));
        dg_qbsh.setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
            @Override
            public void dialogBtnOnClick() {

            }
        });
        dg_qbsh.setMDOnTouchOutside(false);
//        dg_qbsh.show();


    }

    @Override
    public void requestData() {

    }


    @OnClick({ R.id.cjs_rlb,   R.id.sble_tvjcbd, R.id.sble_rll_tx, R.id.sble_rltt, R.id.sble_rlsy, R.id.sble_rlsn, R.id.sble_rlqk, R.id.sble_rlcq, R.id.sble_rltap})
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.cjs_rlb:
                finish();
                break;


            case R.id.sble_tvjcbd://解除绑定手环
                new MaterialDialog(this)
                        .setMDNoTitle(true)
                        .setMDMessage(getString(R.string.ninshifouquerenyaojiechu))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                TestService.int_jcsh = 1;
                                TestService.editor.putString("SH_ADD", "");
                                TestService.editor.commit();

                                if (TestService.mDevice != null) {
                                    TestService.mService.disconnect();
                                }

                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {

                            }
                        })
                        .show();




                break;
            case R.id.sble_rll_tx://手环提醒设置
                //判断基础服务是否打开
                boolean is_jcfw = checkStealFeature1(
                        "com.zhixinyisheng.user/com.zhixinyisheng.user.ui.data.BLE.QQ.MyNotificationService");
                if (!is_jcfw) {
//            MyDialogTS mdg_ts = new MyDialogTS(SetSHActivity.this, "开启知心医生基础服务!", 1);
//            mdg_ts.showDialog();

                    new MaterialDialog(this)
                            .setMDTitle(getString(R.string.notice))
                            .setMDMessage(getString(R.string.ninbixukaiqijichufuwu))
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    //开启基础服务
                                    Intent intent1 = new Intent(
                                            Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                    startActivityForResult(intent1, 0);

                                }
                            })
                            .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {

                                }
                            })
                            .show();
                }else{
                    startActivity(SHTxszActivity.class, null);
                }

                break;
            case R.id.sble_rltt://同步
                TestService.mService.writeRXCharacteristic(BtSerializeation.syncTime());
                showToast(getString(R.string.tongbushijianwancheng));
                break;

            case R.id.sble_rlsy:
                Utils.sendSHML(SetSHActivity.this, CommonSHML.SHML_csy);
                break;
            case R.id.sble_rlsn://闹钟
                startActivity(AlarmActivity.class, null);
                break;
            case R.id.sble_rlqk://清空数据
                new MaterialDialog(this)
                        .setMDNoTitle(true)
                        .setMDMessage(getString(R.string.qingquerenshifouyaoqingkong))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                TestService.mService.writeRXCharacteristic(BtSerializeation.clearData());
                                showToast(getString(R.string.shujuyiqingkong));

                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {

                            }
                        })
                        .show();

                break;
            case R.id.sble_rlcq://重启手环
                new MaterialDialog(this)
                        .setMDNoTitle(true)
                        .setMDMessage(getString(R.string.qingquerenshifouyaochongqi))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                TestService.int_jcsh = 2;
                                //进行自动连接的时候另一种判断形式
                                TestService.is_cqsh = true;

                                TestService.mService.writeRXCharacteristic(BtSerializeation.setReboot());
//                mLoadingDialog.showLoadingDialog("正在重启手环...");
                                showToast(getString(R.string.zhengzaichongqishouhuan));

                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {

                            }
                        })
                        .show();



                break;
            case R.id.sble_rltap://taptap
                startActivity(TapTapActivity.class, null);
                break;

        }
    }
}
