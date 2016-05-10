package com.vgpt.androidpaintings.base;

import android.app.ActionBar;
import android.app.Activity;
import android.view.MenuItem;

import com.vgpt.androidpaintings.application.ExitApplication;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.interfacepackage.MyActivityInterface;
import com.vgpt.androidpaintings.utils.DeviceUtil;

public abstract class MyActivity extends Activity implements MyActivityInterface{
	
	
	protected  int user_id;
	protected String username;
	
	
	
//	添加左上角返回事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == android.R.id.home){

//			执行返回操作
		 super.onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	protected void loadPage(Activity activity){
		
		
		ExitApplication.getInstance().addActivity(activity);
		DeviceUtil.bePortrait(activity);
		
//		显示左上角返回按钮
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
        MyApplication ma = (MyApplication)activity.getApplicationContext();
        
        user_id = ma.getUser_id();
        username = ma.getUsername();
		
		findView();
		
		prepareData();
		
		showContent();
		
		addListener();
		
	}
	



}
