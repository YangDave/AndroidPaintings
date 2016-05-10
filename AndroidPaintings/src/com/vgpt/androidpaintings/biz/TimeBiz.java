package com.vgpt.androidpaintings.biz;


public class TimeBiz {
	
	public static String getPeriod(String date_on){
		
		GetUploadTimeToNow guttn = new GetUploadTimeToNow();
		Long quot=guttn.getTime(date_on);
		quot=quot/1000/60/60/24;
		if(quot==Long.valueOf(0)){
			return "一天内";
		}
		else{
//			date_onText.setText(quot+"天前");
			return quot+"天前";
		}
		
	}

}
