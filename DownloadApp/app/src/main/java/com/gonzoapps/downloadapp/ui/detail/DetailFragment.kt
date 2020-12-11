package com.gonzoapps.downloadapp.ui.detail

import android.app.NotificationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.domain.DownloadDetails
import timber.log.Timber

const val ARG_PARAM_DOWNLOAD = "download"
const val ARG_PARAM_NOTIFICATION_ID = "notification_id"

class DetailFragment : Fragment() {

    private var downloadDetails: DownloadDetails? = null
    private var notificationId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
        )
        arguments?.let {
            downloadDetails = it.getParcelable(ARG_PARAM_DOWNLOAD)
            notificationId = it.getInt(ARG_PARAM_NOTIFICATION_ID)
            Timber.i(downloadDetails?.url.toString())

            notificationId?.let { notificationId ->
                notificationManager.cancel(notificationId)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_detail, container, false)
    }

}