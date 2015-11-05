package com.coolweather.app.model;

public class City {
	private int id;
	private String cityName;
	private int provinceID;
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getCityName(){
		return cityName;
	}
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	public int getProvinceId(){
		return provinceID;
	}
	public void setProvinceId(int provinceID){
		this.provinceID = provinceID;
	}
}
