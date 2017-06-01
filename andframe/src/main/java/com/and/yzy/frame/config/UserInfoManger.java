package com.and.yzy.frame.config;


import com.and.yzy.frame.util.SPUtils;


public abstract class UserInfoManger {

	/**
	 * 获得登陆状态
	 */
	public static boolean isLogin() {
		SPUtils spUtils = new SPUtils("userConfig");
		return (Boolean) spUtils.get("isLogin", false);
	}

	/**
	 * 设置登陆状态
	 */
	public static void setIsLogin( boolean b) {
		SPUtils spUtils = new SPUtils("userConfig");
		spUtils.put("isLogin", b);
	}

	
		
	

}
