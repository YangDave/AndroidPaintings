package com.vgpt.androidpaintings.compoent.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyFragment;
import com.vgpt.androidpaintings.compoent.activity.paintings.PaintingUploadActivity;
import com.vgpt.androidpaintings.compoent.activity.search.SearchActivity;
import com.vgpt.androidpaintings.compoent.widget.MyScrollView;
import com.vgpt.androidpaintings.compoent.widget.MyScrollView.OnScrollDownListener;
import com.vgpt.androidpaintings.compoent.widget.MyScrollView.OnScrollToTopListener;
import com.vgpt.androidpaintings.interfacepackage.MyFragmentInterface;
import com.vgpt.androidpaintings.utils.LogUtils;

/**
 * @author Charles
 * 7-22
 *
 */
public class FragmentPainting extends MyFragment implements MyFragmentInterface{


	final static String ALL = "all";
	final static String GONGBI = "gongbi";
	final static String XIEYI = "xieyi";
	final static String XIHUA = "xihua";
	final static String SHUFA = "shufa";
	final static String OTHERS = "others";


	
	MyScrollView mysv;
	
	TextView loadMoreText;
	
	RadioGroup rg;
	
	RelativeLayout rl;
	final int LENGTH=50;
	static String category="all";
	
	ViewFlipper viewFlipper;
	float startX=0;
	float endX=0;
	int[] imgs={R.drawable.birds,R.drawable.figure,R.drawable.landscape,R.drawable.flower};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		

	}
	
	

	@Override
	public void onResume() {
		super.onResume();
		
		getActivity().getWindow().setTitle(getString(R.string.frag1_title));
	}



	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container, false);
		
		loadPage(rootView);
		
		
		return rootView;
	}
    
   
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		MenuItem upload_item = menu.add(R.string.upload);
		upload_item.setIcon(R.drawable.add)
		.setIntent(new Intent(getActivity(),PaintingUploadActivity.class))
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		MenuItem search_item = menu.add(R.string.search_pic);
		search_item.setIcon(R.drawable.ic_action_search)
		.setIntent(new Intent(getActivity(),SearchActivity.class))
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	
	 


	@Override
	public void findView(View view) {
		
        loadMoreText=(TextView)view.findViewById(R.id.loadmore);
		
		 rl=(RelativeLayout)view.findViewById(R.id.bigImage);
		
		mysv=(MyScrollView)view.findViewById(R.id.my_scroll_view);
		
		rg = (RadioGroup)view.findViewById(R.id.tab_category);
		
		viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper);
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
					case R.id.otherrb:mysv.setCategory(OTHERS);
						
					}
					
					
					
				}
			});

		mysv.setOnScrollDownListener(new OnScrollDownListener() {

			@Override
			public void onScrollDown(boolean isDown) {

				if(isDown){
				}

			}
		});

		mysv.setOnScrollToTopLintener(new OnScrollToTopListener() {
			
			@Override
			public void onScrollTopListener(boolean isTop) {
				
				if(isTop){
					Toast.makeText(getActivity(), "滑动到顶端", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		viewFlipper.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				LogUtils.v("==================fragment1  ontouch=============================");
				viewFlipper.stopFlipping();             // 点击事件后，停止自动播放  
				viewFlipper.setAutoStart(false); 
				
				switch (event.getAction()) {  
				case MotionEvent.ACTION_DOWN:  

					startX = event.getX();  
					break;  
				case MotionEvent.ACTION_UP:  

					endX = event.getX();  

					// 先保存上一个点  

					if( endX - startX > 100){// 查看前一页的广告  

						Animation rInAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）   
						Animation rOutAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）   

						viewFlipper.setInAnimation(rInAnim);  
						viewFlipper.setOutAnimation(rOutAnim);  
						viewFlipper.showPrevious();  
						return true; 
					}  

					if(startX-endX > 100){// 查看后一页的广告  

						Animation rInAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）   
						Animation rOutAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）   

						viewFlipper.setInAnimation(rInAnim);  
						viewFlipper.setOutAnimation(rOutAnim);  
						viewFlipper.showNext();
						return true;

					}

				}
				return true;

			}
		});
		
		
		
	}

	@Override
	public void showContent() {

		loadMoreText.setVisibility(View.GONE);


		for(int i=0;i<imgs.length;i++){

			ImageView iv=new ImageView(getActivity());
			iv.setImageResource(imgs[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			viewFlipper.addView(iv, new LayoutParams(LayoutParams.FILL_PARENT,  
					LayoutParams.FILL_PARENT));  
		}

		viewFlipper.setAutoStart(true); // 设置自动播放功能（点击事件，前自动播放）   
		viewFlipper.setFlipInterval(6000);  
		if (viewFlipper.isAutoStart() && !viewFlipper.isFlipping()) {  
			viewFlipper.startFlipping();  
		}  
		

	}

	@Override
	public void doReady() {
		
	}


	


}