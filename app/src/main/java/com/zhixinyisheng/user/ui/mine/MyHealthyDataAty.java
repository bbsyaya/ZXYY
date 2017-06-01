package com.zhixinyisheng.user.ui.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.adapter.MyRecyclerAdapter;
import com.zhixinyisheng.user.ui.IM.domain.FriendDetialEntity;
import com.zhixinyisheng.user.ui.IM.domain.YichangDataEntity;
import com.zhixinyisheng.user.ui.IM.widget.DividerItemDecoration;
import com.zhixinyisheng.user.ui.data.bmi.BmiNewFgt;
import com.zhixinyisheng.user.ui.data.bushu.BuShuNewFgt;
import com.zhixinyisheng.user.ui.data.laboratory.LaboratoryFgt;
import com.zhixinyisheng.user.ui.data.shuimian.ShuiMianNewFgt;
import com.zhixinyisheng.user.ui.data.tiwen.TiWenFgt;
import com.zhixinyisheng.user.ui.data.xinlv.XinLvFgt;
import com.zhixinyisheng.user.ui.data.xuetang.XueTangFgt;
import com.zhixinyisheng.user.ui.data.xueya.XueYaFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.DensityUtils;
import com.zhixinyisheng.user.util.LanguageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 我的健康数据
 * Created by 焕焕 on 2016/11/11.
 */
public class MyHealthyDataAty extends BaseAty {

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
    @Bind(R.id.main_unread_msg_number)
    TextView mainUnreadMsgNumber;
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
    @Bind(R.id.fridd_tvycsj)
    TextView friddTvycsj;
    @Bind(R.id.fridd_tvhx1)
    TextView friddTvhx1;
    @Bind(R.id.fridd_rlv)
    RecyclerView friddRlv;
    @Bind(R.id.fridd_tv_qbsj)
    TextView friddTvQbsj;
    @Bind(R.id.fridd_tv_xm)
    TextView friddTvXm;
    @Bind(R.id.fridd_rl_xm)
    RelativeLayout friddRlXm;
    @Bind(R.id.fragment_content)
    RelativeLayout fragmentContent;
    //recycleview集合
    private List<YichangDataEntity> mDatas = new ArrayList<>();
    //recycleview适配器
    private MyRecyclerAdapter recycleAdapter;
    //对话框集合
    private ArrayList<DialogMenuItem> testItems = new ArrayList<DialogMenuItem>();
    FriendDetialEntity fde;
    FragmentManager fragmentManager;
    Fragment xinLvFgt, xueYaFgt, buShuFgt, shuiMianFgt, bmiFgt, tiWenFgt, xueTangFgt, laboratoryFgt;

    @Override
    public int getLayoutId() {
        return R.layout.aty_myhealthydata;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Content.myHealthFlag = 1;
        cjsTvt.setText(getResources().getString(R.string.left_myHealthyData));
        ivBack.setVisibility(View.VISIBLE);
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_heartRate)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_bloodPressure)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_steps)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_sleep)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_bmi)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_temperature)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_bloodSuger)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_testSheet)));
//        testItems.add(new DialogMenuItem("尿常规"));
//        testItems.add(new DialogMenuItem("尿蛋白定量"));
//        testItems.add(new DialogMenuItem("免疫球蛋白"));
//        testItems.add(new DialogMenuItem("肾功能"));
//        testItems.add(new DialogMenuItem("肝功能"));
//        testItems.add(new DialogMenuItem("电解质"));
//        testItems.add(new DialogMenuItem("血常规"));

//        testItems.add(new DialogMenuItem("凝血"));
//        testItems.add(new DialogMenuItem("病毒检测"));
//        testItems.add(new DialogMenuItem("抗体系列"));
//        testItems.add(new DialogMenuItem("乙肝五项"));
//        testItems.add(new DialogMenuItem("肾脏彩超"));
        recycleAdapter = new MyRecyclerAdapter(this, mDatas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        friddRlv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        friddRlv.setAdapter(recycleAdapter);
        friddRlv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        friddRlv.addItemDecoration(dividerItemDecoration);

        //设置分隔线
//        friddRlv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        friddRlv.addItemDecoration(new RecycleViewDivider(
//                this, LinearLayoutManager.VERTICAL,10, 0xffeaeaea));
        //设置增加或删除条目的动画
        friddRlv.setItemAnimator(new DefaultItemAnimator());

        fragmentManager = getSupportFragmentManager();
        showFragment("xinLvFgt");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Content.myHealthFlag = 0;
    }

    @Override
    public void requestData() {
        /**
         * 个人详情
         */
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(IMUrl.class).getfriendsDetail(UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret(),
                UserManager.getUserInfo().getUserId(),
                userId, LanguageUtil.judgeLanguage()), 0);
    }

    /**
     * 解决Fragment切换溢出问题
     */
    public void showFragment(String index) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        hideFragment(ft);
        switch (index) {

            case "xinLvFgt":
//                ll_main.setBackgroundColor(0xffff6666);
//                ll_main.setFitsSystemWindows(true);
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                setWrapHeight();
                if (xinLvFgt == null) {
                    xinLvFgt = new XinLvFgt();
                    xinLvFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, xinLvFgt);
                } else {
                    ft.show(xinLvFgt);
                }
                break;
            case "xueYaFgt":
                setWrapHeight();
                if (xueYaFgt == null) {
                    xueYaFgt = new XueYaFgt();
                    xueYaFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, xueYaFgt);
                } else {
                    ft.show(xueYaFgt);
                }

                break;

            case "buShuFgt":
                setWrapHeight();
                if (buShuFgt == null) {
                    buShuFgt = new BuShuNewFgt();
                    buShuFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, buShuFgt);
                } else {
                    ft.show(buShuFgt);
                }

                break;
            case "shuiMianFgt":
                setWrapHeight();
                if (shuiMianFgt == null) {
                    shuiMianFgt = new ShuiMianNewFgt();
                    shuiMianFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, shuiMianFgt);
                } else {
                    ft.show(shuiMianFgt);
                }

                break;
            case "bmiFgt":
                if (bmiFgt == null) {
                    bmiFgt = new BmiNewFgt();
                    bmiFgt.setUserVisibleHint(true);
                    setWrapHeight();
                    ft.add(R.id.fragment_content, bmiFgt);
                } else {
                    setWrapHeight();
                    ft.show(bmiFgt);
                }

                break;
            case "tiWenFgt":
                if (tiWenFgt == null) {
                    tiWenFgt = new TiWenFgt();
                    tiWenFgt.setUserVisibleHint(true);
                    setWrapHeight();
                    ft.add(R.id.fragment_content, tiWenFgt);
                } else {
                    setWrapHeight();
                    ft.show(tiWenFgt);
                }

                break;

            case "xueTangFgt":
                if (xueTangFgt == null) {
                    xueTangFgt = new XueTangFgt();
                    xueTangFgt.setUserVisibleHint(true);
                    setWrapHeight();
                    ft.add(R.id.fragment_content, xueTangFgt);
                } else {
                    setWrapHeight();
                    ft.show(xueTangFgt);
                }

                break;
            case "laboratoryFgt":
                if (laboratoryFgt == null) {
                    laboratoryFgt = new LaboratoryFgt();
                    laboratoryFgt.setUserVisibleHint(true);
                    setLabHeight();
                    ft.add(R.id.fragment_content, laboratoryFgt);
                } else {
                    setLabHeight();
                    ft.show(laboratoryFgt);
                }
                break;
        }

        ft.commit();
    }

    private void setWrapHeight() {
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) fragmentContent.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;// 当控件的高强制设成50象素
        fragmentContent.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
    }

    private void setLabHeight() {
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) fragmentContent.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = DensityUtils.dp2px(50+45*10+200);// 当控件的高强制设成50象素
//        linearParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;// 当控件的高强制设成50象素
        fragmentContent.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
    }

    public void hideFragment(FragmentTransaction ft) {
        //如果不为空，就先隐藏起来
        if (xinLvFgt != null) {
            ft.hide(xinLvFgt);
        }
        if (xueYaFgt != null) {
            ft.hide(xueYaFgt);
        }

        if (buShuFgt != null) {
            ft.hide(buShuFgt);
        }
        if (shuiMianFgt != null) {
            ft.hide(shuiMianFgt);
        }
        if (bmiFgt != null) {
            ft.hide(bmiFgt);
        }
        if (tiWenFgt != null) {
            ft.hide(tiWenFgt);
        }

        if (xueTangFgt != null) {
            ft.hide(xueTangFgt);
        }
        if (laboratoryFgt != null) {
            ft.hide(laboratoryFgt);
        }


    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 0:
                fde = JSON.parseObject(result, FriendDetialEntity.class);
                Logger.e("yichang", result);
//              shezhiSJ(fde);
                //设置异常数据
                try {
                    shezhiYCSJ(fde);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                setXinLvZXT(fde);
                break;
        }
    }

    /**
     * 设置异常数据
     *
     * @param
     */
    private void shezhiYCSJ(FriendDetialEntity fde) {

        String str = fde.getQuestionDatas();
        String[] str1 = str.split("\\|");
        for (int i = 0; i < str1.length; i++) {
            if (str1[i] != null) {
                String[] str2 = str1[i].split(",");
                YichangDataEntity yd = new YichangDataEntity();
                yd.setName(str2[0]);
                yd.setShuzhi(str2[1]);
                yd.setRiqi(str2[2]);
                mDatas.add(yd);
            }

        }
        recycleAdapter.notifyDataSetChanged();
    }


    private void NormalListDialogCustomAttr() {
        final NormalListDialog dialog = new NormalListDialog(this, testItems);
        dialog.title(getResources().getString(R.string.qingxuanze))//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#00c5cd"))//
                .itemPressColor(Color.parseColor("#00c5cd"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show(R.style.myDialogAnim);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

//                showToast(testItems.get(position).getmOperName());
                if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_bloodPressure))) {
                    friddTvXm.setText(getResources().getString(R.string.home_bloodPressure));
                    showFragment("xueYaFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_heartRate))) {
                    friddTvXm.setText(getResources().getString(R.string.home_heartRate));
                    showFragment("xinLvFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_steps))) {
                    friddTvXm.setText(getResources().getString(R.string.home_steps));
                    showFragment("buShuFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_sleep))) {
                    friddTvXm.setText(getResources().getString(R.string.home_sleep));
                    showFragment("shuiMianFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_bmi))) {
                    friddTvXm.setText(getResources().getString(R.string.home_bmi));
                    showFragment("bmiFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_temperature))) {
                    friddTvXm.setText(getResources().getString(R.string.home_temperature));
                    showFragment("tiWenFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_bloodSuger))) {
                    friddTvXm.setText(getResources().getString(R.string.home_bloodSuger));
                    showFragment("xueTangFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_testSheet))) {
                    friddTvXm.setText(getResources().getString(R.string.home_testSheet));
                    showFragment("laboratoryFgt");
                }
                dialog.dismiss();
            }
        });
    }


    @OnClick({R.id.cjs_rlb, R.id.fridd_rl_xm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.fridd_rl_xm:
                NormalListDialogCustomAttr();
                break;

        }
    }
}
