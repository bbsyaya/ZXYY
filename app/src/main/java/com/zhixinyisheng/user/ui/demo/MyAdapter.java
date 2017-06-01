package com.zhixinyisheng.user.ui.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixinyisheng.user.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 焕焕 on 2017/4/27.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public enum LoadStatus {
        CLICK_LOAD_MORE,//上拉加载更多
        LOADING_MORE//正在加载更多
    }

    private LoadStatus mLoadStatus = LoadStatus.CLICK_LOAD_MORE;//上拉加载的状态
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private Context mContext;
    private List<String> mList;
    protected boolean mIsShowFooter;

    public MyAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        } else if (viewType == VIEW_TYPE_ITEM) {
            return onCreateItemViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                onBindItemViewHolder(holder, position);
                break;
            case VIEW_TYPE_FOOTER:
                onBindFooterViewHolder(holder, position, mLoadStatus);
                break;
        }
    }

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footer_layout_demo, parent, false);
        return new FooterViewHolder(view);
    }

    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_demo, parent, false);
        return new ItemViewHolder(view);
    }

    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int poition, LoadStatus loadStatus) {
        FooterViewHolder viewHolder = (FooterViewHolder) holder;
        switch (loadStatus) {
            case CLICK_LOAD_MORE:
                viewHolder.mLoadingLayout.setVisibility(View.GONE);
                viewHolder.mClickLoad.setVisibility(View.VISIBLE);
                break;
            case LOADING_MORE:
                viewHolder.mLoadingLayout.setVisibility(View.VISIBLE);
                viewHolder.mClickLoad.setVisibility(View.GONE);
                break;
        }
    }
    public  void refresh(){
        notifyDataSetChanged();
    }
    public void addAll(List<String> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }
    public void setLoadStatus(LoadStatus loadStatus) {
        this.mLoadStatus = loadStatus;
    }
    @Override
    public int getItemCount() {
        if(mList == null)
            return 0;

        int itemSize = mList.size();
        if(mIsShowFooter){
            itemSize += 1;
        }
        return itemSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooterPosition(position)) {
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }
    /**
     * 是否是 footer item
     * @param position
     * @return
     */
    protected boolean isFooterPosition(int position){
        return (getItemCount() - 1) == position;
    }
    /**
     * 显示Footer
     */
    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    /**
     * 隐藏Footer
     */
    public void hideFooter() {
        mIsShowFooter = false;
        notifyItemRemoved(getItemCount());
    }
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTextView.setText(getItem(position));
    }
    public void reSetData(List<String> list) {
        this.mList = list;
        notifyDataSetChanged();
    }
    public String getItem(int position) {
        return mList.get(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textView)
        TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.click_load_txt)
        TextView mClickLoad;
        @Bind(R.id.loading)
        LinearLayout mLoadingLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
