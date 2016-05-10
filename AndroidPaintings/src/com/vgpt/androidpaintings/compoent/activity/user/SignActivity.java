package com.vgpt.androidpaintings.compoent.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.compoent.fragment.Fragment_sign_in;
import com.vgpt.androidpaintings.compoent.fragment.Fragment_sign_up;
import com.vgpt.androidpaintings.interfacepackage.MyCallBack;
import com.vgpt.androidpaintings.utils.DeviceUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class SignActivity extends Activity implements MyCallBack{

	SharedPreferences signInRecord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DeviceUtil.bePortrait(SignActivity.this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏

		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
				WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏

		signInRecord=getSharedPreferences("signInRecord", MODE_PRIVATE);
		int user_id = signInRecord.getInt("lastSignInUserId", 0);
		if(user_id != 0){
			String username=signInRecord.getString("lastSignInUser", null);
			MyApplication ma = (MyApplication)getApplicationContext();
			ma.setUser_id(user_id);
			ma.setUsername(username);

			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			this.finish();
		}

		setContentView(R.layout.activity_sign);

		if(!isNetworkConnected(this)){
			Toast.makeText(this, "没有连接到网络", Toast.LENGTH_LONG).show();
			LogUtils.v("========没有连接到网络======");
		}

		PackageManager pm = getPackageManager();
		boolean permission = (PackageManager.PERMISSION_GRANTED == 
				pm.checkPermission("android.permission.INTERNET", "com.vgpt.androidpaintings"));
		if (!permission) {
			LogUtils.v("=========没有联网权限==========");
		}

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new Fragment_sign_in()).commit();
		}

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void changeFragment(int layoutId) {

		if(layoutId == R.layout.fragment_sign_in){
			getFragmentManager().beginTransaction()
			.replace(R.id.container, new Fragment_sign_up())
			.addToBackStack(null).commit();
		}else{

			getFragmentManager().beginTransaction()
			.replace(R.id.container, new Fragment_sign_in())
			.addToBackStack(null).commit();
		}

	}

	public boolean isNetworkConnected(Context context) { 
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
					.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
			if (mNetworkInfo != null) { 
				return mNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
	}



}
