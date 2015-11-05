package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
/**
 * 封装对数据库操作
 * 根据id查询名称
 */
	public static final String DB_NAME = "region.db";
	public static final int VERSION = 1;
	private SQLiteDatabase db;
	private static CoolWeatherDB coolWeatherDB;
	/**
	 * 单例类:保证全局范围内只有一个CoolWeatherDB实例
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();
	}
	/**
	 * 获取COOLWeatherDB实例
	 */
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	public List<Province> loadProvince(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("T_Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("ProSort")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("ProName")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
		
	} 
	/**
	 * 从数据库中读取某省份的市
	 */
	public List<City> loadCity(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("T_City", null, "ProID = ?", new String[] { String.valueOf(provinceId)}, null, null, null);
//		Cursor cursor = db.query("T_City", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("CitySort")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("CityName")));
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
		
	} 
	/**
	 * 从数据库中读取某市的县
	 */
	public List<County> loadCounty(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("T_Zone", null, "CityID = ?", new String[]{ String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("ZoneID")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("ZoneName")));
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
		
	}
}