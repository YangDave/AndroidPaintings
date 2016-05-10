package com.vgpt.androidpaintings.entity;

import java.io.Serializable;

public class UserSelfInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String age;
	private String phone;
	private String sex;
	private String address;
	private String birthday;
	private String introduction;
	
	public String getIntroduction() {
		
		return introduction;
	}

	public UserSelfInfo(String name, String age, String phone, String sex,
			String address, String birthday) {
		super();
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.sex = sex;
		this.address = address;
		this.birthday = birthday;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getName() {
		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
