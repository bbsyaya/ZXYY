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

import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.DensityUtils;
import com.and.yzy.frame.util.FileUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.cropper.CropImageView;
import com.zhixinyisheng.user.util.cropper.cropwindow.CropperImage;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 化验单裁剪的第一步
 * Created by 焕焕 on 2017/2/20.
 */
public class LaboratoryCropFirstAty extends BaseAty {
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
    String localPath = "";
    private String languageLib = "chi_sim";
    // 该handler用于处理修改结果的任务
    public Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOWRESULT:
//                    dismissLoadingDialog();

                    textResult = textResult.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
                    Content.chineseName = textResult;
                    Log.e("first jujing", textResult);
                    Intent intent = new Intent(Constant.LAB_CROP_FIRST);
                    sendBroadcast(intent);
                    break;
                case SHOWTREATEDIMG:
//                    showLoadingDialog(null);
                    break;
            }
            super.handleMessage(msg);
        }

    };
    public static  LaboratoryCropFirstAty instance;
    @Override
    public int getLayoutId() {
        return R.layout.aty_lab_crop_first;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        instance = this;
        Content.chineseName = "";
        Content.resultData = "";
        Content.unit = "";
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.xiayibu);
        tvToast.setText(R.string.qingjiequjianchaxiangmu);
        cjsTvt.setText(R.string.jianchaxiangmuzhongwenmingcheng);
        if (LanguageUtil.judgeLanguage().equals("zh")){
            languageLib = "chi_sim";
        }else{
            languageLib = "eng";
        }

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
//        //设置识别模式
////        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_BLOCK_VERT_TEXT);
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
                bitmapSelected = cropperImage.getBitmap();
                String labLocalPath1 = FileUtils.saveFile(this, "lab1.png", bitmapSelected);
                Content.chineseName = labLocalPath1;
                Logger.e("loca",labLocalPath1+"%%%");
//                // 新线程来处理识别
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bitmapTreated = ImgPretreatment
//                                .converyToGrayImg(bitmapSelected);
//                        Message msg = new Message();
//                        msg.what = SHOWTREATEDIMG;
//                        myHandler.sendMessage(msg);
//                        textResult = doOcr(bitmapTreated, languageLib);
//                        Message msg2 = new Message();
//                        msg2.what = SHOWRESULT;
//                        myHandler.sendMessage(msg2);
//                    }
//                }).start();
                Bundle bundle = new Bundle();
                bundle.putString("localPath", localPath);
                startActivity(LaboratoryCropSecondAty.class, bundle);
                finish();

                break;
        }
    }
}
