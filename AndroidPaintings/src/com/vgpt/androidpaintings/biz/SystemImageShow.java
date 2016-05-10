package com.vgpt.androidpaintings.biz;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * @author Charles
 * 调用系统图片展示
 *
 */
public class SystemImageShow {
	
	 String url;
	 Context mContext;
	
	
	public SystemImageShow(String url,Context mContext){
		
		this.mContext=mContext;
		this.url=url;
		
	}
	
	public void show(){
		File file = new File(url);
	    if( file != null && file.isFile() == true){
	     Intent intent = new Intent();
	     intent.setAction(android.content.Intent.ACTION_VIEW);
	     intent.setDataAndType(Uri.fromFile(file), "image/*");
	     mContext.startActivity(intent);      
	    }
	    else{
	    	Toast.makeText(mContext, "文件不存在或不是图片", Toast.LENGTH_LONG).show();
	    }
	}

}