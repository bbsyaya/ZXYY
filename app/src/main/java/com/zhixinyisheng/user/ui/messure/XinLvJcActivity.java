package com.zhixinyisheng.user.ui.messure;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 心率监测
 * Created by 焕焕 on 2016/10/20.
 */
public class XinLvJcActivity extends BaseAty {
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    //    @Bind(R.id.title)
//    RelativeLayout title;
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
    @Bind(R.id.woshixinlvshijian_text)
    TextView woshixinlvshijian_text;
    @Bind(R.id.teshuziti_xl)
    TextView teshuziti_xl;
    @Bind(R.id.wodebpm)
    TextView wodebpm;
    @Bind(R.id.xinlv_img)
    ImageView xinlv_img;
    @Bind(R.id.xinlv_linear)
    LinearLayout xinlv_linear;
    @Bind(R.id.teshuziti_xygao)
    TextView teshuziti_xygao;
    @Bind(R.id.wodegaoy)
    TextView wodegaoy;
    @Bind(R.id.teshuziti_xydi)
    TextView teshuziti_xydi;
    @Bind(R.id.wodediya)
    TextView wodediya;
    @Bind(R.id.xueya_linear)
    LinearLayout xueya_linear;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @Bind(R.id.xinlv_progress)
    ProgressBar xinlv_progress;
    @Bind(R.id.linearLayout2)
    FrameLayout linearLayout2;
    // 曲线
    private Timer timer = new Timer();
    private TimerTask task;
    private static int gx;
    private static int j;
    public static XinLvJcActivity instance;
    private static double flag = 1;
    //    private Handler handler;
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
    private Camera.Parameters parameters = null;
    // private static View image = null;
    private static TextView text = null;
    private static TextView text1 = null;
    private static TextView text2 = null;
    //    private static PowerManager.WakeLock wakeLock = null;
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    private AnimatorSet set;


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
    private int volume;//音量的大小
    AudioManager mAudioManager;
    @SuppressLint("HandlerLeak")
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_xinlvjc;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void initData(Bundle savedInstanceState) {
        instance = this;

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
        teshuziti_xl.setTypeface(type);
        wodebpm.setTypeface(type);
        teshuziti_xygao.setTypeface(type);
        wodegaoy.setTypeface(type);
        teshuziti_xydi.setTypeface(type);
        wodediya.setTypeface(type);
        username = Content.XVORXY;//0是心率，1是血压
        if (username.equals("1")) {
            cjsTvt.setText(getResources().getString(R.string.home_bloodPressure));
            xueya_linear.setVisibility(View.VISIBLE);
        } else if (username.equals("0")) {
            cjsTvt.setText(getResources().getString(R.string.home_heartRate));
            xinlv_linear.setVisibility(View.VISIBLE);
        }
        tubiao();
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);

        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        initVoice2();
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        wakeLock = pm
//                .newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
//        wakeLock.acquire();
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_ceshi);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview.setVisibility(View.VISIBLE);

                try {
                    kaiqi();
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("xinlv", e.toString());
                }
                try {
//                    previewHolder = preview.getHolder();
//                    previewHolder.addCallback(surfaceCallback);
//                    previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//                    camera = Camera.open();
//                    Camera.Parameters parameters = camera.getParameters();
//                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//                    camera.setParameters(parameters);

                } catch (Exception e) {
                    Logger.e("2323 error", e.toString());
                    canUse = false;
                }
                startTime = System.currentTimeMillis();
            }
        });
    }

    private void initVoice2() {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = soundPool.load(context, R.raw.dioneminite, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
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

//        new Thread() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                super.run();
//
//                while (status < 14&&start) {
//                    status = dowork();
//                    Log.e("线程开了", status + "");
//                    mphandler.sendEmptyMessage(0);
//                }
//            }
//        }.start();

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


                    if (xinlv != null && start != 0) {
                        recLen--;
                        list.add(xinlv);
                        int a = Integer.parseInt(xinlv);
                        if (a < 60) {
                            a = 60;
                        }
                        a = a - 7;
                        if (a > 95) {
                            a = 95;
                        }
                        int diYa1 = (int) (a * 1.1);
                        if (diYa1 > 95) {
                            diYa1 = 95;
                        }
                        int gaoYa1 = (int) (a * 1.65);
                        if (gaoYa1 > 135) {
                            gaoYa1 = 135;
                        }
                        if (username.equals("1")) { // 血压
                            teshuziti_xygao.setText(gaoYa1 + "");
                            teshuziti_xydi.setText(diYa1 + "");

                        } else if (username.equals("0")) {//心率
                            /*bar2.setCurrentValues(a);*/
                            teshuziti_xl.setText(a + "");

                            if (a < 20) {
                                xinlv_img.setImageResource(R.drawable.ct_zzcl_img_xl_1);
                            } else if (a < 40) {
                                xinlv_img.setImageResource(R.drawable.ct_zzcl_img_xl_2);
                            } else if (a < 60) {
                                xinlv_img.setImageResource(R.drawable.ct_zzcl_img_xl_3);
                            } else if (a < 80) {
                                xinlv_img.setImageResource(R.drawable.ct_zzcl_img_xl_4);
                            } else if (a < 100) {
                                xinlv_img.setImageResource(R.drawable.ct_zzcl_img_xl_5);
                            } else {
                                xinlv_img.setImageResource(R.drawable.ct_zzcl_img_xl_6);
                            }
                        }
                    }

                    if (recLen < 0
                            || xinlv_progress.getProgress() == 100) {
                        timer1.cancel();
                        int size = list.size();
                        if (size != 0) {
                            arrs = (String[]) list.toArray(new String[size]);
                            stringToInts = StringToInt(arrs);
                            int ave = getAve(stringToInts);
                            ave = ave - 7;
                            diYa = (int) Math.rint(ave * 1.1);
                            if (diYa > 95) {
                                diYa = 95;
                            }
                            gaoYa = (int) Math.rint(ave * 1.65);
                            if (gaoYa > 139) {
                                gaoYa = 139;
                            }
                            if (username.equals("0")) {
//                                post1();  // 将测试结果传到服务器,暂时注释 TODO
//                                post();
                                int ave1 = getAve(stringToInts);
                                int ave2 = ave1 - 7;
                                final int diYa = (int) Math.rint(ave2 * 1.1);
                                final int gaoYa = (int) Math.rint(ave2 * 1.65);
                                showLoadingDialog(null);
                                doHttp(RetrofitUtils.createApi(DataUrl.class).addXinLv(
                                        userId, ave1 + "", nowTime, phone, secret), 0);
//                                doHttp(RetrofitUtils.createApi(DataUrl.class).addXueYa(
//                                        userId, gaoYa + "",diYa+"", nowTime, phone, secret), 0);
                                Bundle bundle = new Bundle();
                                bundle.putString("xinLv", String.valueOf(ave1));
                                startActivity(KsCeShiJieGuo.class, bundle);
                                finish();


                            } else if (username.equals("1")) {
//                                post1();
//                                post();
                                int ave1 = getAve(stringToInts);
                                int ave2 = ave1 - 7;
                                final int diYa = (int) Math.rint(ave2 * 1.1);
                                final int gaoYa = (int) Math.rint(ave2 * 1.65);
                                showLoadingDialog(null);
//                                doHttp(RetrofitUtils.createApi(DataUrl.class).addXinLv(
//                                        userId, ave1 + "", nowTime, phone, secret), 0);
                                doHttp(RetrofitUtils.createApi(DataUrl.class).addXueYa(
                                        userId, gaoYa + "", diYa + "", nowTime, phone, secret, LanguageUtil.judgeLanguage()), 0);
                                Bundle bundle = new Bundle();
                                bundle.putString("diYa", String.valueOf(diYa));
                                bundle.putString("gaoYa", String.valueOf(gaoYa));
                                startActivity(KsCeShiJieGuo.class, bundle);
                                finish();

//                                Intent intent = new Intent(
//                                        XinLvJcActivity.this,
//                                        KsCeShiJieGuo.class);
//                                intent.putExtra("diYa", String.valueOf(diYa));
//                                intent.putExtra("gaoYa", String.valueOf(gaoYa));
//                                //intent.putExtra("xinLv", String.valueOf(ave));
//                                startActivity(intent);
//                                finish();
                            }
//                            finish();

                        } else {
                            Toast.makeText(XinLvJcActivity.this, getResources().getString(R.string.incorrectPosture), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            });
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

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case 0:
//                showToast("添加成功");
                break;
        }
        super.onSuccess(result, call, response, what);

    }

    // 曲线
    @Override
    public void onDestroy() {
        // 当结束程序时关掉Timer
        timer.cancel();
        timer1.cancel();
        super.onDestroy();
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

    int ijs = 0;

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
                    Toast.makeText(XinLvJcActivity.this, getResources().getString(R.string.PleaseFlashlight),
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


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
//        wakeLock.release();
//        if (canUse) {
        try {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
//            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }

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
    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open(0);
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

    private int first = 0;
    int start = 0;

    /**
     * TODO 哪里要调用就执行这行代码
     **/
    public void play_voice() {
        start++;
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        float volume = (float) current / max;
        soundPool.play(music, volume, volume, 0, 0, 1);
        if (start == 1) {
            new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    while (status < 14) {
                        status = dowork();
                        Log.e("线程开了", status + "");
                        mphandler.sendEmptyMessage(0);
                    }
                }
            }.start();
        }
        if (first == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    teshuziti_xl.setText("60");
                    first = 1;
                }
            });
        }
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
    public int[] StringToInt(String[] arrs) {
        int[] ints = new int[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }

    @Override
    public void requestData() {

    }

}
