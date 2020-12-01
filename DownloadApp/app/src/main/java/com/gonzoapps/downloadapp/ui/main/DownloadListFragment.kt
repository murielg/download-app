package com.gonzoapps.downloadapp.ui.main

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.databinding.FragmentDownloadListBinding
import com.gonzoapps.downloadapp.util.setCircleColor


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
            if (rg.checkedRadioButtonId != -1) {
                listViewModel.download(rg.checkedRadioButtonId - 1)
            } else if (!binding.edittextUrl.text.toString().equals("")) {
                listViewModel.download(binding.edittextUrl.text.toString())
            } else {
                Toast.makeText(activity, "Select option or input your own custom URL below", Toast.LENGTH_SHORT).show()
            }
            hideKeyboard()
        }

        binding.edittextUrl.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val rg: RadioGroup = container?.findViewById(R.id.radioGroup) as RadioGroup
                rg.clearCheck()
                view.requestFocus()
            }
        }

        return binding.root

    }

    private fun generateRadioGroup() {
        val radioGroup = RadioGroup(this.activity)
        radioGroup.orientation = RadioGroup.VERTICAL
        radioGroup.id = R.id.radioGroup
        radioGroup.layoutParams = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.MATCH_PARENT,
            RadioGroup.LayoutParams.WRAP_CONTENT,
        )
        val options = listViewModel.options.value
        options?.let {
            for ((index, option) in options.withIndex()) {
                val radioButton = RadioButton(this.activity)
                radioButton.text = option.name
                radioButton.setPadding(16,0,0,0)
                radioButton.textSize = 16F
                radioButton.setTextColor(resources.getColor(R.color.colorPrimaryDark, activity?.theme))
                radioButton.setCircleColor(resources.getColor(R.color.colorPrimary, activity?.theme))
                radioGroup.addView(radioButton)
            }
        }
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (radioGroup.checkedRadioButtonId != -1) {
                hideKeyboard()
            }
        }
        binding.linearLayoutRadiogroupContainer.addView(radioGroup)
    }

    private fun hideKeyboard() {
        binding.edittextUrl.clearFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}