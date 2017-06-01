package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.doctor.Service;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.interfaces.OnSubitemTouchListener;
import com.zhixinyisheng.user.util.DateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Yuanyx on 2016/12/30.
 */

public class MyServiceAdapter extends ArrayListAdapter<Service.ListBean> {
    private Context context;
    private OnSubitemClickListener onSubitemClickListener;

    public void setOnSubitemTouchListener(OnSubitemTouchListener onSubitemTouchListener) {
        this.onSubitemTouchListener = onSubitemTouchListener;
    }

    private OnSubitemTouchListener onSubitemTouchListener;

    public void setOnSubitemClickListener(OnSubitemClickListener onSubitemClickListener) {
        this.onSubitemClickListener = onSubitemClickListener;
    }

    public MyServiceAdapter(Context context) {
        super(context, R.layout.item_myservice);
        this.context = context;
    }

    @Override
    public void bindViewHolder(int position, Object viewHolder) {
        Service.ListBean bean = getItem(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.onSubitemClickListener = onSubitemClickListener;
        holder.onSubitemTouchListener = onSubitemTouchListener;
        holder.pos = position;
        long createTime = bean.getCreatetime();
        String createTimeStr = DateUtils.timeStampToStr(createTime);
        holder.tvTime.setText(createTimeStr);

        holder.tvName.setText(bean.getName() + "\t大夫");

        String titie = bean.getTitle();
        holder.tvIsPay.setText(titie);
        //isAppraise:是否评价 1 是 0 不是
        int isAppraise = bean.getIsAppraise();
        if (1 == isAppraise) {
            holder.tvState.setText("已评价");
            holder.tvState.setTextColor(Color.parseColor("#5e5e5e"));
        } else if (isAppraise == 0) {
            holder.tvState.setText(".待评价");
            holder.tvState.setTextColor(Color.parseColor("#EFA57A"));
        }

        holder.tvDepartment.setText(bean.getSection());
    }

    @Override
    public Object createViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    static class ViewHolder {
        private OnSubitemClickListener onSubitemClickListener;
        private OnSubitemTouchListener onSubitemTouchListener;
        private int pos;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_is_pay)
        TextView tvIsPay;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.tv_department)
        TextView tvDepartment;

        @OnClick({R.id.tv_state, R.id.ll_ctrl})
        public void onClick(View view) {
            if (onSubitemClickListener != null)
                onSubitemClickListener.onSubitemClick(pos, view);
        }

        @OnLongClick(R.id.ll_ctrl)
        public boolean onLongClick(View view) {
            if (onSubitemTouchListener != null) {
                onSubitemTouchListener.onSubitemLongClick(pos, view);
            }
            return true;
        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
