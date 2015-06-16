package com.smarter56.waxberry.helper;

import android.util.Log;



public class Logger {
	private static final int LOGLEVEL=2;
	private static final String TAG="waxberry";
	public static void log(Object object,Object what){
		if (object==null) 
			object="nothing";
		else if (object instanceof String) {
			object=object.toString();
		}
		else {
			object=object.getClass().getSimpleName();
		}
		if(what==null){ what="nothing"; }
		switch (LOGLEVEL) {
			case 0:
				Log.v("【"+TAG+"】【"+object.toString()+"】", "【"+what.toString()+"】");
				break;
			case 1:
				Log.d("【"+TAG+"】【"+object.toString()+"】", "【"+what.toString()+"】");
				break;
			case 2:
				Log.i("【"+TAG+"】【"+object.toString()+"】", "【"+what.toString()+"】");
				break;
			case 3:
				Log.w("【"+TAG+"】【"+object.toString()+"】", "【"+what.toString()+"】");
				break;
			default:
				Log.e("【"+TAG+"】【"+object.toString()+"】", "【"+what.toString()+"】");
		}
	}
	
	public static void log(Object cls,Object method,Object what){
		if (cls==null) 
			cls="nothing";
		else if (cls instanceof String) {
			cls=cls.toString();
		}
		else {
			cls=cls.getClass().getSimpleName();
		}
		if (method==null) 
			method="nothing";
		if(what==null)
			what="nothing";
		switch (LOGLEVEL) {
			case 0:
				Log.v("【"+TAG+"："+cls.toString()+"】【"+method.toString()+"】", "【"+what.toString()+"】");
				break;
			case 1:
				Log.d("【"+TAG+"："+cls.toString()+"】【"+method.toString()+"】", "【"+what.toString()+"】");
				break;
			case 2:
				Log.i("【"+TAG+"："+cls.toString()+"】【"+method.toString()+"】", "【"+what.toString()+"】");
				break;
			case 3:
				Log.w("【"+TAG+"："+cls.toString()+"】【"+method.toString()+"】", "【"+what.toString()+"】");
				break;
			default:
				Log.e("【"+TAG+"："+cls.toString()+"】【"+method.toString()+"】", "【"+what.toString()+"】");
		}
	}
}

