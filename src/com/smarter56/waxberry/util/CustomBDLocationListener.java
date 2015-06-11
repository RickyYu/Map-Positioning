package com.smarter56.waxberry.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.smarter56.waxberry.dao.GpsInfoModel;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Ricky
 */
public class CustomBDLocationListener implements BDLocationListener {
	private final static String TAG = CustomBDLocationListener.class
			.getSimpleName();
	private Context context;
	private String vehicelNo;
	private String totalMeters;
	private static int totalMeterss = 0;
	BDLocation bdLocation1 = new BDLocation();

	public CustomBDLocationListener() {
		super();
	}

	public CustomBDLocationListener(Context context) {
		super();
		this.context = context;
		vehicelNo = new SharedPreferencesUtils(context).getVehicleNo();
		totalMeters = new SharedPreferencesUtils(context).getTotalMeters();
	}

	// 发送广播，提示更新界面
	private void sendToActivity(String gpsInfo) {
		Intent intent = new Intent();
		intent.putExtra("newLoca", gpsInfo);
		intent.setAction(Intents.ACTION_REFRESH_LOCATION);
		context.sendBroadcast(intent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onReceiveLocation(BDLocation location) {
		Log.i(TAG, "--------------onReceiveLocation---------------");
		if (location == null) {
			return;
		}
		// TODO tomorrow
		String totalKm = getDistance(location.getLatitude(),
				location.getLongitude(), bdLocation1.getLatitude(),
				bdLocation1.getLongitude());
		Log.i("ricky", "bdLocation1.getLatitude()=" + bdLocation1.getLatitude());
		bdLocation1 = location;
		totalMeterss = totalMeterss + Integer.parseInt(totalKm);
		GpsInfoModel infoModel = new GpsInfoModel();
		infoModel.setTotalMeters(totalMeterss);
		infoModel.setLat(location.getLatitude());
		infoModel.setLon(location.getLongitude());
		infoModel.setUpdateTime(new Date().getTime());
		// infoModel.setUploadTime(location.getTime());
		infoModel.setVehicleNo("浙B9999");
		infoModel.setPlaceName(String.valueOf(location.getAddrStr()));
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			infoModel.setSpeed(location.getSpeed());
		} else {
			infoModel.setSpeed((float) 0);
		}
		infoModel.setDirection(location.getDirection());
		DBService.getInstance(context).saveGpsInfoModel(infoModel);
		List<GpsInfoModel> gpsInfoModels = DBService.getInstance(context)
				.loadAllGpsInfoModels();
		if (gpsInfoModels.size() >= 2) {

			new HttpUtil(context).new uploadAsyncTask().execute(gpsInfoModels);
		}

		sendToActivity(infoModel.toString());
	}

	/**
	 * 根据两个位置的经纬度，来计算两地的距离（单位为KM） 参数为String类型
	 * 
	 * @param lat1
	 *            用户经度
	 * @param lng1
	 *            用户纬度
	 * @param lat2
	 *            商家经度
	 * @param lng2
	 *            商家纬度
	 * @return
	 */
	public static String getDistance(double lat1Str, double lng1Str,
			double lat2Str, double lng2Str) {

		double radLat1 = rad(lat1Str);
		double radLat2 = rad(lat2Str);
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1Str) - rad(lng2Str);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(
				Math.sin(difference / 2), 2)
				+ Math.cos(radLat1)
				* Math.cos(radLat2)
				* Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * Intents.EARTH_RADIUS * 1000;
		distance = Math.round(distance * 10000) / 10000;
		String distanceStr = distance + "";
		distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));

		return distanceStr;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
}
