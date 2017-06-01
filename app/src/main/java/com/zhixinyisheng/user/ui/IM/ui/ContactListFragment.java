/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhixinyisheng.user.ui.IM.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.db.InviteMessgeDao;
import com.zhixinyisheng.user.ui.IM.db.UserDao;
import com.zhixinyisheng.user.ui.IM.widget.ContactItemView;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * contact list
 *
 */
public class ContactListFragment extends EaseContactListFragment {

    private static final String TAG = ContactListFragment.class.getSimpleName();
    private ContactSyncListener contactSyncListener;
    private BlackListSyncListener blackListSyncListener;
    private ContactInfoSyncListener contactInfoSyncListener;
    private View loadingView;
    private ContactItemView applicationItem;
    private InviteMessgeDao inviteMessgeDao;

    MyBroadcast myBroadcast;


    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        super.initView();
        registerForContextMenu(listView);

        myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter(Constant.MYBROADCASTACTION_QUGZ);
        getActivity().registerReceiver(myBroadcast, intentFilter);
    }

    class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Logger.e("recereir","recereirjie");
            refresh();

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    public void refresh() {
        UserDao dao = new UserDao(getActivity());
        Map<String, EaseUser> m = new Hashtable<String, EaseUser>();

        List<EaseUser> list = dao.getContactList_list();
        Log.e("好友数", list.size() + "");
        for (EaseUser eu : list) {
            if (eu.getNo().equals("0") && eu.getIsPatient() == 0 && eu.getIsMyDoctor() == 0) {
                m.put(eu.getUsername(), eu);
            }
        }
        setContactsMap(m);
        super.refresh();
        if (inviteMessgeDao == null) {
            inviteMessgeDao = new InviteMessgeDao(getActivity());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {
        super.setUpView();


        titleBar.setRightImageResource(R.drawable.em_add);
        titleBar.setRightLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), AddContactActivity.class));
                NetUtils.hasDataConnection(getActivity());
            }
        });

        //设置联系人数据
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    String username = user.getUsername(),
                            remarkName = user.getNick();

                    // demo中直接进入聊天页面，实际一般是进入用户详情页

                    Constant.JUMP_FRIEND_ID = username;
                    Constant.JUMP_FRIEND_NAME = remarkName;



                    Activity activity = getActivity();
                    if (activity != null && activity instanceof FriendsAty) {
                        FriendsAty aty = (FriendsAty) activity;
                        //如果是分享的操作，则直接进入聊天界面分享
                        if (aty.isShare) {
                            Intent intent = new Intent(aty, ChatActivity.class);
                            intent.putExtra("userId", username);
                            intent.putExtra(FriendsAty.EXTRA_IS_SHARE, true);
                            intent.putExtra(FriendsAty.EXTRA_MODEL, aty.docotrInfo);
                            startActivity(intent);
                            activityAnimation();
                        } else {
                            if (user.getPayedUserID() == 1) {
                                Intent intent = new Intent(getActivity(), DoctorPageActivity.class);
                                intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, username);
                                startActivity(intent);
                                activityAnimation();
                            } else {
                                BaseApplication.userId = username;
                                startActivity(FriendsDetialAty.class, null);
                            }
                        }
                    }


                }
            }
        });


        contactSyncListener = new ContactSyncListener();
        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);

        blackListSyncListener = new BlackListSyncListener();
        DemoHelper.getInstance().addSyncBlackListListener(blackListSyncListener);

        contactInfoSyncListener = new ContactInfoSyncListener();
        DemoHelper.getInstance().getUserProfileManager().addSyncContactInfoListener(contactInfoSyncListener);

        if (DemoHelper.getInstance().isContactsSyncedWithServer()) {
//            loadingView.setVisibility(View.GONE);
        } else if (DemoHelper.getInstance().isSyncingContactsWithServer()) {
//            loadingView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (contactSyncListener != null) {
            DemoHelper.getInstance().removeSyncContactListener(contactSyncListener);
            contactSyncListener = null;
        }

        if (blackListSyncListener != null) {
            DemoHelper.getInstance().removeSyncBlackListListener(blackListSyncListener);
        }

        if (contactInfoSyncListener != null) {
            DemoHelper.getInstance().getUserProfileManager().removeSyncContactInfoListener(contactInfoSyncListener);
        }

        getActivity().unregisterReceiver(myBroadcast);

    }


    EaseUser eu;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        eu = (EaseUser) listView.getItemAtPosition(((AdapterContextMenuInfo) menuInfo).position);
        try {
            toBeProcessUsername = eu.getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().getMenuInflater().inflate(R.menu.em_context_contact_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.delete_contact) {
            new MaterialDialog(getActivity())
                    .setMDNoTitle(true)
                    .setMDMessage(getResources().getString(R.string.ninshifouyaoshanchu)+eu.getNick())
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




//            try {
//                // delete contact
//                deleteContact();
//
//                // remove invitation message
//                EMClient.getInstance().chatManager().deleteConversation(eu.getUsername(), true);
//                InviteMessgeDao dao = new InviteMessgeDao(getActivity());
//                dao.deleteMessage(eu.getUsername());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return true;
        } else if (item.getItemId() == R.id.contact_list_gz) {
            //关注此好友
            doHttp(RetrofitUtils.createApi(IMUrl.class).addtoCareFrineds(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(),
                    UserManager.getUserInfo().getUserId(),
                    eu.getUsername()), 1);


        } else if (item.getItemId() == R.id.contact_editremark) {
            //修改备注
            Bundle bundle = new Bundle();
            bundle.putString("id", eu.getUsername());
            startActivity(EditFriendRemarkAty.class, bundle);

        }
        return super.onContextItemSelected(item);
    }


    /**
     * delete contact
     *
     * @param
     */

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
        if (what == 0) {
            // remove invitation message
            EMClient.getInstance().chatManager().deleteConversation(eu.getUsername(), true);
            InviteMessgeDao dao1 = new InviteMessgeDao(getActivity());
            dao1.deleteMessage(eu.getUsername());

            BaseFrameAty.showToast(getResources().getString(R.string.shanchuhaoyouchenggong));
            //删除数据库中的好友
            UserDao dao = new UserDao(getActivity());
            dao.deleteContact(eu.getUsername());
            DemoHelper.getInstance().getContactList().remove(eu.getUsername());
            contactList.remove(eu);
            contactListLayout.refresh();
            Intent intent = new Intent(Constant.MYBROADCASTACTION_GZ);
            getActivity().sendBroadcast(intent);
        } else if (what == 1) {//关注好友
            BaseFrameAty.showToast(getResources().getString(R.string.guanzhuhaoyouchenggong));

            eu.setNo("1");
            DemoDBManager.getInstance().deleteContact(eu.getUsername());
            DemoDBManager.getInstance().saveContact(eu);

            refresh();

            Intent intent = new Intent(Constant.MYBROADCASTACTION_GZ);
            getActivity().sendBroadcast(intent);

        }


    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (what == 0) {
            BaseFrameAty.showToast(getResources().getString(R.string.shanchuhaoyoushibai));
        } else if (what == 1) {//关注好友
            BaseFrameAty.showToast(getResources().getString(R.string.quxiaoguanzhushibai));
        }
    }

    class ContactSyncListener implements DemoHelper.DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contact list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (success) {
//                                loadingView.setVisibility(View.GONE);
                                refresh();
                            } else {
                                String s1 = getResources().getString(R.string.get_failed_please_check);
//                                Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
//                                loadingView.setVisibility(View.GONE);
                            }
                        }

                    });
                }
            });
        }
    }

    class BlackListSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(boolean success) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refresh();
                }
            });
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class ContactInfoSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contactinfo list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    loadingView.setVisibility(View.GONE);
                    if (success) {
                        refresh();
                    }
                }
            });
        }

    }


}
