package com.wing.android.glide.work;

import android.util.Log;

import com.wing.android.glide.binding.inter.LifecycleListener;
import com.wing.android.glide.util.LOG;

public class ImageViewTarget implements LifecycleListener {

    @Override
    public void onStart() {
        Log.i(LOG.TAG, "onStart: ImageViewTarget 做自己的具体业务逻辑处理 ....");
    }

    @Override
    public void onStop() {
        Log.i(LOG.TAG, "onStop: ImageViewTarget 做自己的具体业务逻辑处理 ....");
    }

    @Override
    public void onDestroy() {
        Log.i(LOG.TAG, "onDestroy: ImageViewTarget 做自己的具体业务逻辑处理 ....");
    }
}
