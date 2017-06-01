package com.zhixinyisheng.user.ui.data.BLE.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.util.Colors;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置闹钟（新）
 * Created by 焕焕 on 2017/2/1.
 */
public class SetAlarmAty extends Activity {
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
    @Bind(R.id.main_unread_msg_number)
    TextView mainUnreadMsgNumber;
    @Bind(R.id.cjs_rl_xhd)
    RelativeLayout cjsRlXhd;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.title_btn_hosptal)
    Button titleBtnHosptal;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title_line)
    View titleLine;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.tv_once)
    TextView tvOnce;
    @Bind(R.id.rl_once)
    RelativeLayout rlOnce;
    @Bind(R.id.iv_isrepeat)
    ImageView ivIsrepeat;
    @Bind(R.id.tv_isrepeat)
    TextView tvIsrepeat;
    @Bind(R.id.rl_isrepeat)
    LinearLayout rlIsrepeat;
    @Bind(R.id.tv_mon)
    TextView tvMon;
    @Bind(R.id.rl_mon)
    RelativeLayout rlMon;
    @Bind(R.id.tv_tue)
    TextView tvTue;
    @Bind(R.id.rl_tue)
    RelativeLayout rlTue;
    @Bind(R.id.tv_wed)
    TextView tvWed;
    @Bind(R.id.rl_wed)
    RelativeLayout rlWed;
    @Bind(R.id.tv_thu)
    TextView tvThu;
    @Bind(R.id.rl_thu)
    RelativeLayout rlThu;
    @Bind(R.id.tv_fri)
    TextView tvFri;
    @Bind(R.id.rl_fri)
    RelativeLayout rlFri;
    @Bind(R.id.tv_sat)
    TextView tvSat;
    @Bind(R.id.rl_sat)
    RelativeLayout rlSat;
    @Bind(R.id.tv_sun)
    TextView tvSun;
    @Bind(R.id.rl_sun)
    RelativeLayout rlSun;
    @Bind(R.id.sala_tvt)
    TextView tvjlxz;
    @Bind(R.id.ll_week)
    LinearLayout llWeek;
    @Bind(R.id.tp_alarm)
    TimePicker tp;
    boolean flagOnce, flagRepeat, flagMon, flagTue, flagWed, flagThu, flagFri, flagSat, flagSun;
    int hour, minute1, hourCurrent, minuteCurrent;
    String str1="0",str2="0",str3="0",str4="0",str5="0",str6="0",str7="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_setalarm);
        ButterKnife.bind(this);
        cjsTvt.setText(R.string.shezhinaozhong);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.finish);
        ivBack.setVisibility(View.VISIBLE);
        hour = tp.getCurrentHour();
        minute1 = tp.getCurrentMinute();
        hourCurrent = tp.getCurrentHour();
        minuteCurrent = tp.getCurrentMinute();
        setJLXZ();
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
            tvjlxz.setText("\u0020"+jlxz1 + getString(R.string.xiaoshi) +"\u0020"+ (jlxz - jlxz1 * 60) + getString(R.string.fenzhonghoutaishitixing));
        } else if (hourCurrent == hour && minuteCurrent <= minute1) {
            int jlxz = minute1 - minuteCurrent;
            int jlxz1 = jlxz / 60;
            tvjlxz.setText("\u0020"+jlxz1 + getString(R.string.xiaoshi) + "\u0020"+(jlxz - jlxz1 * 60) + getString(R.string.fenzhonghoutaishitixing));
        } else if (hourCurrent < hour) {
            int jlxz = (hour - hourCurrent - 1) * 60 + (60 - minuteCurrent) + minute1;
            int jlxz1 = jlxz / 60;
            tvjlxz.setText("\u0020"+jlxz1 + getString(R.string.xiaoshi) + "\u0020"+(jlxz - jlxz1 * 60) + getString(R.string.fenzhonghoutaishitixing));
        }
    }
    @OnClick({R.id.title_btn,R.id.cjs_rlb, R.id.rl_once, R.id.rl_isrepeat, R.id.rl_mon, R.id.rl_tue, R.id.rl_wed, R.id.rl_thu, R.id.rl_fri, R.id.rl_sat, R.id.rl_sun})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.rl_once:
                flagMon = false;
                rlMon.setBackgroundResource(R.drawable.btn_white_shape);
                tvMon.setTextColor(Colors.textColor);
                flagTue = false;
                rlTue.setBackgroundResource(R.drawable.btn_white_shape);
                tvTue.setTextColor(Colors.textColor);
                flagWed = false;
                rlWed.setBackgroundResource(R.drawable.btn_white_shape);
                tvWed.setTextColor(Colors.textColor);
                flagThu = false;
                rlThu.setBackgroundResource(R.drawable.btn_white_shape);
                tvThu.setTextColor(Colors.textColor);
                flagFri = false;
                rlFri.setBackgroundResource(R.drawable.btn_white_shape);
                tvFri.setTextColor(Colors.textColor);
                flagSat = false;
                rlSat.setBackgroundResource(R.drawable.btn_white_shape);
                tvSat.setTextColor(Colors.textColor);
                flagSun = false;
                rlSun.setBackgroundResource(R.drawable.btn_white_shape);
                tvSun.setTextColor(Colors.textColor);
                if (!flagOnce) {
                    flagOnce = true;
                    rlOnce.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvOnce.setTextColor(Colors.mainColor);
                    str1="0";str2="0";str3="0";str4="0";str5="0";str6="0";str7="0";
                } else {
                    flagOnce = false;
                    rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                    tvOnce.setTextColor(Colors.textColor);
                }
                break;
            case R.id.rl_isrepeat:
                if (!flagRepeat) {
                    flagRepeat = true;
                    ivIsrepeat.setImageResource(R.drawable.xiangyou);
                    llWeek.setVisibility(View.VISIBLE);
                } else {
                    flagRepeat = false;
                    ivIsrepeat.setImageResource(R.drawable.xiangxia);
                    llWeek.setVisibility(View.GONE);
                }
                break;
            case R.id.rl_mon:
                flagOnce = false;
                rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                tvOnce.setTextColor(Colors.textColor);

                if (!flagMon) {
                    flagMon = true;
                    rlMon.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvMon.setTextColor(Colors.mainColor);
                    str1="1";
                } else {
                    flagMon = false;
                    rlMon.setBackgroundResource(R.drawable.btn_white_shape);
                    tvMon.setTextColor(Colors.textColor);
                    str1="0";
                }
                break;
            case R.id.rl_tue:
                flagOnce = false;
                rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                tvOnce.setTextColor(Colors.textColor);

                if (!flagTue) {
                    flagTue = true;
                    rlTue.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvTue.setTextColor(Colors.mainColor);
                    str2="1";
                } else {
                    flagTue = false;
                    rlTue.setBackgroundResource(R.drawable.btn_white_shape);
                    tvTue.setTextColor(Colors.textColor);
                    str2="0";
                }
                break;
            case R.id.rl_wed:
                flagOnce = false;
                rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                tvOnce.setTextColor(Colors.textColor);

                if (!flagWed) {
                    flagWed = true;
                    rlWed.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvWed.setTextColor(Colors.mainColor);
                    str3="1";
                } else {
                    flagWed = false;
                    rlWed.setBackgroundResource(R.drawable.btn_white_shape);
                    tvWed.setTextColor(Colors.textColor);
                    str3="0";
                }
                break;
            case R.id.rl_thu:
                flagOnce = false;
                rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                tvOnce.setTextColor(Colors.textColor);

                if (!flagThu) {
                    flagThu = true;
                    rlThu.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvThu.setTextColor(Colors.mainColor);
                    str4="1";
                } else {
                    flagThu = false;
                    rlThu.setBackgroundResource(R.drawable.btn_white_shape);
                    tvThu.setTextColor(Colors.textColor);
                    str4="0";
                }
                break;
            case R.id.rl_fri:
                flagOnce = false;
                rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                tvOnce.setTextColor(Colors.textColor);

                if (!flagFri) {
                    flagFri = true;
                    rlFri.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvFri.setTextColor(Colors.mainColor);
                    str5="1";
                } else {
                    flagFri = false;
                    rlFri.setBackgroundResource(R.drawable.btn_white_shape);
                    tvFri.setTextColor(Colors.textColor);
                    str5="0";
                }
                break;
            case R.id.rl_sat:
                flagOnce = false;
                rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                tvOnce.setTextColor(Colors.textColor);

                if (!flagSat) {
                    flagSat = true;
                    rlSat.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvSat.setTextColor(Colors.mainColor);
                    str6="1";
                } else {
                    flagSat = false;
                    rlSat.setBackgroundResource(R.drawable.btn_white_shape);
                    tvSat.setTextColor(Colors.textColor);
                    str6="0";
                }
                break;
            case R.id.rl_sun:
                flagOnce = false;
                rlOnce.setBackgroundResource(R.drawable.btn_white_shape);
                tvOnce.setTextColor(Colors.textColor);

                if (!flagSun) {
                    flagSun = true;
                    rlSun.setBackgroundResource(R.drawable.btn_maincolor_shape);
                    tvSun.setTextColor(Colors.mainColor);
                    str7="1";
                } else {
                    flagSun = false;
                    rlSun.setBackgroundResource(R.drawable.btn_white_shape);
                    tvSun.setTextColor(Colors.textColor);
                    str7="0";
                }
                break;
        }
    }

}
