package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

public class WarningDoctorActivity extends BaseAty {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.fun_pb)
    ProgressBar fun_pb;

    @Override
    public int getLayoutId() {
        return R.layout.activity_warning_doctor;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText(R.string.yishijinggao);

        /**
         * 设置WebView的属性，此时可以去执行JavaScript脚本
         */
        fun_pb.setProgress(0);
        fun_pb.setMax(100);


        webView.loadUrl("http://www.zhixinyisheng.com/zxjg");
        webView.getSettings().setJavaScriptEnabled(true);

        // 添加监听---检测网页的加载过程
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                try {
                    fun_pb.setProgress(newProgress);
                    if (fun_pb.getMax() == newProgress) {
                        fun_pb.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @OnClick(R.id.iv_title_left)
    public void onClick() {
        this.finish();
    }
}
