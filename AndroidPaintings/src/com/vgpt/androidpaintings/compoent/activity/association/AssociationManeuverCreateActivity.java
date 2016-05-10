package com.vgpt.androidpaintings.compoent.activity.association;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;

public class AssociationManeuverCreateActivity extends MyActivity implements OnFocusChangeListener{

	Button mOkBt,mCancelBt;
	EditText mTitleView,mBodyView,mPlaceView,mStartView,mEndView;
	TextView assoNameText;
	int asso_id;
	String asso_name;

	String picturePath;

	final static int PICTURE=11;
	final static int CAMERA=10;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association_maneuver_add);
		

		loadPage(this);

	}

	@Override
	public void findView() {

		mTitleView = (EditText)findViewById(R.id.exer_title);
		mBodyView = (EditText)findViewById(R.id.exer_body);
		mStartView = (EditText)findViewById(R.id.exer_begin);
		mEndView = (EditText)findViewById(R.id.exer_end);
		mPlaceView = (EditText)findViewById(R.id.exer_place);

		mOkBt = (Button)findViewById(R.id.maneuver_ok);
		mCancelBt = (Button)findViewById(R.id.maneuver_cancel);
		assoNameText = (TextView)findViewById(R.id.asso_name);
	}

	@Override
	public void prepareData() {

		Intent intent = getIntent();
		asso_id = intent.getIntExtra("asso_id", 0);
		asso_name = intent.getStringExtra("asso_name");


	}

	@Override
	public void addListener() {

		mCancelBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				AssociationManeuverCreateActivity.this.finish();
			}
		});

		mStartView.setOnFocusChangeListener(this);
		mEndView.setOnFocusChangeListener(this);

		mOkBt.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {

				String title = mTitleView.getText().toString().trim();
				String body = mBodyView.getText().toString().trim();
				String startTime = mStartView.getText().toString().trim();
				String endTime = mEndView.getText().toString().trim();
				String place = mPlaceView.getText().toString().trim();
				if((StringUtils.isNotBlank(title))
						&& (StringUtils.isNotBlank(body))&&(StringUtils.isNotBlank(startTime))
						&&(StringUtils.isNotBlank(endTime))&&(StringUtils.isNotBlank(place))){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("asso_id", asso_id);
					map.put("title", title);
					map.put("body", body);
					map.put("exer_begin",startTime );
					map.put("exer_end",endTime);
					map.put("place", place);
					map.put("user_id", user_id);
					setCreateRequest(map);

				}
				else{
					Toast.makeText(AssociationManeuverCreateActivity.this, "请填写完整", Toast.LENGTH_LONG).show();
				}

			}
		});


	}

	@Override
	public void showContent() {

		assoNameText.setText(asso_name);
	}
	
	@SuppressWarnings("unchecked")
	private void setCreateRequest(Map<String,Object> map){
		AsycTaskUtil atu = new AsycTaskUtil(AssociationManeuverCreateActivity.this, 
				Constant.Association.CREATE_EXER, new OnPostInterface() {

			@Override
			public void code_1(JSONObject json) {

				JSONObject result = json.optJSONObject("result");
				if(result.optBoolean("success")){
					Toast.makeText(AssociationManeuverCreateActivity.this, "活动创建成功", Toast.LENGTH_LONG).show();
					int exer_id = result.optInt("exer_id");
					Intent intent = new Intent(AssociationManeuverCreateActivity.this,AssociationManeuverDetailActivity.class);
					intent.putExtra("asso_id", asso_id);
					intent.putExtra("exer_id", exer_id);
					startActivity(intent);
					AssociationManeuverCreateActivity.this.finish();

				}

			}
		});
		atu.execute(map);
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if(view instanceof TextView){
			final TextView textView = (TextView)view;
			

			final Calendar c = Calendar.getInstance();
			if(hasFocus){

				new DatePickerDialog(AssociationManeuverCreateActivity.this,
						new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, final int year,
							final int monthOfYear, final int dayOfMonth) {
						new TimePickerDialog(AssociationManeuverCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker arg0, int hour, int minute) {
								textView.setText(year +""+(monthOfYear+1)+""+dayOfMonth+""+hour+""+minute);


							}
						}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();

					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();


			}
		}



	}


}
