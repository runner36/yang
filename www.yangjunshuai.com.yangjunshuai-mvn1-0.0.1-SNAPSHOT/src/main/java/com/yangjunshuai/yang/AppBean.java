package com.yangjunshuai.yang;

/**
 * Hello world!
 *
 */
public class AppBean 
{
	private String name;
	private String gender;
	
	public String toString(){
		return this.getName()+","+this.getGender();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
