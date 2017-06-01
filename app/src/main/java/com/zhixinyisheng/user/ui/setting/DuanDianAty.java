package com.zhixinyisheng.user.ui.setting;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.config.SavePath;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;

/**
 * 断点续传
 * Created by 焕焕 on 2017/3/11.
 */
public class DuanDianAty extends BaseAty {
    @Bind(R.id.tv_msg)
    TextView mTvMsg;
    private String result = "";

    private long start = 0;
    private long stop = 1024 * 1024;
    public static final String saveFileName = SavePath.savePath + "1111tessdata.zip";
    private int times = 0;  // 根据文件大小自己设的，
    private Handler moreHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 0 && result!=null){
                if(times >= 10){
                    Message msg1 = Message.obtain();
                    msg1.what = 1;
                    moreHandler.sendMessage(msg1);
                }else{
                    new Thread(moreThread).start();
                    times += 1;
                }

                mTvMsg.setText(result);
            }else if(msg.what == 1){
                mTvMsg.setText(result);
            }
        };
    };
    @Override
    public int getLayoutId() {
        return R.layout.login_aty;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        new Thread(moreThread).start();

    }
    private Thread moreThread = new Thread(){
        public void run() {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(HttpConfig.tessDataUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                // 设置开始下载的位置和结束下载的位置，单位为字节
                connection.setRequestProperty("Range", "bytes=" + start + "-" + stop);

                String path = saveFileName;
                // 断点下载使用的文件对象RandomAccessFile
                RandomAccessFile access = new RandomAccessFile(path, "rw");
                // 移动指针到开始位置
                access.seek(start);
                InputStream is = null;
                Log.e("ADB----", connection.getResponseCode() + "");
                if(connection.getResponseCode() == 206){
                    is = connection.getInputStream();
                    int count = 0;
                    byte[] buffer = new byte[1024];
                    while((count = is.read(buffer)) != -1){
                        access.write(buffer, 0, count);
                    }
                }

                if(access != null){
                    access.close();
                }
                if(is != null){
                    is.close();
                }

                start = stop + 1;
                stop += 1024*1024;   // 每次下载1M
                Log.e("stop----", stop + "$$$$");
                Message msg = Message.obtain();
                msg.what = 0;
                result += "文件" + times + "下载成功" + ":" + start + "---" + stop + "\n";
                Log.e("result",result);
                moreHandler.sendMessage(msg);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
        };
    };
}
