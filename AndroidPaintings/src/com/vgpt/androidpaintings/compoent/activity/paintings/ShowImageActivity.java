package com.vgpt.androidpaintings.compoent.activity.paintings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.ExitApplication;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.biz.JSONToMap;
import com.vgpt.androidpaintings.biz.TimeBiz;
import com.vgpt.androidpaintings.compoent.activity.user.PersonInfoActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.PaintingItem;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.DeviceUtil;
import com.vgpt.androidpaintings.utils.LoadBitmapAsyTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;


public class ShowImageActivity extends Activity {

	ImageView imageView,photoView;
	TextView nameText,uploaderText,categoryText,countText,commentText,collectText,recommendText;
	TextView date_onText;
	TextView introductionText;
	Button focusButton;

	LinearLayout commentList;

	RelativeLayout rl;

	View commentView = null;

	int width;

	String  category;
	SharedPreferences userPre;
	int pic_id = 0;
	int user_id = 0;
	int uploader_id = 0;

	int page = 1;

	Boolean isWorking = false;
	Bitmap bm = null;

	final static int SIZE = 3;

	String username =null;

	final static String comment="intent.CommentPage";

	PaintingItem pi = null;
	LinearLayout mLinearLayout;

	List<PaintingItem> paintingItems = new ArrayList<PaintingItem>();
	@SuppressLint("UseSparseArrays")
	Map<Integer, JSONObject> jsonMap = new HashMap<Integer, JSONObject>();

	ArrayList<Integer> pic_idList = new ArrayList<Integer>();

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_image);

		if(savedInstanceState != null){
			savedInstanceState =null;
		}

		ExitApplication.getInstance().addActivity(this);
		DeviceUtil.bePortrait(this);

		MyApplication ma = (MyApplication)this.getApplicationContext();

		user_id = ma.getUser_id();
		username = ma.getUsername();

		Intent intent =getIntent();
		pi = (PaintingItem)intent.getSerializableExtra("paintingItem");
		pic_id = pi.getPic_id();
		uploader_id = pi.getUploader_id();

		findView();

		addListener();

		initCommentList(pic_id);

		initTextView(pi);

		initImageView(pic_id);

		loadImageInfo();

		initFocusedButton(uploader_id);



	}

	@SuppressWarnings("unchecked")
	public void getPaintingInfo(int user_id, final int pic_id) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("pic_id", pic_id);
		AsycTaskUtil atu = new AsycTaskUtil(this, Constant.Painting.PAINTING_SHOW, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject ret = json.optJSONObject("result");
				jsonMap.put(pic_id, ret);
				openJSONMap(ret);

			}
		});

		atu.execute(map);

	}

	public void openJSONMap(JSONObject ret) {
		String name = ret.optString("name");
		String introduction=ret.optString("introduction");
		String up_name = ret.optString("username");
		uploader_id = ret.optInt("up_actor_id");

		nameText.setText(name);
		if(introduction!=null && !introduction.isEmpty()){
			introductionText.setText(introduction);
		}
		uploaderText.setText(up_name);

	}


	AsycTaskUtil atu = new AsycTaskUtil(ShowImageActivity.this, 
			Constant.Painting.PAINTING_LOAD, new OnPostInterface() {

		@Override
		public void code_1(JSONObject json) {

			JSONObject ret = json
					.optJSONObject("result");
			JSONArray items = ret.optJSONArray("items");
			for(int i = 0;i< items.length();i++){
				Map<String, Object> map;
				try {
					map = JSONToMap.toMap(items.optJSONObject(i));

					PaintingItem pi = new PaintingItem(map);

					paintingItems.add(pi);

				} catch (JSONException e) {
					e.printStackTrace();
					LogUtils.v("======JSONException==== JSONTOMAP");
				}

			}

		}
	});


	class QueryListTask extends
	AsyncTask<Map<String, Object>, Integer, JSONObject> {
		int checkCode;
		JSONArray items;
		int pages;

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if (checkCode == 1) {

				for (int i = 0; i < items.length(); i++) {

					JSONObject itemObject = items.optJSONObject(i);
					int pic_id = itemObject.optInt("pic_id");
					pic_idList.add(pic_id);

					if (i == 0) {
						getPaintingInfo(user_id, pic_id);
					}

				}
			}
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
				cl.post(Constant.Painting.PAINTING_LOAD, list,
						new JSONResponseHandler<JSONObject>() {

					@Override
					public JSONObject handlerJson(JSONObject json) {
						if(json!=null){
							checkCode = json.optInt("code");

							if (json.optInt("code") == 1) {

								JSONObject request = json
										.optJSONObject("request");
								pages = request.optInt("page");


							}}

						return json;
					}	
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

			return json;

		}

	}


	@SuppressWarnings("unchecked")
	public void loadImageInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		map.put("user_id", user_id);
		AsycTaskUtil atu = new AsycTaskUtil(this, Constant.Painting.PAINTING_SHOW, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject ret = json.optJSONObject("result");
				jsonMap.put(pic_id, ret);

				openJSONMap(ret);

			}
		});

		atu.execute(map);
	}



	public void findView() {

		imageView = (ImageView) findViewById(R.id.image);

		photoView = (ImageView)  findViewById(R.id.headPhoto);

		categoryText = (TextView) findViewById(R.id.category);

		countText = (TextView) findViewById(R.id.count);

		nameText = (TextView) findViewById(R.id.name);
		uploaderText = (TextView) findViewById(R.id.uploader);
		date_onText = (TextView) findViewById(R.id.date_on);
		introductionText = (TextView) findViewById(R.id.introduction);
		uploaderText = (TextView) findViewById(R.id.uploader);
		commentText = (TextView)findViewById(R.id.comment);
		collectText = (TextView)findViewById(R.id.collect);
		recommendText = (TextView)findViewById(R.id.recommendation);
		focusButton = (Button) findViewById(R.id.focus);

		mLinearLayout = (LinearLayout)findViewById(R.id.linearLayout);

		commentList = (LinearLayout)findViewById(R.id.comment_container);

		rl = (RelativeLayout)findViewById(R.id.rl);



	}

	@SuppressWarnings("unchecked")
	public void initCommentList(int pic_id) {

		/*
		 * 解决溢出菜单不显示问题
		 */
		try {
			ViewConfiguration mconfig = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (Exception ex) {
		}

		LogUtils.v("=======pic_id "+pic_id+"========");


		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		map.put("page", page);
		map.put("size", SIZE);

		AsycTaskUtil atu = new AsycTaskUtil(this, Constant.Painting.GET_COMMENT, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONArray ret = json.optJSONArray("result");
				if(ret!=null){
					displayCommentList(ret);

				}
				else if(ret==null){
					Toast.makeText(ShowImageActivity.this, "暂无评论", Toast.LENGTH_LONG).show();
				}

			}
		});
		atu.execute(map);


	}


	public void addListener() {


		focusButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {
				Map<String,Object> setfocusmap=new HashMap<String,Object>();
				setfocusmap.put("user_id", user_id);
				setfocusmap.put("focus_id", uploader_id);

				AsycTaskUtil atu = new AsycTaskUtil(ShowImageActivity.this, Constant.Api.SET_FOCUS, new OnPostInterface() {

					@Override
					public void code_1(JSONObject json) {

						JSONObject result = json.optJSONObject("result");
						boolean isSuccess = result.optBoolean("isSuccess");
						if(isSuccess ){
							buttonFocused();
							Toast.makeText(ShowImageActivity.this, "关注成功", Toast.LENGTH_SHORT).show();

						}
						else{
							Toast.makeText(ShowImageActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
						}

					}
				});

			}
		});

		uploaderText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if(uploader_id > 0){
					Intent it = new Intent(ShowImageActivity.this,
							PersonInfoActivity.class);
					it.putExtra("person_id", uploader_id);
					LogUtils.v("uploader_id"+uploader_id+"=========================");
					it.putExtra("user_id",user_id);
					it.putExtra("username", uploaderText.getText().toString());
					startActivity(it);
				}

			}

		});

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShowImageActivity.this,
						PictureChangeSizeShowActivity.class);
				intent.putExtra("url", Constant.Painting.GET_PICTURE+pic_id);
				startActivity(intent);

			}
		});



		photoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(ShowImageActivity.this,
						PictureChangeSizeShowActivity.class);
				intent.putExtra("url", Constant.Api.GET_PHOTO+uploader_id);
				startActivity(intent);

			}
		});


	}

	public void initTextView(PaintingItem pi) {

		pic_id = pi.getPic_id();

		LogUtils.v("============"+pic_id+"=============");

		int count_read = pi.getCount_read();
		int count_comment = pi.getCount_comment();
		int  recommendation = pi.getRecommendation();
		countText.setText(count_read+"人气/"+count_comment+"评论/"+recommendation+"推荐");

		commentText.setText("评论  "+count_comment);
		recommendText.setText("推荐  "+recommendation);
		collectText.setText("收藏  "+count_read);

		category = pi.getCategory();
		categoryText.setText(Constant.categoryMap.get(category));

		String date_on = pi.getDate_on();
		date_onText.setText(TimeBiz.getPeriod(date_on));

		String pic_name = pi.getPic_name();
		nameText.setText(pic_name);

		String uploader = pi.getUploader();
		uploaderText.setText(uploader);


		uploader_id = pi.getUploader_id();
		Picasso.with(ShowImageActivity.this).load(Constant.Api.GET_PHOTO+uploader_id).into(photoView);


	}

	@SuppressWarnings("unchecked")
	private void initImageView(int pic_id){
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		width = metrics.widthPixels;


		LogUtils.v("==========="+width+" pic_id"+pic_id);

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pic_id", pic_id);
		LoadBitmapTask task = new LoadBitmapTask(ShowImageActivity.this, width);
		task.execute(map);
	}

	private void initFocusedButton(int uploader_id){
		if(user_id != uploader_id){

			Map<String,Object> map = new HashMap<String, Object>();
			map.put("user_id",user_id);
			map.put("person_id", uploader_id);
			AsycTaskUtil atu = new AsycTaskUtil(ShowImageActivity.this, Constant.Api.GET_OTHERS_INFO, new OnPostInterface() {

				@Override
				public void code_1(JSONObject json) {

					JSONObject result = json.optJSONObject("result");

					boolean isFocus = result.optBoolean("focus");

					if(isFocus){

						buttonFocused();
					}


				}
			});

		}else{
			focusButton.setVisibility(View.INVISIBLE);
		}



	}

	private void displayCommentList(JSONArray ret){
		for(int i=0;i<ret.length();i++){
			final JSONObject jsonItem=ret.optJSONObject(i);

			int uploader_id = jsonItem.optInt("user_id");
			String uploaderName = jsonItem.optString("username");
			String body = jsonItem.optString("body");
			String time = jsonItem.optString("time");

			commentView = LayoutInflater.from(ShowImageActivity.this).inflate(
					R.layout.comment_item, null);

			ImageView photo = (ImageView)commentView.findViewById(R.id.comment_headphoto);

			TextView nameText=(TextView)commentView.findViewById(R.id.name);
			TextView commentText = (TextView) commentView
					.findViewById(R.id.comment);
			TextView dateText=(TextView)commentView.findViewById(R.id.date);

			Picasso.with(ShowImageActivity.this).load(Constant.Api.GET_PHOTO+uploader_id).into(photo);

			nameText.setText(uploaderName);
			nameText.setTag(uploader_id);
			dateText.setText(time);
			commentText.setText(body); 
			photo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(ShowImageActivity.this,PersonInfoActivity.class);
					intent.putExtra("person_id", jsonItem.optInt("user_id"));
					startActivity(intent);

				}
			});

			commentList.addView(commentView);

			LogUtils.v("=====body"+jsonItem.optString("body")+"==========");

		}

	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		int id = item.getItemId();

//		switch(id){
//
//		case R.id.action_collect:
//
//			Map<String,Object> map = new HashMap<String, Object>();
//
//			map.put("user_id", user_id);
//
//			map.put("pic_id", pic_id);
//
//			AsycTaskUtil atu = new AsycTaskUtil(ShowImageActivity.this, Constant.Painting.SET_COL, new OnPostInterface() {
//
//				@Override
//				public void code_1(JSONObject json) {
//
//					JSONObject ret = json
//							.optJSONObject("result");
//					boolean successOrNot = ret.optBoolean("success");
//					if (successOrNot) {
//						Toast.makeText(ShowImageActivity.this, "添加收藏成功",
//								Toast.LENGTH_SHORT).show();
//					} else {
//						Toast.makeText(ShowImageActivity.this, "收藏失败",
//								Toast.LENGTH_SHORT).show();
//					}
//				}
//			});
//
//			atu.execute(map);
//
//			break;
//
//		case R.id.action_comment:	
//
//			final AlertDialog.Builder builder=new Builder(ShowImageActivity.this);
//			View view=getLayoutInflater().inflate(R.layout.comment_edit, null);
//			final EditText et=(EditText)view.findViewById(R.id.commenteditText);
//			builder.setView(view);
//			builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
//
//				@SuppressWarnings("unchecked")
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//
//					String content=et.getText().toString().replaceAll("(^\\s{1,})|(\\s{1,}$)" , "");
//					if(content!=null && !content.equals("")){
//						Map<String,Object> map=new HashMap<String, Object>();
//						map.put("pic_id", pic_id);
//						map.put("user_id", user_id);
//						map.put("body", content);
//						AsycTaskUtil atu = new AsycTaskUtil(ShowImageActivity.this, Constant.Painting.SET_COMMENT, new OnPostInterface() {
//
//							@Override
//							public void code_1(JSONObject json) {
//
//								JSONObject ret = json.optJSONObject("result");
//
//								boolean isSuccess=ret.optBoolean("success");
//								if(isSuccess){
//
//									Toast.makeText(ShowImageActivity.this, "发表评论成功", Toast.LENGTH_LONG).show();
//								}else{
//									Toast.makeText(ShowImageActivity.this, "发表评论失败", Toast.LENGTH_LONG).show();
//								}
//
//							}
//						});
//						atu.execute(map);
//					}
//				}
//			});
//			builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					builder.show().dismiss();
//
//				}
//			});
//			builder.show();
//
//		}

		return super.onMenuItemSelected(featureId, item);
	}

	class LoadBitmapTask extends LoadBitmapAsyTaskUtil{

		public LoadBitmapTask(Context context, int width) {
			super(context, width);
		}

		@Override
		public void postExecute(Bitmap t, Map<String, Object> map) {

			float ratio = t.getWidth()/(width*1.0f);

			Matrix matrix = new Matrix();
			matrix.postScale(1/ratio, 1/ratio);
			Bitmap resizeBmp = Bitmap.createBitmap(t, 0, 0,
					t.getWidth(), t.getHeight(), matrix, true);

			imageView.setImageBitmap(resizeBmp);

		}

	}
	private void buttonFocused(){
		focusButton.setClickable(false);
		focusButton.setText(R.string.focused);
		focusButton.setBackgroundColor(Color.GRAY);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

				Toast.makeText(ShowImageActivity.this, "ddddd", Toast.LENGTH_SHORT).show();

		return super.dispatchTouchEvent(ev);
	}

}
