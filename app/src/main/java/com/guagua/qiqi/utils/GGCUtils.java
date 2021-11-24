package com.guagua.qiqi.utils;

/**
 * ***********************************************************************************
* Module Name: C程序辅助工具类</br>
* File Name: <b>GGCUtils.java</b></br>
* Description: C/C++编写的辅助工具类</br>
* Author: TPX</br>
* 版权 2008-2015，浙江齐聚科技有限公司 </br>
* 所有版权保护
* 这是浙江齐聚科技有限公司 未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司 
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class GGCUtils {
	private static GGCUtils instance ;
	
	public static GGCUtils getInstance() {
		if(instance == null) {
			instance = new GGCUtils();
		}
		return instance ;
	}
	private GGCUtils(){}

	 static {
	        System.loadLibrary("qiqiutils");
	    }
	 /**
	  * http请求头需要的加密校验码
	  * @author: TPX
	  * @param i64QiqiId
	  * @param i64Timestamp
	  * @return: String
	  * @date: 2015-4-25 上午9:33:55
	  */
	 public native String httpEncryptToken(long i64QiqiId,long i64Timestamp);
}
