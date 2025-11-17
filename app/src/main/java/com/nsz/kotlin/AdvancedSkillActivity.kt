package com.nsz.kotlin

import android.content.Intent
import com.nsz.kotlin.advanced.coroutines.LaunchScopeActivity
import com.nsz.kotlin.advanced.storage.StorageActivity
import com.nsz.kotlin.databinding.ActivityAdvancedSkillBinding
import com.nsz.kotlin.nfc.NFCActivity
import com.nsz.kotlin.ux.common.startActivity

class AdvancedSkillActivity : ViewBindingActivity<ActivityAdvancedSkillBinding>() {

    override fun init() {
        binding.btnNfc.setOnClickListener { startActivity<NFCActivity>() }
        binding.btnStorage.setOnClickListener {
            val intent = Intent(this, StorageActivity::class.java)
            startActivity(intent)
        }
        binding.btnCoroutines.setOnClickListener { startActivity<LaunchScopeActivity>() }
    }

}