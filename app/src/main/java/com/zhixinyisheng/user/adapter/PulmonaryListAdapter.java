package com.zhixinyisheng.user.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas.PulmonaryDatas;
import com.zhixinyisheng.user.util.Colors;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 焕焕 on 2017/1/14.
 */
public class PulmonaryListAdapter extends CommonAdapter<PulmonaryDatas.ListBean> {
    Context context;
    private int selectedPosition = -1;// 选中的位置
    public PulmonaryListAdapter(Context context, List<PulmonaryDatas.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    @Override
    public void convert(final ViewHolder holder, PulmonaryDatas.ListBean item, int positon) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(item.getCREATETIME());
        holder.setTextViewText(R.id.tv_pulmonary_time,dateString);
        holder.setTextViewText(R.id.tv_pulmonary_data,(int)item.getNUM()+"");
        if (selectedPosition==positon){
            holder.setTextViewTextColor(R.id.tv_pulmonary_time, Colors.mainColor);
            holder.setTextViewTextColor(R.id.tv_pulmonary_data, Colors.mainColor);
        }else{
            holder.setTextViewTextColor(R.id.tv_pulmonary_time, Colors.shallowColor);
            holder.setTextViewTextColor(R.id.tv_pulmonary_data, Colors.shallowColor);
        }

    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(int position, String str);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;

    }
}
