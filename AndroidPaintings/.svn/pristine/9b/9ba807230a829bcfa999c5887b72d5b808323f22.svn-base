package com.vgpt.androidpaintings.compoent.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyActivity;
import com.vgpt.androidpaintings.compoent.widget.MyCollectScrollView;
import com.vgpt.androidpaintings.compoent.widget.MyCollectScrollView.OnScrollDownListener;
import com.vgpt.androidpaintings.compoent.widget.MyCollectScrollView.OnScrollToTopListener;

public class MyCollectActivity extends MyActivity{
	final static String ALL = "all";
	final static String GONGBI = "gongbi";
	final static String XIEYI = "xieyi";
	final static String XIHUA = "xihua";
	final static String SHUFA = "shufa";
	final static String OTHER = "others";

	float startX=0;
	float endX=0;

	MyCollectScrollView mysv;
	TextView loadMoreText;

	RadioGroup rg;

	final int LENGTH=50;
	static String category="all";



	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);

		loadPage(MyCollectActivity.this);
		

	}
	
	@Override
	public void findView() {

		loadMoreText=(TextView)findViewById(R.id.loadmore);

		mysv=(MyCollectScrollView)findViewById(R.id.my_scroll_view);

		rg = (RadioGroup)findViewById(R.id.tab_category);
		
		

	}
	@Override
	public void prepareData() {

	}
	@Override
	public void addListener() {

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkId) {

				switch(checkId){

				case R.id.allrb:mysv.setCategory(ALL);
				break;
				case R.id.gongbirb:mysv.setCategory(GONGBI);
				break;
				case R.id.xieyirb:mysv.setCategory(XIEYI);
				break;
				case R.id.xihuarb:mysv.setCategory(XIHUA);
				break;
				case R.id.shufarb:mysv.setCategory(SHUFA);
				break;
				case R.id.otherrb:mysv.setCategory(OTHER);

				}



			}
		});
		
		mysv.setOnScrollDownListener(new OnScrollDownListener() {
			
			@Override
			public void onScrollDown(boolean isDown) {
				// TODO Auto-generated method stub
				
			}
		});


		mysv.setOnScrollToTopLintener(new OnScrollToTopListener() {

			@Override
			public void onScrollTopListener(boolean isTop) {

				if(isTop){
					Toast.makeText(MyCollectActivity.this, "滑动到顶端", Toast.LENGTH_LONG).show();
				}

			}
		});


	}
	@Override
	public void showContent() {

		loadMoreText.setVisibility(View.GONE);

	}

}
