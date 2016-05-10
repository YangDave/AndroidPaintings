package com.vgpt.androidpaintings.entity;

import java.io.Serializable;
import java.util.Map;

public class MyPaintingItem implements Serializable{
	
	private int pic_id;
	private String pic_name;
	private int on_auction;
	public int getPic_id() {
		return pic_id;
	}
	public void setPic_id(int pic_id) {
		this.pic_id = pic_id;
	}
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public int getOn_auction() {
		return on_auction;
	}
	public void setOn_auction(int on_auction) {
		this.on_auction = on_auction;
	}
	public MyPaintingItem(Map<String,Object> map){
		
		this.pic_id=(Integer)map.get("pic_id");
		this.pic_name=(String)map.get("pic_name");
		this.on_auction=(Integer)map.get("on_aucting");
	}
	public MyPaintingItem(int pic_id, String pic_name, int on_auction) {
		super();
		this.pic_id = pic_id;
		this.pic_name = pic_name;
		this.on_auction = on_auction;
	}
	
	
	

}
