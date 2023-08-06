package com.wning.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * 前台追踪器
 */
object ActivityTracker : Application.ActivityLifecycleCallbacks {


    val foreground: Activity? get() = ref.get()

    @Volatile
    private var ref = WeakReference<Activity>(null)

    override fun onActivityResumed(activity: Activity) {
        ref = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        ref.clear()
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}