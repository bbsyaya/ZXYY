package com.zhixinyisheng.user.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.wxpay.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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

/**
 * 分享的工具类
 * Created by 焕焕 on 2017/1/31.
 */
public class ShareUtils {
//    public static void shareToQQ(Context context) {
//        Tencent mTencent = Tencent.createInstance(Content.QQ_ID, context);
//        BaseUiListener listener_qq = new BaseUiListener();
//        final Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, "我是知心医生用户，快来找我吧");
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "下载知心医生，为您的健康保驾护航。");
//        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://222.222.24.133:8088/zhixinyisheng/api/apk/download.html");
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "知心医生");
//        mTencent.shareToQQ((Activity) context, params, listener_qq);
//
//    }
    public static void shareToQQ(Context context,String title,String imageUrl) {
        Tencent mTencent = Tencent.createInstance(Content.QQ_ID, context);
        BaseUiListener listener_qq = new BaseUiListener();
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "下载知心医生，为您的健康保驾护航。");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, HttpConfig.SHARE_URL);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "知心医生");
        mTencent.shareToQQ((Activity) context, params, listener_qq);

    }

//    /**
//     * 分享到微信
//     */
//    public static void sendToWeiXin(Context context) {
//        IWXAPI api = WXAPIFactory.createWXAPI(context, Content.APP_ID, true);
//        api.registerApp(Content.APP_ID);
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "http://222.222.24.133:8088/zhixinyisheng/api/apk/download.html";
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "我是知心医生用户，快来找我吧";
//        msg.description = "下载知心医生，为您的健康保驾护航";
//        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher2);
//        msg.thumbData = Util.bmpToByteArray(thumb, true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
//        api.sendReq(req);
//    }
    /**
     * 分享到微信
     */
    public static void sendToWeiXin(final Context context, String title, String imageUrl) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, Content.APP_ID, true);
        api.registerApp(Content.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = HttpConfig.SHARE_URL;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = context.getString(R.string.baojiahuhang);
        int imageUrl0 = R.mipmap.ic_newlogo;
        Glide.with(context).load(imageUrl0).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bmp, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap thumbBmp =  Bitmap.createScaledBitmap(bmp, 30, 30, true);
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);
            }
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                Toast.makeText(context, "图片加载错误！~", Toast.LENGTH_SHORT).show();
            }
        });

    }


//    /**
//     * 分享到朋友圈
//     */
//    public static void sendToFriends(Context context) {
//        IWXAPI api = WXAPIFactory.createWXAPI(context, Content.APP_ID, true);
//        api.registerApp(Content.APP_ID);
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "http://222.222.24.133:8088/zhixinyisheng/api/apk/download.html";
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "我是知心医生用户，快来找我吧";
//        msg.description = "下载知心医生，为您的健康保驾护航";
//        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher2);
//        msg.thumbData = Util.bmpToByteArray(thumb, true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
//    }
    /**
     * 分享到朋友圈
     */
    public static void sendToFriends(final Context context, String title, String imageUrl) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, Content.APP_ID, true);
        api.registerApp(Content.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = HttpConfig.SHARE_URL;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = context.getString(R.string.baojiahuhang);
        int imageUrl0 = R.mipmap.ic_newlogo;
        Glide.with(context).load(imageUrl0).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bmp, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 30, 30, true);
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                api.sendReq(req);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                Toast.makeText(context, "图片加载错误！~", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    private static class BaseUiListener implements IUiListener {
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
}
