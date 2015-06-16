package com.smarter56.waxberry.util;



/**
 * @author Ricky
 */
public class Constants {

	public static final String ACTION_REFRESH_LOCATION = "action.refresh.location";
	public static final String START_LOCSTART_SERVICE = "service.refresh.location";
	/**
	 * 3类定位方式
	 * gcj02            国测局加密经纬度
	 * bd09ll  百度加密经纬度
	 * bd09               百度加密墨卡托坐标
	 */
	public static final String REQUEST_COOR_TYPE = "bd09ll";
	public static final int REQUEST_MIN_TIME = 10000;// 10秒
	public static final int INTERVAL_UPLOAD_COUNT = 5;// 12*10000 2分钟
	public static final double EARTH_RADIUS = 6378.137;
	public static final int RANGE_SHIFT_MAX =(556*REQUEST_MIN_TIME)/10000;//200KM/h 神奇的算法
	public static final int RANGE_SHIFT_MIN =3;

}
