package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.doctor.DoctorDynamic;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.util.DateUtils;
import com.zhixinyisheng.user.util.StringFormatUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yuanyx on 2016/12/29.
 */

public class DoctorDynamicAdapter extends ArrayListAdapter2<DoctorDynamic.ListBean> {
    private Context context;
    private boolean isMe;
    public OnSubitemClickListener onSubitemClickListener;

    public void setOnSubitemClickListener(OnSubitemClickListener onSubitemClickListener) {
        this.onSubitemClickListener = onSubitemClickListener;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public DoctorDynamicAdapter(Context context) {
        super(context, R.layout.item_docotr_dynamic);
        this.context = context;
    }



    @Override
    public void bindViewHolder(int position, Object viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.onSubitemClickListener = onSubitemClickListener;
        holder.pos = position;
        DoctorDynamic.ListBean bean = getItem(position);
        String content = bean.getContent();
        if (content != null && content.length() >= 45) {
            StringFormatUtil spanStr = new StringFormatUtil(context, content.substring(0, 35) + "...",
                    "...", R.color.maincolor).fillColor();
            holder.tvContenet.setText(spanStr.getResult());
        } else {
            holder.tvContenet.setText(content);
        }

        if (isMe) {
            holder.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.tvDelete.setVisibility(View.GONE);
        }

        int isZan = bean.getIsZan();
        if (isZan == 0) {
            holder.tvZan.setSelected(false);
        } else {
            holder.tvZan.setSelected(true);
        }
        holder.tvZan.setText(bean.getZan() + "");
        String strTime = DateUtils.convertTimeToFormat(bean.getCreatetime() / (long) 1000);
        holder.tvDate.setText(strTime);

        ImageAdapter imageAdapter = (ImageAdapter) holder.gvPic.getAdapter();
        String picUrl = bean.getPicUrl();
        if (picUrl != null && !"".equals(picUrl)) {
            String[] arrayStr = picUrl.split(",");
            List<String> stringList = Arrays.asList(arrayStr);
            imageAdapter.setData(stringList);
        }

    }

    @Override
    public void createViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        ImageAdapter imageAdapter = new ImageAdapter(context);
        holder.gvPic.setAdapter(imageAdapter);
        viewHolder = holder;
    }

    static class ViewHolder {
        private OnSubitemClickListener onSubitemClickListener;
        private int pos;

        @Bind(R.id.tv_contenet)
        TextView tvContenet;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_zan)
        TextView tvZan;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.gv_pic)
        GridView gvPic;

        @OnClick({R.id.tv_zan, R.id.tv_delete, R.id.rl_ctrl})
        public void onClick(View v) {
            if (onSubitemClickListener != null) {
                onSubitemClickListener.onSubitemClick(pos, v);
            }
        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
