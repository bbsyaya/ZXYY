package com.zhixinyisheng.user.ui.IM.ui;

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
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.interfaces.OnFriendsLoadedListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 修改备注
 * Created by 焕焕 on 2016/11/19.
 */
public class EditFriendRemarkAty extends BaseAty {
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
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.et_editremark)
    EditText etEditremark;
    @Bind(R.id.renzheng_rl_number)
    RelativeLayout renzhengRlNumber;
    String friendsid;

    @Override
    public int getLayoutId() {
        return R.layout.aty_editfriendremark;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        cjsTvt.setText(R.string.xiugaibeizhu);
        titleBtn.setText(R.string.save);
        Bundle bundle = getIntent().getExtras();
        friendsid = bundle.getString("id");
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        showLoadingDialog(null);
        DemoHelper.getInstance().asyncFetchContactsFromServer(null);
        DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
            @Override
            public void friendsLoaded() {
                dismissLoadingDialog();
                setResult(0, null);
                finish();
            }

            @Override
            public void LoadedError() {
                dismissLoadingDialog();
                showToast(getString(R.string.qingjianchawangluolianjie));
            }
        });


//        showLoadingDialog(null);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            dismissLoadingDialog();
//                            Toast.makeText(EditFriendRemarkAty.this, R.string.shezhichenggong, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    setResult(0,null);
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @OnClick({R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_btn:
                doHttp(RetrofitUtils.createApi(IMUrl.class).editFriendRemark(UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(),
                        UserManager.getUserInfo().getUserId(),
                        friendsid, etEditremark.getText().toString().trim()), 0);
                break;
        }
    }
}
