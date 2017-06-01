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
 * Created by 焕焕 on 2017/4/15.
 */

public class CareListAdapter extends CommonAdapter<EaseUser> {
    private TextView tvNick;
    private ImageView ivAvatar;
    private Context context;
    public CareListAdapter(Context context, List<EaseUser> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, EaseUser item, int positon) {
        tvNick= holder.getView(R.id.iv_pop_nick);
        ivAvatar = holder.getView(R.id.iv_pop_avatar);
        tvNick.setText(item.getNick());
        GlideUtil.loadCircleAvatar(context,item.getAvatar(),ivAvatar);
    }
}
