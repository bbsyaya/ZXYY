package com.zhixinyisheng.user.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas_zhexiantu.PulmonaryZXT;

import java.util.List;

/**
 * 肺活量折线图下方的列表
 * Created by 焕焕 on 2017/1/23.
 */
public class PulmonaryTrendAdapter  extends CommonAdapter<PulmonaryZXT.ListBean>{
    public PulmonaryTrendAdapter(Context context, List<PulmonaryZXT.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, PulmonaryZXT.ListBean item, int positon) {
        holder.setTextViewText(R.id.item_tvt,item.getBYTIME());
        holder.setTextViewText(R.id.item_tvsj,(int)item.getNUM()+"");
        holder.setTextViewText(R.id.item_tvd,"");
    }
}
