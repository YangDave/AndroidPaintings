package com.vgpt.androidpaintings.biz;

import java.util.Date;

public class GetUploadTimeToNow {
	
	public Long getTime(String uploadDateStr){
		
		Long quot=Long.valueOf(0);
		Date uploadDate=StringToDate.StringToDate(uploadDateStr, "yyyy-MM-dd");
		Date now = new Date();
		quot=now.getTime()-uploadDate.getTime();
		return quot;
	}
	
	
	

}
