package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhixinyisheng.user.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yuanyx on 2016/12/28.
 */

public class ProvinceAdapter extends ArrayListAdapter<String> {
    public ProvinceAdapter(Context context) {
        super(context, R.layout.item_area);
    }
    private int choosePos=0;

    public void setChoosePos(int choosePos) {
        this.choosePos = choosePos;
        notifyDataSetChanged();
    }
    public int getChoosePos(){
        return choosePos;
    };

    @Override
    public void bindViewHolder(int position, Object viewHolder) {
        String province=getItem(position);
        ViewHolder holder= (ViewHolder) viewHolder;
        holder.tvArea.setGravity(Gravity.CENTER);
        holder.tvArea.setText(province);
        if(position==choosePos){
            holder.tvArea.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tvArea.setTextColor(Color.parseColor("#000000"));
        }else{
            holder.tvArea.setBackgroundColor(Color.parseColor("#c0c0c0"));
            holder.tvArea.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public Object createViewHolder(View convertView) {
        ViewHolder holder=new ViewHolder(convertView);
        return holder;
    }

    static class ViewHolder {
        @Bind(R.id.tv_area)
        TextView tvArea;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
