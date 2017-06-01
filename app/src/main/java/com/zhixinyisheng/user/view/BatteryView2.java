package com.zhixinyisheng.user.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zhixinyisheng.user.ui.data.xinlv.XinLvHandwritingAty;
import com.zhixinyisheng.user.util.Colors;

/***
 * 心率手写的控件
 */
public class BatteryView2 extends View {

    /**
     * 画笔信息
     */
    private Paint mPowerPaint;
    /**
     * View高宽
     */
    private int bbwidth = new XinLvHandwritingAty().xxwidth;
    private int bbheigth =  new XinLvHandwritingAty().xxheight;

    /**
     *
     * 红色高度
     */
    private float mPower = 0f;
    /**
     *
     * 红色形状
     */
    private RectF mPowerRect;

    public BatteryView2(Context context) {
        super(context);
        initView();
    }

    public BatteryView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BatteryView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {

        /**
         * 设置红色画笔
         */
        mPowerPaint = new Paint();
        mPowerPaint.setColor(Colors.mainColor);
        mPowerPaint.setAntiAlias(true);
        mPowerPaint.setStyle(Style.FILL);

        mPowerRect = new RectF(0f, bbheigth-(mPower/120)*bbheigth, bbwidth, bbheigth);//和下面那个一样  但是2个都要写
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//        canvas.translate(xx, yy);
        mPowerRect = new RectF(0f, bbheigth-(mPower/120)*bbheigth, bbwidth, bbheigth);//
        canvas.drawRect(mPowerRect, mPowerPaint);// 画红色
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        bbwidth = MeasureSpec.getSize(widthMeasureSpec);
        bbheigth = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(bbwidth, bbheigth);
    }

    /**
     * @category 设置红色
     * @param power
     */
    public void setPower(float power) {
        mPower = power;
        if (mPower < 0) {
            mPower = 0;
        }
        mPowerRect = new RectF(0f, bbheigth-(mPower/120)*bbheigth, bbwidth, bbheigth);//
        invalidate();
    }


}  