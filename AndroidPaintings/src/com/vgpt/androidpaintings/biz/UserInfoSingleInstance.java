package com.vgpt.androidpaintings.biz;

public class UserInfoSingleInstance {
	
	private int user_id;
	private String username;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	private static UserInfoSingleInstance uisi = null;
	
	public static UserInfoSingleInstance getInstance(){
		if(uisi == null ){
			uisi = new UserInfoSingleInstance();
		}
		return uisi;
	}
	

}
