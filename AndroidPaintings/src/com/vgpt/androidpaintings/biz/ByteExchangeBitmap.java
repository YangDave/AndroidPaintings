package com.vgpt.androidpaintings.biz;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * @author Charles
 *
 */
public class ByteExchangeBitmap {
	public static byte[] BitmapToBytes(Bitmap bm){
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
		
	}
	
	public static Bitmap ByteToBitmap(byte[] b){
		if(b.length!=0){
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}else{
		return null;
	}}

}