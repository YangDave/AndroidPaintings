package com.vgpt.androidpaintings.compoent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyFragment;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.compoent.activity.paintings.MyUploadActivity;
import com.vgpt.androidpaintings.compoent.activity.paintings.PictureChangeSizeShowActivity;
import com.vgpt.androidpaintings.compoent.activity.user.MyCollectActivity;
import com.vgpt.androidpaintings.compoent.activity.user.MyFocusActivity;
import com.vgpt.androidpaintings.compoent.activity.user.SettingsActivity;
import com.vgpt.androidpaintings.compoent.activity.user.SignActivity;
import com.vgpt.androidpaintings.compoent.activity.user.UserDataActivity;
import com.vgpt.androidpaintings.constants.Constant;
import com.vgpt.androidpaintings.utils.LogUtils;

/**
 * @author Charles
 * 用户个人信息，收藏，关注；
 * TODO 用户设置
 *
 */
public class FragmentMore extends MyFragment {

	ImageView imageView;


	Boolean sexBoolean=false;

	Button collectButton,settingsButton,userDataBt,myuploadBt,myCommentButton;
	Button myfocusButton,quitButton,myuploadButton,safeButton;
	int user_id=MainActivity.user_id;
	String username=MainActivity.username;

	final static int SETTINGS=30;



	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	}
	
	


	@Override
	public void onResume() {
		super.onResume();
		getActivity().getWindow().setTitle(getString(R.string.frag4_title));
	}




	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {


		final View rootView=inflater.inflate(R.layout.fragment4, container, false);

		loadPage(rootView);


		//		退出登录 needCheck


		return rootView;



	}




	@Override
	public void findView(View view) {

		collectButton=(Button)view.findViewById(R.id.collectButton);
		myfocusButton=(Button)view.findViewById(R.id.myfoucusButton);
		imageView=(ImageView)view.findViewById(R.id.image);
		settingsButton=(Button)view.findViewById(R.id.settingsButton);
		userDataBt =(Button)view.findViewById(R.id.infoButton);
		myuploadBt=(Button)view.findViewById(R.id.myupload);
		myCommentButton = (Button)view.findViewById(R.id.commentButton);

	}


	@Override
	public void doReady() {
		
		if(user_id==0){
			
			Intent intent = new Intent(getActivity(),SignActivity.class);
			startActivity(intent);
			getActivity().finish();
		}

	}


	@Override
	public void addListener() {

		settingsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),SettingsActivity.class);
				startActivity(intent);
				
				
			}
		});
		
		myuploadBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getActivity(),MyUploadActivity.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);

			}
		});

		//		我的收藏
		collectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//				未登录需先登录
				if(user_id!=0){
					Intent collectIntent=new Intent(getActivity(),MyCollectActivity.class);
					startActivity(collectIntent);	
				}
				else{
					Intent signInIntent=new Intent(getActivity(),SignActivity.class);
					startActivity(signInIntent);
					Toast.makeText(getActivity(), "你需要先登录",Toast.LENGTH_LONG).show();
				}

			}
		});

		userDataBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent =new Intent(getActivity(),UserDataActivity.class);
				startActivity(intent);

			}
		});

		//		我的关注
		myfocusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
					Intent myfocusIntent=new Intent(getActivity(),MyFocusActivity.class);
					myfocusIntent.putExtra("user_id", user_id);
					startActivity(myfocusIntent);	

			}
		});

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					Intent intent=new Intent(getActivity(),PictureChangeSizeShowActivity.class);
					intent.putExtra("url", Constant.Api.GET_PHOTO+user_id);
					startActivity(intent);
			}
		});
		
		myCommentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				TODO MyCommentPageActivity修复修复修复！！！！
				Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_LONG).show();
				
//				Intent intent=new Intent(getActivity(),MyCommentPageActivity.class);
//				intent.putExtra("user_id", user_id);
//				startActivity(intent);
			}
		});

	}


	@Override
	public void showContent() {

		if(user_id!=0){

			LogUtils.v("==========frag.user_id="+user_id+"=====");
			Picasso.with(getActivity()).load(Constant.Api.GET_PHOTO+user_id).into(imageView);
		}

	}


}
