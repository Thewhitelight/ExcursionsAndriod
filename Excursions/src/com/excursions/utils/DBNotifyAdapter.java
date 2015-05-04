package com.excursions.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBNotifyAdapter {
	public static final String KEY_TITLE = "title";// 通知标题
	public static final String KEY_CONTENT = "content";// 通知内容
	public static final String KEY_TIME = "time";// 通知时间
	public static final String DATABASE_NAME = "Notify";
	public static final String DATABASE_TABLE = "Notify";
	public static final int VERSION = 1;
	public static final String DATABASE_CREATE = "create table Notify(id integer primary key autoincrement,title text,content text,time text)";
	private DBHelper DBHelper;
	private SQLiteDatabase db;

	public DBNotifyAdapter(Context context) {
		// TODO Auto-generated constructor stub
		DBHelper = new DBHelper(context);
	}

	public DBNotifyAdapter open() throws Exception {
		// TODO Auto-generated method stub
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		// TODO Auto-generated method stub
		DBHelper.close();
	}

	/**
	 * 插入通知
	 * 
	 * @param title
	 * @param content
	 * @param time
	 */
	public void insertNotify(String title, String content, String time) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_CONTENT, content);
		cv.put(KEY_TIME, time);
		db.insert(DATABASE_TABLE, null, cv);
	}

	/**
	 * 查询全部通知
	 * 
	 * @return
	 */
	public Cursor selectAllNotify() {
		// TODO Auto-generated method stub
		return db.query(DATABASE_TABLE, new String[] {}, null, null, null,
				null, "id desc");

	}

	/**
	 * 查询一条通知
	 * 
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public Cursor selectNotify(String title) throws Exception {
		// TODO Auto-generated method stub
		Cursor cursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_TITLE, KEY_CONTENT, KEY_TIME }, KEY_TITLE + "=" + "'"
				+ title + "'", null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}
}
