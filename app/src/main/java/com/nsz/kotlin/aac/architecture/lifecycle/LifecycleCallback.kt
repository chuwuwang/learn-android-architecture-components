package com.nsz.kotlin.aac.architecture.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.nsz.kotlin.App
import com.nsz.kotlin.ux.common.CommonLog

class LifecycleCallback : Application.ActivityLifecycleCallbacks {

    companion object {

        private const val TAG = "LifecycleCallback"

    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        val clazz = activity.javaClass.name
        CommonLog.e("onActivityCreated $clazz")
        ActivityManager.pushActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        val clazz = activity.javaClass.name
        CommonLog.e("onActivityStarted $clazz")
    }

    override fun onActivityResumed(activity: Activity) {
        val clazz = activity.javaClass.name
        // CommonLog.e("onActivityResumed $clazz")
        App.crashHandler.setCurrentActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        val clazz = activity.javaClass.name
        // CommonLog.e("onActivityPaused $clazz")
        App.crashHandler.setCurrentActivity(null)
    }

    override fun onActivityStopped(activity: Activity) {
        val clazz = activity.javaClass.name
        // CommonLog.e("onActivityStopped $clazz")
    }

    override fun onActivityDestroyed(activity: Activity) {
        val clazz = activity.javaClass.name
        CommonLog.e("onActivityDestroyed $clazz")
        ActivityManager.popActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        val clazz = activity.javaClass.name
        CommonLog.e("onActivitySaveInstanceState $clazz")
    }

}