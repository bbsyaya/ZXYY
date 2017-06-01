package com.and.yzy.frame.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.R;

/**
 * 里面有编辑框的dialog
 * Created by 焕焕 on 2017/1/7.
 */
public class MaterialDialogForEditText extends Dialog implements DialogInterface,
        View.OnClickListener,TextWatcher {

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
    private TextView mButton1;
    /**
     * 确定按钮
     */
    private TextView mButton2;
    /**
     * 用来设置点击确认button之后，dialog是否消失，默认点击就消失
     */
    private boolean mIsClickDismiss = true;



    private LinearLayout mLinearLayoutView;

    private RelativeLayout mRelativeLayoutView;


//    /**
//     * 可以将一个view加到此布局中
//     */
//    private FrameLayout mFrameLayoutCustomView;
    /**
     * 动画持续时间
     */
    private int mDuration = 500;
    /**
     * 点击外边是否消失
     */
    private boolean isCancelable = true;

    public MaterialDialogForEditText(Context context) {

        this(context, R.style.dialog_untran);

    }

    public MaterialDialogForEditText(Context context, int theme) {
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
                R.layout.frame_materialforedittext_layout, null);


        mLinearLayoutView = (LinearLayout) mDialogView
                .findViewById(R.id.parentPanel);
        mRelativeLayoutView = (RelativeLayout) mDialogView
                .findViewById(R.id.main);

        mTitle = (TextView) mDialogView.findViewById(R.id.tv_dialog_title);
        mMessage = (TextView) mDialogView.findViewById(R.id.tv_dialog_message);
        mMessage.addTextChangedListener(this);
        mButton1 = (TextView) mDialogView.findViewById(R.id.btn_dialog_cancle);
        mButton2 = (TextView) mDialogView.findViewById(R.id.btn_dialog_confirm);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessage.setCursorVisible(true);
            }
        });

        // 默认点击dialog外，不让他消失
        setMDOnTouchOutside(false);

        setContentView(mDialogView);

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                mLinearLayoutView.setVisibility(View.VISIBLE);
                if (type == null) {
                    type = Effectstype.RotateBottom;
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
     * @returnMD
     */
    public MaterialDialogForEditText setMDTitle(CharSequence title) {
        toggleView(mTitle, title);
        mTitle.setText(title);

        return this;
    }
    public MaterialDialogForEditText setConfirmText(String text){
        mButton2.setText(text);
        return this;
    }
    public MaterialDialogForEditText setCancelText(String text){
        mButton1.setText(text);
        return this;
    }
    /**
     * 隐藏标题
     *
     * @param
     * @return
     */
    public MaterialDialogForEditText setMDNoTitle(boolean isNoTitle) {

        if (isNoTitle){

            mTitle.setVisibility(View.GONE);

        }else{
            mTitle.setVisibility(View.VISIBLE);

        }


        return this;
    }

    /**
     * 设置点击button之后，dialog是否消失，默认点击就消失
     *
     * @param
     * @return
     */
    public MaterialDialogForEditText setMDClickDismiss(boolean isClickDismiss) {

        this.mIsClickDismiss = isClickDismiss;

        return this;
    }



    /**
     * 设置内容
     *
     * @param
     * @return
     */
    public MaterialDialogForEditText setMDMessage(CharSequence msg) {
        toggleView(mMessage, msg);
        mMessage.setText(msg);

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
    public MaterialDialogForEditText setMDCancelBtnClick(DialogBtnCallBack click) {
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
    public MaterialDialogForEditText setMDConfirmBtnClick(DialogBtnCallBack click) {
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
    public MaterialDialogForEditText setMDDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * 设置动画的类型
     *
     * @param
     * @return
     */
    public MaterialDialogForEditText setMDEffect(Effectstype type) {
        this.type = type;
        return this;
    }








    /**
     * 设置是否在对话框外点击消失
     *
     * @param
     * @return
     */
    public MaterialDialogForEditText setMDOnTouchOutside(boolean cancelable) {
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
        if (mMessage.getText().toString().length()<13){
            mMessage.setGravity(Gravity.CENTER);
        }else{
            mMessage.setGravity(Gravity.LEFT);
        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable edt) {
        String temp = edt.toString();
        int posDot = temp.indexOf(".");
        if (posDot <= 0) return;
        if (temp.length() - posDot - 1 > 2) {
            edt.delete(posDot + 3, posDot + 4);
        }
    }

    public interface DialogBtnCallBack {
        void dialogBtnOnClick(String str);
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_dialog_cancle == v.getId()) {
            if (click_01 != null) {
                click_01.dialogBtnOnClick(null);
            }
            dismiss();

        } else if (R.id.btn_dialog_confirm == v.getId()) {
            if (click_02 != null) {
                click_02.dialogBtnOnClick(mMessage.getText().toString());
            }

            if (mIsClickDismiss) {
                dismiss();
            }

        }

    }

}