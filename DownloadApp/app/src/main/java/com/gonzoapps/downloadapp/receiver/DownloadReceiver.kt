package com.gonzoapps.downloadapp.receiver

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.gonzoapps.downloadapp.data.DownloadStatusRepository
import com.gonzoapps.downloadapp.util.sendNotification
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

        val action = intent?.action

        id?.let {
            GlobalScope.launch(Dispatchers.IO){
                dataStore.downloadStatusFlow.collect {
                    if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE) && id == it.id) {
                        //TODO: Show Notification of successful download
                        Timber.i("Successful download of id $it.id")
                        context?.let { context ->  sendNotification(context)}
                    }
                }
            }

        }

    }

    private fun sendNotification(context: Context) {
        Timber.i("Sending Notification")
        val notificationManager = ContextCompat.getSystemService(
            context,
                NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
                "Succesful Download",
                context
        )

    }

}