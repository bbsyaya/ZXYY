package com.zhixinyisheng.user.ui.sos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.SosEntity;
import com.zhixinyisheng.user.http.SosUrl;
import com.zhixinyisheng.user.jpush.MyBroadcastReceiver;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.sos.AMapUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 接受地图呼救界面
 * Created by 焕焕 on 2016/11/8.
 */
public class HelpReceivedAty extends BaseAty implements
        GeocodeSearch.OnGeocodeSearchListener, LocationSource
        , AMapLocationListener, RouteSearch.OnRouteSearchListener {

    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.map)
    MapView mMapView;
    private String address;//地址位置的信息
    private double lat_end, lon_end;//终点的经纬度
    private AMap aMap;//高德地图控制器对象,包括显示交通和定位层显示的配置
    private Marker regeoMarker;//覆盖物，在地图上进行绘制的点
    private OnLocationChangedListener mListener;
    private AMapLocationClient mapLocationClient;//声明AMapLocationClient类对象(定位客户端)
    private LatLonPoint latLonPoint = null;
    private AMapLocationClientOption mLoationOption;//声明mLocationOption对象(定位参数)
    private double lat, lon;//经纬度
    /**
     * 起点
     */
    private LatLonPoint mStartPoint;
    /**
     * 终点
     */
    private LatLonPoint mEndPoint;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    SosEntity sosEntity;
    private MyBroadcastReceiver mSMSBroadcastReceiver;//透传
    @Override
    public int getLayoutId() {
        return R.layout.aty_help;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showLoadingDialog(null);
        cjsTvt.setText(R.string.jinjihujiu);
        ivBack.setVisibility(View.VISIBLE);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        Intent intent = getIntent();
        sosEntity = (SosEntity) intent.getSerializableExtra("sosEntity");
//        Logger.e("helpid sos", sosEntity.getHelpId() + "");
        lat_end = Double.parseDouble(sosEntity.getLat());
        lon_end = Double.parseDouble(sosEntity.getLon());
        mEndPoint = new LatLonPoint(lat_end, lon_end);
        // 路线规划
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        setUpMap();

        mSMSBroadcastReceiver = new MyBroadcastReceiver();
        mSMSBroadcastReceiver.setOnReceivedMessageListener(new MyBroadcastReceiver.MessageListener() {
            @Override
            public void OnReceived(List<String> message) {
                Logger.e("取消透传来了",message.get(0));
                if (message.get(0).equals("sosEnd")){// 对方取消呼救

                }
            }
        });
    }

    /**
     * 设置一些amap属性，设置当前位置的配置
     */

    private void setUpMap() {
        // 设置位置的监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位并不可出发定位，默认是false
        aMap.setMyLocationEnabled(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO 广播
            sosEntity = (SosEntity) intent.getSerializableExtra("sosEntity");
            showCancelDialog();

        }
    };

    private void showCancelDialog() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(sosEntity.getName() + getString(R.string.yijingquxiaolehujiu))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        finish();

                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
        IntentFilter filter = new IntentFilter("sosaccept.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState
        // (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
//		deactivate();// 停止定位的方法

    }

    @Override
    public void requestData() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        // TODO 定位成功后的回调函数
        dismissLoadingDialog();
        if (mListener != null && aMapLocation != null) {
            // dismissDialog();
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                // 显示系统的小蓝点
                // mListener.onLocationChanged(aMapLocation);
                // 获取经度
                lon = aMapLocation.getLongitude();
                // 获取纬度
                lat = aMapLocation.getLatitude();
                // 获取地址
                address = aMapLocation.getAddress();
                Log.e("位置坐标", lon + "," + lat);
                // 设置当前地图显示为当前的位置(放大的级别)
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                        lat, lon), 15));
                // 添加覆盖物图标
                aMap.addMarker(new MarkerOptions().position(
                        new LatLng(lat, lon)).icon(
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.location_marker)));
                mStartPoint = new LatLonPoint(lat, lon);
                myShowDialog();//家属或医生接受求救dialog
                setfromandtoMarker();
                searchRouteResult(RouteSearch.DrivingDefault);
                mMapView.setVisibility(View.VISIBLE);

            } else {
                // 显示错误信息ErrCode是错误码，errinfo是错误的信息。
                final String errText = "" + aMapLocation.getErrorCode() + ":"
                        + aMapLocation.getErrorInfo();
                Logger.e("error 586", "586+++" + errText);
                // Toast.makeText(getApplicationContext(), errText,
                // Toast.LENGTH_SHORT).show();
            }

        }

    }

    /**
     * 家属或医生接受呼救dialog
     */
    private void myShowDialog() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(sosEntity.getHelpName() + getString(R.string.xiangninfalaihujiuwoxianzaichuyu))
                .setConfirmText(getString(R.string.mashangqianwang))
                .setCancelText(getString(R.string.xianzaimeikong))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(SosUrl.class).acceptlHelp(
                                userId, lon + "", lat + "", "2", sosEntity.getHelpId(), phone, secret, LanguageUtil.judgeLanguage()), 0);

                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 0:
                showToast(getString(R.string.ninyijieshoujiuyuan));
             // ----------------家属接受救援请求透传-------------
                String action = "saverLocation";
                EMMessage cmdMsg = EMMessage
                        .createSendMessage(EMMessage.Type.CMD);
                EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
                String toUsername = sosEntity.getFromuserId();// 发送给某个人
                cmdMsg.setReceipt(toUsername);
                cmdMsg.setAttribute("id", userId);
                cmdMsg.setAttribute("name", UserManager.getUserInfo().getUsername());
                cmdMsg.setAttribute("lon", lon + "");
                cmdMsg.setAttribute("lat", lat + "");
//                Logger.e("helpju",userId+"###"+UserManager.getUserInfo().getUsername()+"###"+lon+"##"+lat);
                cmdMsg.addBody(cmdBody);
                EMClient.getInstance().chatManager().sendMessage(cmdMsg);
                break;
            case 1:
                showToast(getString(R.string.ninyijingfangqijiuyuan));
                String action1 = "saverGoBack";// 家属取消救援
                EMMessage cmdMsg1 = EMMessage.createSendMessage(EMMessage.Type.CMD);
                EMCmdMessageBody cmdBody1 = new EMCmdMessageBody(action1);
                String toUsername1 = sosEntity.getFromuserId();// 发送给某个人
                cmdMsg1.setReceipt(toUsername1);
                cmdMsg1.setAttribute("id", userId);
                cmdMsg1.setAttribute("name", UserManager.getUserInfo().getUsername());
                cmdMsg1.addBody(cmdBody1);
                EMClient.getInstance().chatManager().sendMessage(cmdMsg1);
                finish();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        JSONObject object = JSONObject.parseObject(result);
        showToast(object.getString("retMessage"));
        finish();
    }

    /**
     * 开始搜索路径规划方案
     */
    private void searchRouteResult(int mode) {
        if (mStartPoint == null) {
            Toast.makeText(this, R.string.dingweizhongshaohouzaishi, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEndPoint == null) {
            Toast.makeText(this, R.string.zhongdianweishezhi, Toast.LENGTH_SHORT).show();
        }
//        mds.showDialog();
        Logger.e("坐标 414", lat + "," + lon + "," + lat_end + "," + lon_end);
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询

    }

    private void setfromandtoMarker() {
        aMap.addMarker(new MarkerOptions().position(
                AMapUtil.convertToLatLng(mStartPoint)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.start)));
        aMap.addMarker(new MarkerOptions().position(
                AMapUtil.convertToLatLng(mEndPoint)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.end)));

    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mapLocationClient == null) {
            // 初始化定位的客户端
            mapLocationClient = new AMapLocationClient(this);
            // 初始化定位的参数
            mLoationOption = new AMapLocationClientOption();
            // 设置定位监听
            mapLocationClient.setLocationListener(this);
            // 设置为高精度定位的模式
            mLoationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 设置是否强制刷新WIFI，默认为强制刷新
            mLoationOption.setWifiActiveScan(false);
            // 设置是否只是定位一次，默认为false
            mLoationOption.setOnceLocation(true);
            // 设置是否返回的地址信息（默认返回地址信息）
            mLoationOption.setNeedAddress(true);
            // 设置是否允许模拟位置，默认为false，不允许模拟位置
            mLoationOption.setMockEnable(false);
            // 轮询，设置定位间隔现在是2000ms
            mLoationOption.setInterval(2000);
            // 设置定位参数
            mapLocationClient.setLocationOption(mLoationOption);
            /**
             * 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
             * 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
             * 在定位结束后，在合适的生命周期调用onDestroy()方法
             * 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
             */
            // 启动定位
            mapLocationClient.startLocation();

        }

    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mapLocationClient != null) {
            mapLocationClient.stopLocation();
            mapLocationClient.onDestroy();

        }
        mapLocationClient = null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        convertToLatLng(latLonPoint), 15));
                regeoMarker.setPosition(convertToLatLng(latLonPoint));
                // aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                // LatLng(lat, lon), 15));

            } else {
//                Toast.makeText(getApplicationContext(),
//                        getString(R.string.no_result), Toast.LENGTH_SHORT)
//                        .show();

            }

        }
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        // TODO 驾车的回调方法
//        mds.dismisssDialog();
        aMap.clear();// 清理地图上的所有覆盖物
//        Logger.e("errorCode 745", errorCode + "");
        if (errorCode == 1000) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            this, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),

                            mDriveRouteResult.getTargetPos());
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();

                    // mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "("
                            + AMapUtil.getFriendlyLength(dis) + ")";
                    // mRotueTimeDes.setText(des);
                    // mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    // mRouteDetailDes.setText("打车约" + taxiCost + "元");
//                    btn_detail.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View view) {
//                            // Toast.makeText(HelpReceiveActivity.this,
//                            // "点击了drive", 0).show();
//                            Intent intent = new Intent(
//                                    HelpReceiveActivity.this,
//                                    DriveRouteDetailActivity.class);
//                            intent.putExtra("drive_path", drivePath);
//                            intent.putExtra("drive_result", mDriveRouteResult);
//                            intent.putExtra("lat_start", lat);
//                            intent.putExtra("lon_start", lon);
//                            intent.putExtra("lat_end", lat_end);
//                            intent.putExtra("lon_end", lon_end);
//
//                            startActivity(intent);
//
//                        }
//                    });

                } else if (result != null && result.getPaths() == null) {
                    Toast.makeText(this, R.string.duibuqimeiyousousuo, Toast.LENGTH_SHORT)
                            .show();
                }

            } else {
                Toast.makeText(this, R.string.duibuqimeiyousousuo, Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(getString(R.string.ninshifoufangqijiuyuan))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(SosUrl.class).giveUpHelp(sosEntity.getHelpId()
                                , userId, phone, secret,LanguageUtil.judgeLanguage()), 1);

                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                    }
                })
                .show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new MaterialDialog(this)
                    .setMDNoTitle(true)
                    .setMDMessage(getString(R.string.ninshifoufangqijiuyuan))
                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(SosUrl.class).giveUpHelp(sosEntity.getHelpId()
                                    , userId, phone, secret,LanguageUtil.judgeLanguage()), 1);

                        }
                    })
                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
                        }
                    })
                    .show();
        }
        return false;
    }
}
