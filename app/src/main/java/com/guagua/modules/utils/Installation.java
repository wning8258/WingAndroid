package com.guagua.modules.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.text.TextUtils;

public class Installation {
	private static String sID = null;
	private static final String INSTALLATION = "INSTALLATION";

	public synchronized static String getIMEI(Context context) {
		if (sID == null) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists()) {
					writeInstallationFile(context, installation);
				}
				sID = readInstallationFile(installation);
				System.out.println("installation = " + sID);
			}
			catch (Exception e) {
				
			}
		}
		return sID;
	}

	public static String readInstallationFile(File installation) throws IOException {
		FileInputStream fis = new FileInputStream(installation);
		byte[] bytes = new byte[(int) fis.available()];
		fis.read(bytes);
		fis.close();
		return new String(bytes);
	}

	public static void writeInstallationFile(Context context, File installation) throws IOException {
		String imei = null;
		try {
			imei = PhoneHelper.getDeviceId(context);
			if (TextUtils.isEmpty(imei) || imei.equals("null") || Utils.parseStr2Int(imei) == 0) {
				imei = PhoneHelper.getMac(context);
			}
		}
		catch (Exception e) {
			LogUtils.printStackTrace(e);
		}
		if (TextUtils.isEmpty(imei)) {
			imei = String.valueOf(java.util.UUID.randomUUID());
		}
		if (!Utils.isHexString(imei)) {
			imei = MD5.generate(imei);
		}
		FileOutputStream out = new FileOutputStream(installation);
		out.write(imei.getBytes());
		out.close();
	}
}
