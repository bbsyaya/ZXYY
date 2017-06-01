package com.and.yzy.frame.application;

import android.app.Application;
import android.content.Context;

import com.and.yzy.frame.util.CrashHandler;
import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


public class BaseApplication extends Application {

    public static Context mContext;

    public static String userId= "";//用户ID
    public static String phone = "";//登录手机号
    public static String secret = "";//登录返回秘钥

    public static String username = "";//姓名
    public static String headurl = "";//头像





    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        CrashHandler.getInstance().init(this);
        RetrofitUtils.init();
//        initPresco();
        Logger.init("result")               // default tag : PRETTYLOGGER or use just init()
                .setMethodCount(3)            // default 2
                .hideThreadInfo()             // default it is shown
                .setLogLevel(LogLevel.FULL);  // default : LogLevel.FULL


    }

//    private void initPresco() {
//
//        final File file=new File(SavePath.savePath+"cache/");
//        if (!file.exists()){
//            file.mkdirs();
//        }
//        DiskCacheConfig.Builder diskCacheConfig = DiskCacheConfig.newBuilder(mContext)
//                .setBaseDirectoryPathSupplier(new Supplier<File>() {
//                    @Override
//                    public File get() {
//                        return file;
//                    }
//                })
//                .setBaseDirectoryName("image_cache")
//                .setMaxCacheSize(40 * ByteConstants.MB)
//                .setMaxCacheSizeOnLowDiskSpace(10 * ByteConstants.MB)
//                .setMaxCacheSizeOnVeryLowDiskSpace(2 * ByteConstants.MB)
//                ;
//        ImagePipelineConfig.Builder config = ImagePipelineConfig.newBuilder(mContext);
//        config.setMainDiskCacheConfig(diskCacheConfig.build());
//        Fresco.initialize(this, config.build());
//    }


    public static Context getApplicationCotext() {

        return mContext.getApplicationContext();

    }
}
