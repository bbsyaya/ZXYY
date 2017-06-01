package com.zhixinyisheng.user.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zhixinyisheng.user.util.DensityUtils;

/**
 * Created by 焕焕 on 2017/4/15.
 */

public class MaxLinearLayout extends LinearLayout {

    public MaxLinearLayout(Context context) {
        super(context);
    }

    public MaxLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(DensityUtils.dp2px(500),
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
