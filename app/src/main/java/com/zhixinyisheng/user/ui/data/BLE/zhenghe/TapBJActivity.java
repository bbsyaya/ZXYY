package com.zhixinyisheng.user.ui.data.BLE.zhenghe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by 焕焕 on 2016/11/11.
 */
public class TapBJActivity extends BaseAty {
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.main_unread_msg_number)
    TextView mainUnreadMsgNumber;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.tapbj_et)
    EditText et;
    @Bind(R.id.tapbj_tv)
    TextView tv_js;
    @Bind(R.id.tapbj_ll)
    LinearLayout tapbjLl;
    @Bind(R.id.tapbj_rl)
    RelativeLayout tapbjRl;

    @Override
    public int getLayoutId() {
        return R.layout.aty_tapbj;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.hujiaochangyongyu);
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.finish);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (!bundle.getString("content").equals("")) {
            et.setText(bundle.getString("content"));
        }

        int length = et.getText().length();
        tv_js.setText((30 - length) + "");
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                int length = et.getText().length();
                tv_js.setText((30-length)+"");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
    }



    @OnClick({R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                hideSoftKeyboard();
                finish();
                break;
            case R.id.title_btn:
                hideSoftKeyboard();
                if (et.getText().toString().equals("")||et.getText().toString().trim().equals("")) {
                    showToast(getString(R.string.qingshuruneirong));
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("bjxx", et.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }
}
