package com.zhixinyisheng.user.ui.remind;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.remind.RemindTimeAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.domain.remind.RemindTime;
import com.zhixinyisheng.user.domain.remind.UseMedicine;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.view.MaxListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 设置用药
 */
public class MedicineDetailActivity extends BaseAty implements OnSubitemClickListener {
    public static final String EXTRA_TO_USEER_ID = "toUserId";
    public static final String EXTRA_MODEL = "extra_model";
    private static final int REQUEST_CODE = 1;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_measure)
    EditText etMeasure;
    @Bind(R.id.et_start_date)
    EditText etStartDate;
    @Bind(R.id.et_end_date)
    EditText etEndDate;
    @Bind(R.id.et_note)
    EditText etNote;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.switch_btn)
    SwitchCompat switchCompat;
    @Bind(R.id.lv_remind_time)
    MaxListView lvRemindTime;
    private RemindTimeAdapter remindTimeAdapter;
    private String toUserId;
    private UseMedicine.ListBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_medicine_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText(R.string.shezhiyongyao);
        userId = UserManager.getUserInfo().getUserId();
        toUserId = getIntent().getStringExtra(EXTRA_TO_USEER_ID);
        bean = getIntent().getParcelableExtra(EXTRA_MODEL);
        int isDocotr = UserManager.getUserInfo().getIsDoctor();
        if (isDocotr == 1) {
            //医生
            initViewState(true);
            if (bean == null) {
                btnSave.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);
            } else {
                btnSave.setVisibility(View.GONE);
                btnDelete.setVisibility(View.VISIBLE);
                initViewState(false);
            }
        } else {
            //患者
            initViewState(false);
            btnSave.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
        initAdapter();
        displayData(bean);
        loadInputMedicineName();
    }

    private void loadInputMedicineName() {
        doHttp(RetrofitUtils.createApi(DataUrl.class).selectMedcineName(phone, secret, userId), HttpIdentifier.SELECT_EDIT_MEDICINE_NAME);
    }

    private void displayData(UseMedicine.ListBean bean) {
        if (bean != null) {
            etName.setText(bean.getMedicienName());
            etEndDate.setText(bean.getEndTime());
            etStartDate.setText(bean.getBeginTime());
            etNote.setText(bean.getRemark());
            etMeasure.setText(bean.getCount());
        }
    }



    private void initViewState(boolean state) {
        etName.setEnabled(state);
        etNote.setEnabled(state);
        etMeasure.setEnabled(state);
        etStartDate.setEnabled(state);
        etEndDate.setEnabled(state);
        etStartDate.setClickable(state);
        etEndDate.setClickable(state);
    }

    private void initAdapter() {
        remindTimeAdapter = new RemindTimeAdapter(this);
        remindTimeAdapter.initData(bean);
        lvRemindTime.setAdapter(remindTimeAdapter);
        remindTimeAdapter.setOnSubitemClickListener(this);
    }

    @OnClick({R.id.iv_title_left, R.id.et_start_date, R.id.et_end_date, R.id.btn_delete, R.id.btn_save, R.id.et_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.et_start_date:
                showDateDialog(etStartDate);
                break;
            case R.id.et_end_date:
                showDateDialog(etEndDate);
                break;
            case R.id.btn_delete:
                deeteUseMedicineInfo();
                break;
            case R.id.btn_save:
                saveUseMedicineInfo();
                break;
            case R.id.et_name:
                Intent intent = new Intent(this, MedicineNameActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && RESULT_OK == resultCode) {
            String medicineName = data.getStringExtra(MedicineNameActivity.EXTRA_NAME);
            etName.setText(medicineName);
        }
    }

    /**
     * 删除
     */
    private void deeteUseMedicineInfo() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataUrl.class).deleteUseMedicineInfo(phone, secret, bean.getMedicineId()), HttpIdentifier.DELETE_USE_MEDICINE_INFO);
    }


    /**
     * 保存
     */
    private void saveUseMedicineInfo() {
        String medicienName = etName.getText().toString().trim();
        String measure = etMeasure.getText().toString().trim();
        String alertTime = getAllRemindTime();
        String beginTime = etStartDate.getText().toString().trim();
        String endTime = etEndDate.getText().toString().trim();
        String remark = etNote.getText().toString().trim();
        if ("".equals(medicienName) || "".equals(measure) || "".equals(alertTime) || "".equals(beginTime)
                || "".equals(endTime)) {
            showToast(getString(R.string.qingshuruneirong));
            return;
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataUrl.class).saveUseMedicineInfo(phone, secret, userId, toUserId, medicienName, measure
                , alertTime, beginTime, endTime, remark), HttpIdentifier.SAVE_USE_MEDICINE_INFO);
    }

    private String getAllRemindTime() {
        StringBuffer buf = new StringBuffer();
        List<RemindTime> remindTimeList = remindTimeAdapter.getData();
        int count = remindTimeList.size();
        for (int i = 0; i < count; i++) {
            RemindTime bean = remindTimeList.get(i);
            if (i == count - 1) {
                buf.append(bean.getTime());
            } else {
                buf.append(bean.getTime() + ",");
            }
        }
        return buf.toString();
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
//        switch (what) {
//            case HttpIdentifier.SAVE_USE_MEDICINE_INFO:
        if (what == HttpIdentifier.SELECT_EDIT_MEDICINE_NAME) {
            return;
        } else {
            finish();
            JsonResponse jsonResponse = JSON.parseObject(result, JsonResponse.class);
        }

//                break;
//        }
    }

    private Calendar showDate = Calendar.getInstance();

    /**
     * 调用该方法弹出时间选择器dialog 时间间隔为15
     */
    private void showTimePicker(final EditText etTime, final RemindTime remindTime) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_timepiker, null);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(Calendar.MINUTE);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0); // 设为 0
                calendar.set(Calendar.MILLISECOND, 0); // 设为 0
            }
        });
//        //如果需要设置分钟的时间间隔则调用该方法
//        String[] minutes = new String[]{"00", "15", "30", "45"};
//        setNumberPickerTextSize(timePicker, minutes);
        builder.setView(view);
        builder.setTitle(R.string.shezhishijianxinxi);
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int mHour = timePicker.getCurrentHour();
                //如果之前调用了setNumberPickerTextSize方法，则mMinute不是你选择的实际的分钟数，
                //而是实际的分钟数所在数组的索引，mMinute*时间间隔才是实际的分钟数,
                // 例如你选了30,30在分钟数组的索引是2(即mMinute)，而分钟的间隔是15,则2*15=30
                int mMinute = timePicker.getCurrentMinute();
//                showToast( mHour + "hour: " + mMinute*15 + "minute");
                String showTime = etTime.getText().toString();
                if ("".equals(showTime) || getString(R.string.qingxuanze).equals(showTime)) {
//                    remindTimeAdapter.addItem();
                    remindTime.setDiplayCheck(true);
                    remindTime.setAdd(true);
                    remindTimeAdapter.notifyDataSetChanged();
                }
                String chooseTime = DateFormat.format("HH:mm", calendar).toString();
                etTime.setText(chooseTime);
                remindTime.setTime(chooseTime);
                dialog.cancel();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    /**
     * @param viewGroup 传入TimePicker
     * @param minutes   待显示的分钟间隔数组，例如：String[] minutes = new String[]{"00","15","30","45"};
     */
    private void setNumberPickerTextSize(ViewGroup viewGroup, String[] minutes) {
        List<NumberPicker> npList = findNumberPicker(viewGroup);
        if (null != npList) {
            for (NumberPicker mMinuteSpinner : npList) {
                if (mMinuteSpinner.toString().contains("id/minute")) {//对分钟进行间隔设置
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        mMinuteSpinner.setMinValue(0);
                        mMinuteSpinner.setMaxValue(minutes.length - 1);
                        mMinuteSpinner.setDisplayedValues(minutes);  //分钟显示数组
                    }
                }
            }
        }
    }

    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;

        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }

        return npList;
    }

    /**
     * 没有时间间隔的
     *
     * @param etTime
     * @param remindTime
     */
    private void showTimeDialog(final EditText etTime, final RemindTime remindTime) {
        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                showDate.setTimeInMillis(System.currentTimeMillis());
                showDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                showDate.set(Calendar.MINUTE, minute);
                showDate.set(Calendar.SECOND, 0); // 设为 0
                showDate.set(Calendar.MILLISECOND, 0); // 设为 0
                String showTime = etTime.getText().toString().trim();
                if ("".equals(showTime) || "请选择".equals(showTime)) {
//                    remindTimeAdapter.addItem();

                    remindTime.setDiplayCheck(true);
                    remindTime.setAdd(true);
                    remindTimeAdapter.notifyDataSetChanged();
                }
                String chooseTime = DateFormat.format("HH:mm", showDate).toString();
                etTime.setText(chooseTime);
                remindTime.setTime(chooseTime);
            }
        }, showDate.get(Calendar.HOUR_OF_DAY),
                showDate.get(Calendar.MINUTE), true);

        timeDialog.setButton(TimePickerDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        timeDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remindTime.setTime("请选择");
//                isConfirm = false;
                Log.e("onDateSet", "取消-->" + DateFormat.format("yyyy-MM-dd", showDate));
            }
        });
        timeDialog.show();
    }

    private void showDateDialog(final EditText etDate) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        }, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        Log.e("tag", "tag--year---" + showDate.get(showDate.YEAR) + "---MONTH---" + showDate.get(Calendar.MONTH));
        DatePicker picker = dialog.getDatePicker();
        picker.init(showDate.get(Calendar.YEAR), showDate.get(Calendar.MONTH), showDate.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                showDate.set(showDate.YEAR, datePicker.getYear());
                showDate.set(showDate.MONTH, datePicker.getMonth());
                showDate.set(showDate.DAY_OF_MONTH, datePicker.getDayOfMonth());
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.confirm), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String chooseDate = DateFormat.format("yyyy-MM-dd", showDate).toString();
                if (etDate == etEndDate) {
                    String startTime = etStartDate.getText().toString().trim();
                    int flag = disposeDate(startTime, chooseDate);
                    if (flag == 1) {
                        showToast(getString(R.string.jieshushijianbunengzaikaishishijianqian));
                        return;
                    }
                }
                etDate.setText(chooseDate);
                Log.e("onDateSet", "确定-->" + DateFormat.format("yyyy-MM-dd", showDate));
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etDate.setText("");
//                isConfirm = false;
                Log.e("onDateSet", "取消-->" + DateFormat.format("yyyy-MM-dd", showDate));
            }
        });
        dialog.show();
    }

    /**
     * 判断比较两个日期
     */
    private int disposeDate(String date1, String date2) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onSubitemClick(int position, View v) {
        RemindTime remindTime = remindTimeAdapter.getItem(position);
        switch (v.getId()) {
            case R.id.et_remind_date:
                EditText editText = (EditText) v;
//                showTimeDialog(editText, remindTime);
                showTimePicker(editText, remindTime);
                break;
            case R.id.cb_check:
                if (remindTime.isAdd()) {
                    remindTime.setAdd(false);
                    String showTime = remindTime.getTime();
                    if (!getString(R.string.qingxuanze).equals(showTime) && !"".equals(showTime)) {
                        RemindTime bean = new RemindTime();
                        bean.setTime(getString(R.string.qingxuanze));
                        bean.setDiplayCheck(true);
                        bean.setAdd(true);
                        remindTimeAdapter.addItem(bean);
                    } else {
                        showToast(getString(R.string.qingxuanze));
                    }
                } else {
                    //删除
                    remindTimeAdapter.removeData(position);
                }
                break;
        }
    }
}
