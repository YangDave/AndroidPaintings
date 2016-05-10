package com.vgpt.androidpaintings.application;

import android.app.Application;
import android.content.Context;

import com.vgpt.androidpaintings.entity.EditPaintingInfo;

/**
 * @author yuandunlong
 *
 */
public class MyApplication extends Application{


	public static Context applicationContext;

	private int user_id ;
	private String username ;
//	保存MainActivity的fragment索引
	private int fragIndex = 0;
	
	private EditPaintingInfo editPaintingInfo = null;
	
	public EditPaintingInfo getEditPaintingInfo() {
		return editPaintingInfo;
	}
	public void setEditPaintingInfo(EditPaintingInfo editPaintingInfo) {
		this.editPaintingInfo = editPaintingInfo;
	}
	public int getFragIndex() {
		return fragIndex;
	}
	public void setFragIndex(int fragIndex) {
		this.fragIndex = fragIndex;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public static interface Constant{

		int env=1;
	}

	@Override
	public void onCreate(){

		super.onCreate();
		applicationContext=getApplicationContext();

	}

}
