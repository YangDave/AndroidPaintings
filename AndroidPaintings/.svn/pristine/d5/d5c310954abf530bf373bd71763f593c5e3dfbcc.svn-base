package com.vgpt.androidpaintings.compoent.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;

public class SearchActivity  extends MyActivity{
	
	Button searchButton;
	EditText et;
	RadioGroup searchGroup;
	RadioButton searchpicBt;
	RadioButton searchuserBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		loadPage(this);
		searchButton=(Button)findViewById(R.id.searchButton);
		et=(EditText)findViewById(R.id.search);
		searchGroup=(RadioGroup)findViewById(R.id.searchgroup);
		searchpicBt=(RadioButton)findViewById(R.id.search_pic);
		searchuserBt=(RadioButton)findViewById(R.id.search_user);
		
		
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword=et.getText().toString();
				boolean searchItem=searchpicBt.isChecked();
				if(keyword!=null){
					Intent searchResultIntent=new Intent(SearchActivity.this
							,SearchResultActivity.class);
					searchResultIntent.putExtra("keyword", keyword);
					searchResultIntent.putExtra("searchItem",searchItem);
					searchResultIntent.putExtra("user_id", user_id);
					startActivity(searchResultIntent);
					
				}else{
					Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showContent() {
		// TODO Auto-generated method stub
		
	}

	
}
