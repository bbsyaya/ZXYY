package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.util.GlideUtil;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建人: Yuanyx
 * <p>
 * 创建时间: 2016/12/15  17:12
 * <p>
 * 类说明:
 */

public class ImageAdapter extends ArrayListAdapter<String> {
    public boolean isDelete;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean detele) {
        isDelete = detele;
        notifyDataSetChanged();
    }

    public void clearData() {
        if (getData() != null && getData().size() != 0) {
            getData().clear();
            notifyDataSetChanged();
        }
    }

    private Context mContext;

    public ImageAdapter(Context context) {
        super(context, R.layout.item_image);
        this.mContext = context;
    }


    @Override
    public void bindViewHolder(final int position, Object viewHolder) {
        String url = getItem(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.zwt_03)
                .into(holder.iv);
    }

    @Override
    public ViewHolder createViewHolder(View convertView) {
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    static class ViewHolder {

        @Bind(R.id.iv)
        ImageView iv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
