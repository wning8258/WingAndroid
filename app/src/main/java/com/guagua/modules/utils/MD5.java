package com.guagua.modules.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static final String TAG = "MD5";

	private static final String HASH_ALGORITHM = "MD5";
	private static final int RADIX = 16; // 16进制

	public static String generate(String val) {
		byte[] md5 = getMD5(val.getBytes());
		BigInteger bi = new BigInteger(md5).abs();
		return bi.toString(RADIX);
	}

	private static byte[] getMD5(byte[] data) {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(data);
			hash = digest.digest();
		}
		catch (NoSuchAlgorithmException e) {
			LogUtils.printStackTrace(e);
		}
		return hash;
	}
	
	public static void main(String[] args) {
		String test = "sdfsdfsdfsdfsdf";
		String md5 = generate(test);
		System.out.println(md5);
	}
}
