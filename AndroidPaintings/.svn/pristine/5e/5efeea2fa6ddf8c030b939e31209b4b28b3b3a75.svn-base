package com.vgpt.androidpaintings.biz;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetAssetsFile {
	
	public static  Bitmap getImageFromAssetsFile(Context c,String fileName)  
	  {  
	      Bitmap image = null;  
	      AssetManager am = c.getResources().getAssets();  
	      try  
	      {  
	          InputStream is = am.open(fileName);  
	          image = BitmapFactory.decodeStream(is);  
	          is.close();  
	      }  
	      catch (IOException e)  
	      {  
	          e.printStackTrace();  
	      }  
	  
	      return image;  

	  }
}
