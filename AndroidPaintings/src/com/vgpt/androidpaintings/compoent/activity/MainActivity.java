package com.vgpt.androidpaintings.compoent.activity;


import java.lang.reflect.Field;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.ExitApplication;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.compoent.fragment.FragmentAssociation;
import com.vgpt.androidpaintings.compoent.fragment.FragmentAuction;
import com.vgpt.androidpaintings.compoent.fragment.FragmentAuction.OnBackListener;
import com.vgpt.androidpaintings.compoent.fragment.FragmentMore;
import com.vgpt.androidpaintings.compoent.fragment.FragmentPainting;
import com.vgpt.androidpaintings.interfacepackage.MyActivityInterface;
import com.vgpt.androidpaintings.utils.DeviceUtil;


public class MainActivity extends FragmentActivity implements OnBackListener,MyActivityInterface
{
	final int QUIT=50;
	final int UPLOAD=16;
	final int FLIP_DISTANCE=50;
	public static int user_id=0;
	SharedPreferences preference;
	SharedPreferences.Editor editor;
	public static String username;



	SharedPreferences signInRecord;
	SharedPreferences.Editor signInRecordEditor;

	// 瀹氫箟FragmentTabHost瀵硅薄
	private FragmentTabHost mTabHost;
	private RadioGroup mTabRg;

	@SuppressWarnings("rawtypes")
	private final Class[] fragments = { FragmentPainting.class, FragmentAuction.class,
		FragmentAssociation.class, FragmentMore.class };




	@Override
	protected void onCreate(Bundle savedInstanceState) {

		DeviceUtil.bePortrait(MainActivity.this);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		MyApplication ma = (MyApplication)getApplicationContext();
		user_id = ma.getUser_id();
		username = ma.getUsername();

		findView();

		ExitApplication.getInstance().addActivity(this);

		prepareData();


	}

	public void findView() {
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// 寰楀埌fragment鐨勪釜鏁�
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			// 涓烘瘡涓�釜Tab鎸夐挳璁剧疆鍥炬爣銆佹枃瀛楀拰鍐呭
			TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			// 灏員ab鎸夐挳娣诲姞杩汿ab閫夐」鍗′腑
			mTabHost.addTab(tabSpec, fragments[i], null);
		}


		mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		mTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				MyApplication ma = (MyApplication)getApplicationContext();
				switch (checkedId) {
				case R.id.tab_rb_1:
					mTabHost.setCurrentTab(0);
					ma.setFragIndex(0);
					break;
				case R.id.tab_rb_2:
					mTabHost.setCurrentTab(1);
					ma.setFragIndex(1);

					break;
				case R.id.tab_rb_3:

					mTabHost.setCurrentTab(2);
					ma.setFragIndex(2);
					break;
				case R.id.tab_rb_4:

					mTabHost.setCurrentTab(3);
					ma.setFragIndex(3);
					break;

				default:
					break;
				}
			}
		});


		mTabHost.setCurrentTab(0);
	}





	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 * activity重新启动并加载对应的该加载的页面
	 */
	@Override
	protected void onRestart() {

		MyApplication ma = (MyApplication)getApplicationContext();
		mTabHost.setCurrentTab(ma.getFragIndex());
		super.onRestart();
	}



	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 * activity返回foreground 加载对应页面
	 */
	@Override
	protected void onResume() {

		MyApplication ma = (MyApplication)getApplicationContext();
		mTabHost.setCurrentTab(ma.getFragIndex());
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		
		exitAction();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int id = item.getItemId();
		switch(id){
		case R.id.action_exit:
			this.finish();
			System.exit(0);
			ExitApplication.getInstance().exit();
			break;
		case R.id.action_settings:
			Toast toast=Toast.makeText(MainActivity.this, "setting is work", Toast.LENGTH_LONG);
			toast.show();
			break;

		}
		return super.onMenuItemSelected(featureId, item);

	}

	private long a = 0;
	private void exitAction() {
		
		if(a == 0){
			Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
			a = System.currentTimeMillis();
		}else if(System.currentTimeMillis() - a > 2000){
			Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
		}else{
			this.finish();
			System.exit(0);
			ExitApplication.getInstance().exit();
		}
		
	}


	@Override
	public void addListener() {

	}

	@Override
	public void showContent() {

	}

	@Override
	public void prepareData() {
		/*
		 * 解决溢出菜单不显示问题
		 */

		try {
			ViewConfiguration mconfig = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (Exception ex) {
		}

	}

	@Override
	public void backEvent() {
		// TODO Auto-generated method stub
		
	}


}
