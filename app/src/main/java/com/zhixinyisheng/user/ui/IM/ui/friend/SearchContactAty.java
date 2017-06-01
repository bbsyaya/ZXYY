package com.zhixinyisheng.user.ui.IM.ui.friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.application.Constant;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.SearchContactAdapter;
import com.zhixinyisheng.user.config.ActivityRequestCode;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.db.DemoDBManager;
import com.zhixinyisheng.user.ui.IM.ui.FriendsDetialAty;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 搜索联系人
 * Created by 焕焕 on 2017/4/19.
 */

public class SearchContactAty extends BaseAty implements TextWatcher, AdapterView.OnItemClickListener {
    @Bind(R.id.et_contact_search)
    EditText etContactSearch;
    @Bind(R.id.lv_search)
    ListView lvSearch;
    private List<EaseUser> list_e;
    private List<EaseUser> list_searched = new ArrayList<>();
    private SearchContactAdapter searchContactAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.aty_search_contact;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        popUpSoftKeyboard();//弹出软键盘
        etContactSearch.addTextChangedListener(this);
        list_e = DemoDBManager.getInstance().getContactList_list();
        searchContactAdapter = new SearchContactAdapter(this, list_searched, R.layout.ease_row_contact);
        lvSearch.setAdapter(searchContactAdapter);
        lvSearch.setOnItemClickListener(this);
    }

    private void popUpSoftKeyboard() {
        etContactSearch.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) etContactSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(etContactSearch, 0);
                           }
                       },
                300);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        list_searched.clear();
        for (EaseUser eu : list_e) {
            if (eu.getNick().contains(s.toString()) && !TextUtils.isEmpty(s.toString())) {
                list_searched.add(eu);
            }
        }
        searchContactAdapter.setDatas(list_searched);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EaseUser easeUser = list_searched.get(position);
        String username = easeUser.getUsername(),
                remarkName = easeUser.getNick();
        int payedUserId = easeUser.getPayedUserID();
        Constant.JUMP_FRIEND_ID = username;
        Constant.JUMP_FRIEND_NAME = remarkName;
        if (payedUserId == 1) {
            Intent intent = new Intent(this, DoctorPageActivity.class);
            intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, username);
            startActivity(intent);
            activityAnimation();
            finish();
        } else {
            BaseApplication.userId = username;
            startActivityForResult(FriendsDetialAty.class, null, ActivityRequestCode.FRIENDS_DETAIL);
            finish();
        }
    }


    @OnClick({R.id.tv_search_cancel, R.id.ll_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cancel:
            case R.id.ll_search:
                finish();
                break;
        }
    }
}
