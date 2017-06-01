package com.zhixinyisheng.user.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
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
 * 步数和心率的折线图
 * 
 * @author USER
 * 
 */
public class MyViewTW extends View {
	// 默认边距
	// 原点坐标
	private int Xpoint = 0, Ypoint = 0, stopX = 0, stopY = 0;
	// X,Y轴的单位长度
	private int Xscale = 0;
	private int Yscale = 0;
	private int numSize;

	// X,Y轴上面的显示文字
	// private String[] Ylabel = { "34", "35", "36", "37", "38", "39", "40",
	// "41",
	// "42" };
	List<String> Xlabel = new ArrayList<String>(),
			Ylabel = new ArrayList<String>();
	// 标题文本
	private List<String> data = new ArrayList<String>(),
			data1 = new ArrayList<String>();
	Paint p, p1, p2, p3,p7;// p3画Y轴的画笔 p1画标的画笔 p画坐标数字 p2画数
	boolean isHua = false;// 设置变量重绘的时候去画折线
	int changdu = 0;
	Context context;

	public MyViewTW(Context context, AttributeSet attrs) {
		super(context, attrs);
		for (int i = 0; i < 10; i++) {
			Ylabel.add("");
		}
		for (int i = 0; i < 7; i++) {
			Xlabel.add("");
		}
		this.context = context;
		numSize = DensityUtils.sp2px(context, 12);

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
							List<String> data, List<String> data1) {
		this.Xlabel = Xlabel;
		this.Ylabel = Ylabel;
		this.data = data;
		this.data1 = data1;
		Collections.reverse(Xlabel);
		Collections.reverse(data1);
		Collections.reverse(data);
		isHua = true;
		initList();
		postInvalidate();
	}
	//给list赋值
	public void initList(){
		if (data.toString().equals("[]")) {
			data.add("");
		}
		if (data1.toString().equals("[]")) {
			data1.add("");
		}
		if (Xlabel.toString().equals("[]")) {
			Xlabel.add("");
		}
		if (Ylabel.toString().equals("[]")) {
			Ylabel.add("");
		}
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Colors.homeBgc);
		p = new Paint();
		p1 = new Paint();
		p2 = new Paint();
		p3 = new Paint();
		p7 = new Paint();//日期的画笔
		p.setAntiAlias(true);
		p.setStyle(Style.FILL);
		p1.setAntiAlias(true);
		p1.setStyle(Style.FILL);
		p2.setAntiAlias(true);
		p2.setStyle(Style.FILL);
		p7.setAntiAlias(true);
		p7.setStyle(Style.FILL);
		p3.setAntiAlias(true);
		p3.setStyle(Style.FILL);
		// 颜色是十六进制
		p.setColor(Color.BLACK);
		p2.setColor(Color.BLACK);
		p2.setTextSize(numSize);
		p7.setColor(Color.BLACK);
		p7.setTextSize(20);
		init();
		drawXLine(canvas, p);
		drawYLine(canvas, p3);
		drawTable(canvas, p1);
		if (isHua) {
			drawData(canvas, p);
		}

	}

	// 画Y轴颜色条
	private void drawYLine(Canvas canvas, Paint p3) {
		for (int i = 0; i < Ylabel.size() - 1; i++) {
			int startY = Ypoint - i * Yscale;
			if (i == 0) {
				p3.setColor(0xff6367a4);
			} else if (i == 1) {
				p3.setColor(0xff9bd741);

			} else if (i == 2) {
				p3.setColor(0xff1bc7ed);

			} else if (i == 3) {
				p3.setColor(0xfffce273);

			} else if (i == 4) {
				p3.setColor(0xfff8ac32);

			} else if (i == 5) {
				p3.setColor(0xfff48131);
			} else if (i == 6) {
				p3.setColor(0xffee604c);

			} else if (i == 7) {
				p3.setColor(0xfffe0000);

			}

			canvas.drawRect(Xpoint - 15, Ypoint - (i + 1) * Yscale, Xpoint,
					startY, p3);

		}

	}

	// 画横坐标
	private void drawXLine(Canvas canvas, Paint p) {
		canvas.drawLine(Xpoint, Ypoint, stopX, Ypoint, p);
	}

	// 画表格
	private void drawTable(Canvas canvas, Paint p1) {
		Log.e("qqqqq", "q1111");
		// 横向线
		for (int i = 1; (Ypoint - i * Yscale) >= stopY; i++) {
			int startY = Ypoint - i * Yscale;
			p1.setColor(0xffc8c8c8);
			canvas.drawLine(Xpoint, startY, stopX, startY, p1);
			// 画纵轴的数
			p.setColor(Color.BLACK);
			p.setTextSize(20);
			for (String str : Ylabel) {
				if (str.length()<=2) {
					changdu = 0;
				}else if (str.length() == 3) {
					changdu = 1;
				}else if (str.length() == 4) {
					changdu = 2;
				}else if (str.length() == 5) {
					changdu = 3;
				}
				
			}
			if (changdu == 0) {
				canvas.drawText(Ylabel.get(i), Xpoint - 50, startY + 5, p);
			}else if (changdu == 1) {
				canvas.drawText(Ylabel.get(i), Xpoint - 60, startY + 5, p);
			}else if (changdu == 2) {
				canvas.drawText(Ylabel.get(i), Xpoint - 70, startY + 5, p);
			}else if (changdu == 3) {
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
				canvas.drawLine(startX, Ypoint, startX, stopY + 5, p);
			} else if (i == 6) {
				canvas.drawLine(startX, Ypoint, startX, stopY + 5, p);
			}
		}
		for (int i = 0; i < Xlabel.size(); i++) {
			int startX = Xpoint + i * Xscale;
			if (i == 7) {
				break;
			}
			// 画横轴的数
			canvas.drawText(Xlabel.get(i), startX - 17, Ypoint + 35, p7);
		}

		canvas.drawText(Xlabel.get(0), Xpoint - 17, Ypoint + 35, p7);

	}

	// 画表格
	private void drawTable1(Canvas canvas, Paint p1) {
		Log.e("qqqqq", "q222222");
		// 横向线
		for (int i = 1; (Ypoint - i * Yscale) >= stopY; i++) {
			int startY = Ypoint - i * Yscale;
			p1.setColor(0xffc8c8c8);
			canvas.drawLine(Xpoint, startY, stopX, startY, p1);
			// 画纵轴的数
			p.setColor(Color.BLACK);
			p.setTextSize(20);
			canvas.drawText(Ylabel.get(i), Xpoint - 80, startY + 5, p);
		}

		// 画纵线
		for (int i = 1; Xpoint + i * Xscale <= stopX; i++) {
			if (i == 7) {
				break;
			}
			int startX = Xpoint + i * Xscale;
			p.setColor(0xffc8c8c8);
			if (i == 3) {
				canvas.drawLine(startX, Ypoint, startX, stopY + 5, p);
			} else if (i == 6) {
				canvas.drawLine(startX, Ypoint, startX, stopY + 5, p);
			}
		}
		for (int i = 0; i < Xlabel.size(); i++) {
			int startX = Xpoint + i * Xscale;
			if (i == 7) {
				break;
			}
			// 画横轴的数
			canvas.drawText(Xlabel.get(i), startX - 17, Ypoint + 35, p7);
		}

		canvas.drawText(Xlabel.get(0), Xpoint - 17, Ypoint + 35, p7);

	}
	
	private void drawData(Canvas canvas, Paint p) {
		p.setColor(0xff00a1f9);
		// 画折线
		for (int i = 1; i < data.size(); i++) {
			int startX = Xpoint + i * Xscale;
			if (i == 7) {
				break;
			}
			canvas.drawLine(Xpoint + (i - 1) * Xscale, calY(data.get(i - 1)),
					startX, calY(data.get(i)), p);

		}
		// 画折线上方的数据
		for (int i = 0; i < data.size(); i++) {
			if (i == 7) {
				break;
			}
			//如果集合有一个值并且是""就不画
			if (data.size()==1&&data.get(0).equals("")) {
				
			}else {
				// 画原点
				canvas.drawCircle(Xpoint + i * Xscale, calY(data.get(i)), 6, p);
			}
			
			// 画数字
			if (i == 0) {
				if (data1.get(i).equals("0")) {
					canvas.drawText(0 + "", Xpoint + i * Xscale,
							calY(data.get(i)) - 10, p2);
				} else if(data1.get(i).equals("")){

				}else {
					canvas.drawText(data1.get(i) + "", Xpoint + i * Xscale,
							calY(data.get(i)) - 10, p2);
				}

			} else {
				if (data1.get(i).equals("0")) {
					canvas.drawText(0 + "", Xpoint + i * Xscale - 17,
							calY(data.get(i)) - 10, p2);
				} else if(data1.get(i).equals("")){

				}else {
					canvas.drawText(data1.get(i) + "",
							Xpoint + i * Xscale - 17, calY(data.get(i)) - 10,
							p2);
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
