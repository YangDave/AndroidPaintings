package com.vgpt.androidpaintings.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.test.suitebuilder.annotation.MediumTest;

import com.vgpt.androidpaintings.http.JSONResponseHandler;
import com.vgpt.androidpaintings.http.MyHttpClient;

public class MyHttpClientTest  extends BaseTest {
	
	
	
	
	
	@MediumTest
	void postTest() throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("username","ydw");
		map.put("email", "yedww@123.com");
		map.put("password", "yedww@123.com");
		
		
		
		MyHttpClient cl=MyHttpClient.getInstance();
		
		List<NameValuePair>  list=new ArrayList<NameValuePair>();
		
		JSONObject json=new JSONObject(map);
		
		NameValuePair pa=new BasicNameValuePair("json", json.toString());
		list.add(pa);
		
        try {
			cl.post("http://www.baidu.com",list, new JSONResponseHandler<Void>() {

				@Override
				public Void handlerJson(JSONObject json) {
					
				int code=	json.optInt("code");
				if(code==1){
					
					JSONObject result=json.optJSONObject("result");
					
					
				}
					
					
					
					
					return null;
				}
			})	;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

}
