package com.gonzoapps.downloadapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class UrlOption(
    val name: String,
    val url: String
)
@Parcelize
data class DownloadDetails(
        val id: Long,
        val url: String,
        val success: Boolean
) : Parcelable