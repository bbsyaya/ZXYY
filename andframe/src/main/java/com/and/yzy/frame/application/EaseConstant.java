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

public class EaseConstant {
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
    
    public static final String MESSAGE_ATTR_IS_BIG_EXPRESSION = "em_is_big_expression";
    public static final String MESSAGE_ATTR_EXPRESSION_ID = "em_expression_id";
    
    public static final String MESSAGE_ATTR_AT_MSG = "em_at_list";
    public static final String MESSAGE_ATTR_VALUE_AT_MSG_ALL = "ALL";

    //分享是用到的参数tag
    public static final String MESSAGE_ATTR_IS_SHARE_CALL = "message_attr_is_share_call";
    public static final String MESSAGE_SHARE_HEADER_URL = "shareHeaderUrl";
    public static final String MESSAGE_SHARE_ID = "shareID";
    public static final String MESSAGE_SHARE_NAME = "shareName";
    public static final String MESSAGE_SECTION = "section";
    public static final String MESSAGE_TITLE = "title";
    public static final String MESSAGE_GOODAT = "goodAt";
    public static final String MESSAGE_SELF_USERID = "message_self_userid";





    public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;
    public static final int CHATTYPE_CHATROOM = 3;
    
    public static final String EXTRA_CHAT_TYPE = "chatType";
    public static final String EXTRA_USER_ID = "userId";
    public static final String EXTRA_REMARKNAME = "remarkname";

    public static final String EXTRA_DOCTOR_INFO = "doctorInfo";
    //购买的数量
//    public static final String EXTRA_BUY_NUM = "buyNum";

    public static final int CONVERSATION_TYPE_APPLY=1;//好友申请
    public static final int CONVERSATION_TYPE_SOS=2;//SOS呼救
    public static final int CONVERSATION_TYPE_MESSAGE=3;//好友消息推送
    public static final int CONVERSATION_TYPE_IMPROTENCE_NOTICE=4;//重要通知
    public static final int CONVERSATION_TYPE_CHECK_HEALTHY_DATA=5;//申请查看好友健康数据
    public static final int CONVERSATION_TYPE_ABNORMAL_DATA=6;//异常数据推送
    public static final int CONVERSATION_TYPE_CHECK_SUCCESS=7;//知心医生后台消息推送--医生审核通过
    public static final int CONVERSATION_TYPE_CHECK_FAILED=8;//后台消息推送--医生审核不通过
    public static final int CONVERSATION_TYPE_ORDER_MESSAGR=9;//订单消息推送
}
