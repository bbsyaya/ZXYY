package com.zhixinyisheng.user.ui.data.xueya;

import android.Manifest;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.XueYaListAdapter;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.XueYaZXT;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.BLEActivity;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.PermissionsUtil;
import com.zhixinyisheng.user.view.MyViewBD;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 血压
 * Created by 焕焕 on 2016/10/30.
 */
public class XueYaFgt extends BaseFgt {
    @Bind(R.id.iv_rili)
    ImageView ivRili;
    @Bind(R.id.iv_zice)
    ImageView ivZice;
    @Bind(R.id.iv_shouhuan)
    ImageView ivShouhuan;
    @Bind(R.id.iv_shouxie)
    ImageView ivShouxie;
    @Bind(R.id.fl_xinlv)
    FrameLayout flXinlv;
    @Bind(R.id.xl_tv1)
    TextView xlTv1;
    @Bind(R.id.xl_rl1)
    RelativeLayout xlRl1;
    @Bind(R.id.xl_mv)
    MyViewBD mv;
    @Bind(R.id.xy_ll1)
    LinearLayout xyLl1;
    @Bind(R.id.xl_purflv)
    ListView xlPurflv;
    @Bind(R.id.ll_xueyace)
    LinearLayout ll_xueyace;
    List<String> Xlabel = new ArrayList<String>(),
            Ylabel = new ArrayList<String>(),
            data_low = new ArrayList<String>(),
            data_low1 = new ArrayList<String>(),
            data_high = new ArrayList<String>(),
            data_high1 = new ArrayList<String>();

    List<XueYaZXT.ListBean> listBeen = new ArrayList<XueYaZXT.ListBean>();
    XueYaListAdapter xueYaListAdapter;
    //    @Bind(R.id.sv)
//    ScrollView sv;
    @Bind(R.id.tv_bottom_xueya)
    TextView tv_bottom_xueya;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_xueya;
    }

    @Override
    public void initData() {
        Content.XVORXY = "1";
        Content.blood++;
        Ylabel.clear();
        for (int i = 30; i <= 250; i = i + 20) {
            Ylabel.add(i + "");
        }
        if (Content.myHealthFlag == 1 || !UserManager.getUserInfo().getUserId().equals(userId)) {
            try {
                ll_xueyace.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueYaZXT(
                userId, time, phone, secret), 0);
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Logger.e("xueya onUserVisible", "xueya onUserVisible");
//        showLoadingDialog(null);
        if (Content.myHealthFlag != 1 && UserManager.getUserInfo().getUserId().equals(userId)) {
            doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueYaZXT(
                    userId, time, phone, secret), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        tv_bottom_xueya.setHeight(MainActivity.di_height-5);

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
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
                doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueYaZXT(
                        userId, Content.DATA, phone, secret), 1);
                break;
            case 1:
                XueYaZXT xueYaData = JSON.parseObject(result, XueYaZXT.class);
                listBeen.clear();
                listBeen.addAll(xueYaData.getList());
                xueYaListAdapter = new XueYaListAdapter(getActivity(), listBeen, R.layout.item_xueya);
                xlPurflv.setAdapter(xueYaListAdapter);
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

    @Override
    public void requestData() {

    }


    @OnClick({R.id.iv_rili, R.id.iv_zice, R.id.iv_shouhuan, R.id.iv_shouxie})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_rili:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).bloodFindByPid(
                        userId, phone, secret), 6);

                break;
            case R.id.iv_zice:
                if (PermissionsUtil.is6()) {
                    PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Content.XVORXY = "1";
                            startActivity(XueYaJcAty.class, null);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            showToast(getString(R.string.ninmeiyoukaiqixiangjiquanxian));
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }, Manifest.permission.CAMERA);
                } else {
                    Content.XVORXY = "1";
//                    startActivity(XinLvJcActivity.class, null);
                    startActivity(XueYaJcAty.class, null);
                }


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

}
