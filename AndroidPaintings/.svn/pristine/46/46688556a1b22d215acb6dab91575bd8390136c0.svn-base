package com.vgpt.androidpaintings.compoent.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.base.MyFragment;
import com.vgpt.androidpaintings.compoent.activity.MainActivity;
import com.vgpt.androidpaintings.compoent.widget.AuctionScrollView;
import com.vgpt.androidpaintings.compoent.widget.AuctionScrollView.OnScrollDownListener;
import com.vgpt.androidpaintings.compoent.widget.AuctionScrollView.OnScrollToTopListener;
import com.vgpt.androidpaintings.interfacepackage.MyFragmentInterface;

/**
 * @author Charles
 * TODO 期刊
 *
 */
public class FragmentAuction extends MyFragment implements MyFragmentInterface {
	OnBackListener mListener;
	int user_id=MainActivity.user_id;
	String username=MainActivity.username;
	TextView loadMore;
	final static int SIZE=3;
	boolean isWorking=false;
	RadioGroup rg;
	
//	TODO rename all category after server checked
	final static String ALL = "all";
	final static String GONGBI = "gongbi";
	final static String XIEYI = "xieyi";
	final static String XIHUA = "xihua";
	final static String SHUFA = "shufa";
	final static String OTHERS = "others";
	
	
	AuctionScrollView asv;
	

	
	public interface OnBackListener {
		public void backEvent();
	}
	
	
	
    @Override
	public void onResume() {
		super.onResume();
		getActivity().getWindow().setTitle(getString(R.string.frag2_title));
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBackListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View parentView = inflater.inflate(R.layout.fragment2, container, false);
		
		loadPage(parentView);
		return parentView;
	}
	
	public void findView(View view){
		
		
			rg = (RadioGroup)view.findViewById(R.id.tab_category);
			
			asv = (AuctionScrollView)view.findViewById(R.id.auction_scroll_view);
			
			loadMore=(TextView)view.findViewById(R.id.loadmore);
			
		
	}
	
	public void addListener(){
		
	asv.setOnScrollToTopLintener(new OnScrollToTopListener() {
			
			@Override
			public void onScrollTopListener(boolean isTop) {
				
				if(isTop){
					Toast.makeText(getActivity(), "滑动到顶端", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		asv.setOnScrollDownListener(new OnScrollDownListener() {
			
			@Override
			public void onScrollDown(boolean isDown) {
				
				
			}
		});
		
       rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkId) {
				
				switch(checkId){
					
				case R.id.allrb:asv.setCategory(ALL);
				break;
				case R.id.gongbirb:asv.setCategory(GONGBI);
				break;
				case R.id.xieyirb:asv.setCategory(XIEYI);
				break;
				case R.id.xihuarb:asv.setCategory(XIHUA);
				break;
				case R.id.shufarb:asv.setCategory(SHUFA);
				break;
				case R.id.otherrb:asv.setCategory(OTHERS);
					
				}
				
				
				
			}
		});
		
	}


	@Override
	public void showContent() {
		
	}

	@Override
	public void doReady() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
