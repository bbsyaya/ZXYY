package com.zhixinyisheng.user.ui.data.laboratory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.util.DensityUtils;
import com.and.yzy.frame.util.FileUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.cropper.CropImageView;
import com.zhixinyisheng.user.util.cropper.cropwindow.CropperImage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 化验单裁剪的第三步
 * Created by 焕焕 on 2017/2/21.
 */
public class LaboratoryCropThirdAty extends BaseAty{
    @Bind(R.id.civ_lab)
    CropImageView civLab;
    private static String textResult;
    private static Bitmap bitmapSelected;
    private static Bitmap bitmapTreated;
    private static final int SHOWRESULT = 0x101;
    private static final int SHOWTREATEDIMG = 0x102;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.tv_toast)
    TextView tvToast;
    String localPath="";
    // 该handler用于处理修改结果的任务
    public Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOWRESULT:
//                    dismissLoadingDialog();
                    textResult = textResult.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
                    Content.unit = textResult;
                    Log.e("third", textResult);
                    Intent intent = new Intent(Constant.LAB_CROP_FIRST);
                    sendBroadcast(intent);


//                    startActivity(LaboratoryEditAty.class,null);
//                    finish();
                    break;
                case SHOWTREATEDIMG:
//                    showLoadingDialog(null);
                    break;
            }
            super.handleMessage(msg);
        }

    };
    @Override
    public int getLayoutId() {
        return R.layout.aty_lab_crop_first;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.finish);
        cjsTvt.setText(R.string.danwei);
        tvToast.setText(R.string.qingjiequdanweizheyilie);
        Bundle bundle = getIntent().getExtras();
        localPath = bundle.getString("localPath");
        Bitmap bitmap = BitmapFactory.decodeFile(localPath);
        int height = DensityUtils.getScreenHeight(this) - DensityUtils.dp2px(this, 46);
        int weight = DensityUtils.getScreenWidth(this);
        Bitmap newBitmap = bigAsScreen(bitmap, weight, height, 2);
        civLab.setImageBitmap(newBitmap);
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_CROP);
        registerReceiver(myBroadcast, intentFilter);
    }
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            tvToast.setVisibility(View.GONE);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myBroadcast != null) {
            unregisterReceiver(myBroadcast);
        }
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

    /**
     * 进行图片识别
     *
     * @param bitmap   待识别图片
     * @param language 识别语言
     * @return 识别结果字符串
     */
//    public String doOcr(Bitmap bitmap, String language) {
//        TessBaseAPI baseApi = new TessBaseAPI();
//
//        baseApi.init(SavePath.savePath, language);
//
//        // 必须加此行，tess-two要求BMP必须为此配置
//        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//
//        baseApi.setImage(bitmap);
//
//        String text = baseApi.getUTF8Text();
//
//        baseApi.clear();
//        baseApi.end();
//
//        return text;
//    }

    /**
     * 获取sd卡的路径
     *
     * @return 路径的字符串
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取外存目录
        }
        return sdDir.toString();
    }



    @OnClick({R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                new MaterialDialog(this)
                        .setMDNoTitle(true)
                        .setMDMessage(getString(R.string.ninshifouyaotuichushibie))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                finish();
                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {

                            }
                        })
                        .show();

                break;
            case R.id.title_btn:
                CropperImage cropperImage = civLab.getCroppedImage();
//        ivCeshi.setImageBitmap(cropperImage.getBitmap());
                bitmapSelected = cropperImage.getBitmap();
                String labLocalPath3 = FileUtils.saveFile(this, "lab3.png", bitmapSelected);
                Content.unit = labLocalPath3;
                uploadImg();
                // 新线程来处理识别
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bitmapTreated = ImgPretreatment
//                                .converyToGrayImg(bitmapSelected);
//                        Message msg = new Message();
//                        msg.what = SHOWTREATEDIMG;
//                        myHandler.sendMessage(msg);
//                        textResult = doOcr(bitmapTreated, "eng");
//                        Message msg2 = new Message();
//                        msg2.what = SHOWRESULT;
//                        myHandler.sendMessage(msg2);
//                    }
//
//                }).start();
//                startActivity(LaboratoryEditAty.class,null);
//                finish();

                break;
        }
    }


    private void uploadImg() {
        showLoadingDialog(null);
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(2000, TimeUnit.SECONDS)
                .build();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File f1 = new File(Content.chineseName);
        File f2 = new File(Content.resultData);
        File f3 = new File(Content.unit);
//        Logger.e("f1f2f3",Content.chineseName+"%%%"+Content.resultData+"###"+Content.unit);
        builder.addFormDataPart("pic1", f1.getName(), RequestBody.create(MEDIA_TYPE_PNG, f1));
        builder.addFormDataPart("pic2", f2.getName(), RequestBody.create(MEDIA_TYPE_PNG, f2));
        builder.addFormDataPart("pic3", f3.getName(), RequestBody.create(MEDIA_TYPE_PNG, f3));
        final MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(HttpConfig.BASE_URL+"laboratory/showPic" )//地址
                .post(requestBody)//添加请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("上传失败","上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                FileUtils.delFolder(Content.chineseName);
                FileUtils.delFolder(Content.resultData);
                FileUtils.delFolder(Content.unit);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(getString(R.string.shangchuantupianshibai));
                        hideLoadingDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                FileUtils.delFolder(Content.chineseName);
                FileUtils.delFolder(Content.resultData);
                FileUtils.delFolder(Content.unit);
                Logger.e("上传照片成功","上传照片成功：response = " + body);
                System.out.println("上传照片成功：response = " + body);
                try {
                    JSONObject object  = JSONObject.parseObject(body);
                    if (object.getString("result").equals("0000")){
                        Bundle bundle = new Bundle();
                        bundle.putString("a",object.getString("a"));
                        bundle.putString("b",object.getString("b"));
                        bundle.putString("c",object.getString("c"));
                        startActivity(LaboratoryEditAty.class, bundle);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //{"result":"http://192.168.42.216:8066/baobei/api/ceshi/2016-12/d9dd132bb7eb4dab97bed5f747035dc6.jpg,http://192.168.42.216:8066/baobei/api/ceshi/2016-12/110b11f1a0de5403888c97c9d879ba15b.jpg"}
//                        try {
//                            JsonResponse jsonResponse = JSON.parseObject(body, JsonResponse.class);
//                            String mPicUrls = jsonResponse.getResult();
//                            Logger.e("mpic",mPicUrls+"$$");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            showToast("解析失败");
//                        }
//                    }
//                });
            }
        });
    }
}
