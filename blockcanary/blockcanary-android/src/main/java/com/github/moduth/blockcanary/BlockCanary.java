/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.moduth.blockcanary;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.github.moduth.blockcanary.ui.DisplayActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
import static android.content.pm.PackageManager.DONT_KILL_APP;

public final class BlockCanary {

    private static final String TAG = "BlockCanary";

    private static BlockCanary sInstance;
    private BlockCanaryInternals mBlockCanaryCore;
    private boolean mMonitorStarted = false;

    private BlockCanary() {
        //初始化BlockCanaryInternals调度类
        BlockCanaryInternals.setContext(BlockCanaryContext.get());
        mBlockCanaryCore = BlockCanaryInternals.getInstance();

        //为BlockCanaryInternals添加拦截器（责任链）BlockCanaryContext对BlockInterceptor是空实现
        //这里设置的是用户通过install传进来的BlockCanaryContext
        mBlockCanaryCore.addBlockInterceptor(BlockCanaryContext.get());
        if (!BlockCanaryContext.get().displayNotification()) {
            return;
        }
        //DisplayService只在开启通知栏消息的时候添加，当卡顿发生时将通过DisplayService发起通知栏消息
        mBlockCanaryCore.addBlockInterceptor(new DisplayService());

    }

    /**
     * Install {@link BlockCanary}
     *
     * @param context            Application context
     * @param blockCanaryContext BlockCanary context
     * @return {@link BlockCanary}
     */
    public static BlockCanary install(Context context, BlockCanaryContext blockCanaryContext) {
        //BlockCanaryContext.init会将保存应用的applicationContext和用户设置的配置参数
        BlockCanaryContext.init(context, blockCanaryContext);
        //setEnabled将根据用户的通知栏消息配置开启
        setEnabled(context, DisplayActivity.class, BlockCanaryContext.get().displayNotification());
        //get方法 返回BlockCanary的单例对象
        return get();
    }

    /**
     * Get {@link BlockCanary} singleton.
     *
     * @return {@link BlockCanary} instance
     */
    public static BlockCanary get() {
        //使用单例创建了一个BlockCanary对象
        if (sInstance == null) {
            synchronized (BlockCanary.class) {
                if (sInstance == null) {
                    sInstance = new BlockCanary();
                }
            }
        }
        return sInstance;
    }

    /**
     * Start monitoring.
     */
    public void start() {
        if (!mMonitorStarted) {
            mMonitorStarted = true;
            //把mBlockCanaryCore中的monitor设置MainLooper中进行监听
            Looper.getMainLooper().setMessageLogging(mBlockCanaryCore.monitor);
        }
    }

    /**
     * Stop monitoring.
     */
    public void stop() {
        if (mMonitorStarted) {
            mMonitorStarted = false;
            Looper.getMainLooper().setMessageLogging(null);
            mBlockCanaryCore.stackSampler.stop();
            mBlockCanaryCore.cpuSampler.stop();
        }
    }

    /**
     * Zip and upload log files, will user context's zip and log implementation.
     */
    public void upload() {
        Uploader.zipAndUpload();
    }

    /**
     * Record monitor start time to preference, you may use it when after push which tells start
     * BlockCanary.
     */
    public void recordStartTime() {
        PreferenceManager.getDefaultSharedPreferences(BlockCanaryContext.get().provideContext())
                .edit()
                .putLong("BlockCanary_StartTime", System.currentTimeMillis())
                .commit();
    }

    /**
     * Is monitor duration end, compute from recordStartTime end provideMonitorDuration.
     *
     * @return true if ended
     */
    public boolean isMonitorDurationEnd() {
        long startTime =
                PreferenceManager.getDefaultSharedPreferences(BlockCanaryContext.get().provideContext())
                        .getLong("BlockCanary_StartTime", 0);
        return startTime != 0 && System.currentTimeMillis() - startTime >
                BlockCanaryContext.get().provideMonitorDuration() * 3600 * 1000;
    }

    // these lines are originally copied from LeakCanary: Copyright (C) 2015 Square, Inc.
    private static final Executor fileIoExecutor = newSingleThreadExecutor("File-IO");

    private static void setEnabledBlocking(Context appContext,
                                           Class<?> componentClass,
                                           boolean enabled) {
        ComponentName component = new ComponentName(appContext, componentClass);
        PackageManager packageManager = appContext.getPackageManager();
        int newState = enabled ? COMPONENT_ENABLED_STATE_ENABLED : COMPONENT_ENABLED_STATE_DISABLED;
        // Blocks on IPC.
        packageManager.setComponentEnabledSetting(component, newState, DONT_KILL_APP);
    }
    // end of lines copied from LeakCanary

    private static void executeOnFileIoThread(Runnable runnable) {
        fileIoExecutor.execute(runnable);
    }

    private static Executor newSingleThreadExecutor(String threadName) {
        return Executors.newSingleThreadExecutor(new SingleThreadFactory(threadName));
    }

    private static void setEnabled(Context context,
                                   final Class<?> componentClass,
                                   final boolean enabled) {
        final Context appContext = context.getApplicationContext();
        executeOnFileIoThread(new Runnable() {
            @Override
            public void run() {
                setEnabledBlocking(appContext, componentClass, enabled);
            }
        });
    }
}
