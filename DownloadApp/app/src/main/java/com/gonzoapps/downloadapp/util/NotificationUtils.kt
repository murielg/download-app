package com.gonzoapps.downloadapp.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.gonzoapps.downloadapp.MainActivity
import com.gonzoapps.downloadapp.R

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val bundle = Bundle()
    bundle.putString("EXTRA_INFO", "info")

    val testPendingIntent = NavDeepLinkBuilder(applicationContext)
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
        .setAutoCancel(true)
//        .setLargeIcon(meteoriteImage)

    notify(NOTIFICATION_ID, notification.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}