package com.vgpt.androidpaintings.compoent.activity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.activity.dialog.WaitingDialog;
import com.vgpt.androidpaintings.compoent.activity.paintings.PictureChangeSizeShowActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;


/**
 * @author Charles
 *
 */
public class PersonInfoActivity extends MyActivity{


	TextView usernameText;
	TextView personageText;
	TextView personsexText;
	TextView personPhoneText;
	TextView personbirthdayText;
	TextView personintroduction;
	ImageView photo;
	int person_id;
	WaitingDialog dialog;
	MenuItem item;
	@SuppressLint("HandlerLeak")
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_data);
		
		findViewById(R.id.bottom_image_text).setVisibility(View.GONE);

		dialog=new WaitingDialog(PersonInfoActivity.this);
		dialog.createDialog();
		dialog.show();
		loadPage(this);

		//		UserInfoSingleInstance uisi = UserInfoSingleInstance.getInstance();
		//		user_id=uisi.getUser_id();
		//		username=uisi.getUsername();



		//		if(user_id==person_id){
		//			button.setVisibility(View.GONE);
		//		}
		//		





		//        button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//			
		//			@Override
		//			public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		//				
		//				if(!isChecked){
		//					Map<String,Object> setfocusmap=new HashMap<String,Object>();
		//					setfocusmap.put("user_id", user_id);
		//					setfocusmap.put("focus_id", person_id);
		//					SetFocusTask setfocus=new SetFocusTask();
		//					setfocus.execute(setfocusmap);
		//				}
		//				else{
		//					Map<String,Object> delfocusmap=new HashMap<String,Object>();
		//					delfocusmap.put("user_id", user_id);
		//					delfocusmap.put("focus_id", person_id);
		//					DelFocusTask delfocus=new DelFocusTask();
		//					delfocus.execute(delfocusmap);
		//				}
		//			}
		//		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

//		getMenuInflater().inflate(R.menu.person_info_menu, menu);
		item = menu.add(getString(R.string.focus));
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Map<String,Object> setfocusmap=new HashMap<String,Object>();
				setfocusmap.put("user_id", user_id);
				setfocusmap.put("focus_id", person_id);
				SetFocusTask setfocus=new SetFocusTask();
				setfocus.execute(setfocusmap);

				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}





	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}





	class GetPersonInfoTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		private int checkCode;
		JSONObject ret;

		@Override
		protected JSONObject doInBackground(Map<String, Object>... param) {
			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(param[0]);

			NameValuePair pa = new BasicNameValuePair("json", json.toString());
			list.add(pa);

			try {
				cl.post(Constant.Api.GET_OTHERS_INFO, list,
						new JSONResponseHandler<JSONObject>() {

					@Override
					public JSONObject handlerJson(JSONObject json) {
						if (json != null ) {
							checkCode=json.optInt("code");
							if(checkCode==1){
								ret = json
										.optJSONObject("result");

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

			dialog.dismiss();

			if(checkCode==1){
				if(ret!=null){


					String birthday=ret.optString("birthday");
					int age=ret.optInt("age");
					String introduction=ret.optString("introduction");
					Boolean focused=ret.optBoolean("focus");
					usernameText.setText(ret.optString("name"));
					String sex = "男";
					if(ret.optInt("sex")==0){
						sex = "女";
					}
					if(focused){
						item.setTitle(R.string.focused);
						item.setCheckable(false);
					}

					personageText.setText(String.valueOf(age));
					personbirthdayText.setText(birthday);
					personintroduction.setText(introduction);

					personsexText.setText(sex);

				}
			}else{
				//			TODO
			}

		}


	}
	public class SetFocusTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{

		private int checkCode;
		private Boolean isSuccess=false;

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {
			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(params[0]);

			NameValuePair pa = new BasicNameValuePair("json", json.toString());
			list.add(pa);

			try {
				cl.post(Constant.Api.SET_FOCUS, list,
						new JSONResponseHandler<JSONObject>() {

					@Override
					public JSONObject handlerJson(JSONObject json) {
						if(json!=null){

							checkCode=json.optInt("code");
							if(json.optInt("code")==1){
								JSONObject result=json.optJSONObject("result");
								if(result!=null){
									isSuccess=result.optBoolean("success");
								}
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
			if(isSuccess && (checkCode==1)){
				item.setTitle(R.string.focused);
				Toast.makeText(PersonInfoActivity.this, "关注成功", Toast.LENGTH_LONG).show();

			}
			else{
				Toast.makeText(PersonInfoActivity.this, "关注失败", Toast.LENGTH_LONG).show();
			}
		}


	}


	class DelFocusTask extends AsyncTask<Map<String,Object>, Integer, JSONObject>{
		private int checkCode;
		private Boolean isSuccess;

		@Override
		protected JSONObject doInBackground(Map<String, Object>... params) {
			MyHttpClient cl = MyHttpClient.getInstance();

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			JSONObject json = new JSONObject(params[0]);

			NameValuePair pa = new BasicNameValuePair("json", json.toString());
			list.add(pa);

			try {
				cl.post(Constant.Api.DEL_FOCUS, list,
						new JSONResponseHandler<JSONObject>() {

					@Override
					public JSONObject handlerJson(JSONObject json) {
						if(json!=null){
							checkCode=json.optInt("code");

							if(json.optInt("code")==1){
								JSONObject result=json.optJSONObject("result");
								if(result!=null){
									isSuccess=result.optBoolean("success");
								}
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
			if((checkCode==1)&& isSuccess){
				Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_LONG).show();
			}

		}

	}


	@Override
	public void findView() {
		photo = (ImageView)findViewById(R.id.headPhoto);
		usernameText = (TextView)findViewById(R.id.realname);

		personageText=(TextView)findViewById(R.id.age);
		personsexText=(TextView)findViewById(R.id.sex);
		personPhoneText = (TextView)findViewById(R.id.phone);

		personbirthdayText=(TextView)findViewById(R.id.birthday);
		personintroduction=(TextView)findViewById(R.id.introduction);

	}


	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {
		Intent it=getIntent();
		person_id=it.getIntExtra("person_id", 0);

//		Toast.makeText(PersonInfoActivity.this,
//				"person_id "+person_id+" user_id "+user_id, Toast.LENGTH_LONG).show();

		Map<String,Object> map=new HashMap<String, Object>();
		//	TODO
		map.put("user_id", user_id);
		map.put("person_id", person_id);
		GetPersonInfoTask getInfo=new GetPersonInfoTask();
		getInfo.execute(map);

	}


	@Override
	public void addListener() {
		photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent(PersonInfoActivity.this,PictureChangeSizeShowActivity.class);
				intent.putExtra("url", Constant.Api.GET_PHOTO+person_id);
				startActivity(intent);
				
			}
		});

	}


	@Override
	public void showContent() {

		Picasso.with(PersonInfoActivity.this)
		.load(Constant.Api.GET_PHOTO+String.valueOf(person_id)).into(photo);
	}
}
