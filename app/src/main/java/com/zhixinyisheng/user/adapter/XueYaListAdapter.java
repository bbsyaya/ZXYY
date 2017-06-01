package com.zhixinyisheng.user.adapter;

import android.content.Context;
import android.view.View;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.XueYaZXT;
import com.zhixinyisheng.user.util.Colors;

import java.util.List;

/**
 *血压列表的适配器
 * Created by 焕焕 on 2016/10/30.
 */
public class XueYaListAdapter extends CommonAdapter<XueYaZXT.ListBean> {
    Context context;
    public XueYaListAdapter(Context context, List<XueYaZXT.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context =context;
    }

    @Override
    public void convert(ViewHolder holder, XueYaZXT.ListBean item, int positon) {
        holder.setTextViewText(R.id.itxy_tvt,item.getBYTIME());
        holder.setTextViewText(R.id.itxy_tvsj,(int)item.getLOWPRESSURE()+"");
        holder.setTextViewText(R.id.itxy_tvh,(int)item.getHIGHPRESSURE()+"");
        double l= item.getLOWPRESSURE();
        double h = item.getHIGHPRESSURE();
        if (h>=90&&h<=140&&l>=60&&l<=90) {
            holder.setTextViewTextColor(R.id.itxy_tvt, Colors.textColor);
            holder.setTextViewTextColor(R.id.itxy_tvsj,Colors.textColor);
            holder.setTextViewTextColor(R.id.itxy_tvh,Colors.textColor);
            holder.setTextViewTextColor(R.id.itxy_tvx,Colors.textColor);
            holder.setTextViewTextColor(R.id.itxy_tvd,Colors.textColor);
            holder.setViewVisibility(R.id.itxy_iv,View.INVISIBLE);
        }else if (h>140&&h<=179&&l>90&&l<=109) {
            holder.setTextViewTextColor(R.id.itxy_tvt,0xffffd700);
            holder.setTextViewTextColor(R.id.itxy_tvsj,0xffffd700);
            holder.setTextViewTextColor(R.id.itxy_tvh,0xffffd700);
            holder.setTextViewTextColor(R.id.itxy_tvx,0xffffd700);
            holder.setTextViewTextColor(R.id.itxy_tvd,0xffffd700);
            holder.setViewVisibility(R.id.itxy_iv,View.INVISIBLE);
        }else {
            holder.setTextViewTextColor(R.id.itxy_tvt,0xffff0000);
            holder.setTextViewTextColor(R.id.itxy_tvsj,0xffff0000);
            holder.setTextViewTextColor(R.id.itxy_tvh,0xffff0000);
            holder.setTextViewTextColor(R.id.itxy_tvx,0xffff0000);
            holder.setTextViewTextColor(R.id.itxy_tvd,0xffff0000);
            holder.setViewVisibility(R.id.itxy_iv,View.VISIBLE);
        }
    }
}
