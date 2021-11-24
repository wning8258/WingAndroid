package com.wning.demo.net;

import android.text.TextUtils;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*************************************************************************************
* Module Name: 网络接口请求帮助类
* File Name: SignHelper.java
* Description: 添加签名的网络请求帮助类
* Author: 郭晓明
* 版权 2008-2015，浙江齐聚科技有限公司 
* 所有版权保护
* 这是浙江齐聚科技有限公司 未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司 
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
***************************************************************************************/
public class SignHelper {
	private static final String KEY = "qiqiMobile!)5865#$%^7";
	private static final String PAY_KEY = "96aed7ebf98e5a5389e24a5ce3ad30cb";

	public static void addSign(Map<String, String> sArray){
		Map<String, String> map = paraFilter(sArray);
		String str= createLinkString(map);
		String _sign=SHA1(str+"&"+KEY);
		sArray.put("sign", _sign);
	}
	
	 /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {
    	HashMap<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) 
            return result;

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
	
	
	/**
	 * 使用SAH1进行加密
	 *  @param str 需要加密的字符串
	 *  @return String 加密后的值
	 */
    private static String SHA1(String str){
    	if(TextUtils.isEmpty(str))
    		return "";
    	
		String value="";
		try {
    		MessageDigest md = MessageDigest.getInstance("SHA-1");
    		md.update(str.getBytes("UTF-8"));
    		byte[] result = md.digest();
    		StringBuffer sb = new StringBuffer();
    		for (byte b : result) {
    			int i = b & 0xff;
    			if (i < 0xf) {
    				sb.append(0);
    			}
    			sb.append(Integer.toHexString(i));
    		}
    		value=sb.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	 /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> params) {
    	if(params == null)
    		return null;
    	
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }


    /**
     * MD5生成签名数据
     * @param s
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String data,String charset)  {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes(charset));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        byte tmp[] = md.digest();
        String mdPemStr = new String(Hex.encodeHex(tmp));
        return mdPemStr;
    }

    /**
     * 生成签名密钥
     * @param paramsMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static void makePaySign(Map<String,String> paramsMap) {
        try {
            paramsMap.remove("sign");//移除sign字段进入签名
            Object[] keys = paramsMap.keySet().toArray();
            Arrays.sort(keys);
            StringBuilder buffer= new StringBuilder();
            for(int i=0; i<keys.length; i++){
                buffer.append(keys[i]).append("=").append(paramsMap.get(keys[i]));
                if (i!=keys.length-1){
                    buffer.append("&");
                }
            }
            String context =buffer.toString()+"&key="+PAY_KEY;
//		     System.out.println(md5(context)+",context:"+context);
            String _sign=md5(context,"UTF-8").toUpperCase();
            paramsMap.put("sign", _sign);
        }catch (Exception e) {
            e.printStackTrace();
            // throw new Va("签名异常");
        }
    }

}
