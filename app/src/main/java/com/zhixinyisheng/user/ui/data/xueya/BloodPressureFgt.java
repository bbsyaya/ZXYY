package com.zhixinyisheng.user.ui.data.xueya;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.data.shezhen.SheZhenFgt;
import com.zhixinyisheng.user.util.Colors;
import com.zhixinyisheng.user.util.DateUtils;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.PermissionsUtil;
import com.zhixinyisheng.user.util.tool.ImageProcessing;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_BLOOD_DATA;
import static com.zhixinyisheng.user.ui.data.xinlv.HeartRateFgt.StringToInt;
import static com.zhixinyisheng.user.ui.data.xinlv.HeartRateFgt.getAve;

/**
 * Created by 焕焕 on 2017/4/21.
 */

public class BloodPressureFgt extends BaseFgt implements TextWatcher {
    @Bind(R.id.rl_sv)
    RelativeLayout rlSv;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.iv_gongzhu)
    ImageView iv_gongzhu;
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.woshifengx)
    TextView woshifengx;
    @Bind(R.id.woshixinlvshijian_text)
    TextView tvTime;
    @Bind(R.id.teshuziti_xygao)
    TextView teshuziti_xygao;
    @Bind(R.id.wodegaoy)
    TextView wodegaoy;
    @Bind(R.id.xueya_linear)
    LinearLayout xueyaLinear;
    @Bind(R.id.teshuziti_xydi)
    TextView teshuziti_xydi;
    @Bind(R.id.wodediya)
    TextView wodediya;
    @Bind(R.id.rl_result)
    RelativeLayout rlResult;
    @Bind(R.id.btn_messure)
    Button btnMessure;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.tv_listener)
    TextView tvListener;
    private AudioManager mAudioManager;
    private String currentTime;
    int height;
    int height_per;
    int height0;
    ViewGroup.LayoutParams para;
    int i = 0;
    boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7, flag8, flag9, flag10;
    boolean flagXueYa;
    Timer timer_xueya;
    boolean start = false;
    /*声音初始化*/
    private SoundPool soundPool;
    private int music;
    private Timer timerAnimation;
    private static boolean isStart = false;//是否是正在测量
    private List<String> list;
    private String score, nowTime;
    private static String xinlv = null;
    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];
    /**
     * 脉搏数
     */
    private static double beats = 0;
    private static long startTime = 0;
    private static PowerManager.WakeLock wakeLock = null;
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    private static int gx;
    private static int j;
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

    public static enum TYPE {
        GREEN, RED
    }

    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    private static int heartResult;
    private static int beatsAvg;
    private static String[] arrs;
    private static int[] stringToInts;
    private static List<String> listResult = new ArrayList<>();//获取心率数的集合
    private int diYa;
    private int gaoYa;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_blood_pressure;
    }

    @Override
    public void initData() {
        currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
        tvTime.setText(currentTime);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        tvListener.addTextChangedListener(this);
        intFront();//初始化字体
        initVoice2();
        IntentFilter intentFilter = new IntentFilter(Constant.MESSURE_STOP_BLOOD);
        getActivity().registerReceiver(messureStopReceiver, intentFilter);
    }


    BroadcastReceiver messureStopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopMessure();
        }
    };

    private void intFront() {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "stencilstd.otf");
        teshuziti_xygao.setTypeface(type);
        wodegaoy.setTypeface(type);
        teshuziti_xydi.setTypeface(type);
        wodediya.setTypeface(type);
    }


    private void initVoice2() {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = soundPool.load(getActivity(), R.raw.xueya, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
    }

    /**
     * 设置汞柱
     */
    private void setHg() {
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

    SurfaceView surfaceView;

    @OnClick(R.id.btn_messure)
    public void onClick() {
        //TODO 点击事件
        if (PermissionsUtil.is6()) {
            if (toCheckPermission()) {
                startCamera();
            }
        } else {
            startCamera();
        }
    }

    /**
     * 检查权限（运行时权限）
     */
    private boolean toCheckPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != result) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            return false;
        }
        return true;
    }

    private void startCamera() {
        Intent intent = new Intent(Constant.MAIN_BOTTOM_INVISIABLE);//主页底栏消失
        getActivity().sendBroadcast(intent);
        frameLayout.setAlpha(1);
        showToast(getString(R.string.shouzhitongshizhegaishexiangtou));

        currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
        tvTime.setText(currentTime);
        teshuziti_xygao.setText("0");
        teshuziti_xydi.setText("0");
        teshuziti_xygao.setTextColor(Colors.MESSURE_ZERO);
        teshuziti_xydi.setTextColor(Colors.MESSURE_ZERO);
        tvNotice.setText(R.string.benchengxuceshijieguobukexueya);
        isStart = true;
        surfaceView = new SurfaceView(getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500, 600);
        rlSv.addView(surfaceView, params);
        previewHolder = surfaceView.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setMessureBtnFalse();
        try {
            startMessure();
        } catch (Exception e) {
            e.printStackTrace();
        }
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != messureStopReceiver) {
            getActivity().unregisterReceiver(messureStopReceiver);
        }
    }

    boolean isVisibleToUser;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("232323  44444444",isVisibleToUser+"@@@@");
        this.isVisibleToUser = isVisibleToUser;
        if (!isVisibleToUser) {
            try {
                if (teshuziti_xygao.getText().toString().equals("0")) {
                    stopMessure();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (teshuziti_xygao.getText().toString().equals("0")) {
            stopMessure();
        }

    }

    private void startMessure() {
        setHg();
    }

    private void stopMessure() {
        //TODO 停止测量
        isStart = false;
        initFlag();
        try {
//            if (isVisibleToUser) {
//                Intent intent = new Intent(Constant.MAIN_BOTTOM_VISIABLE);//主页底栏显示
//                getActivity().sendBroadcast(intent);
//            }
            frameLayout.setAlpha(0.5f);
            surfaceView.setVisibility(View.GONE);
            if (i < height0) {
                btnMessure.setText(R.string.chongxinceliang);
                i = height0;
                tvNotice.setText(R.string.zhengchanggaoyagaoyadiyajinggao);
            } else {
                btnMessure.setText(R.string.kaishiceliang);
                currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
                tvTime.setText(currentTime);
                teshuziti_xygao.setText("0");
                teshuziti_xydi.setText("0");
                teshuziti_xygao.setTextColor(Colors.MESSURE_ZERO);
                teshuziti_xydi.setTextColor(Colors.MESSURE_ZERO);
                tvNotice.setText(R.string.benchengxuceshijieguobukexueya);
            }
            listResult.clear();
            if (null != timerAnimation) {
                timerAnimation.cancel();
            }
            if (null != timer_xueya) {
                timer_xueya.cancel();
            }
            setMessureBtnTrue();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
            previewHolder = null;
            surfaceView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFlag() {
        flag1 = false;
        flag2 = false;
        flag3 = false;
        flag4 = false;
        flag5 = false;
        flag6 = false;
        flag7 = false;
        flag8 = false;
        flag9 = false;
        flag10 = false;
        flagXueYa = false;
    }

    private void setMessureBtnTrue() {
        btnMessure.setBackgroundResource(R.drawable.shape_main_color);
        btnMessure.setEnabled(true);
    }

    /**
     * 设置测量按钮为不可点击
     */
    private void setMessureBtnFalse() {
        btnMessure.setBackgroundResource(R.drawable.shape_gray_color);
        btnMessure.setText(R.string.zhengzaiceliang);
        btnMessure.setEnabled(false);
    }

    /**
     * 血压汞柱动画
     */
    private void hgAnimation() {
        timerAnimation = new Timer();
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setHgAnimation();
                        if (i < height0) {
                            arrs = listResult.toArray(new String[4]);
                            stringToInts = StringToInt(listResult);
                            heartResult = getAve(stringToInts);//心率测试结果
                            gaoYa = (int) ((0.0318 * heartResult + 5.25) / (0.133 * 0.44));
                            diYa = (int) (gaoYa * 0.4 / 0.72);
                            currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
                            tvTime.setText(currentTime);
                            teshuziti_xygao.setText(gaoYa + "");
                            teshuziti_xydi.setText(diYa + "");
                            teshuziti_xygao.setTextColor(Colors.MESSURE);
                            teshuziti_xydi.setTextColor(Colors.MESSURE);
                            Intent intent = new Intent(Constant.MAIN_BOTTOM_VISIABLE);//主页底栏显示
                            getActivity().sendBroadcast(intent);
                            frameLayout.setAlpha(0.5f);

                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(DataUrl.class).addXueYa(
                                    userId, gaoYa + "", diYa + "", currentTime, phone, secret, LanguageUtil.judgeLanguage()), HttpIdentifier.BLOOD_PRESSURE_MESSURE);
                            stopMessure();
                        }

                    }
                });

            }
        };
        timerAnimation.schedule(tt, 0, 20);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.BLOOD_PRESSURE_MESSURE:
                IS_CHANGE_BLOOD_DATA = true;
                break;
        }
    }

    private void setHgAnimation() {
        para.height = i;
        iv_gongzhu.setLayoutParams(para);
        if (listResult.size() != 0) {
            tvListener.setText(listResult.size() + "");
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
                if (i <= height0) {
                    i = i - 2;
                    flag10 = true;
                }
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (gx < 200 && gx != 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.PleaseFlashlight),
                    Toast.LENGTH_SHORT).show();
            if (listResult.size() != 0) {
                initFlag();
                listResult.clear();
                timer_xueya.cancel();
                i = height0;
            }
            return;
        }


        if (!flagXueYa) {
            flagXueYa = true;
            timer_xueya = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    play_voice();
                }
            };
            timer_xueya.schedule(tt, 0, 3000);
        }

    }

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

    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        public void surfaceCreated(SurfaceHolder holder) {
            if (!isStart) {
                return;
            }
            try {
                SheZhenFgt.camera.setPreviewCallback(null);
                SheZhenFgt.camera.stopPreview();//停掉原来摄像头的预览
                SheZhenFgt.camera.release();//释放资源
                SheZhenFgt.camera = null;//取消原来摄像头
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                camera = Camera.open(0);
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("NewApi")
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            if (!isStart) {
                return;
            }
            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                Camera.Size size = getSmallestPreviewSize(width, height, parameters);//这行代码是必须的！！！
                if (size != null) {
                    parameters.setPreviewSize(size.width, size.height);
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
                //TODO 获得心率数
                listResult.add(xinlv);
//                if (listResult.size() == 8) {
//                    arrs = listResult.toArray(new String[4]);
//                    stringToInts = StringToInt(arrs);
//                    heartResult = getAve(stringToInts);
//                }
                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }

    };

}
