package com.wning.demo.gifteffect;


import com.guagua.modules.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/*************************************************************************************
* Module Name: bean操作的基类
* File Name: BaseBean.java
* Description: 封装一些基本数据bean
* Author: 郭晓明
* 版权 2008-2015，浙江齐聚科技有限公司
* 所有版权保护
* 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
***************************************************************************************/
public class BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String getString(JSONObject o, String key) {
		return ProtocolParser.getJsonStr(o, key, "");
	}

	protected int getInt(JSONObject o, String key) {
		return ProtocolParser.getJsonInt(o, key);
	}
	
	protected long getLong(JSONObject o, String key) {
		return ProtocolParser.getJsonLong(o, key);
	}

	protected int getInt(JSONObject o, String key, int defaultValue) {
		return ProtocolParser.getJsonInt(o, key, defaultValue);
	}

	protected boolean getBoolean(JSONObject o, String key) {
		return ProtocolParser.getJsonBool(o, key);
	}
	
	protected JSONArray getArray(JSONObject o, String key) {
		return ProtocolParser.getArray(o, key);
	}
	
	protected String getArrayString(JSONArray array, int index) {
		return ProtocolParser.getArrayString(array, index);
	}
	
	protected JSONObject getJSONObject(JSONObject jsonObject, String key) {
		if(jsonObject == null)
			return null;
		
		try {
			if (jsonObject.has(key)) {
				return jsonObject.getJSONObject(key);
			}
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e) {
			LogUtils.printStackTrace(e);
		} 
		
		return null;
	}
	
	protected JSONObject getJSONObject(JSONArray jsonArray,int index){
		if(jsonArray == null)
			return null;
		
		try{
			return jsonArray.getJSONObject(index);
		} catch (JSONException e) {
			LogUtils.printStackTrace(e);
		} catch (Exception e){
			LogUtils.printStackTrace(e);
		}
		
		return null;
	}
}
