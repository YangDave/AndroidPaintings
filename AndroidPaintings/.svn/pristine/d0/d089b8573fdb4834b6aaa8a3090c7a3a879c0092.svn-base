package com.vgpt.androidpaintings.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.vgpt.androidpaintings.application.MyApplication;

/**
 * @author yuandunlong
 * @createAt 2014-4-25
 */
public class JDBCTemplate {

	private DBHelper dbHelper;

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	private JDBCTemplate() {
		dbHelper = new DBHelper(MyApplication.applicationContext);

	}

	private static JDBCTemplate instance = null;

	public static JDBCTemplate getInstance() {

		if (instance == null) {

			synchronized (JDBCTemplate.class) {
				if (instance == null) {

					instance = new JDBCTemplate();
				}

			}
		}
		return instance;
	}

	public void execute(String sql, Object... params) {

		dbHelper.getWritableDatabase().execSQL(sql, params);

	}

	/**
	 * 
	 * @param sql
	 * @param mapper
	 * @param params
	 * @return
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> mapper,
			String... params) {

		Cursor c = dbHelper.getReadableDatabase().rawQuery(sql, params);
		List<T> ret = new ArrayList<T>();
		int i = 0;
		if (c.moveToFirst()) {

			do {

				try {

					ret.add(mapper.forEachRow(c, i));
					i++;

				} catch (Exception e) {

				}

			} while (c.moveToNext());
		}

		return ret;

	}

	public <T> T queryForObject(String sql, RowMapper<T> mapper,
			String... params) {
		Cursor c = dbHelper.getReadableDatabase().rawQuery(sql, params);
		T t = null;
		if (c.moveToFirst()) {
			t = mapper.forEachRow(c, 0);

		}

		return t;
	}

	public <T> T queryForValue(String sql, ValueResolver<T> vr,
			String... params) {
		Cursor c = dbHelper.getReadableDatabase().rawQuery(sql, params);

		T t = null;

		if (c.moveToFirst()) {

			t = vr.valueResolve(c, 0);
		}
		return t;
	}

	public void delete(String sql, String... params) {

		execute(sql, params);
	}

}
