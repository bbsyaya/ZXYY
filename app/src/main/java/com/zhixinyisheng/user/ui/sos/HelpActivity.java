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
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.and.yzy.frame.application.Constant;
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
import com.zhixinyisheng.user.ui.IM.ui.SelectFriendsAty;
import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.common.CommonSHML;
import com.zhixinyisheng.user.ui.data.BLE.common.Utils;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;
import com.zhixinyisheng.user.util.LanguageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 发送呼救
 * Created by 焕焕 on 2016/11/5.
 */
public class HelpActivity extends BaseAty implements LocationSource,
        GeocodeSearch.OnGeocodeSearchListener, AMapLocationListener {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.map)
    MapView mMapView;
    private GeocodeSearch geocodeSearch;//地理编码
    private AMap aMap;//高德地图控制器对象,包括显示交通和定位层显示的配置
    private OnLocationChangedListener mListener;
    private AMapLocationClient mapLocationClient;//声明AMapLocationClient类对象(定位客户端)
    private LatLonPoint latLonPoint = null;
    private AMapLocationClientOption mLoationOption;//声明mLocationOption对象(定位参数)
    private double lat, lon;//经纬度
    private String address;//地址位置的信息
    private Marker geoMarker;//覆盖物，在地图上进行绘制的点
    private Marker regeoMarker;//覆盖物，在地图上进行绘制的点
    SosEntity sosEntity;
    String helpId;

    private MyBroadcastReceiver mSMSBroadcastReceiver;//透传

    @Override
    public int getLayoutId() {
        return R.layout.aty_help;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //发送中命令
        if (TestService.mDevice != null) {
            Utils.sendSHML(CommonSHML.SHML_FSZ);
        }
        showLoadingDialog(getString(R.string.qingshaodeng));
        cjsTvt.setText(R.string.jinjihujiu);
        ivBack.setVisibility(View.VISIBLE);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        setUpMap();
        mSMSBroadcastReceiver = new MyBroadcastReceiver();
        mSMSBroadcastReceiver.setOnReceivedMessageListener(new MyBroadcastReceiver.MessageListener() {
            @Override
            public void OnReceived(List<String> message) {
                Logger.e("透传来了", message.get(0));
                if (message.get(0).equals("saverLocation")) {// 有人接受救援了
                    dismissLoadingDialog();
                    //提示手环有人来救你了
                    if (TestService.mDevice != null) {
                        Utils.sendSHML(CommonSHML.SHML_YRLJ);
                    }
                    aMap.clear();
                    // 设置所有maker显示在当前可视区域地图中
                    LatLngBounds bounds = new LatLngBounds.Builder()
                            .include(new LatLng(Double.parseDouble(message.get(3)), Double.parseDouble(message.get(4))))
                            .include(new LatLng(lat, lon)).build();
                    aMap.moveCamera(CameraUpdateFactory
                            .newLatLngBounds(bounds, 19));
                    // 添加覆盖物图标
                    aMap.addMarker(new MarkerOptions().position(
                            new LatLng(lat, lon)).icon(
                            BitmapDescriptorFactory
                                    .fromResource(R.drawable.location_marker)));
                    aMap.addMarker(new MarkerOptions().position(
                            new LatLng(Double.parseDouble(message.get(3)), Double.parseDouble(message.get(4)))).icon(
                            BitmapDescriptorFactory
                                    .fromResource(R.drawable.jumaker2)).title(message.get(1)));

                } else if (message.get(0).equals("saverGoBack")) {

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
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
        IntentFilter filter = new IntentFilter("sosaccept.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO 广播
            Logger.e("推送来了", "推送来了");

            sosEntity = (SosEntity) intent.getSerializableExtra("sosEntity");
            if (sosEntity.getLat().equals("")) {
                //这是对方放弃救援的情况
                showCancelDialog();
            } else {
                showToast(sosEntity.getName() + getString(R.string.jieshoulenindehujiu));
//                showAcceptDialog();


                // 设置所有maker显示在当前可视区域地图中
//                LatLngBounds bounds = new LatLngBounds.Builder()
//                        .include(new LatLng(Double.parseDouble(sosEntity.getLat()), Double.parseDouble(sosEntity.getLon())))
//                        .include(new LatLng(lat, lon)).build();
//                aMap.moveCamera(CameraUpdateFactory
//                        .newLatLngBounds(bounds, 19));
//                aMap.addMarker(new MarkerOptions().position(
//                        new LatLng(Double.parseDouble(sosEntity.getLat()), Double.parseDouble(sosEntity.getLon()))).icon(
//                        BitmapDescriptorFactory
//                                .fromResource(R.drawable.jumaker2)));
            }

        }
    };

    /**
     * 对方放弃救援的dialog
     */
    private void showCancelDialog() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(sosEntity.getName() + getString(R.string.fangqilenindehujiu))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        aMap.clear();
                        // 设置当前地图显示为当前的位置(放大的级别)
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                lat, lon), 15));
                        // 添加覆盖物图标
                        aMap.addMarker(new MarkerOptions().position(
                                new LatLng(lat, lon)).icon(
                                BitmapDescriptorFactory
                                        .fromResource(R.drawable.location_marker)));
                        showLoadingDialog("请稍等");
                        doHttp(RetrofitUtils.createApi(SosUrl.class).sendHelp(
                                userId, lon + "", lat + "", "2", phone, secret, LanguageUtil.judgeLanguage()), 0);

                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        //提示手环呼救取消
                        if (TestService.mDevice != null) {
                            MyLog.showLog("手环取消呼救", "手环取消呼救");
                            Utils.sendSHML(CommonSHML.SHML_SZZD);
                        }
                        finish();
                    }
                })
                .show();
    }

    /**
     * 接收呼救的dialog
     */
    private void showAcceptDialog() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(sosEntity.getName() + "接受了您的呼救!")
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {


                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {

                    }
                })
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
//		deactivate();// 停止定位的方法

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState
        // (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        dismissLoadingDialog();
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                // 显示系统的小蓝点
                mListener.onLocationChanged(aMapLocation);
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

                if (!TestService.is_shhj) {
//                    myShowDialog();// sos主界面弹出的Dialog

                    showLoadingDialog(getString(R.string.qingshaodeng));
                    doHttp(RetrofitUtils.createApi(SosUrl.class).sendHelp(
                            userId, lon + "", lat + "", "2", phone, secret, LanguageUtil.judgeLanguage()), 0);

                }
                //是否手环发过来的呼救
                if (TestService.is_shhj) {
                    TestService.is_shhj = false;
                    showLoadingDialog(getString(R.string.qingshaodeng));
                    doHttp(RetrofitUtils.createApi(SosUrl.class).sendHelp(
                            UserManager.getUserInfo().getUserId(), lon + "", lat + "", "2", UserManager.getUserInfo().getPhone(), UserManager.getUserInfo().getSecret(), LanguageUtil.judgeLanguage()), 0);
                }


            } else {
                // 显示错误信息ErrCode是错误码，errinfo是错误的信息。
                final String errText = aMapLocation.getErrorInfo();

                showToast(errText);
                finish();
//                Log.e("显示错误信息ErrCode是错误码,528", errText + "!  定位不成功!");
                // Toast.makeText(getApplicationContext(), errText,
                // Toast.LENGTH_SHORT).show();
            }

        }

    }

    /**
     * 患者sos界面
     */
    private void myShowDialog() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage("即将向你的医生和家人进行呼救!")
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        showLoadingDialog("请稍等");
                        doHttp(RetrofitUtils.createApi(SosUrl.class).sendHelp(
                                userId, lon + "", lat + "", "2", phone, secret, LanguageUtil.judgeLanguage()), 0);

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
        Logger.e("sos result", result);
        switch (what) {
            case 0:
                showLoadingDialog(getString(R.string.hujiuchenggongqingdengdai));
//                showToast(getString(R.string.hujiuchenggongqingdengdai));
                //提醒手环呼救进行中
                if (TestService.mDevice != null) {
                    Utils.sendSHML(CommonSHML.SHML_HJJX);
                }
                try {
                    JSONObject object = new JSONObject(result);
                    helpId = object.getString("helpId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                showToast(getString(R.string.ninyiquxiaohujiu));
                //提示手环呼救取消
                if (TestService.mDevice != null) {
                    MyLog.showLog("手环取消呼救", "手环取消呼救");
                    Utils.sendSHML(CommonSHML.SHML_SZZD);
                }
                if (sosEntity != null) {
                    EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
                    String action = "sosEnd";// 患者取消救援
                    EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
                    String toUsername = sosEntity.getToUserId();

//                    EMClient.getInstance().chatManager().sendMessage(cmdMsg);
                    cmdMsg.setReceipt(toUsername);
                    cmdMsg.setAttribute("id", userId);
                    cmdMsg.setAttribute("name", UserManager.getUserInfo().getUsername());
                    cmdMsg.setAttribute("lon", lon + "");
                    cmdMsg.setAttribute("lat", lat + "");
                    cmdMsg.addBody(cmdBody);
                    EMClient.getInstance().chatManager().sendMessage(cmdMsg);
                }

                finish();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what) {
            case 0:
                try {
                    JSONObject object = new JSONObject(result);
//            showToast(object.getString("retMessage"));
                    new MaterialDialog(this)
                            .setMDNoTitle(true)
                            .setMDMessage(getString(R.string.ninhaimeiyoujinjihujiuduixiang))
                            .setConfirmText(getString(R.string.qianwangshezhi))
                            .setCancelText(getString(R.string.quxiaohujiu))
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    Constant.GZCKSOS = 2;
                                    Constant.isHelpOpen = 1;
                                    startActivity(SelectFriendsAty.class, null);
                                    finish();
                                }
                            })
                            .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    //提示手环呼救取消
                                    if (TestService.mDevice != null) {
                                        MyLog.showLog("手环取消呼救", "手环取消呼救");
                                        Utils.sendSHML(CommonSHML.SHML_SZZD);
                                    }
                                    finish();
                                }
                            })
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    /**
     * 设置定位参数并启动
     */
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
            Log.e("253", lat + "," + lon);

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


    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());

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

        } else if (rCode == 27) {
//            Toast.makeText(getApplicationContext(),
//                    getResources().getString(R.string.error_network),
//                    Toast.LENGTH_LONG).show();
        } else if (rCode == 32) {
//            Toast.makeText(getApplicationContext(),
//                    getResources().getString(R.string.error_key),
//                    Toast.LENGTH_LONG).show();
        } else {
//            Toast.makeText(getApplicationContext(),
//                    getResources().getString(R.string.error_other) + rCode,
//                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(getString(R.string.ninshifouquxiaohujiu))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        showLoadingDialog(getString(R.string.qingshaodeng));
                        doHttp(RetrofitUtils.createApi(SosUrl.class).cancelHelp(helpId
                                , phone, secret, LanguageUtil.judgeLanguage()), 1);

                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
//                        finish();
                    }
                })
                .show();

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new MaterialDialog(this)
                    .setMDNoTitle(true)
                    .setMDMessage(getString(R.string.ninshifouquxiaohujiu))
                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
                            showLoadingDialog(getString(R.string.qingshaodeng));
                            doHttp(RetrofitUtils.createApi(SosUrl.class).cancelHelp(helpId
                                    , phone, secret, LanguageUtil.judgeLanguage()), 1);

                        }
                    })
                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
//                        finish();
                        }
                    })
                    .show();
        }
        return false;
    }
}
