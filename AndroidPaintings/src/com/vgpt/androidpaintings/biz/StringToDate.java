package com.vgpt.androidpaintings.biz;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;



public class StringToDate {
	
	@SuppressLint("SimpleDateFormat")
	public static Date StringToDate(String dateStr,String formatStr){		
		DateFormat dd=new SimpleDateFormat(formatStr);		
		Date date=null;		
		try {			
			date = dd.parse(dateStr);		
			} catch (ParseException e) {			
				e.printStackTrace();		
				}		
		
		return date;	
				}

}
