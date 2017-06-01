package com.and.yzy.frame.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.R;


/**
 * Created by lee on
 */
public class NiftyDialogBuilder extends Dialog implements DialogInterface,
		View.OnClickListener {

	private Context tmpContext;
	/**
	 * 动画类型
	 */
	private Effectstype type = null;
	/**
	 * dialog布局
	 */
	private View mDialogView;
	/**
	 * button间的那个竖线，当设为一个button的时候要隐藏掉
	 */
	private View fengexian;
	

	/**
	 * title的分割线
	 */
	private View top_fengexian;
	/**
	 * title显示的文字
	 */
	private TextView mTitle;
	/**
	 * 内容区域显示的文字
	 */
	private TextView mMessage;

	/**
	 * 取消按钮
	 */
	private Button mButton1;
	/**
	 * 确定按钮
	 */
	private Button mButton2;
	/**
	 * 用来设置点击确认button之后，dialog是否消失，默认点击就消失
	 */
	private boolean mIsClickDismiss = true;

	/**
	 * 底部按钮显示的容器
	 */
	private LinearLayout linearbtnGroup;

	private LinearLayout mLinearLayoutView;

	private RelativeLayout mRelativeLayoutView;

	/**
	 * 可以将一个view加到此布局中
	 */
	private FrameLayout mFrameLayoutCustomView;

	/**
	 * 动画持续时间
	 */
	private int mDuration = 300;
	/**
	 * 点击外边是否消失
	 */
	private boolean isCancelable = true;

	public NiftyDialogBuilder(Context context) {

		this(context, R.style.dialog_untran);

	}

	public NiftyDialogBuilder(Context context, int theme) {
		super(context, R.style.dialog_untran);
		init(context);
		tmpContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = ViewGroup.LayoutParams.MATCH_PARENT;
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(
				params);

	}

	protected void init(Context context) {

		mDialogView = View.inflate(context,
				R.layout.frame_dialog_layout, null);

		linearbtnGroup = (LinearLayout) mDialogView
				.findViewById(R.id.linerly_dialog_btngroup);
		mLinearLayoutView = (LinearLayout) mDialogView
				.findViewById(R.id.parentPanel);
		mRelativeLayoutView = (RelativeLayout) mDialogView
				.findViewById(R.id.main);

		mTitle = (TextView) mDialogView.findViewById(R.id.tv_dialog_title);
		mMessage = (TextView) mDialogView.findViewById(R.id.tv_dialog_message);
		fengexian = mDialogView.findViewById(R.id.v_dialog_fengexian);
		
		
		

		top_fengexian = mDialogView.findViewById(R.id.v_dialog_top_fenge);

		mFrameLayoutCustomView = (FrameLayout) mDialogView
				.findViewById(R.id.customPanel);

		mButton1 = (Button) mDialogView.findViewById(R.id.btn_dialog_cancle);
		mButton2 = (Button) mDialogView.findViewById(R.id.btn_dialog_confirm);

		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);

		// 默认点击dialog外，不让他消失
		setNd_IsOnTouchOutside(false);

		setContentView(mDialogView);

		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {

				mLinearLayoutView.setVisibility(View.VISIBLE);
				if (type == null) {
					type = Effectstype.Fall;
				}
				start(type);

			}
		});
		mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isCancelable)
					dismiss();
			}
		});
	}

	/**
	 * 设置标题
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_Title(CharSequence title) {
		toggleView(mTitle, title);
		mTitle.setText(title);

		return this;
	}
	/**
	 * 隐藏标题
	 *
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_NoTitle(boolean isNoTitle) {

		if (isNoTitle){

			mTitle.setVisibility(View.GONE);
			top_fengexian.setVisibility(View.GONE);
		}else{
			mTitle.setVisibility(View.VISIBLE);
			top_fengexian.setVisibility(View.VISIBLE);
		}


		return this;
	}

	/**
	 * 设置标题有没有分割线
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_NOFengeView() {
		top_fengexian.setVisibility(View.GONE);
		return this;
	}

	

	/**
	 * 设置标题大小
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_TitleTextSize(int size) {
		mTitle.setTextSize(size);
		return this;
	}

	/**
	 * 设置标题颜色
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_TitleTextColor(int color) {

		mTitle.setTextColor(color);

		return this;
	}

	/**
	 * 设置点击button之后，dialog是否消失，默认点击就消失
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_isClickDismiss(boolean isClickDismiss) {

		this.mIsClickDismiss = isClickDismiss;

		return this;
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public NiftyDialogBuilder setND_Background(Drawable d) {
		mLinearLayoutView.setBackgroundDrawable(d);
		return this;
	}

	/**
	 * 设置内容
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_Message(CharSequence msg) {
		toggleView(mMessage, msg);
		mMessage.setText(msg);

		return this;
	}

	/**
	 * 设置取消button文字的颜色
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_BtnCancelTextColor(int color) {
		mButton1.setTextColor(color);
		return this;
	}
	/**
	 * 设置取消button的颜色
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_BtnCancelTColor(int color) {
		mButton1.setBackgroundColor(color);
		return this;
	}

	/**
	 * 设置确定button文字的颜色
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_BtnComfirmTextColor(int color) {
		mButton2.setTextColor(color);
		return this;
	}
	/**
	 * 设置确定button的颜色
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_BtnComfirmColor(int color) {
		mButton2.setBackgroundColor(color);
		return this;
	}

	/**
	 * 设置确定button文字的颜色
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_BtnComfirmTextColorForRes(int colorId) {
		mButton2.setTextColor(tmpContext.getResources().getColor(colorId));
		return this;
	}

	public NiftyDialogBuilder setND_BtnComfirmText(String str) {
		mButton2.setText(str);
		return this;
	}

	/**
	 * 设置按钮之间分割线的颜色文字的颜色
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_BtnFenGeColor(int color) {
		fengexian.setBackgroundColor(color);
		return this;
	}

	/**
	 * title分割线的颜色
	 * 
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_TitleFenGeColor(int color) {
		top_fengexian.setVisibility(View.VISIBLE);
		top_fengexian.setBackgroundColor(color);

		return this;
	}

	private DialogBtnCallBack click_01;
	private DialogBtnCallBack click_02;

	/**
	 * 设置取消的点击事件
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_CancelBtnClick(DialogBtnCallBack click) {
		if (click != null) {
			this.click_01 = click;
		}
		return this;
	}

	/**
	 * 设置确定按钮的点击事件
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_ConfirmBtnClick(DialogBtnCallBack click) {
		if (click != null) {
			this.click_02 = click;
		}

		return this;
	}

	/**
	 * 设置动画持续时间
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_Duration(int duration) {
		this.mDuration = duration;
		return this;
	}

	/**
	 * 设置动画的类型
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_Effect(Effectstype type) {
		this.type = type;
		return this;
	}

	/**
	 * 设置只有一个按钮(确认按钮)
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_IsOneBtn(boolean isOne) {
		if (isOne) {
			mButton1.setVisibility(Button.GONE);
			fengexian.setVisibility(View.GONE);
		} else {
			mButton1.setVisibility(Button.VISIBLE);
			fengexian.setVisibility(View.VISIBLE);
		}
		return this;
	}

	/**
	 * 设置提示框没有按钮
	 * 
	 * @param isNoBtn
	 * @return
	 */
	public NiftyDialogBuilder setND_NoBtn(boolean isNoBtn) {
		if (isNoBtn) {
			// mButton1.setVisibility(View.GONE);
			// mButton2.setVisibility(View.GONE);
			// fengexian.setVisibility(View.GONE);
			linearbtnGroup.setVisibility(View.GONE);
		}

		return this;
	}

	/**
	 * 在内容区域添加一个view
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_AddCustomView(int resId) {
		View customView = View.inflate(tmpContext, resId, null);
		if (mFrameLayoutCustomView.getChildCount() > 0) {
			mFrameLayoutCustomView.removeAllViews();
		}
		mFrameLayoutCustomView.addView(customView);
		return this;
	}

	/**
	 * 在内容区域添加一个view
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setND_AddCustomView(View view) {
		if (mFrameLayoutCustomView.getChildCount() > 0) {
			mFrameLayoutCustomView.removeAllViews();
		}
		mFrameLayoutCustomView.addView(view);

		return this;
	}

	/**
	 * 设置是否在对话框外点击消失
	 * 
	 * @param
	 * @return
	 */
	public NiftyDialogBuilder setNd_IsOnTouchOutside(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCanceledOnTouchOutside(cancelable);
		return this;
	}

	/**
	 * 判断是否显示view
	 */
	private void toggleView(View view, Object obj) {
		if (obj == null) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void show() {

		if (mTitle.getText().equals(""))
			mTitle.setVisibility(View.GONE);

		super.show();
	}

	/**
	 * 启动动画
	 */
	private void start(Effectstype type) {
		BaseEffects animator = type.getAnimator();
		if (mDuration != -1) {
			animator.setDuration(Math.abs(mDuration));
		}
		animator.start(mRelativeLayoutView);
	}

	@Override
	public void dismiss() {
		super.dismiss();

	}

	public interface DialogBtnCallBack {
		void dialogBtnOnClick();
	}

	@Override
	public void onClick(View v) {
		if (R.id.btn_dialog_cancle == v.getId()) {
			if (click_01 != null) {
				click_01.dialogBtnOnClick();
			}
			dismiss();

		} else if (R.id.btn_dialog_confirm == v.getId()) {
			if (click_02 != null) {
				click_02.dialogBtnOnClick();
			}

			if (mIsClickDismiss) {
				dismiss();
			}

		}

	}

}
