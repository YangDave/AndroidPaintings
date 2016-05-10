package com.vgpt.androidpaintings.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vgpt.androidpaintings.interfacepackage.MyFragmentInterface;

public abstract class MyFragment extends Fragment implements MyFragmentInterface{
	
	
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) ;
	
	protected void loadPage(View view){
		
		findView(view);
		
		doReady();
		
		showContent();
		
		addListener();
		
	}
	
	

}
