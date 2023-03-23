package com.wing.android.glide.work;

import android.util.Log;

import com.wing.android.glide.binding.inter.LifecycleListener;
import com.wing.android.glide.util.LOG;

public class TargetTracker implements LifecycleListener {

    public TargetTracker() {

    }

    @Override
    public void onStart() {
        Log.i(LOG.TAG, "onStart: TargetTracker 做自己的具体业务逻辑处理 ....");
    }

    @Override
    public void onStop() {
        Log.i(LOG.TAG, "onStop: TargetTracker 做自己的具体业务逻辑处理 ....");
    }

    @Override
    public void onDestroy() {
        Log.i(LOG.TAG, "onDestroy: TargetTracker 做自己的具体业务逻辑处理 ....");
    }
}
