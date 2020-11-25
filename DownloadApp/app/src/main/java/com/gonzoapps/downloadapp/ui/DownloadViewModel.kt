package com.gonzoapps.downloadapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber

class DownloadViewModel(private val app: Application) : AndroidViewModel(app) {

    init {
        Timber.i("init viewmodel")
    }

    class Factory(val app: android.app.Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DownloadViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DownloadViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}