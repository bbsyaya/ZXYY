package com.hyphenate.easeui.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.util.RetrofitUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.MyEmconversation;
import com.hyphenate.easeui.domain.SysMessageEntity;
import com.hyphenate.easeui.utils.EaseIMUrl;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 会话列表 fragment
 */
public class EaseConversationListFragment extends EaseBaseFragment {
    private final static int MSG_REFRESH = 2;
    protected EditText queryEt;
    protected ImageButton clearSearch;
    protected boolean hidden;

    List<MyEmconversation> conversationList = new ArrayList<MyEmconversation>();
    protected EaseConversationList conversationListView;
    protected FrameLayout errorItemContainer;

    protected boolean isConflict;
    public LinearLayout youxiaoxi;//有消息时候的布局
    public RelativeLayout wuxiaoxi;//无消息时候的布局
    Bundle bundle = null;//传递个人信息  请求接口
    protected EMConversationListener convListener = new EMConversationListener() {

        @Override
        public void onCoversationUpdate() {
            refresh();
        }

    };


    @Override
    public int getLayoutId() {
        return R.layout.ease_fragment_conversation_list;
    }

    @Override
    public void btnClick(View view) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        bundle = getArguments();

        //隐藏环信标题栏
        titleBar.setVisibility(View.GONE);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        conversationListView = (EaseConversationList) getView().findViewById(R.id.list);
        queryEt = (EditText) getView().findViewById(R.id.query);
        // 清除搜索框内容
        clearSearch = (ImageButton) getView().findViewById(R.id.search_clear);
        errorItemContainer = (FrameLayout) getView().findViewById(R.id.fl_error_item);
        youxiaoxi = (LinearLayout) getView().findViewById(R.id.youxiaoxi);
        wuxiaoxi = (RelativeLayout) getView().findViewById(R.id.wuxiaoxi);
        /**
         * 同意添加好友以后刷新界面
         */
        IntentFilter intentFilter = new IntentFilter(Constant.MYACTION_ACCEPTFRI);
        getActivity().registerReceiver(myBroadcastReceiver, intentFilter);


        /**
         * 小红点的广播
         */
        IntentFilter intentFilter_xhd = new IntentFilter(Constant.MYBROADCASTACTION_XIAOHONGDIAN);
        getActivity().registerReceiver(myBroadcastReceiver_xhd, intentFilter_xhd);

        /**
         * 请求数据
         * */
        if (!hidden) {
            refresh();
        }
    }

    @Override
    protected void setUpView() {

        EMClient.getInstance().addConnectionListener(connectionListener);

        // 搜索栏
        queryEt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                conversationListView.filter(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        // 清空搜索信息
        clearSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                queryEt.getText().clear();
                hideSoftKeyboard();
            }
        });

        // 消息列表touch 隐藏键盘
        conversationListView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
    }


    /**
     * Hyphenate 即时通讯状态连接监听器。
     * 回调函数只有onConnected, onDisconnected, 无需考虑连接中，断开中一类的中间状态
     * 请注意, 应用不要在这两个回调函数中更新界面，这两个现成属于工作线程，直接更新界面会导致界面的并发错误。
     */
    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        // 如果遇到弱网掉线情况，
        // 应用收到onDisconnected，此时不需要处理重连操作，Hyphenate SDK在底层自动处理重连。
        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        //成功连接到Hyphenate IM服务器时触发
        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };

    // 消息列表点击监听
    private EaseConversationListItemClickListener listItemClickListener;

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // 网络差 or 断网
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;
                case MSG_REFRESH:
                    // 先获取系统消息,再获取聊天消息
                    getSysMessage();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 链接服务器
     */
    protected void onConnectionConnected() {
        errorItemContainer.setVisibility(View.GONE);
    }

    /**
     * 与服务器失去链接
     */
    protected void onConnectionDisconnected() {
        errorItemContainer.setVisibility(View.VISIBLE);
    }


    /**
     * 更新ui
     */
    public void refresh() {
        showLoadingContentDialog();
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    /**
     * 加载联系人列表
     *
     * @return +
     */
    protected void loadConversationList() {
        // 获取所有联系人
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

        for (EMConversation conversation : conversations.values()) {
            MyEmconversation myEmconversation = new MyEmconversation();
            myEmconversation.setEmConversation(conversation);
            conversationList.add(myEmconversation);
        }


        List<Pair<Long, MyEmconversation>> sortList = new ArrayList<Pair<Long, MyEmconversation>>();
        /**
         * 最近消息事件 需要实时更新
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (MyEmconversation myEmconversation : conversationList) {
                /**
                 * 区分系统消息和聊天消息
                 */
                if (myEmconversation.getEmConversation() != null) {
                    try {
                        sortList.add(new Pair<Long, MyEmconversation>(
                                myEmconversation.getEmConversation().getLastMessage().getMsgTime()
                                , myEmconversation));
                    } catch (Exception e) {
                        Log.e("获取聊天消息错误", e.toString());

                    }

                } else {
                    long time = zhuanHuaTime(myEmconversation.getListBean().getCreateTime());
                    sortList.add(new Pair<Long, MyEmconversation>(time, myEmconversation));
                }
            }
        }


        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<MyEmconversation> list = new ArrayList<MyEmconversation>();
        for (Pair<Long, MyEmconversation> sortItem : sortList) {
            list.add(sortItem.second);
        }


        conversationList.clear();
        conversationList.addAll(list);
        hideLoadingDialog();
        if (conversationList.size() == 0) {
            wuxiaoxi.setVisibility(View.VISIBLE);
            youxiaoxi.setVisibility(View.GONE);
        } else {
            wuxiaoxi.setVisibility(View.GONE);
            youxiaoxi.setVisibility(View.VISIBLE);
        }


        //初始化
        conversationListView.init(conversationList);


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


    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, MyEmconversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, MyEmconversation>>() {
            @Override
            public int compare(final Pair<Long, MyEmconversation> con1, final Pair<Long, MyEmconversation> con2) {
                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!hidden) {
//            refresh();
//        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestData() {

    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(connectionListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    public interface EaseConversationListItemClickListener {
        /**
         * click event for conversation list
         *
         * @param conversation -- clicked item
         */
        void onListItemClicked(MyEmconversation conversation);
    }

    /**
     * set conversation list item click listener
     *
     * @param listItemClickListener
     */
    public void setConversationListItemClickListener(EaseConversationListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    /**
     * 获取所有好友
     */
    public void getSysMessage() {
        conversationList.clear();
        RetrofitUtils.createApi(EaseIMUrl.class)
                .getsysMessage(bundle.getString("phone"), bundle.getString("code"), bundle.getString("userid"))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        BaseFrameAty.showLog("接口地址", call.request().url().toString());
                        try {
                            String result = response.body().string();
                            Logger.e("接口返回结果 会话 eclf", result);

                            SysMessageEntity sysMessageEntity = JSON.parseObject(result, SysMessageEntity.class);

                            for (SysMessageEntity.ListBean listBean : sysMessageEntity.getList()) {
                                MyEmconversation myEmconversation = new MyEmconversation();
                                long time = zhuanHuaTime(listBean.getCreateTime());
//                                myEmconversation.setTime(time);
                                myEmconversation.setListBean(listBean);
                                conversationList.add(myEmconversation);
                            }

                            /**
                             * 获取聊天消息
                             */
                            loadConversationList();

                        } catch (Exception e) {
                            e.printStackTrace();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            e.printStackTrace(new PrintStream(baos));
                            String exception = baos.toString();
                            hideLoadingDialog();
                            showToast(getString(R.string.qingjianchawangluolianjie));
                            Logger.e("接口解析错误", exception);
//                            BaseFrameAty.showToast("服务器错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        BaseFrameAty.showToast("服务器错误!");
                        hideLoadingDialog();
                        try {
                            showToast(getString(R.string.qingjianchawangluolianjie));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(myBroadcastReceiver);
        getActivity().unregisterReceiver(myBroadcastReceiver_xhd);


    }


    /**
     * 系统消息界面跟新广播
     */
    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // 先获取系统消息,再获取聊天消息
            refresh();


        }
    };

    /**
     * 小红点的广播
     */
    BroadcastReceiver myBroadcastReceiver_xhd = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // 先获取系统消息,再获取聊天消息
            refresh();


        }
    };


}
