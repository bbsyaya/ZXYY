package com.zhixinyisheng.user.ui.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 下拉刷新上拉加载的demo
 * Created by 焕焕 on 2017/4/27.
 */

public class RefreshDemoActivity extends BaseAty implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_refresh_demo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(this,getData("init"));
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override//滚动状态变化时回调
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = mLayoutManager .findLastVisibleItemPosition();
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                //滑动停止并且底部不可滚动（即滑动到底部） 加载更多
                if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1&&visibleItemCount>=10){
                    if(mAdapter != null) {
                        mAdapter.showFooter();
                    }
                    loadMore();
                }
            }

            @Override//滚动时回调
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void loadMore(){
        mAdapter.setLoadStatus(MyAdapter.LoadStatus.LOADING_MORE);
        mAdapter.refresh();
        new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    final List<String> list=getData("load more");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addAll(list);
                            mAdapter.setLoadStatus(MyAdapter.LoadStatus.CLICK_LOAD_MORE);
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onRefresh() {
        new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    final List<String> list=getData("refresh");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.reSetData(list);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public List<String> getData(String flag){
        int idx=1;
        if(mAdapter!=null){
            idx=mAdapter.getItemCount();
        }
        List<String> list=new ArrayList<>(10);
        for (int i=0;i<10;i++){
            list.add(flag+":"+(idx+i));
        }
        return list;
    }


}
