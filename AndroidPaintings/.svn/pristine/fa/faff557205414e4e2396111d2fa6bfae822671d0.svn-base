package com.vgpt.androidpaintings.compoent.activity.user;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyNoActionBarActivity;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.entity.PersonInfo;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class InfoChangeActivity extends MyNoActionBarActivity {

	EditText nameEdit;
	EditText phoneEdit;
	EditText ageEdit;
	EditText introductionEdit;
	EditText birthdayEdit;
	RadioGroup sexEdit;
	RadioButton maleRb,femaleRb;
	Button cancelButton;
	Button okButton;


	String sexreturn,birthday;
	final int PHOTOCHANGE = 3;


	final static int PICTURE=11;
	final static int CAMERA=10;



	@SuppressLint("UseValueOf")
	public static String intToString(int value) {
		Integer integer = new Integer(value);
		return integer.toString();
	}

	public static int stringToInt(String intstr) {
		Integer integer;
		integer = Integer.valueOf(intstr);
		return integer.intValue();
	}
	
	


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infochange);
		
		loadPage(this);
		
	}



	private void infoChangedSuccess(){
		username = MainActivity.username;

		Intent intent = new Intent(InfoChangeActivity.this,UserDataActivity.class);

		InfoChangeActivity.this.setResult(18, intent);
		InfoChangeActivity.this.finish();
	}

	@Override
	public void findView() {

		nameEdit = (EditText) findViewById(R.id.namechange);
		sexEdit = (RadioGroup) findViewById(R.id.sexgroup);
		phoneEdit = (EditText) findViewById(R.id.phonechange);
		ageEdit = (EditText) findViewById(R.id.agechange);
		birthdayEdit = (EditText) findViewById(R.id.birthdaychange);
		introductionEdit = (EditText) findViewById(R.id.selfintroduction);
		cancelButton = (Button) findViewById(R.id.cancelInfoChange);
		okButton = (Button) findViewById(R.id.okinfoChange);
		
		maleRb = (RadioButton)findViewById(R.id.male);
		femaleRb = (RadioButton)findViewById(R.id.female);
	}

	@Override
	public void prepareData() {

		Intent infoIntent = getIntent();
		
		PersonInfo personInfo = (PersonInfo)infoIntent.getSerializableExtra("personInfo");
		

		String name=personInfo.getName();
		String sex=personInfo.getSex();
		LogUtils.v("======"+sex);
		String phone=personInfo.getPhone();
		String age=personInfo.getAge();
		birthday=personInfo.getBirthday();
		String introduction=personInfo.getIntroduction();

		LogUtils.v("name "+name+" phone "+phone);
		
		nameEdit.setText(name);
		nameEdit.setSelection(nameEdit.getText().length());

		if (sex.equals("男")) {
			maleRb.setChecked(true);

		} else {
			femaleRb.setChecked(true);
		}

		phoneEdit.setText(phone);
		phoneEdit.setSelection(phoneEdit.getText().length());

		ageEdit.setText(age);
		ageEdit.setSelection(ageEdit.getText().length());

		birthdayEdit.setText(birthday);

		introductionEdit.setText(introduction);
	}

	@Override
	public void addListener() {

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InfoChangeActivity.this.finish();
			}
		});

		okButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {

				int changedage = 0;
				Long changedphone = 0L;

				String changedbirthday = birthdayEdit.getText().toString();

				if (StringUtils.isNumeric(ageEdit.getText().toString())) {
					changedage = Integer.valueOf(ageEdit.getText().toString())
							.intValue();
				}
				if (StringUtils.isNumeric(phoneEdit.getText().toString())) {

					changedphone = Long.valueOf(phoneEdit.getText().toString()).longValue();

				}

				Boolean changedsex = sexEdit.getCheckedRadioButtonId() == R.id.male ? true
						: false;
				sexreturn = sexEdit.getCheckedRadioButtonId() == R.id.male ? "男" : "女";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user_id", user_id);
				map.put("name", nameEdit.getText().toString());
				map.put("sex", changedsex);
				map.put("phone", changedphone);
				map.put("birthday", changedbirthday);
				map.put("age", changedage);
				map.put("introduction", introductionEdit.getText().toString());
				map.put("photo_name", String.valueOf(user_id) + ".jpg");

				AsycTaskUtil atu = new AsycTaskUtil(InfoChangeActivity.this, 
						Constant.Api.USER_DATA, new OnPostInterface() {

					@Override
					public void code_1(JSONObject json) {
						JSONObject result = json.optJSONObject("result");
						boolean isSuccess = result.optBoolean("success");
						if(isSuccess){
							infoChangedSuccess();
						}else{
							Toast.makeText(InfoChangeActivity.this, "修改失败", Toast.LENGTH_LONG).show();
						}

					}
				});

				atu.execute(map);

			}
		});



		birthdayEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				if(hasFocus){
					int year;
					int month;
					int day;
					if(birthday!=null){
						final String[] date=birthday.split("-");
						year=Integer.parseInt(date[0]);
						month=Integer.parseInt(date[1]);
						day=Integer.parseInt(date[2]);
					}else{
						Calendar c = Calendar.getInstance();
						year=c.get(Calendar.YEAR);
						month=c.get(Calendar.MONTH);
						day=c.get(Calendar.DAY_OF_MONTH);
					}

					new DatePickerDialog(InfoChangeActivity.this,
							new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {

							monthOfYear=monthOfYear+1;
							birthdayEdit.setText(year + "-" + monthOfYear
									+ "-" + dayOfMonth);

						}
					}, year, month-1, day).show();

				}
			}
		});

		LogUtils.v("年："+Calendar.YEAR+"  月："+Calendar.MONTH+" 日:"+Calendar.DAY_OF_MONTH);

	}

	@Override
	public void showContent() {
		// TODO Auto-generated method stub

	}
}
