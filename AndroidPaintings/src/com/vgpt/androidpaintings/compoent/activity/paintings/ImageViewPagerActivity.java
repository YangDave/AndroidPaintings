package com.vgpt.androidpaintings.compoent.activity.paintings;

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
import com.vgpt.androidpaintings.compoent.fragment.ImageFragment;
import com.vgpt.androidpaintings.compoent.widget.MyCollectScrollView;
import com.vgpt.androidpaintings.compoent.widget.MyScrollView;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.PaintingItem;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class ImageViewPagerActivity extends FragmentActivity{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	public static int user_id = 0;
	public String username = null;

	private int SIZE = MyScrollView.PAGE_SIZE;

	int page = 1;
	int currentPosition = 0;
	String category = MyScrollView.category;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	public static boolean isMyCollectPage = false;

	List<PaintingItem> paintingItems = MyScrollView.items;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewpager);
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);


		MyApplication ma = (MyApplication)this.getApplicationContext();

		user_id = ma.getUser_id();
		username = ma.getUsername();
		
		
		Intent intent =getIntent();
		String type = intent.getStringExtra("type");
		currentPosition = intent.getIntExtra("position", 0);
		if(type.equals("mycollect")){
			isMyCollectPage = true;
			category = MyCollectScrollView.picCategory;
			SIZE = MyCollectScrollView.PAGE_SIZE;
			paintingItems = MyCollectScrollView.PaintingColletctedItems;
		}
		
		if(paintingItems.size()%SIZE == 0){
			page = paintingItems.size()/SIZE + 1;
		}else{
			page = paintingItems.size()/SIZE + 2;
		}
		

		
//		loadPaintingInfoList();

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

		return super.onCreateOptionsMenu(menu);
	}


	private int pic_id = 0 ;
	@SuppressWarnings("unchecked")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		pic_id = paintingItems.get(currentPosition).getPic_id();

		int id = item.getItemId();

		switch(id){
		case android.R.id.home:
			this.finish();
			break;

		}

		return super.onContextItemSelected(item);
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
		
			if(position == paintingItems.size() - 1){
				loadPaintingInfoList();
			}
			currentPosition = position;
			LogUtils.v("======="+currentPosition);
			return ImageFragment.newInstance(paintingItems.get(position));
		}
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return paintingItems.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	private void loadPaintingInfoList(){
		String url ;
		if(isMyCollectPage == true){
			url = Constant.Painting.GET_COL;
		}else{
			url = Constant.Painting.PAINTING_LOAD;
		}

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("size", SIZE);
		map.put("user_id", user_id);
		map.put("category", category);
		AsycTaskUtil atu = new AsycTaskUtil(this, 
				url, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {
				LogUtils.v("=====load querylist page+"+page);

				JSONObject ret = json
						.optJSONObject("result");
				JSONArray items = ret.optJSONArray("items");
				if(items != null){
					page++;
				}else{
					return;
				}
				for(int i = 0;i< items.length();i++){
					Map<String, Object> map;
					try {
						map = JSONToMap.toMap(items.optJSONObject(i));
						PaintingItem pi = new PaintingItem(map);
						paintingItems.add(pi);

					} catch (JSONException e) {
						e.printStackTrace();
						LogUtils.v("======JSONException==== JSONTOMAP");
					}

				}

				mSectionsPagerAdapter.notifyDataSetChanged();

			}
		});
		atu.execute(map);

	}

}
