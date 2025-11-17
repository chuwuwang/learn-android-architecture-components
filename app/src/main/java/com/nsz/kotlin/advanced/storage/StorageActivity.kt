package com.nsz.kotlin.advanced.storage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsz.kotlin.databinding.ActivityStorageBinding
import com.nsz.kotlin.ux.common.startActivity

class StorageActivity : AppCompatActivity() {

    private val binding: ActivityStorageBinding by lazy { ActivityStorageBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.mbSaf.setOnClickListener { startActivity<SAFActivity>() }
        binding.mbInternalAndExternal.setOnClickListener { startActivity<ScopedStorageActivity>() }
    }

}