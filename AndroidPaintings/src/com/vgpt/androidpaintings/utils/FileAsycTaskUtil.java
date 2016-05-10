package com.vgpt.androidpaintings.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.compoent.activity.dialog.WaitingDialog;
import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;

public class FileAsycTaskUtil extends AsyncTask<Map<String,Object>, Integer, JSONObject> {

	private String url,filePath,fileName;
	private Context mContext;
	private JSONObject ret = null;
	private OnPostInterface onPostInterface;
	private WaitingDialog dialog;

	public FileAsycTaskUtil(Context mContext,String url,String filePath,String fileName,OnPostInterface onPostExecute){

		this.mContext = mContext;
		this.url = url;
		this.onPostInterface = onPostExecute;
		this.filePath = filePath;
		this.fileName = fileName;
	}



	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		dialog=new WaitingDialog(mContext);
		dialog.createDialog();
	}



	@Override
	protected JSONObject doInBackground(Map<String, Object>... params) {

		MultipartEntity postEntity = new MultipartEntity();
		postEntity.addPart(
				fileName,
				new FileBody(new File(filePath)));
		JSONObject json = new JSONObject(params[0]);

		try {
			postEntity.addPart("json", new StringBody(json.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			MyHttpClient.getInstance().post(url,
					postEntity, new JSONResponseHandler<JSONObject>() {

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

		dialog.dismiss();

		if(json == null){

			Toast.makeText(mContext, R.string.no_connection, Toast.LENGTH_LONG).show();

		}else{
			int code = json.optInt("code");
			LogUtils.v("fileAsyc code="+code);
			if(code == 1){
				onPostInterface.code_1(json);
			}
		}

	}

}
