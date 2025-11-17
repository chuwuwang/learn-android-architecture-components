package com.nsz.kotlin.advanced.presentation

import com.nsz.kotlin.ViewBindingActivity
import com.nsz.kotlin.databinding.ActivityAdvancedPresentationBinding
import com.nsz.kotlin.ux.common.startActivity

class PresentationActivity : ViewBindingActivity<ActivityAdvancedPresentationBinding>() {

    override fun init() {
        binding.btnSecondaryPresentation.setOnClickListener { startActivity<SecondaryPresentationActivity>() }
    }

}