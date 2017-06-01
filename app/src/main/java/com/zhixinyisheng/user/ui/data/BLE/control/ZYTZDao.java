package com.zhixinyisheng.user.ui.data.BLE.control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ZYTZDao {
	private static ZYTZDao dao;
	private DBHelperZYTZ helper;

	private ZYTZDao(Context context) {
		// 使用helper类创建数据库
		helper = new DBHelperZYTZ(context, "zytz.db", null, 1);
	}

	public static ZYTZDao getInstance(Context context) {
		if (dao == null) {
			dao = new ZYTZDao(context);
		}
		return dao;
	}
	
	public List<String> findAll(String patient_id) {
		List<String> list = new ArrayList<String>();
		String sql = "select content from zytz where patient_id='"+patient_id+"'";
		//获取database对象
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			String content = cursor.getString(cursor.getColumnIndex("content"));
			list.add(content);
		}
		cursor.close();//关闭游标释放资源
		db.close();//关闭连接
		return list;
	}
	
	public void save(String content, String patient_id){
		String sql = "insert into zytz(content,patient_id)values" +
								"('"+content+"','"+patient_id+"')";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);	
	}
	
	
	public void delete(String patient_id){
		String sql = "delete from zytz where patient_id='"+patient_id+"'";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}

	public void update(String content_gx, String content_yl, String patient_id){
		String sql = "update zytz set content = '"+content_gx+"' where patient_id = '"+patient_id+
				"' and content = '"+content_yl+"'";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}
	
	
	
}
