package com.zhixinyisheng.user.ui.login;

import android.Manifest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.util.SPUtils;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.MyAdapter;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.MainAty;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 第一个
 * Created by 焕焕 on 2017/2/7.
 */
public class LeaderAty extends BaseAty {
    private static boolean isFirstOpen;//是否是第一次打开
    private ViewPager vpLeader;
    SPUtils spUtils;
    private ImageView[] icons = new ImageView[3];
    private ArrayList<View> views = new ArrayList<View>();
    private MyAdapter adapter;// 适配器
    @Bind(R.id.iv_start)
    ImageView ivStart;
    @Bind(R.id.rl_vp)
    RelativeLayout rlVp;


    @Override
    public int getLayoutId() {
        //判断版本
        spUtils = new SPUtils("userConfig");
        isFirstOpen = (boolean) spUtils.get("firstLogin", true);
       /* if (isFirstOpen) {
            return R.layout.aty_leader;
        } else {
            return R.layout.aty_leader;
        }*/
        return R.layout.aty_leader;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initJPush();
        if (!isFirstOpen) {
            rlVp.setVisibility(View.GONE);
            ivStart.setVisibility(View.VISIBLE);
            if (LanguageUtil.judgeLanguage().equals("zh")){
                ivStart.setImageResource(R.drawable.qidongye);
            }else if (LanguageUtil.judgeLanguage().equals("en")){
                ivStart.setImageResource(R.drawable.qidongye_en);
            }
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (UserManager.isLogin()) {// 如果登陆
                         //登录人信息保存到baseapplication
                        BaseApplication.userId = UserManager.getUserInfo().getUserId();
                        BaseApplication.phone = UserManager.getUserInfo().getPhone();
                        BaseApplication.secret = UserManager.getUserInfo().getSecret();
                        startActivity(MainAty.class, null);
//                        finish();
                    } else {
                        startActivity(LoginAty.class, null);
                        finish();
                    }
                }
            };
            timer.schedule(task, 1000 * 2); //2秒后


//            MyCount myCount = new MyCount(2000, 1000);
//            myCount.start();
//            startActivity(SplashAty.class, null);
//            finish();
        } else {
            rlVp.setVisibility(View.VISIBLE);
            ivStart.setVisibility(View.GONE);
            Content.isFirstLogin = 1;
//            askPermission();
            initLeadIcon();
            initViewPagerData();
        }
    }

    private void initJPush() {
        /****** 极光相关@author 剧京 ******/
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setAlias(this, "jujing",

                new TagAliasCallback() {
                    @Override
                    public void gotResult(int arg0, String arg1, Set<String> arg2) {
                    }
                });
    }
    private class MyCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


        }

        @Override
        public void onFinish() {
            if (UserManager.isLogin()) {// 如果登陆

                /**
                 * 登录人信息保存到baseapplication
                 */
                BaseApplication.userId = UserManager.getUserInfo().getUserId();
                BaseApplication.phone = UserManager.getUserInfo().getPhone();
                BaseApplication.secret = UserManager.getUserInfo().getSecret();


                startActivity(MainAty.class, null);
            } else {
                startActivity(LoginAty.class, null);
            }

            finish();


        }
    }
    /**
     * 初始化引导页面小图标+skip (5个小图标 + 1个skip文字)
     */
    private void initLeadIcon() {
        // 初始3张ICON为默认的灰图 － 当Pager切换后将改
        icons[0] = (ImageView) findViewById(R.id.icon1);
        icons[1] = (ImageView) findViewById(R.id.icon2);
        icons[2] = (ImageView) findViewById(R.id.icon3);
        icons[0].setImageResource(R.drawable.xiaodian2);
    }
    ImageView img,img1,img2;
    /**
     * 初始化引导页面ViewPager视图 (给ViewPager设置Adapter), 初始化引导页面ViewPager内显示的视图数据
     * (向ViewPager的Adapter内添加视图(图片))
     */
    private void initViewPagerData() {
        LayoutInflater inflater = getLayoutInflater();
        /** 布局加载器加载三个布局 */
        View view1 = inflater.inflate(R.layout.leader1, null);
        View view2 = inflater.inflate(R.layout.leader2, null);
        View view3 = inflater.inflate(R.layout.leader3, null);
        img = (ImageView) view3.findViewById(R.id.iv_photo);
        img1 = (ImageView) view1.findViewById(R.id.iv_photo);
        img2  = (ImageView) view2.findViewById(R.id.iv_photo);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        if (LanguageUtil.judgeLanguage().equals("zh")){
            img.setBackgroundResource(R.drawable.guide_page_03);
            img1.setBackgroundResource(R.drawable.guide_page_01);
            img2.setBackgroundResource(R.drawable.guide_page_02);
        }else{
            img.setBackgroundResource(R.drawable.guide_page_03_en);
            img1.setBackgroundResource(R.drawable.guide_page_01_en);
            img2.setBackgroundResource(R.drawable.guide_page_02_en);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spUtils.put("firstLogin", false);
                startActivity(SplashAty.class, null);
                finish();
            }
        });
        adapter = new MyAdapter(views);
        /** 实例化ViewPager */
        vpLeader = (ViewPager) findViewById(R.id.vp_leader);
        vpLeader.setAdapter(adapter);
        vpLeader.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            // 页面改变之后
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < icons.length; i++) {
                    icons[i].setImageResource(R.drawable.xiaodian1);
                }
                icons[position]
                        .setImageResource(R.drawable.xiaodian2);
            }
        });

    }

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
                        }, Manifest.permission.READ_PHONE_STATE);
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

}
