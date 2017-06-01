package com.zhixinyisheng.user.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zhixinyisheng.user.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/8/5.
 */
public class NewBeijing extends SurfaceView implements SurfaceHolder.Callback {

    private float bg_y;
    private float bg_y2;
    private Bitmap background;
    private Bitmap background2;
    private Paint paint = null;


    public NewBeijing(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        paint = new Paint();
        getHolder().addCallback(this);
    }

    public NewBeijing(Context context, AttributeSet attrs) {

        super(context, attrs);
        paint = new Paint();
        getHolder().addCallback(this);
    }

    public NewBeijing(Context context) {
        super(context);
        paint = new Paint();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        initBitmap();
        startTimer();
    }

    private void initBitmap() {
//        R.drawable.hhg_07
        background = BitmapFactory.decodeResource(getResources(), R.drawable.hhg_07);
        background2 = BitmapFactory.decodeResource(getResources(), R.drawable.hhg_07);
        bg_y = 0;
        //bg_y2 = bg_y-getWidth();//向右移
        bg_y2 = bg_y + getWidth();//向左移
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopTimer();

    }

    public void drawSelf() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.save();
        canvas.drawBitmap(background, (bg_y), 0, paint);

        canvas.drawBitmap(background2, (bg_y2), 0, paint);

        canvas.restore();
        getHolder().unlockCanvasAndPost(canvas);
    }

    private Timer timer = null;
    private TimerTask task = null;

    public void startTimer() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                try {
                    drawSelf();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                viewLogicright();
                viewLogicLeft();
            }
        };
        timer.schedule(task, 1, 150);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 向右平行移动
     */

    private void viewLogicright() {
        if (bg_y > bg_y2) {
            bg_y += 15;
            bg_y2 = bg_y - getWidth();
        } else {
            bg_y2 += 15;
            bg_y = bg_y2 - getWidth();
        }
        if (bg_y >= getWidth()) {
            bg_y = bg_y2 - getWidth();

        } else if (bg_y2 >= getWidth()) {
            bg_y2 = bg_y - getWidth();

        }
    }

    /**
     * 向左平行移动
     */

    private void viewLogicLeft() {
        if (bg_y < bg_y2) {
            bg_y -= 10;
            bg_y2 = bg_y + getWidth();
        } else {
            bg_y2 -= 10;
            bg_y = bg_y2 + getWidth();

        }
        if (bg_y <= -getWidth()) {
            bg_y = getWidth() - bg_y2;
        } else if(bg_y2<=-getWidth()){
            bg_y2=bg_y+getWidth();

        }

    }


}
