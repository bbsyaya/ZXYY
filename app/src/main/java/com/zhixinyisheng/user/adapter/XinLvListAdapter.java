package com.zhixinyisheng.user.adapter;

import android.content.Context;
import android.view.View;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.XinLvZXT;
import com.zhixinyisheng.user.util.Colors;

import java.util.List;

/**
 * 心率列表的适配器
 * Created by 焕焕 on 2016/10/28.
 */
public class XinLvListAdapter extends CommonAdapter<XinLvZXT.ListBean> {
    Context context;

    public XinLvListAdapter(Context context, List<XinLvZXT.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, XinLvZXT.ListBean item, int positon) {
        holder.setTextViewText(R.id.item_tvt, item.getBYTIME());
        holder.setTextViewText(R.id.item_tvsj, item.getNum() + "");
        holder.setTextViewText(R.id.item_tvd, context.getResources().getString(R.string.heartUnit));
        int d = item.getNum();
        if (d >= 60 && d <= 120) {
            holder.setTextViewTextColor(R.id.item_tvt, Colors.textColor);
            holder.setTextViewTextColor(R.id.item_tvsj, Colors.textColor);
            holder.setTextViewTextColor(R.id.item_tvd, Colors.textColor);
            holder.setViewVisibility(R.id.item_iv, View.INVISIBLE);
        } else if (d > 160 || d < 40) {
            holder.setTextViewTextColor(R.id.item_tvt, 0xffff0000);
            holder.setTextViewTextColor(R.id.item_tvsj, 0xffff0000);
            holder.setTextViewTextColor(R.id.item_tvd, 0xffff0000);
            holder.setViewVisibility(R.id.item_iv, View.VISIBLE);
        } else {
            holder.setTextViewTextColor(R.id.item_tvt, 0xffffd700);
            holder.setTextViewTextColor(R.id.item_tvsj, 0xffffd700);
            holder.setTextViewTextColor(R.id.item_tvd, 0xffffd700);
            holder.setViewVisibility(R.id.item_iv, View.INVISIBLE);
        }

    }
}
