package com.zhixinyisheng.user.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.and.yzy.frame.util.DensityUtils;
import com.zhixinyisheng.user.util.Colors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 血压的折线图
 */
public class MyViewBD extends View {
    // 默认边距
    // 原点坐标
    private int Xpoint = 0, Ypoint = 0, stopX = 0, stopY = 0;
    // X,Y轴的单位长度
    private int Xscale = 0;
    private int Yscale = 0;

    List<String> Xlabel = new ArrayList<String>(),
            Ylabel = new ArrayList<String>();
    // 标题文本
    private List<String> data_low = new ArrayList<String>(),
            data_low1 = new ArrayList<String>(),
            data_high = new ArrayList<String>(),
            data_high1 = new ArrayList<String>();

    Paint p, p1, p2, p3, p4, p5, p6, p7;
    boolean isHua = false;
    int changdu = 0;
    int xyortw = 0;
    Context context;

    public MyViewBD(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 10; i++) {
            Ylabel.add("");
        }
        for (int i = 0; i < 7; i++) {
            Xlabel.add("");
        }
        this.context = context;
    }

    // 初始化数据值
    public void init() {
        Xpoint = 80;
        Ypoint = getHeight() - 40;
        stopX = getWidth() - 40;
        stopY = 23 + DensityUtils.dp2px(context, 16);
        Xscale = (stopX - Xpoint) / (7 - 1);
        Yscale = (Ypoint - stopY) / (Ylabel.size() - 1);
    }

    public void drawZhexian(List<String> Xlabel, List<String> Ylabel,
                            List<String> data_low, List<String> data_low1, List<String> data_high,
                            List<String> data_high1, int xyortw) {
        this.data_low = data_low;
        this.data_low1 = data_low1;
        this.data_high = data_high;
        this.data_high1 = data_high1;
        this.Xlabel = Xlabel;
        this.Ylabel = Ylabel;
        this.xyortw = xyortw;
        Collections.reverse(Xlabel);
        Collections.reverse(data_low);
        Collections.reverse(data_low1);
        Collections.reverse(data_high);
        Collections.reverse(data_high1);
        isHua = true;
        initList();
        postInvalidate();
    }

    //给list赋值
    public void initList() {
        if (data_low.toString().equals("[]")) {
            data_low.add("");
        }
        if (data_low1.toString().equals("[]")) {
            data_low1.add("");
        }
        if (data_high.toString().equals("[]")) {
            data_high.add("");
        }
        if (data_high1.toString().equals("[]")) {
            data_high1.add("");
        }
        if (Xlabel.toString().equals("[]")) {
            Xlabel.add("");
        }
        if (Ylabel.toString().equals("[]")) {
            Ylabel.add("");
        }

    }

    public void drawZhexian(List<String> Xlabel, List<String> Ylabel,
                            List<String> data_low, List<String> data_low1, int xyortw) {
        this.data_low = data_low;
        this.data_low1 = data_low1;
        this.Xlabel = Xlabel;
        this.Ylabel = Ylabel;
        this.xyortw = xyortw;
        Collections.reverse(Xlabel);
        Collections.reverse(data_low);
        Collections.reverse(data_low1);
        isHua = true;
        initList();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Colors.homeBgc);
        p = new Paint();
        p1 = new Paint();
        p2 = new Paint();
        p7 = new Paint();//日期的画笔
        p3 = new Paint();
        p4 = new Paint();
        p5 = new Paint();//非安全地带的画笔
        p6 = new Paint();//安全地带的画笔
        p.setAntiAlias(true);
        p1.setStyle(Paint.Style.STROKE);
        p1.setAntiAlias(true);
        p2.setStyle(Paint.Style.STROKE);
        p7.setStyle(Paint.Style.STROKE);
        p2.setAntiAlias(true);
        p7.setAntiAlias(true);
        p3.setAntiAlias(true);
        p4.setAntiAlias(true);
        p5.setAntiAlias(true);
        p6.setAntiAlias(true);
        // 颜色是十六进制
        p.setColor(0xffc8c8c8);
        p1.setColor(Color.GREEN);
        p2.setColor(Colors.textColor);
        p7.setColor(Colors.textColor);
        p3.setColor(0xff8b8ee1);
//        if (Build.MODEL.contains("HUAWEI")&&!Build.MODEL.contains("TAG")){
//            p2.setTextSize(30);
//        }else {
//            p2.setTextSize(23);
//        }
        p2.setTextSize(DensityUtils.sp2px(context, 12));

        p7.setTextSize(25);
        p5.setColor(0xffFFF0F0);//安全地带的画笔
        p6.setColor(0xffF0FFF0);//安全地带的画笔
        init();
        drawArea(canvas, p5, p6);
        drawXLine(canvas, p);
        drawYLine(canvas, p4);
        try {
            drawTable(canvas, p1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (isHua) {
            drawData(canvas, p);
        }

    }

    //画安全地带的矩形
    private void drawArea(Canvas canvas, Paint p5, Paint p6) {
        canvas.drawRect(Xpoint, Ypoint - 2 * Yscale, stopX, Ypoint, p5);
        canvas.drawRect(Xpoint, Ypoint - 6 * Yscale, stopX, Ypoint - 2 * Yscale, p6);
        canvas.drawRect(Xpoint, stopY, stopX, Ypoint - 6 * Yscale, p5);
    }

    // 画Y轴颜色条
    private void drawYLine(Canvas canvas, Paint p4) {
        for (int i = 0; i < Ylabel.size() - 1; i++) {
            int startY = Ypoint - i * Yscale;
            if (i == 0) {
                p4.setColor(0xff6367a4);
            } else if (i == 1) {
                p4.setColor(0xff9bd741);

            } else if (i == 2) {
                p4.setColor(0xff1bc7ed);

            } else if (i == 3) {
                p4.setColor(0xfffce273);

            } else if (i == 4) {
                p4.setColor(0xfff8ac32);

            } else if (i == 5) {
                p4.setColor(0xfff48131);
            } else if (i == 6) {
                p4.setColor(0xffee604c);

            } else if (i == 7) {
                p4.setColor(0xfffe0000);

            }

            canvas.drawRect(Xpoint - 15, Ypoint - (i + 1) * Yscale, Xpoint,
                    startY, p4);

        }

    }

    // 画横坐标
    private void drawXLine(Canvas canvas, Paint p) {
        canvas.drawLine(Xpoint, Ypoint, stopX, Ypoint, p);
    }

    // 画表格
    private void drawTable(Canvas canvas, Paint p1) throws Exception {
        // 横向线
        for (int i = 1; (Ypoint - i * Yscale) >= stopY; i++) {
            int startY = Ypoint - i * Yscale;
            p1.setColor(0xffc8c8c8);
            canvas.drawLine(Xpoint, startY, stopX, startY, p1);
            // 画纵轴的数
            p.setColor(Color.BLACK);
            p.setTextSize(20);
            for (String str : Ylabel) {
                if (str.length() <= 2) {
                    changdu = 0;
                } else if (str.length() == 3) {
                    changdu = 1;
                } else if (str.length() == 4) {
                    changdu = 2;
                } else if (str.length() == 5) {
                    changdu = 3;
                }

            }
            if (changdu == 0) {
                canvas.drawText(Ylabel.get(i), Xpoint - 50, startY + 5, p);
            } else if (changdu == 1) {
                canvas.drawText(Ylabel.get(i), Xpoint - 60, startY + 5, p);
            } else if (changdu == 2) {
                canvas.drawText(Ylabel.get(i), Xpoint - 70, startY + 5, p);
            } else if (changdu == 3) {
                canvas.drawText(Ylabel.get(i), Xpoint - 80, startY + 5, p);
            }

        }

        // 画纵线
        for (int i = 1; Xpoint + i * Xscale <= stopX; i++) {
            if (i == 7) {
                break;
            }
            int startX = Xpoint + i * Xscale;
            p.setColor(0xffc8c8c8);
            if (i == 3) {
                canvas.drawLine(startX, Ypoint, startX, stopY + 2, p);
            } else if (i == 6) {
                canvas.drawLine(startX, Ypoint, startX, stopY + 2, p);
            }
        }
        for (int i = 0; i < Xlabel.size(); i++) {
            int startX = Xpoint + i * Xscale;
            if (i == 7) {
                break;
            }
            // 画横轴的数
            canvas.drawText(Xlabel.get(i), startX - 30, Ypoint + 28, p7);
        }

        canvas.drawText(Xlabel.get(0), Xpoint - 30, Ypoint + 28, p7);


    }

    private void drawData(Canvas canvas, Paint p) {
        p.setColor(0xff009ffa);
        for (int i = 1; i < data_low.size(); i++) {
            int startX = Xpoint + i * Xscale;
            if (i == 7) {
                break;
            }
            if (xyortw == 0) {
                canvas.drawLine(Xpoint + (i - 1) * Xscale,
                        calY(data_low.get(i - 1)), startX, calY(data_low.get(i)),
                        p);
                canvas.drawLine(Xpoint + (i - 1) * Xscale,
                        calY(data_high.get(i - 1)), startX,
                        calY(data_high.get(i)), p3);
            } else if (xyortw == 1) {
                canvas.drawLine(Xpoint + (i - 1) * Xscale,
                        calY(data_low.get(i - 1)), startX, calY(data_low.get(i)),
                        p);
            }


        }

        for (int i = 0; i < data_low.size(); i++) {
            if (i == 7) {
                break;
            }
            if (xyortw == 0) {
                //如果集合有一个值并且是""就不画
                if (data_low.size() == 1 && data_low.get(0).equals("")) {

                } else {
                    canvas.drawCircle(Xpoint + i * Xscale, calY(data_low.get(i)), 6, p);
                    canvas.drawCircle(Xpoint + i * Xscale, calY(data_high.get(i)), 6,
                            p3);
                }

                if (i == 0) {
                    if (data_low1.get(i).equals("0")) {
                        canvas.drawText(0 + "", Xpoint + i * Xscale,
                                calY(data_low.get(i)) - 8, p2);
                    } else {
                        canvas.drawText(data_low1.get(i) + "", Xpoint + i * Xscale,
                                calY(data_low.get(i)) - 8, p2);
                    }

                } else {
                    if (data_low1.get(i).equals("0")) {
                        canvas.drawText(0 + "", Xpoint + i * Xscale - 35,
                                calY(data_low.get(i)) - 8, p2);
                    } else {

                        if (i!=6){
                            canvas.drawText(data_low1.get(i) + "", Xpoint + i * Xscale - 13,
                                    calY(data_low.get(i)) - 8, p2);
                        }else {
                            canvas.drawText(data_low1.get(i) + "", Xpoint + i * Xscale - 42,
                                    calY(data_low.get(i)) - 8, p2);
                        }
                    }

                }
                if (i == 0) {
                    if (data_high1.get(i).equals("0")) {
                        canvas.drawText(0 + "", Xpoint + i * Xscale,
                                calY(data_high.get(i)) - 8, p2);
                    } else {
                        canvas.drawText(data_high1.get(i) + "", Xpoint + i * Xscale,
                                calY(data_high.get(i)) - 8, p2);
                    }
                } else {
                    if (data_high1.get(i).equals("0")) {
                        canvas.drawText(0 + "", Xpoint + i * Xscale - 35,
                                calY(data_high.get(i)) - 8, p2);
                    } else {
                        if (i!=6){
                            canvas.drawText(data_high1.get(i) + "", Xpoint + i * Xscale - 11,
                                    calY(data_high.get(i)) - 8, p2);
                        }else {
                            canvas.drawText(data_high1.get(i) + "", Xpoint + i * Xscale - 42,
                                    calY(data_high.get(i)) - 8, p2);
                        }
                    }


                }
            } else if (xyortw == 1) {
                //如果集合有一个值并且是""就不画
                if (data_low.size() == 1 && data_low.get(0).equals("")) {

                } else {
                    canvas.drawCircle(Xpoint + i * Xscale, calY(data_low.get(i)), 6, p);
                }

                if (i == 0) {
                    if (data_low1.get(i).equals("0")) {
                        canvas.drawText(0 + "", Xpoint + i * Xscale,
                                calY(data_low.get(i)) - 8, p2);//Y坐标减的数越多 越往上
                    } else {
//                        if (Integer.parseInt(data_low1.get(i))>=150){//顶端的数缩小点
//                            p2.setTextSize(DensityUtils.sp2px(context, 10));
//                            p2.setColor(0xffff0000);
//                            canvas.drawText( "MAX", Xpoint + i * Xscale,
//                                    calY(data_low.get(i))-8 , p2);
//                        }else{

//                            p2.setTextSize(DensityUtils.sp2px(context, 12));
//                            p2.setColor(Colors.textColor);
                        canvas.drawText(data_low1.get(i) + "", Xpoint + i * Xscale,
                                calY(data_low.get(i)) - 8, p2);
//                        }

                    }

                } else {
                    if (data_low1.get(i).equals("0")) {
                        canvas.drawText(0 + "", Xpoint + i * Xscale - 18,
                                calY(data_low.get(i)) - 8, p2);
                    } else {
//                        if (Integer.parseInt(data_low1.get(i))>=150){//顶端的数缩小点
//                            p2.setTextSize(DensityUtils.sp2px(context, 9));
//                            p2.setColor(0xffff0000);
//                            canvas.drawText( "MAX", Xpoint + i * Xscale-23,
//                                    calY(data_low.get(i))-8 , p2);
//                        }else{
//                            p2.setTextSize(DensityUtils.sp2px(context, 12));
//                            p2.setColor(Colors.textColor);
                        if (i != 6) {
                            canvas.drawText(data_low1.get(i) + "", Xpoint + i * Xscale - 13,
                                    calY(data_low.get(i)) - 8, p2);
                        } else {
                            canvas.drawText(data_low1.get(i) + "", Xpoint + i * Xscale - 42,
                                    calY(data_low.get(i)) - 8, p2);
                        }
//                        }


                    }

                }


            }


        }

    }

    /**
     * 换算y值
     *
     * @param
     * @return
     */
    private float calY(String str) {
        Pattern p = Pattern.compile("[0-9.]*");
        Matcher m = p.matcher(str);
        if (!m.matches()) {
            Log.e("aaaa", "输入的不是数字");
            str = "0";
        }
        if (str.equals("")) {
            str = "0";
        }
        double y = Double.valueOf(str);
        double y0 = 0.0;
        double y1 = 0.0;
        try {
            y0 = Double.parseDouble(Ylabel.get(0));
            y1 = Double.parseDouble(Ylabel.get(1));

        } catch (Exception e) {
            return 0;
        }
        try {
            double yMax = Double.parseDouble(Ylabel.get(Ylabel.size() - 1));
            if (y > yMax) {
                return (float) (Ypoint - ((yMax - y0) * Yscale / (y1 - y0)));
            }
            return (float) (Ypoint - ((y - y0) * Yscale / (y1 - y0)));
        } catch (Exception e) {
            return 0;
        }
    }
}
