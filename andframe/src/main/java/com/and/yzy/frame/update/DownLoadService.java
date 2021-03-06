package com.and.yzy.frame.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.and.yzy.frame.R;
import com.and.yzy.frame.config.SavePath;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yzy on 2016/5/20.
 */
public class DownLoadService extends Service {


    private Context mContext = this;
    // notification 名字
    private String notify_name = getString(R.string.zhengzaixiazai);
    Notification mNotification;
    private static final int NOTIFY_ID = 0;
    private NotificationManager mNotificationManager;
    /* 下载包安装路径 */
    private static final String saveFileName = SavePath.savePath + "updateApp.apk";
    private String apkUrl;
    private int progress;
    boolean canceled;
    private Thread downLoadThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DownloadService", "intent=" + intent.toString() + " ;           flags= " + flags + " ;    startId" + startId);
        if (intent.hasExtra("url")) {
            apkUrl = (String) intent.getExtras().get("url");
            Logger.v("apkUrl=="+apkUrl);
        }
        progress = 0;
        setUpNotification();
        new Thread() {
            public void run() {
                // 开始下载
                startDownload();
            }
        }.start();

        return startId;

    }

    private void startDownload() {
        canceled = false;
        downloadApk();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // 下载完毕
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    installApk();
                    break;
                case 2:
                    // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                case 1:
                    int rate = msg.arg1;
                    if (rate < 100) {
                        RemoteViews contentview = mNotification.contentView;
                        contentview.setTextViewText(R.id.frame_update_tv_progress, rate + "%");
                        contentview.setProgressBar(R.id.frame_update_progress, 100, rate, false);
                    } else {
                        // 下载完毕后变换通知形式

                        Notification.Builder builder = new Notification.Builder(mContext)
                                .setAutoCancel(false)
                                .setTicker(getString(R.string.xiazaiwancheng))
                                .setSmallIcon(R.drawable.logo11)
                                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.logo11))
                                .setContentTitle(getString(R.string.xiazaiwancheng))
                                .setContentText(getString(R.string.wenjianxiazaiwanbi))
                                .setDefaults(Notification.FLAG_AUTO_CANCEL);

                        mNotification = builder.getNotification();
                        stopSelf();// 停掉服务自身
                    }
                    PendingIntent contentIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                    mNotification.contentIntent = contentIntent2;
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
                case 3:
                    mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                    RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.update_download_notification_layout);
                    contentView.setTextViewText(R.id.frame_update_tv_name, getString(R.string.xiazaishibai));
                    // 指定个性化视图
                    mNotification.contentView = contentView;
                     /*   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        // 指定内容意图
                        mNotification.contentIntent = contentIntent;*/
                    mNotificationManager.notify(NOTIFY_ID, mNotification);

                    stopSelf();// 停掉服务自身
                    break;

            }
        }
    };

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    private int lastRate = 0;
    private InputStream is = null;
    private FileOutputStream fos = null;

    /**
     * 下载apk
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private Runnable mdownApkRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                is = conn.getInputStream();

                File file = new File(SavePath.savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress >= lastRate + 1) {
                        mHandler.sendMessage(msg);
                        lastRate = progress;
                    }
                    if (numread <= 0) {
                        mHandler.sendEmptyMessage(0);// 下载完成通知安装
                        // 下载完了，cancelled也要设置
                        canceled = true;
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!canceled);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (Exception e) {

                Message msg = mHandler.obtainMessage();
                msg.what = 3;
                mHandler.sendMessage(msg);
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    is.close();
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

    /**
     * 创建通知
     */
    private void setUpNotification() {

        Notification.Builder builder = new Notification.Builder(mContext)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.logo11)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.logo11))
                .setTicker(getString(R.string.kaishixiazai))
                .setWhen(System.currentTimeMillis())
           //     .setDefaults(Notification.FLAG_ONGOING_EVENT)
                .setOngoing(true);


        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.update_download_notification_layout);
        contentView.setTextViewText(R.id.frame_update_tv_name, notify_name);
        // 指定个性化视图
        builder.setContent(contentView);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        // 指定内容意图
        builder.setContentIntent(contentIntent);

        mNotification = builder.getNotification();

        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

}

