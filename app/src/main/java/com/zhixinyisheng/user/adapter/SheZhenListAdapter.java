package com.zhixinyisheng.user.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas.SheZhenDatas;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 舌诊适配器
 * Created by 焕焕 on 2016/11/30.
 */
public class SheZhenListAdapter extends CommonAdapter<SheZhenDatas.ListBean>{
    Context context;
    public SheZhenListAdapter(Context context, List<SheZhenDatas.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, SheZhenDatas.ListBean item, int positon) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(item.getCreateTime());
        holder.setTextViewText(R.id.item_shezhentime,dateString);
        holder.setTextViewText(R.id.item_shezhencontent,item.getUserResult());
    }
}
