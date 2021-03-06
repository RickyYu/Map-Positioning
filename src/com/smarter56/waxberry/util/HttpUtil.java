package com.smarter56.waxberry.util;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.smarter56.waxberry.dao.GpsInfoModel;
import com.smarter56.waxberry.helper.Logger;

/**
 * @author Ricky
 */
public class HttpUtil {
	private Context context;
	private final static String URL = "http://locationsvr.tunnel.mobi/SLP_Interface_WAR/upload/batch/gps";
	private String msgResponse;

	public HttpUtil(Context context) {
		this.context = context;
	}

	public class uploadAsyncTask extends
			AsyncTask<List<GpsInfoModel>, Integer, String> {

		@Override
		protected String doInBackground(List<GpsInfoModel>... params) {
			// TODO Auto-generated method stub
			return new HttpUtil(context).uploadInfo(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null && result.equalsIgnoreCase("success")) {
				ToastUtils.show(context, "上传成功！");
			} else {
				ToastUtils.show(context, "上传失败！");
			}
		}

	}

	public String uploadInfo(List<GpsInfoModel> gpsInfoModels) {
		String strModels = JSON.toJSONString(gpsInfoModels, true);	
		return uploadSync(new SharedPreferencesUtils(context).getPhoneNo(),
				strModels);
	}

	public String uploadSync(String userName, String infos) {
		RequestParams params = new RequestParams();
		params.add("userName", userName);
		params.add("gpsInfo", infos);
		new SyncHttpClient().post(context, URL, params,
				new TextHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						if (arg2.equals("true")) {
							msgResponse = "success";
							DBService.getInstance(context)
									.deletAllGpsInfoMode();
						} else {
							msgResponse = "failed";
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {
						msgResponse = "failed";
					}
				});
		return msgResponse;
	}
}
