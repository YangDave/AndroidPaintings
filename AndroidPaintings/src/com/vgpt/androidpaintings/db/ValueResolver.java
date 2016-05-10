package com.vgpt.androidpaintings.db;

import android.database.Cursor;

public interface ValueResolver <T> {
	
	
	public T valueResolve(Cursor c,int row);

}
