package com.vgpt.androidpaintings.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class AsycTaskWithWorkingUtil extends AsyncTask<Map<String,Object>, Integer, JSONObject> {
	
	private String url;
	private Context mContext;
	private JSONObject ret = null;
	public boolean isWorking = false ;
    OnPostWithWorkingInterface onPostWithWorkingInterface;
    
    
	
	public boolean isWorking() {
		return isWorking;
	}



	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}



	public AsycTaskWithWorkingUtil(Context mContext,String url,OnPostWithWorkingInterface onPostExecute){
		
		this.mContext = mContext;
		this.url = url;
		this.onPostWithWorkingInterface = onPostExecute;
	}
	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		isWorking = true;
	}



	@Override
	protected JSONObject doInBackground(Map<String, Object>... params) {
		
		MyHttpClient cl = MyHttpClient.getInstance();

		List<NameValuePair> list = new ArrayList<NameValuePair>();

		JSONObject json = new JSONObject(params[0]);

		NameValuePair pa = new BasicNameValuePair("json", json.toString());
		list.add(pa);
		
		

		try {

			cl.post(url, list,
					new JSONResponseHandler<JSONObject>() {

				@Override
				public JSONObject handlerJson(JSONObject retJson) {
					
					ret = retJson;
					return retJson;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	@Override
	protected void onPostExecute(JSONObject json) {
		super.onPostExecute(json);
		
		isWorking = false;
		
		onPostWithWorkingInterface.finishPost();
		if(json == null){
			
			Toast.makeText(mContext, R.string.no_connection, Toast.LENGTH_LONG).show();
			
		}else{
			int code = json.optInt("code");
			if(code == 1){
				onPostWithWorkingInterface.code_1(json);
			}
		}
		
	}

	public interface OnPostWithWorkingInterface{
	
		public void finishPost();
		public void code_1(JSONObject json);
		
	}

}