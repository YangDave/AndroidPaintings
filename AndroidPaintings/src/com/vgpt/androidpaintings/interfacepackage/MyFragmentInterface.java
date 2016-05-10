package com.vgpt.androidpaintings.interfacepackage;

import android.view.View;

public interface MyFragmentInterface {
	
	/*
	 * findViewById
	 */
	
	void findView(View view);
	
	/*
	 * 执行数据预处理
	 */
	
	void doReady();
	
	/*
	 * 增加回调事件
	 */
	
	void addListener();
	
	/*
	 * 展示页面内容
	 */
	
	void showContent();
	

}
