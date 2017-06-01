package com.zhixinyisheng.user.ui.sidebar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.util.FileUtils;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.FormBotomDialogBuilder;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.PhotoTheme;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.http.LeftUrl;
import com.zhixinyisheng.user.ui.BaseAty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 编辑个人资料
 * Created by gjj on 2016/10/27.
 */
public class EditorInformation extends BaseAty{
    @Bind(R.id.iv_back)
    ImageView iv_back;//返回键
    @Bind(R.id.cjs_tvt)
    TextView cjs_tvt;//标题
    @Bind(R.id.title_btn)
    Button title_btn;//确定
    @Bind(R.id.img_editor_touxiang)
    ImageView img_editor_touxiang;//头像
    @Bind(R.id.et_editor_name)
    EditText et_editor_name;//昵称
    @Bind(R.id.img_sex_man2)
    ImageView img_sex_man2;//男选中的图片
    @Bind(R.id.img_sex_woman2)
    ImageView img_sex_woman2;//女选中的图片
    @Bind(R.id.et_editor_age)
    EditText et_editor_age;//年龄
    @Bind(R.id.et_editor_adress)
    EditText et_editor_adress;//地址
    @Bind(R.id.et_editor_gexinqianming)
    EditText et_editor_gexinqianming;//个性签名
    @Bind(R.id.et_editor_country)
    EditText et_editor_country;

    private static String sex_num;

    private List<PhotoInfo> mPhotoList;

    /* 用来标识请求照相功能的activity */
    private final int CAMERA_WITH_DATA = 4023;

    private final int REQUEST_CODE_CAMERA = 2220;
    private final int REQUEST_CODE_GALLERY = 2221;
    /* 用来标识请求gallery的activity */
    private final int PHOTO_PICKED_WITH_DATA = 4021;
    private static final int REQUESTCODE_CUTTING = 4022; // 图片裁切标记
    String mPicUrls;

    @Override
    public int getLayoutId() {
        return R.layout.editorinformation;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPhotoList = new ArrayList<>();
        cjs_tvt.setText(getResources().getString(R.string.bianjiziliao));
        iv_back.setVisibility(View.VISIBLE);
        title_btn.setVisibility(View.VISIBLE);
        title_btn.setText(getResources().getString(R.string.finish));
        PhotoTheme.settingtheme(this,mPhotoList);
        setdata();
        et_editor_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable content = et_editor_name.getText();
                Selection.selectAll(content);
            }
        });

    }


    /**
     * 初始化数据
     */
    private void setdata(){
        Glide.with(this).load(UserManager.getUserInfo().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)
                .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(this)).into(img_editor_touxiang);

        et_editor_name.setText(UserManager.getUserInfo().getUsername());
        if(UserManager.getUserInfo().getSex()==1){
            img_sex_man2.setVisibility(View.VISIBLE);
            sex_num = "1";
        }else {
            img_sex_woman2.setVisibility(View.VISIBLE);
            sex_num = "0";
        }
        if (UserManager.getUserInfo().getAge()!=0){
            et_editor_age.setText(UserManager.getUserInfo().getAge()+"");
        }
        et_editor_adress.setText(UserManager.getUserInfo().getAddress());
        et_editor_gexinqianming.setText(UserManager.getUserInfo().getRemark());
        et_editor_country.setText(UserManager.getUserInfo().getCountry());
    }


    @OnClick({R.id.rl_gxqm,R.id.cjs_rlb,R.id.rl_touxiang,R.id.rl_sex_man,R.id.rl_sex_woman,R.id.title_btn})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()){
            case R.id.rl_gxqm:
                et_editor_gexinqianming.setFocusable(true);
                et_editor_gexinqianming.setFocusableInTouchMode(true);
                et_editor_gexinqianming.requestFocus();
                break;
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
            case R.id.rl_touxiang:
                mPhotoList.clear();
                choosePic();
                break;
            case R.id.rl_sex_man:
                img_sex_man2.setVisibility(View.VISIBLE);
                img_sex_woman2.setVisibility(View.GONE);
                sex_num = "1";
                break;
            case R.id.rl_sex_woman:
                img_sex_woman2.setVisibility(View.VISIBLE);
                img_sex_man2.setVisibility(View.GONE);
                sex_num = "0";
                break;
            case R.id.title_btn:
                if (et_editor_name.getText().toString().equals("")){
                    showToast(getResources().getString(R.string.qingshurunicheng));
                    return;
                }

                try {
                    uploadImg();
                } catch (Exception e) {
                    e.printStackTrace();
                    doHttp(RetrofitUtils.createApi(LeftUrl.class).personinformation(phone,secret,phone,UserManager.getUserInfo().getHeadUrl(),
                            et_editor_name.getText().toString(),sex_num,et_editor_age.getText().toString(),
                            et_editor_adress.getText().toString(),et_editor_gexinqianming.getText().toString(),
                            userId,et_editor_country.getText().toString()),0);
                }
                break;

        }
    }

    private void uploadImg() throws Exception{
        showLoadingDialog(null);
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        OkHttpClient client = new OkHttpClient();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < mPhotoList.size(); i++) {
            PhotoInfo image = mPhotoList.get(i);
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
                        showToast(getResources().getString(R.string.UploadFailed));
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
//                        showToast("上传图片成功");
                        hideLoadingDialog();
                        JsonResponse jsonResponse = JSON.parseObject(body, JsonResponse.class);
                        mPicUrls = jsonResponse.getResult();


                        doHttp(RetrofitUtils.createApi(LeftUrl.class).personinformation(phone,secret,phone,mPicUrls,
                                et_editor_name.getText().toString(),sex_num,et_editor_age.getText().toString(),
                                et_editor_adress.getText().toString(),et_editor_gexinqianming.getText().toString(),
                                userId,et_editor_country.getText().toString()),1);


                    }
                });
            }
        });
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what){
            case 0:
                showToast(getResources().getString(R.string.ModifySuccess));
                UserInfo userInfo = UserManager.getUserInfo();
//                userInfo.setHeadUrl(mPicUrls);
                userInfo.setUsername(et_editor_name.getText().toString());
                try {
                    userInfo.setSex(Integer.parseInt(sex_num));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    userInfo.setAge(Integer.parseInt(et_editor_age.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                userInfo.setAddress(et_editor_adress.getText().toString());
                userInfo.setRemark(et_editor_gexinqianming.getText().toString());
                userInfo.setCountry(et_editor_country.getText().toString());
                UserManager.setUserInfo(userInfo);
                finish();
                break;
            case 1:
                showToast(getResources().getString(R.string.ModifySuccess));
                UserInfo userInfo2 = UserManager.getUserInfo();
                userInfo2.setHeadUrl(mPicUrls);
                userInfo2.setUsername(et_editor_name.getText().toString());
                try {
                    userInfo2.setSex(Integer.parseInt(sex_num));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    userInfo2.setAge(Integer.parseInt(et_editor_age.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                userInfo2.setAddress(et_editor_adress.getText().toString());
                userInfo2.setRemark(et_editor_gexinqianming.getText().toString());
                userInfo2.setCountry(et_editor_country.getText().toString());
                UserManager.setUserInfo(userInfo2);
                finish();
                break;
        }


        super.onSuccess(result, call, response, what);
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        Logger.e("头像上传失败------------------------");
    }



    /**
     * 底部弹出Popupwindow
     */

    private void choosePic() {
        final FormBotomDialogBuilder builder = new FormBotomDialogBuilder(this);
        View v = getLayoutInflater().inflate(R.layout.choose_photo_layout, null);
        builder.setFB_AddCustomView(v);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fbtn_one:
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, PhotoTheme.functionConfig, mOnHanlderResultCallback);
                        break;
                    case R.id.fbtn_two:
                        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, PhotoTheme.functionConfig, mOnHanlderResultCallback);
                        break;
                    case R.id.fbtn_three:
                        builder.dismiss();
                        break;
                }
                builder.dismiss();
            }
        };

        v.findViewById(R.id.fbtn_one).setOnClickListener(listener);
        v.findViewById(R.id.fbtn_two).setOnClickListener(listener);
        v.findViewById(R.id.fbtn_three).setOnClickListener(listener);
        builder.show();
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.addAll(resultList);
                File temp = new File(mPhotoList.get(0).getPhotoPath());
                Uri imageUri = FileProvider.getUriForFile(EditorInformation.this, "com.jph.takephoto.fileprovider", temp);//通过FileProvider创建一个content类型的Uri
                startPhotoZoom(imageUri);

//                Glide.with(EditorInformation.this).load(mPhotoList.get(0).getPhotoPath())
//                        .placeholder(R.mipmap.ic_launcher2)
//                        .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(EditorInformation.this)).into(img_editor_touxiang);


            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(EditorInformation.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }
    /**
     * 获取startActivityForResult传过来的图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 保存裁剪之后的图片数据 把选择的图片显示到ImageView上
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap bitmap = extras.getParcelable("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 30, baos);
            } catch (Exception e) {
                showToast(getResources().getString(R.string.caijianshibai));
                e.printStackTrace();
                return;
            }
            byte[] bytes=baos.toByteArray();
            Drawable drawable = new BitmapDrawable(null, bitmap);
            String pathNow= FileUtils.saveFile(this, "temphead1.png", bitmap);
            mPhotoList.get(0).setPhotoPath(pathNow);
            Glide.with(this).load(bytes)
                    .placeholder(R.mipmap.ic_launcher2)
                    .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(this)).into(img_editor_touxiang);

        }
    }

}
