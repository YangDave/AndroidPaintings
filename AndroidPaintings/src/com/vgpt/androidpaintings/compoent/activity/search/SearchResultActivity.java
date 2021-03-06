package com.vgpt.androidpaintings.compoent.activity.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.compoent.activity.paintings.ShowImageActivity;
import com.vgpt.androidpaintings.compoent.activity.user.PersonInfoActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class SearchResultActivity  extends Activity{
	ListView listView;
	ResultAdapter adapter=null;
	RadioButton dateButton,relateButton;
	RadioGroup searchGroup;
	String keyword;
	boolean searchItem;
	final static int SIZE=5;
	int page=1;
	boolean isWorking=false;
	int user_id;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		listView=(ListView)findViewById(R.id.result_container);
		searchGroup=(RadioGroup)findViewById(R.id.rankGroup);
		
		Intent intent=getIntent();
		keyword=intent.getStringExtra("keyword");
		searchItem=intent.getBooleanExtra("searchItem", true);
		user_id=intent.getIntExtra("user_id", 0);
		keyword=keyword.replaceAll("(^\\s{1,})|(\\s{1,}$)", "");
		
		adapter=new ResultAdapter(new ArrayList<Map<String,Object>>());
		listView.setAdapter(adapter);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("size", SIZE);
		map.put("page", page);
		
		SearchTask task=new SearchTask();
		task.execute(map);
		isWorking=true;
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adv, View v, int position,
					long arg3) {
				
				Map<String,Object> map=adapter.getItem(position);
				if(searchItem){
					Intent intent=new Intent(SearchResultActivity.this,ShowImageActivity.class);
					int pic_id=(Integer) map.get("id");
					intent.putExtra("pic_id", pic_id);
					intent.putExtra("user_id", user_id);
					startActivity(intent);
				}else{
					Intent intent=new Intent(SearchResultActivity.this,PersonInfoActivity.class);
					int person_id=(Integer)map.get("id");
					intent.putExtra("person_id", person_id);
					intent.putExtra("user_id", user_id);
					startActivity(intent);
				}
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView v, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& !isWorking){
					isWorking=true;
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("keyword", keyword);
					map.put("size", SIZE);
					map.put("page", ++page);
					SearchTask task=new SearchTask();
					task.execute(map);
				}
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
			}
		});
	}
	
	class SearchTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		
		private int checkCode;
		private JSONArray retArray=null;
		private boolean no_found=false;

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if(no_found){
				Toast.makeText(SearchResultActivity.this, "没有匹配的搜索结果，请重新输入", Toast.LENGTH_LONG).show();
			}
			
			if((result!=null)&&(checkCode==1)&&(retArray!=null)){
				if(searchItem){
					for(int i=0;i<retArray.length();i++){
						JSONObject jsonItem=retArray.optJSONObject(i);
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("id", jsonItem.optInt("pic_id"));
						map.put("name", jsonItem.optString("name"));
						map.put("introduction", jsonItem.optString("introduction"));
						adapter.getItems().add(map);
					}
					
				}else{
					for(int i=0;i<retArray.length();i++){
						JSONObject jsonItem=retArray.optJSONObject(i);
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("id", jsonItem.optInt("user_id"));
						map.put("name",jsonItem.optString("username"));
						adapter.getItems().add(map);
					}
				}
				adapter.notifyDataSetChanged();
				isWorking=false;
			}
		}

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {
			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(params[0]);

			NameValuePair pa = new BasicNameValuePair("json", json.toString());
			list.add(pa);
			try{
				if(searchItem){
				cl.post(Constant.Search.PIC, list, new JSONResponseHandler<JSONObject>() {

					@Override
					public JSONObject handlerJson(JSONObject json) {
						if((json!=null)&&(json.optInt("code")==1)){
							checkCode=json.optInt("code");
							no_found=json.optBoolean("no_found");
							if(!no_found){
								retArray=json.optJSONArray("result");
								
							}
							
						}
						return json;
					}
				});
				}else{
					cl.post(Constant.Search.USER, list, new JSONResponseHandler<JSONObject>() {

						@Override
						public JSONObject handlerJson(JSONObject json) {
							if(json!=null){
								checkCode=json.optInt("code");
							if(json.optInt("code")==1){
								no_found=json.optBoolean("no_found");
								if(!no_found){
									retArray=json.optJSONArray("result");
								}
								
							}
							}
							return json;
						}
					});
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return json;
		}
		
	}
	
	class ResultAdapter extends BaseAdapter{
		
		List<Map<String,Object>> mListItems;
		
		public ResultAdapter(List<Map<String, Object>> mListItems){
			super();
			this.mListItems = mListItems;
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}
		
		public List<Map<String,Object>> getItems(){
			return mListItems;
		}

		@Override
		public int getCount() {
			return mListItems.size();
		}

		@Override
		public Map<String,Object> getItem(int position) {
			return mListItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemViewHolder itemViewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(SearchResultActivity.this).inflate(
						R.layout.simple_item, null);
				itemViewHolder = new ItemViewHolder();
				convertView.setTag(itemViewHolder);

			} else {
				itemViewHolder = (ItemViewHolder) convertView.getTag();
			}

			itemViewHolder.painting = (ImageView) convertView
					.findViewById(R.id.painting);
			itemViewHolder.nameText = (TextView) convertView
					.findViewById(R.id.name);
			itemViewHolder.introductionText = (TextView) convertView
					.findViewById(R.id.introduction);
			Map<String, Object> item = (Map<String, Object>) getItem(position);
			int id=(Integer)item.get("id");
			
			itemViewHolder.nameText
					.setText(item.get("name").toString());
			if(searchItem){
				Picasso.with(SearchResultActivity.this)
				.load(Constant.Painting.GET_PICTURE+id)
				.into(itemViewHolder.painting);
			}else{
				Picasso.with(SearchResultActivity.this)
				.load(Constant.Api.GET_PHOTO+id)
				.into(itemViewHolder.painting);
			}
			

			return convertView;
		}

		class ItemViewHolder {

			ImageView painting;
			TextView nameText, introductionText;
		}
		
		
	}
	
	

}
