package com.zhixinyisheng.user.ui.data.BLE.control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * 执行增，删，改，查 将当前数据管理类做成单例模式
 */
public class AlarmDAO {
	private static AlarmDAO dao;
	private DBHelperAlarm helper;

	private AlarmDAO(Context context) {
		// 使用helper类创建数据库
		helper = new DBHelperAlarm(context, "alarm.db", null, 1);
	}

	public static AlarmDAO getInstance(Context context) {
		if (dao == null) {
			dao = new AlarmDAO(context);
		}
		return dao;
	}
	/**
	 * 查询所有数据
	 * @return 表中的数据
	 */
	public List<AlarmEN> findAll() {
		List<AlarmEN> list = new ArrayList<AlarmEN>();
		String sql = "select * from alarm";
		//获取database对象
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			int hour = cursor.getInt(cursor.getColumnIndex("hour"));
			int minute = cursor.getInt(cursor.getColumnIndex("minute"));
			String str1 = cursor.getString(cursor.getColumnIndex("str1"));
			String str2 = cursor.getString(cursor.getColumnIndex("str2"));
			String str3 = cursor.getString(cursor.getColumnIndex("str3"));
			String str4 = cursor.getString(cursor.getColumnIndex("str4"));
			String str5 = cursor.getString(cursor.getColumnIndex("str5"));
			String str6 = cursor.getString(cursor.getColumnIndex("str6"));
			String str7 = cursor.getString(cursor.getColumnIndex("str7"));
			String str_on = cursor.getString(cursor.getColumnIndex("str_on"));
			AlarmEN aen = new AlarmEN(hour, minute, str1, str2, str3, str4,
					str5, str6, str7, str_on);
			list.add(aen);
		}
		cursor.close();//关闭游标释放资源
		db.close();//关闭连接
		return list;
	}
	

	/**
	 * 添加一条数据
	 * 
	 * @param
	 */
	public void save(AlarmEN aen) {
		int hour = aen.getHour();
		int minute = aen.getMinute();
		String str1 = aen.getStr1();
		String str2 = aen.getStr2();
		String str3 = aen.getStr3();
		String str4 = aen.getStr4();
		String str5 = aen.getStr5();
		String str6 = aen.getStr6();
		String str7 = aen.getStr7();
		String str_on = aen.getStr_on();
		String sql = "insert into alarm("
				+ "hour,minute,str1,str2,str3,str4,str5,str6,str7,str_on) values"+ "("
				+ hour + "," + minute + ",'" + str1 + "','"+ str2 + "',"+
				str3 + ",'"+ str4 + "','"+ str5 + "','"+ str6 + "','"+
				str7+"','"+str_on+"')";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}

	

	/**
	 * 删除所有记录
	 * 
	 * @param 
	 *            
	 */
	public void delete() {
		String sql = "delete from alarm";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}
	
	

}
