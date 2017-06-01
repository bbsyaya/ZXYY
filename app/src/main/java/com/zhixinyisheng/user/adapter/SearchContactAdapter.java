package com.zhixinyisheng.user.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.util.GlideUtil;

import java.util.List;

/**
 * Created by 焕焕 on 2017/4/19.
 */

public class SearchContactAdapter extends CommonAdapter<EaseUser>{
    private Context context;
    public SearchContactAdapter(Context context, List<EaseUser> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, EaseUser item, int positon) {
        ImageView ivAvatar = holder.getView(R.id.avatar);
        TextView tvName = holder.getView(R.id.name);
        GlideUtil.loadCircleAvatar(context,item.getAvatar(),ivAvatar);
        tvName.setText(item.getNick());
    }
}
