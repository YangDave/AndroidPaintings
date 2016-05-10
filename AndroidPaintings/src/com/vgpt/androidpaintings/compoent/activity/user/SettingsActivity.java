package com.vgpt.androidpaintings.compoent.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.ExitApplication;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.utils.LogUtils;

public class SettingsActivity extends MyActivity{
	
	Button quitBt;
	
	SharedPreferences signInRecord;
	SharedPreferences.Editor signInRecordEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		loadPage(SettingsActivity.this);
		
	}
	
	

	@Override
	public void findView() {
		
		quitBt = (Button)findViewById(R.id.quitBt);
		
	}

	@Override
	public void prepareData() {
		
		signInRecord=getSharedPreferences("signInRecord", MODE_PRIVATE);
		signInRecordEditor=signInRecord.edit();
		
	}

	@Override
	public void addListener() {
		
		quitBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				signInRecordEditor.putInt("lastSignInUserId", 0);
				signInRecordEditor.apply();
				signInRecordEditor.commit();
				
				MyApplication ma = (MyApplication)getApplicationContext();
				ma.setUser_id(0);
				ma.setUsername(null);
				
				Intent signInIntent=new Intent(SettingsActivity.this,SignActivity.class);
				startActivity(signInIntent);
				ExitApplication.getInstance().exit();
				


			}
		});

		
	}

	@Override
	public void showContent() {
		// TODO Auto-generated method stub
		
	}
	
	

}
