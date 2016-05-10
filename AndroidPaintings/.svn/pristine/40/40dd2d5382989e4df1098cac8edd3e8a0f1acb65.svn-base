package com.vgpt.androidpaintings.test;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vgpt.androidpaintings.R;
import com.vgpt.androidpaintings.interfacepackage.OnPostInterface;
import com.vgpt.androidpaintings.utils.AsycTaskUtil;

public class HttpJSONTest extends Activity {
 
	private static final String TAG = "MainActivity";
	private EditText nameEdit;
	private EditText phoneEdit;
	private EditText resultEdit;
	private Button sendBtn;
	private ProgressDialog progressDialog;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_test);
 
		nameEdit = (EditText) findViewById(R.id.name);
		phoneEdit = (EditText) findViewById(R.id.phone);
		resultEdit = (EditText) findViewById(R.id.result);
		sendBtn = (Button) findViewById(R.id.sendBtn);
		progressDialog = new ProgressDialog(this);
 
		sendBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("name", nameEdit.getText().toString().trim());
				map.put("phone", phoneEdit.getText().toString().trim());
				
				send(map);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void send(Map<String,Object> map){
		AsycTaskUtil atu = new AsycTaskUtil(HttpJSONTest.this, "http://202.115.16.43:8080/ServletTest/JSONServlet",
				new OnPostInterface() {
					
					@Override
					public void code_1(JSONObject json) {
						
						Toast.makeText(HttpJSONTest.this, "success", Toast.LENGTH_LONG).show();
					}
				});
		atu.execute(map);
	}
	
	
	class AT extends AsyncTask<String,Integer,String>{
 
		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}
 
		@Override
		protected String doInBackground(String... params) {
			try {
				//请求数据
				HttpClient hc = new DefaultHttpClient();
				
				HttpParams param = new BasicHttpParams();

				HttpProtocolParams.setContentCharset(param, "UTF-8");
				HttpConnectionParams.setStaleCheckingEnabled(param, false);
				HttpConnectionParams.setConnectionTimeout(param, 30 * 1000);
				HttpConnectionParams.setSoTimeout(param, 30 * 1000);
				HttpConnectionParams.setSocketBufferSize(param, 8192);
				HttpClientParams.setRedirecting(param, true);
				HttpClientParams.setCookiePolicy(param,
						CookiePolicy.BROWSER_COMPATIBILITY);
				SchemeRegistry sr = new SchemeRegistry();
				sr.register(new Scheme("http", PlainSocketFactory
						.getSocketFactory(), 80));
				KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

				keyStore.load(null, null);
				
				
				HttpPost hp = new HttpPost(
						"http://202.115.16.43:8080/ServletTest/JSONServlet");
				//请求json报文
				JSONObject jo = new JSONObject();
				if (params.length > 1) {
					jo.put("name", params[0]);
					jo.put("phone", params[1]);
				} else {
					jo.put("err", "error");
				}
				Log.d(TAG, "params : " + jo.toString());
				hp.setEntity(new StringEntity(jo.toString()));
				HttpResponse hr = hc.execute(hp);
				String result = null;
				//获取返回json报文
				if(hr.getStatusLine().getStatusCode() == 200){
					result = EntityUtils.toString(hr.getEntity());
					Log.d(TAG, "result : " + result);
				}
				//关闭连接
				if (hc != null) {
					hc.getConnectionManager().shutdown();
				}
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
 
		@Override
		protected void onPostExecute(String result) {
			resultEdit.setText(result);
			progressDialog.cancel();
		}
	}
	
//	private UrlEncodedFormEntity getEncodedForm(String str) {
//
//		UrlEncodedFormEntity ret = null;
////		try {
////			ret = new UrlEncodedFormEntity(, "UTF-8");
////		} catch (UnsupportedEncodingException e) {
////			// no exception
////		}
//		return ret;
//
//	}
}