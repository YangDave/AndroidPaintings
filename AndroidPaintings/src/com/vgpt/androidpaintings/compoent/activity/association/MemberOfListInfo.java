package com.vgpt.androidpaintings.compoent.activity.association;

import java.io.Serializable;

public class MemberOfListInfo implements Serializable{
	private int user_id;
	private String user_name;
	private int authority;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public MemberOfListInfo(int user_id, String user_name, int authority) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.authority = authority;
	}

}
