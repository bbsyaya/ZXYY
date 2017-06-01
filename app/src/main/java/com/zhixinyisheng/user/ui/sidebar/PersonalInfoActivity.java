package com.zhixinyisheng.user.ui.sidebar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zhixinyisheng.user.util.GlideUtil;
import com.zhixinyisheng.user.view.mydoctor.CityPicker;

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

import static com.zhixinyisheng.user.R.id.cjs_tvt;

/**
 * 个人资料
 * Created by 焕焕 on 2017/5/1.
 */

public class PersonalInfoActivity extends BaseAty {
    @Bind(cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_personal_avatar)
    ImageView ivPersonalAvatar;
    @Bind(R.id.rl_personal_avatar)
    RelativeLayout rlPersonalAvatar;
    @Bind(R.id.tv_personal_nickname)
    TextView tvPersonalNickname;
    @Bind(R.id.ll_personal_nickname)
    LinearLayout llPersonalNickname;
    @Bind(R.id.tv_personal_gender)
    TextView tvPersonalGender;
    @Bind(R.id.ll_personal_gender)
    LinearLayout llPersonalGender;
    @Bind(R.id.tv_personal_age)
    TextView tvPersonalAge;
    @Bind(R.id.ll_personal_age)
    LinearLayout llPersonalAge;
    @Bind(R.id.ll_personal_code)
    LinearLayout llPersonalCode;
    @Bind(R.id.tv_personal_country)
    TextView tvPersonalCountry;
    @Bind(R.id.ll_personal_country)
    LinearLayout llPersonalCountry;
    @Bind(R.id.tv_personal_address)
    TextView tvPersonalAddress;
    @Bind(R.id.ll_personal_address)
    LinearLayout llPersonalAddress;
    @Bind(R.id.tv_personal_remark)
    TextView tvPersonalRemark;
    @Bind(R.id.ll_personal_remark)
    LinearLayout llPersonalRemark;
    private List<PhotoInfo> mPhotoList = new ArrayList<>();
    private final int REQUEST_CODE_CAMERA = 2220;
    private final int REQUEST_CODE_GALLERY = 2221;
    /* 用来标识请求gallery的activity */
    private final int PHOTO_PICKED_WITH_DATA = 4021;
    private static final int REQUESTCODE_CUTTING = 4022; // 图片裁切标记
    String mPicUrls;
    private Dialog dialog;
    private ImageView ewm_ewm, ewm_sex, ewm_icon;
    private TextView ewm_name, ewm_id;
    private String pro, ci;
    private CityPicker mCityPicker;
    private String address;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        cjsTvt.setText(R.string.gerenziliao);
        ivBack.setVisibility(View.VISIBLE);
        PhotoTheme.settingtheme(this, mPhotoList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        if (!UserManager.getUserInfo().getUsername().equals("")) {
            tvPersonalNickname.setText(UserManager.getUserInfo().getUsername());
        }
        if (!UserManager.getUserInfo().getAddress().equals("")) {
            tvPersonalAddress.setText(UserManager.getUserInfo().getAddress());
        }
        if (!UserManager.getUserInfo().getRemark().equals("")) {
            tvPersonalRemark.setText(UserManager.getUserInfo().getRemark());
        }
        if (!UserManager.getUserInfo().getCountry().equals("")) {
            tvPersonalCountry.setText(UserManager.getUserInfo().getCountry());
        }
        GlideUtil.loadCircleAvatar(this, UserManager.getUserInfo().getHeadUrl(), ivPersonalAvatar);
        if (UserManager.getUserInfo().getSex() == 1) {
            tvPersonalGender.setText(R.string.nan);
        } else {
            tvPersonalGender.setText(R.string.nv);
        }
        if (UserManager.getUserInfo().getAge() != 0) {
            tvPersonalAge.setText(UserManager.getUserInfo().getAge() + "");
        }


    }


    @OnClick({R.id.cjs_rlb, R.id.rl_personal_avatar, R.id.ll_personal_nickname, R.id.ll_personal_gender, R.id.ll_personal_age, R.id.ll_personal_code, R.id.ll_personal_country, R.id.ll_personal_address, R.id.ll_personal_remark})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.rl_personal_avatar:
                mPhotoList.clear();
                choosePic();
                break;
            case R.id.ll_personal_nickname:
                startActivity(ChangeNickActivity.class, null);
                break;
            case R.id.ll_personal_gender:
                showGenderDialog();
                break;
            case R.id.ll_personal_age:
                startActivity(ChangeAgeActivity.class, null);
                break;
            case R.id.ll_personal_code:
                erWeiMaDialog();
                break;
            case R.id.ll_personal_country:
                startActivity(ChangeCountryActivity.class, null);
                break;
            case R.id.ll_personal_address:
                if (mCityPicker == null) {
                    mCityPicker = new CityPicker(this, findViewById(R.id.ll_container))
                            .setOnCitySelectListener(new CityPicker.OnCitySelectListener() {
                                @Override
                                public void onCitySelect(String province, String city) {
                                    pro = province;
                                    ci = city;
                                    address = province + "\u0020" + city;
                                    showLoadingDialog(null);
                                    doHttp(RetrofitUtils.createApi(LeftUrl.class).personinformation(phone, secret, phone, null,
                                            null, null, null,
                                            address, null,
                                            UserManager.getUserInfo().getUserId(), null), 4);
                                }
                            });
                }
                mCityPicker.show();
                break;
            case R.id.ll_personal_remark:
                startActivity(ChangeRemarkActivity.class, null);
                break;
        }
    }

    private void showGenderDialog() {
        int flagSex = 0;
        if (UserManager.getUserInfo().getSex() == 0) {
            flagSex = 1;
        } else {
            flagSex = 0;
        }
        new AlertDialog.Builder(this).setTitle(R.string.xingbie).setSingleChoiceItems(
                new String[]{getString(R.string.nan), getString(R.string.nv)}, flagSex,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showLoadingDialog(null);
                                doHttp(RetrofitUtils.createApi(LeftUrl.class).personinformation(phone, secret, phone, null,
                                        null, "1", null,
                                        null, null,
                                        UserManager.getUserInfo().getUserId(), null), 2);
                                break;
                            case 1:
                                showLoadingDialog(null);
                                doHttp(RetrofitUtils.createApi(LeftUrl.class).personinformation(phone, secret, phone, null,
                                        null, "0", null,
                                        null, null,
                                        UserManager.getUserInfo().getUserId(), null), 3);
                                break;
                        }


                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        Logger.e("resultdsads", result);
        showToast(getString(R.string.shezhichenggong));
        switch (what) {
            case 1://头像
                GlideUtil.loadCircleAvatar(this, mPicUrls, ivPersonalAvatar);
                UserInfo userInfo1 = UserManager.getUserInfo();
                userInfo1.setHeadUrl(mPicUrls);
                UserManager.setUserInfo(userInfo1);
                break;
            case 2://性别男
                tvPersonalGender.setText(R.string.nan);
                UserInfo userInfo2 = UserManager.getUserInfo();
                userInfo2.setSex(1);
                UserManager.setUserInfo(userInfo2);
                break;
            case 3://性别女
                tvPersonalGender.setText(R.string.nv);
                UserInfo userInfo3 = UserManager.getUserInfo();
                userInfo3.setSex(0);
                UserManager.setUserInfo(userInfo3);
                break;
            case 4://地区
                tvPersonalAddress.setText(address);
                UserInfo userInfo4 = UserManager.getUserInfo();
                userInfo4.setAddress(address);
                UserManager.setUserInfo(userInfo4);
                break;
        }
    }

    /**
     * 二维码的dialog
     */
    private void erWeiMaDialog() {
          /* 弹出dialog */
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.activity_dialog, null);
        dialog = new Dialog(this, R.style.MyDialog);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        Window window = dialog.getWindow();
        ewm_ewm = (ImageView) window.findViewById(R.id.img_erweima);// 二维码图片
        ewm_id = (TextView) window.findViewById(R.id.ewm_id);// 患者id
        ewm_name = (TextView) window.findViewById(R.id.ewm_Name);// 二维码上的姓名
        ewm_sex = (ImageView) window.findViewById(R.id.ewm_XB);// 二维码上的性别图标
        ewm_icon = (ImageView) window.findViewById(R.id.ewm_TX);// 二维码上的头像
        ewm_name.setText(UserManager.getUserInfo().getUsername());
        if (UserManager.getUserInfo().getName().equals("")) {
            ewm_name.setText(UserManager.getUserInfo().getUsername());
            if (UserManager.getUserInfo().getUsername().equals("")) {
                ewm_name.setText(UserManager.getUserInfo().getCard() + "");
            }
        } else {
            ewm_name.setText(UserManager.getUserInfo().getName());
        }

        ewm_id.setText("ID:" + UserManager.getUserInfo().getCard());
        Glide.with(this).load(UserManager.getUserInfo().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)
                .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(this)).into(ewm_icon);

        // ewm_icon.setImageBitmap();
        String sex = UserManager.getUserInfo().getSex() + "";
        if (sex.equals("0")) {
            ewm_sex.setImageResource(R.drawable.nv);
        } else if (sex.equals("1")) {
            ewm_sex.setImageResource(R.drawable.nan);
        }
        Glide.with(this).load(UserManager.getUserInfo().getTwoDimension()).into(ewm_ewm);
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
                Uri imageUri = FileProvider.getUriForFile(PersonalInfoActivity.this, "com.jph.takephoto.fileprovider", temp);//通过FileProvider创建一个content类型的Uri
                startPhotoZoom(imageUri);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(PersonalInfoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
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
            byte[] bytes = baos.toByteArray();
            Drawable drawable = new BitmapDrawable(null, bitmap);
            String pathNow = FileUtils.saveFile(this, "temphead1.png", bitmap);
            mPhotoList.get(0).setPhotoPath(pathNow);
            GlideUtil.loadCircleAvatar(this, pathNow, ivPersonalAvatar);
            uploadImg();
        }
    }

    private void uploadImg() {
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
                        doHttp(RetrofitUtils.createApi(LeftUrl.class).personinformation(phone, secret, phone, mPicUrls,
                                null, null, null,
                                null, null,
                                UserManager.getUserInfo().getUserId(), null), 1);


                    }
                });
            }
        });
    }


}
