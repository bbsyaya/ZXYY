package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.FriendsEntity;
import com.hyphenate.easeui.domain.MyEmconversation;
import com.hyphenate.easeui.domain.SysMessageEntity;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseIMUrl;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.LanguageUtil;
import com.hyphenate.easeui.widget.EaseConversationList.EaseConversationListHelper;
import com.hyphenate.util.DateUtils;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * conversation list adapter
 */
public class EaseConversationAdapter extends ArrayAdapter<MyEmconversation> {
    private static final String TAG = "ChatAllHistoryAdapter";
    private List<MyEmconversation> conversationList;
    private ConversationFilter conversationFilter;
    private boolean notiyfyByFilter;

    protected int primaryColor;
    protected int secondaryColor;
    protected int timeColor;
    protected int primarySize;
    protected int secondarySize;
    protected float timeSize;

    Context context;

    /**
     * 请求网络对话弹窗
     */
    private LoadingDialog mLoadingDialog;

    /**
     * 接受添加好友和同意好友查看数据
     * 0            1
     */
    int no = 0;

    public EaseConversationAdapter(Context context, int resource,
                                   List<MyEmconversation> objects) {
        super(context, resource, objects);
        this.context = context;
        conversationList = objects;
    }

    @Override
    public int getCount() {
        return conversationList.size();
    }

    @Override
    public MyEmconversation getItem(int arg0) {
        if (arg0 < conversationList.size()) {
            return conversationList.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_chat_history, parent, false);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.unreadLabel = (TextView) convertView.findViewById(R.id.unread_msg_number);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.msgState = convertView.findViewById(R.id.msg_state);
            holder.list_itease_layout = (RelativeLayout) convertView.findViewById(R.id.list_itease_layout);
//            holder.motioned = (TextView) convertView.findViewById(R.id.mentioned);
            holder.tv_ty = (TextView) convertView.findViewById(R.id.chathis_tv_ty);
            holder.rl_xhd = (RelativeLayout) convertView.findViewById(R.id.cjs_rl_xhd);

            convertView.setTag(holder);
        }
        holder.list_itease_layout.setBackgroundResource(R.drawable.ease_mm_listitem);

        // get conversation
        MyEmconversation myEmconversation = getItem(position);
        /**
         * 区分系统和聊天
         */
        for (MyEmconversation emconversation : conversationList) {
            Log.d(TAG, "emconversation.getTime():" + emconversation.getTime());
        }
        if (myEmconversation != null && myEmconversation.getEmConversation() != null) {
            EMConversation conversation = myEmconversation.getEmConversation();

            // 获取userName 或是群组id
            String username = conversation.getUserName();

            if (conversation.getType() == EMConversationType.GroupChat) {
                String groupId = conversation.getUserName();
                if (EaseAtMessageHelper.get().hasAtMeMsg(groupId)) {
//                    holder.motioned.setVisibility(View.VISIBLE);
                } else {
//                    holder.motioned.setVisibility(View.GONE);
                }
                //群组消息  展示群头像
//                holder.avatar.setImageResource(R.drawable.ease_group_icon);
                EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                holder.name.setText(group != null ? group.getGroupName() : username);
            } else if (conversation.getType() == EMConversationType.ChatRoom) {
//                holder.avatar.setImageResource(R.drawable.ease_group_icon);
                EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(username);
                holder.name.setText(room != null && !TextUtils.isEmpty(room.getName()) ? room.getName() : username);
//                holder.motioned.setVisibility(View.GONE);
            } else {
                EaseUserUtils.setUserAvatar(getContext(), username, holder.avatar);
                EaseUserUtils.setUserNick(username, holder.name);
//                holder.motioned.setVisibility(View.GONE);
            }

            if (conversation.getUnreadMsgCount() > 0) {
                // 展示user未读消息条数
                holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
                holder.rl_xhd.setVisibility(View.VISIBLE);
                holder.unreadLabel.setVisibility(View.VISIBLE);
            } else {
                holder.rl_xhd.setVisibility(View.INVISIBLE);
                holder.unreadLabel.setVisibility(View.INVISIBLE);
            }

            if (conversation.getAllMsgCount() != 0) {
                // 展示目录或者最后消息
                EMMessage lastMessage = conversation.getLastMessage();
                String content = null;
                if (cvsListHelper != null) {
                    content = cvsListHelper.onSetItemSecondaryText(lastMessage);
                }
                holder.message.setText(EaseSmileUtils.getSmiledText(getContext(), EaseCommonUtils.getMessageDigest(lastMessage, (this.getContext()))),
                        BufferType.SPANNABLE);
                if (content != null) {
                    holder.message.setText(content);
                }
                holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
                if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                    holder.msgState.setVisibility(View.VISIBLE);
                } else {
                    holder.msgState.setVisibility(View.GONE);
                }
            }

            //设置所有权
//            holder.name.setTextColor(0xff5e5e5e);
            holder.name.setSingleLine(true);
            holder.name.setMaxEms(8);//最大8个字符
//            holder.message.setTextColor(0xff5e5e5e);
//            holder.time.setTextColor(0xffc4c4c4);
//            if (primarySize != 0)
//                holder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
//            if (secondarySize != 0)
//                holder.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondarySize);
//            if (timeSize != 0)
//                holder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeSize);

            /**
             * 防止同一按钮错乱
             */
            holder.tv_ty.setVisibility(View.GONE);
            holder.time.setVisibility(View.VISIBLE);


        } else {//系统消息
            holder.unreadLabel.setVisibility(View.INVISIBLE);
            final SysMessageEntity.ListBean listBean = myEmconversation.getListBean();
            if (listBean.getType()==1){
                holder.name.setText(R.string.xinpengyou);
            }else if (listBean.getType()==2){
                holder.name.setText(R.string.soshujiu);
            }else if (listBean.getType()==3){
                holder.name.setText(R.string.haoyouxiaoxituisong);
            }else if (listBean.getType()==4){
                holder.name.setText(R.string.zhongyaotongzhi);
            }else if (listBean.getType()==5||listBean.getType()==10){
                holder.name.setText(R.string.chakanjiankangshujushenqing);
            }else if (listBean.getType()==6){
                holder.name.setText(R.string.yichangshuju);
            }else if (listBean.getType()==7){
                holder.name.setText(R.string.yishirenzhengshenhejieguo);
            }else if (listBean.getType()==8){
                holder.name.setText(R.string.yishirenzhengshenhejieguo);
            }else if (listBean.getType()==9){
                holder.name.setText(R.string.yonghudingdan);
            }else if (listBean.getType()==13){
                holder.name.setText(R.string.xinpengyou);
            }else{
                holder.name.setText(R.string.zhixinyisheng);
            }
            if (listBean.getType() == 7||listBean.getType()==8) {//医师认证
                Glide.with(getContext()).load(R.drawable.ic_rztz_img)
                        .error(R.drawable.ic_rztz_img)//加载错误图
                        .placeholder(R.drawable.ic_rztz_img)
                        .bitmapTransform(new CropCircleTransformation(getContext()))//裁剪圆形
                        .into(holder.avatar);
            } else {
                Glide.with(getContext()).load(listBean.getHeadUrl())
                        .error(R.drawable.ic_launcher2)//加载错误图
                        .placeholder(R.drawable.ic_launcher2)
                        .bitmapTransform(new CropCircleTransformation(getContext()))//裁剪圆形
                        .into(holder.avatar);
            }
//            holder.motioned.setVisibility(View.GONE);
            if (listBean.getType() == 1 || listBean.getType() == 5) {//好友申请
//                Logger.e("state haoyou",listBean.getState()+"###");
                if (listBean.getState() == 1) {//已接受
                    holder.rl_xhd.setVisibility(View.INVISIBLE);
                    holder.unreadLabel.setVisibility(View.INVISIBLE);
                    holder.tv_ty.setText(R.string.yitongyi);
                    holder.tv_ty.setBackgroundColor(0xffffffff);
                    holder.tv_ty.setTextColor(0xff999999);
                    holder.tv_ty.setEnabled(false);
                } else if (listBean.getState() == 3) {
                    holder.rl_xhd.setVisibility(View.INVISIBLE);
                    holder.unreadLabel.setVisibility(View.INVISIBLE);
                    holder.tv_ty.setText(R.string.yizuofei);
                    holder.tv_ty.setBackgroundColor(0xffffffff);
                    holder.tv_ty.setTextColor(0xff999999);
                    holder.tv_ty.setEnabled(false);
                } else {
                    holder.rl_xhd.setVisibility(View.VISIBLE);
                    holder.unreadLabel.setVisibility(View.INVISIBLE);
                    holder.tv_ty.setText(R.string.tongyi);
                    holder.tv_ty.setBackgroundResource(R.drawable.shape_main_color);
                    holder.tv_ty.setTextColor(0xffffffff);
                    holder.tv_ty.setEnabled(true);
                }

                holder.tv_ty.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.GONE);

                holder.tv_ty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (listBean.getType() == 1) {
                            //接受好友请求
                            showLoadingDialog(null);
                            accpetFrineds(listBean);//TODO 以后再优化
                        } else if (listBean.getType() == 5) {
                            //接受好友申请查看健康数据
                            showLoadingDialog(null);
                            agreeLook(listBean);//TODO 以后再优化

                        }

                    }
                });


            } else if (listBean.getType() == 6) {
                if (listBean.getFlag() == 0) {
                    holder.rl_xhd.setVisibility(View.VISIBLE);
                } else {
                    holder.rl_xhd.setVisibility(View.INVISIBLE);
                }


                holder.unreadLabel.setVisibility(View.INVISIBLE);
                holder.tv_ty.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
            } else {
                if (listBean.getType() == 4) {
                    holder.message.setText(listBean.getTitle());
                } else if (listBean.getType() == 7) {
                    holder.name.setText(R.string.yishengrenzhengshenhejieguo);
                }
                holder.tv_ty.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
                //已读未读
                if (listBean.getFlag() == 0) {
                    holder.rl_xhd.setVisibility(View.VISIBLE);
                } else {
                    holder.rl_xhd.setVisibility(View.INVISIBLE);
                }
            }
            if (listBean.getType()==1||listBean.getType()==5){
                holder.message.setMaxEms(13);
            }else{
                holder.message.setMaxEms(22);
            }

            if (listBean.getType() != 4) {
                //内容
                holder.message.setText(listBean.getContent());
            }
            long time = zhuanHuaTime(listBean.getCreateTime());
            //时间
            holder.time.setText(DateUtils.getTimestampString(new Date(time)));
            //状态
            holder.msgState.setVisibility(View.GONE);

            //set property
//            holder.name.setTextColor(0xff5e5e5e);
//            holder.message.setTextColor(0xff5e5e5e);
//            holder.time.setTextColor(0xffc4c4c4);
//            if (primarySize != 0)
//                holder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
//            if (secondarySize != 0)
//                holder.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondarySize);
//            if (timeSize != 0)
//                holder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeSize);

        }


        return convertView;
    }

    public long zhuanHuaTime(String time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time));
            return c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            BaseFrameAty.showLog("时间转化 EaseContactListFragment", e.toString());
        }
        return 0;
    }

    public void shiPeiLt() {

    }

    public List<MyEmconversation> getConversationList() {
        return conversationList;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!notiyfyByFilter) {
//            conversationList.clear();
//            conversationList.addAll(conversationList);
            notiyfyByFilter = false;
        }
    }

    @Override
    public Filter getFilter() {
        if (conversationFilter == null) {
            conversationFilter = new ConversationFilter(conversationList);
        }
        return conversationFilter;
    }


    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setTimeColor(int timeColor) {
        this.timeColor = timeColor;
    }

    public void setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
    }

    public void setSecondarySize(int secondarySize) {
        this.secondarySize = secondarySize;
    }

    public void setTimeSize(float timeSize) {
        this.timeSize = timeSize;
    }


    private class ConversationFilter extends Filter {
        List<MyEmconversation> mOriginalValues = null;

        public ConversationFilter(List<MyEmconversation> mList) {
            mOriginalValues = mList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<MyEmconversation>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = conversationList;
                results.count = conversationList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mOriginalValues.size();
                final ArrayList<EMConversation> newValues = new ArrayList<EMConversation>();

                for (int i = 0; i < count; i++) {
                    final MyEmconversation myEmconversation = mOriginalValues.get(i);
                    if (myEmconversation.getEmConversation() != null) {
                        final EMConversation value = myEmconversation.getEmConversation();
                        String username = value.getUserName();

                        EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                        if (group != null) {
                            username = group.getGroupName();
                        } else {
                            EaseUser user = EaseUserUtils.getUserInfo(username);
                            // TODO: not support Nick anymore
//                        if(user != null && user.getNick() != null)
//                            username = user.getNick();
                        }

                        // First match against the whole ,non-splitted value
                        if (username.startsWith(prefixString)) {
                            newValues.add(value);
                        } else {
                            final String[] words = username.split(" ");
                            final int wordCount = words.length;

                            // Start at index 0, in case valueText starts with space(s)
                            for (String word : words) {
                                if (word.startsWith(prefixString)) {
                                    newValues.add(value);
                                    break;
                                }
                            }
                        }
                    } else {
                        BaseFrameAty.showLog("EaseContactListFragment 搜索", "空");
                    }

                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            conversationList.clear();
            if (results.values != null) {
                conversationList.addAll((List<MyEmconversation>) results.values);
            }
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    private EaseConversationListHelper cvsListHelper;

    public void setCvsListHelper(EaseConversationListHelper cvsListHelper) {
        this.cvsListHelper = cvsListHelper;
    }

    private static class ViewHolder {
        /**
         * 聊天对象
         */
        TextView name;
        /**
         * 未读条数
         */
        TextView unreadLabel;
        /**
         * 最后信息
         */
        TextView message;
        /**
         * 最后信息时间
         */
        TextView time;
        /**
         * 头像
         */
        ImageView avatar;
        /**
         * 最后信息状态(已读/未读)
         */
        View msgState;
        /**
         * layout
         */
        RelativeLayout list_itease_layout;
//        TextView motioned;
        //同意
        TextView tv_ty;

        RelativeLayout rl_xhd;
    }

    /**
     * 接受好友申请
     */
    public void accpetFrineds(final SysMessageEntity.ListBean listBean) {

//        Logger.e("aaaa", BaseApplication.phone + "@" + BaseApplication.secret + "@" + BaseApplication.userId + "@" + listBean.getFromUserId() + "@" + listBean.getSysMessageId());

        RetrofitUtils.createApi(EaseIMUrl.class)
                .accpetFrineds(BaseApplication.phone, BaseApplication.secret,
                        BaseApplication.userId, listBean.getFromUserId(), listBean.getSysMessageId(),LanguageUtil.judgeLanguage(context))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dismissLoadingDialog();
                        Logger.e("接口地址", call.request().url().toString());
                        JSONObject object;
                        try {
                            String result = response.body().string();
                            Logger.e("接口返回结果", result);

                            object = JSONObject.parseObject(result);
                            if (object.getString("result").equals("0000")) {

                                BaseFrameAty.showToast("同意添加好友成功");

                                /**
                                 * 重新获取此好友的所有信息 更新本地数据库
                                 */
                                no = 0;
                                getMyFriends(listBean.getFromUserId());

                                //刷新界面
                                Intent intent = new Intent(Constant.MYACTION_ACCEPTFRI);
                                context.sendBroadcast(intent);


                            } else {
                                BaseFrameAty.showToast(object.getString("retMessage"));
                            }
                        } catch (Exception e) {
                            Logger.e("接口数据异常", e.toString());
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dismissLoadingDialog();
                        Logger.e("联网onFailure", t.getMessage() + "  @  " + call.request().url().toString());
//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });
    }


    /**
     * 获取所有好友
     */
    public void getMyFriends(final String userid) {
        RetrofitUtils.createApi(EaseIMUrl.class)
                .getMyFriends(BaseApplication.phone, BaseApplication.secret, BaseApplication.userId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Logger.e("接口地址", call.request().url().toString());
                        try {
                            String result = response.body().string();
                            Logger.e("接口返回结果", result);

                            getEaseuser(result, userid);

                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Logger.e("联网onFailure", t.getMessage() + "  @  " + call.request().url().toString());

//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });
    }

    /**
     * 转化为easeuer
     *
     * @param result
     */
    private void getEaseuser(String result, String userid) {
        EaseUser eu = null;


        FriendsEntity fe = JSON.parseObject(result, FriendsEntity.class);


        //转化为easuser
        List<FriendsEntity.ListBean> list = fe.getList();
        Log.e("好友个数", list.size() + "");
        for (FriendsEntity.ListBean mf : list) {
            if (mf.getToUserId().equals(userid)) {
                eu = zhuanhuaEaseuser(mf);
            }
        }

        for (FriendsEntity.CareListBean clb : fe.getCareList()) {
            if (clb.getToUserId().equals(userid)) {
                eu = zhuanhuaEaseuser(clb);
            }
        }


        BaseFrameAty.showLog("aaaaaaaa", eu.getDatas());

        Intent intent = new Intent(Constant.MYBROADCASTACTION_MAINATY_TYJHY);
        Bundle bundle = new Bundle();
        bundle.putString("id", eu.getUsername());
        bundle.putString("name", eu.getNick());
        bundle.putString("ava", eu.getAvatar());
        bundle.putString("sos", eu.getSos());
        bundle.putString("ck", eu.getDatas());
        bundle.putString("no", eu.getNo());
        bundle.putInt("agreeFlag", eu.getAgreeFlag());
        /**
         * 区分同意好友添加和同意查看健康数据(回到mainactivity更新数据库)
         */
        bundle.putInt("qf", no);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);


    }


    private EaseUser zhuanhuaEaseuser(FriendsEntity.CareListBean mf) {

        String username = "", nick = "", avatar = "";
        int agreeFlag = -1;
        if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
            BaseFrameAty.showToast(context.getString(R.string.haoyoumeiyouid));
        } else {
            username = mf.getToUserId();
        }

        if (mf.getFrinedsRemark().equals("") || TextUtils.isEmpty(mf.getFrinedsRemark())) {//好友备注为空

            if (TextUtils.isEmpty(mf.getUsername()) || mf.getUsername().equals("") || mf.getUsername().equals("匿名")) {//好友名字为空
                if (TextUtils.isEmpty(mf.getPhone()) || mf.getPhone().equals("")) {//好友电话为空
                    BaseFrameAty.showToast(context.getString(R.string.haoyoumeiyoudianhua));
                } else {
                    nick = mf.getPhone();
                }
            } else {
                nick = mf.getUsername();
            }
        } else {
            nick = mf.getFrinedsRemark();
        }

        avatar = mf.getHeadUrl();
        agreeFlag = mf.getAgreeFlag();

        EaseUser user = new EaseUser(username);
        user.setSos(mf.getSos() + "");
        user.setDatas(mf.getDatas() + "");
        user.setNo("1");
        user.setNick(nick);
        user.setAvatar(avatar);
        user.setAgreeFlag(agreeFlag);
        return user;


    }


    private EaseUser zhuanhuaEaseuser(FriendsEntity.ListBean mf) {

        String username = "", nick = "", avatar = "";
        int agreeFlag = -1;
        if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
            BaseFrameAty.showToast(context.getString(R.string.haoyoumeiyouid));
        } else {
            username = mf.getToUserId();
        }

        if (mf.getFrinedsRemark().equals("") || TextUtils.isEmpty(mf.getFrinedsRemark())) {//好友备注为空

            if (TextUtils.isEmpty(mf.getUsername()) || mf.getUsername().equals("") || mf.getUsername().equals("匿名")) {//好友名字为空
                if (TextUtils.isEmpty(mf.getPhone()) || mf.getPhone().equals("")) {//好友电话为空
                    BaseFrameAty.showToast(context.getString(R.string.haoyoumeiyoudianhua));
                } else {
                    nick = mf.getPhone();
                }
            } else {
                nick = mf.getUsername();
            }
        } else {
            nick = mf.getFrinedsRemark();
        }

        avatar = mf.getHeadUrl();
        agreeFlag = mf.getAgreeFlag();
        EaseUser user = new EaseUser(username);
        user.setSos(mf.getSos() + "");
        user.setDatas(mf.getDatas() + "");
        user.setNo("0");
        user.setNick(nick);
        user.setAvatar(avatar);
        user.setAgreeFlag(agreeFlag);
        return user;


    }

    /**
     * 接受好友申请查看健康数据
     */
    public void agreeLook(final SysMessageEntity.ListBean listBean) {

        Logger.e("aaaa", BaseApplication.phone + "@" + BaseApplication.secret + "@" + BaseApplication.userId + "@" + listBean.getFromUserId() + "@" + listBean.getSysMessageId());

        RetrofitUtils.createApi(EaseIMUrl.class)
                .agreeLook(BaseApplication.phone, BaseApplication.secret,
                        BaseApplication.userId, listBean.getFromUserId(), listBean.getSysMessageId(), LanguageUtil.judgeLanguage(context))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dismissLoadingDialog();
                        Logger.e("接口地址", call.request().url().toString());
                        try {
                            String result = response.body().string();
                            Logger.e("接口返回结果", result);

                            JSONObject object = JSONObject.parseObject(result);
                            if (object.getString("result").equals("0000")) {

                                BaseFrameAty.showToast(context.getString(R.string.tongyichenggong));

                                /**
                                 * 重新获取次好友的所有信息 更新本地数据库
                                 */
                                no = 1;
                                getMyFriends(listBean.getFromUserId());


                                //刷新界面
                                Intent intent = new Intent(Constant.MYACTION_ACCEPTFRI);
                                context.sendBroadcast(intent);

                            } else {
                                BaseFrameAty.showToast(context.getString(R.string.tongyishibai));
                            }
                        } catch (Exception e) {
                            Logger.e("接口数据异常", e.toString());
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dismissLoadingDialog();
                        Logger.e("联网onFailure", t.getMessage() + "  @  " + call.request().url().toString());
//                        BaseFrameAty.showToast("服务器错误!");
                    }
                });
    }


    /**
     * 显示进度对话条
     */
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(context);
//            mLoadingDialog.setContentView(R.layout.frame_loading_dialog);
        }
        mLoadingDialog.showLoadingDialog(message);
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

}

