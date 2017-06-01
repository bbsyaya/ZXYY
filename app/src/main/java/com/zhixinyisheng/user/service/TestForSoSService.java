package com.zhixinyisheng.user.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.and.yzy.frame.config.HttpConfig;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.ui.MainActivity;
import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.MyToast;
import com.zhixinyisheng.user.ui.data.BLE.common.CommonSHML;
import com.zhixinyisheng.user.ui.data.BLE.common.MyActivityStack;
import com.zhixinyisheng.user.ui.data.BLE.common.StaticCommon;
import com.zhixinyisheng.user.ui.data.BLE.common.StringUtils;
import com.zhixinyisheng.user.ui.data.BLE.common.Utils;
import com.zhixinyisheng.user.ui.data.BLE.control.BtSerializeation;
import com.zhixinyisheng.user.ui.data.BLE.control.PhoneReceiver;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEReceiver;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.SetSHActivity;
import com.zhixinyisheng.user.ui.data.xinlv.XinLvSHAty;
import com.zhixinyisheng.user.ui.data.xueya.XueYaSHAty;
import com.zhixinyisheng.user.ui.sos.HelpActivity;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.RunningTaskUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 焕焕 on 2017/3/29.
 */

public class TestForSoSService extends Service{
    int i = 0;
    //连接手环的一直存在变量
    public static BLEForSoSService mService = null;
    //是不是连接上手环的变量
    public static BluetoothDevice mDevice = null;
    //第一次返回电量连接手环变量
    public static boolean is_shlj = true;
    //是否手环发来的呼救
    public static boolean is_shhj = false;

    //心率,血压,步数
    public static int sh_swich = 0;

    //提醒设置的变量
    public static boolean is_dx = true, is_qq = true, is_wx = true, is_ld = true;


    BLEReceiver bleReceiver;

    public static PhoneReceiver phReceiver;
    public static IntentFilter filter;

    String TAG = "nRFUART";

    //Activity的总容器
    public static MyActivityStack myActivityStack;

    //要连接手环的蓝牙地址
    public static String deviceAddress = "";
    public static String deviceName = "";

    //手环设置界面还是同步数据界面 还是心率等其他界面
    public static int no_shsz = 0; //3是心率 4是血压 5是步数

    //手环保存的东西
    public static SharedPreferences spf;
    public static SharedPreferences.Editor editor;

    public static BluetoothAdapter mBtAdapter = null,//蓝牙是否打开管理类
            mBluetoothAdapter = null,//搜索蓝牙管理类
            mBluetoothAdapter_zd = null;//服务中自动搜索蓝牙管理类


    //解除手环绑定和手环有问题断开连接
    public static int int_jcsh = 0;

    //手环电量
    public static int int_shdl = 0;

    //重启手环变量
    public static boolean is_cqsh = false;

    //用户登陆了
    public static boolean is_dll = false;


    @Override
    public IBinder onBind(Intent arg0) {
        Log.e("TestForSoSService", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TestForSoSService", "TestForSoSService启动了");
        init();
        service_init();

        shWeiLJ();

        //通知
        initService();

        //获取信号定时器
        Timer t, t_dl;
        TimerTask tt, tt_dl;
        t = new Timer();
        tt = new TimerTask() {
            @Override
            public void run() {
                if (TestForSoSService.mDevice != null) {
                    TestForSoSService.mService.getRssi();
                }
            }
        };
        t.schedule(tt, 0, 1000 * 10);

        //实时获取电量
        t_dl = new Timer();
        tt_dl = new TimerTask() {
            @Override
            public void run() {
                if (TestForSoSService.mDevice != null) {//获取手环电量
                    TestForSoSService.mService.writeRXCharacteristic(BtSerializeation
                            .syncTime());
                }
            }
        };
        t_dl.schedule(tt_dl, 0, 1000 * 100);


        new RunningTaskUtil(this);
    }

    private static final int NOTIFICATION_ID = 1; // 如果id设置为0,会导致不能设置为前台service
    public NotificationManager mNotificationManager2;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TestForSoSService", "onStartCommand");
        mNotificationManager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_newlogo);
        builder.setTicker(getString(R.string.zhixinyishengqidong));
        builder.setContentTitle(getString(R.string.homeTitle));
        builder.setContentText(getString(R.string.zhixinyishengshike));
        //Notification notification = builder.build();
        builder.build().flags = Notification.FLAG_NO_CLEAR;
//        startForeground(NOTIFICATION_ID, builder.build()); // 要用的时候再解开
        return super.onStartCommand(intent, flags, startId);
    }


    private void init() {
        myActivityStack = new MyActivityStack();

        spf = getSharedPreferences("SH_BC", MODE_PRIVATE);
        editor = spf.edit();
        StaticCommon.ZYTZ = spf.getString("SH_TAP", "");
//        Logger.e("shtap ", StaticCommon.ZYTZ);
        // 实例化蓝牙有关类
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            MyToast.showToast(this, getString(R.string.nindeshoujibuzhichilanya));
            return;
        }
        if (!getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE)) {
            MyToast.showToast(this, getString(R.string.nindeshoujibuzhichilanya));
        }

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        mBluetoothAdapter_zd = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || mBluetoothAdapter_zd == null) {
            MyToast.showToast(this, getString(R.string.nindeshoujibuzhichilanya));
            return;
        }


    }

    //注册服务和广播
    private void service_init() {
        Log.e("TestForSoSService", "BLEForSoSService启动了");
        //绑定服务
        Intent bindIntent = new Intent(this, BLEForSoSService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        bleReceiver = new BLEReceiver() {
            @Override
            public void bleReceive(Intent intent) {

                String action = intent.getAction();
                if (action.equals(BLEForSoSService.ACTION_DATA_AVAILABLE)) {//接受数据

                    try {
                        jiexiSHShuju(intent);
                    } catch (Exception e) {
                        Log.e("手环有问题 eee", e.toString());
                        MyToast.showToast(TestForSoSService.this, getString(R.string.shouhuanyouwenti));
                    }


                } else if (action.equals(BLEForSoSService.ACTION_GATT_SERVICES_DISCOVERED)) {

                    //取消搜索手环
                    mBluetoothAdapter_zd.stopLeScan(mLeScanCallback);

                    //连接的时候分正常连接和重启手环连接
                    if (!TestForSoSService.is_cqsh) {
                        TestForSoSService.mService.enableTXNotification();
                        shLJCG();
                    }

                } else if (action.equals(BLEForSoSService.RSSI_DATA)) {
//                    MyLog.showLog("手环距离田园", "断开连接!");
                    //距离太远进行通知
                    String myrssi = intent.getStringExtra(BLEForSoSService.EXTRA_DATA);
                    if ((230 + Integer.valueOf(myrssi)) / 2 <= 60) {
                        showIntentActivityNotify_shjl();
                    }

                } else if (action.equals(BLEForSoSService.ACTION_GATT_DISCONNECTED)) {//断开连接

                    MyLog.showLog("手环", "断开连接!");
                    //第一次连接变量设置为true
                    TestForSoSService.is_shlj = true;


                    if (TestForSoSService.mDevice != null) {
                        TestForSoSService.mDevice = null;
                    }

                    TestForSoSService.mService.close();


                    //解除手环绑定和手环有问题断开连接
                    if (int_jcsh == 0) {//有问题断开连接

                        //销毁有关手环的activity
                        TestForSoSService.myActivityStack.finishAll();

                        //手环非正常断开进行通知
                        showIntentActivityNotify();

                    } else if (int_jcsh == 1) {//解除绑定断开
                        int_jcsh = 0;

                        //销毁有关手环的activity
                        TestForSoSService.myActivityStack.finishAll();

                        MyToast.showToast(TestForSoSService.this, getString(R.string.cishouhuanyijiechubangding));
                        TestForSoSService.no_shsz = 0;
                        Intent intent1 = new Intent(TestForSoSService.this, MainActivity.class);//改成MainActivity
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    } else if (int_jcsh == 2) {//重启手环
                        int_jcsh = 0;

                        //重启手环之后要进行自动搜索
                        TestForSoSService.mBluetoothAdapter.startLeScan(mLeScanCallback);

                    }


                }
            }
        };

        //注册广播
        LocalBroadcastManager.getInstance(this).registerReceiver(bleReceiver,
                makeGattUpdateIntentFilter());
//        if (TestForSoSService.mDevice != null) {//剧京改过  于2016 11 17
        //设置来电提醒
        phReceiver = new PhoneReceiver();
        filter = new IntentFilter(PhoneReceiver.ACTION);
        registerReceiver(phReceiver, filter);
//        }


    }

    //手环未连接
    private void shWeiLJ() {
        //等待5秒去通知连接手环
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                MyLog.showLog("自动连接", TestForSoSService.spf.getString("SH_ADD", ""));
                //自动连接手环
                if (TestForSoSService.spf.getString("SH_ADD", "").equals("")) {
                    Log.e("不需要", "自动连接");
                } else {
//                    if (TestForSoSService.myActivityStack.getAcNO()==0&&TestForSoSService.mDevice==null){
//                        MyLog.showLog("手环","未连接!");
//                        Intent intent = new Intent(TestForSoSService.this, ToumingActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
                    //判断蓝牙是否打开
                    if (!TestForSoSService.mBtAdapter.isEnabled()) {//蓝牙未打开
                        final Timer t = new Timer();
                        final TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {
                                if (TestForSoSService.mBtAdapter.isEnabled()) {
                                    if (t != null) {
                                        t.cancel();
                                    }
                                    //开始搜索
//                                    Log.e("打开蓝牙之后", "开始自动连接");
                                    mBluetoothAdapter_zd.startLeScan(mLeScanCallback);
                                }
                                //服务中不要去打开蓝牙
//                                else {
//                                    Intent enableIntent = new Intent(
//                                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                                    enableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(enableIntent);
//                                }

                            }
                        };
                        t.schedule(tt, 0, 1000 * 5);


                    } else {//蓝牙已打开
                        Log.e("不用打开蓝牙", "开始自动连接");
                        //开始搜索
                        mBluetoothAdapter_zd.startLeScan(mLeScanCallback);

                    }


                }


            }
        };
        t.schedule(tt, 1000 * 5);


    }

    //解析手环返回数据
    private void jiexiSHShuju(Intent intent) {
//        String strf = intent.getStringExtra(BLEForSoSService.EXTRA_DATA);
        final byte[] txValue = intent.getByteArrayExtra(BLEForSoSService.EXTRA_DATA);
        if (txValue == null) {
            return;
        }
        String strf = StringUtils.bytesToHexString(txValue);
        Log.e("test statt", strf);
        if (null != strf) {
            strf = strf.replace(" ", "");
            if (String.valueOf(strf.charAt(0)).toUpperCase()
                    .equals("A")) {
                //电量
                if (String.valueOf(strf.charAt(17)).equals("3")
                        && String.valueOf(strf.charAt(21)).equals("1")) {
                    MyLog.showLog("正常返回手环电量", strf);
                    String str1 = strf.substring(26, strf.length());
                    int bleDL = Integer.valueOf(str1, 16);
                    int_shdl = bleDL;
                    MyLog.showLog("手环电量", bleDL + "");
                    if (bleDL < 0) {
                        showIntentActivityNotify_shdlbz();//电量不足进行通知
                    }


                }

                if (String.valueOf(strf.charAt(17)).equals("3")
                        && String.valueOf(strf.charAt(21)).equals("7")) {// 呼救
                    MyLog.showLog("TestForSoSService 手环呼救 110", strf);
//                    if (is_dll) {
//                        if (!Content.flagSos) {
//                            Toast.makeText(TestForSoSService.this, R.string.qingkaiqidingweiquanxian, Toast.LENGTH_SHORT).show();
//                        } else {
                    jinruHujiu();
//                        }

//                    } else {
//                        if (mDevice != null) {
//                            Utils.sendSHML(TestForSoSService.this, CommonSHML.SHML_FSSB);
//                        }
//                        MyToast.showToast(TestForSoSService.this, getString(R.string.qingxiandengluzhanghao));
//                    }

                }

                if (String.valueOf(strf.charAt(17)).equals("3")
                        && String.valueOf(strf.charAt(21)).equals("8")) {// 呼叫
                    MyLog.showLog("TestForSoSService 手环呼叫 119", strf);
                    Logger.e("shouhuansdlkamlskdj", StaticCommon.ZYTZ + "####");
                    if (StaticCommon.ZYTZ.equals("")) {
                        if (mDevice != null) {
                            Utils.sendSHML(TestForSoSService.this, CommonSHML.SHML_FSSB);
                        }
                        MyToast.showToast(TestForSoSService.this, getString(R.string.qingxiantianjiazhongyaotongzhineirong));
                    } else {
                        //发送中命令
                        if (mDevice != null) {
                            Utils.sendSHML(CommonSHML.SHML_FSZ);
                        }
                        postSend();
                    }

                }

            }
        }

    }

    //手环连接成功的跳转
    private void shLJCG() {

        if (TestForSoSService.is_shlj) {
            TestForSoSService.is_shlj = false;

            //连接上手环做标记
            TestForSoSService.mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);

            //保存手环地址
            editor.putString("SH_ADD", deviceAddress);
            editor.commit();

            if (TestForSoSService.no_shsz == 0) {//进入设置手环界面
                Intent intent1 = new Intent(this, SetSHActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            } else if (TestForSoSService.no_shsz == 1) {//进入手环数据界面
//                Intent intent1 = new Intent(this, PostSHDataActivity.class);
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent1);
            } else if (TestForSoSService.no_shsz == 2) {
                // /只是连接手环,不进行跳转
                //手环在后台自动连接进行通知
                showIntentActivityNotify_shylj();
            } else if (TestForSoSService.no_shsz == 3) {//进入心率界面
                //TODO 心率
                Intent intent = new Intent(this, XinLvSHAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (TestForSoSService.no_shsz == 4) {//进入血压界面
                //TODO 心率
                Intent intent = new Intent(this, XueYaSHAty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (TestForSoSService.no_shsz == 5) {//步数
                BLEActivity.instance.finish();
            }

            //销毁BLEActivity
            TestForSoSService.myActivityStack.finish(BLEActivity.class);
//            TestForSoSService.myActivityStack.finish(XiLvTatolActivity.class);


        }

    }

    //进入呼救界面
    private void jinruHujiu() {
        is_shhj = true;
        Intent intent1 = new Intent(this, HelpActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

    }

    /**
     * 发送文字和图片重要提醒
     */
    protected void postSend() {
        final String noticeUrl = HttpConfig.BASE_URL + "sysMessage/addImportantNotice";// 发起文字重要提醒
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(noticeUrl);// sosNoticeText
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("content", StaticCommon.ZYTZ));
                params.add(new BasicNameValuePair("fromUserId", UserManager.getUserInfo().getUserId()));
                params.add(new BasicNameValuePair("zxys_userName", UserManager.getUserInfo().getPhone()));
                params.add(new BasicNameValuePair("zxys_encrypt", UserManager.getUserInfo().getSecret()));
                params.add(new BasicNameValuePair("language", LanguageUtil.judgeLanguage()));
                Log.e("tap发送重要提醒 502", params.toString());
                try {
                    HttpEntity entity = new UrlEncodedFormEntity(params,
                            "UTF-8");
                    httpPost.setEntity(entity);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity httpEntity = response.getEntity();
                    String result = EntityUtils.toString(httpEntity, "UTF-8");
                    Log.e("tap发送重要提醒 result 510", result);
                    JSONObject object = new JSONObject(result);
                    if (object.getString("result").equals("0000")) {
                        //手环显示发送成功
                        if (mDevice != null) {
                            Utils.sendSHML(TestForSoSService.this, CommonSHML.SHML_FSCG);
                        }
                        Toast.makeText(getApplicationContext(), R.string.zhongyaotongzhifasongchenggong,
                                Toast.LENGTH_SHORT).show();
                    } else if (object.getString("result").equals("9992")) {
                        if (mDevice != null) {
                            Utils.sendSHML(TestForSoSService.this, CommonSHML.SHML_FSSB);
                        }
                        Toast.makeText(getApplicationContext(), R.string.henyihan,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (mDevice != null) {
                            Utils.sendSHML(TestForSoSService.this, CommonSHML.SHML_FSSB);
                        }
                        Toast.makeText(getApplicationContext(), R.string.zhongyaotongzhifasongshibai,
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.fuwuqiyichang,
                            Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
                super.run();
            }
        }.start();
    }

    //UART service connected/disconnected
    public static ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((BLEForSoSService.LocalBinder) rawBinder).getService();
            TestForSoSService.mService =  ((BLEForSoSService.LocalBinder) rawBinder).getService();
            MyLog.showLog("mService", "onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e("nRFUART", "Unable to initialize Bluetooth");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            MyLog.showLog("mService", "155");
            mService = null;
        }
    };

    //手环广播动作
    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLEForSoSService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BLEForSoSService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BLEForSoSService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BLEForSoSService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BLEForSoSService.DEVICE_DOES_NOT_SUPPORT_UART);
        intentFilter.addAction(BLEForSoSService.RSSI_DATA);

        return intentFilter;
    }


    /**
     * Notification构造器
     */
    NotificationCompat.Builder mBuilder, mBuilder_shjl;
    /**
     * Notification的ID
     */
    int notifyId = 100;
    int notifyId_shjl = 100;

    /**
     * Notification管理
     */
    public NotificationManager mNotificationManager;

    /**
     * 初始化通知栏
     */
    private void initNotify() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                // .setNumber(number)//显示数量
                .setTicker("测试通知来啦")// 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_ALL)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_newlogo);


        mBuilder_shjl = new NotificationCompat.Builder(this);
        mBuilder_shjl.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                // .setNumber(number)//显示数量
                .setTicker("测试通知来啦")// 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_ALL)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_newlogo);


    }

    /**
     * 显示通知栏点击跳转到指定Activity
     */
    public void showIntentActivityNotify() {
        // Notification.FLAG_ONGOING_EVENT --设置常驻
        // Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
        // notification.flags = Notification.FLAG_AUTO_CANCEL;
        //在通知栏上点击此通知后自动清除此通知
        mBuilder.setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher3)
                // 点击后让通知将消失
                .setContentTitle(getString(R.string.shouhuanyiduankai)).setContentText(getString(R.string.shouhuanlianjieyouwenti))
                .setTicker(getString(R.string.shouhuanyiduankai));
        // 点击的意图ACTION是跳转到Intent

        //如果在手环界面里边不进行跳转
        if (TestForSoSService.myActivityStack.getAcNO() == 0) {
            TestForSoSService.no_shsz = 2;
            Intent resultIntent = new Intent(this, BLEActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
        }

        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    /**
     * 显示通知栏点击跳转到指定Activity
     */
    public void showIntentActivityNotify_shjl() {
        // Notification.FLAG_ONGOING_EVENT --设置常驻
        // Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
        // notification.flags = Notification.FLAG_AUTO_CANCEL;
        //在通知栏上点击此通知后自动清除此通知
        mBuilder_shjl.setAutoCancel(true)
                // 点击后让通知将消失
                .setContentTitle(getString(R.string.shouhuanjulitaiyuan)).setContentText(getString(R.string.qingjinkenengdeshishouhuankaojinshouji))
                .setTicker(getString(R.string.shouhuanjulitaiyuan));

        mNotificationManager.notify(notifyId_shjl, mBuilder_shjl.build());
    }

    /**
     * 显示通知栏点击跳转到指定Activity
     */
    public void showIntentActivityNotify_shylj() {
        // Notification.FLAG_ONGOING_EVENT --设置常驻
        // Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
        // notification.flags = Notification.FLAG_AUTO_CANCEL;
        //在通知栏上点击此通知后自动清除此通知
        mBuilder_shjl.setAutoCancel(true)
                // 点击后让通知将消失
                .setContentTitle(getString(R.string.shouhuanyilianjie)).setContentText(getString(R.string.qingpeidaihaoshouhuan))
                .setTicker(getString(R.string.shouhuanyilianjie));

        mNotificationManager.notify(notifyId_shjl, mBuilder_shjl.build());
    }

    /**
     * 显示通知栏点击跳转到指定Activity
     */
    public void showIntentActivityNotify_shdlbz() {
        // Notification.FLAG_ONGOING_EVENT --设置常驻
        // Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
        // notification.flags = Notification.FLAG_AUTO_CANCEL;
        //在通知栏上点击此通知后自动清除此通知
        mBuilder_shjl.setAutoCancel(true)
                // 点击后让通知将消失
                .setContentTitle(getString(R.string.shouhuandianliangbuzu)).setContentText(getString(R.string.qingjishichongdian))
                .setTicker(getString(R.string.shouhuandianliangbuzu));

        mNotificationManager.notify(notifyId_shjl, mBuilder_shjl.build());
    }


    /**
     * 初始化要用到的系统服务
     */
    private void initService() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /** 初始化通知栏 */

        initNotify();


    }

    /**
     * 清除当前创建的通知栏
     */
    public void clearNotify(int notifyId) {
        mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
//		mNotification.cancel(getResources().getString(R.string.app_name));
    }

    /**
     * 清除所有通知栏
     */
    public void clearAllNotify() {
        mNotificationManager.cancelAll();// 删除你发的所有通知
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }


    @Override
    public void onDestroy() {
        Log.e("TestForSoSService", "onDestroy");
        super.onDestroy();
    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        Log.e("TestForSoSService", "onStart");
        super.onStart(intent, startId);

    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("TestForSoSService", "onUnbind");
        return super.onUnbind(intent);
    }

    //搜索手环监听
    public static BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {


//                    MyLog.showLog("蓝牙设备", "名字:" + device.getName() +
//                            "地址:" + device.getAddress());


                    if (device.getAddress().equals(TestForSoSService.spf.getString("SH_ADD", ""))) {

                        TestForSoSService.deviceAddress = device.getAddress();
                        TestForSoSService.deviceName = device.getName();
//                        MyLog.showLog("要连接的手环:", "名字:" + TestForSoSService.deviceName +
//                                "地址:" + TestForSoSService.deviceAddress);

                        if (TestForSoSService.deviceAddress != null) {

                            //是不是重启手环在进行的连接
                            if (is_cqsh) {
                                if (TestForSoSService.mDevice == null) {

                                    TestForSoSService.no_shsz = 2;
                                    TestForSoSService.mService.connect(TestForSoSService.deviceAddress);
                                }

                            } else {
                                //已经连接上手环或已经进入手环设置界面就不用再连接
                                if (TestForSoSService.myActivityStack.getAcNO() == 0 && TestForSoSService.mDevice == null) {

                                    TestForSoSService.no_shsz = 2;
                                    TestForSoSService.mService.connect(TestForSoSService.deviceAddress);
                                }
                            }


                        }


                    }
                }
            };
}
