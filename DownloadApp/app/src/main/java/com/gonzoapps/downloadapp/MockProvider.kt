package com.gonzoapps.downloadapp

import com.gonzoapps.downloadapp.domain.UrlOption

object MockProvider {
    fun dataSync() : List<UrlOption> {
        val urlOption1 = UrlOption(
            "Glide by Bumptech - Image Library",
            "https://github.com/bumptech/glide"
        )
        val urlOption2 = UrlOption(
            "Retrofit by Square - Networking Library",
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        )
        val urlOption3 = UrlOption(
            "LoadApp Starter Code by Udacity - Android Kotlin ND Project 3",
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        )

        return listOf(
            urlOption1, urlOption2, urlOption3
        )
    }
}