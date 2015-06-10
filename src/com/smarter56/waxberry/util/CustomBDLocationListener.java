package com.smarter56.waxberry.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.smarter56.waxberry.dao.GpsInfoModel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CustomBDLocationListener implements BDLocationListener {
	private final static String TAG = CustomBDLocationListener.class
			.getSimpleName();
	private Context context;

	public CustomBDLocationListener() {
		super();
	}

	public CustomBDLocationListener(Context context) {
		super();
		this.context = context;
	}

	// 发送广播，提示更新界面
	private void sendToActivity(String gpsInfo) {
		Intent intent = new Intent();
		intent.putExtra("newLoca", gpsInfo);
		intent.setAction("NEW LOCATION SENT");
		context.sendBroadcast(intent);
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		Log.i(TAG, "--------------onReceiveLocation---------------");
		Log.i("ricky", location.getTime());
		if (location == null) {
			return;
		}

		/*
		 * JSONObject jsonObject = null; try { jsonObject = new JSONObject();
		 * jsonObject.put("lat", String.valueOf(location.getLatitude()));
		 * jsonObject.put("lon", String.valueOf(location.getLongitude()));
		 * jsonObject.put("updateTime", String.valueOf(location.getTime()));
		 * jsonObject.put("vehicleNo", "浙B999"); jsonObject.put("placeName",
		 * String.valueOf(location.getAddrStr())); jsonObject.put("direction",
		 * "-1"); jsonObject.put("speed", "1"); } catch (JSONException e) { d
		 * catch block e.printStackTrace(); } String string = "[" +
		 * String.valueOf(jsonObject) + "]"; String string2 = new
		 * HttpUtil(context) .uploadInfo("18868877045", string); Log.i("ricky",
		 * "string=" + string + "string2=" + string2);
		 */

		GpsInfoModel infoModel = new GpsInfoModel();
		infoModel.setLat(location.getLatitude());
		infoModel.setLon(location.getLongitude());
		infoModel.setUpdateTime(String.valueOf(location.getTime()));
		infoModel.setVehicleNo("浙B9999");
		infoModel.setPlaceName(String.valueOf(location.getAddrStr()));
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			infoModel.setSpeed(location.getSpeed());
		}
		infoModel.setDirection(location.getDirection());
		if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			infoModel.setPlaceName(String.valueOf(location.getAddrStr()));
		} else {
			infoModel.setPlaceName("暂无位置信息");
		}

		DBService.getInstance(context).saveGpsInfoModel(infoModel);

		List<GpsInfoModel> gpsInfoModels = DBService.getInstance(context)
				.loadAllGpsInfoModels();

		Log.i("ricky",
				"-----------------------CURRENT_INFOMODEL--------------------"
						+ "----" + gpsInfoModels.size() + "------"
						+ gpsInfoModels.toString());

		if (gpsInfoModels.size() >= 12) {
			new HttpUtil(context).uploadInfo(gpsInfoModels);
			DBService.getInstance(context).deletAllGpsInfoMode();
		}

		sendToActivity(infoModel.toString());
	}
}
