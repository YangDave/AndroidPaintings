package com.vgpt.androidpaintings.compoent.activity.auction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.vgpt.androidpaintings.biz.JSONToMap;
import com.vgpt.androidpaintings.compoent.fragment.AuctionItemFragment;
import com.vgpt.androidpaintings.compoent.widget.AuctionScrollView;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.AuctionItem;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class AuctionPaintingFragmentActivity extends FragmentActivity{
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	public static int user_id = 0;
	public static String username = null;
	
	private final int SIZE = AuctionScrollView.PAGE_SIZE;
	private int currentPosition = 0;
	
	int page = 1;
	
	String category = AuctionScrollView.auctionCategory;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	private List<AuctionItem> items = AuctionScrollView.auctionItems;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewpager);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		MyApplication ma = (MyApplication)this.getApplicationContext();

		user_id = ma.getUser_id();
		username = ma.getUsername();
		Intent  intent = getIntent();
		currentPosition = intent.getIntExtra("position", 0);
		if(items.size()%SIZE==0){
			page = items.size()/SIZE+1;
		}else{
			page = items.size()/SIZE+2;
		}
		


		loadAuctionInfoList();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		
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
				loadAuctionInfoList();
			}
			return AuctionItemFragment.newInstance(items.get(position));
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
	private void loadAuctionInfoList(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("page", page);
		map.put("size", SIZE);
		map.put("category", category);
		AsycTaskUtil atu = new AsycTaskUtil(this, 
				Constant.Auction.GET_AUCTION_LIST, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {
				LogUtils.v("=====load querylist page+"+page);
				
				JSONArray retItems = json.optJSONArray("result");
				if (retItems != null) {
					page++;
					for (int i = 0; i < retItems.length(); i++) {
						JSONObject item = retItems.optJSONObject(i);

						Map<String, Object> map;
						try {
							map = JSONToMap.toMap(item);
							AuctionItem ai = new AuctionItem(map);
							items.add(ai);
							
						} catch (JSONException e) {
							LogUtils.e(e);
							e.printStackTrace();
						}

					}
					
					mSectionsPagerAdapter.notifyDataSetChanged();
				}
				
				
			}
		});
		atu.execute(map);
	}


}
