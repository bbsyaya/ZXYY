package com.zhixinyisheng.user.adapter.pay;

import android.content.Context;
import android.widget.ImageView;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.doctor.Doctor;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 支付成功界面的适配器
 * Created by 焕焕 on 2017/1/9.
 */
public class PaySuccessAdapter extends CommonAdapter<Doctor.ListBean> {
    Context context;
    public PaySuccessAdapter(Context context, List<Doctor.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context =context;
    }


    @Override
    public void convert(ViewHolder holder, Doctor.ListBean item, int positon) {
        Glide.with(context).load(item.getAvatar()).
                placeholder(R.mipmap.ic_default_pic).
                error(R.mipmap.ic_default_pic).
                bitmapTransform(new CropCircleTransformation(context)).into((ImageView) holder.getView(R.id.psitem_iv_avatar));
        holder.setTextViewText(R.id.psitem_tv_name,item.getName()+context.getString(R.string.daifu));
        holder.setTextViewText(R.id.psitem_iv_job,item.getPhysician());
        holder.setTextViewText(R.id.psitem_tv_hospital,item.getOrganization());
        holder.setTextViewText(R.id.psitem_tv_section,item.getDepartment());
        holder.setTextViewText(R.id.tv_messageCount,context.getString(R.string.kuohaomianfei)+item.getMessageCount()+context.getString(R.string.tiaokuohao));
        holder.setTextViewText(R.id.tv_disease,item.getBeGoodAt());

    }
}
