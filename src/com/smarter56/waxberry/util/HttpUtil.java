package com.smarter56.waxberry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.smarter56.waxberry.dao.GpsInfoModel;

public class HttpUtil {
	private Context context;
	final static String URL = "http://locationsvr.tunnel.mobi/SLP_IOT_WAR/upload/batch/gps";
	String resResponse = null;

	public HttpUtil(Context context) {
		this.context = context;
	}

	public String uploadInfo(List<GpsInfoModel> gpsInfoModels) {
		String infos = "";
		JSONObject jsonObject = new JSONObject();
		for (GpsInfoModel model : gpsInfoModels) {
			try {
				jsonObject.put("lat", model.getLat());
				jsonObject.put("lon", model.getLon());
				jsonObject.put("updateTime", model.getUpdateTime());
				jsonObject.put("vehicleNo", model.getVehicleNo());
				jsonObject.put("placeName", model.getPlaceName());
				jsonObject.put("direction", model.getDirection());
				jsonObject.put("speed", model.getSpeed());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			infos = infos + String.valueOf(jsonObject) + ",";
		}

		return upload(new SharedPreferencesUtils(context).getPhoneNo(), "[" + infos
				+ "]");
	}

	public String upload(String userName, String infos) {

		RequestParams params = new RequestParams();
		params.add("userName", userName);
		params.add("gpsInfo", infos);
		new AsyncHttpClient().post(context, URL, params,
				new TextHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {

						resResponse = arg2;
						Log.i("ricky", "onSuccess" + resResponse);
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {

						resResponse = arg2;
						Log.i("ricky", "onFailure" + resResponse);
					}
				});

		return resResponse;
	}

}
