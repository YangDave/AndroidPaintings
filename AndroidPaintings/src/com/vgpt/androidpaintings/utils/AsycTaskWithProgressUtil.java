package com.vgpt.androidpaintings.utils;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;

/**
 * @author Charles
 *
 *添加装饰器
 */
public class AsycTaskWithProgressUtil extends AsycTaskUtil{
	

    
	
	public AsycTaskWithProgressUtil(Context mContext, String url,
			OnPostInterface onPostExecute) {
		super(mContext, url, onPostExecute);
	}
	
	public AsycTaskWithProgressUtil(Context mContext,String url,String title,String content,OnPostInterface onPostExecute){
		super(mContext,url,onPostExecute);
		super.title = title;
		super.content = content;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog =ProgressDialog.show(mContext,title, content, true, true);
		
	}

	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(JSONObject json) {
		LogUtils.v("json:"+json);
		dialog.dismiss();
		super.onPostExecute(json);
		
	}


}
