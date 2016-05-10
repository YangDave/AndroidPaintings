package com.vgpt.androidpaintings.entity;

import java.io.Serializable;
import java.util.Map;


public class Association implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String asso_name=null;
	private String mark_url;
	private int asso_id=0;
	
	
	public Association() {
		super();
	}
	public Association(String asso_name, String mark_url, int asso_id) {
		super();
		this.asso_name = asso_name;
		this.mark_url=mark_url;
		this.asso_id = asso_id;
	}
	
	public Association(Map<String,Object> map){
		this.asso_name = map.get("asso_name").toString();
		this.mark_url = map.get("asso_mark").toString();
		this.asso_id = (Integer)map.get("asso_id");
		
	}

	public String getMark_url() {
		return mark_url;
	}

	public void setMark_url(String mark_url) {
		this.mark_url = mark_url;
	}

	public String getAsso_name() {
		return asso_name;
	}
	public void setAsso_name(String asso_name) {
		this.asso_name = asso_name;
	}
	
	public int getAsso_id() {
		return asso_id;
	}
	public void setAsso_id(int asso_id) {
		this.asso_id = asso_id;
	}
	

}
