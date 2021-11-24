package com.guagua.qiqi.utils;
import java.util.HashMap;

/*************************************************************************************
 * Module Name: 网络Http请求接口配置类 
 * File Name: QiQiHttpConfig.java
 * Description: 实现Http网络请求的参数配置
 * Author: 薛文超
 * 版权 2008-2015，金华长风信息技术有限公司
 * 所有版权保护
 * 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
 *************************************************************************************/
public class QiQiHttpConfig {
	public static final String OEMID =31+"";
	private static final String REFERER = "http://tiantian.qq.com";

	public static HashMap<String, String> getHeader() {
		HashMap<String, String> headers = new HashMap<String, String>();
		String MOBILE = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;// 操作系统版本
		if (MOBILE.length() > 20) { //服务器数据库表字段设置最多20个字符
			MOBILE = MOBILE.substring(0, 20);
		}
		String SV = android.os.Build.VERSION.RELEASE;// 操作系统版本
		headers.put("did", "863360026164321");// IMEI码);
		headers.put("network", "wifi");// 入网方式
		headers.put("oemid", OEMID);// Android 15 ,IOS 16
		headers.put("version", "1.0.5.0");// 客户端软件版本 如：1.0，2.0
		headers.put("sy", SV);// 系统版本 如：2.0，2.2，2.2.1，3.1，3.1.1，3.1.2
		headers.put("language","CN");// 语言版本
		headers.put("channel", "50");
		headers.put("mobile", MOBILE);// 设备型号
		headers.put("dt", "1");
		headers.put("userid", "73812846");
		headers.put("Referer", REFERER);
		long timestamp = System.currentTimeMillis();
		headers.put("cleartext", +timestamp + ""); //明文校验
		headers.put("meck", "c37efaa728f68609bf1278a05581dcc504779f4b4e66bf3e8b6585d384317f02");//锦堂要求增加Meck
		headers.put("ciphertext", GGCUtils.getInstance().httpEncryptToken(Long.parseLong("73812846"), timestamp)); //加密校验
		return headers;
	}
}
