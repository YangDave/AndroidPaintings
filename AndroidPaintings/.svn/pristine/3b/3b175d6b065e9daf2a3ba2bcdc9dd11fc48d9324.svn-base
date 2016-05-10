package com.vgpt.androidpaintings.biz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONToMap {
	
	   public static Map<String, Object> toMap(JSONObject jsonObject) throws JSONException 
	    { 
	        Map<String, Object> result = new HashMap<String, Object>(); 
	        @SuppressWarnings("unchecked")
			Iterator<String> iterator = jsonObject.keys(); 
	        String key = null; 
	        Object value = null; 
	        while (iterator.hasNext()) 
	        { 
	            key = iterator.next(); 
	            value = jsonObject.get(key);
	            result.put(key, value); 
	        } 
	        return result; 
	    } 


}
