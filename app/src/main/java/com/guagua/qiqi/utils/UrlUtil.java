package com.guagua.qiqi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * Url地址操作类
 * 提供不带任何参数的Url地址和参数map
 * 转换为完整的url地址
 * @author zdt
 *
 */
public class UrlUtil {
	@SuppressWarnings("unused")
	private static String TAG="UrlUtil";
	
	public static List<NameValuePair> makePostBody(HashMap params){
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		if(params!=null){
			Iterator<java.util.Map.Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				java.util.Map.Entry<String, String> entry = it.next();
				if(entry.getValue()!=null){
					//postParams.add(new BasicNameValuePair(entry.getKey().toString(), 
						//	URLEncoder.encode(entry.getValue().toString(),Constants.SERVER_ENCODE)));
					postParams.add(new BasicNameValuePair(entry.getKey().toString(),entry.getValue().toString()));
				}	
			}	
		}
		return postParams;
	}

	public static String makeGetUrl(String baseUrl,HashMap params){
		StringBuffer sb = new StringBuffer();
		sb.append(baseUrl);
		if(params!=null && params.size()>0){
			if(!baseUrl.contains("?"))
				sb.append("?");
			return RegroupParams(sb, params);	
		}
		return sb.toString();
	}
	
	/**
	 * 组合参数
	 * 
	 * @param sb
	 * @param params
	 * @return
	 */
	private static String RegroupParams(StringBuffer sb, HashMap params) {
		if(params!=null){
			Iterator<java.util.Map.Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				java.util.Map.Entry<String, String> entry = it.next();
				sb.append("&" + entry.getKey() + "=");
				sb.append( entry.getValue().toString());//java.net.URLEncoder.encode()
			}	
		}
		String parasStr=sb.toString();
		return parasStr.replace("?&", "?");
	}
	
	
	
}
