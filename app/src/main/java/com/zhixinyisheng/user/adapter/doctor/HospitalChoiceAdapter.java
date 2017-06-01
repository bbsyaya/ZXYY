package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.doctor.HospitalChoice;

import java.util.List;

/**
 * 医院选择
 * Created by 焕焕 on 2017/1/20.
 */
public class HospitalChoiceAdapter  extends CommonAdapter<HospitalChoice.ListBean>{
    public HospitalChoiceAdapter(Context context, List<HospitalChoice.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, HospitalChoice.ListBean item, int positon) {
        holder.setTextViewText(R.id.tv_hospital_name,item.getName());

    }
}
