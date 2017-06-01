package com.zhixinyisheng.user.ui.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.and.yzy.frame.application.Constant;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseFgt;

import butterknife.Bind;

/**
 * Created by 焕焕 on 2016/10/20.
 */
public class TextFgt extends BaseFgt {
    @Bind(R.id.et_acount)
    EditText etAcount;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.btn_forgetpassword)
    Button btnForgetpassword;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.btn_register)
    Button btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.login_aty;
    }

    @Override
    public void initData() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_NCG_TIME);
        getActivity().registerReceiver(myBroadcast, intentFilter);
    }

    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showToast(textView2.getText().toString());
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myBroadcast != null) {
            getActivity().unregisterReceiver(myBroadcast);
        }
    }
}
