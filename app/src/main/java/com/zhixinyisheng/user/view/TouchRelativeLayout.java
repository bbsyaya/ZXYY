package com.zhixinyisheng.user.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by 焕焕 on 2017/5/7.
 */

public class TouchRelativeLayout extends RelativeLayout {
    private Context context;
    public TouchRelativeLayout(Context context) {
        super(context);
        this.context = context;
    }

    public TouchRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TouchRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("asjkdhajdh","wewewewe");
            //TODO  处理底部布局
        }
        return true;
    }


}
