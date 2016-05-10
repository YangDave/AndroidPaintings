package com.vgpt.androidpaintings.compoent.activity.paintings;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyNoActionBarActivity;
import com.vgpt.androidpaintings.biz.BitmapExchangeDrawable;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.FileAsycTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class PaintingUploadActivity extends MyNoActionBarActivity{
	ImageView paintingView;
	Button creationDateButton;
	Button categoryButton;
	EditText paintingnameText;
	EditText heightText;
	EditText widthText;
	TextView uploader;
	EditText introductionText;
	EditText authorText;
	Bitmap bm;
	Boolean isSetBm=false;
	Boolean isSetDate=false;
	String paintingname;
	String height;
	String width;
	String introduction;
	String date;
	String category;
	String author;
	String picturePath;
	Button okBt,cancelBt;

	final static int PICTURE=11;
	final static int CAMERA=10;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_painting_upload);
//  TODO recreate method
		loadPage(this);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
			Drawable drawable = Drawable.createFromPath(new File(Environment
					.getExternalStorageDirectory(), "camera.jpg")
			.getAbsolutePath());
			bm = BitmapExchangeDrawable.drawableToBitmap(drawable);
			System.out.println("data-->" + data);
			isSetBm=true;

			picturePath=new File(Environment
					.getExternalStorageDirectory(), "camera.jpg")
			.getAbsolutePath();
			
		} else if (requestCode == PICTURE && resultCode == Activity.RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumns={MediaStore.Images.Media.DATA};
			Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			picturePath= c.getString(columnIndex);
			isSetBm=true;
			bm=BitmapFactory.decodeFile(picturePath);
			c.close();

		}
		LogUtils.v("user_id"+user_id);
		paintingView.setImageBitmap(bm);

	}


	@Override
	public void findView() {

		paintingView=(ImageView)findViewById(R.id.image);
		creationDateButton=(Button)findViewById(R.id.creation_date);
		categoryButton=(Button)findViewById(R.id.category);

		paintingnameText=(EditText)findViewById(R.id.paintingname);
		heightText=(EditText)findViewById(R.id.height);
		widthText=(EditText)findViewById(R.id.width);
		authorText=(EditText)findViewById(R.id.author);
		introductionText=(EditText)findViewById(R.id.paintingintroduction);

		uploader=(TextView)findViewById(R.id.uploader);

		okBt = (Button)findViewById(R.id.ok);
		cancelBt = (Button)findViewById(R.id.cancel);

	}

	@Override
	public void prepareData() {


	}

	@Override
	public void addListener() {

		creationDateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar c = Calendar.getInstance();

				new DatePickerDialog(PaintingUploadActivity.this,
						new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {


						creationDateButton.setText(year + "-" + monthOfYear
								+ "-" + dayOfMonth);
						date=year + "-" + monthOfYear
								+ "-" + dayOfMonth;
						isSetDate=true;
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
				.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		paintingView.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				final AlertDialog.Builder builder = new Builder(
						PaintingUploadActivity.this);
				builder.setItems(
						getResources().getStringArray(
								R.array.PaintingItemsArray),
								new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int position) {
								switch (position) {
								case 0: {
									Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
									startActivityForResult(picture, PICTURE);
								}
								break;
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

								default:
									builder.show().dismiss();
								}
							}
						});
				builder.show();

			}
		});

		categoryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final AlertDialog.Builder builder=new Builder(PaintingUploadActivity.this);
				builder.setItems(getResources().getStringArray(R.array.CategoryArray), new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int position) {
						if(position<5){
							category= Constant.categorys[position];

							String	category_CN=Constant.categoryMap.get(category);

							categoryButton.setText(category_CN);

						}
						else{
							builder.show().dismiss();
						}

					}


				});
				builder.show();

			}
		});

		okBt.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {

				paintingname=paintingnameText.getText().toString();
				width=widthText.getText().toString();
				height=heightText.getText().toString();
				introduction=introductionText.getText().toString();
				author=authorText.getText().toString();
				if( isSetBm && isSetDate &&(StringUtils.isNotBlank(paintingname))
						&& (StringUtils.isNotBlank(width))&&(StringUtils.isNotBlank(height))
						&&(StringUtils.isNotBlank(introduction))&&(StringUtils.isNotBlank(category))
						&&(StringUtils.isNotBlank(author))){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("pic_name", paintingname);
					map.put("name",date+".jpeg");
					map.put("user_id", user_id);
					map.put("category", category);
					map.put("author", author);
					map.put("introduction", introduction);
					map.put("date_creation", date);
					map.put("high", Integer.parseInt(height));
					map.put("width", Integer.parseInt(width));
					FileAsycTaskUtil fatu = new FileAsycTaskUtil(PaintingUploadActivity.this, Constant.Painting.UPLOAD,
							picturePath,"picture", new OnPostInterface() {
								
								@Override
								public void code_1(JSONObject json) {
									
									JSONObject ret = json.optJSONObject("result");
									postUpload(ret.optBoolean("success"));
									
									
								}
							});
					
					fatu.execute(map);
					
				}
				else{
					Toast.makeText(PaintingUploadActivity.this, "请填写完整", Toast.LENGTH_LONG).show();
				}

			}
		});

		cancelBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				PaintingUploadActivity.this.finish();
			}
		});

	}

	@Override
	public void showContent() {

		uploader.setText(username);

	}
	
	private void postUpload(boolean isSuccess){
		if(isSuccess){
			Toast.makeText(PaintingUploadActivity.this, "上传成功", Toast.LENGTH_LONG).show();
			AlertDialog.Builder uSBuilder=new Builder(PaintingUploadActivity.this);
			uSBuilder.setItems(getResources().getStringArray(R.array.uploadSuccess),new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int position) {
					switch(position){
					case 0:{
						paintingView.setImageResource(R.drawable.ic_launcher);
						paintingnameText.setText("");
						widthText.setText("");
						heightText.setText("");
						introductionText.setText("");


					}break;
					case 1:{
						Intent homePageIntent=new Intent(PaintingUploadActivity.this,MainActivity.class);
						startActivity(homePageIntent);
						PaintingUploadActivity.this.finish();

					}break;
					}

				}
			});
			uSBuilder.show();

		}
		else{
			Toast.makeText(PaintingUploadActivity.this, "上传失败", Toast.LENGTH_LONG).show();
		}
	}




}
