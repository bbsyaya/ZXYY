package com.zhixinyisheng.user.ui.IM.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.application.EaseConstant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.easeui.adapter.EaseConversationAdapter;
import com.hyphenate.easeui.domain.MyEmconversation;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.ChatRecord;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.IM.db.InviteMessgeDao;
import com.zhixinyisheng.user.ui.mine.RenZhengAty;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorChatActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.WanShanXinXiAty;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ConversationListFragment extends EaseConversationListFragment {

    private TextView errorText;
    MyEmconversation myEmconversation;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myEmconversation = conversationListView.getItem(position);
                /**
                 * 点击聊天消息
                 */
                if (myEmconversation.getEmConversation() != null) {
                    EMConversation conversation = myEmconversation.getEmConversation();
                    String username = conversation.getUserName();
                    if (username.equals(EMClient.getInstance().getCurrentUser()))
                        Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                    else {
                        //TODO 判断是否为医生
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(IMUrl.class).chatInfo(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                username), HttpIdentifier.CHAT_INFO);


//                        // start chat acitivity
//                        Intent intent = new Intent(getActivity(), ChatActivity.class);
//                        if (conversation.isGroup()) {
//                            if (conversation.getType() == EMConversationType.ChatRoom) {
//                                // it's group chat
//                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
//                            } else {
//                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
//                            }
//
//                        }
//                        conversationListView.refresh();
//                        // it's single chat
//                        intent.putExtra(Constant.EXTRA_USER_ID, username);
//                        startActivity(intent);
                    }
                } else {//系统消息
                    int type = myEmconversation.getListBean().getType();
                    if (type == 1) {//好友申请


                    } else if (type == 2) {//sos呼救

                    } else if (type == 3) {//好友消息推送

                    } else if (type == 4) {//重要通知
                        if (myEmconversation.getListBean().getFlag() == 0) {//更新消息为已读
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(IMUrl.class).readSet(UserManager.getUserInfo().getPhone(),
                                    UserManager.getUserInfo().getSecret(),
                                    myEmconversation.getListBean().getSysMessageId()), HttpIdentifier.READ_NOTICE_MESSAGE);

                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("a", myEmconversation.getListBean());
                            startActivity(SysMessageDetialAty.class, bundle);
                        }
                    } else if (type == 10) {//对方同意查看健康数据
                        new MaterialDialog(getActivity())
                                .setMDNoTitle(true)
                                .setMDMessage(myEmconversation.getListBean().getContent())
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        if (myEmconversation.getListBean().getFlag() == 0) {//更新消息为已读
                                            showLoadingDialog(null);
                                            doHttp(RetrofitUtils.createApi(IMUrl.class).readSet(UserManager.getUserInfo().getPhone(),
                                                    UserManager.getUserInfo().getSecret(),
                                                    myEmconversation.getListBean().getSysMessageId()), 4);
                                        } else {
                                            BaseApplication.userId = myEmconversation.getListBean().getFromUserId();
                                            startActivity(FriendsDetialAty.class, null);
                                        }
                                    }
                                })
                                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {

                                    }
                                })
                                .show();

                    } else if (type == 9) {//订单消息
                        new MaterialDialog(getActivity())
                                .setMDNoTitle(true)
                                .setMDMessage(myEmconversation.getListBean().getContent())
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        if (myEmconversation.getListBean().getFlag() == 0) {//更新消息为已读
                                            showLoadingDialog(null);
                                            doHttp(RetrofitUtils.createApi(IMUrl.class).readSet(UserManager.getUserInfo().getPhone(),
                                                    UserManager.getUserInfo().getSecret(),
                                                    myEmconversation.getListBean().getSysMessageId()), HttpIdentifier.READ_ORDER_MESSAGE);

                                        } else {
                                            BaseApplication.userId = myEmconversation.getListBean().getFromUserId();
                                            startActivity(FriendsDetialAty.class, null);
                                        }
                                    }
                                })
                                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {

                                    }
                                })
                                .show();
                    } else if (type == 6 || type == 13) {//异常数据
                        new MaterialDialog(getActivity())
                                .setMDNoTitle(true)
                                .setMDMessage(myEmconversation.getListBean().getContent())
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        if (myEmconversation.getListBean().getFlag() == 0) {//更新消息为已读
                                            showLoadingDialog(null);
                                            doHttp(RetrofitUtils.createApi(IMUrl.class).readSet(UserManager.getUserInfo().getPhone(),
                                                    UserManager.getUserInfo().getSecret(),
                                                    myEmconversation.getListBean().getSysMessageId()), 1);

                                        } else {
                                            if (myEmconversation.getListBean().getPayedUserID() == 1) {
                                                Intent intent = new Intent(getActivity(), DoctorPageActivity.class);
                                                intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, myEmconversation.getListBean().getFromUserId());
                                                startActivity(intent);
                                                activityAnimation();
                                            } else {
                                                BaseApplication.userId = myEmconversation.getListBean().getFromUserId();
                                                startActivity(FriendsDetialAty.class, null);
                                            }

                                        }


                                    }
                                })
                                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {

                                    }
                                })
                                .show();
                    } else if (type == 7) {//医师认证成功
                        new MaterialDialog(getActivity())
                                .setMDNoTitle(true)
                                .setMDMessage(myEmconversation.getListBean().getContent())
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        if (myEmconversation.getListBean().getFlag() == 0) {//更新消息为已读
                                            showLoadingDialog(null);
                                            doHttp(RetrofitUtils.createApi(IMUrl.class).readSet(UserManager.getUserInfo().getPhone(),
                                                    UserManager.getUserInfo().getSecret(),
                                                    myEmconversation.getListBean().getSysMessageId()), 2);
                                        } else {
                                            startActivity(WanShanXinXiAty.class, null);
                                        }
                                    }
                                })
                                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {

                                    }
                                })
                                .show();
                    } else if (type == 8) {//医师认证失败
                        new MaterialDialog(getActivity())
                                .setMDNoTitle(true)
                                .setMDMessage(myEmconversation.getListBean().getContent())
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        if (myEmconversation.getListBean().getFlag() == 0) {//更新消息为已读
                                            showLoadingDialog(null);
                                            doHttp(RetrofitUtils.createApi(IMUrl.class).readSet(UserManager.getUserInfo().getPhone(),
                                                    UserManager.getUserInfo().getSecret(),
                                                    myEmconversation.getListBean().getSysMessageId()), 3);
                                        } else {
                                            startActivity(RenZhengAty.class, null);
                                        }
                                    }
                                })
                                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {

                                    }
                                })
                                .show();
                    }

                }

            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示
//        conversationListView.setConversationListHelper(new EaseConversationListHelper() {
//            @Override
//            public String onSetItemSecondaryText(EMMessage lastMessage) {
//                if (lastMessage.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
//                    String sendNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_SENDER_NAME, "");
//                    String receiveNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_NAME, "");
//                    String msg;
//                    if (lastMessage.direct() == EMMessage.Direct.RECEIVE) {
//                        msg = String.format(getResources().getString(R.string.msg_someone_take_red_packet), receiveNick);
//                    } else {
//                        if (sendNick.equals(receiveNick)) {
//                            msg = getResources().getString(R.string.msg_take_red_packet);
//                        } else {
//                            msg = String.format(getResources().getString(R.string.msg_take_someone_red_packet), sendNick);
//                        }
//                    }
//                    return msg;
//                }
//                return null;
//            }
//        });
        //end of red packet code
    }

    @Override
    public void onResume() {
        super.onResume();
        conversationListView.refresh();
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    int pos;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {//删除回话和消息
            deleteMessage = true;
        }
//        else if (item.getItemId() == R.id.delete_conversation) {//只删除回话
//            deleteMessage = false;
//        }
        pos = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
        myEmconversation = conversationListView.getItem(pos);

        if (myEmconversation == null) {
            return true;
        }

        if (myEmconversation.getEmConversation() != null) {
            BaseFrameAty.showLog("这是", "回话");
            EMConversation tobeDeleteCons = myEmconversation.getEmConversation();
            if (tobeDeleteCons.getType() == EMConversationType.GroupChat) {
                EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.getUserName());
            }
            try {
                // delete conversation
                EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), deleteMessage);
                InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
                inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
            } catch (Exception e) {
                e.printStackTrace();
            }
//            myEmconversation=null;
//            conversationListView.refresh();
            EaseConversationAdapter adapter = (EaseConversationAdapter) conversationListView.getAdapter();
            adapter.getConversationList().remove(pos);
            adapter.notifyDataSetChanged();
            // update unread count
//            ((MainActivity) getActivity()).updateUnreadLabel();
        } else {

            //TODO  删除
            BaseFrameAty.showLog("这是", "系统消息");
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(IMUrl.class).deletesysMessage(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(),
                    myEmconversation.getListBean().getSysMessageId()), 0);


        }


        return true;
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        if (what == 0) {
            showToast(getString(R.string.shanchuchenggong));
            EaseConversationAdapter adapter = (EaseConversationAdapter) conversationListView.getAdapter();
            adapter.getConversationList().remove(pos);
            adapter.notifyDataSetChanged();

            if (adapter.getConversationList().size() == 0) {
                wuxiaoxi.setVisibility(View.VISIBLE);
                youxiaoxi.setVisibility(View.GONE);
            } else {
                wuxiaoxi.setVisibility(View.GONE);
                youxiaoxi.setVisibility(View.VISIBLE);
            }

//            refresh();
        } else if (what == 1) {
            //刷新界面
//            Intent intent = new Intent(Constant.MYACTION_ACCEPTFRI);
//            getActivity().sendBroadcast(intent);
            myEmconversation.getListBean().setFlag(1);
            conversationListView.refresh();

            if (myEmconversation.getListBean().getPayedUserID() == 1) {
                Intent intent = new Intent(getActivity(), DoctorPageActivity.class);
                intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, myEmconversation.getListBean().getFromUserId());
                startActivity(intent);
                activityAnimation();
            } else {
                BaseApplication.userId = myEmconversation.getListBean().getFromUserId();
                startActivity(FriendsDetialAty.class, null);
            }
        } else if (what == 4) {
            myEmconversation.getListBean().setFlag(1);
            conversationListView.refresh();
            BaseApplication.userId = myEmconversation.getListBean().getFromUserId();
            startActivity(FriendsDetialAty.class, null);
        } else if (what == 2) {
            myEmconversation.getListBean().setFlag(1);
            conversationListView.refresh();
            startActivity(WanShanXinXiAty.class, null);
        } else if (what == 3) {
            myEmconversation.getListBean().setFlag(1);
            conversationListView.refresh();
            startActivity(RenZhengAty.class, null);
        } else if (what == HttpIdentifier.READ_ORDER_MESSAGE) {
            myEmconversation.getListBean().setFlag(1);
            conversationListView.refresh();
            BaseApplication.userId = myEmconversation.getListBean().getFromUserId();
            startActivity(FriendsDetialAty.class, null);
        } else if (what == HttpIdentifier.READ_NOTICE_MESSAGE) {
            myEmconversation.getListBean().setFlag(1);
            conversationListView.refresh();

            Bundle bundle = new Bundle();
            bundle.putSerializable("a", myEmconversation.getListBean());
            startActivity(SysMessageDetialAty.class, bundle);
        } else if (what == HttpIdentifier.CHAT_INFO) {
            Logger.e("chat info", result);
            ChatRecord chatRecord = JSON.parseObject(result, ChatRecord.class);
            if (chatRecord.getDb().getPayedUserID() == 1) {//医生
                Intent intent = new Intent(getActivity(), DoctorChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, myEmconversation.getEmConversation().getUserName());
                startActivity(intent);
                activityAnimation();
            } else if (chatRecord.getDb().getPayedUserID() == -2 || chatRecord.getDb().getPayedUserID() == -1) {//不是好友关系，需加好友
                new MaterialDialog(getActivity())
                        .setMDNoTitle(true)
                        .setMDMessage(getString(R.string.nimenbushihaoyouguanxi))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                            }
                        })
                        .show();
            } else {
                // start chat acitivity
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                conversationListView.refresh();
                // it's single chat
                intent.putExtra(Constant.EXTRA_USER_ID, myEmconversation.getEmConversation().getUserName());
                startActivity(intent);
            }


        }

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        showToast(getString(R.string.shanchushibai));
    }
}
