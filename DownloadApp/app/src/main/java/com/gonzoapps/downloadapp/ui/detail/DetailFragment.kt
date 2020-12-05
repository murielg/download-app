package com.gonzoapps.downloadapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.domain.DownloadDetails
import timber.log.Timber

private const val ARG_PARAM1 = "download"

class DetailFragment : Fragment() {

    private var param1: DownloadDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
            Timber.i(param1?.url.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_detail, container, false)
    }

}