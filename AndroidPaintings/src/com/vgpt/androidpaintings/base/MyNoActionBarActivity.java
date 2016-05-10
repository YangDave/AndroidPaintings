package com.vgpt.androidpaintings.base;

import android.app.Activity;

import com.vgpt.androidpaintings.application.ExitApplication;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.utils.DeviceUtil;

public abstract class MyNoActionBarActivity extends MyActivity{

	@Override
	protected void loadPage(Activity activity) {
		ExitApplication.getInstance().addActivity(activity);
		DeviceUtil.bePortrait(activity);
		
        MyApplication ma = (MyApplication)activity.getApplicationContext();
        
        user_id = ma.getUser_id();
        username = ma.getUsername();
		
		findView();
		
		prepareData();
		
		showContent();
		
		addListener();
	}
	
	
	

}
