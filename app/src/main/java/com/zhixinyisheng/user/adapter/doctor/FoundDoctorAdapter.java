package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.doctor.Doctor;
import com.zhixinyisheng.user.util.GlideUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yuanyx on 2016/12/28.
 */

public class FoundDoctorAdapter extends ArrayListAdapter<Doctor.ListBean> {
    private Context mContext;

    public FoundDoctorAdapter(Context context) {
        super(context, R.layout.item_doctor);
        this.mContext = context;
    }


    @Override
    public void bindViewHolder(int position, Object viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Doctor.ListBean doctor = getItem(position);
        holder.tvBeGoodAt.setText("擅长疾病：" + doctor.getBeGoodAt());
        holder.tvDepartment.setText(doctor.getDepartment());
        holder.tvDoctor.setText(doctor.getName());
        holder.tvPhysician.setText(doctor.getPhysician());
        holder.tvOrganization.setText(doctor.getOrganization());
        String day = doctor.getDayMoney();
        String week = doctor.getWeekMoney();
        String year = doctor.getYearMoney();
        if (!"".equals(day)) {
            holder.tvMoeny.setText("￥" + day + "起");
        } else if ("".equals(day) && !"".equals(week)) {
            holder.tvMoeny.setText("￥" + week + "起");
        } else {
            holder.tvMoeny.setText("￥" + year + "起");
        }

        holder.tvNum.setText(doctor.getNum() + "人购买");
        holder.tvNumMsg.setText(doctor.getMessageCount() + "条");
        GlideUtil.loadCircleAvatar(mContext, doctor.getAvatar(), holder.ivAvatar);
    }

    @Override
    public Object createViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    static class ViewHolder {
        @Bind(R.id.tv_num_mesg)
        TextView tvNumMsg;
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_doctor)
        TextView tvDoctor;
        @Bind(R.id.tv_physician)
        TextView tvPhysician;
        @Bind(R.id.tv_organization)
        TextView tvOrganization;
        @Bind(R.id.tv_department)
        TextView tvDepartment;
        @Bind(R.id.tv_be_good_at)
        TextView tvBeGoodAt;
        @Bind(R.id.tv_moeny)
        TextView tvMoeny;
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
