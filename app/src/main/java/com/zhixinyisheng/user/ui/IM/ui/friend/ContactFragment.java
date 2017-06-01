package com.zhixinyisheng.user.ui.IM.ui.friend;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.DoctorHomePage;
import com.hyphenate.easeui.domain.EaseUser;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.ContactAdapter;
import com.zhixinyisheng.user.config.ActivityRequestCode;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.db.InviteMessgeDao;
import com.zhixinyisheng.user.ui.IM.db.UserDao;
import com.zhixinyisheng.user.ui.IM.ui.ChatActivity;
import com.zhixinyisheng.user.ui.IM.ui.EditFriendRemarkAty;
import com.zhixinyisheng.user.ui.IM.ui.FriendsAty;
import com.zhixinyisheng.user.ui.IM.ui.FriendsDetialAty;
import com.zhixinyisheng.user.ui.IM.ui.SearchFriendAty;
import com.zhixinyisheng.user.ui.data.ranking.RankingAty;
import com.zhixinyisheng.user.ui.mine.MineScanAty;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorChatActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;
import com.zhixinyisheng.user.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 好友列表（新）
 * Created by 焕焕 on 2017/4/17.
 */

public class ContactFragment extends BaseFgt implements View.OnClickListener, ContactAdapter.OnArrowChangeListener, ContactAdapter.OnItemClickListener, ContactAdapter.OnItemLongClickListener {
    public static final String EXTRA_IS_SHARE = "extra_is_share";
    public static final String EXTRA_MODEL = "extra_model_doctorInfo";
    private static final int RP_CONTACTS = 1;
    @Bind(R.id.elv_friends)
    ExpandableListView elvFriends;
    TextView tvContactSearch;
    private List<EaseUser> list_friends = new ArrayList<>();//我的好友列表
    private List<EaseUser> list_care = new ArrayList<>();//我关注的列表
    private List<EaseUser> list_patient = new ArrayList<>();//我的患者列表
    private List<EaseUser> list_doctor = new ArrayList<>();//我的医生列表
    private List<List<EaseUser>> list_child = new ArrayList<>();     //子列表
    private List<String> group = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private EaseUser eu;
    //判断是否发分享消息
    public boolean isShare = false;
    //医生信息，用于分享医生名片
    public DoctorHomePage.UserPdBean docotrInfo;
    private int groupPosition,childPosition;
    private Button btnAddContact,btnScan,btnContacts,btnRanking;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    public void initData() {
        //分享到知心医生好友用到
        isShare = getActivity().getIntent().getBooleanExtra(EXTRA_IS_SHARE, false);
        docotrInfo = (DoctorHomePage.UserPdBean)  getActivity().getIntent().getSerializableExtra(EXTRA_MODEL);
        group.add(getString(R.string.wodeguanzhu));
        group.add(getString(R.string.wodehuanzhe));
        group.add(getString(R.string.wodeyisheng));
        group.add(getString(R.string.wodehaoyou));
        contactAdapter = new ContactAdapter(getActivity(), group);
        elvFriends.setAdapter(contactAdapter);
        contactAdapter.setOnArrowChangeListener(this);
        contactAdapter.setOnItemClickListener(this);
        contactAdapter.setOnItemLongClickListener(this);
        initHeaderView();
        IntentFilter intentFilter = new IntentFilter(Constant.MYRECEIVER_TYPE13);
        getActivity().registerReceiver(myReceiver_type_13, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver_type_13!=null){
            getActivity().unregisterReceiver(myReceiver_type_13);
        }

    }

    /**
     * 初始化头布局
     * */
    private void initHeaderView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.head_contact, null, false);
        btnAddContact = (Button) view.findViewById(R.id.btn_add_contact);
        btnScan = (Button) view.findViewById(R.id.btn_scan);
        btnContacts = (Button) view.findViewById(R.id.btn_contacts);
        btnRanking = (Button) view.findViewById(R.id.btn_ranking);
        tvContactSearch = (TextView) view.findViewById(R.id.tv_contact_search);
        btnAddContact.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        btnContacts.setOnClickListener(this);
        btnRanking.setOnClickListener(this);
        tvContactSearch.setOnClickListener(this);
        elvFriends.addHeaderView(view);
    }

    private void setData() {
        list_care.clear();
        list_patient.clear();
        list_doctor.clear();
        list_friends.clear();
        list_child.clear();
        List<EaseUser> list_e = DemoDBManager.getInstance().getContactList_list();
        for (EaseUser eu : list_e) {
            if (eu.getNo().equals("1")) {
                list_care.add(eu);
            } else if (eu.getIsPatient() == 1) {
                list_patient.add(eu);
            } else if (eu.getIsMyDoctor() == 1) {
                list_doctor.add(eu);
            } else if (eu.getNo().equals("0") && eu.getIsPatient() == 0 && eu.getIsMyDoctor() == 0) {
                list_friends.add(eu);
            }
        }
        list_child.add(list_care);
        list_child.add(list_patient);
        list_child.add(list_doctor);
        list_child.add(list_friends);
        contactAdapter.setList_child(list_child);
        int groupCount = contactAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            elvFriends.expandGroup(i);//列表默认展开
        }

    }
    /**
     * 小红点的广播
     */
    BroadcastReceiver myReceiver_type_13 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_contact_search://搜索
                startActivity(SearchContactAty.class,null);
                break;
            case R.id.btn_add_contact:
                startActivity(SearchFriendAty.class, null);
                break;
            case R.id.btn_scan:
                scanIt();
                break;
            case R.id.btn_contacts:
                if (PermissionsUtil.is6()){
                    if (toCheckPermission()) {
                        startActivity(TongXunLuAty.class, null);
                    }
                }else{
                    startActivity(TongXunLuAty.class, null);
                }
                break;
            case R.id.btn_ranking:
                startActivity(RankingAty.class,null);
                break;
        }
    }
    /**
     * 检查权限（运行时权限）
     */
    private boolean toCheckPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);
        if (PackageManager.PERMISSION_GRANTED != result) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
            return false;
        }
        return true;
    }
    /**
     * 扫一扫
     * */
    private void scanIt() {
        if (PermissionsUtil.is6()) {
            PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    startActivity(MineScanAty.class, null);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Toast.makeText(getActivity(), R.string.weikaiqixiangjiquanxian, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    PermissionsUtil.showPermissDialogBy6(getActivity(), token, getString(R.string.kaiqixiangjiquanxian));
                }
            }, Manifest.permission.CAMERA);

        } else {
            if (PermissionsUtil.checkPermission(Manifest.permission.CAMERA)) {
                startActivity(MineScanAty.class, null);
            } else {
                Toast.makeText(getActivity(), R.string.weikaiqixiangjiquanxian, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onChanged(ImageView view, int groupPosition) {
        if (elvFriends.isGroupExpanded(groupPosition)) {
            view.setImageResource(R.drawable.myfriends_btn_wdgz_s2x);
        } else {
            view.setImageResource(R.drawable.myfriends_btn_wdgz_n2x);
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.CANCEL_CARE:
                eu.setNo("0");
                if (eu.getIsPatient() == -2) {
                    eu.setIsPatient(1);
                    contactAdapter.getList_child().get(groupPosition).remove(childPosition);
                    contactAdapter.getList_child().get(1).add(eu);
                } else if (eu.getPayedUserID() == 1) {
                    eu.setIsMyDoctor(1);
                    contactAdapter.getList_child().get(groupPosition).remove(childPosition);
                    contactAdapter.getList_child().get(2).add(eu);
                }else{
                    contactAdapter.getList_child().get(groupPosition).remove(childPosition);
                    contactAdapter.getList_child().get(3).add(eu);
                }
                contactAdapter.notifyDataSetChanged();
                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
                break;
            case HttpIdentifier.DELETE_CONTACT:
                // remove invitation message
                EMClient.getInstance().chatManager().deleteConversation(eu.getUsername(), true);
                InviteMessgeDao dao1 = new InviteMessgeDao(getActivity());
                dao1.deleteMessage(eu.getUsername());
                showToast(getResources().getString(R.string.shanchuhaoyouchenggong));
                //删除数据库中的好友
                UserDao dao = new UserDao(getActivity());
                dao.deleteContact(eu.getUsername());
                contactAdapter.getList_child().get(groupPosition).remove(childPosition);
                contactAdapter.notifyDataSetChanged();
                break;
            case HttpIdentifier.ADD_MYPATIENT_TO_CARELIST: //关注好友(我的患者)
                showToast(getResources().getString(R.string.guanzhuhaoyouchenggong));
                eu.setNo("1");
                eu.setIsPatient(-2);//表示什么 你懂得
                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
                contactAdapter.getList_child().get(groupPosition).remove(childPosition);
                contactAdapter.getList_child().get(0).add(eu);
                contactAdapter.notifyDataSetChanged();
                break;
            case HttpIdentifier.ADD_MYDOCTOR_TO_CARELIST: //关注好友(我的医生)
                showToast(getResources().getString(R.string.guanzhuhaoyouchenggong));
                eu.setNo("1");
                eu.setIsMyDoctor(-2);//表示什么 你懂得
                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
                contactAdapter.getList_child().get(groupPosition).remove(childPosition);
                contactAdapter.getList_child().get(0).add(eu);
                contactAdapter.notifyDataSetChanged();
                break;
            case HttpIdentifier.ADD_MYFRIENDS_TO_CARELIST:
                showToast(getResources().getString(R.string.guanzhuhaoyouchenggong));
                eu.setNo("1");
                DemoDBManager.getInstance().deleteContact(eu.getUsername());
                DemoDBManager.getInstance().saveContact(eu);
                contactAdapter.getList_child().get(groupPosition).remove(childPosition);
                contactAdapter.getList_child().get(0).add(eu);
                contactAdapter.notifyDataSetChanged();
                break;

        }
    }

    @Override
    public void onItemClick(int groupPosition, int childPosition) {
        eu = list_child.get(groupPosition).get(childPosition);
        String username = eu.getUsername(),
                remarkName = eu.getNick();
        int payedUserId = eu.getPayedUserID();
        // demo中直接进入聊天页面，实际一般是进入用户详情页
        Constant.JUMP_FRIEND_ID = username;
        Constant.JUMP_FRIEND_NAME = remarkName;
        if (groupPosition == 0) {//我的关注
            //如果是分享的操作，则直接进入聊天界面分享
            if (isShare) {
                if (payedUserId == 1) {
                    Intent intent = new Intent(getActivity(), DoctorChatActivity.class);//TODO 剧京改过
                    intent.putExtra("userId", username);
                    intent.putExtra(EXTRA_IS_SHARE, true);
                    intent.putExtra(EXTRA_MODEL, docotrInfo);
                    startActivity(intent);
                    activityAnimation();
                } else {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("userId", username);
                    intent.putExtra(EXTRA_IS_SHARE, true);
                    intent.putExtra(EXTRA_MODEL, docotrInfo);
                    startActivity(intent);
                    activityAnimation();
                }
            } else {
                if (payedUserId == 1) {
                    Intent intent = new Intent(getActivity(), DoctorPageActivity.class);
                    intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, username);
                    startActivity(intent);
                    activityAnimation();
                } else {
                    BaseApplication.userId = username;
                    Bundle bundle = new Bundle();
                    bundle.putString("identity","1");
                    startActivityForResult(FriendsDetialAty.class, bundle, ActivityRequestCode.FRIENDS_DETAIL);
                }
            }
        } else if (groupPosition == 1) {//我的患者

            //如果是分享的操作，则直接进入聊天界面分享
            if (isShare) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userId", username);
                intent.putExtra(EXTRA_IS_SHARE, true);
                intent.putExtra(EXTRA_MODEL, docotrInfo);
                startActivity(intent);
                activityAnimation();
            } else {
                BaseApplication.userId = username;
                Bundle bundle = new Bundle();
                bundle.putString("identity","2");
                startActivityForResult(FriendsDetialAty.class, bundle, ActivityRequestCode.FRIENDS_DETAIL);
            }
        } else if (groupPosition == 2) {//我的医生
            //如果是分享的操作，则直接进入聊天界面分享
            if (isShare) {
                Intent intent = new Intent(getActivity(), DoctorChatActivity.class);//TODO 剧京改过
                intent.putExtra("userId", username);
                intent.putExtra(EXTRA_IS_SHARE, true);
                intent.putExtra(EXTRA_MODEL, docotrInfo);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), DoctorPageActivity.class);
                intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, username);
                startActivity(intent);
            }
        } else if (groupPosition == 3) {//我的好友
            if (isShare) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userId", username);
                intent.putExtra(FriendsAty.EXTRA_IS_SHARE, true);
                intent.putExtra(FriendsAty.EXTRA_MODEL, docotrInfo);
                startActivity(intent);
            } else {
                if (payedUserId == 1) {
                    Intent intent = new Intent(getActivity(), DoctorPageActivity.class);
                    intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, username);
                    startActivity(intent);
                } else {
                    BaseApplication.userId = username;
                    Bundle bundle = new Bundle();
                    bundle.putString("identity","3");
                    startActivity(FriendsDetialAty.class, bundle);
                }
            }

        }

    }

    @Override
    public void onItemLongClick(final int groupPosition, final int childPosition) {
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        if (groupPosition == 0) {//我的关注
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    指定下拉列表的显示数据
            final String[] cities = {getResources().getString(R.string.xiugaibeizhu),
                    getResources().getString(R.string.quxiaoguanzhu), getResources().getString(R.string.shanchuhaoyou)};
            //    设置一个下拉的列表选择项
            builder.setItems(cities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eu = list_child.get(groupPosition).get(childPosition);
                    if (which == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", eu.getUsername());
                        startActivityForResult(EditFriendRemarkAty.class, bundle, 0);
                    } else if (which == 1) {
                        //取消关注
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(IMUrl.class).delFromCareFrineds(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                eu.getUsername()), HttpIdentifier.CANCEL_CARE);

                    } else if (which == 2) {
                        new MaterialDialog(getActivity())
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
                    }
                }
            });
            builder.show();


        } else if (groupPosition == 1) {//我的患者
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    指定下拉列表的显示数据
            final String[] cities = {getResources().getString(R.string.xiugaibeizhu),
                    getResources().getString(R.string.guanzhucihaoyou), getResources().getString(R.string.shanchuhaoyou)};
            //    设置一个下拉的列表选择项
            builder.setItems(cities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eu = list_child.get(groupPosition).get(childPosition);
                    if (which == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", eu.getUsername());
                        startActivityForResult(EditFriendRemarkAty.class, bundle, 0);
                    } else if (which == 1) {
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(IMUrl.class).addtoCareFrineds(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                eu.getUsername()), HttpIdentifier.ADD_MYPATIENT_TO_CARELIST);

                    } else if (which == 2) {
                        new MaterialDialog(getActivity())
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
                    }
                }
            });
            builder.show();
        }else if (groupPosition == 2) {//我的医生
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    指定下拉列表的显示数据
            final String[] cities = {getResources().getString(R.string.xiugaibeizhu),
                    getResources().getString(R.string.guanzhucihaoyou),
                    getResources().getString(R.string.shanchuhaoyou)};
            //    设置一个下拉的列表选择项
            builder.setItems(cities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eu = list_child.get(groupPosition).get(childPosition);
                    if (which == 2) {//删除好友
                        new MaterialDialog(getActivity())
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
                        //关注此好友
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(IMUrl.class).addtoCareFrineds(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                eu.getUsername()), HttpIdentifier.ADD_MYDOCTOR_TO_CARELIST);


                    } else if (which == 0) {//修改备注
                        Bundle bundle = new Bundle();
                        bundle.putString("id", eu.getUsername());
                        startActivityForResult(EditFriendRemarkAty.class, bundle, 0);
                    }
                }
            });
            builder.show();
        }else if (groupPosition==3){//我的好友
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    指定下拉列表的显示数据
            final String[] cities = {getResources().getString(R.string.xiugaibeizhu),
                    getResources().getString(R.string.guanzhucihaoyou), getResources().getString(R.string.shanchuhaoyou)};
            //    设置一个下拉的列表选择项
            builder.setItems(cities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eu = list_child.get(groupPosition).get(childPosition);
                    if (which == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", eu.getUsername());
                        startActivityForResult(EditFriendRemarkAty.class, bundle, 0);
                    } else if (which == 1) {
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(IMUrl.class).addtoCareFrineds(UserManager.getUserInfo().getPhone(),
                                UserManager.getUserInfo().getSecret(),
                                UserManager.getUserInfo().getUserId(),
                                eu.getUsername()), HttpIdentifier.ADD_MYFRIENDS_TO_CARELIST);

                    } else if (which == 2) {
                        new MaterialDialog(getActivity())
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
                    }
                }
            });
            builder.show();
        }
    }

    private void deleteContact() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(IMUrl.class).deleteFrined(UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret(),
                UserManager.getUserInfo().getUserId(),
                eu.getUsername()), HttpIdentifier.DELETE_CONTACT);
    }


}
