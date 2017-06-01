package com.zhixinyisheng.user.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.SysMessageEntity;
import com.hyphenate.easeui.utils.EaseIMUrl;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.CareListAdapter;
import com.zhixinyisheng.user.adapter.MyViewPagerAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.ui.ConversationAty;
import com.zhixinyisheng.user.ui.IM.ui.FriendsDetialAty;
import com.zhixinyisheng.user.ui.IM.ui.friend.FindActivity;
import com.zhixinyisheng.user.ui.data.bmi.BmiFgt;
import com.zhixinyisheng.user.ui.data.bushu.BuShuFgt;
import com.zhixinyisheng.user.ui.data.feihuoliang.PulmonaryFgt;
import com.zhixinyisheng.user.ui.data.laboratory.LaboratoryFgt;
import com.zhixinyisheng.user.ui.data.shezhen.SheZhenFgt;
import com.zhixinyisheng.user.ui.data.shuimian.ShuiMianFgt;
import com.zhixinyisheng.user.ui.data.synthesize_info.fragment.IntegratedInfoFgt;
import com.zhixinyisheng.user.ui.data.tiwen.TemperatureFgt;
import com.zhixinyisheng.user.ui.data.xinlv.HeartFragment;
import com.zhixinyisheng.user.ui.data.xuetang.BloodSugerFgt;
import com.zhixinyisheng.user.ui.data.xueya.BloodPressureFragment;
import com.zhixinyisheng.user.ui.mine.MyHealthyDataAty;
import com.zhixinyisheng.user.ui.mine.RenZhengAty;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.FoundDoctorActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.MyDoctorPageActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.WanShanXinXiAty;
import com.zhixinyisheng.user.ui.remind.UseMedicineActivity;
import com.zhixinyisheng.user.ui.setting.SettingAty;
import com.zhixinyisheng.user.ui.sidebar.PersonalInfoActivity;
import com.zhixinyisheng.user.ui.sos.HelpActivity;
import com.zhixinyisheng.user.util.GlideUtil;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.PermissionsUtil;
import com.zhixinyisheng.user.view.MaxListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;
import static com.zhixinyisheng.user.R.id.drawer_layout;

/**
 * 主页面（新）
 * Created by 焕焕 on 2017/4/11.
 */

public class MainAty extends BaseAty implements ViewPager.OnPageChangeListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.id_tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.id_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.iv_main_check)
    ImageView iv_main_check;
    @Bind(R.id.tv_title_right)
    ImageView tvTitleRight;
    @Bind(R.id.rl_main_right)
    RelativeLayout rlMainRight;
    @Bind(R.id.iv_main_friends)
    ImageView ivMainFriends;
    @Bind(R.id.iv_main_sos)
    ImageView ivMainSos;
    @Bind(R.id.rl_main_bottom)
    RelativeLayout rlMainBottom;
    @Bind(R.id.iv_main_right)
    ImageView ivMainRight;
    @Bind(R.id.fl_main)
    FrameLayout flMain;

    // TabLayout中的tab标题
    private String[] mTitles;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;
    private List<BaseFgt> mFragments = new ArrayList<BaseFgt>();
    private Dialog dialog;
    private ImageView ewm_ewm, ewm_sex, ewm_icon;
    private TextView ewm_name, ewm_id;
    private long mExitTime = 0;
    private List<EaseUser> careList = new ArrayList<>();//关注的好友集合
    private CareListAdapter careListAdapter;
    private MenuItem menuRemind, menuDoctorService, menuMyPage;
    private int position;
    @Override
    public int getLayoutId() {
        return R.layout.aty_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter(Constant.MAIN_BOTTOM_INVISIABLE);
        registerReceiver(bottomInvisiableReceiver, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter(Constant.MAIN_BOTTOM_VISIABLE);
        registerReceiver(bottomVisiableReceiver, intentFilter1);


        initView();
        setFragment();
        initJpush();
        editLanguage();
        if (PermissionsUtil.is6()) {
            if (toCheckCameraPermission()) {
            }
        }
        flMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("onTouch", "23133");
                if (position!=4&&position!=5){
                    rlMainBottom.setVisibility(View.GONE);
                    rlMainRight.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

    }

    /**
     * 检查权限（运行时权限）
     */
    private boolean toCheckCameraPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != result) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            return false;
        }
        return true;
    }

    BroadcastReceiver bottomInvisiableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            rlMainBottom.setVisibility(View.GONE);
        }
    };
    BroadcastReceiver bottomVisiableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            rlMainBottom.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != bottomInvisiableReceiver) {
            unregisterReceiver(bottomInvisiableReceiver);
        }
        if (null != bottomVisiableReceiver) {
            unregisterReceiver(bottomVisiableReceiver);
        }
    }

    private void editLanguage() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        String currentLanguage = LanguageUtil.currentLanguage();
        doHttp(RetrofitUtils.createApi(DataUrl.class).editLanguage(UserManager.getUserInfo().getUserId(),
                currentLanguage, "android", imei, phone, secret), HttpIdentifier.EDIT_LANGUAGE);
    }

    private void setFragment() {
        // Tab的标题采用string-array的方法保存，在res/values/arrays.xml中写
        if (LanguageUtil.judgeLanguage().equals("zh")) {
            mTitles = getResources().getStringArray(R.array.tab_titles);
        } else if (LanguageUtil.judgeLanguage().equals("en")) {
            mTitles = getResources().getStringArray(R.array.tab_titles_en);
        }
        IntegratedInfoFgt integratedInfoFgt = new IntegratedInfoFgt();
        mFragments.add(integratedInfoFgt);
        LaboratoryFgt laboratoryFgt = new LaboratoryFgt();
        mFragments.add(laboratoryFgt);
//        XinLvFgt xinLvFgt = new XinLvFgt();
//        HeartRateFragment xinLvFgt = new HeartRateFragment();
        HeartFragment xinLvFgt = new HeartFragment();
        mFragments.add(xinLvFgt);
//        XueYaFgt xueYaFgt = new XueYaFgt();
        BloodPressureFragment xueYaFgt = new BloodPressureFragment();
        mFragments.add(xueYaFgt);
        PulmonaryFgt pulmonaryFgt = new PulmonaryFgt();
        mFragments.add(pulmonaryFgt);
        SheZhenFgt sheZhenFgt = new SheZhenFgt();
        mFragments.add(sheZhenFgt);
        BuShuFgt buShuFgt = new BuShuFgt();
        mFragments.add(buShuFgt);
        ShuiMianFgt shuiMianFgt = new ShuiMianFgt();
        mFragments.add(shuiMianFgt);
        BmiFgt bmiFgt = new BmiFgt();
        mFragments.add(bmiFgt);
        TemperatureFgt tiWenFgt = new TemperatureFgt();
        mFragments.add(tiWenFgt);
        BloodSugerFgt bloodSugerFgt = new BloodSugerFgt();
        mFragments.add(bloodSugerFgt);
        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setTabMode(MODE_SCROLLABLE);
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(this);
    }

    public void setCurrentItem(int itemPos) {
        mViewPager.setCurrentItem(itemPos);
    }

    private void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);
        // 自己写的方法，设置NavigationView中menu的item被选中后要执行的操作
        onNavgationViewMenuItemSelected(mNavigationView);
        menuRemind = mNavigationView.getMenu().findItem(R.id.nav_menu_remind);
        menuDoctorService = mNavigationView.getMenu().findItem(R.id.nav_menu_doctor_service);
        menuMyPage = mNavigationView.getMenu().findItem(R.id.nav_menu_homepage);

        BaseApplication.username = UserManager.getUserInfo().getUsername();
        BaseApplication.headurl = UserManager.getUserInfo().getHeadUrl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setHeaderView();
        IntentFilter intentFilter_xhd = new IntentFilter(Constant.MYBROADCASTACTION_XIAOHONGDIAN);
        registerReceiver(myBroadcastReceiver_xhd, intentFilter_xhd);
        if (UserManager.getUserInfo().getIsDoctor() == 1) {
            menuRemind.setVisible(false);
            menuDoctorService.setVisible(false);
            menuMyPage.setVisible(true);
        } else {
            menuRemind.setVisible(true);
            menuDoctorService.setVisible(true);
            menuMyPage.setVisible(false);
        }
        getSysMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myBroadcastReceiver_xhd != null) {
            unregisterReceiver(myBroadcastReceiver_xhd);
        }
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

    private void getSysMessage() {
        doHttp(RetrofitUtils.createApi(EaseIMUrl.class).getsysMessage(
                UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret(),
                UserManager.getUserInfo().getUserId()), HttpIdentifier.SEND_EMARL);
    }

    int int_unread;

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.SEND_EMARL:
                SysMessageEntity sysMessageEntity = JSON.parseObject(result, SysMessageEntity.class);
                try {
                    for (int i = 0; i < sysMessageEntity.getList().size(); i++) {
                        if (sysMessageEntity.getList().get(i).getContent().contains("恭喜您")) {
                            UserInfo userInfo = UserManager.getUserInfo();
                            userInfo.setIsDoctor(1);
                            UserManager.setUserInfo(userInfo);
                            menuRemind.setVisible(false);
                            menuDoctorService.setVisible(false);
                            menuMyPage.setVisible(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int_unread = 0;
                int_unread += sysMessageEntity.getDb();
                loadConversationList();
                break;
        }
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
            int_unread += conversation.getUnreadMsgCount();
        }

        Log.e("MainAty int_unread 295", int_unread + "###");
        if (int_unread == 0) {
            tvTitleRight.setImageResource(R.drawable.title_xinxi);
        } else {
            tvTitleRight.setImageResource(R.drawable.title_xinxi_s);
        }
    }

    /**
     * 设置头部布局
     */
    private void setHeaderView() {
        View headerView = mNavigationView.getHeaderView(0);
//        ImageView iv_erweima = (ImageView) headerView.findViewById(R.id.iv_erweima);
//        TextView left_tv_uesrname = (TextView) headerView.findViewById(R.id.left_tv_uesrname);
//        ImageView left_img_head = (ImageView) headerView.findViewById(R.id.left_img_head);
        ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        TextView tvNick = (TextView) headerView.findViewById(R.id.tv_nick);
        LinearLayout llHeader = (LinearLayout) headerView.findViewById(R.id.ll_header);
        GlideUtil.loadCircleAvatar(this, UserManager.getUserInfo().getHeadUrl(), ivAvatar);
        if (UserManager.getUserInfo().getName().equals("")) {
            tvNick.setText(UserManager.getUserInfo().getUsername());
            if (UserManager.getUserInfo().getUsername().equals("")) {
                tvNick.setText(UserManager.getUserInfo().getCard() + "");
            }
        } else {
            tvNick.setText(UserManager.getUserInfo().getName());
        }
        llHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erWeiMaDialog();
            }
        });
//        left_img_head.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (UserManager.getUserInfo().getIsDoctor() == 1) {
//                    startActivity(WanShanXinXiAty.class, null);
//                } else {
//                    startActivity(PersonInformation.class, null);
//                }
//            }
//        });
//        iv_erweima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                erWeiMaDialog();
//            }
//        });
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

    /**
     * 设置NavigationView中menu的item被选中后要执行的操作
     *
     * @param mNav
     */
    private void onNavgationViewMenuItemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_my_profile:
                        if (UserManager.getUserInfo().getIsDoctor() == 1) {
                            startActivity(WanShanXinXiAty.class, null);
                        } else {
                            startActivity(PersonalInfoActivity.class, null);
                        }
                        break;
                    case R.id.nav_menu_data:
//                        startActivity(UseMedicineActivity.class, null);
                        startActivity(MyHealthyDataAty.class, null);
                        break;
                    case R.id.nav_menu_certification:
                        startActivity(RenZhengAty.class, null);
                        break;
                    case R.id.nav_menu_homepage:
                        startActivity(MyDoctorPageActivity.class, null);
                        break;
                    case R.id.nav_menu_setting:
                        startActivity(SettingAty.class, null);
                        break;
                    case R.id.nav_menu_remind:
                        startActivity(UseMedicineActivity.class, null);
                        break;
                    case R.id.nav_menu_doctor_service:
                        startActivity(FoundDoctorActivity.class, null);
                        break;
                }
                // Menu item点击后选中，并关闭Drawerlayout
//                menuItem.setChecked(true);
//                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // 如果剩一个说明在主页，提示按两次退出app
            exit();
        }
    }

    /**
     * 退出
     */
    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            AppManger.getInstance().killAllActivity();
        }
    }

    /**
     * 二维码的dialog
     */
    private void erWeiMaDialog() {
          /* 弹出dialog */
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.activity_dialog, null);
        dialog = new Dialog(this, R.style.MyDialog);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        Window window = dialog.getWindow();
        ewm_ewm = (ImageView) window.findViewById(R.id.img_erweima);// 二维码图片
        ewm_id = (TextView) window.findViewById(R.id.ewm_id);// 患者id
        ewm_name = (TextView) window.findViewById(R.id.ewm_Name);// 二维码上的姓名
        ewm_sex = (ImageView) window.findViewById(R.id.ewm_XB);// 二维码上的性别图标
        ewm_icon = (ImageView) window.findViewById(R.id.ewm_TX);// 二维码上的头像
        ewm_name.setText(UserManager.getUserInfo().getUsername());
        if (UserManager.getUserInfo().getName().equals("")) {
            ewm_name.setText(UserManager.getUserInfo().getUsername());
            if (UserManager.getUserInfo().getUsername().equals("")) {
                ewm_name.setText(UserManager.getUserInfo().getCard() + "");
            }
        } else {
            ewm_name.setText(UserManager.getUserInfo().getName());
        }

        ewm_id.setText("ID:" + UserManager.getUserInfo().getCard());
        Glide.with(this).load(UserManager.getUserInfo().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)
                .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(this)).into(ewm_icon);

        // ewm_icon.setImageBitmap();
        String sex = UserManager.getUserInfo().getSex() + "";
        if (sex.equals("0")) {
            ewm_sex.setImageResource(R.drawable.nv);
        } else if (sex.equals("1")) {
            ewm_sex.setImageResource(R.drawable.nan);
        }
        Glide.with(this).load(UserManager.getUserInfo().getTwoDimension()).into(ewm_ewm);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        if (position == 4 || position == 5) {
            rlMainRight.setVisibility(View.GONE);
        } else {
            rlMainRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @OnClick({R.id.iv_main_right, R.id.iv_main_friends, R.id.iv_main_check, R.id.iv_main_sos})
    public void onClick(View view) {
        //TODO 点击事件
        switch (view.getId()) {
            case R.id.iv_main_right:
                rlMainBottom.setVisibility(View.VISIBLE);
                rlMainRight.setVisibility(View.GONE);
                break;
            case R.id.iv_main_friends:
                startActivity(FindActivity.class, null);
                break;
            case R.id.iv_main_check://关注的好友
                Intent intent = new Intent(Constant.MESSURE_STOP);//如果心率和血压测量中，那么就停止测量
                sendBroadcast(intent);
                Intent intent1 = new Intent(Constant.MESSURE_STOP_BLOOD);//如果心率和血压测量中，那么就停止测量
                sendBroadcast(intent1);
                setPopWindow(view);
                break;
            case R.id.iv_main_sos:
                jumpToSos();
                break;
        }
    }

    private void setPopWindow(View view) {
        View v = LayoutInflater.from(this).inflate(R.layout.pop_main, null, false);
        final PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        int[] location = new int[2];
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        popupWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        popupWindow.setFocusable(true);
        // 获得位置 这里的view是目标控件，就是你要放在这个view的上面还是下面
        view.getLocationOnScreen(location);
        setLayoutDarken();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_main_check.setBackgroundResource(R.mipmap.ic_btn_ck);
                setLayoutBright();
            }
        });
        MaxListView lvPop = (MaxListView) v.findViewById(R.id.lv_pop);
        careList.clear();
        List<EaseUser> list = DemoDBManager.getInstance().getContactList_list();
        for (EaseUser eu : list) {
            if (eu.getNo().equals("1")) {
                careList.add(eu);
            }
        }
        if (careList.size() == 0) {
            popupWindow.dismiss();
            showToast(getString(R.string.ninmeiyouguanzhudehaoyouqingtianjia));
            setLayoutBright();
            return;
        }
        careListAdapter = new CareListAdapter(this, careList, R.layout.item_pop);
        lvPop.setAdapter(careListAdapter);
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = v.getMeasuredWidth();    //获取测量后的宽度
        int popupHeight = v.getMeasuredHeight();  //获取测量后的高度
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);

        lvPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser eu = careList.get(position);
                Constant.JUMP_FRIEND_ID = eu.getUsername();
                Constant.JUMP_FRIEND_NAME = eu.getNick();


                if (eu.getPayedUserID() == 1) {
                    Intent intent = new Intent(MainAty.this, DoctorPageActivity.class);
                    intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, eu.getUsername());
                    startActivity(intent);
                    activityAnimation();
                    popupWindow.dismiss();
                } else {
                    BaseApplication.userId = eu.getUsername();
                    startActivity(FriendsDetialAty.class, null);
                    popupWindow.dismiss();
                }
            }
        });
        if (popupWindow.isShowing()) {
            iv_main_check.setBackgroundResource(R.mipmap.ic_btn_cks_n);
        } else {
            iv_main_check.setBackgroundResource(R.mipmap.ic_btn_ck);
        }
    }

    private void setLayoutDarken() {
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = this.getWindow()
                .getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置全局布局明亮
     */
    private void setLayoutBright() {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    /**
     * 检查权限（运行时权限）
     */
    private boolean toCheckPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (PackageManager.PERMISSION_GRANTED != result) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
        return true;
    }

    private void jumpToSos() {
        if (PermissionsUtil.is6()) {
            if (toCheckPermission()) {
                startActivity(HelpActivity.class, null);
            }
        } else {
            startActivity(HelpActivity.class, null);
        }


//        if (PermissionsUtil.is6()) {
//            PermissionsUtil.checkPermissionBy6(new PermissionListener() {
//                @Override
//                public void onPermissionGranted(PermissionGrantedResponse response) {
//                    startActivity(HelpActivity.class, null);
//                }
//                @Override
//                public void onPermissionDenied(PermissionDeniedResponse response) {
//                }
//                @Override
//                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                    PermissionsUtil.showPermissDialogBy6(MainAty.this, token, getString(R.string.kaiqixiangjiquanxian));
//                }
//            }, Manifest.permission.ACCESS_FINE_LOCATION);
//        } else {
//            if (PermissionsUtil.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//                startActivity(HelpActivity.class, null);
//            } else {
//                Toast.makeText(MainAty.this, R.string.weikaiqixiangjiquanxian, Toast.LENGTH_SHORT).show();
//            }
//        }
    }


    @OnClick(R.id.tv_title_right)
    public void onClick() {
        startActivity(ConversationAty.class, null);
    }

}
