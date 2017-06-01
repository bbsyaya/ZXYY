package com.zhixinyisheng.user.ui.data.xueya;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.XueYaListAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.XueYaZXT;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.interfaces.OnLoadMoreListener;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
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

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_BLOOD_DATA;

/**
 * 血压
 * Created by 焕焕 on 2016/10/30.
 */
public class FloodPressureFragment extends BaseFgt implements View.OnClickListener, OnLoadMoreListener {
    private static final int RP_CAMERA = 1;
    TextView xlTv1;
    RelativeLayout xlRl1;
    MyViewBD mv;

    @Bind(R.id.xl_purflv)
    PullToListView xlPurflv;

    List<String> Xlabel = new ArrayList<String>(),
            Ylabel = new ArrayList<String>(),
            data_low = new ArrayList<String>(),
            data_low1 = new ArrayList<String>(),
            data_high = new ArrayList<String>(),
            data_high1 = new ArrayList<String>();

    List<XueYaZXT.ListBean> listBeen = new ArrayList<XueYaZXT.ListBean>();
    XueYaListAdapter xueYaListAdapter;
    private int mPage = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_flood_pressure;
    }

    private void initAdapter() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.head_blood_pressure, null, false);
        xlTv1 = (TextView) view.findViewById(R.id.xl_tv1);
        xlRl1 = (RelativeLayout) view.findViewById(R.id.xl_rl1);
        mv = (MyViewBD) view.findViewById(R.id.xl_mv);
        xueYaListAdapter = new XueYaListAdapter(getActivity(), listBeen, R.layout.item_xueya);
        xlPurflv.addHeaderView(view);
        xlPurflv.setShowFoot(false);
        View viewFooter = LayoutInflater.from(getActivity()).inflate(R.layout.foot_main, null, false);
        xlPurflv.addFooterView(viewFooter);
        xlPurflv.setAdapter(xueYaListAdapter);
        xlPurflv.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        initAdapter();
        Content.XVORXY = "1";
        Content.blood++;
        Ylabel.clear();
        for (int i = 30; i <= 250; i = i + 20) {
            Ylabel.add(i + "");
        }
        setZXTData();
        setListData();
    }

    private void setZXTData() {
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueYaZXT(
                userId, time, phone, secret), 0);
    }

    private void setListData() {
        doHttp(RetrofitUtils.createApi(DataUrl.class).BloodAllList(
                userId, 7, mPage, phone, secret), HttpIdentifier.BLOOD_LIST);
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (Content.myHealthFlag != 1 && UserManager.getUserInfo().getUserId().equals(userId)) {
            if (IS_CHANGE_BLOOD_DATA) {
                listBeen.clear();
                xueYaListAdapter.removeAll();
                mPage = 1;
                setZXTData();
                setListData();
                IS_CHANGE_BLOOD_DATA = false;
            }
        }
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.BLOOD_LIST:
                XueYaZXT xueYaData = JSON.parseObject(result, XueYaZXT.class);
                listBeen.addAll(xueYaData.getList());
                xueYaListAdapter.setDatas(listBeen);
                break;
            case 0:
//                Logger.e("xueya",result);
                data_high.clear();
                data_high1.clear();
                data_low.clear();
                data_low1.clear();
                Xlabel.clear();
                XueYaZXT xueYaZXT = JSON.parseObject(result, XueYaZXT.class);
                if (xueYaZXT.getSize() == 0) {
//                    showToast(Content.DATA + "之前暂无数据!");
                }
                for (int i = 0; i < xueYaZXT.getList().size(); i++) {
                    String strt = xueYaZXT.getList().get(i).getBYTIME();
                    Xlabel.add(strt.substring(5, 7) + "."
                            + strt.substring(8, 10));
                    String str_high = (int) xueYaZXT.getList().get(i).getHIGHPRESSURE() + "";
                    if (Double.valueOf(str_high) < 30) {
                        data_high.add(30 + "");
                    } else {
                        data_high.add(str_high);
                    }
                    data_high1.add(str_high);
                    String str_low = (int) xueYaZXT.getList().get(i).getLOWPRESSURE() + "";
                    // 如果低于最小值最低画到横轴上
                    if (Double.valueOf(str_low) < 30) {
                        data_low.add(30 + "");
                    } else {
                        data_low.add(str_low);
                    }
                    data_low1.add(str_low);
                }
                mv.drawZhexian(Xlabel, Ylabel, data_low, data_low1, data_high,
                        data_high1, 0);
//                doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueYaZXT(
//                        userId, Content.DATA, phone, secret), 1);
                break;
            case 1:
//                XueYaZXT xueYaData = JSON.parseObject(result, XueYaZXT.class);
//                listBeen.clear();
//                listBeen.addAll(xueYaData.getList());
//                xueYaListAdapter.setDatas(listBeen);
//                sv.scrollTo(10,10);
                break;
            case 6:
                FindByPidEntity findByPidEntity = JSON.parseObject(result, FindByPidEntity.class);
                new CalenderDialogTest(getActivity(), findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
//                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueYaZXT(
                                userId, Content.DATA, phone, secret), 0);
                    }
                };
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (what == 6) {
            new CalenderDialogTest(getActivity(), null) {
                @Override
                public void getZXTData() {

                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueYaZXT(
                            userId, Content.DATA, phone, secret), 0);
                }
            };
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (RP_CAMERA == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Content.XVORXY = "1";
                startActivity(XueYaJcAty.class, null);
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
                doHttp(RetrofitUtils.createApi(FindByPid.class).bloodFindByPid(
                        userId, phone, secret), 6);

                break;
            case R.id.iv_zice:
                if (toCheckPermission()) {
                    Content.XVORXY = "1";
                    startActivity(XueYaJcAty.class, null);
                }

//                if (PermissionsUtil.is6()) {
//                    PermissionsUtil.checkPermissionBy6(new PermissionListener() {
//                        @Override
//                        public void onPermissionGranted(PermissionGrantedResponse response) {
//                            Content.XVORXY = "1";
//                            startActivity(XueYaJcAty.class, null);
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
//                    Content.XVORXY = "1";
////                    startActivity(XinLvJcActivity.class, null);
//                    startActivity(XueYaJcAty.class, null);
//                }


                break;
            case R.id.iv_shouhuan:
                Content.XVORXY = "1";
                if (TestService.mDevice == null) {
                    TestService.no_shsz = 4;
                    startActivity(BLEActivity.class, null);
                } else {
                    startActivity(XueYaSHAty.class, null);
                }
                break;
            case R.id.iv_shouxie:
                Content.XVORXY = "1";
                startActivity(XueYaHandwritingAty.class, null);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        mPage++;
        setListData();
    }
}
