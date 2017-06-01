package com.and.yzy.frame.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.R;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.http.HttpCallBack;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.DensityUtils;
import com.and.yzy.frame.util.NetWorkUtils;
import com.and.yzy.frame.util.SPUtils;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Locale;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * activity的基类
 */
public abstract class BaseFrameAty extends AppCompatActivity implements HttpCallBack {


    /**
     * 请求网络对话弹窗
     */
    private LoadingDialog mLoadingDialog;
    /**
     * 请求网络全屏对话弹窗
     */
    private LinearLayout mLoadingContent;

    /**
     * 无网络全屏对话弹窗
     */
    private View errorView;
    /**
     * 系统容器布局
     */
    private FrameLayout content;

    /**
     * 是否初始化请求网络数据
     */
    protected boolean isInitRequestData = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManger.getInstance().addActivity(this);

        // 设置是否是初始化网络操作
        isInitRequestData = setIsInitRequestData();
        //设置为竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.frame_base_layout);
        content = (FrameLayout) findViewById(R.id.content);
        if (getLayoutId() != -1) {
            View childview = getLayoutInflater().inflate(getLayoutId(), null, false);
            if (childview.getParent() != content) {
                content.addView(childview);
            }
        }
        ButterKnife.bind(this);
        initData(savedInstanceState);
        initLang();
        initRequsetMethod();
    }
    protected void initLang() {
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        Locale locale = Locale.getDefault();
        String langStr = "";
        //TODO 获取Sharedpreferences中存储的app语言环境
        SPUtils spUtils = new SPUtils("language");
        langStr = (String) spUtils.get("language", "auto");
        if("zh".equals(langStr)){
            locale = Locale.CHINA;
        }else if("en".equals(langStr)){
            locale =  Locale.ENGLISH;
        }else if ("auto".equals(langStr)){
            locale = Locale.getDefault();//跟随系统
        }
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
    private  String judgeLanguage() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.contains("zh")){
            return "zh";
        }else if (language.contains("en")){
            return  "en";
        }else{
            return "";
        }
    }
    /**
     * 设置是否是初始化网络
     *
     * @return
     */
    public abstract boolean setIsInitRequestData();

    /**
     * 布局文件ID
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    public abstract void initData(Bundle savedInstanceState);

    /**
     * 请求网络初始化数据
     */
    public void requestData() {
    }

    ;

    /**
     * 初始化请求网络
     */
    public void initRequsetMethod() {
        if (isInitRequestData) {
            //判断网络是否可用
            if (NetWorkUtils.isNetworkConnected(this)) {
                requestData();
            } else {
                requestData();
//                showNetWorkErrorPage();
            }
        }

    }

    /**
     * view的点击事件
     */
    public abstract void btnClick(View view);


    //======================================进度条相关=========================================

    /**
     * 显示网络错误界面
     */
    private void showNetWorkErrorPage() {

        dismissLoadingContentDialog();

        if (errorView != null && errorView.getParent() == content) {
            content.removeView(errorView);
            content.addView(errorView);
        } else {

            errorView = getLayoutInflater().inflate(R.layout.frame_error_layout, null, false);
            errorView.setClickable(true);
            content.addView(errorView);
            Button btn_error = (Button) errorView.findViewById(R.id.btn_resh);
            btn_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断网络是否可用
                    if (NetWorkUtils.isNetworkConnected(BaseFrameAty.this)) {

                        initData(null);
                        hideNetWorkErrorPage();
                    } else {
                        showToast(getString(R.string.qingjianchawangluolianjie));
                    }
                }
            });
        }

    }

    /**
     * 隐藏网络错误界面
     */
    private void hideNetWorkErrorPage() {
        if (errorView != null && errorView.getParent() == content) {
            content.removeView(errorView);
        }

    }

    /**
     * 显示提示信息
     *
     * @param message
     */
    public static void showToast(String message) {

        Toast.makeText(BaseApplication.mContext, message, Toast.LENGTH_SHORT).show();

    }

    /**
     * 显示进度对话条
     */
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
//            mLoadingDialog.setContentView(R.layout.frame_loading_dialog);
        }
        mLoadingDialog.showLoadingDialog(message);
    }

    /**
     * 显示全屏遮盖的进度条
     *
     * @param spacingTop    距离顶部的距离单位dp
     * @param spacingBottom 距离底部的距离单位dp
     */
    public void showLoadingContentDialog(int spacingTop, int spacingBottom) {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);

        } else {
            mLoadingContent = (LinearLayout) getLayoutInflater().inflate(R.layout.frame_loading_content_dialog, null, false);

            if (spacingTop >= 0 && spacingBottom >= 0) {

                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(this, spacingTop);
                params.bottomMargin = DensityUtils.dp2px(this, spacingBottom);
                spacingView.setLayoutParams(params);

            } else if (spacingTop >= 0 && spacingBottom < 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(this, spacingTop);
                spacingView.setLayoutParams(params);

            } else if (spacingTop < 0 && spacingBottom >= 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.bottomMargin = DensityUtils.dp2px(this, spacingBottom);
                spacingView.setLayoutParams(params);
            }
            content.addView(mLoadingContent);
        }


    }

    /**
     * 显示全屏遮盖的进度条(toolbar可以显示出来默认56dp)
     */
    public void showLoadingContentDialog() {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);

        } else {
            mLoadingContent = (LinearLayout) getLayoutInflater().inflate(R.layout.frame_loading_content_dialog, null, false);
            content.addView(mLoadingContent);
        }


    }

    /**
     * 隐藏进度条
     */
    public void dismissLoadingContentDialog() {
        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
        }
    }

    /**
     * 隐藏进度条
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            //结束掉网络请求
            mLoadingDialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        ButterKnife.unbind(this);
        AppManger.getInstance().killActivity(this);
    }
    // ============================== 启动Activity ==============================

    public boolean isHasAnimiation() {
        return hasAnimiation;
    }

    public void setHasAnimiation(boolean hasAnimiation) {
        this.hasAnimiation = hasAnimiation;
    }

    private boolean hasAnimiation = true;

    /**
     * 启动一个Activity
     *
     * @param className 将要启动的Activity的类名
     * @param options   传到将要启动Activity的Bundle，不传时为null
     */
    public float startActivity(Class<?> className, Bundle options) {
        Intent intent = new Intent(this, className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivity(intent);
//        if (hasAnimiation) {
//            overridePendingTransition(R.anim.slide_right_in,
//                    R.anim.slide_left_out);
//        }
        return 0;
    }

    public void activityAnimation() {
//        overridePendingTransition(R.anim.slide_right_in,
//                R.anim.slide_left_out);
    }


    /**
     * 启动一个有会返回值的Activity
     *
     * @param className   将要启动的Activity的类名
     * @param options     传到将要启动Activity的Bundle，不传时为null
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> className, Bundle options,
                                       int requestCode) {
        Intent intent = new Intent(this, className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivityForResult(intent, requestCode);
//        if (hasAnimiation) {
//            overridePendingTransition(R.anim.slide_right_in,
//                    R.anim.slide_left_out);
//
//        }
    }

    @Override
    public void finish() {
        super.finish();
//        if (hasAnimiation) {
//            overridePendingTransition(R.anim.slide_left_in,
//                    R.anim.slide_right_out);
//        }
    }


    //==============================================网络相关===================================================
    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        hideLoadingDialog();
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        hideLoadingDialog();
//        CrashHandler.getInstance().saveCrashInfo2File(t, SavePath.savePath + "yzyHttpError/crash/");
    }


    public void doHttp(Call<ResponseBody> bodyCall, final int what) {

        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideLoadingDialog();
                Request request = call.request();
//                if (request.body() == null) {
//                    Logger.w("HttpUrl", "HttpUrl---->" + call.request().url().toString());
//                } else {
//                    Logger.w("HttpUrl", "HttpUrl---->" + call.request().url().toString() +
//                            '\n' + getRequestFrom(request)
//                    );
//                }
                try {
                    String result = response.body().string();
                    Logger.w("HttpResult", "HttpResultSuccess-->" + result);
                    JSONObject object = JSONObject.parseObject(result);
                    if (object.getString("result").equals("0000")) {
                        BaseFrameAty.this.onSuccess(result, call, response, what);
                    } else {
                        BaseFrameAty.this.onFailure(result, call, response, what);
                    }
                } catch (Exception e) {
                    hideLoadingDialog();

                    Logger.e("HttpIOException", "HttpIOException-->" + e.getMessage());
//                    showToast(getResources().getString(R.string.qingjianchawangluolianjie));

//                    BaseFrameAty.this.onFailure("网络异常", call, response, what);
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoadingDialog();
                BaseFrameAty.this.onError(call, t, what);
                Logger.e("HttpFailure", "HttpFailure--->" + t.getMessage());
                showToast(getString(R.string.fuwuqiyichang));
            }
        });

    }

    /**
     * 获取okhttp 的请求体
     *
     * @param request
     * @return
     */
    private String getRequestFrom(Request request) {
        RequestBody requestBody = request.body();
        okio.Buffer buffer = new okio.Buffer();
        try {
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");

            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String paramsStr = buffer.readString(charset);

            return URLDecoder.decode(paramsStr, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public void hideLoadingDialog() {
        dismissLoadingDialog();
        dismissLoadingContentDialog();
        hideNetWorkErrorPage();
    }

//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }


    /**
     * log日志
     *
     * @param str1
     * @param str2
     */
    public static void showLog(String str1, String str2) {

        Log.e(str1, str2);

    }


}
