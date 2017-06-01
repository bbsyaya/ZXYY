package com.zhixinyisheng.user.ui.sidebar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.http.LeftUrl;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 更改昵称
 * Created by 焕焕 on 2017/5/1.
 */

public class ChangeNickActivity extends BaseAty {
    @Bind(R.id.title)
    RelativeLayout title;
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
    @Bind(R.id.cjs_rl_xhd)
    RelativeLayout cjsRlXhd;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.title_btn_hosptal)
    Button titleBtnHosptal;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title_line)
    View titleLine;
    @Bind(R.id.et_change_nickname)
    EditText etChangeNickname;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_nick;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        cjsTvt.setText(R.string.genggainicheng);
        titleBtn.setText(R.string.finish);
        etChangeNickname.setText(UserManager.getUserInfo().getUsername());
        etChangeNickname.requestFocus();
        try {
            etChangeNickname.setSelection(UserManager.getUserInfo().getUsername().length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        showToast(getString(R.string.shezhichenggong));
        UserInfo userInfo = UserManager.getUserInfo();
        userInfo.setUsername(etChangeNickname.getText().toString().trim());
        UserManager.setUserInfo(userInfo);
        finish();
    }

    @OnClick({R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                hideSoftKeyboard();
                break;
            case R.id.title_btn:
                if (etChangeNickname.getText().toString().trim().equals("")){
                    showToast(getString(R.string.qingshurunicheng));
                    return;
                }
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(LeftUrl.class).personinformation(phone, secret, phone, null,
                        etChangeNickname.getText().toString().trim(), null, null,
                        null, null,
                        UserManager.getUserInfo().getUserId(), null), 1);
                break;
        }
    }
}
