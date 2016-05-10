package com.vgpt.androidpaintings.compoent.activity.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.biz.BitmapExchangeDrawable;
import com.vgpt.androidpaintings.biz.SaveToLocalPainting;
import com.vgpt.androidpaintings.compoent.activity.paintings.PictureChangeSizeShowActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.PersonInfo;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.FileAsycTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class UserDataActivity extends MyActivity{

	final static int INFORESET=18;
	final static int REQCODE = 5000;
	final static int PICTURE=11;
	final static int CAMERA=10;

	TextView nameText;
	TextView ageText;
	TextView sexText;
	TextView phoneText;
	TextView birthdayText;
	TextView introductionText;
	ImageView imageView;

	Bitmap bm;

	String name;
	String age;
	String phone;
	String sex;
	String address;
	String birthday;
	String introduction;
	String photo_name;
	String picturePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_user_data);

		loadPage(UserDataActivity.this);


	}






	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.user_data_item, menu);
		return super.onCreateOptionsMenu(menu);
	}






	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		int id = item.getItemId();
		if(id == R.id.change_data){
			
				Bundle persondata=new Bundle();
				PersonInfo personInfo=new PersonInfo(name, age, phone, sex, birthday, introduction);
				persondata.putSerializable("personInfo", personInfo);
				Intent infoIntent=new Intent(UserDataActivity.this,InfoChangeActivity.class);
				infoIntent.putExtras(persondata);
				startActivityForResult(infoIntent, REQCODE);
			
		}
		return super.onMenuItemSelected(featureId, item);
		
		
	}






	@Override
	public void findView() {

		nameText=(TextView)findViewById(R.id.realname);
		ageText=(TextView)findViewById(R.id.age);
		sexText=(TextView)findViewById(R.id.sex);
		phoneText=(TextView)findViewById(R.id.phone);
		birthdayText=(TextView)findViewById(R.id.birthday);
		introductionText=(TextView)findViewById(R.id.introduction);
		imageView = (ImageView)findViewById(R.id.headPhoto);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepareData() {


		LogUtils.v("===========userData.user_id=="+this.user_id+"===========");

		refresh();




	}
	
	@SuppressWarnings("unchecked")
	private void refresh(){
		Map<String,Object> getInfoMap=new HashMap<String,Object>();
		LogUtils.v("=======ready to get from web");
		getInfoMap.put("user_id", user_id);
		AsycTaskUtil atu = new AsycTaskUtil(this, Constant.Api.INFO, new OnPostInterface() {
			
			@Override
			public void code_1(JSONObject json) {
				
				postExecute(json);
				
			}
		});
		
		atu.execute(getInfoMap);
	}

	@Override
	public void addListener() {
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final AlertDialog.Builder builder=new Builder(UserDataActivity.this);
				builder.setItems(getResources().getStringArray(R.array.PaintingItemsArray), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface v, int postion) {
						switch(postion){
						// local photo
						case 0: {
							Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							startActivityForResult(picture, PICTURE);
						}
						break;
						// local camera
						case 1: {
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							intent.putExtra(
									MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(new File(
											Environment
											.getExternalStorageDirectory(),
											"camera.jpg")));
							intent.putExtra(
									MediaStore.EXTRA_VIDEO_QUALITY, 0);
							startActivityForResult(intent, CAMERA);
						}
						break;
						case 2:{
							Intent intent = new Intent(UserDataActivity.this,PictureChangeSizeShowActivity.class);
							intent.putExtra("url", Constant.Api.GET_PHOTO+user_id);
							startActivity(intent);
							
						}
						break;
						default:
							builder.show().dismiss();
						}

					}
				});
				
				builder.show();

			}
		});


	}

	@Override
	public void showContent() {

		Picasso.with(UserDataActivity.this).load(Constant.Api.GET_PHOTO+user_id).into(imageView);

	}

	
	public void postExecute(JSONObject json){
		
		JSONObject result = json.optJSONObject("result");
		name=result.optString("name");
		age=result.optString("age");
		phone=String.valueOf(result.optInt("phone"));
		sex=result.optBoolean("sex")?"男":"女";
		birthday=result.optString("birthday");
		address=result.optString("address");
		introduction=result.optString("introduction");

		nameText.setText(name);
		ageText.setText(age);
		phoneText.setText(phone);
		sexText.setText(sex);
		birthdayText.setText(birthday);
		introductionText.setText(introduction);

		LogUtils.v("info = "+address+" "+introduction+" "+birthday);

	}

	@SuppressWarnings("unchecked")
	public void onActivityResult(int requestCode,int resultCode,Intent data){

		if(requestCode==REQCODE && resultCode==INFORESET){
			refresh();

		}

		if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
			Drawable drawable = Drawable.createFromPath(new File(Environment
					.getExternalStorageDirectory(), "camera.jpg")
			.getAbsolutePath());
			bm = BitmapExchangeDrawable.drawableToBitmap(drawable);
			System.out.println("data-->" + data);
			SaveToLocalPainting.saveHeadPhoto(user_id, bm);
			picturePath=Constant.FileDir.HEADPHOTO+user_id+".jpeg";
		} else if (requestCode == PICTURE && resultCode == Activity.RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumns={MediaStore.Images.Media.DATA};
			Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			picturePath= c.getString(columnIndex);
			bm=BitmapFactory.decodeFile(picturePath);
			c.close();

		}

		if(bm!=null){
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("user_id", user_id);
			map.put("filename",user_id+".jpeg");
			FileAsycTaskUtil fatu = new FileAsycTaskUtil(UserDataActivity.this, 
					Constant.Api.CHANGE_PHOTO, picturePath,"HeadPhoto", new OnPostInterface() {
						
						@Override
						public void code_1(JSONObject json) {
							
							JSONObject result = json.optJSONObject("result");
							boolean isSuccess = result.optBoolean("success");
							
							if(isSuccess){
								
								imageView.setImageBitmap(bm);
								
							}
							
							
						}
					});
			
			fatu.execute(map);


		}


	}

}
