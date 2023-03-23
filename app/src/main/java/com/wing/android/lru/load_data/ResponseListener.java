package com.wing.android.lru.load_data;


import com.wing.android.lru.resource.Value;

/**
 * 加载外部资源 成功 和 失败 回调
 */
public interface ResponseListener {

    public void responseSuccess(Value value); // 成功 Value

    public void responseException(Exception e); // 错误详情

}
