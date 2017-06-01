package com.zhixinyisheng.user.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.DataFragmentPagerAdapter;
import com.zhixinyisheng.user.adapter.DragAdapter;
import com.zhixinyisheng.user.adapter.OtherAdapter;
import com.zhixinyisheng.user.config.ChannelManage;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.ChannelItem;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.data.TextFgt;
import com.zhixinyisheng.user.ui.data.bmi.BmiFgt;
import com.zhixinyisheng.user.ui.data.bushu.BuShuFgt;
import com.zhixinyisheng.user.ui.data.feihuoliang.PulmonaryFgt;
import com.zhixinyisheng.user.ui.data.laboratory.LaboratoryFgt;
import com.zhixinyisheng.user.ui.data.shezhen.SheZhenFgt;
import com.zhixinyisheng.user.ui.data.shuimian.ShuiMianFgt;
import com.zhixinyisheng.user.ui.data.tiwen.TemperatureFgt;
import com.zhixinyisheng.user.ui.data.xinlv.HeartRateFragment;
import com.zhixinyisheng.user.ui.data.xuetang.BloodSugerFgt;
import com.zhixinyisheng.user.ui.data.xueya.FloodPressureFragment;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.tool.BaseTools;
import com.zhixinyisheng.user.view.ColumnHorizontalScrollView;
import com.zhixinyisheng.user.view.DragGrid;
import com.zhixinyisheng.user.view.OtherGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 主页
 */
public class HomeFragm extends BaseFgt implements AdapterView.OnItemClickListener {


    @Bind(R.id.mRadioGroup_content)
    LinearLayout mRadioGroup_content;
    @Bind(R.id.mColumnHorizontalScrollView)
    ColumnHorizontalScrollView mColumnHorizontalScrollView;
    @Bind(R.id.shade_left)
    ImageView shade_left;//左阴影部分
    @Bind(R.id.shade_right)
    ImageView shade_right;//右阴影部分
    @Bind(R.id.rl_column)
    RelativeLayout rl_column;
    @Bind(R.id.button_more_columns)
    ImageView button_more_columns;
    @Bind(R.id.ll_more_columns)
    RelativeLayout ll_more_columns;
    @Bind(R.id.mViewPager)
    ViewPager mViewPager;

    @Bind(R.id.ll_channel)
    LinearLayout ll_channel;//列表布局
    @Bind(R.id.userGridView)
    DragGrid userGridView;//用户栏目的GRIDVIEW
    @Bind(R.id.otherGridView)
    OtherGridView otherGridView;//其它栏目的GRIDVIEW
    @Bind(R.id.btn_editor)
    Button btnEditor;//编辑按钮
    /**
     * 用户栏目对应的适配器，可以拖动
     */
    DragAdapter userAdapter;
    /**
     * 其它栏目对应的适配器
     */
    OtherAdapter otherAdapter;

    /**
     * 用户选择的新闻分类列表
     */
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    /**
     * 其它栏目列表
     */
    ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 0;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;
    /**
     * Item宽度
     */
    private int mItemWidth = 0;
    private ArrayList<BaseFgt> fragments = new ArrayList<BaseFgt>();
    /**
     * 请求CODE
     */
    public final static int CHANNELREQUEST = 1;
    /**
     * 调整返回的RESULTCODE
     */
    public final static int CHANNELRESULT = 10;
    /**
     * 点击item返回的
     */
    public final static int CLICKRESULT = 9;
    //    int positionAll;
    boolean flag;//权限的旗帜
    /**
     * 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。
     */
    boolean isMove = false;

    @Override
    public int getLayoutId() {
        return R.layout.home;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        isVisible =true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean flagSos = PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
        Content.flagSos = flagSos;
        mScreenWidth = BaseTools.getWindowsWidth(getActivity());
        mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
        setChangelView();
        initChannelData();
        IntentFilter filter = new IntentFilter("change.broadcast.action");
        getActivity().registerReceiver(broadcastReceiver, filter);
        IntentFilter filter1 = new IntentFilter(Constant.MYACTION_ACCEPTFRI);
        getActivity().registerReceiver(broadcastReceiver1, filter1);
        TelephonyManager telephonyManager = (TelephonyManager) getActivity()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        String currentLanguage = LanguageUtil.currentLanguage();
        doHttp(RetrofitUtils.createApi(DataUrl.class).editLanguage(UserManager.getUserInfo().getUserId(),
                currentLanguage, "android", imei, phone, secret), HttpIdentifier.EDIT_LANGUAGE);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        Logger.e("currentLanguage", result);
    }

    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            DemoHelper.getInstance().asyncFetchContactsFromServer(null);
        }
    };
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            btnEditor.setText(getResources().getString(R.string.finish));
            userAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (broadcastReceiver != null) {
                getActivity().unregisterReceiver(broadcastReceiver);
            }
            if (broadcastReceiver1 != null) {
                getActivity().unregisterReceiver(broadcastReceiver1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initChannelData() {
//        userChannelList = ((ArrayList<mChannelItem>) ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).getUserChannel());
        otherChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).getOtherChannel());
        userAdapter = new DragAdapter(getActivity(), userChannelList);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(getActivity(), otherChannelList);
        otherGridView.setAdapter(otherAdapter);
        //设置GRIDVIEW的ITEM的点击监听
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
    }

    /**
     * 当栏目项发生变化时候调用
     */
    private void setChangelView() {
        initColumnData();
        initFragment();
        initTabColumn();
    }

    /**
     * 获取Column栏目 数据
     */
    private void initColumnData() {
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).getUserChannel());
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments.clear();//清空
        int count = userChannelList.size();
        for (int i = 0; i < count; i++) {
            Bundle data = new Bundle();
            //把标签名和id传给子fragment
            data.putString("text", userChannelList.get(i).getName());
            data.putInt("id", userChannelList.get(i).getId());
            if (userChannelList.get(i).getId() == 1) {
//                XinLvFgt xinLvFgt = new XinLvFgt();
                HeartRateFragment xinLvFgt = new HeartRateFragment();
                fragments.add(xinLvFgt);
            } else if (userChannelList.get(i).getId() == 2) {
//                XueYaFgt xueYaFgt = new XueYaFgt();
                FloodPressureFragment xueYaFgt = new FloodPressureFragment();
                fragments.add(xueYaFgt);
            } else if (userChannelList.get(i).getId() == 3) {
                PulmonaryFgt pulmonaryFgt = new PulmonaryFgt();
                fragments.add(pulmonaryFgt);
//                PulmonaryFragment feiHuoLiangFgt = new PulmonaryFragment();
//                fragments.add(feiHuoLiangFgt);
            } else if (userChannelList.get(i).getId() == 9) {
                BuShuFgt buShuFgt = new BuShuFgt();
                fragments.add(buShuFgt);


            } else if (userChannelList.get(i).getId() == 5) {
                ShuiMianFgt shuiMianFgt = new ShuiMianFgt();
                fragments.add(shuiMianFgt);
            } else if (userChannelList.get(i).getId() == 6) {
                BmiFgt bmiFgt = new BmiFgt();
                fragments.add(bmiFgt);
            } else if (userChannelList.get(i).getId() == 8) {
//                TiWenFgt tiWenFgt = new TiWenFgt();
//                fragments.add(tiWenFgt);
                TemperatureFgt tiWenFgt = new TemperatureFgt();
                fragments.add(tiWenFgt);
            } else if (userChannelList.get(i).getId() == 10) {
                LaboratoryFgt laboratoryFgt = new LaboratoryFgt();
                fragments.add(laboratoryFgt);

            } else if (userChannelList.get(i).getId() == 7) {
                BloodSugerFgt bloodSugerFgt = new BloodSugerFgt();
                fragments.add(bloodSugerFgt);
//                XueTangFgt xueTangFgt = new XueTangFgt();
//                fragments.add(xueTangFgt);
            } else if (userChannelList.get(i).getId() == 4) {
                SheZhenFgt sheZhenFgt = new SheZhenFgt();
                fragments.add(sheZhenFgt);
            } else {
                TextFgt textFgt = new TextFgt();
                textFgt.setArguments(data);
                fragments.add(textFgt);
            }

        }

        DataFragmentPagerAdapter mAdapetr = new DataFragmentPagerAdapter(this.getChildFragmentManager(), fragments);


        mViewPager.setAdapter(mAdapetr);
        mViewPager.setOnPageChangeListener(pageListener);
        /**Viewpager滑动动画*/
        mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            @Override
            public void transformPage(View page, float position) {
                if (position < -1) {//看不到的一页 *
                    page.setScaleX(1);
                    page.setScaleY(1);
                } else if (position <= 1) {
                    if (position < 0f) {
                        page.setTranslationX(0f);
                        page.setScaleX(1f);
                        page.setScaleY(1f);
                    } else if (position <= 1f) {
                        final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                        page.setAlpha(1 - position);
                        page.setPivotY(0.5f * page.getHeight());
                        page.setTranslationX(page.getWidth() * -position);
                        page.setScaleX(scaleFactor);
                        page.setScaleY(scaleFactor);
                    }
                } else {//看不到的另一页 *
                    page.setScaleX(1);
                    page.setScaleY(1);
                }
            }
        });
//
    }

    private int currentPosition;
    /**
     * ViewPager切换监听方法
     */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub

            currentPosition = position;
            mViewPager.setCurrentItem(position);
            Content.DATA = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//初始化全局日期
            Content.mSelDay = -1;
            Content.mSelMonth = -1;
            Content.mSelYear = -1;
            selectTab(position);


        }
    };
    private int pulPosition, tonPosition;

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        //判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            TextView checkView = (TextView) mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
                checkView.setBackgroundResource(R.drawable.btn_white_shape3);
                String name = checkView.getText().toString();
                if (name.equals("肺活量") || name.contains("Capacity")) {
                    pulPosition = j;
                } else if (name.equals("舌诊") || name.contains("Tongue")) {
                    tonPosition = j;
                }

            } else {
                ischeck = false;
                checkView.setBackgroundResource(R.color.white);
            }
            checkView.setSelected(ischeck);
        }
    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = userChannelList.size();
        mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            TextView columnTextView = new TextView(getActivity());
            columnTextView.setTextAppearance(getActivity(), R.style.top_category_scroll_view_item_text);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(20, 10, 20, 10);
            columnTextView.setTextSize(16);
            columnTextView.setId(i);
//            if (userChannelList.get(i).getName().equals("心率"))
            if (userChannelList.get(i).getId() == 1) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_heartRate));
            } else if (userChannelList.get(i).getId() == 2) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_bloodPressure));
            } else if (userChannelList.get(i).getId() == 3) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_lungCapacity));
            } else if (userChannelList.get(i).getId() == 9) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_steps));
            } else if (userChannelList.get(i).getId() == 5) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_sleep));
            } else if (userChannelList.get(i).getId() == 6) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_bmi));
            } else if (userChannelList.get(i).getId() == 8) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_temperature));
            } else if (userChannelList.get(i).getId() == 10) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_testSheet));
            } else if (userChannelList.get(i).getId() == 7) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_bloodSuger));
            } else if (userChannelList.get(i).getId() == 4) {
                columnTextView.setText(MyApplication.getApp().getResources().getString(R.string.home_tongue));
            }


//            columnTextView.setText(userChannelList.get(i).getName());

            columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Content.DATA = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//初始化全局日期
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        localView.setBackgroundResource(R.color.white);
                        if (localView != v) {
                            localView.setSelected(false);
                            localView.setBackgroundResource(R.color.white);
                        } else {
                            localView.setSelected(true);
                            localView.setBackgroundResource(R.drawable.btn_white_shape3);
                            mViewPager.setCurrentItem(i);
                        }
                    }
//                    Toast.makeText(getActivity(), userChannelList.get(v.getId()).getName(), Toast.LENGTH_SHORT).show();
                    if (userChannelList.get(v.getId()).getName().equals("肺活量")
                            || userChannelList.get(v.getId()).getName().equals("舌诊")
                            || userChannelList.get(v.getId()).getName().contains("Tongue")
                            || userChannelList.get(v.getId()).getName().contains("Capacity")) {
                        Intent intent = new Intent("feihuoliang.broadcast.action");//点击了肺活量的广播
                        getActivity().sendBroadcast(intent);
                    } else {
                        Intent intent = new Intent("exceptfeihuoliang.broadcast.action");//点击了除了肺活量的广播
                        getActivity().sendBroadcast(intent);
                    }

                }
            });
            mRadioGroup_content.addView(columnTextView, i, params);
            mRadioGroup_content.getChildAt(0).setBackgroundResource(R.drawable.btn_white_shape3);
            TextView tv = (TextView) mRadioGroup_content.getChildAt(0);

            String name = tv.getText().toString();
            if (name.equals("肺活量")
                    || name.equals("舌诊")
                    || name.contains("Capacity")
                    || name.contains("Tongue")) {
                Content.isLabNull = 1;
            }
        }
    }


    @OnClick(R.id.ll_more_columns)
    public void onClick() {
        ll_channel.setVisibility(View.VISIBLE);
        Intent intent = new Intent("haoyoulanxiaoshi.broadcast.action");//让底下好友栏消失的广播
        getActivity().sendBroadcast(intent);


    }

    /**
     * 退出时候保存选择后数据库的设置
     */
    private void saveChannel() {
        ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).deleteAllChannel();
        ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
        ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(getActivity());
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 点击ITEM移动动画
     *
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    /**
     * 获取点击的Item的对应View，
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(getActivity());
        iv.setImageBitmap(cache);
        return iv;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if (isMove) {
            return;
        }
        switch (parent.getId()) {
            case R.id.userGridView:
                if (Content.isFinish == 0) {
                    saveChannel();
                    mViewPager.setCurrentItem(position);
                    selectTab(position);
                    ll_channel.setVisibility(View.GONE);
                    if (currentPosition != pulPosition && currentPosition != tonPosition) {
                        Intent intent1 = new Intent("haoyoulanxianshi.broadcast.action");//让底下好友栏显示的广播
                        getActivity().sendBroadcast(intent1);
                    }

                } else {
//                    if (position != 0 && position != 1 && position != 2 && position != 3) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        otherAdapter.setVisible(false);
                        //添加到最后一个
                        otherAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, channel, userGridView);
                                    userAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
//                    }
//                    else {
//                        Toast.makeText(getActivity(), "此选项不可删除!", Toast.LENGTH_SHORT).show();
//                    }
                }
                break;
            case R.id.otherGridView:
                btnEditor.setText(getResources().getString(R.string.finish));
                Content.isFinish = 1;
                userAdapter.notifyDataSetChanged();
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    //添加到最后一个
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, channel, otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;

        }

    }

    @OnClick({R.id.btn_editor, R.id.rl_channel_touming, R.id.iv_gone})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_channel_touming:
                ll_channel.setVisibility(View.GONE);
                if (currentPosition != pulPosition && currentPosition != tonPosition) {
                    Intent intent1 = new Intent("haoyoulanxianshi.broadcast.action");//让底下好友栏显示的广播
                    getActivity().sendBroadcast(intent1);
                }

                break;
            case R.id.btn_editor:
                if (Content.isFinish == 0) {
                    btnEditor.setText(getResources().getString(R.string.finish));
                    Content.isFinish = 1;
                    userAdapter.notifyDataSetChanged();
                } else {
                    saveChannel();
                    if (userAdapter.isListChanged()) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(com.and.yzy.frame.R.anim.slide_up_in,
                                com.and.yzy.frame.R.anim.slide_down_out);
                    } else {
//                    super.onBackPressed();
                    }

                    btnEditor.setText(getResources().getString(R.string.edit));
                    Content.isFinish = 0;
                    userAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.iv_gone:
                ll_channel.setVisibility(View.GONE);
                if (currentPosition != pulPosition && currentPosition != tonPosition) {
                    Intent intent1 = new Intent("haoyoulanxianshi.broadcast.action");//让底下好友栏显示的广播
                    getActivity().sendBroadcast(intent1);
                }
                break;
        }
    }
}
