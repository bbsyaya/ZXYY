package com.zhixinyisheng.user.ui.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.ActivityRequestCode;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.ui.ContactListFragment;
import com.zhixinyisheng.user.ui.IM.ui.FriendsDetialAty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 好友列表demo
 * Created by 焕焕 on 2017/2/10.
 */
public class FriendsDemoAty extends BaseAty {
    @Bind(R.id.ekv_friends)
    ExpandableListView ekvFriends;
    List<EaseUser> list = new ArrayList<>();//我关注的列表
    List<EaseUser> list_patient = new ArrayList<>();//我的患者列表
    List<EaseUser> list_mydoctor = new ArrayList<>();//我的医生列表
    List<List<EaseUser>> list_child = new ArrayList<>();     //子列表
    List<String> group = new ArrayList<>();
    FriendsListAdaper friendsListAdaper;
    EaseUser eu;
    private ContactListFragment contactListFragment;
    @Override
    public int getLayoutId() {
        return R.layout.aty_friendsdemo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        friendsListAdaper = new FriendsListAdaper();
        ekvFriends.setAdapter(friendsListAdaper);
        askForData();
        //好友列表frg
        contactListFragment = new ContactListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, contactListFragment)
                .commit();
    }

    private void askForData() {
        list.clear();
        list_patient.clear();
        list_mydoctor.clear();
        group.clear();
        list_child.clear();
        List<EaseUser> list_e = DemoDBManager.getInstance().getContactList_list();
        for (EaseUser eu : list_e) {
            if (eu.getNo().equals("1")) {
                list.add(eu);
            } else if (eu.getIsPatient() == 1) {
                list_patient.add(eu);
            } else if (eu.getIsMyDoctor() == 1) {
                list_mydoctor.add(eu);
            }
        }
        if (list.size() != 0 && list_patient.size() == 0 && list_mydoctor.size() == 0) {
            group.add("我的关注");
            list_child.add(list);
        } else if (list.size() != 0 && list_patient.size() != 0 && list_mydoctor.size() == 0) {
            group.add("我的关注");
            group.add("我的患者");
            list_child.add(list);
            list_child.add(list_patient);
        } else if (list.size() != 0 && list_patient.size() != 0 && list_mydoctor.size() != 0) {
            group.add("我的关注");
            group.add("我的患者");
            group.add("我的医生");
            list_child.add(list);
            list_child.add(list_patient);
            list_child.add(list_mydoctor);
        } else if (list.size() == 0 && list_patient.size() != 0 && list_mydoctor.size() == 0) {
            group.add("我的患者");
            list_child.add(list_patient);
        } else if (list.size() == 0 && list_patient.size() != 0 && list_mydoctor.size() != 0) {
            group.add("我的患者");
            group.add("我的医生");
            list_child.add(list_patient);
            list_child.add(list_mydoctor);
        } else if (list.size() == 0 && list_patient.size() == 0 && list_mydoctor.size() != 0) {
            group.add("我的医生");
            list_child.add(list_mydoctor);
        } else if (list.size() != 0 && list_patient.size() == 0 && list_mydoctor.size() != 0) {
            group.add("我的关注");
            group.add("我的医生");
            list_child.add(list);
            list_child.add(list_mydoctor);
        }
        friendsListAdaper.notifyDataSetChanged();
        int groupCount = ekvFriends.getCount();
        for (int i = 0; i < groupCount; i++) {
            ekvFriends.expandGroup(i);
        }

    }

    class FriendsListAdaper extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return group.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list_child.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return list_child.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(FriendsDemoAty.this).inflate(
                    R.layout.item_friends_group, null);
            TextView tv_area = (TextView) convertView.findViewById(R.id.tv_area);
            ImageView iv_jiantou = (ImageView) convertView.findViewById(R.id.iv_jiantou);
            tv_area.setText(group.get(groupPosition));
            if (ekvFriends.isGroupExpanded(groupPosition)) {
                iv_jiantou.setImageResource(R.drawable.myfriends_btn_wdgz_s2x);
            } else {
                iv_jiantou.setImageResource(R.drawable.myfriends_btn_wdgz_n2x);
            }

            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(FriendsDemoAty.this).inflate(R.layout.ease_row_contact, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            EaseUser easeUser = list_child.get(groupPosition).get(childPosition);
            String username = easeUser.getUsername();
            EaseUserUtils.setUserNick(username, vh.tv);
            EaseUserUtils.setUserAvatar(FriendsDemoAty.this, username, vh.iv);
            vh.ll_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (groupPosition==0){//我的关注
                        Toast.makeText(FriendsDemoAty.this, "111 guan", Toast.LENGTH_SHORT).show();
                        eu = list_child.get(groupPosition).get(childPosition);
                        String username = eu.getUsername(),
                                remarkName = eu.getNick();
                        // demo中直接进入聊天页面，实际一般是进入用户详情页
                        Constant.JUMP_FRIEND_ID = username;
                        Constant.JUMP_FRIEND_NAME = remarkName;
                        BaseApplication.userId = username;
                        startActivityForResult(FriendsDetialAty.class, null, ActivityRequestCode.FRIENDS_DETAIL);
                    }else if (groupPosition==1){//我的患者
                        Toast.makeText(FriendsDemoAty.this, "111 huan", Toast.LENGTH_SHORT).show();
                    }if (groupPosition==2){//我的医生
                        Toast.makeText(FriendsDemoAty.this, "222 yiyiyi", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            vh.ll_contact.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (groupPosition==0){//我的关注
//                        list_child.get(groupPosition).remove(childPosition);
//                        notifyDataSetChanged();
//                        Toast.makeText(FriendsDemoAty.this, "000", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsDemoAty.this);
                        //    指定下拉列表的显示数据
                        final String[] cities = {"修改备注", "取消关注", "删除好友"};
                        //    设置一个下拉的列表选择项
                        builder.setItems(cities, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
//                                    eu = list_child.get(groupPosition).remove(childPosition);



                                } else if (which == 1) {
                                    eu = list_child.get(groupPosition).get(childPosition);
                                    eu.setNo("0");
                                    if (eu.getIsPatient() == -2) {
                                        eu.setIsPatient(1);
                                    } else if (eu.getPayedUserID() == 1) {
                                        eu.setIsMyDoctor(1);
                                    }
                                    DemoDBManager.getInstance().deleteContact(eu.getUsername());
                                    DemoDBManager.getInstance().saveContact(eu);
//                                    askForData();
                                    list_child.get(groupPosition).remove(childPosition);
                                    notifyDataSetChanged();
                                    Intent intent = new Intent(Constant.MYBROADCASTACTION_QUGZ);
                                    sendBroadcast(intent);

                                } else if (which == 2) {
                                }
                            }
                        });
                        builder.show();



                    }else if (groupPosition==1){//我的患者
                        Toast.makeText(FriendsDemoAty.this, "111", Toast.LENGTH_SHORT).show();
                    }if (groupPosition==2){//我的医生
                        Toast.makeText(FriendsDemoAty.this, "222", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class ViewHolder {
        ImageView iv;
        TextView tv;
        LinearLayout ll_contact;

        public ViewHolder(View v) {
            iv = (ImageView) v.findViewById(R.id.avatar);
            tv = (TextView) v.findViewById(R.id.name);
            ll_contact = (LinearLayout) v.findViewById(R.id.ll_contact);
        }
    }
}
