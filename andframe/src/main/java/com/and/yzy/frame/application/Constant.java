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
package com.and.yzy.frame.application;

public class Constant extends EaseConstant {
	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chatroom";
	public static final String ACCOUNT_REMOVED = "account_removed";
	public static final String ACCOUNT_CONFLICT = "conflict";
	public static final String CHAT_ROBOT = "item_robots";
	public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
	public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
	public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

	//环信注册和登录密码(统一为123456)
	public static final String IM_PASSWORD = "123456";

	/**
	 * 跳转好友详情和好友聊天用的ID,NAME
	 */
	public static String JUMP_FRIEND_ID = "";
	public static String JUMP_FRIEND_NAME = "";

	//接受好友申请广播
	public final static String MYACTION_ACCEPTFRI = "com.zhixinyisheng.user.myaction_acceptfri";

	/**
	 * 我的广播动作
	 */

	//我的关注广播
	public static final String MYBROADCASTACTION_GZ = "com.zhixinyisheng.mybroadcastaction_gz";
	public static final String MYBROADCASTACTION_QUGZ = "com.zhixinyisheng.mybroadcastaction_qxgz";
	//mainaty中同意加好友的广播
	public static final String MYBROADCASTACTION_MAINATY_TYJHY = "com.zhixinyisheng.mybroadcastaction_mainaty_tyjhy";

	//更新主界面小红点的广播和推送刷新系统消息界面
	public static final String MYBROADCASTACTION_XIAOHONGDIAN = "com.zhixinyisheng.mybroadcastaction_xiaohongdian";
	//广播回到好友界面更新字母条
	public static final String MYBROADCASTACTION_MYSIDEBAR= "a";

	public static final String MYRECEIVER_TYPE13="action_type_13";//极光推送 type==13
	public static final String MAIN_BOTTOM_VISIABLE="main_bottom_visiable";//底栏显示
	public static final String MAIN_BOTTOM_INVISIABLE="main_bottom_invisiable";//底栏消失
	public static final String MESSURE_STOP="action_messure_stop";//心率血压测量过程中停止
	public static final String MESSURE_STOP_BLOOD="action_messure_stop_blood";//心率血压测量过程中停止(血压)
	public static final String XINLV_EMPTY = "xinlv_empty";//心率为空时
	public static final String MYRECEIVER_TYPE_10 = "action_type_10";//极光推送 type==10
	public static final String LAB_NCG_COMMIT = "action_lab_ncg_commit";//化验单的提交
	public static final String LAB_NCG_TIME = "action_lab_ncg_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_XCG_COMMIT = "action_lab_xcg_commit";//化验单的提交
	public static final String LAB_XCG_TIME = "action_lab_xcg_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_NDB_COMMIT = "action_lab_ndb_commit";//化验单的提交
	public static final String LAB_NDB_TIME = "action_lab_ndb_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_MYQDB_COMMIT = "action_lab_myqdb_commit";//化验单的提交
	public static final String LAB_MYQDB_TIME = "action_lab_myqdb_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_GGN_COMMIT = "action_lab_ggn_commit";//化验单的提交
	public static final String LAB_GGN_TIME = "action_lab_ggn_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_SGN_COMMIT = "action_lab_sgn_commit";//化验单的提交
	public static final String LAB_SGN_TIME = "action_lab_sgn_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_SZCC_COMMIT = "action_lab_szcc_commit";//化验单的提交
	public static final String LAB_SZCC_TIME = "action_lab_szcc_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_DJZ_COMMIT = "action_lab_djz_commit";//化验单的提交
	public static final String LAB_DJZ_TIME = "action_lab_djz_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_YGWX_COMMIT = "action_lab_ygwx_commit";//化验单的提交
	public static final String LAB_YGWX_TIME = "action_lab_ygwx_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_KTXL_COMMIT = "action_lab_ktxl_commit";//化验单的提交
	public static final String LAB_KTXL_TIME = "action_lab_ktxl_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_NX_COMMIT = "action_nx_commit";//化验单的提交
	public static final String LAB_NX_TIME = "action_nx_time";//化验单手写的时间选择器的确定按钮

	public static final String LAB_BDJC_COMMIT = "action_bdjc_commit";//化验单的提交
	public static final String LAB_BDJC_TIME = "action_bdjc_time";//化验单手写的时间选择器的确定按钮
	public static final String LAB_INTER_ADAPTER = "lab_inter_adapter";//为了折线图

	public static final String LAB_CROP = "lab_crop";//为了图文识别
	public static final String LAB_CROP_FIRST = "lab_crop_first";//为了图文识别
	public static final String LAB_CROP_SECOND = "lab_crop_second";//为了图文识别
	public static final String LAB_CROP_THIRD= "lab_crop_third";//为了图文识别
	/**
	 * 关注,查看,sos
	 * 0    1    2
	 */
	public static int GZCKSOS = 0;
	public static int isHelpOpen = 0;



}
