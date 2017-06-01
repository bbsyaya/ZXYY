package com.zhixinyisheng.user.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.zhixinyisheng.user.util.DensityUtils;

/**
 * 设置最大高度的listView
 * Created by 焕焕 on 2017/4/15.
 */

public class MaxListView extends ListView {
    public MaxListView(Context context) {
        super(context);
    }

    public MaxListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(DensityUtils.dp2px(300),
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
