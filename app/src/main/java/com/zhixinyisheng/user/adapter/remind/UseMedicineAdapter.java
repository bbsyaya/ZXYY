package com.zhixinyisheng.user.adapter.remind;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.ArrayListAdapter;
import com.zhixinyisheng.user.domain.remind.UseMedicine;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.util.DateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/16.
 */

public class UseMedicineAdapter extends ArrayListAdapter<UseMedicine.ListBean> {
    private OnSubitemClickListener onSubitemClickListener;

    public UseMedicineAdapter(Context context) {
        super(context, R.layout.item_use_medicine);
    }

    public void setOnSubitemClickListener(OnSubitemClickListener onSubitemClickListener) {
        this.onSubitemClickListener = onSubitemClickListener;
    }

    @Override
    public void bindViewHolder(int position, Object viewHolder) {
        UseMedicine.ListBean bean = getItem(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.onSubitemClickListener=onSubitemClickListener;
        holder.pos=position;
        holder.tvName.setText(bean.getMedicienName());
        holder.tvHour.setText(DateUtils.formatDateToHour(bean.getAlertTimeBefore()));
        String note = bean.getRemark();
        if (note == null || "".equals(note)) {
            holder.rlNote.setVisibility(View.GONE);
        } else {
            holder.rlNote.setVisibility(View.VISIBLE);
            holder.tvNote.setText(note);
        }
        //0未吃药
        int isEat = bean.getIsEat();
        if (0 == isEat) {
            holder.ivIcon.setImageResource(R.mipmap.ic_un_use_medicine);
        } else {
            holder.ivIcon.setImageResource(R.mipmap.ic_use_medicine);
        }
        holder.tvNum.setText(bean.getCount());
    }

    @Override
    public Object createViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    static class ViewHolder {
        private OnSubitemClickListener onSubitemClickListener;
        private int pos;
        @Bind(R.id.tv_hour)
        TextView tvHour;
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_note)
        TextView tvNote;
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.rl_note)
        RelativeLayout rlNote;

        @OnClick({R.id.ll_ctrl, R.id.iv_icon})
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
