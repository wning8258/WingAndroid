package com.wning.demo.architecture;

import com.guagua.modules.utils.LogUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class LocationListener implements LifecycleObserver {

    private static final String TAG="LocationListener";

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){
        LogUtils.i(TAG,TAG+" onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        LogUtils.i(TAG,TAG+" onStop");
    }
}
