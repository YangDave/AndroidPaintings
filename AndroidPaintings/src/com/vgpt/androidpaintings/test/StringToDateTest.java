package com.vgpt.androidpaintings.test;

import android.os.Handler;
import android.os.Message;

public class StringToDateTest {
	
	static int i =0 ;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		System.out.println(" dasdfsd");
		
		
		
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				if(i<1000){
				System.out.println("i==");
				
				i++;
				}
			}
			
			
			
		};
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Thread.sleep(100);
					
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
//		Date date=StringToDate.StringToDate("2000-1-2", "yyyy-MM-dd");
//		
//		Date now =new Date();
//		
//		Long quot=now.getTime()-date.getTime();
//		System.out.println("" + quot);
	}

}
