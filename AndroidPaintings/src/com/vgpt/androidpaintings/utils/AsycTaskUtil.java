package com.vgpt.androidpaintings.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView.FindListener;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;

public class AsycTaskUtil extends AsyncTask<Map<String,Object>, Integer, JSONObject> {
	
	protected String url;
	protected Context mContext;
	protected JSONObject ret = null;
    OnPostInterface onPostInterface;
    protected ProgressDialog dialog;
    protected String title = "请稍候";
    protected String content = "正在加载。。。";
    
	public AsycTaskUtil(Context mContext,String url,OnPostInterface onPostExecute){
		
		this.mContext = mContext;
		this.url = url;
		this.onPostInterface = onPostExecute;
	}
	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
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
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}



	@Override
	protected void onPostExecute(JSONObject json) {
		super.onPostExecute(json);
		
		if(json == null){
			
			Toast.makeText(mContext, R.string.no_connection, Toast.LENGTH_LONG).show();
			
		}else{
			int code = json.optInt("code");
			if(code == 1){
				onPostInterface.code_1(json);
				publishProgress(1);
			}else{
				Toast.makeText(mContext, "请求错误:"+json.toString(), Toast.LENGTH_LONG).show();
			}
		}
		
	}


}
