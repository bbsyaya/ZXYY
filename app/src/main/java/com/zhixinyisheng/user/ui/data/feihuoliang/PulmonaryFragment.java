package com.zhixinyisheng.user.ui.data.feihuoliang;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.PermissionsUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 肺活量
 * Created by 焕焕 on 2016/10/24.
 */
public class PulmonaryFragment extends BaseFgt implements Animator.AnimatorListener {
    private static final String TAG = "FeiHuoLiangFgt";
    @Bind(R.id.fhl_iv)
    ImageView fhl_iv;
    @Bind(R.id.fhl_tv)
    TextView tvFhl;
//    @Bind(R.id.fhl_tv321)
//    TextView tv321;
    @Bind(R.id.zhi)
    TextView zhi;
    @Bind(R.id.fhl_btn)
    Button fhl_btn;
    @Bind(R.id.fhl_ll_cq)
    LinearLayout ll_cq;
    @Bind(R.id.fhl_ll_yc)
    LinearLayout ll_yc;

    //    @Bind(R.id.iv_cattle)
    ImageView ivCattle;

    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.iv_yun_top)
    ImageView ivYunTop;
    @Bind(R.id.iv_yun_bottom)
    ImageView ivYunBottom;

//    int k = 1;
    int i;
    int int_volume = 0;
    String str_volume = "";
    AudioRecord mAudioRecord;
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.ENCODING_PCM_16BIT);
    private Timer timer1, timer_hqfhl, timer_begin;
    private TimerTask tt1, tt_hqfhl, tt_begin;
    boolean is_dy7 = false;
    boolean isTimer = true;
    Object mLock;

    private float ScreenHeight, ScreenWidth;
    private ObjectAnimator mTranslation;
    private Animation translateAnimation;
    private Animation translateAnimation2;
    //    private ObjectAnimator mTranslationBgTop;
//    private ObjectAnimator mTranslationBgBottom;
    private ObjectAnimator mScale;
    //    Animation mScale;
    private boolean isStartScale,isStart;
//    private AnimationDrawable mAnimationDrawable;

    //边界值
    private int mThreshold=73;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pulmonary;
    }

    @Override
    public void initData() {
        Content.vitalCapacity++;
        mLock = new Object();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager wm1 = getActivity().getWindowManager();
        ScreenWidth = wm1.getDefaultDisplay().getWidth();
        ScreenHeight = wm1.getDefaultDisplay().getHeight();
    }


    @Override
    public void requestData() {

    }

    //    private void visibleBgYun(){
//        ivYunBottom.setVisibility(View.VISIBLE);
//        ivYunTop.setVisibility(View.VISIBLE);
//    }
    private void goneBgYun() {
        ivYunBottom.setVisibility(View.GONE);
        ivYunTop.setVisibility(View.GONE);

        ivYunBottom.clearAnimation();
        ivYunBottom.invalidate();

        ivYunTop.clearAnimation();
        ivYunTop.invalidate();
    }

    private void bgAnimationStart() {
        translateAnimation = new TranslateAnimation(-500, ScreenWidth, 0, 0);
        translateAnimation.setDuration(10000);
        translateAnimation.setInterpolator(getActivity(), android.R.anim.cycle_interpolator);//设置动画插入器
        translateAnimation.setFillAfter(true);//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）
        translateAnimation.setRepeatCount(100);
//        translateAnimation.setRepeatMode(Animation.RESTART);//从头开始
        translateAnimation.setRepeatMode(Animation.REVERSE);//反方向执行
        ivYunBottom.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ivYunTop.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                ivYunTop.setVisibility(View.VISIBLE);
            }
        });

        translateAnimation2 = new TranslateAnimation(-700, ScreenWidth, 0, 0);
        translateAnimation2.setDuration(10000);
        translateAnimation2.setInterpolator(getActivity(), android.R.anim.cycle_interpolator);//设置动画插入器
        translateAnimation2.setFillAfter(true);//设置动画结束后保持当前的
        translateAnimation2.setStartOffset(2000);//延迟执行
        translateAnimation2.setRepeatCount(100);//重复执行的次数
        translateAnimation2.setRepeatMode(Animation.REVERSE);//反方向执行
//        translateAnimation.setRepeatMode(Animation.RESTART);
        ivYunTop.startAnimation(translateAnimation2);
    }

    private void bgAnimationEnd() {
        if(translateAnimation!=null && translateAnimation2!=null){
            translateAnimation.cancel();
            translateAnimation2.cancel();
            goneBgYun();
        }
    }

    /**
     * 开始放大的动画
     */
    private void cattleMagnifyAni() {
        //把按钮放大1.5倍
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 3.0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 3.0f);
        mScale = ObjectAnimator.ofPropertyValuesHolder(ivCattle, pvhX, pvhY);
        mScale.setDuration(20000);
        //执行放大动画
        mScale.start();
        isStartScale = true;
    }

    /**
     * 结束放大的动画
     */
    private void cattleManifyAniEnd() {
        if (mScale != null) {
//            mScale.end();
            mScale.cancel();
            isStartScale = false;
            ivCattle.setVisibility(View.GONE);
            isStart=false;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
//        mAnimationDrawable = null;
        mTranslation = null;
    }

    /**
     * 牛移动的动画 开始
     */
    private void cattleMoveAnimationStart() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        ivCattle = new ImageView(getActivity());
        ivCattle.setLayoutParams(params);
        ivCattle.setImageResource(R.mipmap.ic_niu2);
        rl.addView(ivCattle);
        ivCattle.setVisibility(View.VISIBLE);

        //创建一个水平移动的动画对象，从位置0到300平移
        mTranslation = ObjectAnimator.ofFloat(ivCattle, "translationY", 0f, -ScreenHeight / 2);
        mTranslation.setDuration(10000);
        mTranslation.addListener(this);
        mTranslation.start();
    }

    /**
     * 牛移动的动画  结束
     */
    private void cattleMoveAnimationEnd() {
        if(ivCattle!=null && mTranslation!=null){
            ivCattle.setVisibility(View.GONE);
            mTranslation.removeListener(this);
            mTranslation.end();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {//大于70就开始改变textview肺活量值
                int_volume = int_volume + 38;
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
                if(int_volume>=38){
                    tvFhl.setText(str_volume);
                }
                if(!isStart && int_volume>38){
                    handler.removeMessages(3);
                    //开始动画
                    cattleMoveAnimationStart();
                    ivYunBottom.setVisibility(View.VISIBLE);
                    bgAnimationStart();
                    isStart=true;
                }

            } else if (msg.what == 1) {//小于70完成测试

                cattleMoveAnimationEnd();
                cattleManifyAniEnd();
                bgAnimationEnd();

                is_dy7 = false;//回值
                timer1.cancel();//结束动画
                isTimer = true;//下一次可以再次开始动画
                //取消获取值
                timer_hqfhl.cancel();
                if(mAudioRecord!=null){
                    //初始化测声音变量
                    mAudioRecord.stop();
                    mAudioRecord.release();
                    mAudioRecord = null;
                }

                if (!str_volume.equals("")) {
                    i = Integer.parseInt(str_volume);
                }
                if (i < 2500) {
                    zhi.setText("娇弱绵软，肺活量是肺功能强弱的一个重要指标，建议平常加强有氧运动，多吃瓜果蔬菜补充维生素。");
                } else if (i < 5000) {
                    zhi.setText("您的肺活量比较正常，不过为了延年益寿，充分发挥肺功能的潜在力，建议坚持抬头挺胸直腰、参加适当的体力活动、每天做扩胸动作。");
                } else if (i < 8500) {
                    zhi.setText("狂风扑面，您的肺活量很强了，适于长跑和游泳。");
                } else if (i < 10500) {
                    zhi.setText("哇！恭喜，气吞山河，您是超人了！");
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

                cattleMoveAnimationEnd();
                cattleManifyAniEnd();
                bgAnimationEnd();

                fhl_btn.setText("再测一次");
                fhl_btn.setVisibility(View.VISIBLE);
                zhi.setVisibility(View.VISIBLE);
                ll_cq.setVisibility(View.GONE);
                ll_yc.setVisibility(View.GONE);


                //取消获取值
                timer_hqfhl.cancel();
                if(mAudioRecord!=null){
                    //初始化测声音变量
                    mAudioRecord.stop();
                    mAudioRecord.release();
                    mAudioRecord = null;
                }

                showToast("姿势不正确,请再次测试");
            } else if (msg.what == 4) {
                zhi.setVisibility(View.GONE);
                ll_cq.setVisibility(View.VISIBLE);
                ll_yc.setVisibility(View.VISIBLE);
                beginHqfhl();

               /* if (k == 0) {
                    tv321.setVisibility(View.VISIBLE);
                    tv321.setText("GO!");
                } else if (k == -1) {
                    tv321.setVisibility(View.GONE);
                    zhi.setVisibility(View.GONE);
                    ll_cq.setVisibility(View.VISIBLE);
                    ll_yc.setVisibility(View.VISIBLE);
                    beginHqfhl();
//                    //开始动画
//                    cattleMoveAnimationStart();
//                    ivYunBottom.setVisibility(View.VISIBLE);
//                    bgAnimationStart();
                } else {
                    tv321.setText(k + "");
                    tv321.setVisibility(View.VISIBLE);
                }*/
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
        handler.sendEmptyMessageDelayed(3,5000);
        //开始测试等待5秒如果没有大于一个值就停止测试
        /*timer_begin = new Timer();
        tt_begin = new TimerTask() {
            @Override
            public void run() {
                //测试开始但是没达到70就结束
                handler.sendEmptyMessage(3);
            }
        };
        timer_begin.schedule(tt_begin, 100000);*/

        String phoneType=getPhoneType();
        if("小米".equals(phoneType)){
            mThreshold=45;
        }
        //获取值
        timer_hqfhl = new Timer();
        tt_hqfhl = new TimerTask() {
            @Override
            public void run() {
                double mean = getNoiseLevel();

                Log.e("分贝值:", mean + "");
                if (mean <mThreshold) {
                    if (is_dy7) {
                        //大于70的值激活
                        handler.sendEmptyMessage(1);
                    }
                } else { //大于70
                    is_dy7 = true;
//                    timer_begin.cancel();//取消开始的定时器
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
    private String getPhoneType(){
        //Redmi 3X
        String type = android.os.Build.MODEL;
        if(type.contains("mi")){
            return "小米";
        }
        Logger.e(TAG,"phoneType-->"+type);
        return type;
    }

    //获取声音
    public double getNoiseLevel() {
        double volume = 0;
        //录制的声音容量
        short[] buffer = new short[BUFFER_SIZE];
        // r是实际读取的数据长度，一般而言r会小于buffersize
        int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);

        Log.e("rrrrr",r+"*****");
        if (r<=0){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("您已关闭录音权限，请开启!");
                }
            });

            return 0;
        }

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
        int_volume=0;
        isStart=false;
        boolean flag = PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED;
        Logger.e(TAG, "是否开启语音权限-->000" + flag);

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
            boolean flag2 = PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED;
            if (flag2) {
                fhl_btn.setVisibility(View.GONE);
                zhi.setVisibility(View.GONE);
                tvFhl.setText("0000");
                handler.sendEmptyMessage(4);
               /* //321 倒计时效果
                k = 0;
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
                timer321.schedule(tt321, 0, 1000);*/
            }else{
                showToast("请开启语音权限");
            }
        }


    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        cattleMagnifyAni();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
