package com.nsz.kotlin.advanced.coroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityLaunchScopeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class LaunchScopeActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val binding: ActivityLaunchScopeBinding by lazy { ActivityLaunchScopeBinding.inflate(layoutInflater) }
    private val url = "https://lmg.jj20.com/up/allimg/4k/s/02/21092421351944H-0-lp.jpg"

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        launch(Dispatchers.Main) {
            try {
                val bitmap = getImage()
                val async1 = async {
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width / 2, bitmap.height / 2)
                }
                val async2 = async {
                    Bitmap.createBitmap(bitmap, bitmap.width / 2, 0, bitmap.width / 2, bitmap.height / 2)
                }
                val async3 = async {
                    Bitmap.createBitmap(bitmap, 0, bitmap.height / 2, bitmap.width / 2, bitmap.height / 2)
                }
                val async4 = async {
                    Bitmap.createBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, bitmap.width / 2, bitmap.height / 2)
                }
                binding.iv1.setImageBitmap( async1.await() )
                binding.iv2.setImageBitmap( async2.await() )
                binding.iv3.setImageBitmap( async3.await() )
                binding.iv4.setImageBitmap( async4.await() )
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getImage(): Bitmap = withContext(Dispatchers.IO) {
        val build = Request.Builder().url(url).get().build()
        OkHttpClient().newCall(build).execute().body.byteStream().use { BitmapFactory.decodeStream(it) }
    }

}