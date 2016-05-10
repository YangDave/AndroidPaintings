package com.vgpt.androidpaintings.entity;
/*
 * userInfo and others info last edit at 6.8
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class PersonInfo implements Serializable{
	private String name;
	private String age;
	private String phone;
	private String sex;
	private String address;
	private String birthday;
	private String introduction;
	
	
	public PersonInfo(String name, String age, String sex, String phone,String birthday,String address,
			String introduction) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.phone=phone;
		this.birthday = birthday;
		this.address=address;
		this.introduction = introduction;
	}
	
	

	public PersonInfo(String name, String age, String phone, String sex,
			String birthday, String introduction) {
		super();
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.sex = sex;
		this.birthday = birthday;
		this.introduction = introduction;
	}



	public String getIntroduction() {
		return introduction;
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