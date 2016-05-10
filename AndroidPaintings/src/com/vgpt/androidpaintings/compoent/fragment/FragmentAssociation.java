package com.vgpt.androidpaintings.compoent.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.compoent.activity.association.AssociationActivity;
import com.vgpt.androidpaintings.compoent.activity.association.AssociationCreate;
import com.vgpt.androidpaintings.compoent.adapter.AssoListAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.Association;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;

/**
 * @author Charles
 *
 */
public class FragmentAssociation extends Fragment {
	//	ListView associationlist;

	private ViewPager mPager;//页卡内容
	private List<View> listViews; // Tab页面列表
//	private ImageView cursor;// 动画图片
//	private TextView t1, t2, t3;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 1;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int ivCursorWidth;// 动画图片宽度

	private int tabWidth;// 每个tab头的宽度

	private int offsetX;// tab头的宽度减去动画图片的宽度再除以2（保证动画图片相对tab头居中）

//	private ImageView ivCursor;//下划线图片

	private View joinedView,focusedView,allView;
	
	private RadioGroup rg;
	private RadioButton rb1,rb2,rb3;

	//	private List<Map<String,Object>> joinedList = new ArrayList<Map<String,Object>>();
	//	private List<Map<String,Object>> focusedList = new ArrayList<Map<String,Object>>();
	//	private List<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();

	private AssoListAdapter joinedAdapter,focusedAdapter,allAdapter;
	private ListView joinedListView,focusedListView,allListView;


	//	AssociationListAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getWindow().setTitle(getString(R.string.frag3_title));
	}



	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment3, container, false);


		setHasOptionsMenu(true);
		initView(rootView);
		initViewPager();
//		initImageView();
		return rootView;


	}
	
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		MenuItem item = menu.add(R.string.asso_create);
		Intent intent = new Intent(getActivity(),AssociationCreate.class);
		item.setIntent(intent);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	}
	
	

	public void initView(View view) {

//		t1 = (TextView) view.findViewById(R.id.text1);
//		t2 = (TextView) view.findViewById(R.id.text2);
//		t3 = (TextView) view.findViewById(R.id.text3);
		
		rg = (RadioGroup)view.findViewById(R.id.tab_asso);
		rb1 = (RadioButton)view.findViewById(R.id.asso_rb1);
		rb2 = (RadioButton)view.findViewById(R.id.asso_rb2);
		rb3 = (RadioButton)view.findViewById(R.id.asso_rb3);

		mPager = (ViewPager) view.findViewById(R.id.vPager);
//		cursor = (ImageView) view.findViewById(R.id.cursor);

//		t1.setOnClickListener(new MyOnClickListener(0));
//		t2.setOnClickListener(new MyOnClickListener(1));
//		t3.setOnClickListener(new MyOnClickListener(2));
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.asso_rb1:mPager.setCurrentItem(0);
				break;
				case R.id.asso_rb2:mPager.setCurrentItem(1);
				break;
				case R.id.asso_rb3:mPager.setCurrentItem(2);
				break;
				}
				
			}
		});

	}
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	}
	
	

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int position) {
			((ViewPager) arg0).addView(mListViews.get(position), 0);


			return mListViews.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int position) {
//			Animation animation = new TranslateAnimation(tabWidth * currIndex
//					+ offsetX, tabWidth * position + offsetX, 0, 0);// 显然这个比较简洁，只有一行代码。
//			currIndex = position;
//			animation.setFillAfter(true);// True:图片停在动画结束位置
//			animation.setDuration(300);
//			cursor.startAnimation(animation);
			
			switch (position) {
			case 0:rb1.setChecked(true);
				break;
			case 1:rb2.setChecked(true);
			    break;
			case 2:rb3.setChecked(true);  
			    break;
			default:
				rb1.setChecked(true);
				break;
			}


		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@SuppressWarnings("unchecked")
	public void initViewPager() {

		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		joinedView = mInflater.inflate(R.layout.lay1, null);
		focusedView = mInflater.inflate(R.layout.lay2, null);
		allView = mInflater.inflate(R.layout.lay3, null);
		listViews.add(joinedView);
		listViews.add(focusedView);
		listViews.add(allView);
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		initJoinedListView();
		
		initFocusedListView();
		
		initAllListView();

	
	}

//	private void initImageView(){
//		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
//				.getWidth();// 获取图片宽度
//		DisplayMetrics dm = new DisplayMetrics();
//		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int screenW = dm.widthPixels;// 获取分辨率宽度
//		//		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
//
//
//		ivCursorWidth = BitmapFactory.decodeResource(getResources(),
//				R.drawable.a).getWidth();// 获取图片宽度
//
//		tabWidth = screenW / listViews.size();
//		if (ivCursorWidth > tabWidth) {
////			ivCursor.getLayoutParams().width = tabWidth;
//			ivCursorWidth = tabWidth;
//		}
//		offsetX = (tabWidth - ivCursorWidth) / 2;
//		Matrix matrix = new Matrix();
//		matrix.postTranslate(offset, 0);
////		cursor.setImageMatrix(matrix);// 设置动画初始位置
//	}
	
	@SuppressWarnings("unchecked")
	private void initJoinedListView(){
		
		joinedListView= (ListView) joinedView.findViewById(R.id.joined_asso_list);
		joinedAdapter = new AssoListAdapter(getActivity(), new ArrayList<Map<String,Object>>());
		joinedListView.setAdapter(joinedAdapter);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id",MainActivity.user_id);
		AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Association.GET_ASSOCIATIONLIST, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONArray assoArray = json.optJSONArray("result");
				for(int i=0;i<assoArray.length();i++){
					Map<String,Object> itemmap=new HashMap<String, Object>();
					JSONObject itemObject=assoArray.optJSONObject(i);
					itemmap.put("asso_name", itemObject.optString("asso_name"));
					itemmap.put("asso_id", itemObject.optInt("asso_id"));
					itemmap.put("asso_mark", itemObject.optString("asso_mark"));
					joinedAdapter.getlistItems().add(itemmap);

				}
				joinedAdapter.notifyDataSetChanged();

			}
		});

		atu.execute(map);
		
		joinedListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				
				Association association=new Association(joinedAdapter.getItem(position));
					Intent intent=new Intent(getActivity(),AssociationActivity.class);
					Bundle associationdata=new Bundle();
					associationdata.putSerializable("association", association);
					intent.putExtras(associationdata);
					startActivity(intent);
			}
			
		});
		
	}
	
	private void initFocusedListView(){
		
		focusedListView = (ListView)focusedView.findViewById(R.id.focused_asso_list);
		focusedAdapter = new AssoListAdapter(getActivity(), new ArrayList<Map<String,Object>>());
		focusedListView.setAdapter(focusedAdapter);
		
	}
	
	private void initAllListView(){
		
		allListView = (ListView)allView.findViewById(R.id.all_asso_list);
		allAdapter = new AssoListAdapter(getActivity(), new ArrayList<Map<String,Object>>());
		allListView.setAdapter(allAdapter);

		
	}



}