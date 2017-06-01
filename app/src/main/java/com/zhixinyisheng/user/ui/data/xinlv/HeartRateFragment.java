package com.zhixinyisheng.user.ui.data.xinlv;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.XinLvListAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.XinLvZXT;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.interfaces.OnLoadMoreListener;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.ui.messure.XinLvJcActivity;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.MyViewBD;
import com.zhixinyisheng.user.view.PullToListView;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_HEART_DATA;

/**
 * 心率
 * A simple {@link Fragment} subclass.
 */
public class HeartRateFragment extends BaseFgt implements View.OnClickListener, OnLoadMoreListener {
    private static final int RP_CAMERA = 1;
    @Bind(R.id.xl_purflv)
    PullToListView xlPurflv;
    TextView xlTv1;
    RelativeLayout xlRl1;
    MyViewBD mv;
    LinearLayout xlLl1;
    List<String> Xlabel = new ArrayList<String>(),
            data = new ArrayList<String>(), data1 = new ArrayList<String>(),
            Ylabel = new ArrayList<String>();
    XinLvListAdapter xinLvListAdapter;
    List<XinLvZXT.ListBean> listBeen = new ArrayList<XinLvZXT.ListBean>();

    private int mPage = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_heart_rate;
    }

    private void initAdapter() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.head_heart_rate, null, false);
        xlTv1 = (TextView) view.findViewById(R.id.xl_tv1);
        xlRl1 = (RelativeLayout) view.findViewById(R.id.xl_rl1);
        xlLl1 = (LinearLayout) view.findViewById(R.id.xl_ll1);
        mv = (MyViewBD) view.findViewById(R.id.xl_mv);
        xlPurflv.setShowFoot(false);
        xinLvListAdapter = new XinLvListAdapter(getActivity(), listBeen, R.layout.item_xinlv);
        xlPurflv.addHeaderView(view);
        View viewFooter = LayoutInflater.from(getActivity()).inflate(R.layout.foot_main, null, false);
        xlPurflv.addFooterView(viewFooter);
        xlPurflv.setAdapter(xinLvListAdapter);
        xlPurflv.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        initAdapter();
        Content.XVORXY = "0";
        Content.heartrate++;
        Ylabel.clear();
        for (int i = 40; i <= 150; i = i + 10) {
            Ylabel.add(i + "");
        }
        setZXTData();//设置折线图的数据
        setListData();
    }

    /**
     * 设置列表数据
     */
    private void setListData() {
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXinLvData(
                userId, 7, mPage, phone, secret), HttpIdentifier.HEARTRATE_LIST);
    }

    private void setZXTData() {
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXinLvZXT(
                userId, time, phone, secret), 0);
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (Content.myHealthFlag != 1 && UserManager.getUserInfo().getUserId().equals(userId)) {
            if (IS_CHANGE_HEART_DATA) {
                listBeen.clear();
                xinLvListAdapter.removeAll();
                mPage=1;
                setZXTData();
                setListData();
                IS_CHANGE_HEART_DATA = false;
            }
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (what == 6) {
            new CalenderDialogTest(getActivity(), null) {
                @Override
                public void getZXTData() {
                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkXinLvZXT(
                            userId, Content.DATA, phone, secret), 0);
                }
            };
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.HEARTRATE_LIST:
                XinLvZXT xinLvData = JSON.parseObject(result, XinLvZXT.class);
                listBeen.addAll(xinLvData.getList());
                xinLvListAdapter.setDatas(listBeen);
                break;
            case 0:
                Xlabel.clear();
                data.clear();
                data1.clear();
                XinLvZXT xinLvZXT = JSON.parseObject(result, XinLvZXT.class);
                for (int i = 0; i < xinLvZXT.getList().size(); i++) {
                    String strt = xinLvZXT.getList().get(i).getBYTIME();
                    int strsj = xinLvZXT.getList().get(i).getNum();
                    Xlabel.add(strt.substring(5, 7) + "."
                            + strt.substring(8, 10));
                    if (Double.valueOf(strsj) < 40) {
                        data.add("40");
                    } else {
                        data.add(strsj + "");
                    }
                    data1.add(strsj + "");
                }
                mv.drawZhexian(Xlabel, Ylabel, data, data1, 1);
//                doHttp(RetrofitUtils.createApi(DataUrl.class).checkXinLvZXT(
//                                userId, Content.DATA, phone, secret), 1);

                break;
            case 1:
//                XinLvZXT xinLvData = JSON.parseObject(result, XinLvZXT.class);
//                listBeen.clear();
//                listBeen.addAll(xinLvData.getList());
//
//                xinLvListAdapter.setDatas(listBeen);
//                sv.scrollTo(0,0);
                break;
            case 6:
                FindByPidEntity findByPidEntity = JSON.parseObject(result, FindByPidEntity.class);
                new CalenderDialogTest(getActivity(), findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
                        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXinLvZXT(
                                userId, Content.DATA, phone, secret), 0);
                    }
                };
                break;
        }

    }

    /**
     * 检查权限（运行时权限）
     */
    private boolean toCheckPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != result) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RP_CAMERA);
            return false;
        }
        return true;
    }

    private void showDialog(boolean isReTry) {
        AlertDialog.Builder builder = new AlertDialog
                .Builder(getActivity())
                .setTitle("SD卡读写权限缺少")
                .setMessage("应用的基础数据本地初始化时，需要SD卡的读写权限，否则将无法正常使用本应用。\n 可通过'设置' -> '应用程序'->'权限设置'，重新设置应用权限。")
                .setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        finish();
                    }
                });
        if (isReTry) {
            builder.setPositiveButton("重新授权", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    toCheckPermission();
                }
            });
        }
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (RP_CAMERA == requestCode) {
            Logger.e("grantResults",grantResults[0]+"@@@@@");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Content.XVORXY = "0";
                startActivity(XinLvJcActivity.class, null);
            } else {
                //判断用户是否勾选 不再询问的选项，未勾选可以 说明权限作用，重新授权。
                boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA);
                Logger.e("shouldShow",shouldShow+"@@@");
                if (shouldShow) {
                    showDialog(true);
                } else {
                    showDialog(false);
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_rili:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).heartrate(
                        userId, phone, secret), 6);
                break;
            case R.id.iv_zice://进入自测的界面
                if (toCheckPermission()) {
                    Content.XVORXY = "0";
                    startActivity(XinLvJcActivity.class, null);
                }


//                if (PermissionsUtil.is6()) {
//                    PermissionsUtil.checkPermissionBy6(new PermissionListener() {
//                        @Override
//                        public void onPermissionGranted(PermissionGrantedResponse response) {
//                            Content.XVORXY = "0";
//                            startActivity(XinLvJcActivity.class, null);
//                        }
//
//                        @Override
//                        public void onPermissionDenied(PermissionDeniedResponse response) {
//                            showToast(getString(R.string.ninmeiyoukaiqixiangjiquanxian));
//                        }
//
//                        @Override
//                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                            token.continuePermissionRequest();
//                        }
//                    }, Manifest.permission.CAMERA);
//                } else {
//                    Content.XVORXY = "0";
//                    startActivity(XinLvJcActivity.class, null);
//                }


                break;
            case R.id.iv_shouhuan://进入设置手环的界面
                Content.XVORXY = "0";
                if (TestService.mDevice == null) {
                    TestService.no_shsz = 3;
                    startActivity(BLEActivity.class, null);
                } else {
                    startActivity(XinLvSHAty.class, null);
                }
//                showFragment(FRAGMENT_TWO);
                break;
            case R.id.iv_shouxie://进入手写的界面
                Content.XVORXY = "0";
                startActivity(XinLvHandwritingAty.class, null);
//                showFragment(FRAGMENT_THREE);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        mPage++;
        setListData();
    }
}
