package com.vgpt.androidpaintings.compoent.activity.association;

import android.app.Activity;
import android.os.Bundle;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.ExitApplication;

public class ShowMemberInfoActivity extends Activity{
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_showmemberinfo);
		ExitApplication.getInstance().addActivity(this);
	}

}
