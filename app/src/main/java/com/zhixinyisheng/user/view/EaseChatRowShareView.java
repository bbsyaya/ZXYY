package com.zhixinyisheng.user.view;

import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.application.EaseConstant;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.MyDoctorPageActivity;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Yuanyx on 2017/1/12.
 */

public class EaseChatRowShareView extends EaseChatRow {
    private TextView tv1, tv2, tv3;
    private ImageView ivAvatar;
    public String userId, toUserId;

    public EaseChatRowShareView(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.share_receive_item : R.layout.share_sent_item, this);
        // video call

    }

    @Override
    protected void onFindViewById() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        ivAvatar = (ImageView) findViewById(R.id.iv_to_user_id);
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        isShare = true;
        String sction = message.getStringAttribute(EaseConstant.MESSAGE_SECTION, "section");
        String goodAt = message.getStringAttribute(EaseConstant.MESSAGE_GOODAT, "goodat");
        String title = message.getStringAttribute(EaseConstant.MESSAGE_TITLE, "title");
        String doctorName = message.getStringAttribute(EaseConstant.MESSAGE_SHARE_NAME, "doctorName");
        String doctorAvatar = message.getStringAttribute(EaseConstant.MESSAGE_SHARE_HEADER_URL, "doctorAvatar");
        toUserId = message.getStringAttribute(EaseConstant.MESSAGE_SHARE_ID, "");
        String userName = message.getStringAttribute("name", "userName");
        String userAvatar = message.getStringAttribute("logo", "logo");

        tv1.setText("推荐：" + doctorName + "\t大夫");
        tv2.setText(sction + "\t" + title);
        tv3.setText("擅长：" + goodAt);

        Glide.with(context).load(userAvatar)
                .placeholder(com.hyphenate.easeui.R.drawable.ic_launcher2)//占位图
                .error(com.hyphenate.easeui.R.drawable.ic_launcher2)//加载错误图
                .bitmapTransform(new CropCircleTransformation(context))//裁剪圆形
                .into(userAvatarView);

        Glide.with(context).load(doctorAvatar)
                .placeholder(com.hyphenate.easeui.R.drawable.ic_launcher2)//占位图
                .error(com.hyphenate.easeui.R.drawable.ic_launcher2)//加载错误图
                .bitmapTransform(new CropCircleTransformation(context))//裁剪圆形
                .into(ivAvatar);
    }

    @Override
    protected void onBubbleClick() {
        userId = UserManager.getUserInfo().getUserId();
        Intent intent = null;
        if (toUserId.equals(userId)) {
            intent = new Intent(context, MyDoctorPageActivity.class);
        } else {
            intent = new Intent(context, DoctorPageActivity.class);
            intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, toUserId);
        }
        context.startActivity(intent);
    }
}
