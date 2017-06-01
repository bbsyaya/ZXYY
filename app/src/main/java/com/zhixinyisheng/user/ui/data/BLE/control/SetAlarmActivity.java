package com.zhixinyisheng.user.ui.data.BLE.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置闹钟
 * Created by 焕焕 on 2016/10/27.
 */
public class SetAlarmActivity extends BaseAty implements CompoundButton.OnCheckedChangeListener {
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
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.sala_tvzxyc)
    TextView salaTvzxyc;
    @Bind(R.id.sala_vizxyc)
    View salaVizxyc;
    @Bind(R.id.sala_ivxx)
    ImageView ivxx;
    @Bind(R.id.sala_tvcf)
    TextView tvcf;
    @Bind(R.id.sala_llcf)
    LinearLayout salaLlcf;
    @Bind(R.id.sala_rlcf)
    RelativeLayout salaRlcf;
    @Bind(R.id.sala_vi)
    View salaVi;
    @Bind(R.id.sala_cb1)
    CheckBox cb1;
    @Bind(R.id.sala_cb2)
    CheckBox cb2;
    @Bind(R.id.sala_cb3)
    CheckBox cb3;
    @Bind(R.id.sala_cb4)
    CheckBox cb4;
    @Bind(R.id.sala_cb5)
    CheckBox cb5;
    @Bind(R.id.sala_cb6)
    CheckBox cb6;
    @Bind(R.id.sala_cb7)
    CheckBox cb7;
    @Bind(R.id.sala_rlxz)
    LinearLayout salaRlxz;
    @Bind(R.id.sala_scll)
    ScrollView scll;
    @Bind(R.id.sala_tvt)
    TextView tvjlxz;
    @Bind(R.id.sala_rlt)
    RelativeLayout salaRlt;
    @Bind(R.id.sala_tp)
    TimePicker tp;

    int hour, minute1, hourCurrent, minuteCurrent, secondCurrent;
    int no = 0,n1 = 1;
    String str1="0",str2="0",str3="0",str4="0",str5="0",str6="0",str7="0";

    @Override
    public int getLayoutId() {
        return R.layout.activity_setalar;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText("设置闹钟");
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText("完成");
        ivBack.setVisibility(View.VISIBLE);
        hour = tp.getCurrentHour();
        minute1 = tp.getCurrentMinute();
        hourCurrent = tp.getCurrentHour();
        minuteCurrent = tp.getCurrentMinute();
        setJLXZ();
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        cb4.setOnCheckedChangeListener(this);
        cb5.setOnCheckedChangeListener(this);
        cb6.setOnCheckedChangeListener(this);
        cb7.setOnCheckedChangeListener(this);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minute1 = minute;
                setJLXZ();
            }
        });
    }

    //算时间
    private void setJLXZ() {
        if ((hourCurrent > hour) || (hourCurrent == hour && minuteCurrent > minute1)) {
            int jlxz = (24 - hourCurrent - 1) * 60 + (60 - minuteCurrent) + hour * 60 + minute1;
            int jlxz1 = jlxz / 60;
            tvjlxz.setText(jlxz1 + "小时" + (jlxz - jlxz1 * 60) + "分钟后开始提醒");
        } else if (hourCurrent == hour && minuteCurrent <= minute1) {
            int jlxz = minute1 - minuteCurrent;
            int jlxz1 = jlxz / 60;
            tvjlxz.setText(jlxz1 + "小时" + (jlxz - jlxz1 * 60) + "分钟后开始提醒");
        } else if (hourCurrent < hour) {
            int jlxz = (hour - hourCurrent - 1) * 60 + (60 - minuteCurrent) + minute1;
            int jlxz1 = jlxz / 60;
            tvjlxz.setText(jlxz1 + "小时" + (jlxz - jlxz1 * 60) + "分钟后开始提醒");
        }
    }

    @Override
    public void requestData() {

    }


    @OnClick({R.id.cjs_view, R.id.cjs_tvt, R.id.iv_sliding, R.id.cjs_rlb, R.id.title_xinxi, R.id.title_close, R.id.title_btn, R.id.cjs_rlr, R.id.cjs_rl_title, R.id.title, R.id.sala_tvzxyc, R.id.sala_vizxyc, R.id.sala_ivxx, R.id.sala_tvcf, R.id.sala_llcf, R.id.sala_rlcf, R.id.sala_vi, R.id.sala_cb1, R.id.sala_cb2, R.id.sala_cb3, R.id.sala_cb4, R.id.sala_cb5, R.id.sala_cb6, R.id.sala_cb7, R.id.sala_rlxz, R.id.sala_scll, R.id.sala_tvt, R.id.sala_rlt, R.id.sala_tp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_view:
                break;
            case R.id.cjs_tvt:
                break;
            case R.id.iv_sliding:
                break;

            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_xinxi:
                break;
            case R.id.title_close:
                break;
            case R.id.title_btn://完成按钮
                Intent intent = new Intent();
                intent.putExtra("alarm_hour", hour);
                intent.putExtra("alarm_minute", minute1);
                intent.putExtra("alarm_str1", str1);
                intent.putExtra("alarm_str2", str2);
                intent.putExtra("alarm_str3", str3);
                intent.putExtra("alarm_str4", str4);
                intent.putExtra("alarm_str5", str5);
                intent.putExtra("alarm_str6", str6);
                intent.putExtra("alarm_str7", str7);
                intent.putExtra("alarm_stron", "1");
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case R.id.cjs_rlr:
                break;
            case R.id.cjs_rl_title:
                break;
            case R.id.title:
                break;
            case R.id.sala_tvzxyc:
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);
                cb5.setChecked(false);
                cb6.setChecked(false);
                cb7.setChecked(false);
                if (n1==0) {
                    salaTvzxyc.setTextColor(0xff00B9BB);
                    n1 = 1;
                    str1="0";str2="0";str3="0";str4="0";str5="0";str6="0";str7="0";
                }else if (n1==1) {
                    salaTvzxyc.setTextColor(0xff000000);
                    n1 = 0;
                }
                break;
            case R.id.sala_vizxyc:
                break;
            case R.id.sala_ivxx:
                break;
            case R.id.sala_tvcf:
                break;
            case R.id.sala_llcf:
                break;
            case R.id.sala_rlcf:
                if (no==0) {
                    ivxx.setImageResource(R.drawable.xiangyou);
                    scll.setVisibility(View.VISIBLE);
                    tvcf.setTextColor(0xff00B9BB);
                    no = 1;
                }else if (no==1) {
                    ivxx.setImageResource(R.drawable.xiangxia);
                    scll.setVisibility(View.GONE);
                    no = 0;
                }
                break;
            case R.id.sala_vi:
                break;
            case R.id.sala_cb1:
                break;
            case R.id.sala_cb2:
                break;
            case R.id.sala_cb3:
                break;
            case R.id.sala_cb4:
                break;
            case R.id.sala_cb5:
                break;
            case R.id.sala_cb6:
                break;
            case R.id.sala_cb7:
                break;
            case R.id.sala_rlxz:
                break;
            case R.id.sala_scll:
                break;
            case R.id.sala_tvt:
                break;
            case R.id.sala_rlt:
                break;
            case R.id.sala_tp:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onCheckedChanged(CompoundButton cb, boolean isCheck) {
        if (cb==cb1) {
            salaTvzxyc.setTextColor(0xff000000);
            n1 = 0;
            if (isCheck) {
                Log.e("rrr", "nnnn");
                str1="1";
            }else {
                Log.e("rrr", "dddd");
                str1="0";
            }
        }else if (cb==cb2) {
            salaTvzxyc.setTextColor(0xff000000);
            n1 = 0;
            if (isCheck) {
                str2="1";
            }else {
                str2="0";
            }
        }else if (cb==cb3) {
            salaTvzxyc.setTextColor(0xff000000);
            n1 = 0;
            if (isCheck) {
                str3="1";
            }else {
                str3="0";
            }
        }else if (cb==cb4) {
            salaTvzxyc.setTextColor(0xff000000);
            n1 = 0;
            if (isCheck) {
                str4="1";
            }else {
                str4="0";
            }
        }else if (cb==cb5) {
            salaTvzxyc.setTextColor(0xff000000);
            n1 = 0;
            if (isCheck) {
                str5="1";
            }else {
                str5="0";
            }
        }else if (cb==cb6) {
            salaTvzxyc.setTextColor(0xff000000);
            n1 = 0;
            if (isCheck) {
                str6="1";
            }else {
                str6="0";
            }
        }else if (cb==cb7) {
            salaTvzxyc.setTextColor(0xff000000);
            n1 = 0;
            if (isCheck) {
                str7="1";
            }else {
                str7="0";
            }
        }

    }
}
