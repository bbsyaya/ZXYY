package com.zhixinyisheng.user.ui.IM;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMessage.Status;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseEmojiconInfoProvider;
import com.hyphenate.easeui.controller.EaseUI.EaseSettingsProvider;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.model.EaseNotifier.EaseNotificationInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.AppConfig;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.interfaces.OnFriendsLoadedListener;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.db.InviteMessgeDao;
import com.zhixinyisheng.user.ui.IM.db.UserDao;
import com.zhixinyisheng.user.ui.IM.domain.EmojiconExampleGroupData;
import com.zhixinyisheng.user.ui.IM.domain.FriendsEntity;
import com.zhixinyisheng.user.ui.IM.domain.InviteMessage;
import com.zhixinyisheng.user.ui.IM.domain.RobotUser;
import com.zhixinyisheng.user.ui.IM.parse.UserProfileManager;
import com.zhixinyisheng.user.ui.IM.receiver.CallReceiver;
import com.zhixinyisheng.user.ui.IM.ui.ChatActivity;
import com.zhixinyisheng.user.ui.IM.ui.ConversationAty;
import com.zhixinyisheng.user.ui.IM.utils.PreferenceManager;
import com.zhixinyisheng.user.ui.MainAty;
import com.zhixinyisheng.user.ui.login.LoginAty;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoHelper {
    /**
     * 同步监听
     */
    public interface DataSyncListener {
        /**
         * 同步完成监听(成功 / 失败)
         *
         * @param success true：data sync successful，false: failed to sync data
         */
        void onSyncComplete(boolean success);
    }

    protected static final String TAG = "DemoHelper";

    private EaseUI easeUI;

    // 消息事件监听
    protected EMMessageListener messageListener = null;

    private Map<String, EaseUser> contactList;

    private Map<String, RobotUser> robotList;

    private UserProfileManager userProManager;

    private static DemoHelper instance = null;

    private DemoModel demoModel = null;

    /**
     * 同步群组 listener
     */
    private List<DataSyncListener> syncGroupsListeners;
    /**
     * 同步联系人状态 listener
     */
    private List<DataSyncListener> syncContactsListeners;
    /**
     * 同步黑名单状态 listener
     */
    private List<DataSyncListener> syncBlackListListeners;

    // 是否同步群组
    private boolean isSyncingGroupsWithServer = false;
    // 是否同步联系人
    private boolean isSyncingContactsWithServer = false;
    // 是否同步黑名单
    private boolean isSyncingBlackListWithServer = false;

    private boolean isGroupsSyncedWithServer = false; // 是否读取群组
    private boolean isContactsSyncedWithServer = false;//是否读取好友
    private boolean isBlackListSyncedWithServer = false; // 是否读取黑名单


    public boolean isVoiceCalling;
    public boolean isVideoCalling;

    private String username;

    private Context appContext;

    private CallReceiver callReceiver;

    private InviteMessgeDao inviteMessgeDao;
    private UserDao userDao;

    private LocalBroadcastManager broadcastManager;

    private boolean isGroupAndContactListenerRegisted;


    private DemoHelper() {

    }

    public synchronized static DemoHelper getInstance() {
        if (instance == null) {
            instance = new DemoHelper();
        }
        return instance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(Context context) {
        demoModel = new DemoModel(context);
        EMOptions options = initChatOptions();
        //use default options if options is null
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;

            //debug mode, 最好设置为false 如果是要发布正式的APP.
            EMClient.getInstance().setDebugMode(false);
            //获取easeUI对象
            easeUI = EaseUI.getInstance();
            //设置用户 profile 和头像
            setEaseUIProviders();
            //初始化收藏管理
            PreferenceManager.init(context);
            //initialize profile manager
            getUserProfileManager().init(context);

            EMClient.getInstance().callManager().getVideoCallHelper().setAdaptiveVideoFlag(getModel().isAdaptiveVideoEncode());

            setGlobalListeners();
            broadcastManager = LocalBroadcastManager.getInstance(appContext);
            initDbDao();
        }
    }


    // 设置聊天选项
    private EMOptions initChatOptions() {
        Log.d(TAG, "init HuanXin Options");
        EMOptions options = new EMOptions();
        // 设置是否允许自动添加
        options.setAcceptInvitationAlways(true);
        // 设置是否需要接受回执
        options.setRequireAck(true);
        // 设置是否发送回执
        options.setRequireDeliveryAck(false);

        //you need apply & set your own id if you want to use google cloud messaging.
        options.setGCMNumber("324169311137");
        //you need apply & set your own id if you want to use Mi push notification
        options.setMipushConfig("2882303761517426801", "5381742660801");
        //you need apply & set your own id if you want to use Huawei push notification
        options.setHuaweiPushAppId("10492024");

        //set custom servers, commonly used in private deployment
        if (demoModel.isCustomServerEnable() && demoModel.getRestServer() != null && demoModel.getIMServer() != null) {
            options.setRestServer(demoModel.getRestServer());
            options.setIMServer(demoModel.getIMServer());
            if (demoModel.getIMServer().contains(":")) {
                options.setIMServer(demoModel.getIMServer().split(":")[0]);
                options.setImPort(Integer.valueOf(demoModel.getIMServer().split(":")[1]));
            }
        }

        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());

        return options;
    }

    // set profile provider if you want easeUI to 处理 头像 and 昵称
    protected void setEaseUIProviders() {
        // set profile provider if you want easeUI to 处理 头像 and 昵称
        easeUI.setUserProfileProvider(new EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });

        //set 选项
        easeUI.setSettingsProvider(new EaseSettingsProvider() {

            // 是否语音打开
            @Override
            public boolean isSpeakerOpened() {
                // 语音
                return demoModel.getSettingMsgSpeaker();
            }

            // 是否允许震动
            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                // 是否消息震动允许
                return demoModel.getSettingMsgVibrate();
            }

            // 是否有声音
            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                // 是否消息声音
                return demoModel.getSettingMsgSound();
            }

            // 是否允许消息通知
            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                // 是否消息通知允许
                if (message == null) {
                    return demoModel.getSettingMsgNotification();
                }
                if (!demoModel.getSettingMsgNotification()) {
                    return false;
                } else {
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // get user or group id which was blocked to show message notifications
                    if (message.getChatType() == ChatType.Chat) {
                        chatUsename = message.getFrom();
                        notNotifyIds = demoModel.getDisabledIds();
                    } else {
                        chatUsename = message.getTo();
                        notNotifyIds = demoModel.getDisabledGroups();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });

        //设置表情 provider
        easeUI.setEmojiconInfoProvider(new EaseEmojiconInfoProvider() {
            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
                for (EaseEmojicon emojicon : data.getEmojiconList()) {
                    if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
                        return emojicon;
                    }
                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                return null;
            }
        });

        //设置notification选项,如果选择默认, 则不设置
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotificationInfoProvider() {
            @Override
            public String getTitle(EMMessage message) {
                //可以在此处更新环信title
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //可以在此处更新icon
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // 用于在notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if (message.getType() == Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
//                        return String.format(appContext.getString(R.string.at_your_in_group), user.getNick());
                    }
                    return user.getNick() + ": " + ticker;
                } else {
                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
//                        return String.format(appContext.getString(R.string.at_your_in_group), message.getFrom());
                    }
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                // 当用户点击通知时  设置所展示的activity
                Intent intent = new Intent(appContext, ChatActivity.class);
                // open calling activity if there is call
                if (isVideoCalling) {
//                    intent = new Intent(appContext, VideoCallActivity.class);
                } else if (isVoiceCalling) {
//                    intent = new Intent(appContext, VoiceCallActivity.class);
                } else {
                    ChatType chatType = message.getChatType();
                    if (chatType == ChatType.Chat) { // 单一聊天信息 ? ? ?
                        intent.putExtra("userId", message.getFrom());
                        intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
                    } else { // group chat message
                        // message.getTo() 是group id
                        intent.putExtra("userId", message.getTo());
                        if (chatType == ChatType.GroupChat) {
                            intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                        } else {
                            intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
                        }

                    }
                }
                return intent;
            }
        });
    }

    EMConnectionListener connectionListener;

    /**
     * 设置长连接
     */
    protected void setGlobalListeners() {
        syncGroupsListeners = new ArrayList<DataSyncListener>();
        syncContactsListeners = new ArrayList<DataSyncListener>();
        syncBlackListListeners = new ArrayList<DataSyncListener>();

        isGroupsSyncedWithServer = demoModel.isGroupsSynced();
        isContactsSyncedWithServer = demoModel.isContactSynced();
        isBlackListSyncedWithServer = demoModel.isBacklistSynced();

        //创建长连接 listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
//                if (error == EMError.USER_REMOVED) {
//                    onCurrentAccountRemoved();
//                } else
                if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    handler.sendEmptyMessage(0);

                    try {
                        onConnectionConflict();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onConnected() {
                Log.e("环信", "连接成功");
                // 当链接成功时, 告知sdk已经准备好接收事件了
                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
                    EMLog.d(TAG, "group and contact already synced with servre");
                } else {
                    if (!isGroupsSyncedWithServer) {
                        asyncFetchGroupsFromServer(null);
                    }

                    if (!isContactsSyncedWithServer) {
                        asyncFetchContactsFromServer(null);
                    }

                    if (!isBlackListSyncedWithServer) {
                        asyncFetchBlackListFromServer(null);
                    }
                }
            }
        };

        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }

        //绑定拨入接收器
        appContext.registerReceiver(callReceiver, callFilter);
        //绑定链接监听
        EMClient.getInstance().addConnectionListener(connectionListener);
        //绑定群组事件监听
        registerGroupAndContactListener();
        //绑定消息事件监听
        registerMessageListener();

    }

    private void initDbDao() {
        inviteMessgeDao = new InviteMessgeDao(appContext);
        userDao = new UserDao(appContext);
    }

    /**
     * 绑定群组事件监听, 需要在登录是绑定
     */
    public void registerGroupAndContactListener() {
        if (!isGroupAndContactListenerRegisted) {
            EMClient.getInstance().groupManager().addGroupChangeListener(new MyGroupChangeListener());
            EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
            isGroupAndContactListenerRegisted = true;
        }

    }

    /**
     * 群组变更监听
     */
    class MyGroupChangeListener implements EMGroupChangeListener {

        // 用户邀请你加入群组
        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {

            new InviteMessgeDao(appContext).deleteMessage(groupId);

            // 用户邀请你加入群组
            InviteMessage msg = new InviteMessage();
            msg.setFrom(groupId);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(groupName);
            msg.setReason(reason);
            msg.setGroupInviter(inviter);
            Log.d(TAG, "receive invitation to join the group：" + groupName);
            msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION);
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        //用户接受你的邀请
        @Override
        public void onInvitationAccepted(String groupId, String invitee, String reason) {

            new InviteMessgeDao(appContext).deleteMessage(groupId);

            //用户接受你的邀请
            boolean hasGroup = false;
            EMGroup _group = null;
            for (EMGroup group : EMClient.getInstance().groupManager().getAllGroups()) {
                if (group.getGroupId().equals(groupId)) {
                    hasGroup = true;
                    _group = group;
                    break;
                }
            }
            if (!hasGroup)
                return;

            InviteMessage msg = new InviteMessage();
            msg.setFrom(groupId);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(_group == null ? groupId : _group.getGroupName());
            msg.setReason(reason);
            msg.setGroupInviter(invitee);
            Log.d(TAG, invitee + "Accept to join the group：" + _group == null ? groupId : _group.getGroupName());
            msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_ACCEPTED);
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        //用户拒绝你的邀请
        @Override
        public void onInvitationDeclined(String groupId, String invitee, String reason) {

            new InviteMessgeDao(appContext).deleteMessage(groupId);

            //用户拒绝你的邀请
            EMGroup group = null;
            for (EMGroup _group : EMClient.getInstance().groupManager().getAllGroups()) {
                if (_group.getGroupId().equals(groupId)) {
                    group = _group;
                    break;
                }
            }
            if (group == null)
                return;

            InviteMessage msg = new InviteMessage();
            msg.setFrom(groupId);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(group.getGroupName());
            msg.setReason(reason);
            msg.setGroupInviter(invitee);
            Log.d(TAG, invitee + "Declined to join the group：" + group.getGroupName());
            msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED);
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        //用户被移出群组
        @Override
        public void onUserRemoved(String groupId, String groupName) {
            //用户被移出群组
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        // 群组解散
        @Override
        public void onGroupDestroyed(String groupId, String groupName) {
            // 群组解散
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        // 用户申请加入群组
        @Override
        public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {

            // 用户申请加入群组
            InviteMessage msg = new InviteMessage();
            msg.setFrom(applyer);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(groupName);
            msg.setReason(reason);
            Log.d(TAG, applyer + " Apply to join group：" + groupName);
            msg.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        // 加群申请通过
        @Override
        public void onApplicationAccept(String groupId, String groupName, String accepter) {

            String st4 = appContext.getString(R.string.Agreed_to_your_group_chat_application);
            // 加群申请通过
            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(accepter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new EMTextMessageBody(accepter + " " + st4));
            msg.setStatus(Status.SUCCESS);
            // 保存允许信息
            EMClient.getInstance().chatManager().saveMessage(msg);
            // 通知通过消息
            getNotifier().vibrateAndPlayTone(msg);

            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        // 申请被拒
        @Override
        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
            // 申请被拒
        }

        //自动同意加入群组 sdk会先加入这个群组，并通过此回调通知应用
        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            // 获取一个邀请
            String st3 = appContext.getString(R.string.Invite_you_to_join_a_group_chat);
            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(inviter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new EMTextMessageBody(inviter + " " + st3));
            msg.setStatus(EMMessage.Status.SUCCESS);
            // 通过消息形式保存邀请
            EMClient.getInstance().chatManager().saveMessage(msg);
            // 通知 邀请消息
            getNotifier().vibrateAndPlayTone(msg);
            EMLog.d(TAG, "onAutoAcceptInvitationFromGroup groupId:" + groupId);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }
    }

    /***
     * 好友变化listener
     */
    public class MyContactListener implements EMContactListener {

        //增加联系人
        @Override
        public void onContactAdded(String username) {
            // 保存联系人
            Map<String, EaseUser> localUsers = getContactList();
            Map<String, EaseUser> toAddUsers = new HashMap<String, EaseUser>();
            EaseUser user = new EaseUser(username);

            if (!localUsers.containsKey(username)) {
                userDao.saveContact(user);
            }
            toAddUsers.put(username, user);
            localUsers.putAll(toAddUsers);

            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
        }

        // 删除联系人
        @Override
        public void onContactDeleted(String username) {
            Map<String, EaseUser> localUsers = DemoHelper.getInstance().getContactList();
            localUsers.remove(username);
            userDao.deleteContact(username);
            inviteMessgeDao.deleteMessage(username);

            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
        }

        //收到好友邀请
        @Override
        public void onContactInvited(String username, String reason) {
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();

            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getGroupId() == null && inviteMessage.getFrom().equals(username)) {
                    inviteMessgeDao.deleteMessage(username);
                }
            }
            // 以Message保存邀请
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            msg.setReason(reason);
            Log.d(TAG, username + "apply to be your friend,reason: " + reason);
            // 设置邀请状况
            msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
        }

        // 邀请通过
        @Override
        public void onContactAgreed(String username) {
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getFrom().equals(username)) {
                    return;
                }
            }
            // save invitation as message
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            Log.d(TAG, username + "accept your request");
            msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
            notifyNewInviteMessage(msg);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
        }

        // 邀请被拒
        @Override
        public void onContactRefused(String username) {
            // your request was refused
            Log.d(username, username + " 邀请被拒");
        }
    }

    /**
     * 保存并通知邀请消息
     *
     * @param msg
     */
    private void notifyNewInviteMessage(InviteMessage msg) {
        if (inviteMessgeDao == null) {
            inviteMessgeDao = new InviteMessgeDao(appContext);
        }
        inviteMessgeDao.saveMessage(msg);
        //增加未读消息数
        inviteMessgeDao.saveUnreadMessageCount(1);
        // 通知新信息
        getNotifier().vibrateAndPlayTone(null);
    }

    /**
     * 用户异地登录
     */
    protected void onConnectionConflict() throws Exception{
//        Intent intent = new Intent(appContext, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Constant.ACCOUNT_CONFLICT, true);
//        appContext.startActivity(intent);


        /**
         * 退出IM
         */
        imLogout();

        //清除极光推送信息
        JPushInterface.setAlias(appContext, "", null);
        Set<String> set = new HashSet<>();
        JPushInterface.setTags(appContext, set, null);
        Intent sIntent = new Intent(AppConfig.ACTION_LOGOUT);
        appContext.sendBroadcast(sIntent);
        UserManager.setIsLogin(false);
//      UserManager.setUserInfo(new UserInfo());
        AppManger.getInstance().killActivity(MainAty.class);
        AppManger.getInstance().killAllActivity();

        Intent intent = new Intent(appContext, LoginAty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);

    }

    /**
     * 退出IM
     */
    private void imLogout() {
        DemoHelper.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                BaseFrameAty.showLog("IM 退出", "成功");
            }

            @Override
            public void onProgress(int progress, String status) {
                BaseFrameAty.showLog("IM 退出", "进行");
            }

            @Override
            public void onError(int code, String message) {
                BaseFrameAty.showLog("IM 退出", "失败");
            }
        });
    }

    /**
     * account is removed 用户被删除 ???什么鬼
     * --- 应该是注销吧
     */
    protected void onCurrentAccountRemoved() {
        Intent intent = new Intent(appContext, MainAty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.ACCOUNT_REMOVED, true);
        appContext.startActivity(intent);
    }

    //根据ID从数据库中获取好友
    private EaseUser getUserInfo(String username) {

        // 获取EaseUser, 获取user列表从内存中
        // 如果要从服务器中获取 最好写缓存
        EaseUser user = null;
        if (username.equals(EMClient.getInstance().getCurrentUser()))
            return getUserProfileManager().getCurrentUserInfo();

//        user = getContactList().get(username);
        user = DemoDBManager.getInstance().getContactList().get(username);

//        if (user == null && getRobotList() != null) {
//            user = getRobotList().get(username);
//        }

        // 如果用户不在你的联系人列表中, 发送添加邀请 给他
        if (user == null) {
            user = new EaseUser(username);
            user.setNick("单项好友");
            EaseCommonUtils.setUserInitialLetter(user);
        }
        return user;
    }

    /**
     * 长连接监听
     * 如果事件已经被某一个activity处理 不需要再处理一次了
     * activityList.size() <= 0 以为着所有activity都在background 或没有activity在堆内存中
     */
    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            // 接受消息接口，在接受到文本消息，图片，视频，语音，地理位置，文件这些消息体的时候，会通过此接口通知用户。
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    Log.e("demoHelper 收到消息", message.getUserName() + ":" + message.getBody());
                    // 在子线程中, 不要在这更新UI, 通知 notification bar
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().onNewMsg(message);
                    }
                }
            }

            // 区别于{onMessageReceived(List<EMMessage> messages)}, 这个接口只包含命令的消息体，包含命令的消息体通常不对用户展示。
            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    Logger.e("IM  CMD", action);

                    if (action.equals("im_agreelook")) {

                        Intent intent = new Intent(appContext, ConversationAty.class);
                        appContext.startActivity(intent);

                        initNotify();
                        showIntentActivityNotify(message.getStringAttribute("a", "").trim());


                    }


                    // --------医生端（家属端）发来的确认营救
                    if (action.equals("saverLocation")) {
                        String stringAttribute = message.getStringAttribute(
                                "name", null);
                        String DOCTOR_ID = message.getStringAttribute("id",
                                null);
                        String lat = message.getStringAttribute("lat", null);
                        String lon = message.getStringAttribute("lon", null);
                        Intent broadcastIntent = new Intent(
                                "com.zhixinyisheng.user.jpush.MyBroadcastReceiver");
                        broadcastIntent.putExtra("cmd_value", action);
                        broadcastIntent.putExtra("name", stringAttribute);
                        broadcastIntent.putExtra("id", DOCTOR_ID);
                        broadcastIntent.putExtra("lat", lat);
                        broadcastIntent.putExtra("lon", lon);
                        appContext.sendBroadcast(broadcastIntent, null);
                    } else if (action.equals("sosEnd")) {// 患者发来的主动取消请求
                        String stringAttribute = message.getStringAttribute(
                                "name", null);
                        String DOCTOR_ID = message.getStringAttribute("id",
                                null);
                        Intent broadcastIntent = new Intent(
                                "com.zhixinyisheng.user.jpush.MyBroadcastReceiver");
                        broadcastIntent.putExtra("cmd_value", action);
                        broadcastIntent.putExtra("name", stringAttribute);
                        broadcastIntent.putExtra("id", DOCTOR_ID);
                        broadcastIntent.putExtra("lat", "null");
                        broadcastIntent.putExtra("lon", "null");
                        appContext.sendBroadcast(broadcastIntent, null);

                    } else if (action.equals("saverGoBack")) {// 医生端（家属端）主动放弃请求
                        String stringAttribute = message.getStringAttribute(
                                "name", null);
                        String DOCTOR_ID = message.getStringAttribute("id",
                                null);
                        Intent broadcastIntent = new Intent(
                                "com.zhixinyisheng.user.jpush.MyBroadcastReceiver");
                        broadcastIntent.putExtra("cmd_value", action);
                        broadcastIntent.putExtra("name", stringAttribute);
                        broadcastIntent.putExtra("id", DOCTOR_ID);
                        broadcastIntent.putExtra("lat", "null");
                        broadcastIntent.putExtra("lon", "null");
                        appContext.sendBroadcast(broadcastIntent, null);

                    }


                    //red packet code : 处理红包回执透传消息
                    if (!easeUI.hasForegroundActivies()) {
//                        if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                            RedPacketUtil.receiveRedPacketAckMessage(message);
//                            broadcastManager.sendBroadcast(new Intent(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION));
//                        }
                    }
                    // 红包代码结束
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
                }
            }

            // 接受到消息体的已读回执, 消息的接收方已经阅读此消息。
            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            //收到消息体的发送回执，消息体已经成功发送到对方设备
            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            /**
             * 接受消息发生改变的通知，包括消息ID的改变。消息是改变后的消息。
             * @param message 发生改变的消息
             * @param change 包含改变的信息
             */
            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 登出
     *
     * @param unbindDeviceToken 是否打开了设备的设置?
     * @param callback          callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * get instance of EaseNotifier
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    public DemoModel getModel() {
        return (DemoModel) demoModel;
    }

    /**
     * 更新联系人列表
     *
     * @param
     */
    public void setContactList(Map<String, EaseUser> aContactList) {
        if (aContactList == null) {
            if (contactList != null) {
                contactList.clear();
            }
            return;
        }

        contactList = aContactList;
    }

    /**
     * 保存一个联系人
     */
    public void saveContact(EaseUser user) {
        contactList.put(user.getUsername(), user);
        demoModel.saveContact(user);
    }

    /**
     * 获取联系人列表
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            contactList = demoModel.getContactList();
        }

        // return a empty non-null object 避免崩溃
        if (contactList == null) {
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }

    /**
     * 设置当前userName
     *
     * @param username
     */
    public void setCurrentUserName(String username) {
        this.username = username;
        demoModel.setCurrentUserName(username);
    }

    /**
     * 获取当前用户ID
     */
    public String getCurrentUsernName() {
        if (username == null) {
            username = demoModel.getCurrentUsernName();
        }
        return username;
    }

    // 设置机器人列表
    public void setRobotList(Map<String, RobotUser> robotList) {
        this.robotList = robotList;
    }

    // 获取机器人列表
    public Map<String, RobotUser> getRobotList() {
        if (isLoggedIn() && robotList == null) {
            robotList = demoModel.getRobotList();
        }
        return robotList;
    }

    /**
     * 在缓存和数据库中更新用户列表
     *
     * @param
     */
    public void updateContactList(List<EaseUser> contactInfoList) {
        for (EaseUser u : contactInfoList) {
            contactList.put(u.getUsername(), u);
        }
        ArrayList<EaseUser> mList = new ArrayList<EaseUser>();
        mList.addAll(contactList.values());
        demoModel.saveContactList(mList);
    }

    public UserProfileManager getUserProfileManager() {
        if (userProManager == null) {
            userProManager = new UserProfileManager();
        }
        return userProManager;
    }

    protected void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSyncGroupListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncGroupsListeners.contains(listener)) {
            syncGroupsListeners.add(listener);
        }
    }

    public void removeSyncGroupListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncGroupsListeners.contains(listener)) {
            syncGroupsListeners.remove(listener);
        }
    }

    // 添加同步链接监听
    public void addSyncContactListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncContactsListeners.contains(listener)) {
            syncContactsListeners.add(listener);
        }
    }

    // 移出同步链接监听
    public void removeSyncContactListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncContactsListeners.contains(listener)) {
            syncContactsListeners.remove(listener);
        }
    }

    // 添加同步黑名单监听
    public void addSyncBlackListListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncBlackListListeners.contains(listener)) {
            syncBlackListListeners.add(listener);
        }
    }

    // 移出黑名单同步监听
    public void removeSyncBlackListListener(DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncBlackListListeners.contains(listener)) {
            syncBlackListListeners.remove(listener);
        }
    }

    /**
     * 从服务器获取群组列表
     * 该方法会同步状态
     *
     * @throws HyphenateException
     */
    public synchronized void asyncFetchGroupsFromServer(final EMCallBack callback) {
        if (isSyncingGroupsWithServer) {
            return;
        }

        isSyncingGroupsWithServer = true;

        new Thread() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();

                    // 如果在服务器返回前 退出了, 应该马上返回一个结果
                    if (!isLoggedIn()) {
                        isGroupsSyncedWithServer = false;
                        isSyncingGroupsWithServer = false;
                        noitifyGroupSyncListeners(false);
                        return;
                    }

                    demoModel.setGroupsSynced(true);

                    isGroupsSyncedWithServer = true;
                    isSyncingGroupsWithServer = false;

                    //通知群组同步成功
                    noitifyGroupSyncListeners(true);

                    if (callback != null) {
                        callback.onSuccess();
                    }
                } catch (HyphenateException e) {
                    demoModel.setGroupsSynced(false);
                    isGroupsSyncedWithServer = false;
                    isSyncingGroupsWithServer = false;
                    noitifyGroupSyncListeners(false);
                    if (callback != null) {
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }

            }
        }.start();
    }

    //通知群组同步监听
    public void noitifyGroupSyncListeners(boolean success) {
        for (DataSyncListener listener : syncGroupsListeners) {
            listener.onSyncComplete(success);
        }
    }

    EMValueCallBack<List<String>> callback = null;

    // 从服务器同步接收联系人
    public void asyncFetchContactsFromServer(final EMValueCallBack<List<String>> callback) {
        Log.e("存储好友到数据库", "存储好友到数据库");

        this.callback = callback;
//        if (isSyncingContactsWithServer) {
//            return;
//        }
//
//        isSyncingContactsWithServer = true;
        //获取我的好友
        getMyFriends();

    }

    //通知联系人同步监听
    public void notifyContactsSyncListener(boolean success) {
        for (DataSyncListener listener : syncContactsListeners) {
            listener.onSyncComplete(success);
        }
    }

    //从服务器同步黑名单
    public void asyncFetchBlackListFromServer(final EMValueCallBack<List<String>> callback) {

        if (isSyncingBlackListWithServer) {
            return;
        }

        isSyncingBlackListWithServer = true;

        new Thread() {
            @Override
            public void run() {
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getBlackListFromServer();

                    // in case that logout already before server returns, we should return immediately
                    if (!isLoggedIn()) {
                        isBlackListSyncedWithServer = false;
                        isSyncingBlackListWithServer = false;
                        notifyBlackListSyncListener(false);
                        return;
                    }

                    demoModel.setBlacklistSynced(true);

                    isBlackListSyncedWithServer = true;
                    isSyncingBlackListWithServer = false;

                    notifyBlackListSyncListener(true);
                    if (callback != null) {
                        callback.onSuccess(usernames);
                    }
                } catch (HyphenateException e) {
                    demoModel.setBlacklistSynced(false);

                    isBlackListSyncedWithServer = false;
                    isSyncingBlackListWithServer = true;
                    e.printStackTrace();

                    if (callback != null) {
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }

            }
        }.start();
    }

    //通知黑名单同步监听
    public void notifyBlackListSyncListener(boolean success) {
        for (DataSyncListener listener : syncBlackListListeners) {
            listener.onSyncComplete(success);
        }
    }

    public boolean isSyncingGroupsWithServer() {
        return isSyncingGroupsWithServer;
    }

    // 是否从服务器同步联系人
    public boolean isSyncingContactsWithServer() {
        return isSyncingContactsWithServer;
    }

    public boolean isSyncingBlackListWithServer() {
        return isSyncingBlackListWithServer;
    }

    public boolean isGroupsSyncedWithServer() {
        return isGroupsSyncedWithServer;
    }

    //是否同步成功
    public boolean isContactsSyncedWithServer() {
        return isContactsSyncedWithServer;
    }

    public boolean isBlackListSyncedWithServer() {
        return isBlackListSyncedWithServer;
    }

    synchronized void reset() {
        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;

        demoModel.setGroupsSynced(false);
        demoModel.setContactSynced(false);
        demoModel.setBlacklistSynced(false);

        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;

        isGroupAndContactListenerRegisted = false;

        setContactList(null);
        setRobotList(null);
        getUserProfileManager().reset();
        DemoDBManager.getInstance().closeDB();
    }

    //推送
    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    // 弹出
    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }

    /**
     * TODO 获取所有好友
     */
    public void getMyFriends() {
        RetrofitUtils.createApi(IMUrl.class)
                .getMyFriends(UserManager.getUserInfo().getPhone(), UserManager.getUserInfo().getSecret(), UserManager.getUserInfo().getUserId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        BaseFrameAty.showLog("接口地址", call.request().url().toString());
                        try {
                            String result = response.body().string();
                            Logger.e("接口返回结果 好友列表", result);
                            getEaseuser(result);

                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            Logger.e("接口解析错误", exception);
                            if (onFriendsLoadedListener != null) {
                                onFriendsLoadedListener.LoadedError();
                                onFriendsLoadedListener = null;
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        BaseFrameAty.showToast("服务器错误!");
                        if (onFriendsLoadedListener != null) {
                            onFriendsLoadedListener.LoadedError();
                            onFriendsLoadedListener = null;
                        }
                    }
                });
    }

    /**
     * 转化为easeuer
     *
     * @param result
     */
    private void getEaseuser(String result) {
        FriendsEntity fe = JSON.parseObject(result, FriendsEntity.class);

        Map<String, EaseUser> userlist = new HashMap<String, EaseUser>();


        //转化为easuser
        List<FriendsEntity.ListBean> list = fe.getList();
        for (FriendsEntity.ListBean mf : list) {
            String username = "";
            if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
                BaseFrameAty.showToast("好友没有ID");
            } else {
                username = mf.getToUserId();
            }
            userlist.put(mf.getFriendsId(), zhuanhuaEaseuser(mf));//明日要改
        }
        List<FriendsEntity.CareListBean> list1 = fe.getCareList();
        for (FriendsEntity.CareListBean mf : list1) {
            String username = "";
            if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
                BaseFrameAty.showToast("好友没有ID");
            } else {
                username = mf.getToUserId();
            }
            userlist.put(mf.getFriendsId(), zhuanhuaEaseuser(mf));
        }
        List<FriendsEntity.PatientListBean> list_patient = fe.getPatientList();
        if (list_patient != null) {
            for (FriendsEntity.PatientListBean mf : list_patient) {
                if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
                    BaseFrameAty.showToast("好友没有ID");
                } else {
                    username = mf.getToUserId();
                }
                userlist.put(mf.getFriendsId(), zhuanhuaEaseuser(mf));
            }
        }

        List<FriendsEntity.DoctorListBean> list_doctor = fe.getDoctorList();
        if (list_doctor != null) {
            for (FriendsEntity.DoctorListBean mf : list_doctor) {
                if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
                    BaseFrameAty.showToast("好友没有ID");
                } else {
                    username = mf.getToUserId();
                }
                userlist.put(mf.getFriendsId(), zhuanhuaEaseuser(mf));
            }
        }

        xiugaiChushizhi(userlist);


    }

    private EaseUser zhuanhuaEaseuser(FriendsEntity.DoctorListBean mf) {

        String username = "", nick = "", avatar = "";
        int agreeFlag = -1;
        if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
            BaseFrameAty.showToast("好友没有ID");
        } else {
            username = mf.getToUserId();
        }

        if ("".equals(mf.getFrinedsRemark())) {//备注为空
            if ("".equals(mf.getName())) {//姓名为空
                if ("".equals(mf.getUsername())) {//昵称为空
                    nick = mf.getCard() + "";
                } else {
                    nick = mf.getUsername();
                }
            } else {
                nick = mf.getName();
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
        user.setPayedUserID(mf.getPayedUserID());
        user.setCanSosSet(mf.getCanSosSet());
        user.setIsPatient(0);
        user.setIsMyDoctor(1);
        user.setNick(nick);
        user.setAvatar(avatar);
        user.setAgreeFlag(agreeFlag);
        return user;


    }

    private EaseUser zhuanhuaEaseuser(FriendsEntity.PatientListBean mf) {

        String username = "", nick = "", avatar = "";
        int agreeFlag = -1;
        if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
            BaseFrameAty.showToast("好友没有ID");
        } else {
            username = mf.getToUserId();
        }

        if ("".equals(mf.getFrinedsRemark())) {//备注为空
            if ("".equals(mf.getName())) {//姓名为空
                if ("".equals(mf.getUsername())) {//昵称为空
                    nick = mf.getCard() + "";
                } else {
                    nick = mf.getUsername();
                }
            } else {
                nick = mf.getName();
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
        user.setPayedUserID(mf.getPayedUserID());
        user.setCanSosSet(1);
        user.setIsPatient(1);
        user.setIsMyDoctor(0);
        user.setNick(nick);
        user.setAvatar(avatar);
        user.setAgreeFlag(agreeFlag);
        return user;


    }

    private EaseUser zhuanhuaEaseuser(FriendsEntity.CareListBean mf) {

        String username = "", nick = "", avatar = "";
        int agreeFlag = -1;
        if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
            BaseFrameAty.showToast("好友没有ID");
        } else {
            username = mf.getToUserId();
        }

        if ("".equals(mf.getFrinedsRemark())) {//备注为空
            if ("".equals(mf.getName())) {//姓名为空
                if ("".equals(mf.getUsername())) {//昵称为空
                    nick = mf.getCard() + "";
                } else {
                    nick = mf.getUsername();
                }
            } else {
                nick = mf.getName();
            }
        } else {
            nick = mf.getFrinedsRemark();
        }

        avatar = mf.getHeadUrl();
        agreeFlag = mf.getAgreeFlag();

        EaseUser user = new EaseUser(username);
        user.setSos(mf.getSos() + "");
        user.setDatas(mf.getDatas() + "");
        user.setPayedUserID(mf.getPayedUserID());
        if (mf.getCanSosSet() == -1) {
            user.setCanSosSet(1);
        } else {
            user.setCanSosSet(mf.getCanSosSet());
        }
        user.setNo("1");
        user.setIsPatient(0);
        user.setIsMyDoctor(0);
        user.setNick(nick);
        user.setAvatar(avatar);
        user.setAgreeFlag(agreeFlag);
        return user;


    }

    private EaseUser zhuanhuaEaseuser(FriendsEntity.ListBean mf) {

        String username = "", nick = "", avatar = "";
        int agreeFlag = -1;
        if (TextUtils.isEmpty(mf.getToUserId()) || mf.getToUserId().equals("")) {
            BaseFrameAty.showToast("好友没有ID");
        } else {
            username = mf.getToUserId();
        }
        if ("".equals(mf.getFrinedsRemark())) {//备注为空
            if ("".equals(mf.getName())) {//姓名为空
                if ("".equals(mf.getUsername())) {//昵称为空
                    nick = mf.getCard() + "";
                    Logger.e("nick", nick + "@@@");
                } else {
                    nick = mf.getUsername();
                }
            } else {
                nick = mf.getName();
            }
        } else {
            nick = mf.getFrinedsRemark();
        }
//        Logger.e("nick 222",nick+"###@@@");
        avatar = mf.getHeadUrl();
        agreeFlag = mf.getAgreeFlag();
        EaseUser user = new EaseUser(username);
        user.setSos(mf.getSos() + "");
        user.setDatas(mf.getDatas() + "");
        user.setCanSosSet(1);
        user.setPayedUserID(mf.getPayedUserID());
        user.setNo("0");
        user.setIsPatient(0);
        user.setIsMyDoctor(0);
        user.setNick(nick);
        user.setAvatar(avatar);
        user.setAgreeFlag(agreeFlag);
//        Logger.e("nick 22222233",nick+"###@@@");
        return user;


    }

    /**
     * 存储好友,修改初始值
     *
     * @param userlist
     */
    private void xiugaiChushizhi(final Map<String, EaseUser> userlist) {
        //TODO  很重要
//        new Thread() {
//            @Override
//            public void run() {
        try {
            // 在缓存中保存联系人
            getContactList().clear();
            getContactList().putAll(userlist);
            // 在数据库中保存联系人
            UserDao dao = new UserDao(appContext);
            List<EaseUser> users = new ArrayList<EaseUser>(userlist.values());

            //拿到好友ID集合
            List<String> usernames = new ArrayList<>();
            for (EaseUser eu : users) {
                usernames.add(eu.getUsername());
//                        Logger.e("好友123456", eu.getUsername() + "@" + eu.getNick() + "@" + eu.getAvatar());
            }

            dao.saveContactList(users);

            demoModel.setContactSynced(false);
            EMLog.d(TAG, "set contact syn status to true");

            isContactsSyncedWithServer = false;
            isSyncingContactsWithServer = false;

            //通知同步成功
            notifyContactsSyncListener(true);

            updateContactList(users);
            getUserProfileManager().notifyContactInfosSyncListener(true);

            if (callback != null) {
                callback.onSuccess(usernames);
            }
            //好友刷新成功后的回调
            if (onFriendsLoadedListener != null) {
                onFriendsLoadedListener.friendsLoaded();
                onFriendsLoadedListener = null;
            }
        } catch (Exception e) {
            demoModel.setContactSynced(false);
            isContactsSyncedWithServer = false;
            isSyncingContactsWithServer = false;
            notifyContactsSyncListener(false);
            e.printStackTrace();
            BaseFrameAty.showLog("获取好友错误", e.toString());
            if (onFriendsLoadedListener != null) {
                onFriendsLoadedListener.LoadedError();
                onFriendsLoadedListener = null;
            }
//                    if(callback != null){
//                        callback.onError(e.getErrorCode(), e.toString());
//                    }
        }
//            }
//        }.start();


    }

    private OnFriendsLoadedListener onFriendsLoadedListener;

    public void setOnFriendsLoadedListener(OnFriendsLoadedListener onFriendsLoadedListener) {
        this.onFriendsLoadedListener = onFriendsLoadedListener;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                BaseFrameAty.showToast(appContext.getString(R.string.nindezhanghaozaiqitashebeidenglu));
            }

        }
    };


    /**
     * Notification构造器
     */
    NotificationCompat.Builder mBuilder;
    /**
     * Notification的ID
     */
    int notifyId = 100;

    /**
     * Notification管理
     */
    public NotificationManager mNotificationManager;

    /**
     * 初始化通知栏
     */
    private void initNotify() {
        mNotificationManager = (NotificationManager) appContext.getSystemService(appContext.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(appContext);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                // .setNumber(number)//显示数量
                .setTicker("测试通知来啦")// 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_ALL)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);


    }

    /**
     * 显示通知栏点击跳转到指定Activity
     */
    public void showIntentActivityNotify(String content) {
        // Notification.FLAG_ONGOING_EVENT --设置常驻
        // Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
        // notification.flags = Notification.FLAG_AUTO_CANCEL;
        //在通知栏上点击此通知后自动清除此通知
        mBuilder.setAutoCancel(true)
                // 点击后让通知将消失
                .setContentTitle(appContext.getString(R.string.homeTitle)).setContentText(content + appContext.getString(R.string.qingqiuchakannindejiankangshuju))
                .setTicker(appContext.getString(R.string.shouhuanyiduankai));
        // 点击的意图ACTION是跳转到Intent

        Intent resultIntent = new Intent(appContext, ConversationAty.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, 1, new Intent(), flags);
        return pendingIntent;
    }


}
