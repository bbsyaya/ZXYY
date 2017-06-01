package com.zhixinyisheng.user.view.calendar;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.util.Content;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 焕焕 on 2016/11/23.
 */
public abstract class CalenderDialogTest {
    private Dialog dialog;
    private MonthDateView monthDateView;
    private String time1="";
    public CalenderDialogTest(final Context context, List<FindByPidEntity.ListBean> list){
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.dialog_calendar, null);
        dialog = new Dialog(context, R.style.MyDialog);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        monthDateView = (MonthDateView) layout.findViewById(R.id.monthDateView);
        monthDateView.setListDate(list);
        // 不改变时候的时间
        String str_SelMonth = monthDateView.getmSelMonth() + 1 + "";
        if (str_SelMonth.length() == 1) {
            str_SelMonth = "0" + str_SelMonth;
        }
        String str_SelDay = monthDateView.getmSelDay() + 1 + "";
        if (str_SelDay.length() == 1) {
            str_SelDay = "0" + str_SelDay;
        }
        time1 = monthDateView.getmSelYear() + "-" + str_SelDay;
        TextView month = (TextView) layout.findViewById(R.id.date_month);
        monthDateView.setTextView(month, null);
        monthDateView.setDateClick(new MonthDateView.DateClick() {
            @Override
            public void onClickOnDate() {
                /**
                 * TODO 获取当前点击日期的值
                 */
                String str_SelMonth = monthDateView.getmSelMonth() + 1 + "";
                if (str_SelMonth.length() == 1) {
                    str_SelMonth = "0" + str_SelMonth;
                }
                String str_SelDay = monthDateView.getmSelDay() + "";
                if (str_SelDay.length() == 1) {
                    str_SelDay = "0" + str_SelDay;
                }
                time1 = monthDateView.getmSelYear() + "-" + str_SelMonth + "-" + str_SelDay;
                Content.mSelYear = monthDateView.getmSelYear();
                Content.mSelMonth = monthDateView.getmSelMonth();
                Content.mSelDay = monthDateView.getmSelDay();
//                Log.e("mSelDay 61",PublicData.mSelDay+"");
                Content.DATA = time1;

                String timeClick = monthDateView.getmSelYear() + str_SelMonth + str_SelDay;
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String timeNow = sdf.format(d);
                if (Long.parseLong(timeNow)<Long.parseLong(timeClick)){
                    Toast.makeText(context, R.string.qinzheyitianhaimeidaolai, Toast.LENGTH_SHORT).show();
                }else{
//                    title_name.setText(time1+titleName);
//                    Intent intent = new Intent(sendMessage);//发送消息，刷新页面的广播
//                    context.sendBroadcast(intent);
//                    Intent intent1 = new Intent("photo.broadcast.action");
//                    context.sendBroadcast(intent1);
                    getZXTData();
                    dialog.dismiss();
                }

            }
        });
        layout.findViewById(R.id.imgbtn_lift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        /* 左三角 */
                monthDateView.onLeftClick();
                        /* 选择月份继续更新日期值 */
                Content.DATA = monthDateView.getmSelYear() + "-"
                        + (monthDateView.getmSelMonth() + 1) + "-"
                        + monthDateView.getmSelDay();
            }
        });
        layout.findViewById(R.id.imgbtn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        /* 右三角 */
                monthDateView.onRightClick();
                Content.DATA = monthDateView.getmSelYear() + "-"
                        + (monthDateView.getmSelMonth() + 1) + "-"
                        + monthDateView.getmSelDay();
            }
        });
    }



    public abstract void getZXTData();

}
