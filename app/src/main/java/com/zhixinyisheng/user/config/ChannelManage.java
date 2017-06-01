package com.zhixinyisheng.user.config;

import android.database.SQLException;
import android.util.Log;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.dao.ChannelDao;
import com.zhixinyisheng.user.dao.SQLHelper;
import com.zhixinyisheng.user.domain.ChannelItem;
import com.zhixinyisheng.user.ui.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChannelManage {
	public static ChannelManage channelManage;
	/**
	 * 默认的用户选择频道列表
	 * */
	public static List<ChannelItem> defaultUserChannels;
	/**
	 * 默认的其他频道列表
	 * */
	public static List<ChannelItem> defaultOtherChannels;
	private ChannelDao channelDao;
	/** 判断数据库中是否存在用户数据 */
	private boolean userExist = false;
	static {
		defaultUserChannels = new ArrayList<ChannelItem>();
		defaultOtherChannels = new ArrayList<ChannelItem>();
		defaultUserChannels.add(new ChannelItem(1, MyApplication.getApp().getResources().getString(R.string.home_heartRate), 1, 1));
		defaultUserChannels.add(new ChannelItem(2, MyApplication.getApp().getResources().getString(R.string.home_bloodPressure), 2, 1));
		defaultUserChannels.add(new ChannelItem(3, MyApplication.getApp().getResources().getString(R.string.home_lungCapacity), 3, 1));
		defaultUserChannels.add(new ChannelItem(4, MyApplication.getApp().getResources().getString(R.string.home_tongue), 4, 1));
		defaultUserChannels.add(new ChannelItem(5, MyApplication.getApp().getResources().getString(R.string.home_sleep), 5, 1));
		defaultUserChannels.add(new ChannelItem(6, MyApplication.getApp().getResources().getString(R.string.home_bmi), 6, 1));
		defaultUserChannels.add(new ChannelItem(7, MyApplication.getApp().getResources().getString(R.string.home_bloodSuger), 7, 1));
		defaultUserChannels.add(new ChannelItem(8, MyApplication.getApp().getResources().getString(R.string.home_temperature), 8, 1));
		defaultUserChannels.add(new ChannelItem(9, MyApplication.getApp().getResources().getString(R.string.home_steps), 9, 1));
		defaultUserChannels.add(new ChannelItem(10, MyApplication.getApp().getResources().getString(R.string.home_testSheet), 10, 1));

//		defaultUserChannels.add(new ChannelItem(10, "尿常规", 1, 0));
//		defaultUserChannels.add(new ChannelItem(11, "血常规", 2, 0));
//		defaultUserChannels.add(new ChannelItem(12, "尿蛋白定量", 3, 0));
//		defaultUserChannels.add(new ChannelItem(13, "免疫球蛋白", 4, 0));
//		defaultUserChannels.add(new ChannelItem(14, "肝功能", 5, 0));
//		defaultUserChannels.add(new ChannelItem(15, "肾功能", 6, 0));
//		defaultUserChannels.add(new ChannelItem(16, "肾脏彩超", 7, 0));
//		defaultOtherChannels.add(new ChannelItem(17, "电解质", 8, 0));
//		defaultOtherChannels.add(new ChannelItem(18, "乙肝五项", 9, 0));
//		defaultOtherChannels.add(new ChannelItem(19, "抗体系列", 10, 0));
//		defaultOtherChannels.add(new ChannelItem(20, "凝血",11, 0));
//		defaultOtherChannels.add(new ChannelItem(21, "病毒检测", 12, 0));
//		defaultOtherChannels.add(new ChannelItem(22, "化验单", 13, 0));

	}

	private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
		if (channelDao == null)
			channelDao = new ChannelDao(paramDBHelper.getContext());
		// NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
		return;
	}

	/**
	 * 初始化频道管理类
	 * @param
	 * @throws SQLException
	 *
	 */
	public static ChannelManage getManage(SQLHelper dbHelper)throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage(dbHelper);
		return channelManage;
	}

	/**
	 * 清除所有的频道
	 */
	public void deleteAllChannel() {
		channelDao.clearFeedTable();
	}
	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
	 */
	public List<ChannelItem> getUserChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItem> list = new ArrayList<ChannelItem>();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return defaultUserChannels;
	}

	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelItem> getOtherChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?" ,new String[] { "0" });
		List<ChannelItem> list = new ArrayList<ChannelItem>();
		if (cacheList != null && !((List) cacheList).isEmpty()){
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate= new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		if(userExist){
			return list;
		}
		cacheList = defaultOtherChannels;
		return (List<ChannelItem>) cacheList;
	}

	/**
	 * 保存用户频道到数据库
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItem> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) userList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			channelDao.addCache(channelItem);
		}
	}

	/**
	 * 保存其他频道到数据库
	 * @param otherList
	 */
	public void saveOtherChannel(List<ChannelItem> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));
			channelDao.addCache(channelItem);
		}
	}

	/**
	 * 初始化数据库内的频道数据
	 */
	private void initDefaultChannel(){
		Log.d("deleteAll", "deleteAll");
		deleteAllChannel();
		saveUserChannel(defaultUserChannels);
		saveOtherChannel(defaultOtherChannels);
	}
}
