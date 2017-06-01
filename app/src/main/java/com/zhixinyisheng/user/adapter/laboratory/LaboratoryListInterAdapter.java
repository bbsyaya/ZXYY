package com.zhixinyisheng.user.adapter.laboratory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.application.Constant;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.lab.LaboratoryInterList;
import com.zhixinyisheng.user.util.Colors;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 焕焕 on 2017/2/25.
 */
public class LaboratoryListInterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LaboratoryInterList> data;
    private Context context;
    public void setData(List<LaboratoryInterList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public LaboratoryInterList getItem(int pos) {
        return data.get(pos);
    }


    public void addData(List<LaboratoryInterList> newList) {
        data.addAll(newList);
        notifyDataSetChanged();
    }

    public LaboratoryListInterAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lab_list_inter, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        final LaboratoryInterList bean = data.get(position);
        holder.tvInterName.setText(bean.getName());
        holder.tvInterData.setText(bean.getData());
        holder.tvInterUnit.setText(bean.getUnit());
        boolean isZheXianTu = bean.isZheXianTu();
        if (isZheXianTu){
            holder.ivInterZheXianTu.setVisibility(View.VISIBLE);
        }else{
            holder.ivInterZheXianTu.setVisibility(View.GONE);
        }
        holder.ivInterZheXianTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = bean.getName();
                Intent intent = new Intent(Constant.LAB_INTER_ADAPTER);
                intent.putExtra("labName", name);
                context.sendBroadcast(intent);
            }
        });
        String colorFlag = bean.getColorFlag();
        if (null != colorFlag){
            if (colorFlag.equals("1")){//黑色
                holder.tvInterName.setTextColor(Colors.textColor);
                holder.tvInterData.setTextColor(Colors.textColor);
                holder.tvInterUnit.setTextColor(Colors.textColor);
            }else if (colorFlag.equals("2")){//黄色
                holder.tvInterName.setTextColor(Colors.yellowColor);
                holder.tvInterData.setTextColor(Colors.yellowColor);
                holder.tvInterUnit.setTextColor(Colors.yellowColor);
            }else if (colorFlag.equals("3")){//红色
                holder.tvInterName.setTextColor(Colors.redColor);
                holder.tvInterData.setTextColor(Colors.redColor);
                holder.tvInterUnit.setTextColor(Colors.redColor);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_inter_name)
        TextView tvInterName;
        @Bind(R.id.tv_inter_data)
        TextView tvInterData;
        @Bind(R.id.tv_inter_unit)
        TextView tvInterUnit;
        @Bind(R.id.iv_inter_zhexiantu)
        ImageView ivInterZheXianTu;
        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
