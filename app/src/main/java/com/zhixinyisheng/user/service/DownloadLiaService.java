package com.zhixinyisheng.user.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.config.SavePath;
import com.and.yzy.frame.util.SPUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.util.download.ProgressDownloader;
import com.zhixinyisheng.user.util.download.ProgressResponseBody;
import com.zhixinyisheng.user.util.tool.UnzipAssets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 下载中文识别库的服务
 * Created by 焕焕 on 2017/3/11.
 */
public class DownloadLiaService extends Service implements ProgressResponseBody.ProgressListener {
    private Context mContext = this;
    // notification 名字
//    private String notify_name = getString(R.string.zhengzaixiazaizhongwenshibieku);
    Notification mNotification;
    private static final int NOTIFY_ID = 0;
    private NotificationManager mNotificationManager;
    private int progress;
    boolean canceled;
    private Thread downLoadThread;
    /* 下载包安装路径 */
    public static final String saveFileName = SavePath.savePath + "tessdata.zip";
    private InputStream is = null;
    private FileOutputStream fos = null;
    private int lastRate = 0;
    public static int isOpen;
    SPUtils spUtils;
    private long breakPoints;
    private ProgressDownloader downloader;
    private File file;
    private long totalBytes;
    private long contentLength;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        progress = 0;
        isOpen = 1;
        setUpNotification();
        spUtils = new SPUtils("breakPoints");
        breakPoints = (long) spUtils.get("breakPoints", 0L);
        Log.e("breakPoints",breakPoints+"####");
        File fileD = new File(SavePath.savePath);
        if (!fileD.exists()) {
            Log.e("reStart",breakPoints+"!!!!");
            breakPoints = 0L;
            spUtils.put("breakPoints", breakPoints);
            fileD.mkdirs();
        }
        file = new File(saveFileName);
        downloader = new ProgressDownloader(HttpConfig.tessDataUrl, file, this);
        downloader.download(0L);
        return startId;
    }

    /**
     * 创建通知
     */
    private void setUpNotification() {

        Notification.Builder builder = new Notification.Builder(mContext)
                .setAutoCancel(false)
                .setSmallIcon(com.and.yzy.frame.R.drawable.logo11)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), com.and.yzy.frame.R.drawable.logo11))
                .setTicker(getString(R.string.kaishixiazai))
                .setWhen(System.currentTimeMillis())
                //     .setDefaults(Notification.FLAG_ONGOING_EVENT)
                .setOngoing(true);


        RemoteViews contentView = new RemoteViews(getPackageName(), com.and.yzy.frame.R.layout.update_download_notification_layout);
        contentView.setTextViewText(com.and.yzy.frame.R.id.frame_update_tv_name, getString(R.string.zhengzaixiazaizhongwenshibieku));
        // 指定个性化视图
        builder.setContent(contentView);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        // 指定内容意图
        builder.setContentIntent(contentIntent);

        mNotification = builder.getNotification();

        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("service fgf", "onDestroy");
        downloader.pause();
//        Toast.makeText(this, "下载暂停", Toast.LENGTH_SHORT).show();
        // 存储此时的totalBytes，即断点位置。
        breakPoints = totalBytes;
        spUtils.put("breakPoints", breakPoints);

    }

    @Override
    public void onPreExecute(long contentLength) {
        // 文件总长只需记录一次，要注意断点续传后的contentLength只是剩余部分的长度
        if (this.contentLength == 0L) {
            this.contentLength = contentLength;
            Log.e("contentLength",this.contentLength+"####"+contentLength);
        }

    }

    @Override
    public void update(long totalBytes, boolean done) {
        // 注意加上断点的长度
        this.totalBytes = totalBytes + 0L;
        Log.e("xiazaiwancheng", this.totalBytes+"&&&&&"+totalBytes);
        spUtils.put("breakPoints", this.totalBytes);
//        progressBar.setProgress((int) (totalBytes + breakPoints) / 1024);
        int rate = (int) (((float)this.totalBytes / 29386270) * 100);
        RemoteViews contentview = mNotification.contentView;
        contentview.setTextViewText(com.and.yzy.frame.R.id.frame_update_tv_progress, rate + "%");
        contentview.setProgressBar(com.and.yzy.frame.R.id.frame_update_progress, 100, rate, false);

        if (done) {
            Log.e("xiazaiwancheng", "下载完成");
            // 切换到主线程
            // 下载完毕后变换通知形式

            Notification.Builder builder = new Notification.Builder(mContext)
                    .setAutoCancel(false)
                    .setTicker(getString(R.string.xiazaiwancheng))
                    .setSmallIcon(com.and.yzy.frame.R.drawable.logo11)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), com.and.yzy.frame.R.drawable.logo11))
                    .setContentTitle(getString(R.string.xiazaiwancheng))
                    .setContentText(getString(R.string.wenjianyixiazaiwanbi))
                    .setDefaults(Notification.FLAG_AUTO_CANCEL);

            mNotification = builder.getNotification();
            /**
             * 解压图文识别文件
             * */
            try {
                UnzipAssets.unZip(mContext, saveFileName, SavePath.savePath);
            } catch (IOException e) {
                Logger.e("eroreojkroasjdo", e.toString());
                e.printStackTrace();
            }
            isOpen = 0;

//            stopSelf();// 停掉服务自身
        }
        PendingIntent contentIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.contentIntent = contentIntent2;
        mNotificationManager.notify(NOTIFY_ID, mNotification);


    }
}

