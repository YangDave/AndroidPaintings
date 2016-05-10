package com.vgpt.androidpaintings.compoent.activity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.biz.JSONToMap;
import com.vgpt.androidpaintings.compoent.adapter.MyCommentListAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class MyCommentPageActivity extends MyActivity implements OnClickListener {
	
	ListView myCommentList;
	TextView loadMore;
	MyCommentListAdapter adapter=null;
	int page=1;
	final static int SIZE=3;
	
	boolean isWorking=false;
	
//	TODO
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycomment);
		
		
		loadPage(this);
		
	
		
		myCommentList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& !isWorking){
					Map<String,Object> map2=new HashMap<String, Object>();
					map2.put("user_id", user_id);
					map2.put("page", ++page);
					map2.put("size", SIZE);
					GetMyCommentTask task2=new GetMyCommentTask();
					task2.execute(map2);
					
				}
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
			}
		});
		
		myCommentList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
		
		
	}

	@Override
	public void onClick(View arg0) {
		
	}
	
	class GetMyCommentTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		
		private int checkCode;
		private JSONArray ret;

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if((checkCode==1)&&(ret!=null)){
				for(int i=0;i<ret.length();i++){
					JSONObject commItem=ret.optJSONObject(i);
					
					Map<String, Object> map = null;
					try {
						map = JSONToMap.toMap(commItem);
						adapter.getlistItems().add(map);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					Map<String,Object> map=new HashMap<String, Object>();
//					map.put("pic_id", commItem.optInt("pic_id"));
//					map.put("pic_name",commItem.optString("pic_name"));
//					map.put("body", commItem.optString("body"));
//					map.put("comment_id", commItem.optInt("comment_id"));
//					map.put("time", commItem.optString("time"));
					
				}
				
				adapter.notifyDataSetChanged();
				
			}
			else {
				//TODO
				Toast.makeText(MyCommentPageActivity.this, "未知错误", Toast.LENGTH_LONG).show();
			}
			isWorking=false;
			
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
				cl.post(Constant.Api.SIGN_IN, list,
						new JSONResponseHandler<JSONObject>() {

							@Override
							public JSONObject handlerJson(JSONObject json) {
								if(json!=null){
									checkCode=json.optInt("code");
								if(json.optInt("code")==1){
									ret=json.optJSONArray("result");
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
		
		myCommentList=(ListView)findViewById(R.id.mycommentlist);
		loadMore=(TextView)findViewById(R.id.loadmore);
		
	}

	@Override
	public void prepareData() {

		adapter=new MyCommentListAdapter(this, new ArrayList<Map<String,Object>>());
		myCommentList.setAdapter(adapter);
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("page", page);
		map.put("size", SIZE);
		
//		AsycTaskWithWorkingUtil atu = new AsycTaskWithWorkingUtil(this, Constant., onPostExecute)
	}

	@Override
	public void addListener() {
		
		
		
	}

	@Override
	public void showContent() {
		// TODO Auto-generated method stub
		
	}

}
