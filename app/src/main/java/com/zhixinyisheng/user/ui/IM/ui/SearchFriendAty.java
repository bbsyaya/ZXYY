package com.zhixinyisheng.user.ui.IM.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.util.MatchStingUtil;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.wxpay.Util;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.orhanobut.logger.Logger;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.ShareUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/10/31  15:02
 * <p>
 * 类说明: 添加好友的搜索界面
 */
public class SearchFriendAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.aty_addf_etss)
    EditText atyAddfEtss;
    @Bind(R.id.aty_addf_rlss)
    RelativeLayout atyAddfRlss;
    @Bind(R.id.aty_addf_tvhx)
    TextView atyAddfTvhx;
    @Bind(R.id.aty_addf_tvsjh)
    TextView atyAddfTvsjh;
    @Bind(R.id.aty_addf_rlssjg)
    LinearLayout atyAddfRlssjg;
    @Bind(R.id.aty_addf_llswkt)
    LinearLayout atyAddfLlswkt;
    @Bind(R.id.aty_addf_ivsc)
    ImageView atyAddfIvsc;
    @Bind(R.id.aty_addf_btn_fsyq)
    Button atyAddfBtnFsyq;

    BaseUiListener listener;
    Tencent mTencent;//QQ分享

    //对话框集合
    private ArrayList<DialogMenuItem> testItems = new ArrayList<DialogMenuItem>();

    String APP_ID = "wx54a16495034d3dd2";
    IWXAPI api;

    @Override
    public int getLayoutId() {
        return R.layout.aty_searchfriend;
    }

    @Override
    public void requestData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);


        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("1105772631", this.getApplicationContext());
        listener = new BaseUiListener();


        testItems.add(new DialogMenuItem("QQ"));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.weChat)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.duanxin)));

        cjsTvt.setText(getResources().getString(R.string.jiahaoyou));
        ivBack.setVisibility(View.VISIBLE);

        atyAddfEtss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String str_sjh = atyAddfEtss.getText().toString().trim();
                atyAddfTvsjh.setText(str_sjh);
                if (TextUtils.isEmpty(str_sjh)) {
                    atyAddfRlssjg.setVisibility(View.GONE);
                    atyAddfLlswkt.setVisibility(View.GONE);
                } else {
                    atyAddfRlssjg.setVisibility(View.VISIBLE);
                    atyAddfLlswkt.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick({R.id.iv_back, R.id.aty_addf_rlssjg, R.id.aty_addf_ivsc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.aty_addf_rlssjg:
                String str_sjh = atyAddfEtss.getText().toString().trim();
                if (MatchStingUtil.isMobile(str_sjh)){
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(IMUrl.class).findFrined(UserManager.getUserInfo().getPhone(),
                            UserManager.getUserInfo().getSecret(),userId,str_sjh,str_sjh, LanguageUtil.judgeLanguage()), 0);
                }else if (MatchStingUtil.isEmail(str_sjh)){
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(IMUrl.class).findFrined(UserManager.getUserInfo().getPhone(),
                            UserManager.getUserInfo().getSecret(),userId,str_sjh,str_sjh, LanguageUtil.judgeLanguage()), 0);
                }else{
                    showToast(getString(R.string.qingshuruzhengquexinxizhanghao));
                }




                break;
            case R.id.aty_addf_ivsc:
                atyAddfEtss.setText("");
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);

        Bundle bundle = new Bundle();
        bundle.putString("a", result);

        startActivity(AddFriendDetialAty.class, bundle);


    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);

        JSONObject object = JSONObject.parseObject(result);
        if (object.getString("result").equals("1020")) {

            atyAddfRlssjg.setVisibility(View.GONE);
            atyAddfLlswkt.setVisibility(View.VISIBLE);

        }else {
            showToast(object.getString("retMessage"));
        }


    }



    @OnClick(R.id.aty_addf_btn_fsyq)
    public void onClick() {

        NormalListDialogCustomAttr();

    }


    private void NormalListDialogCustomAttr() {
        final NormalListDialog dialog = new NormalListDialog(this, testItems);
        dialog.title(getResources().getString(R.string.qingxuanze))//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#25CDBC"))//
                .itemPressColor(Color.parseColor("#25CDBC"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show(R.style.myDialogAnim);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (testItems.get(position).getmOperName().equals("QQ")){
                    final Bundle params = new Bundle();
                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, getResources().getString(R.string.kuailaizhaowoba));
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  getResources().getString(R.string.baojiahuhang));
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  HttpConfig.SHARE_URL);
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,UserManager.getUserInfo().getHeadUrl());
                    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  getResources().getString(R.string.homeTitle));
                    mTencent.shareToQQ(SearchFriendAty.this, params, listener);
                }else if(testItems.get(position).getmOperName().equals(getResources().getString(R.string.weChat))){
                    ShareUtils.sendToWeiXin(SearchFriendAty.this, getResources().getString(R.string.kuailaizhaowoba), UserManager.getUserInfo().getHeadUrl());
//                    sendToWeiXin();
                }else if(testItems.get(position).getmOperName().equals(getResources().getString(R.string.duanxin))){
                    sendSMS(atyAddfEtss.getText().toString().trim());
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 分享到微信
     */
    private void sendToWeiXin() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = HttpConfig.SHARE_URL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getResources().getString(R.string.kuailaizhaowoba);
        msg.description = getResources().getString(R.string.baojiahuhang);
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher2);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }




    private void sendSMS(String smsNumber){

//	    Uri smsToUri = Uri.parse("smsto:"+smsNumber);

        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setData(Uri.parse("smsto:"+smsNumber));

        intent.putExtra("sms_body", getResources().getString(R.string.baojiahuhangxiazai)+ HttpConfig.SHARE_URL);

        startActivity(intent);

    }

    private class BaseUiListener implements IUiListener {
        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onComplete(Object o) {
        }

        @Override
        public void onError(UiError e) {
            Logger.e("onError:", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {
            Logger.e("onCancel", "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,listener);
    }


}
