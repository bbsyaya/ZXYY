package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.EaseConstant;
import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.LoadingDialog;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.ChatRecord;
import com.zhixinyisheng.user.domain.doctor.Doctor;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.service.ChatRecordService;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.ui.ChatFragment;
import com.zhixinyisheng.user.ui.pay.PurchaseServiceAty;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 和医生聊天
 */
public class DoctorChatActivity extends BaseAty {
    public static final String EXTRA_DOCTOR_NAME = "doctorname";
    public static final String EXTRA_RELATION_STATE = "extra_relation_state";
    public static final String EXTRA_ORDER_ID = "EXTRA_ORDER_ID";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    private EaseChatFragment chatFragment;
    private String doctorId, mOrderId;
    private int initChatSize;
    private List<EMMessage> allMessage;
    // 0好友 1自己是患者 2自己是医生 -1无关系
    private int relationSate = -3;
    private int isDoctor;
    //1年 2月 3周 4日  6免费
    public String num = "0", bugNum = "0", canSayNum = "0", type = "6";
    public long lEndTime;
    private DoctorHomePage doctorHomePage;
    private DoctorHomePage.UserPdBean doctorInfo;
    private Doctor.ListBean doctorInfoFromPay;
    public long webTime;
    LoadingDialog mLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_docotr_chat;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userId = UserManager.getUserInfo().getUserId();
        doctorId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        relationSate = getIntent().getIntExtra(EXTRA_RELATION_STATE, -3);
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        getDoctorInfo();
        try {
            doctorInfoFromPay = (Doctor.ListBean) getIntent().getSerializableExtra(EaseConstant.EXTRA_DOCTOR_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            doctorInfoFromPay = null;
        }
        initViewData();
        initEMInfo();
//        loadGetChatNumberApi();
        new Thread(new Runnable() {
            @Override
            public void run() {
                webTime = getWebsiteDatetime(HttpConfig.BASE_URL);
            }
        }).start();
    }

    /**
     * 获取指定网站的日期时间
     *
     * @param webUrl
     * @return
     * @author SHANHY
     * @date 2015年11月27日
     */
    private static long getWebsiteDatetime(String webUrl) {
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            return ld;
//            Date date = new Date(ld);// 转换为标准时间对象
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
//            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取医生信息
     */
    private void getDoctorInfo() {
//        showLoadingDialog(null);
        mLoadingDialog.showLoadingDialog(null);
        //获取doctorinfo
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).doctorInfo(phone, secret, userId, doctorId),
                HttpIdentifier.DOCTOR_INFO);
    }

    /**
     * 和view赋值
     */
    private void initViewData() {

        isDoctor = UserManager.getUserInfo().getIsDoctor();
        chatFragment = new ChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.rl_chat_fragment, chatFragment).commit();

    }

    /**
     * 初始化em需要的信息
     */
    private void initEMInfo() {

        EaseUser easeUser = EaseUserUtils.getUserInfo(doctorId);
        initChatSize = initChatSize(easeUser);
        //用于上传聊天记录
        allMessage = getChatAllMessage(easeUser);
        if (allMessage != null && allMessage.size() != 0) {
            EMMessage message = allMessage.get(allMessage.size() - 1);
            UserManager.saveMsgIdSp(message.getMsgId());
        } else {
            UserManager.saveMsgIdSp("");
        }
    }

    /**
     * 获取聊天条数接口
     */
    private void loadGetChatNumberApi() {
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).getChatNumber(phone, secret, userId, doctorId),
                HttpIdentifier.GET_CHAT_NUMBER);
    }

    public int payedUserId;

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        switch (what) {
            case HttpIdentifier.GET_CHAT_NUMBER:
                Logger.e("chat num",result);
                try {
                    mLoadingDialog.dismiss();
                    ChatRecord chatRecord = JSON.parseObject(result, ChatRecord.class);
                    ChatRecord.DbBean dbBean = chatRecord.getDb();
                    //payedUserID：0 好友关系 1 付费方 2收费方 -1不是医患关系，需付费    -2 不是好友关系，需加好友
                    payedUserId = dbBean.getPayedUserID();
                    mOrderId = dbBean.getPayRecordId();
                    canSayNum = dbBean.getCanSayNum() + "";
                    num = dbBean.getNum() + "";
                    if (num.equals("")) {//剧京：num 有可能为空字符串
                        num = "0";
                    }
                    UserManager.setChatNum(Integer.parseInt(num));
                    type = dbBean.getType() + "";
                    bugNum = dbBean.getBuyNum() + "";
                    if (bugNum.equals("")) {//剧京：bugNum 有可能为空字符串
                        bugNum = "0";
                    }

                    if (1 == payedUserId) {
                        // 0 不是 1 是(获得的是最后一个订单ID是否已评价)
                        String isAppraise = dbBean.getIsAppraise();
                        if ("0".equals(isAppraise)) {
                            tvTitleRight.setText(R.string.pingjia);
                        } else if ("1".equals(isAppraise)) {
                            tvTitleRight.setText(R.string.yipingjia);
                        }
                    } else if (2 == payedUserId) {
                        tvTitleRight.setText(R.string.jinggao);
                    } else if (0 == payedUserId) {
                        tvTitleRight.setVisibility(View.GONE);
                    }

                    String strEndTime = dbBean.getEndTime();
                    if (!"".equals(lEndTime)) {
                        lEndTime = Long.parseLong(strEndTime);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                break;
            case HttpIdentifier.DOCTOR_INFO://医生信息
                doctorHomePage = JSON.parseObject(result, DoctorHomePage.class);
                doctorInfo = doctorHomePage.getUserPd();
                tvTitle.setText(doctorInfo.getName() + getString(R.string.tdaifu));
                loadGetChatNumberApi();
                break;
        }
    }

    public void showTagDialog() {
        new MaterialDialog(this)
                .setMDNoTitle(true)
                .setMDMessage(getString(R.string.ninyichangguomianfeitiaoshu))
                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        Intent intent = new Intent(DoctorChatActivity.this, PurchaseServiceAty.class);
                        if (doctorInfo != null) {
                            intent.putExtra(EaseConstant.EXTRA_DOCTOR_INFO, doctorInfo);
                        } else {
                            intent.putExtra(EaseConstant.EXTRA_DOCTOR_INFO, doctorInfoFromPay);
                        }
                        //将购买的数量传过去
//                        intent.putExtra(EaseConstant.EXTRA_BUY_NUM, getIntent().getStringExtra(EaseConstant.EXTRA_BUY_NUM));
                        startActivity(intent);
                        activityAnimation();
                        DoctorChatActivity.this.finish();
                    }
                })
                .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        DoctorChatActivity.this.finish();
                    }
                })
                .show();

    }

    private int initChatSize(EaseUser easeUser) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(easeUser.getUsername());
        if (conversation != null) {
            //获取此会话的所有消息
            List<EMMessage> messages = conversation.getAllMessages();
            return messages.size();
        }
        return 0;
    }

    /**
     * 获取所有聊天记录消息
     *
     * @return
     */
    private List<EMMessage> getChatAllMessage(EaseUser easeUser) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(easeUser.getUsername());
        if (conversation != null) {
            //获取此会话的所有消息
            List<EMMessage> messages = conversation.getAllMessages();
            return messages;
        }
        return null;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //发送服务 在后台处理逻辑
        Intent intent = new Intent(this, ChatRecordService.class);
        intent.putExtra(ChatRecordService.EXTRA_DOCTOR_ID, doctorId);
        intent.putExtra(ChatRecordService.EXTRA_INIT_CHAT_SIZE, initChatSize);
        startService(intent);
    }

    @OnClick({R.id.tv_title_right, R.id.iv_title_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                hideSoftKeyboard();
                this.finish();
                break;
            case R.id.tv_title_right:
                Intent intent = null;
                String text = tvTitleRight.getText().toString();
                if (getString(R.string.jinggao).equals(text)) {
                    intent = new Intent(this, WarningDoctorActivity.class);
                } else if (getString(R.string.pingjia).equals(text)) {
                    intent = new Intent(this, EvaluateDoctorActivity.class);
                    intent.putExtra(DoctorChatActivity.EXTRA_ORDER_ID, mOrderId);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, doctorId);
                }else{
                    showToast(getString(R.string.bunengchongfupingjia));
                    return;
                }
                startActivity(intent);
                activityAnimation();
                this.finish();
                break;
        }
    }

}