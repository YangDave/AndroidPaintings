package com.vgpt.androidpaintings.compoent.activity.asynctask;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;

public class SleepThread  extends Thread{
	
	ProgressDialog dialog;
	int sleeptime;
	Handler handler;
		
	public SleepThread(final ProgressDialog dialog, int sleeptime) {
		super();
		this.dialog = dialog;
		this.sleeptime = sleeptime;
		
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				dialog.dismiss();
			}
		};
		
	}

	@Override
	public void run() {
		super.run();
		
		try {
			SleepThread.sleep(sleeptime);
			handler.sendEmptyMessage(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
