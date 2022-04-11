package com.wing.android.utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class TimeCache {
    public static Map<String, Long> sStartTime = new HashMap<>();
    public static Map<String, Long> sEndTime = new HashMap<>();

    public static void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
    }

    public static void setEndTime(String methodName, long time) {
        sEndTime.put(methodName, time);
    }

    public static String getCostTime(String methodName) {
        long start = sStartTime.get(methodName);
        long end = sEndTime.get(methodName);
        long dex = end - start;
        return "method: " + methodName + " cost " + dex + " ns";
    }

    private void countTime() {
        TimeCache.setStartTime("newFunc", System.currentTimeMillis());
        TimeCache.setEndTime("newFunc", System.currentTimeMillis());
        Log.d("耗时", TimeCache.getCostTime("newFunc"));
    }
}
