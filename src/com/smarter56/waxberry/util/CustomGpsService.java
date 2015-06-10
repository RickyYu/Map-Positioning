package com.smarter56.waxberry.util;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CustomGpsService extends Service {

	private static final int REQUEST_MIN_TIME =10000;//10秒
	private LocationClient locationClient;
	private CustomBDLocationListener locationListener;
	private LocationClientOption locationClientOption;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//option
		locationClientOption = new LocationClientOption();
		locationClientOption.setLocationMode(LocationMode.Hight_Accuracy);
		locationClientOption.setScanSpan(REQUEST_MIN_TIME);
		locationClientOption.setCoorType("bd09ll");
		locationClientOption.setOpenGps(true);
		locationClientOption.setIsNeedAddress(true);
		
		locationListener = new CustomBDLocationListener(getApplicationContext());
		
		locationClient = new LocationClient(getApplicationContext());
		locationClient.setLocOption(locationClientOption);
		
		locationClient.registerLocationListener(locationListener);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if (locationClient != null){
			Log.i("ricky", "locationClient.start()");
			locationClient.start();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (locationClient != null && locationClient.isStarted()){
			locationClient.stop();
		}
		locationClient.unRegisterLocationListener(locationListener);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
