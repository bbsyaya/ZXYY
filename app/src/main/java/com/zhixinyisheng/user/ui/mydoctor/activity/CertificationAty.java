package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.config.HttpConfig;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.ChooseIconAdapter;
import com.zhixinyisheng.user.config.PhotoTheme;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.ui.BaseAty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 上传胸牌或者身份证照片
 * Created by 焕焕 on 2017/1/12.
 */
public class CertificationAty extends BaseAty implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.main_unread_msg_number)
    TextView mainUnreadMsgNumber;
    @Bind(R.id.cjs_rl_xhd)
    RelativeLayout cjsRlXhd;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.title_btn_hosptal)
    Button titleBtnHosptal;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title_line)
    View titleLine;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.gv_pic)
    GridView gvPic;
    private List<PhotoInfo> mPhotoList = new ArrayList<>();
    private List<PhotoInfo> mPhotoListWillAdd = new ArrayList<>();
    private ChooseIconAdapter chooseIconAdapter;
    private List<String> selectedPath = new ArrayList<String>();// 选中的图片地址
    private final int REQUEST_CODE_GALLERY = 1001;
    String[] urlPath;
    String cardPic;
    @Override
    public int getLayoutId() {
        return R.layout.aty_certification;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText("普通认证");
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText("提交");
        chooseIconAdapter = new ChooseIconAdapter(this);
        gvPic.setAdapter(chooseIconAdapter);
        PhotoTheme.settingtheme(this, mPhotoList);
        gvPic.setOnItemClickListener(this);
        gvPic.setOnItemLongClickListener(this);

        Bundle bundle = getIntent().getExtras();
        cardPic = bundle.getString("cardPic");
        Log.e("cardpic",cardPic.length()+"&&");
        if (cardPic.length()!=0){
            Log.e("cardpic",cardPic+"&&$$$");
            urlPath = cardPic.split(",");
            for (int i = 0; i < urlPath.length; i++) {
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.setPhotoPath(urlPath[i]);
                photoInfo.setHeight(0);
                mPhotoList.add(photoInfo);
            }
            chooseIconAdapter.setData(mPhotoList);
        }


    }

    /**
     * 多选图片
     */
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.addAll(resultList);
                mPhotoListWillAdd.addAll(resultList);
                chooseIconAdapter.setData(mPhotoList);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            showToast(errorMsg);
        }
    };

    @OnClick({R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_btn:
                try {
                    uploadImg(mPhotoListWillAdd);
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissLoadingDialog();
                    showToast("请上传照片！");
                }
                break;
        }

    }

    /**
     * 上传图片
     */
    private void uploadImg(List<PhotoInfo> imageList) throws Exception {
        showLoadingDialog(null);
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        OkHttpClient client = new OkHttpClient();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < imageList.size(); i++) {
            PhotoInfo image = imageList.get(i);
            File f = new File(image.getPhotoPath());
            builder.addFormDataPart("files", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
        }

        //添加其它信息
//        builder.addFormDataPart("time",takePicTime);
//        builder.addFormDataPart("mapX", SharedInfoUtils.getLongitude());
//        builder.addFormDataPart("mapY",SharedInfoUtils.getLatitude());
//        builder.addFormDataPart("name",SharedInfoUtils.getUserName());
        final MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(HttpConfig.BASE_URL + "upload/user")//地址
                .post(requestBody)//添加请求体
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("上传图片失败");
                        hideLoadingDialog();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                final String body = response.body().string();
                System.out.println("上传照片成功：response = " + body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("上传图片成功");
                        hideLoadingDialog();
                        JsonResponse jsonResponse = JSON.parseObject(body, JsonResponse.class);
                        String mPicUrls = jsonResponse.getResult();
                        Log.e("mPicUrls 228",mPicUrls);
                        if (cardPic.length()!=0){
                            mPicUrls = cardPic+","+mPicUrls;
                        }
                        Intent intent = new Intent();
                        intent.putExtra("cardPic", mPicUrls);
                        setResult(0, intent);
                        finish();

                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        if (0 == mPhotoList.size() ? true : i == mPhotoList.size()) {
            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, PhotoTheme.functionConfig, mOnHanlderResultCallback);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
