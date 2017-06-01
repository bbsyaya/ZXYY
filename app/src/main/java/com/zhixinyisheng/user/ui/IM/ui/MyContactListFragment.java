package com.zhixinyisheng.user.ui.IM.ui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.and.yzy.frame.application.Constant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.MyEaseContactListFgt;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/11/10  9:56
 * <p/>
 * 类说明:
 */
public class MyContactListFragment extends MyEaseContactListFgt {

    //好友列表集合

    public static List<EaseUser> list = new ArrayList<>();

    //选择的好友集合
    public static List<EaseUser> list_check = new ArrayList<>();


    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        super.initView();
        list = DemoDBManager.getInstance().getContactList_list();
    }

    @Override
    public void refresh() {
        Log.e("SeleteFriendsFgt", "refresh");
        Map<String, EaseUser> m = new Hashtable<String, EaseUser>();
        for (EaseUser eu:list) {
            m.put(eu.getUsername(),eu);
        }
        setContactsMap(m);
        super.refresh();

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {
        super.setUpView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser)listView.getItemAtPosition(position);
                if (Constant.GZCKSOS==2){
                    if (user.getPayedUserID()==2){
                        Toast.makeText(getActivity(), R.string.ninbunengshezhinindehuanzhe, Toast.LENGTH_SHORT).show();
                    }else{
                        if (user.getCanSosSet()==0){
                            Toast.makeText(getActivity(), R.string.nindedingdanguoqi, Toast.LENGTH_SHORT).show();

                        }
                    }
                }

//                if (user.getAgreeFlag()==0&&Constant.GZCKSOS ==2){
//                    Toast.makeText(getActivity(), "对方没有加您为好友,您不能设置为SOS发送对象!", Toast.LENGTH_SHORT).show();
//                }
                for (int i = 0; i < list.size(); i++) {
                    if (user.getUsername().equals(list.get(i).getUsername())) {

                        if (Constant.GZCKSOS == 0) {
                            if (list.get(i).getNo().equals("1")) {
                                list.get(i).setNo("0");
                            } else {
                                list.get(i).setNo("1");
                            }
                        } else if (Constant.GZCKSOS == 1) {
                            if (list.get(i).getDatas().equals("1")) {
                                list.get(i).setDatas("0");
                            } else {
                                list.get(i).setDatas("1");
                            }
                        }else if (Constant.GZCKSOS == 2) {
                            if (list.get(i).getSos().equals("1")) {
                                list.get(i).setSos("0");
                            } else {
                                list.get(i).setSos("1");
                            }
                        }
                    }
                }

                refresh();

            }
        });

    }


}