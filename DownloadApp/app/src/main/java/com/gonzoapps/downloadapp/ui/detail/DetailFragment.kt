package com.gonzoapps.downloadapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.domain.DownloadDetails
import timber.log.Timber

const val ARG_PARAM_DOWNLOAD = "download"

class DetailFragment : Fragment() {

    private var downloadDetails: DownloadDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            downloadDetails = it.getParcelable(ARG_PARAM_DOWNLOAD)
            Timber.i(downloadDetails?.url.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_detail, container, false)
    }

}