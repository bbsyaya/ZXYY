package com.zhixinyisheng.user.ui.data.laboratory;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.config.SavePath;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.util.tool.UnzipAssets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载语音识别中文库的服务
 * Created by 焕焕 on 2017/3/3.
 */
public class TessDataService extends Service {
    private Context mContext = this;
    // notification 名字
//    private String notify_name = getString(R.string.zhengzaixiazaizhongwenshibieku);
    Notification mNotification;
    private static final int NOTIFY_ID = 0;
    private NotificationManager mNotificationManager;
    private int progress;
    boolean canceled;
    private Thread downLoadThread;
    private String tessDataUrl = HttpConfig.tessDataUrl;//中文识别库的下载地址
    /* 下载包安装路径 */
    public static final String saveFileName = SavePath.savePath + "tessdata.zip";
    private InputStream is = null;
    private FileOutputStream fos = null;
    private int lastRate = 0;
    public static int isOpen;
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
        new Thread() {
            public void run() {
                // 开始下载
                startDownload();
            }
        }.start();
        return startId;
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mNotificationManager.cancel(NOTIFY_ID);
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
                        contentview.setTextViewText(com.and.yzy.frame.R.id.frame_update_tv_progress, rate + "%");
                        contentview.setProgressBar(com.and.yzy.frame.R.id.frame_update_progress, 100, rate, false);
                    } else {
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
                            UnzipAssets.unZip(mContext,TessDataService.saveFileName, SavePath.savePath);
                        } catch (IOException e) {
                            Logger.e("eroreojkroasjdo", e.toString());
                            e.printStackTrace();
                        }
                        isOpen = 0;

                        stopSelf();// 停掉服务自身
                    }
                    PendingIntent contentIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                    mNotification.contentIntent = contentIntent2;
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
                case 3:
                    mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                    RemoteViews contentView = new RemoteViews(getPackageName(), com.and.yzy.frame.R.layout.update_download_notification_layout);
                    contentView.setTextViewText(com.and.yzy.frame.R.id.frame_update_tv_name, getString(R.string.xiazaishibai));
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
    private void startDownload() {
        canceled = false;
        downloadTessData();
    }

    /**
     * 下载语音识别文件
     */
    private void downloadTessData() {
        downLoadThread = new Thread(mdownTessDataRunnable);
        downLoadThread.start();
    }

    private Runnable mdownTessDataRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(tessDataUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                is = conn.getInputStream();
                File file = new File(SavePath.savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File tessDataFile = new File(saveFileName);
                fos = new FileOutputStream(tessDataFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count+=numread;
                    progress = (int) (((float) count / length) * 100);
                    //下载进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress >= lastRate + 1) {
                        mHandler.sendMessage(msg);
                        lastRate = progress;
                    }
                    if (numread <= 0) {
//                        mHandler.sendEmptyMessage(0);// 下载完成通知安装
                        // 下载完了，cancelled也要设置
                        canceled = true;
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!canceled);
                fos.close();
                is.close();
            } catch (Exception e) {
                Message msg = mHandler.obtainMessage();
                msg.what = 3;
                mHandler.sendMessage(msg);
                e.printStackTrace();
            }finally {
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
                .setSmallIcon(com.and.yzy.frame.R.drawable.logo11)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), com.and.yzy.frame.R.drawable.logo11))
                .setTicker(getString(R.string.kaishixiazai))
                .setWhen(System.currentTimeMillis())
                //     .setDefaults(Notification.FLAG_ONGOING_EVENT)
                .setOngoing(true);


        RemoteViews contentView = new RemoteViews(getPackageName(), com.and.yzy.frame.R.layout.update_download_notification_layout);
        contentView.setTextViewText(com.and.yzy.frame.R.id.frame_update_tv_name,  getString(R.string.zhengzaixiazaizhongwenshibieku));
        // 指定个性化视图
        builder.setContent(contentView);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        // 指定内容意图
        builder.setContentIntent(contentIntent);

        mNotification = builder.getNotification();

        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }
}
