package com.excursions.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	private static final String KEY_ATT = "att";// 景点
	private static final String KEY_BUDGET = "budget";// 预算
	private static final String KEY_TIME = "time";// 时间
	private static final String KEY_TYPE = "type";// 花销类型
	private static final String KEY_TCOST = "tcost";// 花销类型价格
	private static final String KEY_FUND = "fund";// 决算
	private static final String DATABASE_NAME = "Excursions";
	private static final String DATABASE_TABLE = "Excursions";
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_CREATE = "create table Excursions(id integer primary key autoincrement,att text,budget text,time text,type text,tcost text,fund text)";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		DBHelper = new DatabaseHelper(context);
	}

	public DBAdapter open() throws SQLException {
		// TODO Auto-generated method stub
		db = DBHelper.getWritableDatabase();
		return this;

	}

	public void close() {
		// TODO Auto-generated method stub
		DBHelper.close();
	}

	/**
	 * 插入数据
	 * 
	 * @param att
	 * @param budget
	 * @param time
	 * @param type
	 * @param tcost
	 * @return
	 */

	public long insertData(String att, String budget, String time, String type,
			String tcost, String fund) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_ATT, att);
		cv.put(KEY_BUDGET, budget);
		cv.put(KEY_TIME, time);
		cv.put(KEY_TYPE, type);
		cv.put(KEY_TCOST, tcost);
		cv.put(KEY_FUND, fund);
		return db.insert(DATABASE_TABLE, null, cv);
	}

	/**
	 * 删除数据
	 * 
	 * @param att
	 * @return
	 */
	public boolean deleteData(String att) {
		// TODO Auto-generated method stub
		return db.delete(DATABASE_TABLE, KEY_ATT + "='" + att + "'", null) > 0;
	}

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public Cursor getAllData() {
		// TODO Auto-generated method stub
		return db.query(DATABASE_TABLE, new String[] {}, null, null, null,
				null, null);
	}

	/**
	 * 查询一个数据
	 * 
	 * @param att
	 * @return
	 */
	public Cursor getData(String att) throws SQLiteException {
		// TODO Auto-generated method stub\
		Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ATT,
				KEY_BUDGET, KEY_TIME, KEY_TYPE, KEY_TCOST, KEY_FUND }, KEY_ATT
				+ "=" + "'" + att + "'", null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	/**
	 * 更新数据
	 * 
	 * @param att
	 * @param type
	 * @param tcost
	 * @return
	 */
	public boolean updateData(String att, String type, String tcost) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_TYPE, type);
		cv.put(KEY_TCOST, tcost);
		return db.update(DATABASE_TABLE, cv, KEY_TCOST + "=" + tcost, null) > 0;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}
}
