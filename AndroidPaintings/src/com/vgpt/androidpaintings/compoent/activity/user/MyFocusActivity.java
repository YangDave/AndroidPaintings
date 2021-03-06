package com.vgpt.androidpaintings.compoent.activity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.adapter.FocusListAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class MyFocusActivity extends MyActivity{
	ListView myFocusList;
	FocusListAdapter adapter=null;
	int page=1;
	final static int SIZE=3;
	boolean isWorking=false;
	

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_focus);
		
		loadPage(this);
		
			
		
			
		
		
		
	}
	class GetFocusTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		int checkCode;
		JSONArray focused_items;

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			isWorking=false;
//			dialog.dismiss();
			if(checkCode==1){
				if(focused_items!=null){
					for(int i=0;i<focused_items.length();i++){
						Map<String,Object> item=new HashMap<String, Object>();
						JSONObject jsonItem=focused_items.optJSONObject(i);
						item.put("focusedname",jsonItem.optString("name"));
						item.put("person_id", jsonItem.optInt("person_id"));
						adapter.getlistItems().add(item);
					}
					adapter.notifyDataSetChanged();
					
				}else{
					Toast.makeText(MyFocusActivity.this, "无关注用户", Toast.LENGTH_SHORT).show();
				}
			}else{
				
			}
			
			
			
		}


		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {
			isWorking=true;
			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(params[0]);

			NameValuePair pa = new BasicNameValuePair("json", json.toString());
			list.add(pa);

			try {
				cl.post(Constant.Api.GET_FOCUS, list,
						new JSONResponseHandler<JSONObject>() {

							@Override
							public JSONObject handlerJson(JSONObject json) {
								if (json != null) {
									checkCode=json.optInt("code");
									if(json.optInt("code") == 1){
									JSONObject ret=json.optJSONObject("result");
									focused_items=ret.optJSONArray("items");
									
									}
								}

								return json;
							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}

			return json;
			
		}
		
	}
	@Override
	public void findView() {
		
		myFocusList=(ListView)findViewById(R.id.myfocuslist);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {
		
//		user_id=UserInfoSingleInstance.getInstance().getUser_id();
		
		
		Map<String,Object> focusMap=new HashMap<String, Object>();
		focusMap.put("user_id",user_id);
		focusMap.put("page", page);
		focusMap.put("size", SIZE);
		GetFocusTask getFocus=new GetFocusTask();
		getFocus.execute(focusMap);
		
		adapter=new FocusListAdapter(MyFocusActivity.this,new ArrayList<Map<String,Object>>(),user_id);
		myFocusList.setAdapter(adapter);
		
	}
	@Override
	public void addListener() {
		
		myFocusList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View mView, int position,
					long arg3) {
//				Map<String,Object> map=(Map<String, Object>) adapter.getItem(position);
//				int focused_id=(Integer) map.get("focused_id");
//				Intent showFocusedInfoIntent=new Intent(MyFocusActivity.this,PersonInfoActivity.class);
//				showFocusedInfoIntent.putExtra("person_id", focused_id);
//				showFocusedInfoIntent.putExtra("user_id", user_id);
//				showFocusedInfoIntent.putExtra("focusedornot", true);
//				startActivity(showFocusedInfoIntent);
				Intent intent = new Intent(MyFocusActivity.this,PersonInfoActivity.class);
				;
				intent.putExtra("person_id", (Integer)adapter.getItem(position).get("person_id"));
				startActivity(intent);
//				Toast.makeText(MyFocusActivity.this, "功能完善中", Toast.LENGTH_LONG).show();
				
			}
		});
		
		myFocusList.setOnScrollListener(new OnScrollListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE&&!isWorking){
					
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("user_id",user_id);
					map.put("page", ++page);
					map.put("size", SIZE);
					GetFocusTask getListTask=new GetFocusTask();
					getListTask.execute(map);
				}
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
			}
		});
		
		
		
	}
	@Override
	public void showContent() {
		// TODO Auto-generated method stub
		
	}



}
