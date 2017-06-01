package com.zhixinyisheng.user.ui.remind;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.remind.UseMedicineAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.remind.UseMedicine;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.DateUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 服药
 */
public class UseMedicineActivity extends BaseAty implements OnSubitemClickListener {
    public static final String EXTRA_TO_USEER_ID = "EXTRA_TO_USEER_ID";

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.lv_use_medicine)
    ListView lvUseMedicine;

    private UseMedicineAdapter useMedicineAdapter;
    private int isDocotr;
    private String toUserId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_use_medicine;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText(R.string.fuyao);
        tvTitleRight.setText(R.string.tianjia);
        initAdapter();
        isDocotr = UserManager.getUserInfo().getIsDoctor();
        toUserId = getIntent().getStringExtra(EXTRA_TO_USEER_ID);
        if ("".equals(toUserId) || toUserId == null) {
            toUserId = userId;
        }
        if (isDocotr == 1) {
            //医生
            tvTitleRight.setVisibility(View.VISIBLE);
        } else {
            //患者
            tvTitleRight.setVisibility(View.GONE);
        }
    }


    private void initAdapter() {
        useMedicineAdapter = new UseMedicineAdapter(this);
        lvUseMedicine.setAdapter(useMedicineAdapter);
        useMedicineAdapter.setOnSubitemClickListener(this);
    }


    String currentTime;

    @Override
    protected void onResume() {
        super.onResume();
        currentTime = DateUtils.getTodayDate();
        tvDate.setText(currentTime);
        loadData();
    }

    private void loadData() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataUrl.class).findByTimeToData(phone, secret, toUserId, currentTime), HttpIdentifier.FIND_BY_TIMETO_DATA);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case HttpIdentifier.FIND_BY_TIMETO_DATA:
                UseMedicine useMedicine = JSON.parseObject(result, UseMedicine.class);
                List<UseMedicine.ListBean> beanList = useMedicine.getList();
                useMedicineAdapter.setData(beanList);
                break;
            case HttpIdentifier.EDIT_USE_MEDICINE_STATE:
                loadData();
                break;
        }
    }

    @OnClick({R.id.iv_title_left, R.id.tv_title_right, R.id.iv_rili})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.tv_title_right:
                Intent intent = new Intent(this, MedicineDetailActivity.class);
                intent.putExtra(MedicineDetailActivity.EXTRA_TO_USEER_ID, toUserId);
                startActivity(intent);
                break;
            case R.id.iv_rili:
                showDateDialog(tvDate);
                break;
        }
    }

    private Calendar showDate = Calendar.getInstance();

    private void showDateDialog(final TextView tvDate) {
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
//                isConfirm = true;
                String chooseTime = DateFormat.format("yyyy-MM-dd", showDate).toString();
                tvDate.setText(chooseTime);
                currentTime = chooseTime;
                loadData();
                Log.e("onDateSet", "确定-->" + DateFormat.format("yyyy-MM-dd", showDate));
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                isConfirm = false;
                Log.e("onDateSet", "取消-->" + DateFormat.format("yyyy-MM-dd", showDate));
            }
        });
        dialog.show();
    }

    @Override
    public void onSubitemClick(int position, View v) {
        UseMedicine.ListBean bean = useMedicineAdapter.getItem(position);
        switch (v.getId()) {
            case R.id.ll_ctrl:
                Intent intent = new Intent(this, MedicineDetailActivity.class);
                intent.putExtra(MedicineDetailActivity.EXTRA_MODEL, bean);
                startActivity(intent);
                break;
            case R.id.iv_icon:

                if (isDocotr==1){
                    return;
                }
                int isEat = bean.getIsEat();
                if (isEat == 0) {
                    showHintDialog(bean.getMedenimeAndPushId());
                }
                break;
        }
    }

    private void showHintDialog(final int medenimeAndPushId) {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(getString(R.string.ninshifouyongyao))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        doHttp(RetrofitUtils.createApi(DataUrl.class).amindEatState(phone, secret, String.valueOf(medenimeAndPushId), String.valueOf(1)),
                                HttpIdentifier.EDIT_USE_MEDICINE_STATE);
                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {

                    }
                })
                .show();

    }

}
