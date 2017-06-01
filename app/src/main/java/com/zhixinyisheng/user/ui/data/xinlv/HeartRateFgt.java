package com.zhixinyisheng.user.ui.data.xinlv;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
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
import com.zhixinyisheng.user.ui.messure.XinLvJcActivity;
import com.zhixinyisheng.user.util.Colors;
import com.zhixinyisheng.user.util.DateUtils;
import com.zhixinyisheng.user.util.DensityUtils;
import com.zhixinyisheng.user.util.PermissionsUtil;
import com.zhixinyisheng.user.util.tool.ImageProcessing;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
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

import static com.zhixinyisheng.user.R.id.ll_seekbar;
import static com.zhixinyisheng.user.R.id.tv_result;
import static com.zhixinyisheng.user.config.HttpIdentifier.IS_CHANGE_HEART_DATA;


/**
 * 心率（新）
 * Created by 焕焕 on 2017/4/19.
 */

public class HeartRateFgt extends BaseFgt {
    @Bind(R.id.rl_chart)
    RelativeLayout rlChart;
    @Bind(R.id.iv_chart_arrow)
    ImageView ivChartArrow;
    @Bind(R.id.fl_chart)
    FrameLayout flChart;
    @Bind(R.id.iv_heart)
    ImageView ivHeart;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.btn_messure)
    Button btnMessure;
    @Bind(tv_result)
    TextView tvResult;
    @Bind(R.id.tv_bpm)
    TextView tvBpm;
    @Bind(R.id.rl_sv)
    RelativeLayout rlSv;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.iv_seekbar)
    ImageView ivSeekbar;
    @Bind(ll_seekbar)
    LinearLayout llSeekbar;
    @Bind(R.id.rl_seekbar)
    RelativeLayout rlSeekbar;
    @Bind(R.id.iv_point)
    ImageView ivPoint;
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    @Bind(R.id.iv_cover_stop)
    ImageView ivCoverStop;
    private String currentTime;
    private AnimatorSet set;//笔的动画
    private XYSeries series;
    private XYMultipleSeriesDataset mDataset;
    private String title = "pulse";
    private GraphicalView chart;
    private XYMultipleSeriesRenderer renderer;
    private XYSeriesRenderer r;
    /*声音初始化*/
    private SoundPool soundPool;
    private int music;
    private int addX = -1;
    double addY;
    int[] xv = new int[300];
    int[] yv = new int[300];
    int[] hua = new int[]{10, 9, 10, 11, 12, 13, 14, 13, 12, 11, 10};
    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private static SurfaceHolder previewHolder = null;
    private static Camera camera = null;
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    private static int gx;
    private static int j;
    private static XinLvJcActivity.TYPE currentType = XinLvJcActivity.TYPE.GREEN;

    public static XinLvJcActivity.TYPE getCurrent() {
        return currentType;
    }

    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];
    private static double beats = 0;//脉搏数
    private static long startTime = 0;
    private static String xinlv = null;//心率数
    private static double flag = 1;
    int start = 0;
    AudioManager mAudioManager;
    private Timer timerChart;
    private static String[] arrs;
    private static int[] stringToInts;
    private static List<String> listResult = new ArrayList<>();//获取心率数的集合
    private static int heartResult;
    private static boolean isStart = false;//是否是正在测量
    private static int beatsAvg;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_heart_rate;
    }

    @Override
    public void initData() {
        IntentFilter intentFilter = new IntentFilter(Constant.MESSURE_STOP);
        getActivity().registerReceiver(messureStopReceiver, intentFilter);

    }


    private void intFront() {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "stencilstd.otf");
        tvResult.setTypeface(type);
        tvBpm.setTypeface(type);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
        tvTime.setText(currentTime);
        intFront();//初始化字体
        initChart();
        initEquipment();
        initVoice2();
        initArrowAnimator();

    }

    BroadcastReceiver messureStopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (heartResult == 0) {
                ivCoverStop.setVisibility(View.VISIBLE);
            }
            stopMessure();
        }
    };


    private void initEquipment() {
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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

    TimerTask taskChart;

    /**
     * 开启测量
     */
    private void startMessure() {

        flChart.setBackgroundResource(R.drawable.bj_03xinlv);
        ivCoverStop.setVisibility(View.GONE);
//        nbChart.setVisibility(View.VISIBLE);
        taskChart = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateChart();
                        setIvHeart();
                        if (heartResult != 0) {
                            tvResult.setText(heartResult + "");
                            tvResult.setTextColor(Colors.MESSURE);
                            currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
                            tvTime.setText(currentTime);
                            Intent intent = new Intent(Constant.MAIN_BOTTOM_VISIABLE);//主页底栏显示
                            getActivity().sendBroadcast(intent);
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(DataUrl.class).addXinLv(
                                    userId, heartResult + "", currentTime, phone, secret), HttpIdentifier.HEART_RATE_MESSURE);
                            stopMessure();
                        }
                    }
                });
            }
        };
        timerChart = new Timer();
        timerChart.schedule(taskChart, 1, 15); // 曲线
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        IS_CHANGE_HEART_DATA = true;
    }

    private void setIvHeart() {
        if (beatsAvg < 20) {
            ivHeart.setImageResource(R.mipmap.ic_heart1);
        } else if (beatsAvg < 40) {
            ivHeart.setImageResource(R.mipmap.ic_heart2);
        } else if (beatsAvg < 60) {
            ivHeart.setImageResource(R.mipmap.ic_heart3);
        } else if (beatsAvg < 80) {
            ivHeart.setImageResource(R.mipmap.ic_heart4);
        } else if (beatsAvg < 100) {
            ivHeart.setImageResource(R.mipmap.ic_heart5);
        } else if (beatsAvg < 130) {
            ivHeart.setImageResource(R.mipmap.ic_heart6);
        } else {
            ivHeart.setImageResource(R.mipmap.ic_heart7);
        }
    }

    /**
     * 停止测量
     */
    private void stopMessure() {
        //TODO 停止测量
        isStart = false;
        try {
            if (isVisibleToUser) {

            }
            surfaceView.setVisibility(View.GONE);
            if (heartResult != 0) {
                btnMessure.setText(R.string.chongxinceliang);
                tvNotice.setVisibility(View.GONE);
                llSeekbar.setVisibility(View.VISIBLE);
                float d = 1.5f * heartResult;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivPoint.getLayoutParams();
                params.leftMargin = DensityUtils.dp2px(d - 5);
                ivPoint.setLayoutParams(params);
                heartResult = 0;
            } else {
                btnMessure.setText(R.string.kaishiceliang);
                tvNotice.setVisibility(View.VISIBLE);
                llSeekbar.setVisibility(View.GONE);
            }
            listResult.clear();
//            heartResult = 0;
            flChart.setBackgroundResource(R.drawable.ct_zzcl_img_bg_s);
//            nbChart.stopTimer();
//            nbChart.setVisibility(View.GONE);
            if (null != timerChart) {
                timerChart.cancel();
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

    private void initVoice2() {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = soundPool.load(getActivity(), R.raw.dioneminite, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
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
        this.isVisibleToUser = isVisibleToUser;
        if (!isVisibleToUser) {
            stopCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    private void stopCamera() {
        try {

            if (tvResult.getText().toString().equals("0")) {
                try {
                    ivCoverStop.setVisibility(View.VISIBLE);
                    tvResult.setText("0");
                    tvResult.setTextColor(Colors.MESSURE_ZERO);
                    currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stopMessure();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setMessureBtnTrue() {
        btnMessure.setBackgroundResource(R.drawable.shape_main_color);
//        btnMessure.setText("开始测量");
        btnMessure.setEnabled(true);
    }

    private void updateChart() {
        if (flag == 1)
            addY = 10;
        else {
            if (gx > 200) {
                set.start();
            }
            flag = 1;
            if (gx == 0) {
                return;
            }
            if (gx < 200) {
                if (hua[10] > 5) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.PleaseFlashlight),
                            Toast.LENGTH_SHORT).show();
                    hua[10] = 0;
                }
                hua[10]++;

                return;
            } else
                hua[10] = 10;


            if (hua[6] == 14) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        play_voice();

                    }
                }).start();

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
     * TODO 哪里要调用就执行这行代码
     **/
    public void play_voice() {
        start++;
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        float volume = (float) current / max;
        soundPool.play(music, volume, volume, 0, 0, 1);
    }


    /**
     * 初始化图标
     */
    private void initChart() {
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
        chart = ChartFactory.getLineChartView(getActivity(), mDataset, renderer);
        renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
        // 将图表添加到布局中去
        rlChart.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
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
            XinLvJcActivity.TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = XinLvJcActivity.TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    flag = 0;
                }
            } else if (imgAvg > rollingAverage) {
                newType = XinLvJcActivity.TYPE.GREEN;
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
                if (listResult.size() == 5) {
//                    arrs = listResult.toArray(new String[4]);
                    stringToInts = StringToInt(listResult);
                    heartResult = getAve(stringToInts);
                }
                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }

    };

    /**
     * 初始化笔的动画
     */
    private void initArrowAnimator() {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(ivChartArrow, "TranslationY", ivChartArrow.getTop() + 5, ivChartArrow.getTop() - 50);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(ivChartArrow, "TranslationY", ivChartArrow.getTop() - 50, ivChartArrow.getTop());
        set = new AnimatorSet();
        set.playSequentially(animator2, animator3);
        set.setDuration(2);
    }

    SurfaceView surfaceView;

    @OnClick(R.id.btn_messure)
    public void onClick() {
        //TODO 点击
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
//        ivCover.setVisibility(View.GONE);

        Intent intent = new Intent(Constant.MAIN_BOTTOM_INVISIABLE);//主页底栏消失
        getActivity().sendBroadcast(intent);


        tvResult.setText("0");
        tvResult.setTextColor(Colors.MESSURE_ZERO);
        currentTime = DateUtils.timeStampToStr(System.currentTimeMillis());//获取当前时间
        tvTime.setText(currentTime);
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

    /**
     * 设置测量按钮为不可点击
     */
    private void setMessureBtnFalse() {
        btnMessure.setBackgroundResource(R.drawable.shape_gray_color);
        btnMessure.setText(R.string.zhengzaiceliang);
        btnMessure.setEnabled(false);
    }

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
    public static int[] StringToInt(List<String> arrs) {
        int[] ints = new int[arrs.size()];
        for (int i = 0; i < arrs.size(); i++) {
            ints[i] = Integer.parseInt(arrs.get(i));
        }
        return ints;
    }

}
