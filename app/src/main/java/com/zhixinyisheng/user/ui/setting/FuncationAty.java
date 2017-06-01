package com.zhixinyisheng.user.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 功能介绍
 * Created by gjj on 2016/11/12.
 */
public class FuncationAty extends BaseAty {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.cjs_tvt)
    TextView cjs_tvt;
    @Bind(R.id.web_funcation)
    WebView web_funcation;
    @Bind(R.id.fun_pb)
    ProgressBar fun_pb;

    private String weburl="http://www.zhixinyisheng.com/enappgnjs/";

    @Override
    public int getLayoutId() {
        return R.layout.funcationaty;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        super.initData(savedInstanceState);
        iv_back.setVisibility(View.VISIBLE);
        cjs_tvt.setText(R.string.gongnengjieshao);
//            fun_pb.setProgress(0);
//            fun_pb.setMax(100);
        if (LanguageUtil.judgeLanguage().equals("zh")) {
            weburl = "http://www.zhixinyisheng.com/appgnjs/";
        } else {
            weburl = "http://www.zhixinyisheng.com/enappgnjs/";
        }
        initview();
        if (null != savedInstanceState) {
            web_funcation.restoreState(savedInstanceState);
        } else {
            web_funcation.loadUrl(weburl);
        }

//            // 添加监听---检测网页的加载过程
//            web_funcation.setWebChromeClient(new WebChromeClient() {
//                @Override
//                public void onProgressChanged(WebView view, int newProgress) {
//                    super.onProgressChanged(view, newProgress);
//                    try {
//                        fun_pb.setProgress(newProgress);
//                        if (fun_pb.getMax() == newProgress) {
//                            fun_pb.setVisibility(View.GONE);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();  `
//                    }
//
//                }
//
//            });


    }


    private void initview() {
        web_funcation.getSettings().setJavaScriptEnabled(true);
//        web_funcation.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web_funcation.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                web_funcation.loadUrl(weburl);
                return true;
            }
        });
    }

    @OnClick(R.id.iv_back)
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web_funcation.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            web_funcation.stopLoading();
            web_funcation.removeAllViews();
            web_funcation.destroy();
            web_funcation = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}




