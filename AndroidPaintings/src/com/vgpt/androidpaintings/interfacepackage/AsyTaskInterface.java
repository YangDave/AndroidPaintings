package com.vgpt.androidpaintings.interfacepackage;

import java.util.Map;

public interface AsyTaskInterface<Bitmap> {
	
//	public T doInBackGound(String url);
	
	
	public void onPostTask(Bitmap bitmap,Map<String,Object> map);
	

}
