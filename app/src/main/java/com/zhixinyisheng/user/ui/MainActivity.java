package com.zhixinyisheng.user.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.config.SavePath;
import com.and.yzy.frame.update.AppUpdateUtil;
import com.and.yzy.frame.update.UpdateCallBack;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.util.SPUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.MyEmconversation;
import com.hyphenate.easeui.domain.SysMessageEntity;
import com.hyphenate.easeui.utils.EaseIMUrl;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.ActionFriendAdapter;
import com.zhixinyisheng.user.config.AppConfig;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.domain.XinLvZXT;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.service.DownloadLiaService;
import com.zhixinyisheng.user.slidingmenu.SlidingFragmentActivity;
import com.zhixinyisheng.user.slidingmenu.SlidingMenu;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.ui.ConversationAty;
import com.zhixinyisheng.user.ui.IM.ui.FriendsDetialAty;
import com.zhixinyisheng.user.ui.IM.ui.SelectFriendsAty;
import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.ui.messure.XinLvJcActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;
import com.zhixinyisheng.user.ui.sos.HelpActivity;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.PermissionsUtil;
import com.zhixinyisheng.user.util.RotateUtils;
import com.zhixinyisheng.user.util.tool.ActivityController;
import com.zhixinyisheng.user.util.tool.UnzipAssets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_IN;
import static android.util.TypedValue.COMPLEX_UNIT_MM;
import static android.util.TypedValue.COMPLEX_UNIT_PT;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener, AMapLocationListener {
    /**
     * 主界面头像
     */
    RelativeLayout cjs_rlb;
    /**
     * 主界面标题栏
     */

    TextView cjs_tvt;
    @Bind(R.id.cjs_rl_xhd)
    RelativeLayout cjsRlXhd;
    private Fragment mContent;
    ImageView iv_sliding;//侧滑按钮
    public static MainActivity instance;
    private LoginReceiver receiver;
    RelativeLayout rl_touming, rl_touming_kscl;//透明层
    //    Button btn_kscs,btn_kscl;//快速测试按钮
    ImageView title_xinxi;//消息按钮;
    RelativeLayout rl_friends;//下面的好友布局
    private GridView actionFriendView; // 关注的好友列表
    private Button upList;
    private boolean isUp = true;// 是否展开
    private RelativeLayout.LayoutParams params;
    //下边头像集合
    List<EaseUser> picList = new ArrayList<>();
    ActionFriendAdapter friendAdapter;

    //系统消息小红点
    TextView tv_sys;
    ImageView btn_main_up;
    String versionName;
    public static int di_height;
    RelativeLayout cjs_rlr;
    View title_line;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient = null;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    SPUtils spUtils;
    private long breakPoints;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        AppManger.getInstance().addActivity(this);
        instance = this;
        TestService.is_dll = true;
        initView();
        initdata();
        onclick();
        initSlidingMenu(savedInstanceState);
        //手环服务
//        zcBLEServices();
        postUpdate();
        getLocationInfo();
        //下载图文识别文件
//        tessdataDownload();
    }

    /**
     * 解压图文识别文件
     */
    private void tessdataDownload() {
        try {
            UnzipAssets.unZip(this, DownloadLiaService.saveFileName, SavePath.savePath);
        } catch (IOException e) {
            Logger.e("eroreojkroasjdo", e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 注册广播
     */
    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter(AppConfig.LOGIN_ACTION);
        registerReceiver(receiver, intentFilter);
        IntentFilter filter = new IntentFilter("feihuoliang.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        IntentFilter filter1 = new IntentFilter("exceptfeihuoliang.broadcast.action");
        registerReceiver(broadcastReceiver1, filter1);
        IntentFilter filter2 = new IntentFilter("haoyoulanxiaoshi.broadcast.action");
        registerReceiver(broadcastReceiver2, filter2);
        IntentFilter filter3 = new IntentFilter("haoyoulanxianshi.broadcast.action");
        registerReceiver(broadcastReceiver3, filter3);
        /**
         * 同意添加好友的广播
         */
        IntentFilter filter_ty = new IntentFilter(Constant.MYBROADCASTACTION_MAINATY_TYJHY);
        registerReceiver(broadcastReceiver_ty, filter_ty);

        /**
         * 小红点的广播
         */
        IntentFilter intentFilter_xhd = new IntentFilter(Constant.MYBROADCASTACTION_XIAOHONGDIAN);
        registerReceiver(myBroadcastReceiver_xhd, intentFilter_xhd);
    }

    /**
     * 获取位置信息
     */
    private void getLocationInfo() {
        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
        // 设置是否只是定位一次，默认为false
        mLocationOption.setOnceLocation(true);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();
    }

    /**
     * 检查更新接口
     */
    private void postUpdate() {
        try {
            versionName = getPackageManager().getPackageInfo("com.zhixinyisheng.user", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        FormBody formBody = new FormBody.Builder()
                .add("version", versionName)
                .add("zxys_userName", UserManager.getUserInfo().getPhone())
                .add("zxys_encrypt", UserManager.getUserInfo().getSecret())
                .build();

        AppUpdateUtil updateUtil = new AppUpdateUtil(this, HttpConfig.UPDATE_URL, formBody);

        updateUtil.checkUpdate(new UpdateCallBack() {
            @Override
            public void onError() {
            }

            @Override
            public void isUpdate(String result) {

            }

            @Override
            public void isNoUpdate() {
            }
        });
    }

    private void initView() {
        tv_sys = (TextView) findViewById(R.id.main_unread_msg_number);
        btn_main_up = (ImageView) findViewById(R.id.btn_main_up);
        title_line = findViewById(R.id.title_line);
        title_line.setVisibility(View.GONE);
        BaseApplication.username = UserManager.getUserInfo().getUsername();
        BaseApplication.headurl = UserManager.getUserInfo().getHeadUrl();
    }


    //注册广播服务
    private void zcBLEServices() {
        MyLog.showLog("启动 main", "服务");
        //不死服务
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
//        startDownloadLiaService();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
//                breakPoints = (long) spUtils.get("breakPoints", 0L);
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (breakPoints > 0 && breakPoints < 29386270) {
//                        Intent intent1 = new Intent(MainActivity.this, DownloadLiaService.class);
//                        startService(intent1);
//                        Toast.makeText(MainActivity.this, R.string.jixuxiazaizhongwenshibieku, Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Permission Denied
//                    Toast.makeText(MainActivity.this, R.string.qingkaiqiwenjiancunchuquanxian, Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

    private void startDownloadLiaService() {
        spUtils = new SPUtils("breakPoints");
        breakPoints = (long) spUtils.get("breakPoints", 0L);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            if (breakPoints > 0 && breakPoints < 29386270) {
                Intent intent1 = new Intent(MainActivity.this, DownloadLiaService.class);
                startService(intent1);
                Toast.makeText(MainActivity.this, R.string.jixuxiazaizhongwenshibieku, Toast.LENGTH_SHORT).show();
            }
        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            rl_friends.setVisibility(View.GONE);
        }
    };
    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            rl_friends.setVisibility(View.VISIBLE);
        }
    };
    BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            rl_friends.setVisibility(View.GONE);
        }
    };
    BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            rl_friends.setVisibility(View.VISIBLE);
        }
    };
    BroadcastReceiver broadcastReceiver_ty = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e("MainActivity", "同意加好友");
            /**
             * 好友添加到数据库
             */

            Bundle bundle = intent.getExtras();

            EaseUser eu = new EaseUser(bundle.getString("id"));
            eu.setNick(bundle.getString("name"));
            eu.setAvatar(bundle.getString("ava"));
            eu.setSos(bundle.getString("sos"));
            eu.setDatas(bundle.getString("ck"));
            eu.setNo(bundle.getString("no"));
            eu.setAgreeFlag(bundle.getInt("agreeFlag"));
            BaseFrameAty.showLog("bbbbbbb", eu.getDatas());


            if (bundle.getInt("qf") == 0) {//添加好友
                DemoDBManager.getInstance().saveContact(eu);
            } else if (bundle.getInt("qf") == 1) {//查看数据
                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
            }

        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister();
        modelCount();
    }

    /**
     * 注销广播
     */
    private void unregister() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        if (broadcastReceiver1 != null) {
            unregisterReceiver(broadcastReceiver1);
        }
        if (broadcastReceiver2 != null) {
            unregisterReceiver(broadcastReceiver2);
        }
        if (broadcastReceiver3 != null) {
            unregisterReceiver(broadcastReceiver3);
        }

        if (broadcastReceiver_ty != null) {
            unregisterReceiver(broadcastReceiver_ty);
        }

        if (myBroadcastReceiver_xhd != null) {
            unregisterReceiver(myBroadcastReceiver_xhd);
        }
    }

    /**
     * 用户数据统计
     */
    private void modelCount() {
        RetrofitUtils.createApi(DataUrl.class)
                .modelCount(UserManager.getUserInfo().getUserId(),
                        UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(),
                        Content.cbc,
                        Content.urineprotein,
                        Content.blood,
                        Content.bloodsugar,
                        Content.animalHeat,
                        Content.vitalCapacity,
                        Content.stepnumber,
                        Content.bmi,
                        Content.cruor,
                        Content.hbv,
                        Content.heartrate,
                        Content.immune,
                        Content.kidneycte,
                        Content.liverf,
                        Content.renalf,
                        Content.virus,
                        Content.antibody,
                        Content.electrolyte,
                        Content.tongue)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Logger.e("接口地址 destroy", call.request().url().toString());
                        try {
                            String result = response.body().string();
//                            Logger.e("接口返回结果 destroy", result);
                            Content.cbc = 0;
                            Content.urineprotein = 0;
                            Content.blood = 0;
                            Content.bloodsugar = 0;
                            Content.animalHeat = 0;
                            Content.vitalCapacity = 0;
                            Content.stepnumber = 0;
                            Content.bmi = 0;
                            Content.cruor = 0;
                            Content.hbv = 0;
                            Content.heartrate = 0;
                            Content.immune = 0;
                            Content.kidneycte = 0;
                            Content.liverf = 0;
                            Content.renalf = 0;
                            Content.virus = 0;
                            Content.antibody = 0;
                            Content.electrolyte = 0;
                            Content.tongue = 0;


                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcast();
        if (Content.isLabNull == 1) {
            rl_friends.setVisibility(View.GONE);
            Content.isLabNull = -1;
        }
        isUp = true;
        ViewTreeObserver vto = rl_friends.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_friends.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                di_height = rl_friends.getHeight();
                rl_friends.getWidth();
            }
        });

        getPiclist();
        //小红点
        getSysMessage();

        ActivityController.addActivity(this);
        ActivityController.setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    /**
     * 获取下边头像集合
     */
    public void getPiclist() {
        picList.clear();
        List<EaseUser> list = DemoDBManager.getInstance().getContactList_list();
        for (EaseUser eu : list) {
            if (eu.getNo().equals("1")) {
                picList.add(eu);
            }
        }
        for (int i = 0; i < 1; i++) {
            EaseUser eu = new EaseUser("加好");
            picList.add(eu);
        }
        friendAdapter = new ActionFriendAdapter(this, picList);
        actionFriendView.setAdapter(friendAdapter);
        if (picList.size() > 6) {
            RotateUtils.rotateArrow(btn_main_up, false);
            Resources resources = getResources();
            float v = applyDimension(COMPLEX_UNIT_DIP, 80f, resources.getDisplayMetrics());
            params.height = (int) v;
            rl_friends.setLayoutParams(params);
            btn_main_up.setVisibility(View.VISIBLE);
        } else {
//            Logger.e("picListSize 222",picList.size()+"$$$$");
            RotateUtils.rotateArrow(btn_main_up, false);
            Resources resources = getResources();
            float v = applyDimension(COMPLEX_UNIT_DIP, 80f, resources.getDisplayMetrics());
            params.height = (int) v;
            rl_friends.setLayoutParams(params);
            btn_main_up.setVisibility(View.GONE);
        }

    }

    private long lastTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Logger.e("lastTime", lastTime + "###");
            if ((System.currentTimeMillis() - lastTime) < 800) {
                startActivity(HelpActivity.class, null);
            }
            lastTime = System.currentTimeMillis();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initdata() {
        /**
         * by vampire 2016-11-04 15:59:18
         */

        actionFriendView = (GridView) findViewById(R.id.gridView_bottom);
        friendAdapter = new ActionFriendAdapter(this, picList);
        actionFriendView.setAdapter(friendAdapter);

        actionFriendView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EaseUser eu = picList.get(i);
                if (!eu.getUsername().equals("加好")) {
                    Constant.JUMP_FRIEND_ID = eu.getUsername();
                    Constant.JUMP_FRIEND_NAME = eu.getNick();
                    if (eu.getPayedUserID() == 1) {
                        Intent intent = new Intent(MainActivity.this, DoctorPageActivity.class);
                        intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, eu.getUsername());
                        startActivity(intent);
                        activityAnimation();
                    } else {
                        BaseApplication.userId = eu.getUsername();
                        startActivity(FriendsDetialAty.class, null);
                    }
                } else {
                    Constant.GZCKSOS = 0;
                    startActivity(SelectFriendsAty.class, null);
                }
            }
        });


        rl_friends = (RelativeLayout) findViewById(R.id.rl_friends);
        params = (RelativeLayout.LayoutParams) rl_friends.getLayoutParams();

        Log.e("高度", params.height + "");
//        Logger.e("size", picList.size() + "$$$");
//        if (picList.size() >6) {
//            Resources resources = getResources();
//            float v = applyDimension(COMPLEX_UNIT_DIP, 110f, resources.getDisplayMetrics());
//            params.height = (int) v;
//            rl_friends.setLayoutParams(params);
//            btn_main_up.setVisibility(View.VISIBLE);
//            Logger.e("wolaile","wolaile");
//        }
        if (picList.size() < 6) {
            btn_main_up.setVisibility(View.GONE);
        } else {
            btn_main_up.setVisibility(View.VISIBLE);
        }
        btn_main_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUp) {
                    Logger.e("piclist", picList.size() + "&&&");
                    if (picList.size() >= 6 && picList.size() <= 12) {
                        RotateUtils.rotateArrow(btn_main_up, isUp);
                        Resources resources = getResources();
                        float v = applyDimension(COMPLEX_UNIT_DIP, 150f, resources.getDisplayMetrics());
                        params.height = (int) v;
                        isUp = false;
                    } else if (picList.size() >= 13 && picList.size() <= 18) {
                        RotateUtils.rotateArrow(btn_main_up, isUp);
                        Resources resources = getResources();
                        float v = applyDimension(COMPLEX_UNIT_DIP, 150f, resources.getDisplayMetrics());
                        params.height = (int) v + 70 * 2;
                        isUp = false;
                    } else if (picList.size() >= 19 && picList.size() <= 24) {
                        RotateUtils.rotateArrow(btn_main_up, isUp);
                        Resources resources = getResources();
                        float v = applyDimension(COMPLEX_UNIT_DIP, 150f, resources.getDisplayMetrics());
                        params.height = (int) v + 71 * 3;
                        isUp = false;
                    } else if (picList.size() >= 25 && picList.size() < 30) {
                        RotateUtils.rotateArrow(btn_main_up, isUp);
                        Resources resources = getResources();
                        float v = applyDimension(COMPLEX_UNIT_DIP, 150f, resources.getDisplayMetrics());
                        params.height = (int) v + 74 * 4;
                        isUp = false;
                    }

                } else {
                    RotateUtils.rotateArrow(btn_main_up, isUp);
                    Resources resources = getResources();
                    float v = applyDimension(COMPLEX_UNIT_DIP, 80f, resources.getDisplayMetrics());
                    params.height = (int) v;
                    isUp = true;
                }
                rl_friends.setLayoutParams(params);
            }
        });


        receiver = new LoginReceiver();

        rl_friends = (RelativeLayout) findViewById(R.id.rl_friends);
        cjs_rlb = (RelativeLayout) findViewById(R.id.cjs_rlb);
        cjs_rlr = (RelativeLayout) findViewById(R.id.cjs_rlr);
        cjs_tvt = (TextView) findViewById(R.id.cjs_tvt);
        cjs_tvt.setText(R.string.homeTitle);
        iv_sliding = (ImageView) findViewById(R.id.iv_sliding);
        iv_sliding.setVisibility(View.VISIBLE);
        rl_touming = (RelativeLayout) findViewById(R.id.rl_touming);
//        btn_kscs = (Button) findViewById(R.id.btn_kscs);
        rl_touming_kscl = (RelativeLayout) findViewById(R.id.rl_touming_kscl);
        if (LanguageUtil.judgeLanguage().equals("zh")) {
            rl_touming.setBackgroundResource(R.drawable.xinshouzhiyin1);
            rl_touming_kscl.setBackgroundResource(R.drawable.xinshouzhiyin2);
        } else {
            rl_touming.setBackgroundResource(R.drawable.xinshouzhiyin1en);
            rl_touming_kscl.setBackgroundResource(R.drawable.xinshouzhiyin2en);
        }


//        btn_kscl = (Button) findViewById(R.id.btn_kscl);
        title_xinxi = (ImageView) findViewById(R.id.title_xinxi);
        title_xinxi.setVisibility(View.VISIBLE);

//        if (Content.isFirstLogin == 1) {
//            rl_touming.setVisibility(View.VISIBLE);
//            Content.isFirstLogin = 0;
//        } else {
//            rl_touming.setVisibility(View.GONE);
//        }

        checkISXinLvEmpty();
        initJpush();
        askPermission();
    }

    private void checkISXinLvEmpty() {
        RetrofitUtils.createApi(DataUrl.class)
                .checkXinLvZXT(UserManager.getUserInfo().getUserId(),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        UserManager.getUserInfo().getPhone(), UserManager.getUserInfo().getSecret())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Logger.e("接口地址 main", call.request().url().toString());
                        try {
                            String result = response.body().string();
//                            Logger.e("接口返回结果 main 1022", result);

                            XinLvZXT xinLvZXT = JSON.parseObject(result, XinLvZXT.class);
                            if (xinLvZXT.getSize() == 0) {
                                rl_touming.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });
    }

    public void activityAnimation() {
        overridePendingTransition(com.and.yzy.frame.R.anim.slide_right_in,
                com.and.yzy.frame.R.anim.slide_left_out);
    }


    /**
     * 访问权限
     */
    private void askPermission() {
        if (PermissionsUtil.is6()) {
            PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    if (PermissionsUtil.is6()) {
                        PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                if (PermissionsUtil.is6()) {
                                    PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse response) {
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
                                            }, Manifest.permission.RECORD_AUDIO);

                                        }

                                        @Override
                                        public void onPermissionDenied(PermissionDeniedResponse response) {
                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                            token.continuePermissionRequest();
                                        }
                                    }, Manifest.permission.INTERNET);
                                }

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }, Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }, Manifest.permission.CAMERA);
        }
    }

    void onclick() {
        rl_touming.setOnClickListener(this);
        rl_touming_kscl.setOnClickListener(this);
        cjs_rlr.setOnClickListener(this);
        cjs_rlb.setOnClickListener(this);
    }

    private void initJpush() {
        if (UserManager.isLogin()) {
            Set<String> set = new HashSet<>();
            set.add(UserManager.getUserInfo().getUserId());
            JPushInterface.setAliasAndTags(this, UserManager.getUserInfo().getUserId(), set, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Logger.v("code==" + i);
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cjs_rlb:
                toggle();
                break;
            case R.id.cjs_rlr:
                startActivity(ConversationAty.class, null);
                break;
            case R.id.rl_touming:
                rl_touming.setVisibility(View.GONE);
                rl_touming_kscl.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_touming_kscl:
                rl_touming_kscl.setVisibility(View.GONE);
                Content.XVORXY = "0";
                startActivity(XinLvJcActivity.class, null);
                break;
        }
    }

    private boolean hasAnimiation = true;

    /**
     * 启动一个Activity
     *
     * @param className 将要启动的Activity的类名
     * @param options   传到将要启动Activity的Bundle，不传时为null
     */
    public float startActivity(Class<?> className, Bundle options) {
        Intent intent = new Intent(this, className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivity(intent);
        if (hasAnimiation) {
            overridePendingTransition(com.and.yzy.frame.R.anim.slide_right_in,
                    com.and.yzy.frame.R.anim.slide_left_out);
        }
        return 0;
    }

    /**
     * 初始化侧边栏
     */
    public void initSlidingMenu(Bundle savedInstanceState) {
        // 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent");
        }

        if (mContent == null) {
            mContent = new HomeFragm();
            mContent.setUserVisibleHint(true);
        }
        switchConent(mContent, getString(R.string.homeTitle));
        // 设置左侧滑动菜单
        setBehindContentView(R.layout.menu_frame_left);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.menu_frame, new LeftFragment()).commit();
        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动的菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单阴影的图像资源
        sm.setShadowDrawable(null);
        // 设置滑动菜单视图的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.8f);
        // 设置触摸屏幕的模式,这里设置为全屏、
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//左右可以滑，从左边屏幕滑是侧边栏
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置可以滑动
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//设置不能滑动
        // 设置下方视图的在滚动时的缩放比例
        sm.setBehindScrollScale(0.0f);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     */
    public void switchConent(Fragment fragment, String title) {
        mContent = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
        getSlidingMenu().showContent();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息

                String city = aMapLocation.getCity();
                city = city.replace("市", "");
                String province = aMapLocation.getProvince();
                province = province.replace("省", "");
                Logger.e("location", city + "$$$" + province);
                UserInfo userInfo = UserManager.getUserInfo();
                userInfo.setCity(city);
                userInfo.setProvince(province);
                UserManager.setUserInfo(userInfo);
//                Logger.e("sdsadasdasd", aMapLocation.getCity() + "###" + aMapLocation.getProvince());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Toast.makeText(MainActivity.this, R.string.ninmeikaiqidingweiquanxian, Toast.LENGTH_SHORT).show();
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }


    }

    /**
     * 登陆广播接收器
     */
    public class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppConfig.LOGIN_ACTION)) {
                initJpush();
            }

        }
    }

    private long oldTime;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        long newtime = System.currentTimeMillis();
        if (newtime - oldTime > 3000) {
            oldTime = newtime;
            Toast.makeText(MainActivity.this, R.string.zaidianyicituichu, Toast.LENGTH_SHORT).show();
        } else {
            AppManger.getInstance().killAllActivity();

//            AppManger.getInstance().AppExit(this);
        }
    }

    public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics) {
        switch (unit) {
            case COMPLEX_UNIT_PX:
                return value;
            case COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }


    /**
     * 小红点的广播
     */
    BroadcastReceiver myBroadcastReceiver_xhd = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            getSysMessage();


        }
    };


    List<MyEmconversation> conversationList = new ArrayList<MyEmconversation>();

    /**
     * 小红点未读消息和未读回话的数量
     */
    public int int_unread = 0;

    /**
     * 获取所有好友
     */
    public void getSysMessage() {
        conversationList.clear();
        int_unread = 0;
        RetrofitUtils.createApi(EaseIMUrl.class)
                .getsysMessage(UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(),
                        UserManager.getUserInfo().getUserId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Logger.e("接口地址 main", call.request().url().toString());
                        try {
                            String result = response.body().string();
//                            Logger.e("接口返回结果 main 1022", result);

                            SysMessageEntity sysMessageEntity = JSON.parseObject(result, SysMessageEntity.class);
                            int_unread = 0;
                            int_unread += sysMessageEntity.getDb();
//                            try {
//                                for (SysMessageEntity.ListBean listBean : sysMessageEntity.getList()) {
//                                    MyEmconversation myEmconversation = new MyEmconversation();
//                                    long time = zhuanHuaTime(listBean.getCreateTime());
////                                myEmconversation.setTime(time);
//                                    myEmconversation.setListBean(listBean);
//                                    conversationList.add(myEmconversation);
//                                }
//                            } catch (Exception e) {
//
//                            }


                            /**
                             * 获取聊天消息
                             */
                            loadConversationList();

                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });
    }


    /**
     * 加载联系人列表
     *
     * @return +
     */
    protected void loadConversationList() {
        // 获取所有联系人
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (EMConversation conversation : conversations.values()) {
//            MyEmconversation myEmconversation = new MyEmconversation();
//            myEmconversation.setEmConversation(conversation);
//            conversationList.add(myEmconversation);
            int_unread += conversation.getUnreadMsgCount();
        }

//        for (MyEmconversation me : conversationList) {
//            if (me.getEmConversation() != null) {
//                int_unread += me.getEmConversation().getUnreadMsgCount();
//            } else {
//                SysMessageEntity.ListBean listBean = me.getListBean();
////                int_unread += me.getDb();
////                if (listBean.getType() == 1) {//好友申请
////                    if (listBean.getState() == 0) {
////                        int_unread++;
////                    }
////                } else {
////                    if (listBean.getFlag() == 0) {
////                        int_unread++;
////                    }
////                }
//            }
//        }
        tv_sys.setText(int_unread + "");
        if (int_unread == 0) {
            cjsRlXhd.setVisibility(View.GONE);
        } else {
            cjsRlXhd.setVisibility(View.VISIBLE);
        }
    }

}
