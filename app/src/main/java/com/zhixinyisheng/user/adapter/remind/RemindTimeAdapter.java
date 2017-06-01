package com.zhixinyisheng.user.adapter.remind;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.ArrayListAdapter;
import com.zhixinyisheng.user.domain.remind.RemindTime;
import com.zhixinyisheng.user.domain.remind.UseMedicine;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.ui.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/20.
 */

public class RemindTimeAdapter extends ArrayListAdapter<RemindTime> {
    private OnSubitemClickListener onSubitemClickListener;
    private List<RemindTime> remindTimes = new ArrayList<>();
    private boolean isEdit=true;
    public void setOnSubitemClickListener(OnSubitemClickListener onSubitemClickListener) {
        this.onSubitemClickListener = onSubitemClickListener;
    }

    @Override
    public List<RemindTime> getData() {
        return remindTimes;
    }

    public void removeData(int pos) {
        remindTimes.remove(pos);
        handleData();
        notifyDataSetChanged();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public RemindTimeAdapter(Context context) {
        super(context, R.layout.item_remind_time);
    }

    public void initData(UseMedicine.ListBean bean) {
        if (bean == null) {
            remindTimes.add(new RemindTime());
        } else {
            isEdit=false;
            String alertTime = bean.getAlertTime();
            String[] arrayList = alertTime.split(",");
            int count = arrayList.length;
            for (int i = 0; i < count; i++) {
                RemindTime remindTime = new RemindTime();
                remindTime.setTime(arrayList[i]);
                if (count == 1) {
                    remindTime.setDiplayCheck(false);
                } else {
                    remindTime.setTitle(MyApplication.getApp().getString(R.string.remind_time)+(i+1));
                    remindTime.setDiplayCheck(true);
                    if (i == count - 1) {
                        remindTime.setAdd(true);
                    }else{
                        remindTime.setAdd(false);
                    }
                }
                remindTimes.add(remindTime);
            }
        }

    }

    public void addItem(RemindTime remindTime) {
        remindTimes.add(remindTime);
        handleData();
        notifyDataSetChanged();
    }

    public void handleData() {
        int count = remindTimes.size();
        for (int i = 0; i < count; i++) {
            RemindTime remindTime = remindTimes.get(i);
            remindTime.setTitle(MyApplication.getApp().getString(R.string.remind_time) + (i + 1));
            if (count == 1) {
                remindTime.setDiplayCheck(false);
                remindTime.setAdd(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RemindTime getItem(int pos) {
        return remindTimes.get(pos);
    }

    @Override
    public void bindViewHolder(int position, Object viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.pos = position;
        holder.onSubitemClickListener = onSubitemClickListener;
        RemindTime remindTime = remindTimes.get(position);
        String time = remindTime.getTime();
        if (!"".equals(time)) {
            holder.etRemindDate.setText(time);
        }
        holder.tv3.setText(remindTime.getTitle());
        boolean isDiplayCheck = remindTime.isDiplayCheck();
        if (!isDiplayCheck) {
            holder.cbCheck.setVisibility(View.GONE);
        } else {
            holder.cbCheck.setVisibility(View.VISIBLE);
        }
        boolean isAdd = remindTime.isAdd();
        if (isAdd) {
            holder.cbCheck.setImageResource(R.mipmap.ic_remind_add);
        } else {
            holder.cbCheck.setImageResource(R.mipmap.ic_remind_minus);
        }
        if(!isEdit){
            holder.cbCheck.setEnabled(false);
            holder.etRemindDate.setEnabled(false);
        }else{
            holder.cbCheck.setEnabled(true);
            holder.etRemindDate.setEnabled(true);
        }
    }

    @Override
    public int getCount() {
        return remindTimes.size();
    }

    @Override
    public Object createViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    static class ViewHolder {
        private OnSubitemClickListener onSubitemClickListener;
        private int pos;
        @Bind(R.id.et_remind_date)
        EditText etRemindDate;
        @Bind(R.id.tv3)
        TextView tv3;
        @Bind(R.id.cb_check)
        ImageView cbCheck;

        @OnClick({R.id.et_remind_date, R.id.cb_check})
        public void onClick(View view) {
            if (onSubitemClickListener != null) {
                onSubitemClickListener.onSubitemClick(pos, view);
            }
        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
