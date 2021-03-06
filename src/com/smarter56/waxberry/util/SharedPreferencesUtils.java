package com.smarter56.waxberry.util;

import java.util.Map;
import java.util.Set;


import com.smarter56.waxberry.MyApplication;
import com.smarter56.waxberry.helper.Logger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * @author Ricky
 */
@SuppressLint("CommitPrefEdits")
public class SharedPreferencesUtils {
	private SharedPreferences sp;
	private Editor spEditor;
	private String spName = "waxberry";
	private int mode = Context.MODE_PRIVATE;

	public SharedPreferencesUtils() {
		MyApplication instance = MyApplication.getInstance();
		this.sp = instance.getSharedPreferences(spName, mode);
		this.spEditor = sp.edit();
	}

	public SharedPreferencesUtils(Context context) {
		this.sp = context.getSharedPreferences(spName, mode);
		this.spEditor = sp.edit();
	}

	public SharedPreferencesUtils(Context context, String name, int mode) {
		this.sp = context.getSharedPreferences(name, mode);
		this.spEditor = sp.edit();
	}

	public SharedPreferences getSharedPreferences() {
		return sp;
	}

	/**
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void add(Map<String, String> map) {
		Set<String> set = map.keySet();
		for (String key : set) {
			spEditor.putString(key, map.get(key));
		}
		spEditor.commit();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void deleteAll() throws Exception {
		spEditor.clear();
		spEditor.commit();
	}

	/**
	 */
	public void delete(String key) {
		spEditor.remove(key);
		spEditor.commit();
	}

	/**
	 * 
	 * @return
	 */
	public Editor getEditor() {
		return spEditor;
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getString(String key, String defValue) {
		if (sp != null) {
			return sp.getString(key, defValue);
		}
		return defValue;
	}

	public long getLong(String key) {
		return sp.getLong(key, -1);
	}

	public int getInteger(String key) {
		return sp.getInt(key, -1);
	}

	public int getInteger(String key, Integer value) {
		return sp.getInt(key, value);
	}

	public boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean bool) {
		return sp.getBoolean(key, bool);
	}

	public float getFloat(String key) {
		return sp.getFloat(key, -1);
	}

	public void setPreferences(String key, Object value) {
		Editor editor = sp.edit();
		if (value instanceof Integer) {
			Logger.log(this, "setPreferences i", value);
			editor.putInt(key, (Integer) value);
		} else if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Boolean) {
			Logger.log(this, "setPreferences b", value);
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		}
		boolean edit = editor.commit();
		Logger.log(this, "setPreferences", edit + "/" + key + "/" + value);
	}

	public String getPhoneNo() {
		return sp.getString("phoneNo", "");
	}

	public boolean setPhoneNo(String phoneNo) {
		spEditor.putString("phoneNo", phoneNo);
		return spEditor.commit();
	}

	public String getVehicleNo() {
		return sp.getString("vehicleno", "");
	}

	public boolean setVehicleNo(String vehicleno) {
		spEditor.putString("vehicleno", vehicleno);
		return spEditor.commit();
	}

	public int getTotalMeters() {
		return sp.getInt("totalMeters", 0);
	}

	public boolean setTotalMeters(int totalMeters) {
		spEditor.putInt("totalMeters", totalMeters);
		return spEditor.commit();
	}

	public boolean getAutoLogin() {
		return sp.getBoolean("auto_login", false);
	}

	public boolean setAutoLogin() {
		spEditor.putBoolean("auto_login", true);
		return spEditor.commit();
	}

	public boolean getRecordMileage() {
		return sp.getBoolean("record_mileage", false);
	}

	public boolean setRecordMileage(boolean tag) {
		spEditor.putBoolean("record_mileage", tag);
		return spEditor.commit();
	}
}
