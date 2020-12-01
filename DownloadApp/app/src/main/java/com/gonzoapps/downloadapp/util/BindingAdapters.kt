package com.gonzoapps.downloadapp.util

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

@BindingAdapter("selectedUrl")
fun setSelectedUrl(radioGroup: RadioGroup, url: MutableLiveData<String>) {
    radioGroup.setOnCheckedChangeListener { radioGroup, i ->
        val option = radioGroup.findViewById<RadioButton>(i)
        url.value = option.text.toString()
    }
}