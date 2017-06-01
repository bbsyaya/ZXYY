package com.hyphenate.easeui.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hyphenate.easeui.R;

/**
 * 高德地图获取位置信息
 *
 * @author 张立涛 2016-05-30--(创建) 2016-05-31--(第一次修改)
 *         LocationSource--定位接口
 *         实现AMapLocationListener以取得高德返回的经纬度
 *         实现OnGeocodeSearchListener以取得高德返回逆地理编码结果
 */
public class EaseGaoDeMapActivity extends EaseBaseActivity implements
        LocationSource, AMapLocationListener, OnGeocodeSearchListener {

    private final static String TAG = "map";

    /**
     * 发送地理信息按钮
     */
    Button sendbtn = null;
    //	EditText indexText = null;
    int index = 0;
    public static EaseGaoDeMapActivity instance = null;
    ProgressDialog progressDialog;
    /**
     * 地理编码
     */
    private GeocodeSearch geocodeSearch;
    /**
     * 地图控制 包括显示交通 定位层显示配置
     */
    private AMap aMap;
    /**
     * 定义地图容器
     */
    private MapView mapView = null;
    private OnLocationChangedListener mlistener;
    /**
     * 声明AMapLocationClient类对象(定位客户端)
     */
    private AMapLocationClient mlocationClient;
    /**
     * 声明mLocationOption对象(定位参数)
     */
    private AMapLocationClientOption mLoationOption;
    private LatLonPoint latLonPoint = null;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 维度
     */
    private double latitude;
    /**
     * 地址信息
     */
    private String address;
    /**
     * 覆盖物,在地图上进行绘制的点
     */
    private Marker geoMarker;
    /**
     * 覆盖物,在地图上进行绘制的点
     */
    private Marker regeoMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ease_activity_baidumap);
        // 获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        // 在activity执行onCreate是执行mapview.onCreate(saveInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        sendbtn = (Button) findViewById(R.id.btn_location_send);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        if (latitude == 0) {
            setUpMap();
            showMapWithLocationClient(getResources().getString(
                    R.string.Making_sure_your_location));
        } else {
            longitude = intent.getDoubleExtra("longitude", 0);
            address = intent.getStringExtra("address");
            latLonPoint = new LatLonPoint(latitude, longitude);
            showMap();
        }
    }

    private void showMap() {
        sendbtn.setVisibility(View.GONE);
        showMapWithLocationClient(getResources().getString(
                R.string.Is_to_get_the_address));
// 设置当前地图显示为当前的位置(放大的级别)
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                latitude, longitude), 15));
        // 添加覆盖物图标
        aMap.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude)).icon(
                BitmapDescriptorFactory
                        .fromResource(R.drawable.location_marker)));
//		geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)// 锚点位置
//				.icon(BitmapDescriptorFactory
//						.fromResource(R.drawable.location_marker)));
//		regeoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
//				.icon(BitmapDescriptorFactory
//						.fromResource(R.drawable.location_marker)));
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        // latlng--范围多少米--表示是火星坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求

    }

    /**
     * 显示进度条对话框
     *
     * @param
     */
    private void showMapWithLocationClient(String str) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(str);
        progressDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                finish();
            }
        });
        progressDialog.show();
    }

    /**
     * 设置一些amap属性 设置当前位置的配置
     */
    private void setUpMap() {
        // 设置位置监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，可以由定位、跟随或地图根据面方向旋转几种
//		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

    }

    /**
     * 隐藏进度条对话框
     */
    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写 保存Activity状态
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    public void back(View v) {
        finish();
    }

    /**
     * 发送坐标
     *
     * @param view
     */
    public void sendLocation(View view) {
        Intent intent = this.getIntent();
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("address", address);
        this.setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_from_left,
                R.anim.slide_out_to_right);

    }

    /**
     * 设置定位参数并启动
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mlistener = listener;
        if (mlocationClient == null) {
            // 初始化定位客户端
            mlocationClient = new AMapLocationClient(this);
            // 初始化定位参数
            mLoationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLoationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            // 轮询，设置定位间隔现在是30000ms
            mLoationOption.setInterval(30000);
            // 设置定位参数
            mlocationClient.setLocationOption(mLoationOption);
            /**
             * 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
             * 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
             * 在定位结束后，在合适的生命周期调用onDestroy()方法
             * 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
             */

            // 启动定位
            mlocationClient.startLocation();
        }

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mlistener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mlistener != null && amapLocation != null) {
            sendbtn.setEnabled(true);
            dismissDialog();
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mlistener.onLocationChanged(amapLocation);// 显示系统小蓝点
                longitude = amapLocation.getLongitude();// 获取经度
                latitude = amapLocation.getLatitude();// 获取维度
                address = amapLocation.getAddress();// 获取地址
//				Toast.makeText(getApplicationContext(),
//						longitude + "," + latitude + "," + address,
//						Toast.LENGTH_SHORT).show();
                // 设置当前地图显示为当前的位置(放大的级别)
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                        latitude, longitude), 15));
                // 添加覆盖物图标
                aMap.addMarker(new MarkerOptions().position(
                        new LatLng(latitude, longitude)).icon(
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.location_marker)));
            } else {
                // 显示错误信息ErrCode是错误码，errInfo是错误信息。
                final String errText = "定位失败," + amapLocation.getErrorCode()
                        + ": " + amapLocation.getErrorInfo();
                Toast.makeText(getApplicationContext(), errText,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == 0) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        convertToLatLng(latLonPoint), 15));
//				regeoMarker.setPosition(convertToLatLng(latLonPoint));
            } else {
//				Toast.makeText(getApplicationContext(),
//						getResources().getString(R.string.no_result),
//						Toast.LENGTH_SHORT).show();
            }
        } else if (rCode == 27) {
//			Toast.makeText(getApplicationContext(),
//					getResources().getString(R.string.error_network),
//					Toast.LENGTH_SHORT).show();
        } else if (rCode == 32) {
//			Toast.makeText(getApplicationContext(),
//					getResources().getString(R.string.error_key),
//					Toast.LENGTH_SHORT).show();
        } else {
//			Toast.makeText(getApplicationContext(),
//					getResources().getString(R.string.error_other) + rCode,
//					Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }
}
