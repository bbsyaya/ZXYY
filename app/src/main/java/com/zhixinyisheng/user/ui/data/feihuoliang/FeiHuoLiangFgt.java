package com.zhixinyisheng.user.ui.data.feihuoliang;

import android.Manifest;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.PermissionsUtil;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 肺活量
 * Created by 焕焕 on 2016/10/24.
 */
public class FeiHuoLiangFgt extends BaseFgt {
    private static final String TAG = "FeiHuoLiangFgt";
    @Bind(R.id.fhl_iv)
    ImageView fhl_iv;
    @Bind(R.id.fhl_tv)
    TextView tvFhl;
    @Bind(R.id.fhl_tv321)
    TextView tv321;
    @Bind(R.id.zhi)
    TextView zhi;
    @Bind(R.id.fhl_btn)
    Button fhl_btn;
    @Bind(R.id.fhl_ll_cq)
    LinearLayout ll_cq;
    @Bind(R.id.fhl_ll_yc)
    LinearLayout ll_yc;

    int k = 4;
    int i;
    int int_volume = 0;
    String str_volume = "";
    AudioRecord mAudioRecord;
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.ENCODING_PCM_16BIT);
    private Random mRandom = new Random();
    private Timer timer1, timer_hqfhl, timer_begin;
    private TimerTask tt1, tt_hqfhl, tt_begin;
    boolean is_dy7 = false;
    boolean isTimer = true;
    Object mLock;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_feihuoliang;
    }

    @Override
    public void initData() {
        mLock = new Object();
    }

    @Override
    public void requestData() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {//大于70就开始改变textview肺活量值
                int_volume = int_volume + 76;
                str_volume = String.valueOf(int_volume);
                if (str_volume.length() == 1) {
                    str_volume = str_volume;
                } else if (str_volume.length() == 2) {
                    str_volume = str_volume;
                } else if (str_volume.length() == 3) {
                    str_volume = str_volume;
                } else if (str_volume.length() == 4) {
                    str_volume = str_volume;
                }
                tvFhl.setText(str_volume);
            } else if (msg.what == 1) {//小于70完成测试
                is_dy7 = false;//回值
                timer1.cancel();//结束动画
                isTimer = true;//下一次可以再次开始动画
                //取消获取值
                timer_hqfhl.cancel();
                //初始化测声音变量
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
                if (!str_volume.equals("")) {
                    i = Integer.parseInt(str_volume);
                }
                if (i < 1500) {
                    zhi.setText("娇弱绵软，如果是妹子，那可真是惹人怜爱。如果是汉子，那可真是……");
                } else if (i < 4500) {
                    zhi.setText("差强人意，你的肺活量一般般了，是没发挥好吗？要不然再来一次？");
                } else if (i < 6500) {
                    zhi.setText("狂风扑面，你的肺活量很强了，是经常运动吗？要坚持哦！");
                } else if (i < 8500) {
                    zhi.setText("气吞山河，勇士，以后说话尽量不要对着人，容易把人吹跑……");
                } else if (i < 10500) {
                    zhi.setText("我的天呐！请不要对着大地吹气，拜托！");
                } else {
                    zhi.setText("额滴神呐！维护宇宙和平的任务就拜托你了！");
                }
                zhi.setVisibility(View.VISIBLE);
                fhl_btn.setText("再测一次");
                fhl_btn.setVisibility(View.VISIBLE);
                ll_cq.setVisibility(View.GONE);
                ll_yc.setVisibility(View.GONE);

            } else if (msg.what == 2) {//提交数据
//                dg_shen.dismisssDialog();
                showToast("提交成功");
//                finish();
            } else if (msg.what == 3) {//测试开始但是没达到70就结束
                fhl_btn.setText("再测一次");
                fhl_btn.setVisibility(View.VISIBLE);
                zhi.setVisibility(View.VISIBLE);
                ll_cq.setVisibility(View.GONE);
                ll_yc.setVisibility(View.GONE);


                //取消获取值
                timer_hqfhl.cancel();
                //初始化测声音变量
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
                showToast("姿势不正确,请再次测试");
            } else if (msg.what == 4) {
                if (k == 0) {
                    tv321.setText("GO!");
                } else if (k == -1) {
                    tv321.setVisibility(View.GONE);
                    zhi.setVisibility(View.GONE);
                    ll_cq.setVisibility(View.VISIBLE);
                    ll_yc.setVisibility(View.VISIBLE);
                    beginHqfhl();
                } else {
                    tv321.setText(k + "");
                    tv321.setVisibility(View.VISIBLE);
                }
            } else if (msg.what == 10) {
//                dg_shen.dismisssDialog();
                showToast("错误!");
            }
        }

    };

    /**
     * 处理 开始  记录 分贝值
     */
    private void beginHqfhl() {
        tvFhl.setText("0000");
        int_volume = 0;
        str_volume = "";

        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        mAudioRecord.startRecording();


        //开始测试等待5秒如果没有大于一个值就停止测试
        timer_begin = new Timer();
        tt_begin = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(3);
            }
        };
        timer_begin.schedule(tt_begin, 10000);


        //获取值
        timer_hqfhl = new Timer();
        tt_hqfhl = new TimerTask() {
            @Override
            public void run() {
                double mean = getNoiseLevel();
                Log.e("分贝值:", mean + "");
                if (mean <= 60) {
                    if (is_dy7) {//大于70的值激活
                        handler.sendEmptyMessage(1);
                    }
                } else {//大于70
                    is_dy7 = true;
                    timer_begin.cancel();//取消开始的定时器
                    handler.sendEmptyMessage(0);
                    if (isTimer) {//第一次大于70走动画
                        timer1 = new Timer();//心形动画
                        tt1 = new TimerTask() {
                            @Override
                            public void run() {
                                isTimer = false;
                            }
                        };
                        timer1.schedule(tt1, 0, 800);
                    }
                }


            }
        };

        timer_hqfhl.schedule(tt_hqfhl, 0, 100);

    }

    //获取声音
    public double getNoiseLevel() {
        double volume = 0;
        //录制的声音容量
        short[] buffer = new short[BUFFER_SIZE];
        // r是实际读取的数据长度，一般而言r会小于buffersize
        int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
        long v = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (int i = 0; i < buffer.length; i++) {
            v += buffer[i] * buffer[i];
        }
        // 平方和除以数据总长度，得到音量大小。
        double mean = v / (double) r;
        volume = 10 * Math.log10(mean);
        return volume;
    }

    @OnClick(R.id.fhl_btn)
    public void onClick() {
        boolean flag = PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED;
        Logger.e(TAG, "是否开启语音权限-->"+flag);

        if (!flag) {
            new MaterialDialog(getActivity())
                    .setMDTitle("重要指示")
                    .setMDMessage("需要开启录音权限！")
                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
                            PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }, Manifest.permission.RECORD_AUDIO);

                        }
                    })
                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {

                        }
                    })
                    .show();
        } else {
            fhl_btn.setVisibility(View.GONE);
            zhi.setVisibility(View.GONE);
            tvFhl.setText("0000");
            //321 倒计时效果
            k = 4;
            final Timer timer321 = new Timer();
            TimerTask tt321 = new TimerTask() {
                @Override
                public void run() {
                    k--;
                    handler.sendEmptyMessage(4);
                    if (k == -1) {
                        timer321.cancel();
                    }
                }
            };
            timer321.schedule(tt321, 0, 1000);
        }

    }

}
