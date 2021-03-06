/*
 * Copyright (c) 2015 LingoChamp Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhixinyisheng.user.view.blood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.zhixinyisheng.user.R;

import java.lang.ref.WeakReference;

/**
 * Created by Jacksgong on 12/8/15.
 * 里边的圈
 */
public class MagicProgressCircle1 extends View implements ISmoothTarget {

    // ColorInt开始值
    private int startColor;
    // ColorInt 结束值
    private int endColor;
    // ColorInt 默认值
    private int defaultColor;
    //百分比的颜色
    private int percentEndColor;
    //划的宽度
    private int strokeWidth;
    //百分比
    private float percent;

    // 用于渐变
    private Paint paint;
    Paint paint1 = new Paint();
    Paint paint2 = new Paint();

    int[] colors;
    float[] positions;

    private SmoothHandler smoothHandler;

    public MagicProgressCircle1(Context context) {
        super(context);
        init(context, null);
    }

    public MagicProgressCircle1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MagicProgressCircle1(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    // public MagicProgressCircle(Context context, AttributeSet attrs, int
    // defStyleAttr, int defStyleRes) {
    // super(context, attrs, defStyleAttr, defStyleRes);
    // init(context, attrs);
    // }

    private void init(final Context context, final AttributeSet attrs) {
        float defaultPercent = -1;
        if (isInEditMode()) {
            defaultPercent = 0.6f;
        }

        do {
            final int strokeWdithDefaultValue = (int) (50 * getResources()
                    .getDisplayMetrics().density + 0.5f);
            if (context == null || attrs == null) {
                strokeWidth = strokeWdithDefaultValue;
                percent = defaultPercent;
                startColor = getResources().getColor(R.color.mpc_start_color);
                endColor = getResources().getColor(R.color.mpc_end_color);
                defaultColor = getResources().getColor(
                        R.color.mpc_default_color);
                break;
            }

            TypedArray typedArray = null;
            try {
                typedArray = context.obtainStyledAttributes(attrs,
                        R.styleable.MagicProgressCircle);
                percent = typedArray.getFloat(
                        R.styleable.MagicProgressCircle_mpc_percent,
                        defaultPercent);
                strokeWidth = (int) typedArray.getDimension(
                        R.styleable.MagicProgressCircle_mpc_stroke_width,
                        strokeWdithDefaultValue);
                startColor = typedArray.getColor(
                        R.styleable.MagicProgressCircle_mpc_start_color,
                        getResources().getColor(R.color.mpc_start_color));
                endColor = typedArray.getColor(
                        R.styleable.MagicProgressCircle_mpc_end_color,
                        getResources().getColor(R.color.mpc_end_color));
                defaultColor = typedArray.getColor(
                        R.styleable.MagicProgressCircle_mpc_default_color,
                        getResources().getColor(R.color.mpc_default_color));
            } finally {
                if (typedArray != null) {
                    typedArray.recycle();
                }
            }

        } while (false);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        startPaint = new Paint();
        startPaint.setColor(startColor);
        startPaint.setAntiAlias(true);
        startPaint.setStyle(Style.FILL);

        endPaint = new Paint();
        endPaint.setAntiAlias(true);
        endPaint.setStyle(Style.FILL);

        paint1.setAntiAlias(true);
        paint1.setStyle(Style.FILL);
        paint1.setColor(0xffCFCFCF);

        paint2.setAntiAlias(true);
        paint2.setStyle(Style.FILL);
        paint2.setColor(0xffffffff);

        refreshDelta();

        customColors = new int[]{startColor, percentEndColor, defaultColor,
                defaultColor};
        fullColors = new int[]{startColor, endColor};
        emptyColors = new int[]{defaultColor, defaultColor};

        customPositions = new float[4];
        customPositions[0] = 0;
        customPositions[3] = 1;

        extremePositions = new float[]{0, 1};
    }

    private void refreshDelta() {
        int endR = (endColor & 0xFF0000) >> 16;
        int endG = (endColor & 0xFF00) >> 8;
        int endB = (endColor & 0xFF);

        this.startR = (startColor & 0xFF0000) >> 16;
        this.startG = (startColor & 0xFF00) >> 8;
        this.startB = (startColor & 0xFF);

        deltaR = endR - startR;
        deltaG = endG - startG;
        deltaB = endB - startB;
    }

    /**
     * @param percent FloatRange(from = 0.0, to = 1.0)
     */
    public void setPercent(float percent) {
        percent = Math.min(1, percent);
        percent = Math.max(0, percent);

        if (smoothHandler != null) {
            smoothHandler.commitPercent(percent);
        }

        if (this.percent != percent) {
            this.percent = percent;
            invalidate();
        }

    }

    @Override
    public void setSmoothPercent(float percent) {
        if (smoothHandler == null) {
            smoothHandler = new SmoothHandler(new WeakReference<ISmoothTarget>(
                    this));
        }

        smoothHandler.loopSmooth(percent);
    }

    public float getPercent() {
        return this.percent;
    }

    /**
     * @param color ColorInt
     */
    public void setStartColor(final int color) {
        if (this.startColor != color) {
            this.startColor = color;
            // delta变化
            refreshDelta();

            // 渐变前部分
            customColors[0] = color;
            // 前半圆
            startPaint.setColor(color);
            // 全满时 渐变起点
            fullColors[0] = color;

            invalidate();
        }
    }

    public int getStartColor() {
        return this.startColor;
    }

    /**
     * @param color ColorInt
     */
    public void setEndColor(final int color) {
        if (this.endColor != color) {
            this.endColor = color;
            // delta变化
            refreshDelta();

            // 渐变后部分 动态计算#draw
            // 后半圆 需要动态计算#draw，在某些情况下没有

            // 全满时 渐变结束
            fullColors[1] = color;

            invalidate();
        }
    }

    public int getEndColor() {
        return this.endColor;
    }

    /**
     * @param color ColorInt
     */
    public void setDefaultColor(final int color) {
        if (this.defaultColor != color) {
            this.defaultColor = color;

            // 渐变后半部分
            customColors[2] = color;
            customColors[3] = color;

            // percent = 0
            emptyColors[0] = color;
            emptyColors[1] = color;

            invalidate();
        }
    }

    public int getDefaultColor() {
        return this.defaultColor;
    }

    /**
     * @param width px
     */
    public void setStrokeWidth(final int width) {
        if (this.strokeWidth != width) {
            this.strokeWidth = width;
            // 画描边的描边变化
            paint.setStrokeWidth(width);

            // 会影响measure
            requestLayout();
        }
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    private int deltaR, deltaB, deltaG;
    private int startR, startB, startG;

    private void calculatePercentEndColor(final float percent) {
        percentEndColor = ((int) (deltaR * percent + startR) << 16)
                + ((int) (deltaG * percent + startG) << 8)
                + ((int) (deltaB * percent + startB)) + 0xFF000000;
    }

    int index, w, h;
    RectF rectF1 = new RectF();
    RectF rectF2 = new RectF();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.rectF.left = getMeasuredWidth() / 2 - strokeWidth / 2;
        this.rectF.top = 0;
        this.rectF.right = getMeasuredWidth() / 2 + strokeWidth / 2;
        this.rectF.bottom = strokeWidth;

        w = MeasureSpec.getSize(widthMeasureSpec);
        h = MeasureSpec.getSize(heightMeasureSpec);
        w = Math.min(w, h);
        setMeasuredDimension(w, w);
        rectF1 = new RectF(0, 0, w, w);
        rectF2 = new RectF(strokeWidth, strokeWidth, getMeasuredWidth()
                - strokeWidth, getMeasuredWidth() - strokeWidth);
    }

    private Paint startPaint;
    private Paint endPaint;

    private final RectF rectF = new RectF();

    private int[] customColors;
    private int[] fullColors;
    private int[] emptyColors;
    private float[] customPositions;
    private float[] extremePositions;

    // 目前由于SweepGradient赋值只在构造函数，无法pre allocate & reuse instead
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int restore = canvas.save();

        final int cx = getMeasuredWidth() / 2;
        final int cy = getMeasuredHeight() / 2;

        final int radius = getMeasuredWidth() / 2 - strokeWidth / 2;
//
        float drawPercent = percent;
        if (drawPercent > 0.97 && drawPercent < 1) {
            // 设计师说这样比较好
            drawPercent = 0.97f;
        }
        // 背景圆
        canvas.save();
        canvas.drawArc(rectF1, -61, 302, true, paint1);
        canvas.restore();

        // 背景白园
        canvas.save();
        canvas.drawArc(rectF2, -90-145, 360, true, paint2);
        canvas.restore();
        // 绘制开始的半圆
        canvas.save();
        canvas.rotate(30, cx, cy);
        canvas.drawArc(rectF, 90f, 180f, true, paint1);
        canvas.restore();
        // 绘制结束的半圆
        canvas.save();
        endPaint.setColor(percentEndColor);
        canvas.rotate(-30, cx, cy);
        canvas.drawArc(rectF, -90f, 180f, true, paint1);
        canvas.restore();

        // 画渐变圆
        canvas.save();
        canvas.rotate(-61, cx, cy);

        if (drawPercent > 308 / 360f) {
            drawPercent =308 / 360f;
        }
        if (drawPercent <= 308/ 360f && drawPercent >= 0) {
            calculatePercentEndColor(drawPercent);
            customColors[1] = percentEndColor;
            colors = customColors;
            customPositions[1] = drawPercent;
            customPositions[2] = drawPercent;
            positions = customPositions;
        }
        if (drawPercent <=308 / 360f && drawPercent >= 0) {
            final SweepGradient sweepGradient = new SweepGradient(
                    getMeasuredWidth() / 2, getMeasuredHeight() / 2, colors,
                    positions);
            paint.setShader(sweepGradient);
            canvas.drawCircle(cx, cy, radius, paint);

            canvas.restore();
            canvas.save();
        }

        if (drawPercent > 0) {

            // 绘制结束的半圆
            if (drawPercent <=308/ 360f) {
                canvas.save();
                endPaint.setColor(percentEndColor);
                canvas.rotate(
                        ((int) Math.floor(360.0f * drawPercent)) + 28, cx,
                        cy);
                canvas.drawArc(rectF, -90f, 180f, true, endPaint);
                canvas.restore();
            }

            canvas.save();
            // 绘制开始的半圆
            canvas.rotate(30, cx, cy);
            canvas.drawArc(rectF, 90f, 180f, true, startPaint);
            canvas.restore();
        }

        canvas.restoreToCount(restore);

    }
}
