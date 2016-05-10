package com.vgpt.androidpaintings.compoent.activity.auction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.biz.JSONToMap;
import com.vgpt.androidpaintings.compoent.activity.paintings.PictureChangeSizeShowActivity;
import com.vgpt.androidpaintings.compoent.adapter.AuctionRecordAdapter;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.AuctionItem;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.AsycTaskWithWorkingUtil;
import com.vgpt.androidpaintings.utils.AsycTaskWithWorkingUtil.OnPostWithWorkingInterface;
import com.vgpt.androidpaintings.utils.LogUtils;

public class AuctionPaintingActivity extends MyActivity {

	TextView uploaderNameText,nameText,categoryText,current_priceText,auction_timesText,remain_timeText;

	ImageView mImageView;

	Button setPriceBt;

	EditText priceEdit;

	ListView auctionRecordList;
	
	AuctionRecordAdapter adapter = null;

	private final int SIZE = 10;

	private int page = 1;
	
	private boolean isWorking = false;
	
	int pic_id,auction_id,current_price,remain_time,price_times;

	String category,pic_name,uploaderName,pic_url,remain_timeStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auction);

		loadPage(AuctionPaintingActivity.this);
		

	}

	@Override
	public void findView() {

		uploaderNameText = (TextView)findViewById(R.id.uploaderName);

		nameText = (TextView)findViewById(R.id.name);

		categoryText = (TextView)findViewById(R.id.category);

		current_priceText = (TextView)findViewById(R.id.current_price);

		auction_timesText = (TextView)findViewById(R.id.auction_times);

		remain_timeText = (TextView)findViewById(R.id.remain_time);

		mImageView = (ImageView)findViewById(R.id.image);

		setPriceBt = (Button)findViewById(R.id.setPriceBt);

		priceEdit = (EditText)findViewById(R.id.price_edit);

		auctionRecordList = (ListView)findViewById(R.id.auction_recordList);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {



		Intent intent = getIntent();

		adapter = new AuctionRecordAdapter(AuctionPaintingActivity.this, new ArrayList<Map<String,Object>>());

		auctionRecordList.setAdapter(adapter);

		AuctionItem ai = (AuctionItem)intent.getSerializableExtra("auctionItem");

		pic_id = ai.getPic_id();
		auction_id = ai.getAuction_id();
		current_price = ai.getCurrent_price();
		remain_time = ai.getRemain_time();
		price_times = ai.getPrice_times();
		category = ai.getCategory();
		pic_name = ai.getPic_name();
		uploaderName = ai.getUploaderName();
		pic_url = Constant.Painting.GET_PICTURE+pic_id;
		
		LogUtils.v("========pic_id "+pic_id);

		int hours = remain_time/60;
		int minutes = remain_time%60;

		LogUtils.v("auction_id "+auction_id);
		remain_timeStr = hours+"时"+minutes+"分";

		getAuctionList();


	}

	@Override
	public void addListener() {


		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AuctionPaintingActivity.this,PictureChangeSizeShowActivity.class);

				intent.putExtra("url", pic_url);

				startActivity(intent);

			}
		});

		setPriceBt.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {

				String price = priceEdit.getText().toString().trim();
				if(price.isEmpty() || price.equals("")){

					Toast.makeText(AuctionPaintingActivity.this, R.string.price_null_not_allowed, Toast.LENGTH_LONG).show();

					priceEdit.setFocusable(true);

				}else if(Integer.parseInt(price) <= current_price){

					Toast.makeText(AuctionPaintingActivity.this, R.string.price_under_current_warnning, Toast.LENGTH_LONG).show();

					priceEdit.setFocusable(true);

				}else{

					Map<String,Object> map = new HashMap<String, Object>();
					map.put("user_id", user_id);
					map.put("pic_id", pic_id);
					map.put("price", Integer.parseInt(price));
					
					
					
					AsycTaskUtil atu = new AsycTaskUtil(AuctionPaintingActivity.this, Constant.Auction.SET_PRICE, new OnPostInterface() {
						
						@Override
						public void code_1(JSONObject json) {
							
							JSONObject ret=json.optJSONObject("result");
							
							if(ret !=null ){
								if(ret.optBoolean("success")){
									Toast.makeText(AuctionPaintingActivity.this,R.string.set_price_success, Toast.LENGTH_LONG).show();
									page=1;

									getAuctionList();

								}else{
									Toast.makeText(AuctionPaintingActivity.this, R.string.set_price_failure, Toast.LENGTH_LONG).show();
								}

							}
							
						}
					});
					atu.execute(map);

				}

			}
		});

		auctionRecordList.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView alv, int scrollState) {
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE&&!isWorking){

					getAuctionList();
				}

			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

			}
		});


	}

	@Override
	public void showContent() {

		Picasso.with(AuctionPaintingActivity.this).load(pic_url).into(mImageView);

		current_priceText.setText(current_price+"");

		auction_timesText.setText(price_times+"人出价");

		remain_timeText.setText(remain_timeStr);

		nameText.setText(pic_name);

		uploaderNameText.setText(uploaderName);

		categoryText.setText(category);


	}
	
	



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("unchecked")
	private void getAuctionList(){

		isWorking = true;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("auction_id", auction_id);
		map.put("page", page);
		map.put("size", SIZE);
		
		AsycTaskWithWorkingUtil atu = new AsycTaskWithWorkingUtil(this, Constant.Auction.GET_PRICE_RECORD, new OnPostWithWorkingInterface() {
			
			@Override
			public void code_1(JSONObject json) {
				
				JSONArray items = json.optJSONArray("result");
				
				if(items !=null && (items.length() != 0)){
					for(int i = 0; i<items.length(); i++){

						JSONObject item = items.optJSONObject(i);

						try {
							Map<String,Object> map = JSONToMap.toMap(item);
							adapter.getlistItems().add(map);
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
					page++;
					
					adapter.notifyDataSetChanged();
				}
				
			}

			@Override
			public void finishPost() {
				isWorking = false;
				
			}
		});
		
		atu.execute(map);


	}

}
