package com.gonzoapps.downloadapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel: DownloadViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            DownloadViewModel.Factory(activity.application)
        ).get(DownloadViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.downloadViewModel = viewModel

        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            val radio: RadioButton = group.findViewById(checkedId)
            Timber.i("selectedtext--> ${radio.text.toString()}")

        })

        return binding.root
    }


}