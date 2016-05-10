package com.vgpt.androidpaintings.compoent.activity.association;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.biz.JSONToMap;
import com.vgpt.androidpaintings.compoent.adapter.AssoManeuverListAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;

public class AssociationManeuverActivity extends MyActivity{

	ListView mListView;
	int asso_id;
	String asso_name;

	int page = 1;
	final int SIZE = 10;
	private boolean isWorking = false;

	AssoManeuverListAdapter adapter = null;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.maneuver_list_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case R.id.maneuver_add:
			Intent intent = new Intent(AssociationManeuverActivity.this,AssociationManeuverCreateActivity.class);
			intent.putExtra("asso_id", asso_id);
			intent.putExtra("asso_name", asso_name);
			startActivity(intent);

			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if(null != savedInstanceState){
//			asso_id = savedInstanceState.getInt("asso_id");
//			asso_name = savedInstanceState.getString("asso_name");
//		}else{

//			savedInstanceState = new Bundle();
			Intent intent = getIntent();
			asso_id = intent.getIntExtra("asso_id", 0);
			asso_name = intent.getStringExtra("asso_name");
//			savedInstanceState.putInt("asso_id", asso_id);
//			savedInstanceState.putString("asso_name", asso_name);
//		}
		setContentView(R.layout.activity_association_maneuver_list);

		loadPage(this);

	}





	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("asso_id", asso_id);
		outState.putString("asso_name", asso_name);
	}


	@Override
	public void findView() {
		mListView = (ListView)findViewById(R.id.maneuver_list);

	}

	@Override
	public void prepareData() {

		adapter = new AssoManeuverListAdapter(this,new ArrayList<Map<String,Object>>());
		mListView.setAdapter(adapter);
	}

	@Override
	public void addListener() {

		//		item点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				Map<String,Object> map = adapter.getItem(position);
				Intent intent = new Intent(AssociationManeuverActivity.this,AssociationManeuverDetailActivity.class);
				intent.putExtra("asso_id", asso_id);
				intent.putExtra("exer_id", (Integer)map.get("id"));
				startActivity(intent);

			}
		});
		//		滑动事件
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {

				if((scrollState == OnScrollListener.SCROLL_STATE_IDLE )&&(!isWorking)){
					Map<String,Object> extramap=new HashMap<String, Object>();
					extramap.put("asso_id", asso_id);
					extramap.put("page", ++page);
					extramap.put("size", SIZE);
					getManeuverList(extramap);


				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showContent() {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("asso_id", asso_id);
		map.put("page", page);
		map.put("size", SIZE);
		getManeuverList(map);

	}
	//	获取列表并展示
	@SuppressWarnings("unchecked")
	private void getManeuverList(Map<String,Object> map){
		AsycTaskUtil atu = new AsycTaskUtil(this, Constant.Association.GET_ASSO_MANEUVER_LIST, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {
				isWorking =false;
				if(json.optInt("code") == 1){
					JSONArray result = json.optJSONArray("result");
					for(int i = 0;i<result.length();i++){

						JSONObject item = result.optJSONObject(i);
						try {
							Map<String,Object> map = JSONToMap.toMap(item);
							adapter.getlistItems().add(map);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					adapter.notifyDataSetChanged();
				}

			}
		});
		isWorking = true;
		atu.execute(map);
	}

}