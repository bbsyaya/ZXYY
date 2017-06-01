package com.zhixinyisheng.user.ui.data.BLE.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperAlarm extends SQLiteOpenHelper {
	/**
	 * 构造有参构造方法
	 * @param context --应用上下文
	 * @param name  ---数据库的名字
	 * @param factory  --游标工厂对象主要是为查询服务
	 * @param version  --数据库的版本号
	 */
	public DBHelperAlarm(Context context, String name, CursorFactory factory,
						 int version) {
		super(context, name, factory, version);
		
	}
	/**
	 * 在数据库创建的时候回调用此方法--只被调用一次
	 * 数据表的创建
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table alarm" +
				"(_id integer primary key autoincrement," +
				"hour integer," +
				"minute integer," +
				"str1 text," +
				"str2 text," +
				"str3 integer," +
				"str4 text," +
				"str5 text," +
				"str6 text," +
				"str7 text," +
				"str_on text)";
		//执行sql语句
		db.execSQL(sql);

	}
  /**
   * 如果数据库版本发生变化此方法会被调用
   * 如果是表结构发生变化需要删除相应的表，然后重建
   */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
		
		
	}

}
