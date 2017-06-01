package com.zhixinyisheng.user.ui.data.laboratory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.and.yzy.frame.application.Constant;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.data.bingdujiance.BingDuJianCeLabFgt;
import com.zhixinyisheng.user.ui.data.dianjiezhi.DianJieZhiLabFgt;
import com.zhixinyisheng.user.ui.data.gangongneng.GanGongNengLabFgt;
import com.zhixinyisheng.user.ui.data.kantixilie.KangTiXiLieLabFgt;
import com.zhixinyisheng.user.ui.data.mianyiqiudanbai.MianYiQiuDanBaiLabFgt;
import com.zhixinyisheng.user.ui.data.niaochuanggui.NiaoChangGuiLabFgt;
import com.zhixinyisheng.user.ui.data.niaodanbai.NiaoDanBaiLabFgt;
import com.zhixinyisheng.user.ui.data.ningxuejiance.NingXueLabFgt;
import com.zhixinyisheng.user.ui.data.shengongneng.ShenGongNengLabFgt;
import com.zhixinyisheng.user.ui.data.shengzangcaichao.ShenZangCaiChaoLabFgt;
import com.zhixinyisheng.user.ui.data.xuechanggui.XueChangGuiLabFgt;
import com.zhixinyisheng.user.ui.data.yiganwuxiang.YiGanWuXiangLabFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.lab.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 化验单手写
 * Created by 焕焕 on 2017/2/22.
 */
public class LabHandwritingAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.tv_hand_time)
    TextView tvHandTime;
    //对话框集合
    private ArrayList<DialogMenuItem> testItems = new ArrayList<DialogMenuItem>();
    private FragmentManager fragmentManager;
    private Fragment  niaoChangGuiFgt, niaodanbaiFgt, mianyiqiudanbai,
            shengongnengfgt, gangongnengfgt, dianjiehifgt, xuechangguiFgt, ningxueFgt, bingdujianceFgt,
            kangtixilieFgt, yiganwuxiangFgt, shenzangcaichaoFgt;
    private CustomDatePicker customDatePicker;

    @Override
    public int getLayoutId() {
        return R.layout.aty_lab_handwriting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setData();
        initDatePicker();

    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvHandTime.setText(now.split(" ")[0]);
        Content.time_lab = now.split(" ")[0];
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvHandTime.setText(time.split(" ")[0]);
                Content.time_lab = time.split(" ")[0];
                //TODO 发广播到fragment
                sendReceiver();
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    private void sendReceiver() {
        Intent intent = new Intent(Constant.LAB_NCG_TIME);
        sendBroadcast(intent);
        Intent intent1 = new Intent(Constant.LAB_XCG_TIME);
        sendBroadcast(intent1);
        Intent intent2 = new Intent(Constant.LAB_NDB_TIME);
        sendBroadcast(intent2);
        Intent intent3 = new Intent(Constant.LAB_MYQDB_TIME);
        sendBroadcast(intent3);
        Intent intent4 = new Intent(Constant.LAB_GGN_TIME);
        sendBroadcast(intent4);
        Intent intent5 = new Intent(Constant.LAB_SZCC_TIME);
        sendBroadcast(intent5);
        Intent intent6 = new Intent(Constant.LAB_SZCC_TIME);
        sendBroadcast(intent6);
        Intent intent7 = new Intent(Constant.LAB_DJZ_TIME);
        sendBroadcast(intent7);
        Intent intent8 = new Intent(Constant.LAB_YGWX_TIME);
        sendBroadcast(intent8);
        Intent intent9 = new Intent(Constant.LAB_KTXL_TIME);
        sendBroadcast(intent9);
        Intent intent10 = new Intent(Constant.LAB_NX_TIME);
        sendBroadcast(intent10);
        Intent intent11 = new Intent(Constant.LAB_BDJC_TIME);
        sendBroadcast(intent11);
    }

    private void setData() {
        cjsTvt.setText(R.string.niaochanggui);
        testItems.add(new DialogMenuItem(getString(R.string.niaochanggui)));
        testItems.add(new DialogMenuItem(getString(R.string.niaodanbaidingliang)));
        testItems.add(new DialogMenuItem(getString(R.string.mianyiqiudanbai)));
        testItems.add(new DialogMenuItem(getString(R.string.shengongneng)));
        testItems.add(new DialogMenuItem(getString(R.string.gangongneng)));
        testItems.add(new DialogMenuItem(getString(R.string.dianjiezhi)));
        testItems.add(new DialogMenuItem(getString(R.string.xuechanggui)));
        testItems.add(new DialogMenuItem(getString(R.string.ningxue)));
        testItems.add(new DialogMenuItem(getString(R.string.bingdujiance)));
        testItems.add(new DialogMenuItem(getString(R.string.kangtixilie)));
        testItems.add(new DialogMenuItem(getString(R.string.yiganwuxiang)));
        testItems.add(new DialogMenuItem(getString(R.string.shenzangcaichao)));
        fragmentManager = getSupportFragmentManager();
        showFragment("niaoChangGuiFgt");
    }

    private void NormalListDialogCustomAttr() {
        final NormalListDialog dialog = new NormalListDialog(this, testItems);
        dialog.title(getString(R.string.qingxuanze))//
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
                Logger.e("23nini",testItems.get(position).getmOperName());
                Logger.e("23ninisssss",getString(R.string.niaodanbaidingliang));
                 if (testItems.get(position).getmOperName().equals(getString(R.string.niaochanggui))) {
                    cjsTvt.setText(R.string.niaochanggui);
                    showFragment("niaoChangGuiFgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.niaodanbaidingliang))) {
                    cjsTvt.setText(R.string.niaodanbaidingliang);
                    showFragment("niaodanbaiFgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.mianyiqiudanbai))) {
                    cjsTvt.setText(R.string.mianyiqiudanbai);
                    showFragment("mianyiqiudanbai");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.shengongneng))) {
                    cjsTvt.setText(R.string.shengongneng);
                    showFragment("shengongnengfgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.gangongneng))) {
                    cjsTvt.setText(R.string.gangongneng);
                    showFragment("gangongnengfgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.dianjiezhi))) {
                    cjsTvt.setText(R.string.dianjiezhi);
                    showFragment("dianjiehifgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.xuechanggui))) {
                    cjsTvt.setText(R.string.xuechanggui);
                    showFragment("xuechangguiFgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.ningxue))) {
                    cjsTvt.setText(R.string.ningxue);
                    showFragment("ningxueFgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.bingdujiance))) {
                    cjsTvt.setText(R.string.bingdujiance);
                    showFragment("bingdujianceFgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.kangtixilie))) {
                    cjsTvt.setText(R.string.kangtixilie);
                    showFragment("kangtixilieFgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.yiganwuxiang))) {
                    cjsTvt.setText(R.string.yiganwuxiang);
                    showFragment("yiganwuxiangFgt");
                } else if (testItems.get(position).getmOperName().equals(getString(R.string.shenzangcaichao))) {
                    cjsTvt.setText(R.string.shenzangcaichao);
                    showFragment("shenzangcaichaoFgt");
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 解决Fragment切换溢出问题
     */
    public void showFragment(String index) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragment(ft);

        switch (index) {



            case "niaoChangGuiFgt"://尿常规
                if (niaoChangGuiFgt == null) {
                    niaoChangGuiFgt = new NiaoChangGuiLabFgt();
                    niaoChangGuiFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, niaoChangGuiFgt);
                } else {
                    ft.show(niaoChangGuiFgt);
                }

                break;
            case "niaodanbaiFgt"://尿蛋白定量
                if (niaodanbaiFgt == null) {
                    niaodanbaiFgt = new NiaoDanBaiLabFgt();
                    niaodanbaiFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, niaodanbaiFgt);
                } else {
                    ft.show(niaodanbaiFgt);
                }

                break;
            case "mianyiqiudanbai"://免疫球蛋白
                if (mianyiqiudanbai == null) {
                    mianyiqiudanbai = new MianYiQiuDanBaiLabFgt();
                    mianyiqiudanbai.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, mianyiqiudanbai);
                } else {
                    ft.show(mianyiqiudanbai);
                }

                break;
            case "shengongnengfgt":
                if (shengongnengfgt == null) {
                    shengongnengfgt = new ShenGongNengLabFgt();
                    shengongnengfgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, shengongnengfgt);
                } else {
                    ft.show(shengongnengfgt);
                }

                break;
            case "gangongnengfgt"://肝功能
                if (gangongnengfgt == null) {
                    gangongnengfgt = new GanGongNengLabFgt();
                    gangongnengfgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, gangongnengfgt);
                } else {
                    ft.show(gangongnengfgt);
                }

                break;
            case "dianjiehifgt"://电解质
                if (dianjiehifgt == null) {
                    dianjiehifgt = new DianJieZhiLabFgt();
                    dianjiehifgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, dianjiehifgt);
                } else {
                    ft.show(dianjiehifgt);
                }

                break;
            case "xuechangguiFgt"://血常规
                if (xuechangguiFgt == null) {
                    xuechangguiFgt = new XueChangGuiLabFgt();
                    xuechangguiFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, xuechangguiFgt);
                } else {
                    ft.show(xuechangguiFgt);
                }

                break;

            case "ningxueFgt"://凝血
                if (ningxueFgt == null) {
                    ningxueFgt = new NingXueLabFgt();
                    ningxueFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, ningxueFgt);
                } else {
                    ft.show(ningxueFgt);
                }

                break;
            case "bingdujianceFgt"://病毒检测
                if (bingdujianceFgt == null) {
                    bingdujianceFgt = new BingDuJianCeLabFgt();
                    bingdujianceFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, bingdujianceFgt);
                } else {
                    ft.show(bingdujianceFgt);
                }

                break;
            case "kangtixilieFgt"://抗体系列
                if (kangtixilieFgt == null) {
                    kangtixilieFgt = new KangTiXiLieLabFgt();
                    kangtixilieFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, kangtixilieFgt);
                } else {
                    ft.show(kangtixilieFgt);
                }

                break;
            case "yiganwuxiangFgt":
                if (yiganwuxiangFgt == null) {
                    yiganwuxiangFgt = new YiGanWuXiangLabFgt();
                    yiganwuxiangFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, yiganwuxiangFgt);
                } else {
                    ft.show(yiganwuxiangFgt);
                }

                break;
            case "shenzangcaichaoFgt"://肾脏彩超
                if (shenzangcaichaoFgt == null) {
                    shenzangcaichaoFgt = new ShenZangCaiChaoLabFgt();
                    shenzangcaichaoFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, shenzangcaichaoFgt);
                } else {
                    ft.show(shenzangcaichaoFgt);
                }

                break;

        }

        ft.commit();
    }

    public void hideFragment(FragmentTransaction ft) {
        //如果不为空，就先隐藏起来

        if (niaoChangGuiFgt != null) {
            ft.hide(niaoChangGuiFgt);
        }
        if (niaodanbaiFgt != null) {
            ft.hide(niaodanbaiFgt);
        }
        if (mianyiqiudanbai != null) {
            ft.hide(mianyiqiudanbai);
        }
        if (shengongnengfgt != null) {
            ft.hide(shengongnengfgt);
        }
        if (gangongnengfgt != null) {
            ft.hide(gangongnengfgt);
        }
        if (dianjiehifgt != null) {
            ft.hide(dianjiehifgt);
        }
        if (xuechangguiFgt != null) {
            ft.hide(xuechangguiFgt);
        }

        if (ningxueFgt != null) {
            ft.hide(ningxueFgt);
        }
        if (bingdujianceFgt != null) {
            ft.hide(bingdujianceFgt);
        }
        if (kangtixilieFgt != null) {
            ft.hide(kangtixilieFgt);
        }
        if (yiganwuxiangFgt != null) {
            ft.hide(yiganwuxiangFgt);
        }
        if (shenzangcaichaoFgt != null) {
            ft.hide(shenzangcaichaoFgt);
        }

    }

    @OnClick({R.id.cjs_rlb, R.id.cjs_tvt, R.id.title_btn, R.id.rl_lab_time})
    public void onClick(View view) {
        //TODO 点击时间
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.cjs_tvt:
                NormalListDialogCustomAttr();
                break;
            case R.id.title_btn:
                LaboratoryFgt.isLabLoading= true;
                //发广播到fragment
                Intent intent = new Intent(Constant.LAB_NCG_COMMIT);
                sendBroadcast(intent);
                Intent intent1 = new Intent(Constant.LAB_XCG_COMMIT);
                sendBroadcast(intent1);
                Intent intent2 = new Intent(Constant.LAB_NDB_COMMIT);
                sendBroadcast(intent2);
                Intent intent3 = new Intent(Constant.LAB_MYQDB_COMMIT);
                sendBroadcast(intent3);
                Intent intent4 = new Intent(Constant.LAB_GGN_COMMIT);
                sendBroadcast(intent4);
                Intent intent5 = new Intent(Constant.LAB_SGN_COMMIT);
                sendBroadcast(intent5);
                Intent intent6 = new Intent(Constant.LAB_SZCC_COMMIT);
                sendBroadcast(intent6);
                Intent intent7 = new Intent(Constant.LAB_DJZ_COMMIT);
                sendBroadcast(intent7);
                Intent intent8 = new Intent(Constant.LAB_YGWX_COMMIT);
                sendBroadcast(intent8);
                Intent intent9 = new Intent(Constant.LAB_KTXL_COMMIT);
                sendBroadcast(intent9);
                Intent intent10 = new Intent(Constant.LAB_NX_COMMIT);
                sendBroadcast(intent10);
                Intent intent11 = new Intent(Constant.LAB_BDJC_COMMIT);
                sendBroadcast(intent11);
                break;
            case R.id.rl_lab_time:
                customDatePicker.show(tvHandTime.getText().toString());
                break;
        }
    }
}
