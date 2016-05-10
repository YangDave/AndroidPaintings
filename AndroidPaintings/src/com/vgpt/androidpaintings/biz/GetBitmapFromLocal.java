package com.vgpt.androidpaintings.biz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class GetBitmapFromLocal {
	
	public static Bitmap getBitmapAutoResize(String imagePath){
		if((imagePath==null)||imagePath.equals("")){
			return null;
		}
		Bitmap bm=null;
		Options opts=new Options();
		opts.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(imagePath,opts);
		opts.inJustDecodeBounds=false;
		int sampleSize=1;
		
		while(true){
			if(opts.outHeight*opts.outWidth/sampleSize<1281*901){
				break;
			}
			sampleSize*=2;
		}
		opts.inSampleSize=sampleSize;
		try{
			bm=BitmapFactory.decodeFile(imagePath,opts);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		return bm;
		
	}

}
