package com.smarter56.waxberry.util;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Ricky
 */
public class CustomGpsService extends Service {

	private LocationClient locationClient;
	private CustomBDLocationListener locationListener;
	private LocationClientOption locationClientOption;

	@Override
	public void onCreate() {
		super.onCreate();
		// option
		locationClientOption = new LocationClientOption();
		locationClientOption.setLocationMode(LocationMode.Hight_Accuracy);
		locationClientOption.setScanSpan(Intents.REQUEST_MIN_TIME);
		locationClientOption.setCoorType(Intents.REQUEST_COOR_TYPE);
		locationClientOption.setOpenGps(true);
		locationClientOption.setIsNeedAddress(true);

		locationListener = new CustomBDLocationListener(getApplicationContext());

		locationClient = new LocationClient(getApplicationContext());
		locationClient.setLocOption(locationClientOption);

		locationClient.registerLocationListener(locationListener);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (locationClient != null) {
			locationClient.start();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (locationClient != null && locationClient.isStarted()) {
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
