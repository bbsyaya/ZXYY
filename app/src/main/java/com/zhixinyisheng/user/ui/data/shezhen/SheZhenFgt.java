package com.zhixinyisheng.user.ui.data.shezhen;

import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.util.FileUtils;
import com.and.yzy.frame.util.RetrofitUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.SheZhenListAdapter;
import com.zhixinyisheng.user.config.PhotoTheme;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.datas.SheZhenDatas;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.data.shezhen.util.AlbumHelper;
import com.zhixinyisheng.user.ui.data.shezhen.util.ImageBucket;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.UpLodeUtilofHeadImage;
import com.zhixinyisheng.user.util.cropper.cropwindow.CropperImage;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;
import com.zhixinyisheng.user.view.leida.RadarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 舌诊
 * Created by 焕焕 on 2016/11/8.
 */
public class SheZhenFgt extends BaseFgt implements SurfaceHolder.Callback, AdapterView.OnItemClickListener {
    @Bind(R.id.take_photo_layout)
    RelativeLayout mTakePhotoLayout;
    @Bind(R.id.btn_xiangce)
    ImageView btnXiangce;
    @Bind(R.id.CropImageView)
    com.zhixinyisheng.user.util.cropper.CropImageView mCropImageView;
    @Bind(R.id.cropper_layout)
    RelativeLayout mCropperLayout;
    @Bind(R.id.shezhen_layout)
    FrameLayout frameLayout;
    @Bind(R.id.iv_getcaijian)
    ImageView iv_getcaijian;
    @Bind(R.id.fl_getcaijian)
    FrameLayout fl_getcaijian;
    @Bind(R.id.radar_view)
    RadarView radar_view;
    @Bind(R.id.ll_zhenduanwancheng)
    LinearLayout ll_zhenduanwancheng;
    @Bind(R.id.shezhen_result)
    TextView shezhen_result;
    @Bind(R.id.ll_zhenduanshibai)
    LinearLayout ll_zhenduanshibai;
    @Bind(R.id.sv_shezhen)
    SurfaceView sv_shezhen;
    @Bind(R.id.lv_shezhen)
    ListView lv_shezhen;
    @Bind(R.id.rl_zhenduanjieguo)
    RelativeLayout rl_zhenduanjieguo;
    @Bind(R.id.tv_shezhentime)
    TextView tv_shezhentime;
    @Bind(R.id.iv_zhenduanjieguo)
    ImageView iv_zhenduanjieguo;
    @Bind(R.id.tv_zhenduanjieguo)
    TextView tv_zhenduanjieguo;
    private List<PhotoInfo> mPhotoList = new ArrayList<>();
    String[] colors;
    private AlbumHelper helper;
    List<ImageBucket> contentList;
    boolean qiehuanFlag;//切换前后摄像头的旗帜

    /**
     * 创建摄像头对象
     */
    public static Camera camera;
    /**
     * 设置预览是否开启的变量
     */
    private boolean isPreview;
    /**
     * 管理拍照预览的对象SurfaceHolder.Callback;
     */
    private SurfaceHolder holder;
    /**
     * 照相机的参数对象
     */
    private Camera.Parameters parameters;
    String userPic, picALL, urlPath_result;
    String imgUrl = HttpConfig.BASE_URL + "upload/picTongue";
    List<SheZhenDatas.ListBean> listBeen = new ArrayList<SheZhenDatas.ListBean>();
    SheZhenListAdapter sheZhenListAdapter;
    List<HashMap<String, String>> listImage;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_shezhen;
    }

    @Override
    public void initData() {
        try {
            setCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        try {
            openFrontCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setCamera() {
        Content.tongue++;
        PhotoTheme.settingtheme(getActivity(), mPhotoList);
        try {
            sv_shezhen.setVisibility(View.VISIBLE);
            holder = sv_shezhen.getHolder();// 获取管理对象
            /** 保持屏幕常亮 */
            holder.setKeepScreenOn(true);
            /** 添加回调事件完成对surfaceView的管理 */
            holder.addCallback(this);
            String[] projection = {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA};
            String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            getContentProvider(uri, projection, orderBy);
            Glide.with(this).load(listImage.get((int) (Math.random() * 10)).get("_data"))
                    .placeholder(R.mipmap.ic_launcher2)
                    .error(R.mipmap.ic_launcher2).into(btnXiangce);
            lv_shezhen.setOnItemClickListener(this);
        } catch (Exception e) {
            Log.e("camera error", e.toString());
            e.printStackTrace();
        }
    }


    public void getContentProvider(Uri uri, String[] projection, String orderBy) {
        listImage = new ArrayList<HashMap<String, String>>();
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < projection.length; i++) {
                map.put(projection[i], cursor.getString(i));
//                System.out.println(projection[i]+":"+cursor.getString(i));
            }
            listImage.add(map);
        }
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.clear();
                mPhotoList.addAll(resultList);
                //准备截图
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(mPhotoList.get(0).getPhotoPath());
                    int height = frameLayout.getHeight();
                    int weight = frameLayout.getWidth();
                    Bitmap newBitmap = bigAsScreen(bitmap, weight, height, 2);
                    mCropImageView.setImageBitmap(newBitmap);
                } catch (Exception e) {
                }
                showCropperLayout();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onPause() {
        super.onPause();
//        if (canUse) {
        try {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private Bitmap bigAsScreen(Bitmap bitmap, int weight, int height, int flag) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float sx = (float) weight / w;//要强制转换，不转换我的在这总是死掉。
        float sy = (float) height / h;
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, w,
                h, matrix, true);
        float[] floats = null;
        switch (flag) {
            case 0: // 水平反转
                floats = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
                break;
            case 1: // 垂直反转
                floats = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
                break;
            case 2:
                floats = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
                //啥也不干
                break;
        }
        Matrix bmp = new Matrix();
        bmp.setValues(floats);
        return Bitmap.createBitmap(resizeBmp, 0, 0, resizeBmp.getWidth(), resizeBmp.getHeight(), bmp, true);


    }

    private void initVisible() {
        mTakePhotoLayout.setVisibility(View.GONE);
        mCropperLayout.setVisibility(View.GONE);
        fl_getcaijian.setVisibility(View.GONE);
        ll_zhenduanwancheng.setVisibility(View.GONE);
        ll_zhenduanshibai.setVisibility(View.GONE);
        rl_zhenduanjieguo.setVisibility(View.GONE);
    }

    private void showCropperLayout() {
        initVisible();
        mCropperLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        if (what == 0) {
            radar_view.stop();
            initVisible();
            ll_zhenduanwancheng.setVisibility(View.VISIBLE);
            try {
                JSONObject object = new JSONObject(result);
                shezhen_result.setText("\u3000" + object.getString("db"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (what == 1) {
            SheZhenDatas sheZhenDatas = JSON.parseObject(result, SheZhenDatas.class);
            listBeen.clear();
            listBeen.addAll(sheZhenDatas.getList());
            sheZhenListAdapter = new SheZhenListAdapter(getActivity(), listBeen, R.layout.item_shezhen);
            lv_shezhen.setAdapter(sheZhenListAdapter);
        } else if (what == 2) {
            FindByPidEntity findByPidEntity = JSON.parseObject(result, FindByPidEntity.class);
            new CalenderDialogTest(getActivity(), findByPidEntity.getList()) {
                @Override
                public void getZXTData() {
//                        showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DataUrl.class).findBytime(userId, Content.DATA, phone, secret, LanguageUtil.judgeLanguage()), 1);
                }
            };
        }


    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
//        showToast("当天没有测量数据！~");
//        listBeen.clear();
//        sheZhenListAdapter.notifyDataSetChanged();
        if (what == 2) {
            new CalenderDialogTest(getActivity(), null) {
                @Override
                public void getZXTData() {
//                        showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DataUrl.class).findBytime(userId, Content.DATA, phone, secret, LanguageUtil.judgeLanguage()), 1);
                }
            };
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        if (what == 0) {
            initVisible();
            ll_zhenduanshibai.setVisibility(View.VISIBLE);
        } else if (what == 1) {

        }

    }

    @OnClick({R.id.rl_shezhenrili, R.id.iv_shezhenclose, R.id.rl_shezhenqiehuan, R.id.iv_takephoto, R.id.btn_xiangce, R.id.btn_zhenduan, R.id.btn_zhenduan_cancel, R.id.iv_zice, R.id.btn_rezhenduan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_shezhenrili:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(DataUrl.class).findByPid(userId, phone, secret), 2);
                break;
            case R.id.iv_shezhenclose:
                initVisible();
                mTakePhotoLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_shezhenqiehuan:
                if (!qiehuanFlag) {
                    openBackCamera();
                    qiehuanFlag = true;
                } else {
                    openFrontCamera();
                    qiehuanFlag = false;
                }
                break;
            case R.id.iv_zice:
                initVisible();
                mTakePhotoLayout.setVisibility(View.VISIBLE);
                openFrontCamera();
                break;
            case R.id.btn_rezhenduan:
                initVisible();
                mTakePhotoLayout.setVisibility(View.VISIBLE);
                openFrontCamera();
                break;
            case R.id.iv_takephoto:
                takePhoto();
                break;
            case R.id.btn_xiangce:
                GalleryFinal.openGallerySingle(1001, PhotoTheme.functionConfig, mOnHanlderResultCallback);
                break;
            case R.id.btn_zhenduan:
                initVisible();
                mCropperLayout.setVisibility(View.VISIBLE);
                fl_getcaijian.setVisibility(View.VISIBLE);
                radar_view.start();
                final CropperImage cropperImage = mCropImageView.getCroppedImage();
                Glide.with(this).load(FileUtils.bitmapToBytes(cropperImage.getBitmap()))
                        .bitmapTransform(new CropCircleTransformation(getActivity())).into(iv_getcaijian);
                final String localPath = FileUtils.saveFile(getActivity(), System.currentTimeMillis() + "shezhen.png", cropperImage.getBitmap());
                Logger.e("shezhen389", localPath);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(localPath);
                        urlPath_result = UpLodeUtilofHeadImage.uploadFile_SheZhen(file, imgUrl);
//                        Logger.e("urlPath_result",urlPath_result);
                        JSONObject jo;
                        try {
                            jo = new JSONObject(urlPath_result);
                            userPic = jo.getString("userPic");
                            picALL = jo.getString("picALL");
//                            Log.e("url 310", userPic + "###" + picALL);
                            GetPicColor getPicColor = new GetPicColor(cropperImage.getBitmap());
                            colors = getPicColor.getColor();
                            doHttp(RetrofitUtils.createApi(DataUrl.class).getSheColor(colors[0], colors[1], colors[2], phone, secret, userId, userPic, picALL, LanguageUtil.judgeLanguage()), 0);
                        } catch (Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initVisible();
                                    ll_zhenduanshibai.setVisibility(View.VISIBLE);
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.btn_zhenduan_cancel:
                initVisible();
                mTakePhotoLayout.setVisibility(View.VISIBLE);
                openFrontCamera();
//                mCameraPreview.start();   //继续启动摄像头
                break;
        }
    }

    /**
     * 打开前置摄像头
     */
    private void openFrontCamera() {
        if (null != camera) {
            camera.stopPreview();//停掉原来摄像头的预览
            camera.release();//释放资源
            camera = null;//取消原来摄像头
        }
        camera = Camera.open(1);//打开当前选中的摄像头
        // 旋转90
        camera.setDisplayOrientation(90);
        // 设置相关参数
        parameters = camera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
        Camera.Size optionSize = getOptimalPreviewSize(sizeList, sv_shezhen.getWidth(), sv_shezhen.getHeight());//获取一个最为适配的camera.size
        parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
        int maxZoom = parameters.getMaxZoom();// 获取最大焦距
        parameters.setZoom((int) (maxZoom * 0.8));
        /** 照相机的闪光灯为自动 */
        if (parameters.getSupportedFlashModes() != null) {
            // 闪光灯为自动模式
            parameters.setFlashMode(parameters.FLASH_MODE_AUTO);
        }

        try {
            camera.setParameters(parameters);
            camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();//开始预览
    }

    /**
     * 打开后置摄像头
     */
    private void openBackCamera() {
        camera.stopPreview();//停掉原来摄像头的预览
        camera.release();//释放资源
        camera = null;//取消原来摄像头
        camera = Camera.open(0);//打开当前选中的摄像头
        // 旋转90
        camera.setDisplayOrientation(90);
        // 设置相关参数
        parameters = camera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
        Camera.Size optionSize = getOptimalPreviewSize(sizeList, sv_shezhen.getWidth(), sv_shezhen.getHeight());//获取一个最为适配的camera.size
        parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
        int maxZoom = parameters.getMaxZoom();// 获取最大焦距
        parameters.setZoom((int) (maxZoom * 0.8));
        /** 照相机的闪光灯为自动 */
        if (parameters.getSupportedFlashModes() != null) {
            // 闪光灯为自动模式
            parameters.setFlashMode(parameters.FLASH_MODE_AUTO);
        }
        camera.setParameters(parameters);
        try {
            camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();//开始预览
    }

    private void takePhoto() {
        // 使用默认方式对焦
        try {
            camera.autoFocus(null);
        } catch (Exception e) {
            e.printStackTrace();
            showToast(getString(R.string.ninmeiyoukaiqixiangjiquanxian));
            return;
        }
        // 拍照
        camera.takePicture(null, null, new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                bitmap = bigAsScreen(bitmap, 2000, 2000, 2);
                mCropImageView.setImageBitmap(bitmap);
                if (qiehuanFlag) {
                    mCropImageView.rotateImage(90);
                } else {
                    mCropImageView.rotateImage(-90);
                }

                showCropperLayout();
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Logger.e("shezhen 532", "surfaceCreated");

        if (camera != null) {
            camera.release();// 释放摄像头
            camera = null;
        }
        // 打开摄像头
        try {
            camera = Camera.open(1);//TODO 如果没开启权限的话会崩
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // 旋转90
        camera.setDisplayOrientation(90);
        // 设置相关参数
        parameters = camera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
        Camera.Size optionSize = getOptimalPreviewSize(sizeList, sv_shezhen.getWidth(), sv_shezhen.getHeight());//获取一个最为适配的camera.size
        parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
        int maxZoom = parameters.getMaxZoom();// 获取最大焦距
        Log.e("maxZoom", maxZoom + "");
        parameters.setZoom((int) (maxZoom * 0.8));
        /** 照相机的闪光灯为自动 */
        if (parameters.getSupportedFlashModes() != null) {
            // 闪光灯为自动模式
            parameters.setFlashMode(parameters.FLASH_MODE_AUTO);
        }

        // 设置预览效果
        try {
            camera.setParameters(parameters);
            camera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 开始预览
        camera.startPreview();
        isPreview = true;
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            if (isPreview) {
                camera.stopPreview();// 关闭预览
            }
            camera.release();// 释放摄像头
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        initVisible();
        rl_zhenduanjieguo.setVisibility(View.VISIBLE);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(listBeen.get(position).getCreateTime());
        tv_shezhentime.setText(dateString);
        tv_zhenduanjieguo.setText("\u3000\u3000" + listBeen.get(position).getUserResult());
        Glide.with(getActivity()).load(listBeen.get(position).getUserPic()).placeholder(R.drawable.zwt_03).into(iv_zhenduanjieguo);
    }
}
