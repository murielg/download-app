package com.gonzoapps.downloadapp.receiver

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.core.content.ContextCompat
import com.gonzoapps.downloadapp.data.DownloadStatusRepository
import com.gonzoapps.downloadapp.ui.loadingbutton.ButtonState
import com.gonzoapps.downloadapp.util.sendNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class DownloadReceiver: BroadcastReceiver() {

    private lateinit var dataStore: DownloadStatusRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        broadcastDownloadState(ButtonState.Loading)

        context?.let {
            dataStore = DownloadStatusRepository.getInstance(context)
        }

        val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

        val action = intent?.action

        id?.let {
            GlobalScope.launch(Dispatchers.IO){
                dataStore.downloadStatusFlow.collect {
                    if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE) && id == it.id) {
                        val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        val query = DownloadManager.Query()
                        query.setFilterById(id);
                        val cursor: Cursor = dm.query(query)
                        if (cursor.moveToFirst()) {
                            if (cursor.count > 0) {
                                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                    sendNotification(context)
                                    Timber.i("Successful download of id $it.id")
                                    broadcastDownloadState(ButtonState.Completed)
                                } else {
                                    Timber.i("Download failed $it.id")
                                    broadcastDownloadState(ButtonState.Completed)
                                }
                            }
                        }

                    }

                }
            }

        }

    }

    protected abstract fun broadcastDownloadState(state: ButtonState)

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