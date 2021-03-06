package com.vgpt.androidpaintings.compoent.fragment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.biz.InputStyleValidate;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.MyCallBack;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;
import com.vgpt.androidpaintings.utils.AsycTaskWithProgressUtil;

public class Fragment_sign_up extends Fragment {
	
	EditText muserEditText,mEmailText;
	EditText mpwdEditText;
	Button msignUpButton;
	TextView msignInView;
	ToggleButton mtoggleButton;

	SharedPreferences preference;
	SharedPreferences.Editor editor;
	SharedPreferences signInRecord;
	SharedPreferences.Editor signInRecordEditor;
	
	MyCallBack myCallBack = null;


	String username;
	
	public Fragment_sign_up(){
		
	}
	
	


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			myCallBack = (MyCallBack)activity;
		}catch(ClassCastException e){
			throw new ClassCastException("activity must implement MyCallBack!");
		}
		
	}
	
	




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
		
		initView(rootView);
		
		mtoggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {

				if(checked){
					mtoggleButton.setBackgroundResource(R.drawable.toggle);
					mpwdEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
				else{
					mtoggleButton.setBackgroundResource(R.drawable.toggle2);
					mpwdEditText.setInputType(InputType.TYPE_CLASS_TEXT);
				}



			}
		});

		mEmailText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if(!hasFocus){

					if(!InputStyleValidate.isEmailAddressFormat(mEmailText.getText().toString())){

						Toast.makeText(getActivity(), "不是邮箱格式", Toast.LENGTH_LONG).show();
					}
				}

			}
		});

		msignUpButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {
				if (InputStyleValidate.isUserNameOrPwdFormat(muserEditText.getText().toString())
						&& InputStyleValidate.isUserNameOrPwdFormat(mpwdEditText.getText().toString()))
				{
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("username", muserEditText.getText());
					map.put("password", mpwdEditText.getText());
					map.put("email", mEmailText.getText());
					final AsycTaskWithProgressUtil atu= new AsycTaskWithProgressUtil(getActivity(), Constant.Api.SIGN_UP, new OnPostInterface() {
						
						@Override
						public void code_1(JSONObject json) {
							JSONObject ret = json
									.optJSONObject("result");
							boolean signUpSuccess = ret.optBoolean("success");
							int retUser_id = ret.optInt("user_id");
							
							signUpPostExecute(signUpSuccess, retUser_id);
							
						
						}
					});
					atu.execute(map);


				} else {
					Toast.makeText(getActivity(), R.string.signupwarn, Toast.LENGTH_LONG).show();
				}

			}
		});
		msignInView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				myCallBack.changeFragment(R.layout.fragment_sign_up);

			}
		});

		muserEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (StringUtils.isNotBlank(muserEditText.getText().toString())) {
						if (InputStyleValidate.isUserNameOrPwdFormat(muserEditText.getText()
								.toString()) == false) {
							Toast.makeText(getActivity(), R.string.userlengthwarn, Toast.LENGTH_LONG).show();

						} else { // used for connect
							Map<String, Object> usermap = new HashMap<String, Object>();
							usermap.put("username", muserEditText.getText());
							
							final AsycTaskUtil atu = new AsycTaskUtil(getActivity(), Constant.Api.CHECk_USER, new OnPostInterface() {

								@Override
								public void code_1(JSONObject json) {
									JSONObject ret = json.optJSONObject("result");
									uniquePostExecute(ret);
									
								}
								
							});

						}

					}
				}
			}
		});

		mpwdEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (mpwdEditText.getText().toString().length() != 0 ) {

						if (!InputStyleValidate.isUserNameOrPwdFormat(mpwdEditText.getText()
								.toString())) {

							Toast.makeText(getActivity(), R.string.pwdlengthwarn, Toast.LENGTH_LONG).show();
						} 

						if (mpwdEditText.getText().toString().equals(muserEditText
								.getText().toString())) {

							Toast.makeText(getActivity(), R.string.pwdequalusername, Toast.LENGTH_LONG).show();

							mpwdEditText.requestFocus();
						}
					}
				}
			}

		});
		
		return rootView;
	}
	
	private void initView(View view){
		
		muserEditText = (EditText)view.findViewById(R.id.username);
		mpwdEditText = (EditText) view.findViewById(R.id.password);
		mEmailText = (EditText) view.findViewById(R.id.emailtext);
		msignUpButton = (Button) view.findViewById(R.id.signUp);
		msignInView = (TextView) view.findViewById(R.id.signIn);

		mtoggleButton = (ToggleButton) view.findViewById(R.id.toggle);
		
	}
	
	private void signUpPostExecute(boolean signUpSuccess,int retUser_id){
		if (signUpSuccess) {
			username=muserEditText.getText().toString();
			preference=getActivity().getSharedPreferences(username, getActivity().MODE_PRIVATE);
			editor=preference.edit();
			editor.putString("username",username);
			editor.putInt("user_id", retUser_id);
			editor.commit();

			signInRecord=getActivity().getSharedPreferences("signInRecord", getActivity().MODE_PRIVATE);
			signInRecordEditor=signInRecord.edit();
			signInRecordEditor.putString("lastSignInUser", username);
			signInRecordEditor.putInt("lastSignInUserId", retUser_id);
			signInRecordEditor.commit();
			
			MyApplication ma = (MyApplication)getActivity().getApplicationContext();
			ma.setUser_id(retUser_id);
			ma.setUsername(username);
			Intent intent = new Intent(getActivity(),
					MainActivity.class);
			startActivity(intent);
			getActivity().finish();

		}
		else{
			Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_LONG).show();
		}
	}

	private void uniquePostExecute(JSONObject ret){

		boolean	userNameUnique = false;
			if (ret != null) {

				userNameUnique = ret.optBoolean(
						"valid", false);
			}
			if(!userNameUnique){
				Toast.makeText(getActivity(), R.string.usernameunique, Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(getActivity(), R.string.connectfalsetitle, Toast.LENGTH_LONG).show();
			}
	}

}
