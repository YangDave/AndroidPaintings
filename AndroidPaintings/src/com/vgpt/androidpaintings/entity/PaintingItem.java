package com.vgpt.androidpaintings.entity;

import java.io.Serializable;
import java.util.Map;

public class PaintingItem  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2342352344234L;
	
	String pic_name;
	String uploader;
	int pic_id;
	int uploader_id;
	int recommendation ;
	int count_read;
	int count_comment;
	String category;
	String date_on;
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public int getPic_id() {
		return pic_id;
	}
	public void setPic_id(int pic_id) {
		this.pic_id = pic_id;
	}
	public int getUploader_id() {
		return uploader_id;
	}
	public void setUploader_id(int uploader_id) {
		this.uploader_id = uploader_id;
	}
	public int getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(int recommendation) {
		this.recommendation = recommendation;
	}
	public int getCount_read() {
		return count_read;
	}
	public void setCount_read(int count_read) {
		this.count_read = count_read;
	}
	public int getCount_comment() {
		return count_comment;
	}
	public void setCount_comment(int count_comment) {
		this.count_comment = count_comment;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDate_on() {
		return date_on;
	}
	public void setDate_on(String date_on) {
		this.date_on = date_on;
	}
	
	
	public PaintingItem(Map<String,Object> map){
		
		if(map.get("up_actor_id")!= null){
			map.put("uploader_id", map.get("up_actor_id"));
		}
		if(map.get("up_actor")!=null){
			map.put("uploader", map.get("up_actor"));
		}
		setPic_name(map.get("name").toString());
		setUploader(map.get("uploader").toString());
		setPic_id((Integer)map.get("pic_id"));
		setUploader_id((Integer)map.get("uploader_id"));
		setRecommendation((Integer)map.get("recommendation"));
		setCount_read((Integer)map.get("count_read"));
		setCount_comment((Integer)map.get("count_comment"));
		setCategory((String)map.get("category"));
		setDate_on((String)map.get("date_on"));
		
	}
	

}
