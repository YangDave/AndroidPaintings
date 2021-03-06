package com.vgpt.androidpaintings.compoent.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.application.MyApplication;
import com.vgpt.androidpaintings.biz.InputStyleValidate;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.interfacepackage.MyCallBack;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskWithProgressUtil;
import com.vgpt.androidpaintings.utils.LogUtils;

public class Fragment_sign_in extends Fragment{

	EditText muserEditText;
	EditText mpwdEditText;
	Button msignInButton;
	TextView msignUpView,forgetPassView;
	ImageView imageView;


	SharedPreferences preference;
	SharedPreferences.Editor editor;
	SharedPreferences signInRecord;
	SharedPreferences.Editor signInRecordEditor;

	MyCallBack myCallBack = null;


	String username;
	int userId;

	public Fragment_sign_in(){

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
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
		
		initView(rootView);

		msignInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(InputStyleValidate.isUserNameOrPwdFormat(muserEditText.getText().toString())&&
						InputStyleValidate.isUserNameOrPwdFormat(mpwdEditText.getText().toString())){

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("username", muserEditText.getText());
					map.put("password", mpwdEditText.getText());

					final AsycTaskWithProgressUtil atu = new AsycTaskWithProgressUtil(getActivity(),Constant.Api.SIGN_IN,new OnPostInterface(){

						@Override
						public void code_1(JSONObject json) {

							LogUtils.v("========="+json.optInt("code")+"=======");

							JSONObject result = json.optJSONObject("result");
							if(result != null){

								boolean	signInIsSuccess = result.optBoolean("success");
								int retUser_id=result.optInt("user_id",0);
								doPostExecute(signInIsSuccess, retUser_id);

							}
						}
					});
					atu.execute(map);


				}

				else { Toast.makeText(getActivity(), R.string.userorpwdlengthwarn, Toast.LENGTH_LONG).show();

				}

			}
		});
		
		msignUpView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				myCallBack.changeFragment(R.layout.fragment_sign_in);
//				getActivity().getFragmentManager().beginTransaction()
//				.replace(container.getId(), new Fragment_sign_up()).commit();
				
			}
		});
		return rootView;
	}

	private void initView(View v){
		muserEditText = (EditText) v.findViewById(R.id.signInUserId);
		mpwdEditText = (EditText) v.findViewById(R.id.signInPwdId);
		msignInButton = (Button) v.findViewById(R.id.signInButtonId);
		msignUpView = (TextView) v.findViewById(R.id.register);
		forgetPassView = (TextView)v.findViewById(R.id.forgetPass);
		imageView=(ImageView)v.findViewById(R.id.image);
	}



	private void doPostExecute(Boolean signInIsSuccess, int retUser_id){

		if (signInIsSuccess){
			username=muserEditText.getText().toString();
			preference=getActivity().getSharedPreferences(username, getActivity().MODE_PRIVATE);
			editor=preference.edit();
			editor.putString("username", username);
			editor.putInt("user_id", retUser_id);
			editor.commit();

			signInRecord=getActivity().getSharedPreferences("signInRecord", getActivity().MODE_PRIVATE);
			signInRecordEditor=signInRecord.edit();
			signInRecordEditor.putString("lastSignInUser", username);
			signInRecordEditor.putInt("lastSignInUserId",retUser_id);
			signInRecordEditor.commit();

			MyApplication ma = (MyApplication)getActivity().getApplicationContext();
			ma.setUser_id(retUser_id);
			ma.setUsername(username);
			
			Intent intent=new Intent(getActivity(),MainActivity.class);
			startActivity(intent);
			getActivity().finish();


		}
		else{
			Toast.makeText(getActivity(),"登录失败", Toast.LENGTH_LONG).show();
		}

	}


}
