package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.graphics.Color;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.doctor.SectionChoice;
import com.zhixinyisheng.user.util.Colors;

import java.util.List;

/**
 * 选择科室适配器
 * Created by 焕焕 on 2017/1/24.
 */
public class SectionChoiceAdapter extends CommonAdapter<SectionChoice.ListBean>{
    private int selectedPosition = -1;// 选中的位置
    public SectionChoiceAdapter(Context context, List<SectionChoice.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    @Override
    public void convert(ViewHolder holder, SectionChoice.ListBean item, int positon) {
        holder.setTextViewText(R.id.tv_section,item.getName());
        if (selectedPosition==positon){
            holder.setBackgroundColor(R.id.rl_section,Colors.homeBgc);
            holder.setTextViewTextColor(R.id.tv_section,Colors.mainColor);
        }else{
            holder.setBackgroundColor(R.id.rl_section, Color.WHITE);
            holder.setTextViewTextColor(R.id.tv_section,Colors.textColor);
        }

    }
}
