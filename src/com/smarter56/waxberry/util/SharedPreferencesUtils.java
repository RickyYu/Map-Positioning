package com.smarter56.waxberry.util;

import java.util.Map;
import java.util.Set;


import com.smarter56.waxberry.MyApplication;

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

	/**
	 * 
	 * @return
	 */
	public Editor getEditor() {
		return spEditor;
	}

	public String getPhoneNo() {
		return sp.getString("phoneNo", "");
	}

	public boolean setPhoneNo(String phoneNo) {
		spEditor.putString("phoneNo", phoneNo);
		return spEditor.commit();
	}
}
