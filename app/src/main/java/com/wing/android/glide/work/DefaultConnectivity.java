package com.wing.android.glide.work;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.wing.android.glide.binding.inter.LifecycleListener;
import com.wing.android.glide.util.LOG;
import com.wing.android.glide.work.net.NetworkStateReceive;

// 生命周期 管理的 网络 广播
public class DefaultConnectivity implements LifecycleListener {

    private Context context;
    private NetworkStateReceive networkStateReceive;

    public DefaultConnectivity(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        Log.i(LOG.TAG, "onStart: DefaultConnectivity 做自己的具体业务逻辑处理 ....");

        // 页面可见时注册
        networkStateReceive = new NetworkStateReceive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.context.registerReceiver(networkStateReceive, filter);
    }

    @Override
    public void onStop() {
        Log.i(LOG.TAG, "onStop: DefaultConnectivity 做自己的具体业务逻辑处理 ....");

        // 页面不可见时注销
        if (networkStateReceive != null) {
            this.context.unregisterReceiver(networkStateReceive);
        }
    }

    @Override
    public void onDestroy() {
        // Do nothing.
    }
}
