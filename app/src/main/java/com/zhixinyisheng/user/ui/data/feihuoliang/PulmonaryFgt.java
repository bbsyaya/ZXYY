package com.zhixinyisheng.user.ui.data.feihuoliang;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.PulmonaryListAdapter;
import com.zhixinyisheng.user.adapter.PulmonaryTrendAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.datas.PulmonaryDatas;
import com.zhixinyisheng.user.domain.datas_zhexiantu.PulmonaryZXT;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.MyViewBD;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 肺活量(新)
 * Created by 焕焕 on 2017/1/14.
 */
public class PulmonaryFgt extends BaseFgt implements Animator.AnimatorListener {
    private static final int RP_RADIO = 1;
    @Bind(R.id.fhl_ll_cq)
    LinearLayout ll_cq;
    @Bind(R.id.fhl_ll_yc)
    LinearLayout ll_yc;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.fhl_tv)
    TextView tvFhl;
    @Bind(R.id.zhi)
    TextView zhi;
    @Bind(R.id.fhl_btn)
    Button fhl_btn;
    @Bind(R.id.lv_pulmonary)
    ListView lvPulmonary;
    @Bind(R.id.ll_rili)
    LinearLayout llRili;
    @Bind(R.id.sv_zhexiantu)
    ScrollView svZhexiantu;
    @Bind(R.id.pulmonary_mv)
    MyViewBD mv;
    @Bind(R.id.lv_pulmonary_trend)
    ListView lv_pulmonary_trend;
    ImageView ivCattle;
    Object mLock;
    int i;
    private float ScreenHeight, ScreenWidth;
    int int_volume = 0;
    private boolean isStartScale, isStart;
    String str_volume = "";
    private ObjectAnimator mTranslation;
    private ObjectAnimator mScale;
    boolean is_dy7 = false;
    boolean isTimer = true;
    private Timer timer1, timer_hqfhl, timer_begin;
    private TimerTask tt1, tt_hqfhl, tt_begin;
    AudioRecord mAudioRecord;
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.ENCODING_PCM_16BIT);
    //边界值
    private int mThreshold = 73;
    PulmonaryListAdapter pulmonaryListAdapter;
    PulmonaryDatas pulmonaryDatas;
    List<String> Xlabel = new ArrayList<String>(),
            data = new ArrayList<String>(), data1 = new ArrayList<String>(),
            Ylabel = new ArrayList<String>();

    @Override
    public int getLayoutId() {
        return R.layout.fgt_pulmonary;
    }

    @Override
    public void initData() {
        Content.vitalCapacity++;
        mLock = new Object();
        try {
            lvPulmonary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pulmonaryListAdapter.setSelectedPosition(position);
                    pulmonaryListAdapter.notifyDataSetInvalidated();
                    zhi.setVisibility(View.VISIBLE);
                    i = (int) pulmonaryDatas.getList().get(position).getNUM();
                    if (i < 2500) {
                        zhi.setText(getResources().getString(R.string.delicateAndSoft));
                    } else if (i < 5000) {
                        zhi.setText(getResources().getString(R.string.LungNormal));
                    } else if (i < 8500) {
                        zhi.setText(getResources().getString(R.string.LungStrong));
                    } else if (i < 10500) {
                        zhi.setText(getResources().getString(R.string.WowCongratulations));
                    } else {
                        zhi.setText(getResources().getString(R.string.OhMyGod));
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager wm1 = getActivity().getWindowManager();
        ScreenWidth = wm1.getDefaultDisplay().getWidth();
        ScreenHeight = wm1.getDefaultDisplay().getHeight();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mAnimationDrawable = null;
        mTranslation = null;
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
                if (int_volume >= 38) {
                    tvFhl.setText(str_volume);
                }
                if (!isStart && int_volume > 38) {
                    handler.removeMessages(3);
                    //开始动画
                    cattleMoveAnimationStart();
                    isStart = true;
                }

            } else if (msg.what == 1) {//小于70完成测试

                cattleMoveAnimationEnd();
                cattleManifyAniEnd();

                is_dy7 = false;//回值
                timer1.cancel();//结束动画
                isTimer = true;//下一次可以再次开始动画
                //取消获取值
                timer_hqfhl.cancel();
                if (mAudioRecord != null) {
                    //初始化测声音变量
                    mAudioRecord.stop();
                    mAudioRecord.release();
                    mAudioRecord = null;
                }

                if (!str_volume.equals("")) {
                    i = Integer.parseInt(str_volume);
                }
                if (i < 2500) {
                    zhi.setText(getResources().getString(R.string.delicateAndSoft));
                } else if (i < 5000) {
                    zhi.setText(getResources().getString(R.string.LungNormal));
                } else if (i < 8500) {
                    zhi.setText(getResources().getString(R.string.LungStrong));
                } else if (i < 10500) {
                    zhi.setText(getResources().getString(R.string.WowCongratulations));
                } else {
                    zhi.setText(getResources().getString(R.string.OhMyGod));
                }
                if (i > 100) {
//                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DataUrl.class).addPulmonary(
                            userId, i + "", time, phone, secret), HttpIdentifier.PULMONARY_ADD);
                }
                zhi.setVisibility(View.VISIBLE);
                fhl_btn.setText(getResources().getString(R.string.tryAgain));
                fhl_btn.setVisibility(View.VISIBLE);
                ll_cq.setVisibility(View.GONE);
                ll_yc.setVisibility(View.GONE);

            } else if (msg.what == 2) {//提交数据
//                dg_shen.dismisssDialog();
                showToast(getResources().getString(R.string.submitSuccess));
//                finish();
            } else if (msg.what == 3) {//测试开始但是没达到70就结束

                cattleMoveAnimationEnd();
                cattleManifyAniEnd();

                fhl_btn.setText(getResources().getString(R.string.tryAgain));
                fhl_btn.setVisibility(View.VISIBLE);
                zhi.setVisibility(View.VISIBLE);
                ll_cq.setVisibility(View.GONE);
                ll_yc.setVisibility(View.GONE);
                //取消获取值
                timer_hqfhl.cancel();
                if (mAudioRecord != null) {
                    //初始化测声音变量
                    mAudioRecord.stop();
                    mAudioRecord.release();
                    mAudioRecord = null;
                }
                showToast(getString(R.string.qingduizhunshoujimaikefeng));
            } else if (msg.what == 4) {
                zhi.setVisibility(View.GONE);
                ll_cq.setVisibility(View.VISIBLE);
                ll_yc.setVisibility(View.VISIBLE);
                beginHqfhl();

            } else if (msg.what == 10) {
//                dg_shen.dismisssDialog();
                showToast(getString(R.string.cuowu));
            }
        }

    };

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.PULMONARY_CHECK_ZXT:
                Xlabel.clear();
                data.clear();
                data1.clear();
                PulmonaryZXT pulmonaryZXT = JSON.parseObject(result, PulmonaryZXT.class);
                for (int i = 0; i < pulmonaryZXT.getList().size(); i++) {
                    String strt = pulmonaryZXT.getList().get(i).getBYTIME();
                    int strsj = (int) pulmonaryZXT.getList().get(i).getNUM();
                    Xlabel.add(strt.substring(5, 7) + "."
                            + strt.substring(8, 10));
                    if (Double.valueOf(strsj) < 0) {
                        data.add("0");
                    } else {
                        data.add(strsj + "");
                    }
                    data1.add(strsj + "");
                }
                mv.drawZhexian(Xlabel, Ylabel, data, data1, 1);
//                doHttp(RetrofitUtils.createApi(DataUrl.class).checkPlumonaryZXT(
//                        userId, Content.DATA, phone, secret), 1);
                PulmonaryTrendAdapter pulmonaryTrendAdapter = new PulmonaryTrendAdapter(getActivity(), pulmonaryZXT.getList(), R.layout.item_xinlv);
                lv_pulmonary_trend.setAdapter(pulmonaryTrendAdapter);

                break;
            case 1:
                FindByPidEntity findByPidEntity1 = JSON.parseObject(result, FindByPidEntity.class);
                new CalenderDialogTest(getActivity(), findByPidEntity1.getList()) {
                    @Override
                    public void getZXTData() {
                        doHttp(RetrofitUtils.createApi(DataUrl.class).checkPlumonaryZXT(
                                userId, Content.DATA, phone, secret), HttpIdentifier.PULMONARY_CHECK_ZXT);
                    }
                };
                break;
            case HttpIdentifier.PULMONARY_HAVA_DATA:
                FindByPidEntity findByPidEntity = JSON.parseObject(result, FindByPidEntity.class);
                new CalenderDialogTest(getActivity(), findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
//                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).vitalCapacityFindBytime(userId, Content.DATA, phone, secret), HttpIdentifier.PULMONARY_CHECK_DATA);
                    }
                };
                break;
            case HttpIdentifier.PULMONARY_CHECK_DATA:
                tvFhl.setVisibility(View.GONE);
                llRili.setVisibility(View.VISIBLE);
                fhl_btn.setText(getResources().getString(R.string.tryAgain));
                pulmonaryDatas = JSON.parseObject(result, PulmonaryDatas.class);
                pulmonaryListAdapter = new PulmonaryListAdapter(getActivity(), pulmonaryDatas.getList(), R.layout.item_pulmonary);
                lvPulmonary.setAdapter(pulmonaryListAdapter);
                if (pulmonaryDatas.getList().size() == 0) {

                }
                break;

        }
    }

    private String getPhoneType() {
        //Redmi 3X
        String type = android.os.Build.MODEL;
        if (type.contains("mi")) {
            return "小米";
        }
        return type;
    }

    //获取声音
    public double getNoiseLevel() {
        double volume = 0;
        //录制的声音容量
        short[] buffer = new short[BUFFER_SIZE];
        // r是实际读取的数据长度，一般而言r会小于buffersize
        int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);

        Log.e("rrrrr", r + "*****");
        if (r <= 0) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(getString(R.string.ninyiguanbiluyinquanxian));
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
        handler.sendEmptyMessageDelayed(3, 5000);

        String phoneType = getPhoneType();
        if ("小米".equals(phoneType)) {
            mThreshold = 45;
        }
        //获取值
        timer_hqfhl = new Timer();
        tt_hqfhl = new TimerTask() {
            @Override
            public void run() {
                double mean = getNoiseLevel();

                Log.e("分贝值:", mean + "");
                if (mean < mThreshold) {
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

    /**
     * 牛移动的动画  结束
     */
    private void cattleMoveAnimationEnd() {
        if (ivCattle != null && mTranslation != null) {
            ivCattle.setVisibility(View.GONE);
            mTranslation.removeListener(this);
            mTranslation.end();
        }
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
            isStart = false;
        }
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
     * 检查权限（运行时权限）
     */
    private boolean toCheckPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
        if (PackageManager.PERMISSION_GRANTED != result) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RP_RADIO);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (RP_RADIO == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fhl_btn.setVisibility(View.GONE);
                zhi.setVisibility(View.GONE);
                tvFhl.setText("0000");
                handler.sendEmptyMessage(4);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.iv_rili3, R.id.iv_zhexiantu_close, R.id.iv_zhexiantu, R.id.iv_zhexiantu2, R.id.iv_rili2, R.id.iv_rili, R.id.fhl_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_zhexiantu_close://折线图关闭按钮
                svZhexiantu.setVisibility(View.GONE);
                break;
            case R.id.iv_zhexiantu://首页的折线图按钮
            case R.id.iv_zhexiantu2://日历页的折线图按钮
                svZhexiantu.setVisibility(View.VISIBLE);
                Ylabel.clear();
                for (int i = 0; i <= 10000; i += 1000) {
                    Ylabel.add(i + "");
                }
                doHttp(RetrofitUtils.createApi(DataUrl.class).checkPlumonaryZXT(
                        userId, time, phone, secret), HttpIdentifier.PULMONARY_CHECK_ZXT);
                break;
            case R.id.iv_rili3:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).vitalCapacityFindByPid(
                        userId, phone, secret), 1);
                break;
            case R.id.iv_rili2://日历页的日历按钮
            case R.id.iv_rili://首页的日历按钮
//                tvFhl.setVisibility(View.GONE);
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).vitalCapacityFindByPid(
                        userId, phone, secret), HttpIdentifier.PULMONARY_HAVA_DATA);
                break;
            case R.id.fhl_btn:
                if (llRili.getVisibility() == View.VISIBLE) {
                    llRili.setVisibility(View.GONE);
                }
                tvFhl.setVisibility(View.VISIBLE);
                int_volume = 0;
                isStart = false;

                if (toCheckPermission()){
                    fhl_btn.setVisibility(View.GONE);
                    zhi.setVisibility(View.GONE);
                    tvFhl.setText("0000");
                    handler.sendEmptyMessage(4);
                }
                break;
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

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        cattleMagnifyAni();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
