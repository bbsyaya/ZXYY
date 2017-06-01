package com.zhixinyisheng.user.adapter.pay;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.pay.IncomeDetail;

import java.util.Date;
import java.util.List;

/**
 * 收入明细
 * Created by 焕焕 on 2017/1/12.
 */
public class IncomeDetailAdapter extends CommonAdapter<IncomeDetail.ListBean> {
    Context context;

    public IncomeDetailAdapter(Context context, List<IncomeDetail.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, IncomeDetail.ListBean item, int positon) {
        Date date = new Date(item.getCreatetime());
        holder.setTextViewText(R.id.tv_id_week, DateTool.getWeekOfDate(date));
        holder.setTextViewText(R.id.tv_id_date, DateTool.getFormatTime(item.getCreatetime()));
        holder.setTextViewText(R.id.tv_id_money, item.getMoney());
        holder.setTextViewText(R.id.tv_id_title, item.getTitle());
//        holder.setTextViewText(R.id.tv_id_day,DateTool.showDate(item.getCreatetime(),"yyyy-MM-dd"));

        double createTime = Double.parseDouble(DateTool.getFormatDateExceptYear(item.getCreatetime()));
        double currentTime = Double.parseDouble(DateTool.getFormatDateExceptYear(System.currentTimeMillis()));
        if (createTime == currentTime) {
            holder.setTextViewText(R.id.tv_id_day, "今天");
        }else if (currentTime-1==createTime){
            holder.setTextViewText(R.id.tv_id_day, "昨天");
        }else{
            holder.setTextViewText(R.id.tv_id_day,  DateTool.getFormatDate(item.getCreatetime()));
        }

    }
}
