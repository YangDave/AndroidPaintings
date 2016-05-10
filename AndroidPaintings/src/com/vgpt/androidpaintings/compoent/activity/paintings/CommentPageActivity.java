package com.vgpt.androidpaintings.compoent.activity.paintings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.compoent.adapter.CommentListAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.CommentItem;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class CommentPageActivity extends Activity{
	
	ListView commListView;
	int pic_id;
	final static int SIZE=10;
	int page=1;
	CommentListAdapter adapter=null;
	int user_id;
	String content;
	
	boolean isWorking=false;
	
	final static String INTENT="commentpage.to.commentitem";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_comment);
		
		commListView=(ListView)findViewById(R.id.commentlist);
		
		Intent intent=getIntent();
		pic_id=intent.getIntExtra("pic_id", 0);
		user_id=intent.getIntExtra("user_id", 0);
		adapter=new CommentListAdapter(CommentPageActivity.this
				, new ArrayList<Map<String,Object>>());
		commListView.setAdapter(adapter);
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		map.put("page", page);
		map.put("size", SIZE);
		
		GetCommentTask task=new GetCommentTask();
		task.execute(map);
		
		commListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				Intent intent=new Intent(CommentPageActivity.this,CommentItemActivity.class);
				Bundle data=new Bundle();
				Map<String,Object> map=adapter.getItem(position);
				
				CommentItem commentItem=new CommentItem(map);
				data.putSerializable("commentItem", commentItem);
				intent.putExtras(data);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
				
				
			}
		});
		commListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isWorking){
					
					Map<String,Object> getMap=new HashMap<String, Object>();
					getMap.put("pic_id", pic_id);
					getMap.put("page", ++page);
					getMap.put("size", SIZE);
					
					GetCommentTask task=new GetCommentTask();
					task.execute(getMap);
					
				}
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
			}
		});
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.commentmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId()==R.id.item1){
			
			final AlertDialog.Builder builder=new Builder(CommentPageActivity.this);
			View view=getLayoutInflater().inflate(R.layout.comment_edit, null);
			final EditText et=(EditText)view.findViewById(R.id.commenteditText);
			builder.setView(view);
			builder.setPositiveButton("确定", new OnClickListener() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
					content=et.getText().toString().replaceAll("(^\\s{1,})|(\\s{1,}$)" , "");
					if(content!=null && !content.equals("")){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("pic_id", pic_id);
					map.put("user_id", user_id);
					map.put("body", content);
					SetCommentTask task=new SetCommentTask();
					task.execute(map);
					}
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					builder.show().dismiss();
					
				}
			});
			builder.show();
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	class SetCommentTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{

		
		int checkCode;
		JSONObject ret;
		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {

			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(params[0]);

			NameValuePair pa = new BasicNameValuePair("json", json.toString());
			list.add(pa);

			try {
				cl.post(Constant.Painting.SET_COMMENT, list,
						new JSONResponseHandler<JSONObject>() {

							@Override
							public JSONObject handlerJson(JSONObject json) {
								if(json!=null){
									checkCode=json.optInt("code");
								if(json.optInt("code")==1){
									ret=json.optJSONObject("result");
								}
								}
								return json;
							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ret;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if(checkCode==1 && ret!=null){
				boolean isSuccess=ret.optBoolean("success");
				if(isSuccess){
//				TODO	
					
					Toast.makeText(CommentPageActivity.this, "发表评论成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(CommentPageActivity.this, "发表评论失败", Toast.LENGTH_LONG).show();
				}
				
			}
		}
		
		
	}


	class GetCommentTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		
		JSONArray ret;
		int checkCode;
		

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			isWorking=false;
			
			if(checkCode==1){
			if(ret!=null){
				for(int i=0;i<ret.length();i++){
					JSONObject jsonItem=ret.optJSONObject(i);
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("user_id", jsonItem.optInt("user_id"));
					map.put("username", jsonItem.optString("username"));
					map.put("body", jsonItem.optString("body"));
					map.put("date", jsonItem.optString("time"));
					map.put("comment_id", jsonItem.optInt("comment_id"));
					
					adapter.getlistItems().add(map);
				}
				adapter.notifyDataSetChanged();
			}
			else if(ret==null){
				Toast.makeText(CommentPageActivity.this, "暂无评论", Toast.LENGTH_LONG).show();
			}
			}
		}

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {
			
			isWorking=true;
			MyHttpClient cl=MyHttpClient.getInstance();
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			JSONObject json=new JSONObject(params[0]);
			NameValuePair pa=new BasicNameValuePair("json", json.toString());
			list.add(pa);
			
			
				try {
					cl.post(Constant.Painting.GET_COMMENT, list, 
							new JSONResponseHandler<JSONObject>() {

								@Override
								public JSONObject handlerJson(JSONObject json) {
									
									
									if(json!=null){
										checkCode=json.optInt("code");
										if(checkCode==1){
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
	
	

}
