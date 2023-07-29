package com.wning.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.mmkv.MMKV;

/**
 * 保存与登录状态无关的信息(userid例外字段用于判断登录状态)
 * Created by liangruiyu on 2018/2/26.
 * update 20190219 ： 替换为MMKV后，不再支持使用 key 获取 Object 的方式，一般需要指定需要返回的具体数据类型
 */

public class AppInfoSPManager {

    private static final String SCHEME_HTTP = "http://";
    private static final String SCHEME_HTTPS = "https://";

    //private static final String KEY_INIT_APP = TextUtils.equals(WBBuildConfig.APPLICATION_ID, "com.wodi.who") ? "3c1bf3329c8dd6b1" : "3c1bf3329c8dd6b1_" + WBBuildConfig.APPLICATION_ID;

    private static volatile AppInfoSPManager instance;

    private MMKV mSP;

    private SharedPreferences.Editor mEditor;

    private AppInfoSPManager() {
    }

    public static AppInfoSPManager getInstance() {
        if (instance == null) {
            //保证异步处理安全操作
            synchronized (AppInfoSPManager.class) {
                if (instance == null) {
                    instance = new AppInfoSPManager();
                    instance.init();
                }
            }
        }
        return instance;
    }

    public void init() {
        mSP = MMKV.mmkvWithID("default_preferences", MMKV.MULTI_PROCESS_MODE);
        mEditor = mSP.edit();
    }

    public void setDarkMode(int darkMode) {
        mEditor.putInt("darkMode", darkMode).commit();
    }

    public int getDarkMode() {
        return mSP.getInt("darkMode", 0);
    }
}
