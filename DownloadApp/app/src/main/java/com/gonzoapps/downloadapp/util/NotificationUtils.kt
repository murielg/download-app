package com.gonzoapps.downloadapp.util

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.gonzoapps.downloadapp.MainActivity
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.domain.DownloadDetails
import com.gonzoapps.downloadapp.ui.detail.ARG_PARAM_DOWNLOAD

private val DOWNLOAD_NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context, downloadDetails: DownloadDetails?) {

    // TODO: Add progress to notification
    // val PROGRESS_MAX = 100
    // val PROGRESS_CURRENT = 0

    val bundle = Bundle()
    bundle.putParcelable(ARG_PARAM_DOWNLOAD, downloadDetails)

    val testPendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.main_nav)
            .setDestination(R.id.detailFragment)
            .setArguments(bundle)
            .createPendingIntent()

    val notification = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.downloads_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_download)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(testPendingIntent)
        .addAction(
                R.drawable.ic_download, "Check Download Status", testPendingIntent
        )

            .setAutoCancel(true)

    notify(DOWNLOAD_NOTIFICATION_ID, notification.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}