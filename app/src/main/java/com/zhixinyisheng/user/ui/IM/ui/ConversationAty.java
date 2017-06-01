package com.zhixinyisheng.user.ui.IM.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/10/27  9:40
 * <p/>
 * 类说明: 好友消息类
 */
public class ConversationAty extends BaseAty {
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.title_line)
    View title_line;
    private ConversationListFragment conversationListFragment;

    @Override
    public int getLayoutId() {
        return R.layout.aty_conversation;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //好友列表frg
        conversationListFragment = new ConversationListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("phone", UserManager.getUserInfo().getPhone());
        bundle.putString("code", UserManager.getUserInfo().getSecret());
        bundle.putString("userid", UserManager.getUserInfo().getUserId());

        conversationListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_conversation, conversationListFragment).show(conversationListFragment)
                .commit();

        cjsTvt.setText(R.string.xiaoxi);
        ivBack.setVisibility(View.VISIBLE);

    }




    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        finish();
    }
}
