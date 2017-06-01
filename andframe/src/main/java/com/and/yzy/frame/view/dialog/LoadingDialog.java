package com.and.yzy.frame.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.R;

/**
 * 进度对话框
 * Created by 焕焕 on 2016/10/26.
 */
public class LoadingDialog {
    private Dialog dialog;
    private View mView;

    TextView tv_message;
    public LoadingDialog(Context context) {
        dialog = new Dialog(context, R.style.dialog_untran);
        mView= LayoutInflater.from(context).inflate(com.and.yzy.frame.R.layout.frame_loading_dialog, null);
        tv_message= (TextView) mView.findViewById(com.and.yzy.frame.R.id.frame_tv_message);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
    }

//    public LoadingDialog(Context context, int theme) {
//        super(context, theme);
//        loadingView= LayoutInflater.from(context).inflate(R.layout.frame_loading_dialog,null,false);
//        tv_message= (TextView) loadingView.findViewById(R.id.frame_tv_message);
//        setContentView(R.layout.frame_loading_dialog);
//
//    }


    public void showLoadingDialog(String message) {

        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message);

        }

        dialog.show();
    }

    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
    public boolean isShowing(){
        return dialog.isShowing();
    }
}
