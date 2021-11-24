package com.wning.demo.gifteffect;


import com.guagua.modules.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ProtocolParser {
	private static final String nullString = "null";

	public static String getJsonStr(JSONObject jsonObj, String name) {
		return getJsonStr(jsonObj, name, null);
	}

	public static int getJsonInt(JSONObject jsonObj, String name) {
		return getJsonInt(jsonObj, name, -1);
	}
	
	public static long getJsonLong(JSONObject jsonObj, String name) {
		return getJsonLong(jsonObj, name, -1);
	}
	
	public static boolean getJsonBool(JSONObject jsonObj, String name) {
		return getJsonBool(jsonObj, name, false);
	}
	
	public static String getJsonStr(JSONObject jsonObj, String name, String defaultValue) {
		try {
			if (jsonObj.has(name)) {
				if (jsonObj.getString(name).equals(nullString)) {
					return "";
				} else {
					return jsonObj.getString(name);
				}
			}
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return defaultValue;
	}

	public static int getJsonInt(JSONObject jsonObj, String name, int defaultValue) {
		try {
			if (jsonObj.has(name)) {
				return jsonObj.getInt(name);
			}
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return defaultValue;
	}

	public static long getJsonLong(JSONObject jsonObj, String name, long defaultValue) {
		try {
			if (jsonObj.has(name)) {
				return jsonObj.getLong(name);
			}
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return defaultValue;
	}

	public static JSONObject getJsonObj(JSONObject jsonArr, String name) {
		try {
			if (jsonArr.has(name)) {
				return jsonArr.getJSONObject(name);
			}
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return null;
	}

	public static boolean getJsonBool(JSONObject jsonObj, String name, boolean defaultValue) {
		try {
			if (jsonObj.has(name)) {
				return jsonObj.getBoolean(name);
			}
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return defaultValue;
	}
	
	public static JSONArray getArray(JSONObject o, String key) {
		if(o == null || !o.has(key))
			return null;
		
		try {
			return o.getJSONArray(key);
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return null;
	}
	
	public static String getArrayString(JSONArray array, int index) {
		try {
			return array.getString(index);
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return "";
	}
	
}
