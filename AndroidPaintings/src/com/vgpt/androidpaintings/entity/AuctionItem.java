package com.vgpt.androidpaintings.entity;

import java.io.Serializable;
import java.util.Map;

public class AuctionItem implements Serializable {
	
	private int pic_id;
	private String pic_name;
	private int remain_time;
	private String introduction;
	private int current_price;
	private int auction_id;
	private String category;
	private int price_times;
	private String uploaderName;
	
	
	
	public String getUploaderName() {
		return uploaderName;
	}
	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
	public int getPrice_times() {
		return price_times;
	}
	public void setPrice_times(int price_times) {
		this.price_times = price_times;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(int auction_id) {
		this.auction_id = auction_id;
	}
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
	public int getRemain_time() {
		return remain_time;
	}
	public void setRemain_time(int remain_time) {
		this.remain_time = remain_time;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public int getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(int current_price) {
		this.current_price = current_price;
	}
	
	public AuctionItem(Map<String,Object> map){
		this.pic_id=(Integer) map.get("pic_id");
		this.introduction=(String)map.get("introduction");
		this.current_price=(Integer)map.get("current_price");
		this.remain_time=(Integer)map.get("remain_time");
		this.pic_name=(String)map.get("pic_name");
		this.auction_id=(Integer)map.get("auction_id");
	}
	
	public AuctionItem(){
		
	}
	
	public AuctionItem setAttribute(Map<String,Object> map){
		setPic_id((Integer)map.get("pic_id"));
		setCategory((String)map.get("category"));
		setCurrent_price((Integer)map.get("current_price"));
		setAuction_id((Integer)map.get("auction_id") );
		setPrice_times((Integer)map.get("price_times") );
		setPic_name((String)map.get("pic_name") );
		setRemain_time((Integer)map.get("remain_time") );
		setUploaderName((String)map.get("uploader_name") );
		return this;
	}
	

}
