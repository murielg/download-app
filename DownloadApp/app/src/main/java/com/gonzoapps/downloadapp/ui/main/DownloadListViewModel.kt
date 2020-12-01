package com.gonzoapps.downloadapp.ui.main

import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.gonzoapps.downloadapp.MockProvider
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.domain.UrlOption
import timber.log.Timber


class DownloadListViewModel(private val app: Application) : AndroidViewModel(app) {

    private var downloadID: Long = 0

    private val _options = MutableLiveData<List<UrlOption>>()
    val options : LiveData<List<UrlOption>>
        get() = _options

    init {
        _options.value = MockProvider.dataSync()
    }

    fun download(index: Int) {
        if (index >= 0) {
            val URL = options.value?.elementAt(index)?.url
            Timber.i(URL)
//            val request = DownloadManager.Request(Uri.parse(URL))
//                .setTitle(app.getString(R.string.app_name))
//                .setDescription(app.getString(R.string.app_description))
//                .setRequiresCharging(false)
//                .setAllowedOverMetered(true)
//                .setAllowedOverRoaming(true)
//
//            val downloadManager = app.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
//            downloadID = downloadManager.enqueue(request)
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DownloadListViewModel::class.java)) {
                return DownloadListViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }
}