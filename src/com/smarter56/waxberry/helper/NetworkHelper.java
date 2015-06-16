package com.smarter56.waxberry.helper;

import java.util.List;

import com.smarter56.waxberry.R;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class NetworkHelper {

	/**
	 * check if the network is connected
	 * @param context
	 * @return
	 * @exception 
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean networkConnected = false;
		if (null != cm) {
			NetworkInfo network = cm.getActiveNetworkInfo();
			if (null != network && network.isConnected()) {
				// network.getState() == NetworkInfo.State.CONNECTED
				networkConnected = true;
			}
		}
		return networkConnected;
	}
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean networkAvailable = false;
		if (null != cm) {
			NetworkInfo network = cm.getActiveNetworkInfo();
			if(null != network){
				networkAvailable = network.isAvailable();
			}
		}
		return networkAvailable;
	}	

	public static boolean isGPSEnabled(Context context) {
		LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders = locationManager.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}

	public static boolean isWIFIEnabled(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
				.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	/**
	 * 判断当前网络是否是wifi网络
	 * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前网络是否是3G网络
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean is3G(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * open the wireless setting activity of android
	 * 
	 * @param context
	 */
	public static void openNetworkSetting(Context context) {
		Intent intent = null;
		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		} else {
			intent = new Intent();
			ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
			intent.setComponent(component);
			intent.setAction(Intent.ACTION_VIEW);
		}
		context.startActivity(intent);
	}

    public static boolean checkNetworkConnect(Context context){
        boolean connected = isNetworkConnected(context);
        if(!connected){
            Toast.makeText(context, R.string.msg_network_unconnected, Toast.LENGTH_SHORT).show();
        }
        return connected;
    }
}
