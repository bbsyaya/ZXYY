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
package com.hyphenate.easeui.domain;

import com.hyphenate.chat.EMContact;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.io.Serializable;

public class EaseUser extends EMContact implements Serializable{
	private String sortLetters;
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

    
    /**
     * initial letter for nickname
     */
	protected String initialLetter;
	/**
	 * avatar of the user
	 */
	protected String avatar;
	/**
	 * 是否是我的患者
	 * */
	int isPatient;//0不是患者  1是患者
	/**
	 * 是否在我的医生分组
	 * */
	int isMyDoctor;//0不是  1是
	/**
	 * 是否关注
	 */
	String no ; //0未关注 1关注

	/**
	 * 是否sos
	 */
	String sos ; //0未关注 1关注

	/**
	 * 是否查看健康数据
	 */
	String datas ; //0未关注 1关注


	int agreeFlag;//0是单向好友 ， 1是双向好友

	int payedUserID;
	int canSosSet;

	public EaseUser(){
	}
	public int getPayedUserID(){
		return payedUserID;
	}
	public void setPayedUserID(int payedUserID){
		this.payedUserID = payedUserID;
	}
	public int getCanSosSet(){
		return canSosSet;
	}
	public void setCanSosSet(int canSosSet){
		this.canSosSet = canSosSet;
	}
	public int getIsMyDoctor(){
		return isMyDoctor;
	}
	public void setIsMyDoctor(int isMyDoctor){
		this.isMyDoctor = isMyDoctor;
	}
	public int getIsPatient(){
		return isPatient;
	}
	public  void setIsPatient(int isPatient){
		this.isPatient = isPatient;
	}
	public int getAgreeFlag() {
		return agreeFlag;
	}

	public void setAgreeFlag(int agreeFlag) {
		this.agreeFlag = agreeFlag;
	}

	public EaseUser(String username){
	    this.username = username;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSos() {
		return sos;
	}

	public void setSos(String sos) {
		this.sos = sos;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public String getInitialLetter() {
	    if(initialLetter == null){
            EaseCommonUtils.setUserInitialLetter(this);
        }
		return initialLetter;
	}

	public void setInitialLetter(String initialLetter) {
		this.initialLetter = initialLetter;
	}


	public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof EaseUser)) {
			return false;
		}
		return getUsername().equals(((EaseUser) o).getUsername());
	}

	@Override
	public String toString() {
		return nick == null ? username : nick;
	}
}
