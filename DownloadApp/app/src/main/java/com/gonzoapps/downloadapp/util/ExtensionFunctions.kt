package com.gonzoapps.downloadapp.util

import android.R
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.widget.RadioButton
/*
* Source: https://android--code.blogspot.com/2020/07/android-kotlin-radiobutton-circle-color.html
* */
fun RadioButton.setCircleColor(color: Int){
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-R.attr.state_checked), // unchecked
            intArrayOf(R.attr.state_checked) // checked
        ), intArrayOf(
            Color.GRAY, // unchecked color
            color // checked color
        )
    )

    // finally, set the radio button's button tint list
    buttonTintList = colorStateList

    // optionally set the button tint mode or tint blend mode
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        buttonTintBlendMode = BlendMode.SRC_IN
    }else{
        buttonTintMode = PorterDuff.Mode.SRC_IN
    }

    invalidate() //could not be necessary
}