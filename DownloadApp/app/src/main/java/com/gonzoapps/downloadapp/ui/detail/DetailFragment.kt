package com.gonzoapps.downloadapp.ui.detail

import android.app.NotificationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.databinding.FragmentDownloadDetailBinding
import com.gonzoapps.downloadapp.databinding.FragmentDownloadListBinding
import com.gonzoapps.downloadapp.domain.DownloadDetails
import timber.log.Timber

const val ARG_PARAM_DOWNLOAD = "download"
const val ARG_PARAM_NOTIFICATION_ID = "notification_id"

class DetailFragment : Fragment() {

    private var downloadDetails: DownloadDetails? = null
    private var notificationId: Int? = null
    private lateinit var binding: FragmentDownloadDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
        )

        arguments?.let {
            downloadDetails = it.getParcelable(ARG_PARAM_DOWNLOAD)
            notificationId = it.getInt(ARG_PARAM_NOTIFICATION_ID)
            notificationId?.let { notificationId ->
                notificationManager.cancel(notificationId)
            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadDetailBinding.inflate(inflater)

        binding.textviewFilename.text = downloadDetails?.url
        downloadDetails?.let {
            binding.textviewFilename.text = it.url
            if (it.success) {
                binding.textviewStatus.setTextColor(resources.getColor(R.color.success))
                binding.textviewStatus.text = resources.getString(R.string.status_label_success)
            } else {
                binding.textviewStatus.setTextColor(resources.getColor(R.color.failure))
                binding.textviewStatus.text = resources.getString(R.string.status_label_failure)
            }
        }

        binding.buttonDone.setOnClickListener {
            this.findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToMainFragment())
        }

        return binding.root
    }

}