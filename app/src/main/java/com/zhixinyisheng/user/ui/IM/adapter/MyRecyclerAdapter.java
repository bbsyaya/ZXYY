package com.zhixinyisheng.user.ui.IM.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.IM.domain.YichangDataEntity;

import java.util.List;

/**
 * 创建人: Fu
 * <p>
 * 创建时间: 2016/11/9  9:09
 * <p>
 * 类说明:
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<YichangDataEntity> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    String num, danWei;

    public MyRecyclerAdapter(Context context, List<YichangDataEntity> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        for (int i = 0; i < mDatas.get(position).getShuzhi().length(); i++) {
            char item = mDatas.get(position).getShuzhi().charAt(i);
            if (item != '0' && item != '1' && item != '2' && item != '3' && item != '4' && item != '5' && item != '6' && item != '7' && item != '8' && item != '9' && item != '*' && item != '.') {
                num = mDatas.get(position).getShuzhi().substring(0, i);
                danWei = mDatas.get(position).getShuzhi().substring(i, mDatas.get(position).getShuzhi().length());
                break;
            }
        }
        holder.tvn.setText(mDatas.get(position).getName());
        holder.tvd.setText(num);
//        if (num.contains("*")&&num.length()>8&&num.length()<13) {
//            holder.tvd.setTextSize(6);
//        }else if(num.contains("*")&&num.length()>5){
//            holder.tvd.setTextSize(8);
//        }
        if (danWei.contains("+")||danWei.contains("-")||danWei.contains("±")){
            holder.fridd_danwei.setVisibility(View.GONE);
//            holder.tv_middle.setVisibility(View.VISIBLE);
            holder.tvd.setText(danWei);
        }else {
            holder.fridd_danwei.setVisibility(View.VISIBLE);
//            holder.tv_middle.setVisibility(View.GONE);
            holder.fridd_danwei.setText(danWei);
        }
        holder.tvr.setText(mDatas.get(position).getRiqi());

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.frienddetial_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends ViewHolder {

        TextView tvn, tvd, tvr, fridd_danwei,tv_middle;

        public MyViewHolder(View view) {
            super(view);
            tvn = (TextView) view.findViewById(R.id.fridd_it_tvn);
            tvd = (TextView) view.findViewById(R.id.fridd_it_tvs);
            tvr = (TextView) view.findViewById(R.id.fridd_it_tvr);
            fridd_danwei = (TextView) view.findViewById(R.id.fridd_danwei);
            tv_middle = (TextView) view.findViewById(R.id.tv_middle);
        }

    }
}
