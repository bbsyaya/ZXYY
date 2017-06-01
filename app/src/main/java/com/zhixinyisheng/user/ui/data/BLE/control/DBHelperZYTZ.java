package com.zhixinyisheng.user.ui.data.BLE.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperZYTZ extends SQLiteOpenHelper {
	public DBHelperZYTZ(Context context, String name, CursorFactory factory,
						int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table zytz" +
				"(_id integer primary key autoincrement," +
				"content text," +
				"patient_id text)";
		//执行sql语句
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
