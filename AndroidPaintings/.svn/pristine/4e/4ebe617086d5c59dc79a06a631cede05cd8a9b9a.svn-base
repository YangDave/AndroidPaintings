package com.vgpt.androidpaintings.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;

	private static final String NAME = "picture.db";

	private static final String PicNameInfoCreateSql = "create table if not exists pic_file_name_info(id integer primary key autoincrement,pic_id, integer ,pic_file_name text,gmt_creat_time integer,gmt_update_time integer)";

	public DBHelper(Context context) {
		super(context, NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(PicNameInfoCreateSql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
