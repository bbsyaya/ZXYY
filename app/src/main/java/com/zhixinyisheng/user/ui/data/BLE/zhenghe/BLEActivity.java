package com.zhixinyisheng.user.ui.data.BLE.zhenghe;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.DeviceAdapter;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.MyToast;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

public class BLEActivity extends BaseAty implements AdapterView.OnItemClickListener {

    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
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
    @Bind(R.id.fble_iv1)
    ImageView fbleIv1;
    @Bind(R.id.fble_tv_hx)
    TextView fbleTvHx;
    @Bind(R.id.fble_tv_sssh)
    TextView fbleTvSssh;
    @Bind(R.id.fble_pb_sssh)
    ProgressBar fblePbSssh;
    @Bind(R.id.fble_ll_sssh)
    LinearLayout fbleLlSssh;
    @Bind(R.id.fble_lv)
    ListView fbleLv;
    @Bind(R.id.fble_ivf)
    ImageView fbleIvf;
    @Bind(R.id.fble_rlf)
    RelativeLayout fbleRlf;

    List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();//蓝牙设备的集合
    DeviceAdapter deviceAdapter = null;
    Handler mHandler;
    //动画移动的距离
    int[] location = new int[2];
    int[] location_ss = new int[2];
    int height = 0;
    LoadingDialog ljbleDialog;//连接手环的dialog
    public static final int REQUEST_ENABLE_BT = 2;
    public static final int REQUEST_LOCATION = 3;
    boolean is_dycss = true;
    Map<String, Integer> devRssiValues = new HashMap<String, Integer>();
    Timer t_ljsh;//连接手环时的定时器
    TimerTask tt_ljsh;

    private Dialog dialog1;
    private View mView;
    public static BLEActivity instance;
    public static BluetoothAdapter mBtAdapter = null,//蓝牙是否打开管理类
            mBluetoothAdapter = null,//搜索蓝牙管理类
            mBluetoothAdapter_zd = null;//服务中自动搜索蓝牙管理类
    @Override
    public int getLayoutId() {
        return R.layout.activity_findble;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        instance = this;
        try {
            TestService.myActivityStack.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (LanguageUtil.judgeLanguage().equals("zh")){
            fbleIvf.setImageResource(R.drawable.ct_btn_jzcl_llse);
        }else{
            fbleIvf.setImageResource(R.drawable.ic_ljsb);
        }

        cjsTvt.setText(getResources().getString(R.string.smartBracelet));
        ivBack.setVisibility(View.VISIBLE);
        if (deviceAdapter == null) {
            deviceAdapter = new DeviceAdapter(this, deviceList, R.layout.device_element, devRssiValues);
            fbleLv.setAdapter(deviceAdapter);
        } else {
            deviceAdapter.removeAll();
            deviceAdapter.addAll(deviceList);
        }

        if (PermissionsUtil.is6()) {
            PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION);
        }


        mHandler = new Handler();
        initLayout();
        initDialog();
        //判断蓝牙是否打开
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();


        if (!TestService.mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        fbleLv.setOnItemClickListener(this);
        if (LanguageUtil.judgeLanguage().equals("zh")){
            fbleIv1.setBackgroundResource(R.drawable.shouhuan);
        }else{
            fbleIv1.setBackgroundResource(R.drawable.shouhuan_en);
        }
    }
    /**
     * Gps是否可用
     * */
    public static final boolean isGpsEnable(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
    private void initDialog() {


        if (ljbleDialog == null) {
            ljbleDialog = new LoadingDialog(this);
        }
//        ljbleDialog.showLoadingDialog("jujing");


    }

    // 获取listview的高度
    private void initLayout() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                tv_hx.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
//                MyLog.showLog("位置1","横坐标:"+location[0]+"纵坐标:"+location[1]);
                try {
                    fbleTvHx.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                    MyLog.showLog("位置2", "横坐标:" + location[0] + "纵坐标:" + location[1]);

                    fbleRlf.getLocationOnScreen(location_ss);
                    MyLog.showLog("位置22", "横坐标:" + location_ss[0] + "纵坐标:" + location_ss[1]);

                    height = location_ss[1] - location[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 500);
    }

    @Override
    public void requestData() {

    }


    @OnClick({R.id.cjs_rlb, R.id.fble_rlf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                TestService.myActivityStack.finish(BLEActivity.class);
                finish();
                break;
            case R.id.fble_rlf:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(isGpsEnable(this)){
                        searchBLE();
                    }else {
                        new MaterialDialog(this)
                                .setMDNoTitle(true)
                                .setMDMessage(getString(R.string.android60yishang))
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivityForResult(intent,REQUEST_LOCATION);
                                    }
                                })
                                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        finish();
                                    }
                                })
                                .show();
                    }
                    return;
                }
                searchBLE();



                break;
        }
    }

    private void searchBLE() {
        fbleRlf.setEnabled(false);
        fbleTvSssh.setText(getResources().getString(R.string.searchingBracelet));
        if (is_dycss) {
            fbleLlSssh.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(fbleRlf, "translationY", 0f, -height - 50)
                    .setDuration(500).start();
        } else {
            fblePbSssh.setVisibility(View.VISIBLE);
        }

        scanLeDevice(true);
        is_dycss = false;
    }

    private void addDevice(BluetoothDevice device, int rssi) {
        boolean deviceFound = false;
        for (BluetoothDevice listDev : deviceList) {
            if (listDev.getAddress().equals(device.getAddress())) {
                deviceFound = true;
                break;
            }
        }

        devRssiValues.put(device.getAddress(), rssi);

        if (!deviceFound) {
            deviceList.add(device);
            deviceAdapter.notifyDataSetChanged();
        }
    }

    public BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {//必须回到主线程
                        @Override
                        public void run() {
                            addDevice(device, rssi);
                        }
                    });

                }
            };

    //搜索手环设备蓝牙
    private void scanLeDevice(boolean enable) {
        if (enable) {
            //搜索10秒
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        fbleRlf.setEnabled(true);
                        TestService.mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        fblePbSssh.setVisibility(View.GONE);
                        if (deviceList.size() > 0) {
                            fbleTvSssh.setText(getResources().getString(R.string.searchedBracelet));
                        } else {
                            fbleTvSssh.setText(getResources().getString(R.string.noSearchBracelet));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1000 * 20);
            TestService.mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            TestService.mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

    }

    // 返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    MyToast.showToast(BLEActivity.this, getResources().getString(R.string.succeedBluetooth));

                } else {
                    MyToast.showToast(BLEActivity.this, getResources().getString(R.string.failedBluetooth));
                    finish();
                }
                break;
            case REQUEST_LOCATION:
                if (resultCode == Activity.RESULT_OK) {
                    MyToast.showToast(BLEActivity.this, "已打开GPS！");
                }
                break;
            default:
                Log.e("findble", "wrong request code");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLog.showLog("BLEActivity", "onPause");

        //连接上手环取消连接手环定时器
        if (t_ljsh != null) {
            MyLog.showLog("取消了", "连接手环定时器");
            t_ljsh.cancel();
        }
        //连接对话框消失
        if (ljbleDialog.isShowing()) {
            ljbleDialog.dismiss();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TestService.mBluetoothAdapter.stopLeScan(mLeScanCallback);
        BluetoothDevice device = deviceList.get(position);
        TestService.deviceAddress = device.getAddress();
        TestService.deviceName = device.getName();
        // 判断系统蓝牙是否连接上
        // 获得已配对的远程蓝牙设备的集合
        Set<BluetoothDevice> devices = TestService.mBtAdapter.getBondedDevices();
        if (devices.size() > 0) {
//            if (true) {
            boolean is_qbsh = true;
            for (BluetoothDevice device1 : devices) {
                if (device1.getAddress().equals(
                        TestService.deviceAddress)) {
                    is_qbsh = true;
                }
            }
            if (is_qbsh) {
                //正在连接
                ljbleDialog.showLoadingDialog(getResources().getString(R.string.connectingBracelet));
                //定时器
                t_ljsh = new Timer();
                tt_ljsh = new TimerTask() {
                    @Override
                    public void run() {
                        TestService.mService.disconnect();
                        if (ljbleDialog != null) {
                            //结束掉网络请求
                            ljbleDialog.dismiss();
                        }
                    }
                };
                t_ljsh.schedule(tt_ljsh, 1000 * 10);
                if (TestService.deviceAddress != null) {
                    boolean is_connect = TestService.mService.connect(TestService.deviceAddress);

                }
            } else {
                showDialog();
            }

        } else {
            showDialog();
        }
    }

    AlertDialog dialog;

    public void showDialog() {

        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(getString(R.string.zhuyiqingbaozhengpeidui))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        //正在连接
                        ljbleDialog.showLoadingDialog(getResources().getString(R.string.connectingBracelet));
                        //定时器
                        t_ljsh = new Timer();
                        tt_ljsh = new TimerTask() {
                            @Override
                            public void run() {
                                TestService.mService.disconnect();
                                if (ljbleDialog.isShowing()) {
                                    ljbleDialog.dismiss();
                                }
                            }
                        };
                        t_ljsh.schedule(tt_ljsh, 1000 * 10);
                        if (TestService.deviceAddress != null) {
                            boolean is_connect = TestService.mService.connect(TestService.deviceAddress);
                            MyLog.showLog("手环连接情况", is_connect + "");

                        }
                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                    }
                })
                .show();


//        dialog = new AlertDialog.Builder(this).create();
//        //点击外部区域不能取消dialog
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.show();
//
//        Window window = dialog.getWindow();
//        window.setContentView(R.layout.my_dialog_tishi);
//        TextView tv_title = (TextView) window.findViewById(R.id.dgts_tvtt);
//        TextView tv_qd = (TextView) window.findViewById(R.id.dgts_tvqd);
//        tv_title.setText("注意:请保证手机和此手环配对!");
//        tv_qd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                //正在连接
//                ljbleDialog.showLoadingDialog("正在连接手环");
//                //定时器
//                t_ljsh = new Timer();
//                tt_ljsh = new TimerTask() {
//                    @Override
//                    public void run() {
//                        TestService.mService.disconnect();
//                        if (ljbleDialog.isShowing()) {
//                            ljbleDialog.dismiss();
//                        }
//                    }
//                };
//                t_ljsh.schedule(tt_ljsh, 1000 * 10);
//                if (TestService.deviceAddress != null) {
//                    boolean is_connect = TestService.mService.connect(TestService.deviceAddress);
//                    MyLog.showLog("手环连接情况", is_connect + "");
//
//                }
//
//            }
//        });
//
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            TestService.myActivityStack.finish(BLEActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
