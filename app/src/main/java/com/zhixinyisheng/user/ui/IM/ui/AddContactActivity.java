/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhixinyisheng.user.ui.IM.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.IM.domain.FindFrinedEntity;
import com.zhixinyisheng.user.util.LanguageUtil;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AddContactActivity extends BaseAty{
	private EditText editText;
	private RelativeLayout searchedUserLayout;
	private TextView nameText;
	private Button searchBtn;
	private ImageView iv_ff;

	//搜索道德好友对象
	FindFrinedEntity ff = null;



	@Override
	public int getLayoutId() {
		return R.layout.em_activity_add_contact;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		super.initData(savedInstanceState);
		TextView mTextView = (TextView) findViewById(R.id.add_list_friends);

		editText = (EditText) findViewById(R.id.edit_note);
		String strAdd = getResources().getString(R.string.add_friend);
		mTextView.setText(strAdd);
		String strUserName = getResources().getString(R.string.user_name);
		editText.setHint(strUserName);
		searchedUserLayout = (RelativeLayout) findViewById(R.id.ll_user);
		iv_ff = (ImageView) findViewById(R.id.avatar);
		nameText = (TextView) findViewById(R.id.name);
		searchBtn = (Button) findViewById(R.id.search);
	}




	/**
	 * search contact
	 * @param v
	 */
	public void searchContact(View v) {
		final String name = editText.getText().toString();
		String saveText = searchBtn.getText().toString();

		if (getString(R.string.button_search).equals(saveText)) {
			if(TextUtils.isEmpty(name)) {
				new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
				return;
			}
			
			// TODO you can search the user from your app server here.
			if (LanguageUtil.judgeLanguage().equals("zh")){
				doHttp(RetrofitUtils.createApi(IMUrl.class).findFrined(UserManager.getUserInfo().getPhone(),
						UserManager.getUserInfo().getSecret(),userId,name, name,LanguageUtil.judgeLanguage()),0);
			}else{
				doHttp(RetrofitUtils.createApi(IMUrl.class).findFrined(UserManager.getUserInfo().getPhone(),
						UserManager.getUserInfo().getSecret(),userId,name,name, LanguageUtil.judgeLanguage()),0);
			}

		}
	}	
	
	/**
	 *  add contact
	 * @param view
	 */
	public void addContact(View view){
		Log.e("环信当前登录人ID", EMClient.getInstance().getCurrentUser());
		if(EMClient.getInstance().getCurrentUser().equals(ff.getDb().getUserId())){
			new EaseAlertDialog(this, R.string.not_add_myself).show();
			return;
		}
		
		if(DemoHelper.getInstance().getContactList().containsKey(ff.getDb().getUserId())){
		    //环信中判断是否拉黑

			new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
			return;
		}

		showLoadingContentDialog();

		Log.e("aa", UserManager.getUserInfo().getPhone() + "@" + UserManager.getUserInfo().getSecret() + "@" + UserManager.getUserInfo().getUserId() + "@" + ff.getDb().getUserId());



		
//		new Thread(new Runnable() {
//			public void run() {
//
//				try {
//					//demo use a hardcode reason here, you need let user to input if you like
//					String s = getResources().getString(R.string.Add_a_friend);
//					EMClient.getInstance().contactManager().addContact(toAddUsername, s);
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							String s1 = getResources().getString(R.string.send_successful);
//							Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
//						}
//					});
//				} catch (final Exception e) {
//					runOnUiThread(new Runnable() {
//						public void run() {
//							progressDialog.dismiss();
//							String s2 = getResources().getString(R.string.Request_add_buddy_failure);
//							Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
//						}
//					});
//				}
//			}
//		}).start();
	}

	@Override
	public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
		super.onSuccess(result, call, response, what);
		if (what == 0) {
			ff = JSON.parseObject(result,FindFrinedEntity.class);
			//show the userame and add button if user exist
			searchedUserLayout.setVisibility(View.VISIBLE);
			nameText.setText(ff.getDb().getName());

			Glide.with(this).load(ff.getDb().getHeadUrl())
					.placeholder(R.mipmap.ic_launcher2)//占位图
					.error(R.mipmap.ic_launcher2)//加载错误图
					.bitmapTransform(new CropCircleTransformation(AddContactActivity.this))//裁剪圆形
					.into(iv_ff);

		} else if (what == 1) {
			showToast(getString(R.string.tianjiahaoyouchenggong));
		}


	}

	@Override
	public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
		super.onFailure(result, call, response, what);

		if (what == 0) {
			showToast(getString(R.string.fuwuqiyichang));
		} else if (what == 1) {
			ff = JSON.parseObject(result,FindFrinedEntity.class);
			if (ff.getResult().equals("1001")) {
				showToast(getString(R.string.haoyouyicunzai));
			} else {
				showToast(getString(R.string.fuwuqiyichang));
			}


		}

	}

	public void back(View v) {
		finish();
	}
}
