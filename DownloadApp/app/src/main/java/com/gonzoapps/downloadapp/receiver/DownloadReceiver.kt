package com.gonzoapps.downloadapp.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gonzoapps.downloadapp.data.DownloadStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

object DownloadReceiver: BroadcastReceiver() {

    private lateinit var dataStore: DownloadStatusRepository

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            dataStore = DownloadStatusRepository.getInstance(context)
        }

        val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

        id.let {
            Timber.i("onReceive id: $id")
            Timber.i("onReceive action: ${intent?.action}")
            GlobalScope.launch(Dispatchers.IO){
                dataStore.downloadStatusFlow.collect {
                    Timber.i( "Download Id is: ${it.id}")
                }
            }

        }

    }
}