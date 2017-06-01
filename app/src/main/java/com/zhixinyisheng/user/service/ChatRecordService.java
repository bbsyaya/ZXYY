package com.zhixinyisheng.user.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.DoctorRequestBody;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yuanyx on 2017/1/17.
 */

public class ChatRecordService extends Service {
    public static final String EXTRA_DOCTOR_ID = "doctorId";
    public static final String EXTRA_INIT_CHAT_SIZE = "extra_init_chat_size";

    private List<EMMessage> messages;
    private int initChatSize;
    public String phone = UserManager.getUserInfo().getPhone();//登录手机号
    public String secret = UserManager.getUserInfo().getSecret();//登录返回秘钥
    public String userId = UserManager.getUserInfo().getUserId();
    private String doctorId;
    private static final String FIELD_CONN = "╞€∫╡";
    private static final String MODEL_CONN = "╠ψ╦ψ╣";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            doctorId = intent.getStringExtra(EXTRA_DOCTOR_ID);
            initChatSize = intent.getIntExtra(EXTRA_INIT_CHAT_SIZE, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EaseUser easeUser = EaseUserUtils.getUserInfo(doctorId);
        handleChatRecord(easeUser.getUsername());

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 处理消息记录
     */
    private void handleChatRecord(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if (conversation != null) {
            //获取此会话的所有消息
            messages = conversation.getAllMessages();
            Log.e("MessageRecord", "allmessages-->" + messages.size());
            //获取上一次聊天的最后一条消息id
            String msgId = UserManager.getMsgId();
            //上传拼接的字段
            String chatContent = "";
            if ("".equals(msgId)) {
                // TODO: 2017/1/17 讲全部的聊天记录上传
                chatContent = handleChatInfo(messages);
            } else {
                // TODO: 2017/1/17 处理消息 上传新发的消息
                //上一次所有的聊天记录
                // 获取startMsgId之前的pagesize条消息，不包括当前msgId的信息
                List<EMMessage> messageList = conversation.loadMoreMsgFromDB(msgId, messages.size());
                Log.e("MessageRecord", "messageList-->" + messageList.size());
                List<EMMessage> newMessageList = getNewMessageRecord(messages, messageList);
                chatContent = handleChatInfo(newMessageList);
            }
            if ("".equals(chatContent)) {
                return;
            }
            doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).uploadingChatRecord(phone, secret, userId, chatContent));

        }
    }

    /**
     * 拼接聊天信息
     *
     * @param messageList
     * @return
     */
    private String handleChatInfo(List<EMMessage> messageList) {
        //from:c5f5197aed78453d84588c5feed2c3bf, to:159d7d91fd7a43dcac63c7f795b72791 body:txt:"咯哦哦"
        String chatContent = "";
        for (int i = 0; i < messageList.size(); i++) {
            EMMessage message = messageList.get(i);
            EMMessageBody messageBody = message.getBody();
            EMMessage.Type type = message.getType();
            String common = "";
            if (type == EMMessage.Type.IMAGE) {
                common = handleUploadingInfo(message, "图片");
            } else if (type == EMMessage.Type.VOICE) {
                common = handleUploadingInfo(message, "语音");
            } else if (type == EMMessage.Type.LOCATION) {
                common = handleUploadingInfo(message, "位置");
            } else {
                common = handleUploadingInfo(message, message.getBody().toString());
            }
            if (i == 0) {
                chatContent = common;
            } else {
                chatContent = chatContent + MODEL_CONN + common;
            }
        }
        Log.e("MessageRecord", "chatContent-->" + chatContent);
        return chatContent;
    }

    /**
     * 处理上传消息的字段
     *
     * @param message
     * @param body
     * @return
     */
    private String handleUploadingInfo(EMMessage message, String body) {
        String common = "aConversationId=" + doctorId + FIELD_CONN + "aFrom=" + message.getFrom() + FIELD_CONN +
                "aTo=" + message.getTo() + FIELD_CONN + "aBody=" + body + FIELD_CONN + "aTime=" + message.getMsgTime();
        return common;
    }

    /**
     * 获取最新的聊天记录，去重
     *
     * @return
     */
    private List<EMMessage> getNewMessageRecord(List<EMMessage> allMessageList, List<EMMessage> lastMessageList) {
        List<EMMessage> newMessageList = new ArrayList<>();
        int lastSize = lastMessageList.size();
        int endSize = allMessageList.size();
        for (int j = 0; j < endSize; j++) {
            if (j >= lastSize) {
                EMMessage message = allMessageList.get(j);
                newMessageList.add(message);
//                Log.e("MessageRecord","EMMessage-->"+message.getBody().toString());
            }
        }
        if (newMessageList.size() != 0) {
            newMessageList.remove(0);
        }
        Log.e("MessageRecord", "newMessageList-->" + newMessageList.size());
        return newMessageList;
    }

    public void doHttp(Call<ResponseBody> bodyCall) {
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                stopSelf();
                Request request = call.request();
                if (request.body() == null) {
                    Logger.w("messageHttpUrl", "HttpUrl---->" + call.request().url().toString());
                } else {
                    Logger.w("messageHttpUrl", "HttpUrl---->" + call.request().url().toString() +
                            '\n' + getRequestFrom(request));
                }
                try {
                    String result = response.body().string();
                    Logger.w("messageHttpResult", "HttpResultSuccess-->" + result);
                    JSONObject object = JSONObject.parseObject(result);
                    if (object.getString("result").equals("0000")) {

                    } else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                stopSelf();
            }
        });

    }

    /**
     * 获取okhttp 的请求体
     *
     * @param request
     * @return
     */
    private String getRequestFrom(Request request) {
        RequestBody requestBody = request.body();
        okio.Buffer buffer = new okio.Buffer();
        try {
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");

            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String paramsStr = buffer.readString(charset);

            return URLDecoder.decode(paramsStr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

}
