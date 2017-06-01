package com.zhixinyisheng.user.ui.data.BLE.control;

public class AlarmEN {
	int hour;
	int minute;
	String str1,str2,str3,str4,str5,str6,str7,str_on;
	
	public AlarmEN() {
		super();
	}

	public AlarmEN(int hour, int minute, String str1, String str2, String str3,
				   String str4, String str5, String str6, String str7, String str_on) {
		super();
		this.hour = hour;
		this.minute = minute;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.str4 = str4;
		this.str5 = str5;
		this.str6 = str6;
		this.str7 = str7;
		this.str_on = str_on;
		
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public String getStr5() {
		return str5;
	}

	public void setStr5(String str5) {
		this.str5 = str5;
	}

	public String getStr6() {
		return str6;
	}

	public void setStr6(String str6) {
		this.str6 = str6;
	}

	public String getStr7() {
		return str7;
	}

	public void setStr7(String str7) {
		this.str7 = str7;
	}

	public String getStr_on() {
		return str_on;
	}

	public void setStr_on(String str_on) {
		this.str_on = str_on;
	}

	@Override
	public String toString() {
		return "AlarmEN [hour=" + hour + ", minute=" + minute + ", str1="
				+ str1 + ", str2=" + str2 + ", str3=" + str3 + ", str4=" + str4
				+ ", str5=" + str5 + ", str6=" + str6 + ", str7=" + str7
				+ ", str_on=" + str_on + "]";
	}

	
	
	
	
	
}
