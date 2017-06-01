package com.zhixinyisheng.user.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.ShuiMianZXT;

import java.util.List;

/**
 * 睡眠适配器
 * Created by 焕焕 on 2016/11/2.
 */
public class ShuiMianListAdapter extends CommonAdapter<ShuiMianZXT.ListBean> {
    Context context;
    public ShuiMianListAdapter(Context context, List<ShuiMianZXT.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context =context;
    }

    @Override
    public void convert(ViewHolder holder, ShuiMianZXT.ListBean item, int positon) {
        holder.setTextViewText(R.id.item_tvt,item.getBYTIME());
        holder.setTextViewText(R.id.item_tvsj,item.getHOUR()+"");
        holder.setTextViewText(R.id.item_tvd,context.getResources().getString(R.string.hours));
    }
}
