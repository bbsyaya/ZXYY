package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.ChooseIconAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.PhotoTheme;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.view.MyGridView;

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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_AMEND_INFO_DYNAMIC;

/**
 * 发布 和 举报医生 界面
 */
public class PublishActivity extends BaseAty implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener,TextWatcher {
    public static final String EXTRA_IS_PUBLISH_OR_APPEAL = "extra_is_publish_or_appeal";
    public static final String EXTRA_DOCTOR_ID = "dctorId";
    private final int REQUEST_CODE_GALLERY = 1001;

    @Bind(R.id.et_problem)
    EditText etProblem;
    @Bind(R.id.gv_pic)
    MyGridView gvPic;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_problem_tijiao)
    Button btnCommit;
    private List<PhotoInfo> mPhotoList = new ArrayList<>();
    private ChooseIconAdapter chooseIconAdapter;
    // 0 发布 1举报
    private int mTag = 0;
    private String mDoctorId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userId = UserManager.getUserInfo().getUserId();
        mTag = getIntent().getIntExtra(EXTRA_IS_PUBLISH_OR_APPEAL, 0);
        if (mTag == 0) {
            tvTitle.setText(R.string.fabudongtai);
        } else if (mTag == 1) {
            tvTitle.setText(R.string.jubao);
        }
        userId = UserManager.getUserInfo().getUserId();

        mDoctorId = getIntent().getStringExtra(EXTRA_DOCTOR_ID);

        chooseIconAdapter = new ChooseIconAdapter(this);
        gvPic.setAdapter(chooseIconAdapter);
        PhotoTheme.settingtheme(this, mPhotoList);
        gvPic.setOnItemClickListener(this);
        gvPic.setOnItemLongClickListener(this);
        etProblem.addTextChangedListener(this);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        JsonResponse jsonResponse = JSON.parseObject(result, JsonResponse.class);
        showToast(jsonResponse.getRetMessage());
        switch (what) {
            case HttpIdentifier.APPEAL_DOCTOR://举报医生
                showToast(getString(R.string.jubaochenggong));
                break;
            case HttpIdentifier.PUBLISH_DYNAMIC:
                IS_AMEND_INFO_DYNAMIC = true;
                break;
        }
        this.finish();
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        try {
            JsonResponse jsonResponse = JSON.parseObject(result, JsonResponse.class);
            showToast(jsonResponse.getRetMessage());
        } catch (Exception e) {

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
                        showToast(getString(R.string.shangchuantupianshibai));
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
                        //{"result":"http://192.168.42.216:8066/baobei/api/ceshi/2016-12/d9dd132bb7eb4dab97bed5f747035dc6.jpg,http://192.168.42.216:8066/baobei/api/ceshi/2016-12/110b11f1a0de5403888c97c9d879ba15b.jpg"}
                        try {
                            JsonResponse jsonResponse = JSON.parseObject(body, JsonResponse.class);
                            String mPicUrls = jsonResponse.getResult();
                            uplodingData(mPicUrls);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showToast("解析失败");
                        }
                    }
                });
            }
        });
    }


    @OnClick({R.id.btn_problem_tijiao, R.id.iv_title_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_problem_tijiao:

                try {
                    String content = etProblem.getText().toString().trim();
                    if (!"".equals(content)) {
                        List<PhotoInfo> photoList = chooseIconAdapter.getData();
                        int count = photoList.size();
                        if (count == 0) {
                            uplodingData("");
                        } else {
                            uploadImg(photoList);
                        }
                    } else {
                        showToast(getString(R.string.qingshuruneirong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(getString(R.string.qingxuanzetupian));
                    dismissLoadingDialog();
                }
                break;
            case R.id.iv_title_left:
                hideSoftKeyboard();
                this.finish();
                break;
        }

    }

    private void uplodingData(String mPicUrls) {
        String content = etProblem.getText().toString().trim();
        if ("".equals(content)) {
            showToast(getString(R.string.qingshuruneirong));
            return;
        }
        if (mTag == 0) {//发布
            doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).
                            publishDynamic(phone, secret, userId, content, mPicUrls),
                    HttpIdentifier.PUBLISH_DYNAMIC);
        } else if (mTag == 1) {//举报
            doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).
                            appealDoctor(phone, secret, userId, mDoctorId, content, mPicUrls),
                    HttpIdentifier.APPEAL_DOCTOR);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (0 == mPhotoList.size() ? true : i == mPhotoList.size()) {
            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, PhotoTheme.functionConfig, mOnHanlderResultCallback);
        }
    }

    /**
     * 多选图片
     */
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                PhotoTheme.settingtheme(PublishActivity.this, resultList);
                mPhotoList.clear();
                mPhotoList.addAll(resultList);
                chooseIconAdapter.setData(mPhotoList);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            showToast(errorMsg);
        }
    };

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i <= mPhotoList.size() - 1) {
            final int pos = i;
            new MaterialDialog(this)
                    .setMDNoTitle(true)
                    .setMDMessage(getString(R.string.shifoushanchugaitupian))
                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
                            chooseIconAdapter.deleteData(pos);
                        }
                    })
                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {

                        }
                    })
                    .show();
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!etProblem.getText().toString().trim().equals("")){
            btnCommit.setBackgroundResource(R.drawable.shape_main_color);
        }else{
            btnCommit.setBackgroundResource(R.drawable.shape_gray_color);
        }
    }
}
