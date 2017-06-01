package com.zhixinyisheng.user.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.BmiZXT;

import java.util.List;

/**
 * bmi
 * Created by 焕焕 on 2016/11/2.
 */
public class BmiListAdapter extends CommonAdapter<BmiZXT.BmiListBean> {
    Context context;
    public BmiListAdapter(Context context, List<BmiZXT.BmiListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context =context;
    }

    @Override
    public void convert(ViewHolder holder, BmiZXT.BmiListBean item, int positon) {
        holder.setTextViewText(R.id.item_tvt,item.getByTime());
        holder.setTextViewText(R.id.item_tvsj,item.getNum()+"");
        holder.setTextViewText(R.id.item_tvd,"");
    }
}
