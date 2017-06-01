package com.zhixinyisheng.user.ui.IM.ui.friend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.ActivityRequestCode;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 设置备注
 * Created by 焕焕 on 2017/1/13.
 */
public class SetRemarkAty extends BaseAty {
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
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.et_editremark)
    EditText etEditremark;
    @Bind(R.id.renzheng_rl_number)
    RelativeLayout renzhengRlNumber;

    @Override
    public int getLayoutId() {
        return R.layout.aty_editfriendremark;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        cjsTvt.setText(R.string.shezhiremark);
        titleBtn.setText(R.string.finish);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
//        DemoHelper.getInstance().init(this);
        DemoHelper.getInstance().asyncFetchContactsFromServer(null);
        showToast(getString(R.string.shezhichenggong));
        Intent mIntent = new Intent();
        mIntent.putExtra("remark", etEditremark.getText().toString().trim());
        this.setResult(ActivityRequestCode.SET_REMARK, mIntent);
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
                if (etEditremark.getText().toString().trim().equals("")){
                    showToast(getString(R.string.beizhubunengweikong));
                    return;
                }
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(IMUrl.class).editFriendRemark(UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(),
                        UserManager.getUserInfo().getUserId(),
                        userId,etEditremark.getText().toString().trim()), 0);
                break;
        }
    }

}
