package com.gonzoapps.downloadapp

import com.gonzoapps.downloadapp.domain.UrlOption

object MockProvider {
    fun dataSync() : List<UrlOption> {
        val urlOption1 = UrlOption(
            "Glide by Bumptech v4.11.0 - Image Library",
            "https://github.com/bumptech/glide/archive/v4.11.0.zip"
        )
        val urlOption2 = UrlOption(
            "Retrofit by Square v2.9.0 - Networking Library",
            "https://github.com/square/retrofit/archive/2.9.0.zip"
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