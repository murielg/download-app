package com.gonzoapps.downloadapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class UrlOption(
    val name: String,
    val url: String
)

@Parcelize
data class DownloadDetails(
        var id: Long = 0,
        var url: String = "",
        var success: Boolean = false
) : Parcelable