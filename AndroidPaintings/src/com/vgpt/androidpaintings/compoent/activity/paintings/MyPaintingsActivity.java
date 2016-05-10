package com.vgpt.androidpaintings.compoent.activity.paintings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.compoent.fragment.MyPaintingItemShowFragment;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.MyPaintingItem;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;

public class MyPaintingsActivity extends FragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	public static int user_id = 0;
	public static String username = null;

	private int currentPosition = 0;
	public final int SIZE = MyUploadActivity.SIZE;

	int page = 1;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private List<MyPaintingItem> items = new ArrayList<MyPaintingItem>();



	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewpager);
		setTitle("我的上传");

		getActionBar().setDisplayHomeAsUpEnabled(true);

		MyApplication ma = (MyApplication)this.getApplicationContext();

		user_id = ma.getUser_id();
		username = ma.getUsername();
		Intent  intent = getIntent();
		currentPosition = intent.getIntExtra("listPosition", 0);
		items = (List<MyPaintingItem>) intent.getSerializableExtra("items");
		if(items.size()%SIZE==0){
			page = items.size()/SIZE + 1;
		}else{
			page = items.size()/SIZE+2;
		}

		loadMyPaintingsList();

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(currentPosition);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		switch(item.getItemId()){
		case R.id.action_settings:
			return true;
		case android.R.id.home:
			super.onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			if(position == items.size()-1){
				loadMyPaintingsList();
			}
			return MyPaintingItemShowFragment.newInstance(items.get(position));
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return items.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private void loadMyPaintingsList(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("page", page);
		map.put("size", SIZE);
		AsycTaskUtil atu = new AsycTaskUtil(this, Constant.Search.FIND_MYPIC, new OnPostInterface() {
			
			@Override
			public void code_1(JSONObject json) {
				
				JSONArray result = json.optJSONArray("result");
				for (int i = 0; i < result.length(); i++) {
					
					int  pic_id = result.optJSONObject(i).optInt("pic_id");
					int on_aucting = result.optJSONObject(i).optInt("on_aucting");
					String name = result.optJSONObject(i).optString("pic_name");
					items.add(new MyPaintingItem(pic_id,name,on_aucting));
					mSectionsPagerAdapter.notifyDataSetChanged();
				}
				
				if(result.length() != 0){
					page++;
				}
				
			}
		});
	
		atu.execute(map);
	}

}
