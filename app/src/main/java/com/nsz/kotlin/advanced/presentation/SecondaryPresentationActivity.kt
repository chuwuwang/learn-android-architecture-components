package com.nsz.kotlin.advanced.presentation

import android.content.Context
import android.hardware.display.DisplayManager
import android.widget.Toast
import com.nsz.kotlin.ViewBindingActivity
import com.nsz.kotlin.databinding.ActivityAdvancedPresentationSecondaryBinding

class SecondaryPresentationActivity : ViewBindingActivity<ActivityAdvancedPresentationSecondaryBinding>() {

    private lateinit var secondaryPresentation: SecondaryPresentation

    override fun init() {
        initView()
    }

    private fun initView() {
        val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = displayManager.displays
        if (displays == null || displays.size <= 1) {
            Toast.makeText(this, "The not found secondary presentation", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val display = displays[1]
        secondaryPresentation = SecondaryPresentation(this, display)

        binding.btnCancel.setOnClickListener { cancel() }
        binding.btnShowImage.setOnClickListener { showImage() }
        binding.btnSingleText.setOnClickListener { showSingleLineText() }
        binding.btnMultiText.setOnClickListener { showMultiLineText() }
        binding.btnInteractions.setOnClickListener { behavioralInteractions() }
    }

    private fun cancel() {
        secondaryPresentation.dismiss()
    }

    private fun showImage() {
        secondaryPresentation.showImage()
        if ( ! secondaryPresentation.isShowing) secondaryPresentation.show()
    }

    private fun showSingleLineText() {
        secondaryPresentation.showText("Welcome Presentation")
        if ( ! secondaryPresentation.isShowing) secondaryPresentation.show()
    }

    private fun showMultiLineText() {
        secondaryPresentation.showText("Welcome AAC Secondary Presentation")
        if ( ! secondaryPresentation.isShowing) secondaryPresentation.show()
    }

    private fun behavioralInteractions() {
        secondaryPresentation.behavioralInteractions { binding.textContent.text = it }
        if ( ! secondaryPresentation.isShowing) secondaryPresentation.show()
    }

}