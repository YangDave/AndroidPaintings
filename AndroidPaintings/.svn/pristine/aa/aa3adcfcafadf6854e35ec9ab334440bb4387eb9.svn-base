package com.vgpt.androidpaintings.compoent.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class FocusListAdapter extends AbsListAdapter{
	private int user_id;
	private int clickedBtPosition;

	public FocusListAdapter(Context mContext,
			List<Map<String, Object>> listItems,int user_id) {
		super(mContext, listItems);
		this.user_id=user_id;
	}
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ItemViewHolder itemViewHolder=null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.collect_listitem, null);
			itemViewHolder = new ItemViewHolder();
			convertView.setTag(itemViewHolder);

		} else {
			itemViewHolder = (ItemViewHolder) convertView.getTag();

		}

		itemViewHolder.painting = (ImageView) convertView
				.findViewById(R.id.painting);
		itemViewHolder.nameText = (TextView) convertView
				.findViewById(R.id.name);
		
		
		@SuppressWarnings("unchecked")
		Map<String, Object> picItem = (Map<String, Object>) getItem(position);
		final int person_id=(Integer)picItem.get("person_id");
		itemViewHolder.nameText
				.setText(picItem.get("focusedname").toString());
		
//		itemViewHolder.button.setOnClickListener(new OnClickListener() {
//			
//			@SuppressWarnings("unchecked")
//			@Override
//			public void onClick(View arg0) {
//				Map<String,Object> map=new HashMap<String, Object>();
//				clickedBtPosition=position;
//				map.put("user_id", user_id);
//				map.put("focus_id", person_id);
//				DelFocusTask task=new DelFocusTask();
//				task.execute(map);
//				
//			}
//		});
		
		Picasso.with(mContext).load(Constant.Api.GET_PHOTO+person_id).into(itemViewHolder.painting);

		return convertView;
	}
	class ItemViewHolder{
		ImageView painting;
		TextView nameText,introduction;
	}
	
	class DelFocusTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		
		private int checkCode;
		private JSONObject ret;

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {

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
									ret=json.optJSONObject("result");
									
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

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			if(checkCode==1){
				if(ret!=null){
					boolean isSuccess=ret.optBoolean("success");
					if(isSuccess){
						Toast.makeText(mContext, "删除关注成功", Toast.LENGTH_LONG).show();
						getlistItems().remove(clickedBtPosition);
						notifyDataSetChanged();
					}else{
						Toast.makeText(mContext, "删除关注失败", Toast.LENGTH_LONG).show();
					}
					
				}
			}else{
//				TODO
			}
		}
		
		
		
	}
}
