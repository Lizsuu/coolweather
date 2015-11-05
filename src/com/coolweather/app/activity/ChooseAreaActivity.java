package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.R;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAreaActivity extends Activity{

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	
	private TextView titleText;
	private ListView regionList;
	private ArrayAdapter<String> adapter;
	private List<String> dataList = new ArrayList<String>();
	private CoolWeatherDB coolWeatherDB;
	
	private List<Province> provinceList;
	private List<City> cityList;
	private List<County> countyList;
	private Province selectedProvince;
//	private City selectedCity;
//	private County selectedCounty;
	private int currentLevel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		titleText = (TextView)findViewById(R.id.title_text);
		regionList = (ListView)findViewById(R.id.list_view);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
		regionList.setAdapter(adapter);
		//查询数据库,并写ListView监听事件,获取相应的显示数据
		
		
		coolWeatherDB = CoolWeatherDB.getInstance(this);
		queryProvinces();
		regionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) {
				// TODO Auto-generated method stub
				if(currentLevel == LEVEL_PROVINCE){
					selectedProvince = provinceList.get(index);
					queryCities();
				}else if(currentLevel == LEVEL_CITY){
					queryCounty(cityList.get(index));
				}
			}
		});
	}
	
	private void queryProvinces() {
		// TODO Auto-generated method stub
		provinceList = coolWeatherDB.loadProvince();
		if(provinceList.size()>0){
			dataList.clear();
			for(Province province:provinceList){
				dataList.add(province.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			regionList.setSelection(0);
			titleText.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			Toast.makeText(this, "加载失败,请重试!", Toast.LENGTH_SHORT).show();
		}
	}
	private void queryCities() {
		// TODO Auto-generated method stub
		cityList = coolWeatherDB.loadCity(selectedProvince.getId());
		if(cityList.size() > 0){
			dataList.clear();
			for(City city:cityList){
				dataList.add(city.getCityName());
			}
			adapter.notifyDataSetChanged();
			regionList.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLevel = LEVEL_CITY;
		} else {
			Toast.makeText(this, "加载失败,请重试!", Toast.LENGTH_SHORT).show();
		}
	}
	protected void queryCounty(City city) {
		// TODO Auto-generated method stub
		countyList = coolWeatherDB.loadCounty(city.getId());
		if(provinceList.size()>0){
			dataList.clear();
			for(County county:countyList){
				dataList.add(county.getCountyName());
			}
			adapter.notifyDataSetChanged();
			regionList.setSelection(0);
			titleText.setText(city.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else {
			Toast.makeText(this, "加载失败,请重试!", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(currentLevel == LEVEL_COUNTY){
			queryCities();
		}else if(currentLevel == LEVEL_CITY){
			queryProvinces();
		}else {
			finish();
		}
	}

}