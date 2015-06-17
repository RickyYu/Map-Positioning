package com.smarter56.waxberry.helper;

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
import com.smarter56.waxberry.util.Constants;
import com.smarter56.waxberry.util.DBService;
import com.smarter56.waxberry.util.HttpUtil;
import com.smarter56.waxberry.util.SharedPreferencesUtils;
import com.smarter56.waxberry.util.ToastUtils;
import com.smarter56.waxberry.util.HttpUtil.uploadAsyncTask;

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
	private boolean first_refresh = true;
	private String vehicelNo;
	private static int totalMeters;
	private BDLocation tempBdLocation;
	private SharedPreferencesUtils utils;

	public CustomBDLocationListener() {
		super();		
	}

	public CustomBDLocationListener(Context context) {
		super();
		this.context = context;
		utils = new SharedPreferencesUtils(context);
		vehicelNo = utils.getVehicleNo();
		totalMeters = utils.getTotalMeters();
	}

	// 发送广播，提示更新界面
	private void postRefreshView(String gpsInfo) {
		Intent intent = new Intent();
		intent.putExtra("gpsInfo", gpsInfo);
		intent.setAction(Constants.ACTION_REFRESH_LOCATION);
		context.sendBroadcast(intent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onReceiveLocation(BDLocation location) {
		Log.i(TAG, "--------------onReceiveLocation---------------");
		if (location == null) {
			return;
		}
		handleMileage(location);
		GpsInfoModel infoModel = new GpsInfoModel();
		infoModel.setTotalMeters(totalMeters);
		infoModel.setLat(location.getLatitude());
		infoModel.setLon(location.getLongitude());
		infoModel.setUpdateTime(new Date().getTime());
		infoModel.setVehicleNo(vehicelNo);
		//infoModel.setPlaceName(String.valueOf(location.getAddrStr()));
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			infoModel.setSpeed(location.getSpeed());
		} else {
			infoModel.setSpeed((float) 0);
		}
		infoModel.setDirection(location.getDirection());
		DBService.getInstance(context).saveGpsInfoModel(infoModel);
		ToastUtils.show(context,
						+ DBService.getInstance(context).countInfoModels()+"条数据！");
		if (DBService.getInstance(context).countInfoModels() >= Constants.INTERVAL_UPLOAD_COUNT) {
			new HttpUtil(context).new uploadAsyncTask().execute(DBService
					.getInstance(context).loadAllGpsInfoModels());
		}
		postRefreshView(infoModel.toString());
	}

	private void handleMileage(BDLocation location) {
		if (!first_refresh) {
			String strTotalMeters = getDistance(location.getLatitude(),
					location.getLongitude(), tempBdLocation.getLatitude(),
					tempBdLocation.getLongitude());
			int meters = Integer.parseInt(strTotalMeters);
			if (meters > Constants.RANGE_SHIFT_MIN
					&& meters < Constants.RANGE_SHIFT_MAX) {// 时速上线不超过200KM
				totalMeters = totalMeters + meters;
				utils.setTotalMeters(totalMeters);

			}
		}
		tempBdLocation = location;
		first_refresh = false;

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
		distance = distance * Constants.EARTH_RADIUS * 1000;
		distance = Math.round(distance * 10000) / 10000;
		String strDistance = distance + "";
		strDistance = strDistance.substring(0, strDistance.indexOf("."));

		return strDistance;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

}
