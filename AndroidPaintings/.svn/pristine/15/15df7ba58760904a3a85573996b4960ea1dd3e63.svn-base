package com.vgpt.androidpaintings.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONArrayToList {
	
	public static List<Map<String,Object>> toList(JSONArray jsonList) throws JSONException{
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i = 0;i < jsonList.length();i++){
			JSONObject item = jsonList.getJSONObject(i);
			Map<String,Object> map = JSONToMap.toMap(item);
			list.add(map);
		}
		
		return list;
		
	}

}
