package com.wing.android.lru.cache;


import com.wing.android.lru.resource.Value;
import com.wing.android.lru.resource.ValueCallback;
import com.wing.android.lru.util.Tool;

import java.util.HashMap;
import java.util.Map;

// 活动缓存 ---> 正在使用的图片 访问 活动缓存
public class ActiveCache {

    private Map<String, Value> mapList = new HashMap<>();  // 容器

    private ValueCallback valueCallback;

    public ActiveCache(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    /**
     * TODO 添加 活动缓存
     */
    public void put(String key, Value value) {
        Tool.checkNotEmpty(key); // key 不能为空

        // 每次put的时候 put进来的Value 绑定到 valueCallback
        value.setCallback(this.valueCallback);

        // 存储 --> 容器
        mapList.put(key, value);
    }

    /**
     * TODO 给外界获取Value
     */
    public Value get(String key) {
        Value value = mapList.get(key);
        if (null != value) {
            return value; // 返回容器里面的 Value
        }
        return null;
    }

    /**
     * TODO 释放 活动缓存
     */
    public void recycleActive() {
        for (Map.Entry<String, Value> valueEntry : mapList.entrySet()) {
            valueEntry.getValue().recycle(); // 移除后 加入到  LRU内存缓存
            mapList.remove(valueEntry.getKey()); // 移除
        }
    }
}
