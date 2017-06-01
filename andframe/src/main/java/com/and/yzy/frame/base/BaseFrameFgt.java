package com.and.yzy.frame.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.R;
import com.and.yzy.frame.http.HttpCallBack;
import com.and.yzy.frame.util.DensityUtils;
import com.and.yzy.frame.util.NetWorkUtils;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.net.URLDecoder;
import java.nio.charset.Charset;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * fragment基类，实现懒加载,显示进度条的时候尽量放在onActivityCreated
 */
public abstract class BaseFrameFgt extends Fragment implements HttpCallBack {


    /**
     * 请求网络对话弹窗
     */
    private LoadingDialog mLoadingDialog;

    private LoadingDialog mLoadingHYDDialog;
    /**
     * 请求网络全屏对话弹窗
     */
    private LinearLayout mLoadingContent;

    /**
     * 无网络全屏对话弹窗
     */
    private View errorView;
    /**
     * 容器布局
     */
    private FrameLayout content;

    private boolean isPrepared;//剧京改过,一开始默认值是false

    private LayoutInflater mInflater;

    /**
     * 是否初始化请求网络数据
     */
    protected boolean isInitRequestData = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View fgtView = inflater.inflate(R.layout.frame_base_layout, container, false);
        content = (FrameLayout) fgtView.findViewById(R.id.content);

        View child = inflater.inflate(getLayoutId(), null, false);
        if (child.getParent() != content) {
            content.addView(child);
        }
        ButterKnife.bind(this, fgtView);
        return fgtView;
    }

    /**
     * 布局文件ID
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * view的点击事件
     */
    public void btnClick(View view) {
    }

    ;


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

            errorView = mInflater.inflate(R.layout.frame_error_layout, null, false);
            errorView.setClickable(true);
            content.addView(errorView);
            Button btn_error = (Button) errorView.findViewById(R.id.btn_resh);
            btn_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断网络是否可用
                    if (NetWorkUtils.isNetworkConnected(getActivity())) {

                        initData();
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
    public void showToast(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示进度对话条
     */
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
//            mLoadingDialog.setContentView(R.layout.frame_loading_dialog);
        }
        mLoadingDialog.showLoadingDialog(message);
    }

    /**
     * 显示化验单进度对话条
     */
    public void showLoadingHYDDialog(String message) {
        if (mLoadingHYDDialog == null) {
            mLoadingHYDDialog = new LoadingDialog(getActivity());
//            mLoadingDialog.setContentView(R.layout.frame_loading_dialog);
        }
        mLoadingHYDDialog.showLoadingDialog(message);
    }

    /**
     * 显示全屏遮盖的进度条
     *
     * @param spacingTop    距离顶部的距离dp
     * @param spacingBottom 距离底部的距离dp
     */
    public void showLoadingContentDialog(int spacingTop, int spacingBottom) {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);

        } else {
            mLoadingContent = (LinearLayout) mInflater.inflate(R.layout.frame_loading_fgt_content_dialog, null, false);

            if (spacingTop >= 0 && spacingBottom >= 0) {

                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(getActivity(), spacingTop);
                params.bottomMargin = DensityUtils.dp2px(getActivity(), spacingBottom);
                spacingView.setLayoutParams(params);

            } else if (spacingTop >= 0 && spacingBottom < 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(getActivity(), spacingTop);
                spacingView.setLayoutParams(params);

            } else if (spacingTop < 0 && spacingBottom >= 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.bottomMargin = DensityUtils.dp2px(getActivity(), spacingBottom);
                spacingView.setLayoutParams(params);
            }
            content.addView(mLoadingContent);
        }


    }

    /**
     * 显示全屏遮盖的进度条
     */
    public void showLoadingContentDialog() {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);
        } else {
            mLoadingContent = (LinearLayout) mInflater.inflate(R.layout.frame_loading_fgt_content_dialog, null, false);
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

    /**
     * 隐藏化验单进度条
     */
    public void dismissLoadingHYDDialog() {
        if (mLoadingHYDDialog != null) {
            //结束掉网络请求
            mLoadingHYDDialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        ButterKnife.unbind(this);

    }

//================================================懒加载代码=======================================================

    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared) {//剧京改过
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {
        // 设置是否是初始化网络操作
        isInitRequestData = setIsInitRequestData();
        initData();

        initRequsetMethod();


    }

    /**
     * 初始化请求网络
     */
    public void initRequsetMethod() {
        if (isInitRequestData) {
            //判断网络是否可用
            if (NetWorkUtils.isNetworkConnected(getActivity())) {
                requestData();
            } else {
                requestData();
//                showNetWorkErrorPage();
            }
        }
    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 请求网络初始化数据
     */
    public void requestData() {
    }

    /**
     * 设置是否是初始化网络
     *
     * @return
     */
    public abstract boolean setIsInitRequestData();

    /**
     * fragment可见（切换回来或者onResume）
     */
    public abstract void onUserVisible();


    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstUserInvisible() {

    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {


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
    public void startActivity(Class<?> className, Bundle options) {
        Intent intent = new Intent(getActivity(), className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivity(intent);
        if (hasAnimiation) {
//            getActivity().overridePendingTransition(R.anim.slide_right_in,
//                    R.anim.slide_left_out);
        }
    }
    public void activityAnimation(){
//        getActivity().overridePendingTransition(R.anim.slide_right_in,
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
        Intent intent = new Intent(getActivity(), className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivityForResult(intent, requestCode);
        if (hasAnimiation) {
//            getActivity().overridePendingTransition(R.anim.slide_right_in,
//                    R.anim.slide_left_out);

        }
    }


    //==============================================网络相关===================================================
    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        hideLoadingDialog();
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        hideLoadingDialog();
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        hideLoadingDialog();
//        showToast("连接失败");
//        CrashHandler.getInstance().saveCrashInfo2File(t, SavePath.savePath + "yzyHttpError/crash/");
    }


    public void doHttp(Call<ResponseBody> bodyCall, final int what) {

        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Request request = call.request();
//                if (request.body() == null) {
//                    Logger.w("HttpUrl", "HttpUrl---->" + request.url().toString());
//                } else {
//                    Logger.w("HttpUrl", "HttpUrl---->" + call.request().url().toString() +
//                            '\n' + getRequestFrom(request)
//                    );
//                }
                hideLoadingDialog();

                try {

                    String result = response.body().string();
                    Logger.w("HttpResult", "HttpResultSuccess-->" + result);
                    JSONObject object = JSONObject.parseObject(result);
                    if (object.getString("result").equals("0000")) {
                        BaseFrameFgt.this.onSuccess(result, call, response, what);
                    } else if (object.getString("result").equals("9993")) {
                        BaseFrameFgt.this.onFailure(result, call, response, what);
                    } else {
                        BaseFrameFgt.this.onFailure(object.getString("result"), call, response, what);
                    }
                } catch (Exception e) {
//                    showToast(getResources().getString(R.string.qingjianchawangluolianjie));
                    Logger.e("HttpIOException", "HttpIOException-->" + e.getMessage());

                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoadingDialog();

                BaseFrameFgt.this.onError(call, t, what);
                Logger.e("HttpFailure", "HttpFailure--->" + t.getMessage());

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
//            Log.w("MediaType","MediaType-->"+contentType.toString()+"----charset--->"+charset);
            if (contentType != null) {
                charset = contentType.charset(charset);
//                Log.w("charset","----charset--->"+charset);
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