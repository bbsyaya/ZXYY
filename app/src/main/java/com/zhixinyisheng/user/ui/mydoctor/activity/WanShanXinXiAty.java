package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.PhotoTheme;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.http.DoctorUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.pay.JobChoiseAty;
import com.zhixinyisheng.user.ui.pay.PersonalizedChargeAty;

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
 * 完善医生信息
 * Created by 焕焕 on 2016/12/27.
 */
public class WanShanXinXiAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.et_wanshan_name)
    EditText etWanshanName;
    @Bind(R.id.et_wanshan_shoujihao)
    EditText etWanshanShoujihao;
    @Bind(R.id.et_wanshan_shoujishao)
    TextView etWanshanShoujishao;
    @Bind(R.id.et_wanshan_shanchangjibing)
    EditText etWanshanShanchangjibing;
    @Bind(R.id.et_wanshan_weixinhao)
    EditText etWanshanWeixinhao;
    @Bind(R.id.et_wanshan_qqhao)
    EditText etWanshanQqhao;
    @Bind(R.id.et_wanshan_yiyuandizhi)
    EditText etWanshanYiyuandizhi;
    @Bind(R.id.et_wanshan_hospital)
    TextView etWanshanHospital;
    @Bind(R.id.et_wanshan_xzks)
    TextView et_wanshan_xzks;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
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
    @Bind(R.id.iv_ws_avatar)
    ImageView ivWsAvatar;
    @Bind(R.id.rl_touxiang)
    RelativeLayout rlTouxiang;
    @Bind(R.id.tv_xingming)
    TextView tvXingming;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.et_wanshan_username)
    EditText etWanshanUsername;
    @Bind(R.id.tv_ws_sex)
    TextView tvWsSex;
    @Bind(R.id.img_sex_man)
    ImageView imgSexMan;
    @Bind(R.id.img_sex_man2)
    ImageView imgSexMan2;
    @Bind(R.id.rl_man)
    RelativeLayout rlMan;
    @Bind(R.id.sex_nan)
    TextView sexNan;
    @Bind(R.id.rl_sex_man)
    RelativeLayout rlSexMan;
    @Bind(R.id.img_sex_woman)
    ImageView imgSexWoman;
    @Bind(R.id.img_sex_woman2)
    ImageView imgSexWoman2;
    @Bind(R.id.rl_woman)
    RelativeLayout rlWoman;
    @Bind(R.id.rl_sex_woman)
    RelativeLayout rlSexWoman;
    @Bind(R.id.tv_nianling)
    TextView tvNianling;
    @Bind(R.id.et_editor_age)
    EditText etEditorAge;
    @Bind(R.id.tv_shoujihao)
    TextView tvShoujihao;
    @Bind(R.id.tv_suozaiyiyuan)
    TextView tvSuozaiyiyuan;
    @Bind(R.id.iv_yiyuan_xiangyou)
    ImageView ivYiyuanXiangyou;
    @Bind(R.id.rl_ws_szyy)
    RelativeLayout rlWsSzyy;
    @Bind(R.id.tv_suozaikeshi)
    TextView tvSuozaikeshi;
    @Bind(R.id.iv_keshi_xiangyou)
    ImageView ivKeshiXiangyou;
    @Bind(R.id.rl_ws_xzks)
    RelativeLayout rlWsXzks;
    @Bind(R.id.tv_zhicheng)
    TextView tvZhicheng;
    @Bind(R.id.iv_zhicheng_xiangyou)
    ImageView ivZhichengXiangyou;
    @Bind(R.id.rl_ws_zhicheng)
    RelativeLayout rlWsZhicheng;
    @Bind(R.id.shanchangjibing)
    TextView shanchangjibing;
    @Bind(R.id.tv_xiongpai)
    TextView tvXiongpai;
    @Bind(R.id.rl_ws_sczp)
    RelativeLayout rlWsSczp;
    @Bind(R.id.et_ws_gexingqianming)
    EditText etWsGexingqianming;
    private final int requestCode_jobChoice = 0;
    private final int requestCode_hospital = 1;
    private final int requestCode_sectionChoice = 2;
    private final int requestCode_certification = 3;
    private String hospitalId, sectionId;
    private int grade;//职称的等级
    private String name, doctorPhone, job, disease, weChat, qq, hospitalAddress, section, cardPic;
    private String username,sex,age,remark;
    private List<PhotoInfo> mPhotoList;
    private final int REQUEST_CODE_CAMERA = 2220;
    private final int REQUEST_CODE_GALLERY = 2221;
    private static final int REQUESTCODE_CUTTING = 4022; // 图片裁切标记
    private String mPicUrls;//头像地址
    @Override
    public int getLayoutId() {
        return R.layout.aty_wanshanxinxi;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPhotoList = new ArrayList<>();
        PhotoTheme.settingtheme(this,mPhotoList);
        cjsTvt.setText("个人信息");
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText("完成");
        if (UserManager.getUserInfo().getSection().equals("") || null == UserManager.getUserInfo().getSection()) {
            btnNext.setVisibility(View.VISIBLE);
            titleBtn.setVisibility(View.GONE);
            cjsTvt.setText("完善信息");
        }
        etWanshanName.setText(UserManager.getUserInfo().getName());
        etWanshanShoujihao.setText(UserManager.getUserInfo().getDoctorPhone());
        etWanshanHospital.setText(UserManager.getUserInfo().getHospital());
        //以后要补全科室信息
        et_wanshan_xzks.setText(UserManager.getUserInfo().getSection());
        etWanshanShoujishao.setText(UserManager.getUserInfo().getJob());
        etWanshanShanchangjibing.setText(UserManager.getUserInfo().getDisease());
        etWanshanWeixinhao.setText(UserManager.getUserInfo().getWebchat());
        //以后要补全QQ
        etWanshanQqhao.setText(UserManager.getUserInfo().getQq());
        etWanshanYiyuandizhi.setText(UserManager.getUserInfo().getHospitalAddress());
        cardPic = UserManager.getUserInfo().getCardPic();
        //后来加上的信息（与个人信息合并）
        Glide.with(this).load(UserManager.getUserInfo().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)
                .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(this)).into(ivWsAvatar);
        etWanshanUsername.setText(UserManager.getUserInfo().getUsername());
        if(UserManager.getUserInfo().getSex()==1){
            imgSexMan2.setVisibility(View.VISIBLE);
            sex="1";
        }else {
            imgSexWoman2.setVisibility(View.VISIBLE);
            sex="0";
        }
        etEditorAge.setText(UserManager.getUserInfo().getAge()+"");
        etWsGexingqianming.setText(UserManager.getUserInfo().getRemark());
        mPicUrls = UserManager.getUserInfo().getHeadUrl();
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.DOCTOR_INFO:
                Logger.e("DOCTOR INFO", result);
                showToast("设置成功");
                UserInfo userInfo = UserManager.getUserInfo();
                userInfo.setName(name);
                userInfo.setDoctorPhone(doctorPhone);
                userInfo.setHospital(etWanshanHospital.getText().toString());
                userInfo.setHospitalId(hospitalId);
                userInfo.setJob(job);
                userInfo.setDisease(disease);
                userInfo.setWebchat(weChat);
                userInfo.setHospitalAddress(hospitalAddress);
                userInfo.setGrade(grade);
                userInfo.setSection(section);//以后再补上
                userInfo.setSectionId(sectionId);
                userInfo.setCardPic(cardPic);
                userInfo.setQq(qq);//以后补上
                //新加的
                userInfo.setUsername(username);
                userInfo.setSex(Integer.parseInt(sex));
                userInfo.setAge(Integer.parseInt(age));
                userInfo.setRemark(remark);
                userInfo.setHeadUrl(mPicUrls);

                UserManager.setUserInfo(userInfo);
                if (btnNext.getVisibility() == View.VISIBLE) {
                    startActivity(PersonalizedChargeAty.class, null);
                }
                finish();
                break;
        }

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what) {
            case HttpIdentifier.DOCTOR_INFO:
                showToast("请您将必传信息补充完整！");
                break;
        }
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
                Uri imageUri = FileProvider.getUriForFile(WanShanXinXiAty.this, "com.jph.takephoto.fileprovider", temp);//通过FileProvider创建一个content类型的Uri
                startPhotoZoom(imageUri);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(WanShanXinXiAty.this, errorMsg, Toast.LENGTH_SHORT).show();
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
    @OnClick({R.id.rl_sex_man,R.id.rl_sex_woman,R.id.rl_touxiang,R.id.btn_next, R.id.rl_ws_zhicheng, R.id.rl_ws_sczp, R.id.cjs_rlb, R.id.title_btn, R.id.rl_ws_xzks, R.id.rl_ws_szyy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_touxiang://修改头像
                mPhotoList.clear();
                choosePic();
                break;
            case R.id.rl_sex_man:
                imgSexMan2.setVisibility(View.VISIBLE);
                imgSexWoman2.setVisibility(View.GONE);
                sex = "1";
                break;
            case R.id.rl_sex_woman:
                imgSexWoman2.setVisibility(View.VISIBLE);
                imgSexMan2.setVisibility(View.GONE);
                sex = "0";
                break;
            case R.id.rl_ws_zhicheng://选择职称（确认订单）
                startActivityForResult(JobChoiseAty.class, null, requestCode_jobChoice);

                break;
            case R.id.rl_ws_sczp://上传胸牌或者身份证照片
                Bundle bundle = new Bundle();
                bundle.putString("cardPic",cardPic);
                startActivityForResult(CertificationAty.class, bundle, requestCode_certification);
                break;
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
            case R.id.btn_next://下一步
            case R.id.title_btn:
                HttpIdentifier.sIsSaveInfo=true;
                name = etWanshanName.getText().toString();
                doctorPhone = etWanshanShoujihao.getText().toString();
                job = etWanshanShoujishao.getText().toString();
                disease = etWanshanShanchangjibing.getText().toString();
                weChat = etWanshanWeixinhao.getText().toString();
                qq = etWanshanQqhao.getText().toString();
                hospitalAddress = etWanshanYiyuandizhi.getText().toString();
                section = et_wanshan_xzks.getText().toString();
                username = etWanshanUsername.getText().toString();
                age = etEditorAge.getText().toString();
                remark = etWsGexingqianming.getText().toString();
                if (hospitalId == null) {
                    hospitalId = UserManager.getUserInfo().getHospitalId();
                }
                if (grade == 0) {
                    grade = UserManager.getUserInfo().getGrade();
                }
                if (sectionId == null) {
                    sectionId = UserManager.getUserInfo().getSectionId();
                }
                String province,city;
                if (UserManager.getUserInfo().getProvince().equals("")||null==UserManager.getUserInfo().getProvince()){
                    province="河北省";
                    city = "石家庄市";
                }else{
                    province = UserManager.getUserInfo().getProvince();
                    city = UserManager.getUserInfo().getCity();
                }

                try {
                    uploadImg();
                } catch (Exception e) {
                    e.printStackTrace();
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DoctorUrl.class).doctorInfo(phone, secret, userId, name, doctorPhone,
                            province,
                            city,
                            hospitalId,
                            etWanshanHospital.getText().toString(),
                            sectionId,
                            section,
                            job,
                            grade,
                            disease,
                            cardPic,
                            weChat,
                            qq,
                            hospitalAddress,username,sex,age,remark,mPicUrls), HttpIdentifier.DOCTOR_INFO);
                }
                break;
            case R.id.rl_ws_xzks://选择科室  （先给购买服务开个口）
                startActivityForResult(SectionChoiceAty.class, null, requestCode_sectionChoice);
                break;
            case R.id.rl_ws_szyy://所在医院选择界面
                startActivityForResult(HospitalChoiceAty.class, null, requestCode_hospital);
                break;
        }

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
                showToast("裁剪失败，请重新选择图片！");
                e.printStackTrace();
                return;
            }
            byte[] bytes=baos.toByteArray();
            Drawable drawable = new BitmapDrawable(null, bitmap);
            String pathNow= FileUtils.saveFile(this, "temphead1.png", bitmap);
            mPhotoList.get(0).setPhotoPath(pathNow);
            Glide.with(this).load(bytes)
                    .placeholder(R.mipmap.ic_launcher2)
                    .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(this)).into(ivWsAvatar);

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
                        showToast("上传图片失败");
                        hideLoadingDialog();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                final String body = response.body().string();
//                System.out.println("上传照片成功：response = " + body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        showToast("上传图片成功");
                        hideLoadingDialog();
                        JsonResponse jsonResponse = JSON.parseObject(body, JsonResponse.class);
                        mPicUrls = jsonResponse.getResult();


                        doHttp(RetrofitUtils.createApi(DoctorUrl.class).doctorInfo(phone, secret, userId, name, doctorPhone,
                                UserManager.getUserInfo().getProvince(),
                                UserManager.getUserInfo().getCity(),
                                hospitalId,
                                etWanshanHospital.getText().toString(),
                                sectionId,
                                section,
                                job,
                                grade,
                                disease,
                                cardPic,
                                weChat,
                                qq,
                                hospitalAddress,username,sex,age,remark,mPicUrls), HttpIdentifier.DOCTOR_INFO);


                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case requestCode_jobChoice:
                String getData = "";
                try {
                    getData = data.getStringExtra("job");
                    etWanshanShoujishao.setText(getData);
                    grade = data.getIntExtra("grade", -1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case requestCode_hospital:
                try {
                    etWanshanHospital.setText(data.getStringExtra("hospitalName"));
                    hospitalId = data.getStringExtra("hospitalId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case requestCode_sectionChoice:
                try {
                    et_wanshan_xzks.setText(data.getStringExtra("section"));
                    sectionId = data.getStringExtra("sectionId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case requestCode_certification:
                try {
                    cardPic = data.getStringExtra("cardPic");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;

        }
    }

}
