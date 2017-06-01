package com.zhixinyisheng.user.ui.login;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.config.HttpConfig;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by 焕焕 on 2016/12/5.
 */
public class XieYiAty extends BaseAty {
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
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.xieyi_web)
    WebView xieyi;
    String webIcon;

    @Override
    public int getLayoutId() {
        return R.layout.aty_xieyi;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.yonghuxieyi);
        ivBack.setVisibility(View.VISIBLE);
        if (LanguageUtil.judgeLanguage().equals("zh")){
            webIcon = HttpConfig.BASE_URL+"xieyi.html";
        }else{
            webIcon = HttpConfig.BASE_URL+"xieyi_english.html";
        }

//        webIcon = "http://222.223.218.50:8166/zxys_user/api/xieyi.html";
        initview();
        if (null != savedInstanceState) {
            xieyi.restoreState(savedInstanceState);
        } else {
            xieyi.loadUrl(webIcon);
        }
    }

    private void initview() {
        xieyi.getSettings().setJavaScriptEnabled(true);
        xieyi.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        xieyi.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                xieyi.loadUrl(webIcon);
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        xieyi.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            xieyi.stopLoading();
            xieyi.removeAllViews();
            xieyi.destroy();
            xieyi = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void requestData() {

    }


    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        finish();
    }
}
