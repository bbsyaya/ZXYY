package com.zhixinyisheng.user.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.interfaces.OnLoadMoreListener;

/**
 * Created by Administrator on 2017/4/12.
 */

public class PullToListView extends ListView {
    private Context context;
    private View topView;
    private int lastVisiblePostion;
    private int count;
    private LinearLayout loading_view_layout;
    private LinearLayout end_layout;
    private TextView tvDaodi, tvMorePull;
    private boolean isShowFoot = true;

    public void setShowFoot(boolean showFoot) {
        isShowFoot = showFoot;
    }

    public PullToListView(Context context) {
        super(context);
        this.context = context;
        initData();
    }

    public PullToListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    public PullToListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PullToListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initData();
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        initFootView();
        if (!isShowFoot) {
            end_layout.setVisibility(GONE);
            loading_view_layout.setVisibility(GONE);
        }
        super.setAdapter(adapter);
    }

    private void initFootView() {
        View footView = LayoutInflater.from(context).inflate(R.layout.footer_layout, null, false);
        end_layout = (LinearLayout) footView.findViewById(R.id.end_layout);
        loading_view_layout = (LinearLayout) footView.findViewById(R.id.loading_view_layout);
        tvDaodi = (TextView) footView.findViewById(R.id.tv_daodi);
        tvMorePull = (TextView) footView.findViewById(R.id.tv_more_pull);
        addFooterView(footView);
    }

    private void initData() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    int childCoont = getChildCount();
                    count = getCount();
                    lastVisiblePostion = view.getLastVisiblePosition();
                    topView = view.getChildAt(0);
                    if (onLoadMoreListener == null) {
                        return;
                    }
                    if (getChildCount() > 0 && lastVisiblePostion == count - 1 && isUpwardScroll) {
                        onLoadMoreListener.onLoadMore();
                        if (isShowFoot) {
                            end_layout.setVisibility(GONE);
                            loading_view_layout.setVisibility(VISIBLE);
                        }

                    } else {
                        if (isShowFoot) {
                            end_layout.setVisibility(VISIBLE);
                            loading_view_layout.setVisibility(GONE);
                            tvDaodi.setVisibility(GONE);
                            tvMorePull.setVisibility(VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("onScroll", firstVisibleItem + "--" + visibleItemCount + "--" + totalItemCount);
            }
        });
        setGestureListener();
    }

    private OnLoadMoreListener onLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private float mPosX, mPosY, mCurPosX, mCurPosY;//记录 滑动的位置
    private boolean isUpwardScroll = false;

    /**
     * 设置上下滑动作监听器 为了解决下拉刷新和上啦加载bug
     *
     * @author jczmdeveloper
     */
    private void setGestureListener() {
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        isUpwardScroll = false;
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY > 0
                                && (Math.abs(mCurPosY - mPosY) > 225)) {
                            //向下滑動
                            isUpwardScroll = false;
                        } else if (mCurPosY - mPosY < 0
                                && (Math.abs(mCurPosY - mPosY) > 125)) {
                            //向上滑动
                            isUpwardScroll = true;
                        }
                        break;
                }
                return false;
            }

        });
    }
}
