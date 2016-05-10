package com.vgpt.androidpaintings.compoent.activity.association;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.biz.BitmapExchangeDrawable;
import com.vgpt.androidpaintings.biz.SaveToLocalPainting;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.FileAsycTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class AssociationCreate extends MyActivity{

	EditText asso_nameText;
	EditText asso_introductionText,organizorText,chairmanText;
	ImageView markView;
	Bitmap bm;
	boolean isSetBm;
	Button okButton;
	Button cancelButton;
	final int PICTURE = 1000;
	final int CAMERA = 2000;
	String filePath;
	
	final static String NAME="name";
	final static String INTRO = "introduction";
	final static String ORGANIZOR = "organizor";
	final static String CHAIRMAN = "chairman";
	

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

		super.onRestoreInstanceState(savedInstanceState);
		
	}
	
	

	@Override
	public void onBackPressed() {

		this.finish();
		super.onBackPressed();
		
	}



	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		outState.putString(NAME, asso_nameText.getText().toString().trim());
		outState.putString(INTRO, asso_introductionText.getText().toString().trim());
		outState.putString(ORGANIZOR, organizorText.getText().toString().trim());
		outState.putString(CHAIRMAN, chairmanText.getText().toString().trim());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_create);

		loadPage(this);
		if(savedInstanceState != null){
			
			LogUtils.v("============bundle="+savedInstanceState.getString(NAME));
			asso_nameText.setText(savedInstanceState.getString(NAME));
			asso_introductionText.setText(savedInstanceState.getString(INTRO));
			organizorText.setText(savedInstanceState.getString(ORGANIZOR));
			chairmanText.setText(savedInstanceState.getString(CHAIRMAN));
			
		}

	}

	@Override
	public void findView() {

		asso_nameText=(EditText)findViewById(R.id.asso_name_edit);
		asso_introductionText=(EditText)findViewById(R.id.asso_introduction);
		organizorText = (EditText)findViewById(R.id.asso_organizor);
		chairmanText = (EditText)findViewById(R.id.asso_chairman);
		markView = (ImageView)findViewById(R.id.mark);

		
		okButton=(Button)findViewById(R.id.ok);
		cancelButton=(Button)findViewById(R.id.cancel);

	}

	@Override
	public void prepareData() {


	}

	@Override
	public void addListener() {
		
		markView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				final AlertDialog.Builder builder = new Builder(
						AssociationCreate.this);
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

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AssociationCreate.this.finish();

			}
		});
		
		okButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    final String	asso_name=asso_nameText.getText().toString().trim();
				String asso_introduction=asso_introductionText.getText().toString().trim();
				String chairman = chairmanText.getText().toString().trim();
				String organizor = organizorText.getText().toString().trim();
					if((StringUtils.isNotBlank(asso_name))&&(StringUtils.isNotBlank(asso_introduction))
							&&StringUtils.isNotBlank(chairman) && StringUtils.isNotBlank(organizor) && isSetBm){
						Map<String,Object> map=new HashMap<String, Object>();
						map.put("asso_name", asso_name);
						map.put("introduction",asso_introduction);
						map.put("chairman", chairman);
						map.put("orginator", organizor);
						map.put("production", null);
						map.put("symbol", asso_name+".jpeg");
						map.put("user_id", user_id);
						FileAsycTaskUtil fatu = new FileAsycTaskUtil(AssociationCreate.this,
								Constant.Association.REGISTER_ASSO, filePath,"Symbol", new OnPostInterface() {
									
									@Override
									public void code_1(JSONObject json) {
										
										JSONObject result = json.optJSONObject("result");
										boolean isSuccess = result.optBoolean("success");
										if(isSuccess){
											int asso_id = result.optInt("asso_id");
											createAssoSuccess(asso_id,asso_name);
										}else{
											Toast.makeText(AssociationCreate.this,
													R.string.asso_create_fail, Toast.LENGTH_LONG).show();
										}
										
										
										
									}
								});
						
						fatu.execute(map);
					}
			}
		});

	}

	@Override
	public void showContent() {
		// TODO Auto-generated method stub

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
			Drawable drawable = Drawable.createFromPath(new File(Environment
					.getExternalStorageDirectory(), "camera.jpg")
			.getAbsolutePath());
			bm = BitmapExchangeDrawable.drawableToBitmap(drawable);
			System.out.println("data-->" + data);
			isSetBm=true;
			String picName = "asso_mark.jpg";

			SaveToLocalPainting.savePaintingToUpload(bm,picName);
			
			filePath=Constant.MainDir+picName;
			
		} else if (requestCode == PICTURE && resultCode == Activity.RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumns={MediaStore.Images.Media.DATA};
			Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			filePath= c.getString(columnIndex);
			isSetBm=true;
			bm=BitmapFactory.decodeFile(filePath);
			c.close();


		}
		LogUtils.v("user_id"+user_id);
		markView.setImageBitmap(bm);

	}
	
	private void createAssoSuccess(int asso_id,String asso_name){
		
		Toast.makeText(AssociationCreate.this, R.string.asso_create_success, Toast.LENGTH_LONG).show();
//		Intent intent = new Intent(AssociationCreate.this,AssociationActivity.class);
//		Bundle data = new Bundle();
//		Association asso = new Association(asso_name, null, asso_id);
//		data.putSerializable("association", asso);
//		intent.putExtras(data);
//		startActivity(intent);
//		AssociationCreate.this.finish();
	}


}
