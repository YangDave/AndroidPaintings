package com.vgpt.androidpaintings.test;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import com.vgpt.androidpaintings.http.MyHttpClient;

public class UploadTest {
	
	
	public void uploadTest() throws Exception{
		MultipartEntity postEntity=new MultipartEntity();
		postEntity.addPart("myfilename", new FileBody(new File("sss")));
		JSONObject json=new JSONObject();
		json.put("username", "sss");
		postEntity.addPart("json", new StringBody(json.toString()));
		
		MyHttpClient.getInstance().post("url", postEntity, new ResponseHandler<Void>() {

			@Override
			public Void handleResponse(HttpResponse arg0)
					throws ClientProtocolException, IOException {
				
				return null;
			}
		});
	}

}
