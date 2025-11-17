package com.nsz.kotlin.advanced.presentation

import android.app.Presentation
import android.content.Context
import android.view.Display
import android.view.View
import android.widget.Toast
import com.nsz.kotlin.R
import com.nsz.kotlin.databinding.ActivityAdvancedPresentationSecondary2Binding

class SecondaryPresentation(context: Context, display: Display) : Presentation(context, display) {

    private val binding: ActivityAdvancedPresentationSecondary2Binding

    init {
        binding = ActivityAdvancedPresentationSecondary2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showImage() {
        binding.textView.visibility = View.GONE
        binding.clickMeBtn.visibility = View.GONE
        binding.imageView.setVisibility(View.VISIBLE)
        binding.imageView.setImageResource(R.drawable.profile_98)
    }

    fun showText(text: String) {
        binding.textView.text = text
        binding.textView.visibility = View.VISIBLE
        binding.clickMeBtn.visibility = View.GONE
        binding.imageView.setVisibility(View.GONE)
    }

    fun behavioralInteractions(action: (String) -> Unit) {
        binding.clickMeBtn.setOnClickListener {
            action("Hello, I'm Secondary Presentation")
            Toast.makeText(context, "Hello, I'm Secondary Presentation", Toast.LENGTH_SHORT).show()
        }
        binding.textView.visibility = View.GONE
        binding.clickMeBtn.visibility = View.VISIBLE
        binding.imageView.setVisibility(View.GONE)
    }

}