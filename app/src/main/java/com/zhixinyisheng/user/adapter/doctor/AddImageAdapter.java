package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.util.GlideUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 创建人: Yuanyx
 * <p>
 * 创建时间: 2016/12/15  17:12
 * <p>
 * 类说明:
 */

public class AddImageAdapter extends ArrayListAdapter<PhotoInfo> {
    public boolean isDelete;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean detele) {
        isDelete = detele;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (getData().size() == 0) {
            return 1;
        }
        return getData().size() + 1;
    }


    private Context mContext;

    public AddImageAdapter(Context context) {
        super(context, R.layout.item_image);
        this.mContext = context;
    }

    @Override
    public void bindViewHolder(final int position, Object viewHolder) {

        ViewHolder holder = (ViewHolder) viewHolder;

        if (getData().size() == 0 || position == getData().size()) {
            holder.iv.setImageResource(R.mipmap.ic_jubao_add);
        } else if (position != getData().size()) {
            PhotoInfo info = getItem(position);
            String str = info.getPhotoPath();
            GlideUtil.loadlocalImage(mContext, new File(str), holder.iv);
        }
    }

    @Override
    public Object createViewHolder(View convertView) {
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
