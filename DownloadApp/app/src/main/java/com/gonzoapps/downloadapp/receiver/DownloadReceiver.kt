package com.gonzoapps.downloadapp.receiver

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.core.content.ContextCompat
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.data.DownloadStatusRepository
import com.gonzoapps.downloadapp.domain.DownloadDetails
import com.gonzoapps.downloadapp.ui.loadingbutton.ButtonState
import com.gonzoapps.downloadapp.util.sendNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class DownloadReceiver: BroadcastReceiver() {

    private lateinit var dataStore: DownloadStatusRepository

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            dataStore = DownloadStatusRepository.getInstance(context)
        }

        val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

        val action = intent?.action

        val downloadDetails = DownloadDetails()

        id?.let {
            GlobalScope.launch(Dispatchers.IO){
                dataStore.downloadStatusFlow.collect {
                    if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE) && id == it.id) {
                        val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        val query = DownloadManager.Query()
                        query.setFilterById(id);
                        val cursor: Cursor = dm.query(query)
                        downloadDetails.id = it.id
                        downloadDetails.url = it.url
                        if (cursor.moveToFirst()) {
                            if (cursor.count > 0) {
                                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                    downloadDetails.success = true
                                    sendNotification(context, downloadDetails)
                                } else {
                                    downloadDetails.success = false
                                    sendNotification(context, downloadDetails)
                                }
                            }
                        }
                    }
                }

            }

        }

    }

    protected abstract fun broadcastDownloadState(state: ButtonState)

    private fun sendNotification(context: Context, downloadDetails: DownloadDetails) {
        broadcastDownloadState(ButtonState.Completed)

        val notificationMessage: String = if (downloadDetails.success){
            context.getString(R.string.download_notification_success_message)
        } else {
            context.getString(R.string.download_notification_failed_message)
        }

        val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
                notificationMessage,
                context,
                downloadDetails
        )
    }
}