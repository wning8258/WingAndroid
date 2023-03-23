package com.wing.android.glide;

import android.content.Context;
import android.util.Log;

import androidx.annotation.GuardedBy;

import com.wing.android.glide.binding.ApplicationLifecycle;
import com.wing.android.glide.binding.inter.Lifecycle;
import com.wing.android.glide.binding.inter.LifecycleListener;
import com.wing.android.glide.util.LOG;
import com.wing.android.glide.work.DefaultConnectivity;
import com.wing.android.glide.work.ImageViewTarget;
import com.wing.android.glide.work.TargetTracker;

public class RequestManager implements LifecycleListener {

    // 伪代码模拟 ...

    // Glide 大量采用 两种方式

    // 第一种写法  RM.onStart
    @GuardedBy("this")
    private final ImageViewTarget imageViewTarget = new ImageViewTarget();
    private final TargetTracker targetTracker = new TargetTracker();

    // 第二种写法  Defatxxx 注册到  this.lifecycle.addListener
    private DefaultConnectivity defaultConnectivity;

    private Lifecycle lifecycle;

    public RequestManager(Glide glide, Lifecycle lifecycle, Context applicationContext) {
        this.lifecycle = lifecycle;

        this.lifecycle.addListener(this); // 构造函数 已经给自己注册了【自己给自己绑定】

        defaultConnectivity = new DefaultConnectivity(applicationContext);
        this.lifecycle.addListener(defaultConnectivity); // 网络广播注册
    }

    // Activity/Fragment 可见时恢复请求 （onStart() ） 掉用函数
    @Override
    public void onStart() {
        Log.d(LOG.TAG, "开始执行生命周期业务 onStart: 运行队列 全部执行，等待队列 全部清空 ....");
        imageViewTarget.onStart();
        targetTracker.onStart();
        // defaultConnectivity.onStart(); 不需要
    }

    // Activity/Fragment 不可见时暂停请求 （onStop() ） 掉用函数
    @Override
    public void onStop() {
        Log.d(LOG.TAG, "开始执行生命周期业务 onStop: 运行队列 全部停止，把任务都加入到等待队列 ....");
        imageViewTarget.onStop();
        targetTracker.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG.TAG, "开始执行生命周期业务 onDestroy: 自己负责移除自己绑定的生命周期监听，释放操作 ....");
        this.lifecycle.removeListener(this); // 已经给自己销毁了 【自己给自己移除】
        imageViewTarget.onDestroy();
        targetTracker.onDestroy();

        this.lifecycle.removeListener(defaultConnectivity); // 网络广播注销
    }
}
