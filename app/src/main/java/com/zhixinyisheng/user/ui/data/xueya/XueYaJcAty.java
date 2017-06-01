package com.zhixinyisheng.user.ui.data.xueya;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.messure.XinLvJcActivity;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.tool.ImageProcessing;
import com.zhixinyisheng.user.view.NewBeijing;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 血压自测
 * Created by 焕焕 on 2016/12/25.
 */
public class XueYaJcAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ydbj_nb)
    NewBeijing ydbj_nb;
    @Bind(R.id.linearlayout1_xinlv)
    RelativeLayout linearlayout1_xinlv;
    @Bind(R.id.tiaodongjiantou)
    ImageView zhizhen;
    @Bind(R.id.Frame_layout1)
    FrameLayout Frame_layout1;
    @Bind(R.id.preview)
    SurfaceView preview;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.iv_gongzhu)
    ImageView iv_gongzhu;
    @Bind(R.id.woshixinlvshijian_text)
    TextView woshixinlvshijian_text;
    @Bind(R.id.teshuziti_xygao)
    TextView teshuziti_xygao;
    @Bind(R.id.wodegaoy)
    TextView wodegaoy;
    @Bind(R.id.teshuziti_xydi)
    TextView teshuziti_xydi;
    @Bind(R.id.wodediya)
    TextView wodediya;
    @Bind(R.id.xinlv_progress)
    ProgressBar xinlv_progress;//底下的进度条
    // 曲线
    private Timer timer = new Timer();
    private TimerTask task;
    private static int gx;
    private static int j;
    public static XinLvJcActivity instance;
    private static double flag = 1;
    private String title = "pulse";
    private XYSeries series;
    private XYMultipleSeriesDataset mDataset;
    private GraphicalView chart;
    private XYMultipleSeriesRenderer renderer;
    private XYSeriesRenderer r;
    private Context context;
    private int addX = -1;
    double addY;
    int[] xv = new int[300];
    int[] yv = new int[300];
    int[] hua = new int[]{10, 9, 10, 11, 12, 13, 14, 13, 12, 11, 10};

    // private static final String TAG = "HeartRateMonitor";
    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private static SurfaceHolder previewHolder = null;
    private static Camera camera = null;
    boolean canUse = true;//是否开启摄像头
    private static PowerManager.WakeLock wakeLock = null;
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    private AnimatorSet set;
    int i = 0;
    ViewGroup.LayoutParams para;
    boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7, flag8, flag9, flag10;
    int height;
    int height_per;
    int height0;

    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        finish();
    }

    public static enum TYPE {
        GREEN, RED
    }

    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];
    /**
     * 脉搏数
     */
    private static double beats = 0;
    private static long startTime = 0;
    private Vibrator vibrator;
    private static String xinlv = null;
    private String[] xinlvData;

    private int recLen = 14;

    Timer timer1 = new Timer();

    private List<String> list;
    private int[] stringToInts;
    private String score, nowTime;
    private SharedPreferences sharedPrefe;
    private String path, path1, patient_id;//重要数据
    private double health;
    /*声音初始化*/
    private SoundPool soundPool;
    private int music;
    /**
     * 测量显示进度条剩余时间
     */


    private int[] data = new int[100];
    private int hasdata = 0;

    private int status;
    private String[] arrs;
    private Intent intent1;
    private String username;//心率还是血压的旗帜
    AudioManager mAudioManager;
    Handler mphandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {

                try {
                    xinlv_progress.setProgress((int) (((float) status / 14) * 100));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };
    boolean flagXueYa ;
    Timer timer_xueya = new Timer();
    @Override
    public int getLayoutId() {
        return R.layout.activity_xueyajc;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        xinlv_progress.setVisibility(View.GONE);
        setGongZhu();
        ivBack.setVisibility(View.VISIBLE);
        // 曲线
        context = getApplicationContext();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        nowTime = formatter.format(curDate);
        woshixinlvshijian_text.setText(nowTime);
        list = new ArrayList<String>();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(zhizhen, "TranslationY", zhizhen.getTop() + 5, zhizhen.getTop() - 50);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(zhizhen, "TranslationY", zhizhen.getTop() - 50, zhizhen.getTop());
        set = new AnimatorSet();
        set.playSequentially(animator2, animator3);
        set.setDuration(2);
        //zhizheweizhi();
        Typeface type = Typeface.createFromAsset(getAssets(), "stencilstd.otf");
//        teshuziti_xl.setTypeface(type);
//        wodebpm.setTypeface(type);
        teshuziti_xygao.setTypeface(type);
        wodegaoy.setTypeface(type);
        teshuziti_xydi.setTypeface(type);
        wodediya.setTypeface(type);
        cjsTvt.setText(getResources().getString(R.string.home_bloodPressure));
        tubiao();
        try {
            kaiqi();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("xueyaa",e.toString());
        }

        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);

        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        initVoice2();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm
                .newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
        teshuziti_xygao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!flagXueYa){
                    flagXueYa = true;

                    TimerTask tt = new TimerTask() {
                        @Override
                        public void run() {
                            play_voice();
                        }
                    };
                    timer_xueya.schedule(tt,0,3000);
                }
            }
        });
    }

    private void initVoice2() {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = soundPool.load(context, R.raw.xueya, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
    }

    /**
     * 设置汞柱
     */
    private void setGongZhu() {
        para = iv_gongzhu.getLayoutParams();

        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = imageView.getHeight();
//                Log.e("heigth", height + "$$");//1232
                height0 = (int) (height * ((double) 72 / 1232));//0刻度的时候(初始值)***   注意 一开始是83/1232
                //(116-83)/1232 是一个刻度的百分比（0.0268）
//                para.height=(int) (height*0.0268)+(int) (height*((double)83/1232));
                para.width = 20;
//                iv_gongzhu.setLayoutParams(para);
                height_per = (int) (height * 0.0268);
//                Log.e("per", height_per + "$" + height0);
                i = height0;
                hgAnimation();

            }
        });
    }

    Timer t;

    /**
     * 血压汞柱动画
     */
    private void hgAnimation() {
        t = new Timer();
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String num = null;
                        try {
                            num = teshuziti_xygao.getText().toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Logger.e("teshuziti_xygao",e.toString());
                            num="0";
                        }
                        para.height = i;
                        iv_gongzhu.setLayoutParams(para);
                        if (!num.equals("0")) {
                            if (!flag1 && !flag2 && !flag3 && !flag4 && !flag5 && !flag6 && !flag7 && !flag8 && !flag9 && !flag10) {
                                i++;
                                if (i >= height0 + 4 * height_per) {
                                    flag1 = true;
                                }
                            }
                            if (flag1 && !flag2 && !flag3 && !flag4 && !flag5 && !flag6 && !flag7 && !flag8 && !flag9 && !flag10) {
                                i = i - 3;
//                                Log.e("jump 384","jump");
                                if (i <= height0 + 3 * height_per) {
                                    flag2 = true;
                                }
                            }
                            if (flag1 && flag2 && !flag3 && !flag4 && !flag5 && !flag6 && !flag7 && !flag8 && !flag9 && !flag10) {
                                if (i >= height0 + 3 * height_per && i <= height0 + 4 * height_per) {
                                    i = i + 3;
                                } else {
                                    i++;
                                }


                                if (i >= height0 + 8 * height_per) {
                                    flag3 = true;
                                }
                            }
                            if (flag1 && flag2 && flag3 && !flag4 && !flag5 && !flag6 && !flag7 && !flag8 && !flag9 && !flag10) {
                                i = i - 3;
//                                Log.e("jump 403","jump");
                                if (i <= height0 + 7 * height_per) {
                                    flag4 = true;
                                }
                            }
                            if (flag1 && flag2 && flag3 && flag4 && !flag5 && !flag6 && !flag7 && !flag8 && !flag9 && !flag10) {
                                if (i >= height0 + 7 * height_per && i <= height0 + 8 * height_per) {
                                    i = i + 3;
                                } else {
                                    i++;
                                }
                                if (i >= height0 + 12 * height_per) {
                                    flag5 = true;
                                }
                            }
                            if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6 && !flag7 && !flag8 && !flag9 && !flag10) {
                                i = i - 3;
//                                Log.e("jump 420","jump");
                                if (i <= height0 + 11 * height_per) {
                                    flag6 = true;
                                }
                            }
                            if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && !flag7 && !flag8 && !flag9 && !flag10) {
                                if (i >= height0 + 11 * height_per && i <= height0 + 12 * height_per) {
                                    i = i + 3;
                                } else {
                                    i++;
                                }
                                if (i >= height0 + 15 * height_per) {
                                    flag7 = true;
                                }
                            }
                            if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && !flag8 && !flag9 && !flag10) {
                                i = i - 3;
//                                Log.e("jump 437","jump");
                                if (i <= height0 + 14 * height_per) {
                                    flag8 = true;
                                }
                            }
                            if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && !flag9 && !flag10) {
                                if (i >= height0 + 14 * height_per && i <= height0 + 15 * height_per) {
                                    i = i + 3;
                                } else {
                                    i++;
                                }
                                if (i >= height0 + 20 * height_per) {
                                    flag9 = true;
                                }
                            }
                            if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && !flag10) {
                                i = i - 3;
                                timer_xueya.cancel();
//                                Log.e("jump 454","drop");
                                if (i <= height0) {
                                    i = i - 2;
//                                    Log.e("drop 457","drop");
                                    flag10 = true;
                                }
                            }
                        }


                    }
                });

            }
        };
        t.schedule(tt, 0, 20);
    }

    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
            }
        }

        @SuppressLint("NewApi")
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                Camera.Size size = getSmallestPreviewSize(width, height, parameters);
                if (size != null) {
                    parameters.setPreviewSize(size.width, size.height);
                    // Log.d(TAG, "Using width=" + size.width + " height=" +
                    // size.height);
                }
                camera.setParameters(parameters);
                camera.startPreview();
            } catch (Exception e) {

            }


        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // Ignore
        }
    };

    @SuppressLint("NewApi")
    private static Camera.Size getSmallestPreviewSize(int width, int height,
                                                      Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea)
                        result = size;
                }
            }
        }
        return result;
    }

    private static Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        int hjl = 0;
        int hjl2;

        private int beatsAvg;

        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null)
                throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null)
                throw new NullPointerException();
            if (!processing.compareAndSet(false, true))
                return;
            int width = size.width;
            int height = size.height;

            // 图像处理
            hjl2 = hjl;
            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(),
                    height, width);
            hjl = imgAvg;
            if (Math.abs(hjl2 - hjl) > 0) {
                gx = hjl;

            } else {
                gx = 0;
            }

            // text1.setText("平均像素值是" + String.valueOf(imgAvg));
            // 像素平均值imgAvg,日志
            // Log.i(TAG, "imgAvg=" + imgAvg);
            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }

            int averageArrayAvg = 0;
            /**
             * 记录变化的次数
             */
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt)
                    : 0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    flag = 0;
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize)
                averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
                // image.postInvalidate();
            }
            // 获取系统结束时间（ms）
            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 2) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180 || imgAvg < 200) {
                    // 获取系统开始时间（ms）
                    startTime = System.currentTimeMillis();
                    // beats心跳总数
                    beats = 0;
                    processing.set(false);
                    return;
                }
                // ssssss(TAG, "totalTimeInSecs=" + totalTimeInSecs + " beats="+
                // beats);
                if (beatsIndex == beatsArraySize)
                    beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;
                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                beatsAvg = (beatsArrayAvg / beatsArrayCnt);
                xinlv = String.valueOf(beatsAvg);

                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }

    };

    @Override
    protected void onStop() {
        if (null != vibrator) {
            vibrator.cancel();
        }
        if (null != timer1) {
            timer1.cancel();
        }
        super.onStop();
    }

    // 曲线
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 当结束程序时关掉Timer
        timer.cancel();
        timer1.cancel();
        t.cancel();
        timer_xueya.cancel();

    }

    boolean start = false;

    /**
     * 哪里要调用就执行这行代码
     **/
    public void play_voice() {
        start = true;
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        float volume = (float) current / max;
        soundPool.play(music, volume, volume, 0, 0, 1);
    }

    private void updateChart() {

        // 设置好下一个需要增加的节点
        // addX = 10;
        // addY = (int)(Math.random() * 90 + 50);
        // addY = (int)(HardwareControler.readADC());
        // addY=10+addY;
        // if(addY>1400)
        // addY=10;


        if (flag == 1)
            addY = 10;
        else {
            // addY=250;

           /* if (gx>0){
                if (ijs==0){
                    gx=0;
                    ijs++;
                }else {
                    ijs=0;
                }
            }*/
            if (gx > 200) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                set.start();
                            }
                        });

                    }
                }).start();
            }
            flag = 1;
            if (gx == 0) {
                return;
            }
            if (gx < 200) {
                if (hua[10] > 5) {
                    Toast.makeText(XueYaJcAty.this, getResources().getString(R.string.PleaseFlashlight),
                            Toast.LENGTH_SHORT).show();
                    hua[10] = 0;
                }


                hua[10]++;

                return;
            } else
                hua[10] = 10;


            if (hua[6] == 14) {
                start = true;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        play_voice();
//                    }
//                }).start();

                /*long[] pattern = {800, 50, 400, 30}; // OFF/ON/OFF/ON...
                vibrator.vibrate(pattern, -1);// -1不重复，非-1为从pattern的指定下标开始重复*/
                /*runOnUiThread(new Runnable() {
                    @Override
					public void run() {
						set.start();
					}
				});*/
            }
            j = 0;

        }
        if (j < 10) {
            addY = hua[j];
            j++;
        }

        // 移除数据集中旧的点集
        mDataset.removeSeries(series);


        int length = series.getItemCount();

        if (length <= 300) {
            series.add(300, 10);
            for (int k = 0; k < 300; k++) {
                series.add(k, 10);
            }
            mDataset.addSeries(series);

        } else {
            int bz = 0;

            if (length > 300) {
                length = 300;
                bz = 1;
            }

            addX = length;
            for (int i = 0; i < length; i++) {

                xv[i] = (int) series.getX(i) - bz;
                yv[i] = (int) series.getY(i);
            }

            // 点集先清空，为了做成新的点集而准备
            series.clear();
            mDataset.addSeries(series);
            // 将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
            // 这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点
            series.add(addX, addY);
            for (int k = 0; k < length; k++) {
                series.add(xv[k], yv[k]);
            }

            // 在数据集中添加新的点集
            // mDataset.addSeries(series);

            // 视图更新，没有这一步，曲线不会呈现动态
            // 如果在非UI主线程中，需要调用postInvalidate()，具体参考api
            new Thread(new Runnable() {
                @Override
                public void run() {
                    chart.postInvalidate();
                }
            }).start();

        }


    }

    /**
     * 时间进度条调用方法
     */
    public int dowork() {
//        data[hasdata++] = (int) (Math.random() * 100);
        hasdata++;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hasdata;

    }

    private void kaiqi() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                while (status < 14) {
                    status = dowork();
                    Log.e("线程开了", status + "");
                    mphandler.sendEmptyMessage(0);
                }
            }
        }.start();

        Frame_layout1.setBackgroundResource(R.drawable.bj_03xinlv);
        ydbj_nb.setVisibility(View.VISIBLE);

        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateChart();
                    }
                });
            }
        };
        timer.schedule(task, 1, 15); // 曲线
        timer1.schedule(task1, 2000, 2000); // timeTask
    }

    TimerTask task1 = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread

                private int diYa;
                private int gaoYa;

                @Override
                public void run() {
                    recLen--;
                    if (xinlv != null&&start) {
//                        list.add(xinlv);
                        int a = Integer.parseInt(xinlv);
                        Logger.e("aaaa", a + "&&&");
                        if (a < 60) {
                            a = 60;
                        } else if (a > 100) {
                            a = 100;
                        }
                        list.add(a + "");
                        int gaoYa1 = (int) ((0.0318 * a + 5.25) / (0.133 * 0.44));
                        int diYa1 = (int) (gaoYa1 * 0.4 / 0.72);
//                        int diYa1 = (int) (a * 1.1);
//                        int gaoYa1 = (int) (a * 1.65);
                        teshuziti_xygao.setText(gaoYa1 + "");
                        teshuziti_xydi.setText(diYa1 + "");
                    }

                    if (i < height0) {
                        timer1.cancel();
                        int size = list.size();
                        if (size != 0) {
                            arrs = (String[]) list.toArray(new String[size]);
                            stringToInts = StringToInt(arrs);
                            int ave = getAve(stringToInts);
//                            ave = ave - 7;
                            gaoYa = (int) ((0.0318 * ave + 5.25) / (0.133 * 0.44));
                            diYa = (int) (gaoYa * 0.4 / 0.72);
//                            diYa = (int) Math.rint(ave * 1.1);
//                            gaoYa = (int) Math.rint(ave * 1.65);
//                            int ave1 = getAve(stringToInts);
//                            int ave2 = ave1 - 7;
//                            final int diYa = (int) Math.rint(ave2 * 1.1);
//                            final int gaoYa = (int) Math.rint(ave2 * 1.65);
//                            final int diYa = Integer.parseInt(teshuziti_xydi.getText().toString());
//                            final int gaoYa = Integer.parseInt(teshuziti_xygao.getText().toString());
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(DataUrl.class).addXueYa(
                                    userId, gaoYa + "", diYa + "", nowTime, phone, secret, LanguageUtil.judgeLanguage()), 0);
                            Bundle bundle = new Bundle();
                            bundle.putString("diYa", String.valueOf(diYa));
                            bundle.putString("gaoYa", String.valueOf(gaoYa));
//                            startActivity(KsCeShiJieGuo.class, bundle);
                            startActivity(XueYaJieGuoAty.class, bundle);
                            finish();


                        } else {
                            Toast.makeText(XueYaJcAty.this, getResources().getString(R.string.incorrectPosture), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            });
        }
    };

    /**
     * 平均数
     *
     * @param array
     * @return
     */
    public static int getAve(int[] array) {
        int ave = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        ave = sum / array.length;
        return ave;
    }

    /**
     * String 转 int
     *
     * @param arrs
     * @return
     */
    public int[] StringToInt(String[] arrs) {
        int[] ints = new int[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }

    private void tubiao() {
        // 这里获得main界面上的布局，下面会把图表画在这个布局里面
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.linearlayout1_xinlv);
        // 这个类用来放置曲线上的所有点，是一个点的集合，根据这些点画出曲线
        // series = new XYSeries(title);
        series = new XYSeries(title);

        // 创建一个数据集的实例，这个数据集将被用来创建图表
        mDataset = new XYMultipleSeriesDataset();

        // 将点集添加到这个数据集中
        mDataset.addSeries(series);
        // 以下都是曲线的样式和属性等等的设置，renderer相当于一个用来给图表做渲染的句柄
        int color = Color.TRANSPARENT;
        PointStyle style = PointStyle.POINT;
        renderer = buildRenderer(color, style, true);
        renderer.setPanEnabled(false, false);
        renderer.setZoomEnabled(false);
        renderer.setShowAxes(false);
        renderer.setShowLegend(false);
        renderer.setShowLabels(false);


        // 设置好图表的样式
        setChartSettings(renderer, "X", "Y", 0, 300, 4, 16,
                Color.argb(0x00, 0x01, 0x01, 0x01),
                Color.argb(0x00, 0x01, 0x01, 0x01));

        // 生成图表
        chart = ChartFactory.getLineChartView(context, mDataset, renderer);
        renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
        // 将图表添加到布局中去
        linearlayout1_xinlv.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    protected XYMultipleSeriesRenderer buildRenderer(int color,
                                                     PointStyle style, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        // 设置图表中曲线本身的样式，包括颜色、点的大小以及线的粗细等
        r = new XYSeriesRenderer();
        r.setColor(Color.BLACK);
        // r.setPointStyle(null);
        // r.setFillPoints(fill);
        r.setPointStyle(PointStyle.X); // 折线点的样式
        r.setLineWidth(3);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        try {
            camera = Camera.open();
        } catch (Exception e) {
//            new MaterialDialog(this)
//                    .setMDTitle(getResources().getString(R.string.importance))
//                    .setMDMessage("需要开启摄像头权限！")
//                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
//                        @Override
//                        public void dialogBtnOnClick() {
//                            Uri packageURI = Uri.parse("package:" + "com.zhixinyisheng.user");
//                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                            startActivity(intent);
//
//                        }
//                    })
//                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
//                        @Override
//                        public void dialogBtnOnClick() {
//                            finish();
//                        }
//                    })
//                    .show();

            canUse = false;
        }

        startTime = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        t.cancel();
        wakeLock.release();
//        if (canUse) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void setChartSettings(XYMultipleSeriesRenderer renderer,
                                    String xTitle, String yTitle, double xMin, double xMax,
                                    double yMin, double yMax, int axesColor, int labelsColor) {
        // 有关对图表的渲染可参看api文档
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
        // renderer.setShowGrid(true);
        // renderer.setGridColor(Color.GREEN);
        renderer.setXLabels(10);
        renderer.setYLabels(10);
        renderer.setXTitle("Time");
        renderer.setZoomEnabled(false);
        renderer.setYTitle("mmHg");
        renderer.setYLabelsAlign(Paint.Align.RIGHT);

        renderer.setPointSize((float) 2);
        renderer.setShowLegend(false);
        renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
    }

    @Override
    public void requestData() {

    }

}
