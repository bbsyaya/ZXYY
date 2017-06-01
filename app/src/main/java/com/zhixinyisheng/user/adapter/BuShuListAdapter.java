package com.zhixinyisheng.user.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.BuShuZXT;

import java.util.List;

/**
 * 步数适配器
 * Created by 焕焕 on 2016/11/1.
 */
public class BuShuListAdapter extends CommonAdapter<BuShuZXT.ListBean> {
    Context context;
    public BuShuListAdapter(Context context, List<BuShuZXT.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context =context;
    }

    @Override
    public void convert(ViewHolder holder, BuShuZXT.ListBean item, int positon) {
        holder.setTextViewText(R.id.item_tvt,item.getBYTIME());
        holder.setTextViewText(R.id.item_tvsj,item.getSTEPS()+"");
        holder.setTextViewText(R.id.item_tvd,context.getResources().getString(R.string.StepUnit));
    }
}
