package com.gonzoapps.downloadapp.ui.main

import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.gonzoapps.downloadapp.MockProvider
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.data.DownloadStatusRepository
import com.gonzoapps.downloadapp.domain.UrlOption
import com.gonzoapps.downloadapp.util.Event
import kotlinx.coroutines.launch
import java.net.MalformedURLException


class DownloadListViewModel(private val app: Application, private val downloadStatusRepository: DownloadStatusRepository) : AndroidViewModel(app) {

    private var downloadID: Long = 0
    private val _showToast = MutableLiveData<Event<String>>()
    val showToast :LiveData<Event<String>>
        get() = _showToast

    private val _options = MutableLiveData<List<UrlOption>>()
    val options : LiveData<List<UrlOption>>
        get() = _options

    init {
        _options.value = MockProvider.dataSync()
    }

    fun download(index: Int) {
        if (index >= 0) {
            val url = options.value?.elementAt(index)?.url
            url?.let { executeDownload(it) }
        } else {

        }
    }

    fun download(url: String) {
        if (URLUtil.isValidUrl(url)) {
            executeDownload(url)
        } else {
            _showToast.value = Event("Invalid URL. Please make sure to provide a valid URL")
        }
    }

    private fun executeDownload(URL: String) {
            val request = DownloadManager.Request(Uri.parse(URL))
                .setTitle(app.getString(R.string.app_name))
                .setDescription(app.getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS.toString(), "/download/repo.zip");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            val downloadManager = app.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
            downloadID = downloadManager.enqueue(request)

            viewModelScope.launch {
                downloadStatusRepository.storeActiveDownload(downloadID, URL)
            }

    }

    class Factory(val app: Application, val downloadStatusRepository: DownloadStatusRepository) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DownloadListViewModel::class.java)) {
                return DownloadListViewModel(app,downloadStatusRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }
}