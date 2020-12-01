package com.gonzoapps.downloadapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.databinding.FragmentDownloadListBinding


class DownloadListFragment : Fragment() {

    private val listViewModel: DownloadListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, DownloadListViewModel.Factory(activity.application))
            .get(DownloadListViewModel::class.java)
    }
    private lateinit var binding: FragmentDownloadListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDownloadListBinding.inflate(inflater)

        binding.viewModel = listViewModel

        binding.lifecycleOwner = this

        generateRadioGroup()

        binding.buttonDownload.setOnClickListener {
            val rg: RadioGroup = container?.findViewById(R.id.radioGroup) as RadioGroup
            listViewModel.download(rg.checkedRadioButtonId - 1)
        }

        return binding.root

    }

    private fun generateRadioGroup() {
        val radioGroup = RadioGroup(this.activity)
        radioGroup.orientation = RadioGroup.VERTICAL
        radioGroup.id = R.id.radioGroup
        val options = listViewModel.options.value
        options?.let {
            for ((index, option) in options.withIndex()) {
                val radioButton = RadioButton(this.activity)
                radioButton.text = option.name
                radioGroup.addView(radioButton)
            }
        }
        binding.linearLayoutRadiogroupContainer.addView(radioGroup)
    }
}