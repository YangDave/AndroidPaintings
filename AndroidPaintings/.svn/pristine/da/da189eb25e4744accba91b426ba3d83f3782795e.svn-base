package com.vgpt.androidpaintings.compoent.activity.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.vgpt.androidpaintings.R;

public class WaitingDialog extends ProgressDialog{
	
	
	Context context;

	public WaitingDialog(Context context) {
		super(context);
		this.context=context;
		
		
	}
	
	public void createDialog(){
		
		
	    setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条   
        setCancelable(true);// 设置是否可以通过点击Back键取消   
        setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条   
        setIcon(R.drawable.ic_launcher);//   
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的   
        setTitle("正在加载中。。。"); 
        show();
		
	}
	
	
}