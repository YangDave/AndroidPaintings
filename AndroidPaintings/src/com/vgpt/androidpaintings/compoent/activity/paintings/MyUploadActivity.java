package com.vgpt.androidpaintings.compoent.activity.paintings;

import java.io.Serializable;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.adapter.PaintingListAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.MyPaintingItem;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class MyUploadActivity extends MyActivity{
	ListView listView;
	PaintingListAdapter adapter=null;
	int page=1;
	public final static int SIZE=5;
	int user_id;
	boolean isWorking=false;
	List<MyPaintingItem> items = new ArrayList<MyPaintingItem>();




	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId() == android.R.id.home){
			this.finish();
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//		TODO 翻页
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myupload_list);
		
		getActionBar().setTitle("我的上传");

		loadPage(this);

	}

	class GetMyUploadTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{

		private int checkCode;
		private JSONArray ret;
		private boolean nofound=false;
		
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if((checkCode==1)&&(ret!=null)&&(!nofound)){
				for(int i=0;i<ret.length();i++){
					JSONObject jsonItem= ret.optJSONObject(i);
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("pic_id", jsonItem.optInt("pic_id"));
					map.put("name", jsonItem.optString("pic_name"));
					map.put("on_aucting", jsonItem.optInt("on_aucting"));

					adapter.getlistItems().add(map);
					items.add(new MyPaintingItem(map));

				}
				adapter.notifyDataSetChanged();
			}
			else if(nofound){
				Toast.makeText(MyUploadActivity.this, "无上传画作", Toast.LENGTH_LONG).show();
			}
			isWorking=false;
		}

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {

			isWorking = true;

			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(params[0]);

			NameValuePair pa = new BasicNameValuePair("json", json.toString());
			list.add(pa);

			try {
				cl.post(Constant.Search.FIND_MYPIC, list,
						new JSONResponseHandler<JSONObject>() {

					@Override
					public JSONObject handlerJson(JSONObject json) {
						if (json != null && json.optInt("code") == 1) {
							ret=json.optJSONArray("result");
							checkCode = json.optInt("code");
							nofound=json.optBoolean("no_found");

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
		listView=(ListView)findViewById(R.id.uploadList);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {
		adapter=new PaintingListAdapter(MyUploadActivity.this, new ArrayList<Map<String,Object>>());
		listView.setAdapter(adapter);


		Intent intent=getIntent();
		user_id=intent.getIntExtra("user_id", 0);

		Map<String,Object> map=new HashMap<String,Object>();
		if(user_id != 0){
			map.put("user_id",user_id);
			map.put("page", page);
			map.put("size", SIZE);
			GetMyUploadTask task=new GetMyUploadTask();
			task.execute(map);
		}

	}

	@Override
	public void addListener() {
		listView.setOnScrollListener(new OnScrollListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if((scrollState == OnScrollListener.SCROLL_STATE_IDLE )&&(!isWorking)){
					isWorking=true;
					Map<String,Object> extramap=new HashMap<String, Object>();
					extramap.put("user_id", user_id);
					extramap.put("page", ++page);
					extramap.put("size", SIZE);
					GetMyUploadTask extraTask=new GetMyUploadTask();
					extraTask.execute(extramap);

				}

			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				Map<String,Object> clickedMap=adapter.getItem(position);
				MyPaintingItem mpi=new MyPaintingItem(clickedMap);
				Intent intent=new Intent(MyUploadActivity.this,MyPaintingsActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("items", (Serializable) items);
				intent.putExtras(data);
				intent.putExtra("listPosition", position);
				startActivity(intent);
			}
		});

	}

	@Override
	public void showContent() {
		// TODO Auto-generated method stub

	}



}
