package com.zhixinyisheng.user.ui.IM.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.and.yzy.frame.view.toprightwindow.MenuItem;
import com.and.yzy.frame.view.toprightwindow.TopRightMenu;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.ActivityRequestCode;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.interfaces.OnFriendsLoadedListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.db.InviteMessgeDao;
import com.zhixinyisheng.user.ui.IM.db.UserDao;
import com.zhixinyisheng.user.ui.IM.ui.friend.TongXunLuAty;
import com.zhixinyisheng.user.ui.mine.MineScanAty;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorChatActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.zhixinyisheng.user.R.id.tv_title_right;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/10/27  8:48
 * <p/>
 * 类说明: 我的好友
 */
public class FriendsAty extends BaseAty {
    public static final String EXTRA_IS_SHARE = "extra_is_share";
    public static final String EXTRA_MODEL = "extra_model_doctorInfo";
    private static final int RP_CONTACTS = 1;
    //    @Bind(R.id.title_xinxi)
//    ImageView titleXinxi;
//    @Bind(R.id.cjs_tvt)
//    TextView cjsTvt;
//    @Bind(R.id.iv_back)
//    ImageView ivBack;
    ImageView atyMfLlIvGzXl;
    LinearLayout atyMfLlGz;
    ListView atyMfLv;//我的关注ListView
    ImageView aty_mf_ll_iv_gz_sl;
    @Bind(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(tv_title_right)
    TextView tvTitleRight;
    private boolean isResum;
    private ContactListFragment contactListFragment;
    List<EaseUser> list = new ArrayList<>();//我关注的列表
    //折叠我的关注
    boolean is_gz = false;
    //折叠我的好友
    boolean is_hy = false;
    MyAdapter adapter;
    MyBroadcast myBroadcast;
    private View headerView;
    //医生信息，用于分享医生名片
    public DoctorHomePage.UserPdBean docotrInfo;
    //判断是否发分享消息
    public boolean isShare = false;

    ListView lvMyPatient;//我的患者列表
    ImageView ivMyPatientRight;//向右箭头
    ImageView ivMyPatientDown;//向下箭头
    LinearLayout llMyPatient;//我的患者布局

    ListView lvMyDoctor;//我的医生列表
    ImageView ivMyDoctorRight;//向右箭头
    ImageView ivMyDoctorDown;//向下箭头
    LinearLayout llMyDoctor;//我的医生布局
    LinearLayout llMyFriends;
    ImageView iv_myfriends;

    //折叠我的患者
    boolean is_patient = false;
    boolean is_mydoctor = false;
    boolean is_myfriends;
    List<EaseUser> list_patient = new ArrayList<>();//我的患者列表
    List<EaseUser> list_mydoctor = new ArrayList<>();//我的医生列表
    List<EaseUser> list_friends = new ArrayList<>();//我的好友列表
    MyAdapter adapter_patient, adapter_mydoctor;
    private TopRightMenu mTopRightMenu;

    @Override
    public int getLayoutId() {
        return R.layout.aty_myfriends;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initData(Bundle savedInstanceState) {
        //分享到知心医生好友用到
        isShare = getIntent().getBooleanExtra(EXTRA_IS_SHARE, false);
        docotrInfo = (DoctorHomePage.UserPdBean) getIntent().getSerializableExtra(EXTRA_MODEL);
        //好友列表frg
        contactListFragment = new ContactListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, contactListFragment)
                .commit();
        tvTitle.setText(getResources().getString(R.string.left_myFriends));
//        ivBack.setVisibility(View.VISIBLE);
        tvTitleRight.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.friend3xian_selrctor, 0, 0, 0);
//        tv_title_right.setImageResource(R.drawable.friend3xian_selrctor);
//        titleXinxi.setVisibility(View.VISIBLE);
        headerView = LayoutInflater.from(this).inflate(R.layout.view_firend_head, null);
        llMyFriends = (LinearLayout) headerView.findViewById(R.id.ll_myfriends);
        iv_myfriends = (ImageView) headerView.findViewById(R.id.iv_myfriends);
        initList();
        registerBroadcast();
        refreshList();
    }

    private void registerBroadcast() {
        myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter(Constant.MYBROADCASTACTION_GZ);
        registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.MYRECEIVER_TYPE13);
        registerReceiver(myReceiver_type_13, intentFilter2);
    }

    private void initList() {
        careList();
        patientList();
        myDoctorList();
    }

    /**
     * 小红点的广播
     */
    BroadcastReceiver myReceiver_type_13 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            showLoadingDialog(null);
            DemoHelper.getInstance().asyncFetchContactsFromServer(null);
            DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
                @Override
                public void friendsLoaded() {
                    dismissLoadingDialog();
                    requestData();
                    //刷新界面
                    Intent intent2 = new Intent(Constant.MYBROADCASTACTION_QUGZ);
                    sendBroadcast(intent2);
                }

                @Override
                public void LoadedError() {
                    dismissLoadingDialog();
                    showToast(getString(R.string.qingjianchawangluolianjie));
                }
            });


        }
    };

    /**
     * 刷新列表
     */
    private void refreshList() {
        showLoadingDialog(null);
//        DemoHelper.getInstance().init(this);
        DemoHelper.getInstance().asyncFetchContactsFromServer(null);
        DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
            @Override
            public void friendsLoaded() {
                dismissLoadingDialog();
                requestData();
            }

            @Override
            public void LoadedError() {
                dismissLoadingDialog();
                showToast(getString(R.string.qingjianchawangluolianjie));
            }
        });


    }

    /**
     * 我的医生列表
     */
    private void myDoctorList() {
        lvMyDoctor = (ListView) headerView.findViewById(R.id.lv_mydoctor);
        ivMyDoctorRight = (ImageView) headerView.findViewById(R.id.iv_mydoctor_right);
        ivMyDoctorDown = (ImageView) headerView.findViewById(R.id.iv_mydoctor_down);
        llMyDoctor = (LinearLayout) headerView.findViewById(R.id.ll_mydoctor);
        llMyDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_mydoctor) {
                    is_mydoctor = false;
                    ivMyDoctorRight.setVisibility(View.GONE);
                    ivMyDoctorDown.setVisibility(View.VISIBLE);
                    lvMyDoctor.setVisibility(View.VISIBLE);

                } else {
                    is_mydoctor = true;
                    ivMyDoctorRight.setVisibility(View.VISIBLE);
                    ivMyDoctorDown.setVisibility(View.GONE);
                    lvMyDoctor.setVisibility(View.GONE);
                }

            }
        });
        adapter_mydoctor = new MyAdapter(list_mydoctor);
        lvMyDoctor.setAdapter(adapter_mydoctor);
        lvMyDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eu = list_mydoctor.get(i);
                String username = eu.getUsername(),
                        remarkName = eu.getNick();

                // demo中直接进入聊天页面，实际一般是进入用户详情页

                Constant.JUMP_FRIEND_ID = username;
                Constant.JUMP_FRIEND_NAME = remarkName;
//                BaseApplication.userId = username;
                //如果是分享的操作，则直接进入聊天界面分享
                if (isShare) {
                    Intent intent = new Intent(FriendsAty.this, DoctorChatActivity.class);//TODO 剧京改过
                    intent.putExtra("userId", username);
                    intent.putExtra(EXTRA_IS_SHARE, true);
                    intent.putExtra(EXTRA_MODEL, docotrInfo);
                    startActivity(intent);
                    activityAnimation();
                } else {
//                    startActivityForResult(FriendsDetiaelAty.class, null, ActivityRequestCode.FRIENDS_DETAIL);
                    Intent intent = new Intent(FriendsAty.this, DoctorPageActivity.class);
                    intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, username);
                    startActivity(intent);
                    activityAnimation();
                }
            }
        });

        lvMyDoctor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsAty.this);
                //    指定下拉列表的显示数据
                final String[] cities = {getResources().getString(R.string.xiugaibeizhu),
                        getResources().getString(R.string.guanzhucihaoyou),
                        getResources().getString(R.string.shanchuhaoyou)};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 2) {//删除好友
                            eu = list_mydoctor.get(position);
                            new MaterialDialog(FriendsAty.this)
                                    .setMDNoTitle(true)
                                    .setMDMessage(getResources().getString(R.string.ninshifouyaoshanchu) + eu.getNick())
                                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {
                                            deleteContact();
                                        }
                                    })
                                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {

                                        }
                                    })
                                    .show();


                        } else if (which == 1) {//关注好友
                            eu = list_mydoctor.get(position);

                            //关注此好友
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(IMUrl.class).addtoCareFrineds(UserManager.getUserInfo().getPhone(),
                                    UserManager.getUserInfo().getSecret(),
                                    UserManager.getUserInfo().getUserId(),
                                    eu.getUsername()), HttpIdentifier.ADD_MYDOCTOR_TO_CARELIST);


                        } else if (which == 0) {//修改备注
                            eu = list_mydoctor.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", eu.getUsername());
                            startActivityForResult(EditFriendRemarkAty.class, bundle, 0);
                        }
                    }
                });
                builder.show();


                return true;
            }
        });
    }

    /**
     * 我的患者列表
     */
    private void patientList() {
        lvMyPatient = (ListView) headerView.findViewById(R.id.lv_mypatient);
        ivMyPatientRight = (ImageView) headerView.findViewById(R.id.iv_mypatient_right);
        ivMyPatientDown = (ImageView) headerView.findViewById(R.id.iv_mypatient_down);
        llMyPatient = (LinearLayout) headerView.findViewById(R.id.ll_mypatient);

        llMyPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_patient) {
                    is_patient = false;
                    ivMyPatientRight.setVisibility(View.GONE);
                    ivMyPatientDown.setVisibility(View.VISIBLE);
                    lvMyPatient.setVisibility(View.VISIBLE);

                } else {
                    is_patient = true;
                    ivMyPatientRight.setVisibility(View.VISIBLE);
                    ivMyPatientDown.setVisibility(View.GONE);
                    lvMyPatient.setVisibility(View.GONE);
                }
            }
        });
        adapter_patient = new MyAdapter(list_patient);
        lvMyPatient.setAdapter(adapter_patient);
        lvMyPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eu = list_patient.get(i);
                String username = eu.getUsername(),
                        remarkName = eu.getNick();

                // demo中直接进入聊天页面，实际一般是进入用户详情页

                Constant.JUMP_FRIEND_ID = username;
                Constant.JUMP_FRIEND_NAME = remarkName;

                //如果是分享的操作，则直接进入聊天界面分享
                if (isShare) {
                    Intent intent = new Intent(FriendsAty.this, ChatActivity.class);
                    intent.putExtra("userId", username);
                    intent.putExtra(EXTRA_IS_SHARE, true);
                    intent.putExtra(EXTRA_MODEL, docotrInfo);
                    startActivity(intent);
                    activityAnimation();
                } else {
                    BaseApplication.userId = username;
                    startActivityForResult(FriendsDetialAty.class, null, ActivityRequestCode.FRIENDS_DETAIL);
                }
            }
        });

        lvMyPatient.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsAty.this);
                //    指定下拉列表的显示数据
                final String[] cities = {getResources().getString(R.string.xiugaibeizhu),
                        getResources().getString(R.string.guanzhucihaoyou), getResources().getString(R.string.shanchuhaoyou)};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 2) {
                            eu = list_patient.get(position);
                            new MaterialDialog(FriendsAty.this)
                                    .setMDNoTitle(true)
                                    .setMDMessage(getResources().getString(R.string.ninshifouyaoshanchu) + eu.getNick())
                                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {
                                            deleteContact();
                                        }
                                    })
                                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {

                                        }
                                    })
                                    .show();


                        } else if (which == 1) {
                            eu = list_patient.get(position);

                            //关注此好友
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(IMUrl.class).addtoCareFrineds(UserManager.getUserInfo().getPhone(),
                                    UserManager.getUserInfo().getSecret(),
                                    UserManager.getUserInfo().getUserId(),
                                    eu.getUsername()), HttpIdentifier.ADD_MYPATIENT_TO_CARELIST);


                        } else if (which == 0) {//修改备注
                            eu = list_patient.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", eu.getUsername());
                            startActivityForResult(EditFriendRemarkAty.class, bundle, 0);
                        }
                    }
                });
                builder.show();


                return true;
            }
        });

    }

    /**
     * 我关注的好友列表
     */
    private void careList() {
        atyMfLv = (ListView) headerView.findViewById(R.id.aty_mf_lv);
        aty_mf_ll_iv_gz_sl = (ImageView) headerView.findViewById(R.id.aty_mf_ll_iv_gz_sl);
        atyMfLlIvGzXl = (ImageView) headerView.findViewById(R.id.aty_mf_ll_iv_gz_xl);
        atyMfLlGz = (LinearLayout) headerView.findViewById(R.id.aty_mf_ll_gz);

        //关注的点击
        atyMfLlGz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_gz) {
                    is_gz = false;
                    atyMfLlIvGzXl.setVisibility(View.GONE);
                    aty_mf_ll_iv_gz_sl.setVisibility(View.VISIBLE);
                    atyMfLv.setVisibility(View.VISIBLE);

                } else {
                    is_gz = true;
                    atyMfLlIvGzXl.setVisibility(View.VISIBLE);
                    aty_mf_ll_iv_gz_sl.setVisibility(View.GONE);
                    atyMfLv.setVisibility(View.GONE);

                }
            }
        });


        adapter = new MyAdapter(list);
        atyMfLv.setAdapter(adapter);


        atyMfLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eu = list.get(i);
                String username = eu.getUsername(),
                        remarkName = eu.getNick();
                int payedUserId = eu.getPayedUserID();

                // demo中直接进入聊天页面，实际一般是进入用户详情页

                Constant.JUMP_FRIEND_ID = username;
                Constant.JUMP_FRIEND_NAME = remarkName;

                //如果是分享的操作，则直接进入聊天界面分享
                if (isShare) {
                    if (payedUserId == 1) {
                        Intent intent = new Intent(FriendsAty.this, DoctorChatActivity.class);//TODO 剧京改过
                        intent.putExtra("userId", username);
                        intent.putExtra(EXTRA_IS_SHARE, true);
                        intent.putExtra(EXTRA_MODEL, docotrInfo);
                        startActivity(intent);
                        activityAnimation();
                    } else {
                        Intent intent = new Intent(FriendsAty.this, ChatActivity.class);
                        intent.putExtra("userId", username);
                        intent.putExtra(EXTRA_IS_SHARE, true);
                        intent.putExtra(EXTRA_MODEL, docotrInfo);
                        startActivity(intent);
                        activityAnimation();
                    }
                } else {
                    if (payedUserId == 1) {
                        Intent intent = new Intent(FriendsAty.this, DoctorPageActivity.class);
                        intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, username);
                        startActivity(intent);
                        activityAnimation();
                    } else {
                        BaseApplication.userId = username;
                        startActivityForResult(FriendsDetialAty.class, null, ActivityRequestCode.FRIENDS_DETAIL);
                    }


                }


            }
        });


        atyMfLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsAty.this);
                //    指定下拉列表的显示数据
                final String[] cities = {getResources().getString(R.string.xiugaibeizhu),
                        getResources().getString(R.string.quxiaoguanzhu), getResources().getString(R.string.shanchuhaoyou)};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 2) {
                            eu = list.get(position);
                            new MaterialDialog(FriendsAty.this)
                                    .setMDNoTitle(true)
                                    .setMDMessage(getResources().getString(R.string.ninshifouyaoshanchu) + eu.getNick())
                                    .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {
                                            deleteContact();
                                        }
                                    })
                                    .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                        @Override
                                        public void dialogBtnOnClick() {

                                        }
                                    })
                                    .show();
                            // remove invitation message
//                            EMClient.getInstance().chatManager().deleteConversation(eu.getUsername(), true);
//                            InviteMessgeDao dao = new InviteMessgeDao(FriendsAty.this);
//                            dao.deleteMessage(eu.getUsername());

                        } else if (which == 1) {
                            eu = list.get(position);
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(IMUrl.class).delFromCareFrineds(UserManager.getUserInfo().getPhone(),
                                    UserManager.getUserInfo().getSecret(),
                                    UserManager.getUserInfo().getUserId(),
                                    eu.getUsername()), 1);
                        } else if (which == 0) {//修改备注
                            eu = list.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", eu.getUsername());
                            startActivityForResult(EditFriendRemarkAty.class, bundle, 0);
                        }
                    }
                });
                builder.show();


                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestData();
    }


    class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            requestData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myBroadcast != null) {

            unregisterReceiver(myBroadcast);
        }
        if (myReceiver_type_13 != null) {
            unregisterReceiver(myReceiver_type_13);
        }
    }

    EaseUser eu;

    public void deleteContact() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(IMUrl.class).deleteFrined(UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret(),
                UserManager.getUserInfo().getUserId(),
                eu.getUsername()), 0);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 0:
                // remove invitation message
                EMClient.getInstance().chatManager().deleteConversation(eu.getUsername(), true);
                InviteMessgeDao dao1 = new InviteMessgeDao(FriendsAty.this);
                dao1.deleteMessage(eu.getUsername());

                BaseFrameAty.showToast(getResources().getString(R.string.shanchuhaoyouchenggong));
                //删除数据库中的好友
                UserDao dao = new UserDao(FriendsAty.this);
                dao.deleteContact(eu.getUsername());

                requestData();
                break;
            case 1: {//取消关注
                BaseFrameAty.showToast(getResources().getString(R.string.quxiaoguanzhuchenggong));

                eu.setNo("0");
                if (eu.getIsPatient() == -2) {
                    eu.setIsPatient(1);
                } else if (eu.getPayedUserID() == 1) {
                    eu.setIsMyDoctor(1);
                }
                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
                requestData();
                Intent intent = new Intent(Constant.MYBROADCASTACTION_QUGZ);
                sendBroadcast(intent);

                break;
            }
            case HttpIdentifier.ADD_MYPATIENT_TO_CARELIST: {//关注好友(我的患者)
                BaseFrameAty.showToast(getResources().getString(R.string.guanzhuhaoyouchenggong));
                eu.setNo("1");
                eu.setIsPatient(-2);//表示什么 你懂得

                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
                requestData();
                Intent intent = new Intent(Constant.MYBROADCASTACTION_GZ);
                sendBroadcast(intent);
                break;
            }
            case HttpIdentifier.ADD_MYDOCTOR_TO_CARELIST: {//关注好友(我的医生)
                BaseFrameAty.showToast(getResources().getString(R.string.guanzhuhaoyouchenggong));
                eu.setNo("1");
                eu.setIsMyDoctor(-2);//表示什么 你懂得

                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
                requestData();
                Intent intent = new Intent(Constant.MYBROADCASTACTION_GZ);
                sendBroadcast(intent);
                break;
            }
        }


    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (what == 0) {
            BaseFrameAty.showToast(getResources().getString(R.string.dingdanhaimeiguoqi));
        } else if (what == 1) {//取消关注
            BaseFrameAty.showToast(getResources().getString(R.string.guanzhuhaoyoushibai));
        }

    }


    @Override
    public void requestData() {
        list.clear();
        list_patient.clear();
        list_mydoctor.clear();
        list_friends.clear();
        List<EaseUser> list_e = DemoDBManager.getInstance().getContactList_list();
        for (EaseUser eu : list_e) {
            if (eu.getNo().equals("1")) {
                list.add(eu);
            } else if (eu.getIsPatient() == 1) {
                list_patient.add(eu);
            } else if (eu.getIsMyDoctor() == 1) {
                list_mydoctor.add(eu);
            } else if (eu.getNo().equals("0") && eu.getIsPatient() == 0 && eu.getIsMyDoctor() == 0) {
                list_friends.add(eu);
            }
        }
        if (list_friends.size() == 0) {
            llMyFriends.setVisibility(View.GONE);
        } else {
            llMyFriends.setVisibility(View.VISIBLE);
        }

        if (list.size() == 0) {
            atyMfLlGz.setVisibility(View.GONE);
            atyMfLv.setVisibility(View.GONE);
        } else {
            atyMfLlGz.setVisibility(View.VISIBLE);
            atyMfLv.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        if (list_patient.size() == 0) {
            llMyPatient.setVisibility(View.GONE);
            lvMyPatient.setVisibility(View.GONE);
        } else {
            llMyPatient.setVisibility(View.VISIBLE);
            lvMyPatient.setVisibility(View.VISIBLE);
        }
        adapter_patient.notifyDataSetChanged();
        if (list_mydoctor.size() == 0) {
            llMyDoctor.setVisibility(View.GONE);
            lvMyDoctor.setVisibility(View.GONE);
        } else {
            llMyDoctor.setVisibility(View.VISIBLE);
            lvMyDoctor.setVisibility(View.VISIBLE);
        }
        adapter_mydoctor.notifyDataSetChanged();

    }


    @Override
    protected void onResume() {
        super.onResume();
        Logger.e("onResume", "onResume");
//        addPopWindow.dismiss();
        if (!isResum) {
            isResum = true;
            EaseContactListFragment superFragment = (EaseContactListFragment) contactListFragment;
            superFragment.listView.addHeaderView(headerView);
        }
    }

    /**
     * 检查权限（运行时权限）
     */
    private boolean toCheckPermission() {
        int result = ContextCompat.checkSelfPermission(FriendsAty.this, Manifest.permission.READ_CONTACTS);
        if (PackageManager.PERMISSION_GRANTED != result) {
            ActivityCompat.requestPermissions(FriendsAty.this, new String[]{Manifest.permission.READ_CONTACTS}, RP_CONTACTS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (RP_CONTACTS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(TongXunLuAty.class, null);
            } else {

            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.iv_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_title_right:
//                addPopWindow.showPopupWindow(titleXinxi);
                mTopRightMenu = new TopRightMenu(FriendsAty.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem(R.mipmap.hylbtk_btn_tjhy, getResources().getString(R.string.jiahaoyou)));
                menuItems.add(new MenuItem(R.mipmap.hylbtk_btn_sys, getResources().getString(R.string.saoyisao)));
                menuItems.add(new MenuItem(R.mipmap.ic_pop_tongxunlu, getResources().getString(R.string.tongxunlu)));
                mTopRightMenu
                        .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)     //默认高度480
                        .setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
//                                Toast.makeText(FriendsAty.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                                switch (position) {
                                    case 0://加好友
                                        startActivity(SearchFriendAty.class, null);
                                        break;
                                    case 1:
                                        SaoYiSao();
                                        break;
                                    case 2:
//                                        boolean w = PermissionsUtil.checkPermission(Manifest.permission.WRITE_CONTACTS);
//                                        boolean r = PermissionsUtil.checkPermission(Manifest.permission.READ_CONTACTS);
//                                        Logger.e("w or r", w + ",,," + r);
                                        if (toCheckPermission()) {
                                            startActivity(TongXunLuAty.class, null);
                                        }

                                        break;
                                }

                            }
                        });
                if (LanguageUtil.judgeLanguage().equals("zh")) {
                    mTopRightMenu.showAsDropDown(tvTitleRight, -100, 0);
                } else {
                    mTopRightMenu.showAsDropDown(tvTitleRight, 0, 0);
                }


                break;
        }
    }

    private void TongXunLu() {
        if (PermissionsUtil.is6()) {
            PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    Logger.e("aaaa", "aaaa");
                    startActivity(TongXunLuAty.class, null);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Logger.e("bbbb", "bbbb");
                    Toast.makeText(FriendsAty.this, R.string.weikaiqiduqulianxirenquanxian, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    Logger.e("cccc", "cccc");
                    PermissionsUtil.showPermissDialogBy6(FriendsAty.this, token, getString(R.string.kaiqiduqulianxirenquanxian));
                }
            }, Manifest.permission.READ_CONTACTS);

        } else {
            if (PermissionsUtil.checkPermission(Manifest.permission.READ_CONTACTS)) {
                startActivity(TongXunLuAty.class, null);
            } else {
                Toast.makeText(FriendsAty.this, R.string.weikaiqiduqulianxirenquanxian, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaoYiSao() {
        if (PermissionsUtil.is6()) {
            PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    startActivity(MineScanAty.class, null);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Toast.makeText(FriendsAty.this, R.string.weikaiqixiangjiquanxian, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    PermissionsUtil.showPermissDialogBy6(FriendsAty.this, token, getString(R.string.kaiqixiangjiquanxian));
                }
            }, Manifest.permission.CAMERA);

        } else {
            if (PermissionsUtil.checkPermission(Manifest.permission.CAMERA)) {
                startActivity(MineScanAty.class, null);
            } else {
                Toast.makeText(FriendsAty.this, R.string.weikaiqixiangjiquanxian, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class MyAdapter extends BaseAdapter {
        List<EaseUser> list = new ArrayList<>();

        public MyAdapter(List<EaseUser> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (view == null) {
                view = LayoutInflater.from(FriendsAty.this).inflate(R.layout.ease_row_contact, null);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            try {
                EaseUser easeUser = list.get(position);
                String username = easeUser.getUsername();
                EaseUserUtils.setUserNick(username, vh.tv);
                EaseUserUtils.setUserAvatar(FriendsAty.this, username, vh.iv);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return view;
        }
    }

    public class ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder(View v) {
            iv = (ImageView) v.findViewById(R.id.avatar);
            tv = (TextView) v.findViewById(R.id.name);
        }
    }


}
