package com.vgpt.androidpaintings.biz;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


/**
 * @author Charles
 * Bitmap 和 Drawable的互换
 *
 */
public class BitmapExchangeDrawable {
	public static Bitmap drawableToBitmap(Drawable drawable){
		Bitmap bitmap = Bitmap.createBitmap(
				 drawable.getIntrinsicWidth(),
				 drawable.getIntrinsicHeight(),
				 drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
		
	}
	@SuppressWarnings("deprecation")
	public static Drawable bitmapToDrawable(Bitmap bitmap){
		Drawable drawable= new BitmapDrawable(bitmap ); 
		return drawable;
		
	}
	
}
