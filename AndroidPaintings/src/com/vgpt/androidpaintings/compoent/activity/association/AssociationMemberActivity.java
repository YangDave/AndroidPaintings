package com.vgpt.androidpaintings.compoent.activity.association;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.compoent.activity.user.PersonInfoActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class AssociationMemberActivity extends MyActivity{

	ListView memberList;
	int asso_id;
	String asso_name;
	TextView asso_nameText;
	MemberAdapter adapter;

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_member);
		getActionBar().setTitle(R.string.asso_member);
		loadPage(this);


	}
	class GetMemberTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		private int checkCode;
		private JSONObject ret;

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {
			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(params[0]);

			try {

				NameValuePair pa = new BasicNameValuePair("json",
						json.toString());
				list.add(pa);

				cl.post(Constant.Association.GET_MEMBER, list,
						new JSONResponseHandler<JSONObject>() {

					@Override
					public JSONObject handlerJson(JSONObject json) {
						if (json != null && json.optInt("code") == 1) {
							checkCode=json.optInt("code");
							ret=json.optJSONObject("result");

						}

						return json;
					}
				});
			} catch (Exception e) {
			}
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if((checkCode==1)&& (ret!=null)){
				JSONArray userArray=ret.optJSONArray("user");
				JSONArray adminArray=ret.optJSONArray("admin");
				for(int j=0;j<adminArray.length();j++){
					Map<String,Object> listItemadmin=new HashMap<String, Object>();
					JSONObject adminJsonObject=adminArray.optJSONObject(j);
					listItemadmin.put("user_id", adminJsonObject.optInt("user_id"));
					listItemadmin.put("user_name",adminJsonObject.optString("user_name"));
					listItemadmin.put("authority", 1);
					adapter.getListItems().add(listItemadmin);
				}
				adapter.notifyDataSetChanged();
				for(int i=0;i<userArray.length();i++){
					Map<String,Object> listItem=new HashMap<String,Object>();
					JSONObject userJsonObject=userArray.optJSONObject(i);
					listItem.put("user_id", userJsonObject.optInt("user_id"));
					listItem.put("user_name", userJsonObject.optString("user_name"));
					listItem.put("authority", 0);
					adapter.getListItems().add(listItem);
				}
				adapter.notifyDataSetChanged();


			}
		}

	}
	class MemberAdapter extends BaseAdapter{
		List<Map<String,Object>> mlistItems;

		public MemberAdapter(List<Map<String,Object>> mlistItems){
			this.mlistItems=mlistItems;
		}


		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mlistItems.size();
		}
		public List<Map<String,Object>> getListItems(){
			return mlistItems;
		}

		@Override
		public Object getItem(int position) {
			return mlistItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemViewHolder itemViewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(AssociationMemberActivity.this).inflate(
						R.layout.member_adapter, null);
				itemViewHolder = new ItemViewHolder();
				convertView.setTag(itemViewHolder);

			} else {
				itemViewHolder = (ItemViewHolder) convertView.getTag();

			}
			itemViewHolder.nameText = (TextView) convertView
					.findViewById(R.id.name);
			itemViewHolder.authorityText=(TextView)convertView.findViewById(R.id.authority);
			@SuppressWarnings("unchecked")
			Map<String, Object> memberItem = (Map<String, Object>) getItem(position);
			itemViewHolder.authorityText.setText(
					(Integer)memberItem.get("authority")==1?R.string.admin:R.string.normal_member);
			itemViewHolder.nameText
			.setText(memberItem.get("user_name").toString());

			return convertView;
		}
		class ItemViewHolder {
			TextView nameText, authorityText;
		}

	}
	@Override
	public void findView() {
		memberList=(ListView)findViewById(R.id.memberlist);
		asso_nameText=(TextView)findViewById(R.id.asso_nameInMemberList);

	}
	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {
		Intent intent=getIntent();
		asso_id=intent.getIntExtra("asso_id", 0);
		asso_name=intent.getStringExtra("asso_name");
		asso_nameText.setText(asso_name);

		adapter=new MemberAdapter(new ArrayList<Map<String,Object>>());
		memberList.setAdapter(adapter);
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("asso_id", asso_id);
		GetMemberTask task=new GetMemberTask();
		task.execute(map);

	}
	@Override
	public void addListener() {
		memberList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> memberadapter, View v, int position,
					long arg3) {
				Intent showMemerInfoIntent=new Intent(AssociationMemberActivity.this,PersonInfoActivity.class);
				showMemerInfoIntent.putExtra("person_id",(Integer)((Map<String,Object>)adapter.getItem(position)).get("user_id"));
				showMemerInfoIntent.putExtra("user_id",MainActivity.user_id);
				showMemerInfoIntent.putExtra("focusedornot", false);
				startActivity(showMemerInfoIntent);

			}
		});

	}
	@Override
	public void showContent() {
		// TODO Auto-generated method stub

	}


}
