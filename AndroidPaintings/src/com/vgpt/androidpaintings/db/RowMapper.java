package com.vgpt.androidpaintings.db;

import android.database.Cursor;

public interface RowMapper<T> {
	
	public T forEachRow(final Cursor c ,final int row);

}
