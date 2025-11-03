package com.nsz.kotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import com.nsz.kotlin.aac.architecture.lifecycle.LifecycleCallback
import com.nsz.kotlin.performance.monitor.CrashHandler
import io.realm.Realm
import io.realm.RealmConfiguration

@SuppressLint("StaticFieldLeak")
class App : Application() {

    companion object {

        const val TAG = "strawberry"

        lateinit var instance: App
        lateinit var context: Context
        lateinit var crashHandler: CrashHandler
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext

        initializeCrashHandler()

        val appStatusObserver = AppStatusObserver()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appStatusObserver)

        val lifecycleCallback = LifecycleCallback()
        registerActivityLifecycleCallbacks(lifecycleCallback)

        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        Stetho.initializeWithDefaults(this)
    }

    private fun initializeCrashHandler() {
        crashHandler = CrashHandler(application = this)
        Thread.setDefaultUncaughtExceptionHandler(crashHandler)
    }

}