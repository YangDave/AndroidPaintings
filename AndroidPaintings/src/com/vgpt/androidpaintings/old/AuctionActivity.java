package com.vgpt.androidpaintings.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.AuctionItem;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;


public class AuctionActivity extends Activity{
	
	TextView nameText,authorText,createDateText,uploaderText,dateOnText,sizeText
	,currentPriceText,remainTimeText,introductionText,countText;
	EditText setPriceEdit;
	ImageView image;
	
	int pic_id;
	int user_id;
	int auction_id;
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auction);
		
		nameText=(TextView)findViewById(R.id.name);
		authorText=(TextView)findViewById(R.id.author);
		createDateText=(TextView)findViewById(R.id.creation_date);
		uploaderText=(TextView)findViewById(R.id.uploader);
		dateOnText=(TextView)findViewById(R.id.date_on);
		sizeText=(TextView)findViewById(R.id.size);
		currentPriceText=(TextView)findViewById(R.id.current_price);
		remainTimeText=(TextView)findViewById(R.id.remain_time);
		introductionText=(TextView)findViewById(R.id.introduction);
		countText=(TextView)findViewById(R.id.count);
		
		setPriceEdit=(EditText)findViewById(R.id.set_price);
		
		image=(ImageView)findViewById(R.id.image);
		
		Intent intent=getIntent();
		
		user_id=intent.getIntExtra("user_id", 0);
		AuctionItem ai=(AuctionItem) intent.getSerializableExtra("auctionItem");
		
		pic_id=ai.getPic_id();
		auction_id=ai.getAuction_id();
		
		currentPriceText.setText(String.valueOf(ai.getCurrent_price()));
		
		remainTimeText.setText(String.valueOf(ai.getRemain_time()));
		
		nameText.setText(ai.getPic_name());
		
		introductionText.setText(ai.getIntroduction());
		
		
		
		Picasso.with(AuctionActivity.this).load(Constant.Painting.GET_PICTURE+pic_id).into(image);
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		map.put("auction_id", auction_id);
		
		GetAuctionPicTask task=new GetAuctionPicTask();
		task.execute(map);
		
		
		
		
		
		
		
		
		
	}
	
	
	
	public void loadJSONObjectToView(JSONObject ret){
		
		currentPriceText.setText(String.valueOf(ret.optInt("current_price")));
		remainTimeText.setText(String.valueOf(ret.optInt("remain_time")));
		introductionText.setText(ret.optString("introduction"));
		authorText.setText(ret.optString("author"));
		countText.setText(String.valueOf(ret.optInt("count")));
		sizeText.setText(ret.optInt("width")+"X"+ret.optInt("high"));
		createDateText.setText(ret.optString("date_creation"));
		dateOnText.setText(ret.optString("date_on"));
		uploaderText.setText(ret.optString("username"));
		uploaderText.setTag(ret.optInt("user_id"));
		
		
		
	}
	
	class GetAuctionPicTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		
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

				cl.post(Constant.Association.GET_ASSO_INFO, list,
						new JSONResponseHandler<JSONObject>() {

							@Override
							public JSONObject handlerJson(JSONObject json) {
								if (json != null) {
									checkCode=json.optInt("code");
									if(checkCode == 1){
									ret=json.optJSONObject("result");

								}
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
			
			if(checkCode==1 && ret!=null){
				
				loadJSONObjectToView(ret);
			}
			else{
//				TODO
			}
		}
		
		
		
	}
	

}
