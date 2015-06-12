package com.smarter56.waxberry;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;

import com.smarter56.waxberry.util.NetworkHelper;
import com.smarter56.waxberry.util.SharedPreferencesUtils;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author Ricky
 */
public class MyApplication extends Application {
	public static String TAG = MyApplication.class.getSimpleName();
	private static MyApplication instance;

	/**
	 * support a method to get a instance for the outside
	 */
	public synchronized static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	   
	}

}
