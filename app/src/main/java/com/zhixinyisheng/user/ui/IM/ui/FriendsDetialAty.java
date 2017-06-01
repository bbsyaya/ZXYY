package com.zhixinyisheng.user.ui.IM.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.and.yzy.frame.view.switchbutton.SwitchButton;
import com.bumptech.glide.Glide;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.ActivityRequestCode;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.ChatRecord;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.Friend;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.interfaces.OnFriendsLoadedListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.IM.adapter.MyRecyclerAdapter;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.domain.FriendDetialEntity;
import com.zhixinyisheng.user.ui.IM.domain.FriendDetialEntity_F;
import com.zhixinyisheng.user.ui.IM.domain.YichangDataEntity;
import com.zhixinyisheng.user.ui.IM.ui.friend.FriendsMaterialAty;
import com.zhixinyisheng.user.ui.IM.widget.DividerItemDecoration;
import com.zhixinyisheng.user.ui.data.bmi.BmiNewFgt;
import com.zhixinyisheng.user.ui.data.bushu.BuShuNewFgt;
import com.zhixinyisheng.user.ui.data.laboratory.LaboratoryFgt;
import com.zhixinyisheng.user.ui.data.shuimian.ShuiMianNewFgt;
import com.zhixinyisheng.user.ui.data.tiwen.TiWenFgt;
import com.zhixinyisheng.user.ui.data.xinlv.XinLvFgt;
import com.zhixinyisheng.user.ui.data.xuetang.XueTangFgt;
import com.zhixinyisheng.user.ui.data.xueya.XueYaFgt;
import com.zhixinyisheng.user.ui.remind.UseMedicineActivity;
import com.zhixinyisheng.user.util.Colors;
import com.zhixinyisheng.user.util.DensityUtils;
import com.zhixinyisheng.user.util.LanguageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 好友详情
 * Created by 焕焕 on 2016/11/11.
 */
public class FriendsDetialAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_nick_sex)
    TextView tvNickSex;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.fridd_rlv)
    RecyclerView friddRlv;
    @Bind(R.id.fridd_tv_xm)
    TextView friddTvXm;
    @Bind(R.id.ll_have_right)
    LinearLayout llHaveRight;
    @Bind(R.id.rl_noright)
    RelativeLayout rl_noright;
    @Bind(R.id.fragment_content)
    RelativeLayout fragmentContent;
    @Bind(R.id.switch_btn)
    SwitchButton switchCompat;
    @Bind(R.id.tv_remind)
    TextView tvRemind;
    @Bind(R.id.rl_fankun)
    RelativeLayout rlFankun;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.view_line)
    View viewLine;
    //recycleview集合
    private List<YichangDataEntity> mDatas = new ArrayList<>();
    //recycleview适配器
    private MyRecyclerAdapter recycleAdapter;
    //对话框集合
    private ArrayList<DialogMenuItem> testItems = new ArrayList<DialogMenuItem>();
    FriendDetialEntity fde;
    FriendDetialEntity_F fdef;
    FragmentManager fragmentManager;
    Fragment xinLvFgt, xueYaFgt, buShuFgt, shuiMianFgt, bmiFgt, tiWenFgt, xueTangFgt, laboratoryFgt;
    private String result_detial = "";
    private String loginUserId = "";//网络返回的

    @Override
    public int getLayoutId() {
        return R.layout.activity_friends_detial;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titleBtn.setVisibility(View.VISIBLE);
        List<EaseUser> euList = DemoDBManager.getInstance().getContactList_list();
        for (EaseUser eu : euList) {
            if (eu.getUsername().equals(userId)) {
                if (eu.getNo().equals("1")) {
                    titleBtn.setText(R.string.quxiaoguanzhu);
                    titleBtn.setTextColor(Colors.GRAY_999);
                } else {
                    titleBtn.setText(R.string.guanzhu);
                    titleBtn.setTextColor(Colors.GRAY_333);
                }
            }
        }
        int isDoctor = UserManager.getUserInfo().getIsDoctor();
        if (isDoctor == 1) {
            //医生
            rlFankun.setVisibility(View.VISIBLE);
            tvRemind.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
        } else {
            rlFankun.setVisibility(View.GONE);
            tvRemind.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        }
        loginUserId = UserManager.getUserInfo().getUserId();
        DemoHelper.getInstance().asyncFetchContactsFromServer(null);
        cjsTvt.setText(getResources().getString(R.string.haoyouxiangqing));
        ivBack.setVisibility(View.VISIBLE);
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_heartRate)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_bloodPressure)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_steps)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_sleep)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_bmi)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_temperature)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_bloodSuger)));
        testItems.add(new DialogMenuItem(getResources().getString(R.string.home_testSheet)));
        recycleAdapter = new MyRecyclerAdapter(this, mDatas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        friddRlv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        friddRlv.setAdapter(recycleAdapter);
        friddRlv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        friddRlv.addItemDecoration(dividerItemDecoration);

        //设置分隔线
//        friddRlv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
        friddRlv.setItemAnimator(new DefaultItemAnimator());

        fragmentManager = getSupportFragmentManager();
        showFragment("xinLvFgt");
        IntentFilter intentFilter = new IntentFilter(Constant.MYRECEIVER_TYPE_10);
        registerReceiver(myReceiver_type_10, intentFilter);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loadIsAllAlertData(isChecked);
            }
        });
    }

    private void loadIsAllAlertData(boolean isChecked) {
        //用药情况实时提醒，1是 ，0不是
        int isAllAlert = 1;
        if (isChecked) {
            isAllAlert = 1;
        } else {
            isAllAlert = 0;
        }
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataUrl.class).isAlertEdit(phone, secret, loginUserId, userId, String.valueOf(isAllAlert)),
                HttpIdentifier.IS_ALL_ALERT_EDIT);
    }

    BroadcastReceiver myReceiver_type_10 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            requestData();
        }
    };

    @Override
    public void requestData() {
        /**
         * 个人详情
         */
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(IMUrl.class).getfriendsDetail(UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret(),
                UserManager.getUserInfo().getUserId(),
                userId, LanguageUtil.judgeLanguage()), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.userId = UserManager.getUserInfo().getUserId();
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1000://取消关注
                //初始化环信IM SDK
                DemoHelper.getInstance().asyncFetchContactsFromServer(null);
                showLoadingDialog(null);
                DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
                    @Override
                    public void friendsLoaded() {
                        dismissLoadingDialog();
                        showToast(getString(R.string.shezhichenggong));
                        titleBtn.setText(R.string.guanzhu);
                        titleBtn.setTextColor(Colors.GRAY_333);
                    }

                    @Override
                    public void LoadedError() {
                        dismissLoadingDialog();
                        showToast(getString(R.string.qingjianchawangluolianjie));
                    }
                });
                break;
            case HttpIdentifier.ADD_MYPATIENT_TO_CARELIST://关注
                //初始化环信IM SDK
                DemoHelper.getInstance().asyncFetchContactsFromServer(null);
                showLoadingDialog(null);
                DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
                    @Override
                    public void friendsLoaded() {
                        dismissLoadingDialog();
                        showToast(getString(R.string.shezhichenggong));
                        titleBtn.setText(R.string.quxiaoguanzhu);
                        titleBtn.setTextColor(Colors.GRAY_999);
                    }

                    @Override
                    public void LoadedError() {
                        dismissLoadingDialog();
                        showToast(getString(R.string.qingjianchawangluolianjie));
                    }
                });
                break;
            case 0:
                result_detial = result;
                Logger.e("haoyouxiangqing", result);
                rl_noright.setVisibility(View.GONE);
                llHaveRight.setVisibility(View.VISIBLE);
                fde = JSON.parseObject(result, FriendDetialEntity.class);
                shezhiSJ(fde);
                //设置异常数据
                try {
                    shezhiYCSJ(fde);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                setXinLvZXT(fde);
                break;
            case 1:
                showToast(getResources().getString(R.string.submitSuccess));
                sendCMDMessage();

                break;
            case HttpIdentifier.CHAT_INFO:
                ChatRecord chatRecord = JSON.parseObject(result, ChatRecord.class);
                if (chatRecord.getDb().getPayedUserID() == -2 || chatRecord.getDb().getPayedUserID() == -1) {//不是好友关系，需加好友
                    new MaterialDialog(FriendsDetialAty.this)
                            .setMDNoTitle(true)
                            .setMDMessage(getResources().getString(R.string.bunengliaotian))
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
                    Intent intent = new Intent(FriendsDetialAty.this, ChatActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    activityAnimation();
                }
                break;
        }
    }

    /**
     * 发送透传消息
     */
    private void sendCMDMessage() {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);

        //支持单聊和群聊，默认单聊，如果是群聊添加下面这行
        cmdMsg.setChatType(EMMessage.ChatType.Chat);
        String action = "im_agreelook";//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setReceipt(fdef.getDb().getUserId());//发送给某个人
        cmdMsg.setAttribute("a", fdef.getDb().getUsername());//将动态信息发送给对方
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);


    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what) {
            case 0:
                result_detial = result;
                Logger.e("frienddetial", result);
                rl_noright.setVisibility(View.VISIBLE);
                llHaveRight.setVisibility(View.GONE);
                JSONObject object = JSONObject.parseObject(result);
                if (object.getString("result").equals("3333")) {
                    fdef = JSON.parseObject(result, FriendDetialEntity_F.class);
                    shezhiSJ(fdef);
                } else {
                    showToast(object.getString("retMessage"));
                }
                break;
            case 1:
                JSONObject object2 = JSONObject.parseObject(result);
                showToast(object2.getString("retMessage"));
//                showToast("提交失败");
                break;
        }
    }

    public void hideFragment(FragmentTransaction ft) {
        //如果不为空，就先隐藏起来
        if (xinLvFgt != null) {
            ft.hide(xinLvFgt);
        }
        if (xueYaFgt != null) {
            ft.hide(xueYaFgt);
        }

        if (buShuFgt != null) {
            ft.hide(buShuFgt);
        }
        if (shuiMianFgt != null) {
            ft.hide(shuiMianFgt);
        }
        if (bmiFgt != null) {
            ft.hide(bmiFgt);
        }
        if (tiWenFgt != null) {
            ft.hide(tiWenFgt);
        }

        if (xueTangFgt != null) {
            ft.hide(xueTangFgt);
        }
        if (laboratoryFgt != null) {
            ft.hide(laboratoryFgt);
        }

    }

    /**
     * 解决Fragment切换溢出问题
     */
    public void showFragment(String index) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragment(ft);

        switch (index) {

            case "xinLvFgt":
//                ll_main.setBackgroundColor(0xffff6666);
//                ll_main.setFitsSystemWindows(true);
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                setWrapHeight();
                if (xinLvFgt == null) {
                    xinLvFgt = new XinLvFgt();
                    xinLvFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, xinLvFgt);
                } else {
                    ft.show(xinLvFgt);
                }
                break;
            case "xueYaFgt":
                setWrapHeight();
                if (xueYaFgt == null) {
                    xueYaFgt = new XueYaFgt();
                    xueYaFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, xueYaFgt);
                } else {
                    ft.show(xueYaFgt);
                }

                break;

            case "buShuFgt":
                setWrapHeight();
                if (buShuFgt == null) {
                    buShuFgt = new BuShuNewFgt();
                    buShuFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, buShuFgt);
                } else {
                    ft.show(buShuFgt);
                }

                break;
            case "shuiMianFgt":
                setWrapHeight();
                if (shuiMianFgt == null) {
                    shuiMianFgt = new ShuiMianNewFgt();
                    shuiMianFgt.setUserVisibleHint(true);
                    ft.add(R.id.fragment_content, shuiMianFgt);
                } else {
                    ft.show(shuiMianFgt);
                }

                break;
            case "bmiFgt":
                if (bmiFgt == null) {
                    bmiFgt = new BmiNewFgt();
                    bmiFgt.setUserVisibleHint(true);
                    setWrapHeight();
                    ft.add(R.id.fragment_content, bmiFgt);
                } else {
                    setWrapHeight();
                    ft.show(bmiFgt);
                }

                break;
            case "tiWenFgt":
                if (tiWenFgt == null) {
                    tiWenFgt = new TiWenFgt();
                    tiWenFgt.setUserVisibleHint(true);
                    setWrapHeight();
                    ft.add(R.id.fragment_content, tiWenFgt);
                } else {
                    setWrapHeight();
                    ft.show(tiWenFgt);
                }

                break;

            case "xueTangFgt":
                if (xueTangFgt == null) {
                    xueTangFgt = new XueTangFgt();
                    xueTangFgt.setUserVisibleHint(true);
                    setWrapHeight();
                    ft.add(R.id.fragment_content, xueTangFgt);
                } else {
                    setWrapHeight();
                    ft.show(xueTangFgt);
                }

                break;
            case "laboratoryFgt":
                if (laboratoryFgt == null) {
                    laboratoryFgt = new LaboratoryFgt();
                    laboratoryFgt.setUserVisibleHint(true);
                    setLabHeight();
                    ft.add(R.id.fragment_content, laboratoryFgt);
                } else {
                    setLabHeight();
                    ft.show(laboratoryFgt);
                }
                break;
        }

        ft.commit();
    }

    private void setWrapHeight() {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) fragmentContent.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;// 当控件的高强制设成50象素
        fragmentContent.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
    }

    private void setLabHeight() {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) fragmentContent.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = DensityUtils.dp2px(50 + 45 * 10 + 200);// 当控件的高强制设成50象素
//        linearParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;// 当控件的高强制设成50象素
        fragmentContent.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ActivityRequestCode.FRIENDS_MATERIAL) {
            tvNickSex.setText(data.getStringExtra("remark"));
            if (fdef != null) {
                fdef.getDb().setUsername(data.getStringExtra("remark"));
            } else {
                fde.getDb().setUsername(data.getStringExtra("remark"));
            }
            setResult(ActivityRequestCode.FRIENDS_DETAIL, null);

        }
    }

    @OnClick({R.id.title_btn, R.id.fridd_rltt, R.id.cjs_rlb, R.id.fridd_rl_xm, R.id.fridd_btn_fsxx, R.id.tv_shenqingchakan, R.id.tv_remind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_btn:
                if (titleBtn.getText().toString().equals(getString(R.string.guanzhu))) {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(IMUrl.class).addtoCareFrineds(UserManager.getUserInfo().getPhone(),
                            UserManager.getUserInfo().getSecret(),
                            UserManager.getUserInfo().getUserId(),
                            userId), HttpIdentifier.ADD_MYPATIENT_TO_CARELIST);
                } else if (titleBtn.getText().toString().equals(getString(R.string.quxiaoguanzhu))) {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(IMUrl.class).delFromCareFrineds(UserManager.getUserInfo().getPhone(),
                            UserManager.getUserInfo().getSecret(),
                            UserManager.getUserInfo().getUserId(),
                            userId), 1000);
                }
                break;
            case R.id.tv_remind:
                Intent intent = new Intent(this, UseMedicineActivity.class);
                intent.putExtra(UseMedicineActivity.EXTRA_TO_USEER_ID, userId);
                startActivity(intent);
                break;
            case R.id.fridd_rltt:
                Bundle bundle = new Bundle();
//                if (fdef!=null){
//                    bundle.putString("friendsid",fdef.getDb().getUserId());
//                    bundle.putString("username",fdef.getDb().getUsername());
//                }else{
//                    bundle.putString("friendsid",fde.getDb().getUserId());
//                    bundle.putString("username",fde.getDb().getUsername());
//                }
                bundle.putString("result_detial", result_detial);
                startActivityForResult(FriendsMaterialAty.class, bundle, ActivityRequestCode.FRIENDS_MATERIAL);


                //以下是图片放大效果，先注释掉
//                Intent intentDetail = new Intent(FriendsDetialAty.this, RenZhengImageDetailAty.class);
//                if (fdef!=null){
//                    intentDetail.putExtra("images", fdef.getDb().getHeadUrl());
//                }else{
//                    intentDetail.putExtra("images", fde.getDb().getHeadUrl());
//                }
//                startActivity(intentDetail);
//                overridePendingTransition(0, 0);
                break;
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.fridd_rl_xm:
                NormalListDialogCustomAttr();
                break;
            case R.id.fridd_btn_fsxx:
                //TODO 判断是否为好友
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(IMUrl.class).chatInfo(UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(),
                        UserManager.getUserInfo().getUserId(),
                        userId), HttpIdentifier.CHAT_INFO);


//                finish();
                break;
            case R.id.tv_shenqingchakan:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(Friend.class).applylook(UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(),
                        UserManager.getUserInfo().getUserId(),
                        fdef.getDb().getUserId(), LanguageUtil.judgeLanguage()), 1);
                break;
        }
    }

    private void NormalListDialogCustomAttr() {
        final NormalListDialog dialog = new NormalListDialog(this, testItems);
        dialog.title(getResources().getString(R.string.qingxuanze))//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#25CDBC"))//
                .itemPressColor(Color.parseColor("#25CDBC"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show(R.style.myDialogAnim);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showToast(testItems.get(position).getmOperName());
                if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_bloodPressure))) {
                    friddTvXm.setText(getResources().getString(R.string.home_bloodPressure));
                    showFragment("xueYaFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_heartRate))) {
                    friddTvXm.setText(getResources().getString(R.string.home_heartRate));
                    showFragment("xinLvFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_steps))) {
                    friddTvXm.setText(getResources().getString(R.string.home_steps));
                    showFragment("buShuFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_sleep))) {
                    friddTvXm.setText(getResources().getString(R.string.home_sleep));
                    showFragment("shuiMianFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_bmi))) {
                    friddTvXm.setText(getResources().getString(R.string.home_bmi));
                    showFragment("bmiFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_temperature))) {
                    friddTvXm.setText(getResources().getString(R.string.home_temperature));
                    showFragment("tiWenFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_bloodSuger))) {
                    friddTvXm.setText(getResources().getString(R.string.home_bloodSuger));
                    showFragment("xueTangFgt");
                } else if (testItems.get(position).getmOperName().equals(getResources().getString(R.string.home_testSheet))) {
                    friddTvXm.setText(getResources().getString(R.string.home_testSheet));
                    showFragment("laboratoryFgt");
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 设置异常数据
     *
     * @param
     */
    private void shezhiYCSJ(FriendDetialEntity fde) {

        String str = fde.getQuestionDatas();
        if (!str.equals("")) {
            String[] str1 = str.split("\\|");
            for (int i = 0; i < str1.length; i++) {
                if (str1[i] != null) {
                    String[] str2 = str1[i].split(",");
                    YichangDataEntity yd = new YichangDataEntity();
                    yd.setName(str2[0]);
                    yd.setShuzhi(str2[1]);
                    yd.setRiqi(str2[2]);
                    mDatas.add(yd);
                }

            }
            recycleAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 设置界面数据
     *
     * @param fde
     */
    private void shezhiSJ(FriendDetialEntity fde) {
        //获取数据苦衷
//        EaseUser easeUser = EaseUserUtils.getUserInfo(fde.getDb().getUserId());

        Glide.with(this).load(fde.getDb().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)//占位图
                .error(R.mipmap.ic_launcher2)//加载错误图
                .bitmapTransform(new CropCircleTransformation(this))//裁剪圆形
                .into(ivAvatar);
        tvNickSex.setText(fde.getDb().getUsername() + "");
        tvAge.setText(fde.getDb().getAge() + getResources().getString(R.string.YearsOld));
        if (fde.getDb().getSex()==0){
            tvNickSex.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_female,0);
        }else{
            tvNickSex.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_male,0);
        }
        tvAddress.setText(fde.getDb().getAddress());
        if (fde.getDb().getIsAllAlert()==0){
            switchCompat.setChecked(false);
        }else{
            switchCompat.setChecked(true);
        }
//        friddTvttr.setText(fde.getDb().getRemark() + "");
    }

    private void shezhiSJ(FriendDetialEntity_F fdef) {
        Glide.with(this).load(fdef.getDb().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)//占位图
                .error(R.mipmap.ic_launcher2)//加载错误图
                .bitmapTransform(new CropCircleTransformation(this))//裁剪圆形
                .into(ivAvatar);
        tvNickSex.setText(fdef.getDb().getUsername() + "");
        tvAge.setText(fdef.getDb().getAge() + getResources().getString(R.string.YearsOld));
        if (fdef.getDb().getSex()==0){
            tvNickSex.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_female,0);
        }else{
            tvNickSex.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_male,0);
        }
        tvAddress.setText(fde.getDb().getAddress());
//        friddTvttr.setText(fdef.getDb().getRemark() + "");
    }


}
