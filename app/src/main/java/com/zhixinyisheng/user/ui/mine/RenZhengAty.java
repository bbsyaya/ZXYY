package com.zhixinyisheng.user.ui.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.config.SavePath;
import com.and.yzy.frame.util.FileUtils;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.FormBotomDialogBuilder;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.PhotoTheme;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.RenZhengInfo;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.http.LeftUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.UpLodeUtilofHeadImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 医生认证
 * Created by 焕焕 on 2016/10/18.
 */
public class RenZhengAty extends BaseAty {
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;

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
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.doctor_et_number)
    EditText doctorEtNumber;
    @Bind(R.id.renzheng_rl_number)
    RelativeLayout renzhengRlNumber;
    @Bind(R.id.tv_shilitupian)
    TextView tvShilitupian;
    @Bind(R.id.rl_shilitupian)
    RelativeLayout rlShilitupian;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.iv_shangchuan1)
    ImageView ivShangchuan1;
    @Bind(R.id.iv_shangchuan2)
    ImageView ivShangchuan2;
    @Bind(R.id.rl_touming)
    RelativeLayout rlTouming;
    @Bind(R.id.btn_close)
    Button btnClose;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.iv_shili)
    ImageView iv_shili;
    @Bind(R.id.ll_renzhen_main)
    RelativeLayout ll_renzhen_main;
    @Bind(R.id.ll_xieyi)
    LinearLayout ll_xieyi;
    @Bind(R.id.btn_commit)
    Button btn_commit;
    @Bind(R.id.iv_read)
    ImageView iv_read;
    @Bind(R.id.tv_xieyi)
    TextView tvXieYi;
    private static String IMAGE_FILE_NAME = SavePath.savePath + "Image01.png";// 头像文件名称
    private static String IMAGE_FILE_NAME_DOWN = "Image02.png";// 头像文件名称
    private String flagClick = "-1";//判断是点了哪张图片
    ThemeConfig themeConfig = null;
    private List<PhotoInfo> mPhotoList;
    private String urlpath1 = "", urlpath2 = "";
    private String imgUrl = HttpConfig.BASE_URL + "upload/user";
    private UserInfo userinfo;
    LoadingDialog mLoadingDialog;
    int flagPhoto1 = 0;
    int flagPhoto2 = 0;
    String renZhengPath1, renZhengPath2;//认证图片的本地文件地址
    String[] images;
    RenZhengInfo renZhengInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_renzheng;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(LeftUrl.class).doctorNews(userId, phone, secret), 2);
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mPhotoList = new ArrayList<>();
        cjsTvt.setText(getResources().getString(R.string.left_doctorCertification));
        titleBtn.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);

        PhotoTheme.settingtheme(this, mPhotoList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null!=renZhengInfo){
            if (renZhengInfo.getDb().getAttpStates() == 0 || renZhengInfo.getDb().getAttpStates() == 3) {//未认证或审核失败
                ivShangchuan1.setEnabled(true);
                ivShangchuan2.setEnabled(true);
                btn_commit.setEnabled(true);
                doctorEtNumber.setEnabled(true);
                tvXieYi.setEnabled(true);
            }else {
                ivShangchuan1.setEnabled(true);
                ivShangchuan2.setEnabled(true);
                doctorEtNumber.setEnabled(false);
                tvXieYi.setEnabled(true);
            }
        }
        if (Content.READ_XIEYI == 0) {
            iv_read.setImageResource(R.drawable.ct_btn_selected_n);
        } else {
            iv_read.setImageResource(R.drawable.ct_btn_selected_s);

        }
    }

    @OnClick({R.id.tv_xieyi, R.id.btn_commit, R.id.ll_renzhen_main, R.id.btn_close, R.id.btn_send, R.id.iv_shangchuan1, R.id.iv_shangchuan2, R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_xieyi:
                startActivity(RenZhengXieYiAty.class, null);
                break;
            case R.id.btn_commit:
                if (Content.READ_XIEYI == 0) {
                    showToast(getResources().getString(R.string.qingyueduyishi));
                    return;
                }
                if (doctorEtNumber.getText().toString().equals("") || urlpath1.equals("") || urlpath2.equals("")) {
                    showToast(getResources().getString(R.string.qingshuruzhengque));
                } else {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(LeftUrl.class).renZheng(phone, secret, userId, doctorEtNumber.getText().toString(),
                            urlpath1 + "," + urlpath2), 0);
                }
                break;
            case R.id.btn_close:
                ivShangchuan1.setEnabled(true);
                ivShangchuan2.setEnabled(true);
                btn_commit.setEnabled(true);
                doctorEtNumber.setEnabled(true);
                tvXieYi.setEnabled(true);
                ll_renzhen_main.setVisibility(View.VISIBLE);
                rlTouming.setVisibility(View.GONE);
                break;
            case R.id.btn_send:
                ivShangchuan1.setEnabled(true);
                ivShangchuan2.setEnabled(true);
                btn_commit.setEnabled(true);
                doctorEtNumber.setEnabled(true);
                tvXieYi.setEnabled(true);
                choosePic();
                break;
            case R.id.iv_shangchuan1:
                ivShangchuan1.setEnabled(false);
                ivShangchuan2.setEnabled(false);
                btn_commit.setEnabled(false);
                doctorEtNumber.setEnabled(false);
                tvXieYi.setEnabled(false);

                if (images != null) {
                    Intent intent = new Intent(RenZhengAty.this, RenZhengImageDetailAty.class);
                    intent.putExtra("images", images[0]);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    iv_shili.setImageResource(R.drawable.large_shili1);
                    mPhotoList.clear();
                    flagClick = "1";
                    rlTouming.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_shangchuan2:
                ivShangchuan1.setEnabled(false);
                ivShangchuan2.setEnabled(false);
                btn_commit.setEnabled(false);
                doctorEtNumber.setEnabled(false);
                tvXieYi.setEnabled(false);
                if (images != null) {
                    Intent intent = new Intent(RenZhengAty.this, RenZhengImageDetailAty.class);
                    intent.putExtra("images", images[1]);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    iv_shili.setImageResource(R.drawable.large_shili2);
                    mPhotoList.clear();
                    flagClick = "2";
                    rlTouming.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_btn:
//                Log.e("postdata 158",doctorEtNumber.getText().toString()+"####"+urlpath1+","+urlpath2);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        if (what == 0) {
            showToast(getResources().getString(R.string.submitSuccess));
            finish();
//            showLoadingDialog(null);
//            doHttp(RetrofitUtils.createApi(LeftUrl.class).lookperson(userId), 1);
        }
//        else if (what == 1) {
//            userinfo = AppJsonUtil.getObject(result, UserInfo.class);
//            userinfo.setSecret(secret);
//            UserManager.setUserInfo(userinfo);
//            finish();
//        }

        else if (what == 2) {
            Logger.e("zhuangtai", result);
            renZhengInfo = JSON.parseObject(result, RenZhengInfo.class);
            if (renZhengInfo.getDb().getAttpStates() == 0 || renZhengInfo.getDb().getAttpStates() == 3) {//未认证或审核失败
                ivShangchuan1.setImageResource(R.drawable.img_shangchuan_05);
                ivShangchuan2.setImageResource(R.drawable.img_shangchuan_05);
                titleBtn.setText("");
            } else if (renZhengInfo.getDb().getAttpStates() == 1) {//审核中
                iv_read.setImageResource(R.drawable.ct_btn_selected_s);
//                ll_xieyi.setVisibility(View.GONE);
                btn_commit.setVisibility(View.GONE);
                titleBtn.setText(getResources().getString(R.string.renzhengzhong));
                titleBtn.setEnabled(false);
                doctorEtNumber.setEnabled(false);
                doctorEtNumber.setText(renZhengInfo.getDb().getAttp());
                images = renZhengInfo.getDb().getAttpUrl().split(",");
                mLoadingDialog.showLoadingDialog(getResources().getString(R.string.tupianjiezaizhong));
                Glide.with(this)
                        .load(images[0])
                        .into(new GlideDrawableImageViewTarget(ivShangchuan1) {
                            @Override
                            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                //在这里添加一些图片加载完成的操作
                                flagPhoto1 = 1;
                                if (flagPhoto1 == 1 && flagPhoto2 == 1) {
                                    mLoadingDialog.dismiss();
                                }
                            }
                        });
                Glide.with(this)
                        .load(images[1])
                        .into(new GlideDrawableImageViewTarget(ivShangchuan2) {
                            @Override
                            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                //在这里添加一些图片加载完成的操作
                                flagPhoto2 = 1;
                                if (flagPhoto1 == 1 && flagPhoto2 == 1) {
                                    mLoadingDialog.dismiss();
                                }
                            }
                        });
            } else if (renZhengInfo.getDb().getAttpStates() == 2) {//审核成功
                iv_read.setImageResource(R.drawable.ct_btn_selected_s);
                UserInfo userInfo = UserManager.getUserInfo();
                userInfo.setIsDoctor(1);
                UserManager.setUserInfo(userInfo);
//                ll_xieyi.setVisibility(View.GONE);
                btn_commit.setVisibility(View.GONE);
                titleBtn.setText(getResources().getString(R.string.shenhewancheng));
                titleBtn.setEnabled(false);
                doctorEtNumber.setEnabled(false);
                doctorEtNumber.setText(renZhengInfo.getDb().getAttp());
                images = renZhengInfo.getDb().getAttpUrl().split(",");
                mLoadingDialog.showLoadingDialog(getResources().getString(R.string.tupianjiezaizhong));
                Glide.with(this)
                        .load(images[0])
                        .into(new GlideDrawableImageViewTarget(ivShangchuan1) {
                            @Override
                            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                //在这里添加一些图片加载完成的操作
                                flagPhoto1 = 1;
                                if (flagPhoto1 == 1 && flagPhoto2 == 1) {
//                                Logger.e("wolaile1","wolaile");
                                    mLoadingDialog.dismiss();
                                }
                            }
                        });
                Glide.with(this)
                        .load(images[1])
                        .into(new GlideDrawableImageViewTarget(ivShangchuan2) {
                            @Override
                            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                //在这里添加一些图片加载完成的操作
                                flagPhoto2 = 1;
                                if (flagPhoto1 == 1 && flagPhoto2 == 1) {
//                                Logger.e("wolaile2","wolaile");
                                    mLoadingDialog.dismiss();
                                }
                            }
                        });

            }


        }


    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.addAll(resultList);
                if (flagClick.equals("1")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(mPhotoList.get(0).getPhotoPath());
                    renZhengPath1 = FileUtils.saveFile(RenZhengAty.this, "renzheng1.png", bitmap);


                    Glide.with(RenZhengAty.this).load(FileUtils.bitmapToBytes(bitmap)).into(ivShangchuan1);
                    showLoadingDialog(null);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            File file = new File(renZhengPath1);
                            urlpath1 = UpLodeUtilofHeadImage.uploadFile(file, imgUrl);
                            dismissLoadingDialog();
                        }
                    }).start();
                } else if (flagClick.equals("2")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(mPhotoList.get(0).getPhotoPath());
                    renZhengPath2 = FileUtils.saveFile(RenZhengAty.this, "renzheng2.png", bitmap);

                    Glide.with(RenZhengAty.this).load(FileUtils.bitmapToBytes(bitmap)).into(ivShangchuan2);
                    showLoadingDialog(null);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            File file = new File(renZhengPath2);
                            urlpath2 = UpLodeUtilofHeadImage.uploadFile(file, imgUrl);
                            dismissLoadingDialog();
                        }
                    }).start();
                }

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(RenZhengAty.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

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
                        rlTouming.setVisibility(View.GONE);
                        break;
                    case R.id.fbtn_two:
                        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, PhotoTheme.functionConfig, mOnHanlderResultCallback);
                        rlTouming.setVisibility(View.GONE);
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


}
