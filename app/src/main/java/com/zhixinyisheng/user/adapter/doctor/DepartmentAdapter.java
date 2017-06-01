package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;

import java.util.List;

/**
 * 科室适配器
 * Created by 焕焕 on 2017/1/9.
 */
public class DepartmentAdapter extends CommonAdapter<String> {

    public DepartmentAdapter(Context context, List<String> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);

    }

    @Override
    public void convert(ViewHolder holder, String item, int positon) {
        holder.setTextViewText(R.id.tv_area,item);
    }
}

