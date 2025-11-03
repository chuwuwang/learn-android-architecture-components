package com.nsz.kotlin.performance.monitor

import android.app.Activity
import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.view.PixelCopy
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class CrashHandler(private val application: Application) : Thread.UncaughtExceptionHandler {

    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    private var currentActivity: WeakReference<Activity> ? = null

    fun setCurrentActivity(activity: Activity ? ) {
        if (activity != null) {
            currentActivity = WeakReference(activity)
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        e.printStackTrace()
        Log.e(TAG, "Uncaught exception detected on thread: " + t.name)
        // 使用 runBlocking 确保在崩溃前完成截图捕获
        try {
            runBlocking { withTimeout(SCREENSHOT_TIMEOUT_MS) { captureScreenshot(e) } }
        } catch (e: Exception) {
            Log.d(TAG, "Failed to handle crash with screenshot", e)
        }
        val handler = defaultHandler ?: return
        handler.uncaughtException(t, e)
    }

    private suspend fun captureScreenshot(throwable: Throwable) {
        val weakReference = currentActivity ?: return
        val activity = weakReference.get()
        if (activity == null) {
            Log.e(TAG, "No current activity, not capturing screenshot")
            return
        }
        val root = activity.window.decorView.rootView
        if (root == null || root.width <= 0 || root.height <= 0 || !root.isShown) {
            Log.e(TAG, "Root view is invalid, not capturing screenshot")
            return
        }
        val window = activity.window
        if (window == null) {
            Log.e(TAG, "Phone window is null, not capturing screenshot")
            return
        }
        val screenshot = Bitmap.createBitmap(root.width, root.height, Bitmap.Config.ARGB_8888)
        try {
            val handler = android.os.Handler(activity.mainLooper)
            PixelCopy.request(activity.window, screenshot, onPixelCopyFinishedListener, handler)
        } catch (e: Exception) {
            Log.d(TAG, "Failed to capture screenshot", e)
        }
        saveScreenshotToFile(screenshot, throwable)
    }

    private val onPixelCopyFinishedListener = PixelCopy.OnPixelCopyFinishedListener { copyResult ->
        if (copyResult == PixelCopy.SUCCESS) {
            Log.e(TAG, "Screenshot captured successfully")
        } else {
            Log.e(TAG, "Failed to capture screenshot")
        }
    }

    private fun saveScreenshotToFile(bitmap: Bitmap, throwable: Throwable) {
        executor.execute {
            try {
                val screenshotsDir = File(application.filesDir, SCREENSHOTS_DIR)
                val exists = screenshotsDir.exists()
                if ( ! exists) {
                    screenshotsDir.mkdirs()
                }
                val date = Date()
                val simpleName = throwable.javaClass.simpleName
                val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(date)
                val filename = "screenshot_" + timestamp + "_$simpleName.jpg"
                val screenshotFile = File(screenshotsDir, filename)
                FileOutputStream(screenshotFile).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                }
                Log.e(TAG, "Screenshot saved to: " + screenshotFile.absolutePath)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to save screenshot", e)
            }
        }
    }

    companion object {

        private const val TAG = "CrashScreenshotHandler"

        private const val SCREENSHOTS_DIR = "crash_screenshots"

        private const val SCREENSHOT_TIMEOUT_MS = 5000L

    }

}