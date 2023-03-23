package com.wing.android.lru.fragment;

// Fragment 监听
public interface LifecycleCallback {

    // 生命周期 开始初始化了
    void glideInitAction();  // onStart

    // 生命周期 停止了
    void glideStopAction(); // onStop

    // 生命周期 释放操作
    void glideRecycleAction(); // onDestroy

}
