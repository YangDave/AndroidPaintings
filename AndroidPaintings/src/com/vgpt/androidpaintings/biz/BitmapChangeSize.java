package com.vgpt.androidpaintings.biz;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapChangeSize {

	public static Bitmap big(Bitmap b,float x,float y)
	{
		int w=b.getWidth();
		int h=b.getHeight();
		float sx=(float)x/w;//要强制转换，不转换我的在这总是死掉。
		float sy=(float)y/h;
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w,
				h, matrix, true);
		return resizeBmp;
	} 

}
