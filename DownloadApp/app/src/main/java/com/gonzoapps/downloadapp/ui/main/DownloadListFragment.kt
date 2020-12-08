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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gonzoapps.downloadapp.R
import com.gonzoapps.downloadapp.data.DownloadStatusRepository
import com.gonzoapps.downloadapp.databinding.FragmentDownloadListBinding
import com.gonzoapps.downloadapp.ui.loadingbutton.ButtonState
import com.gonzoapps.downloadapp.util.setCircleColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DownloadListFragment : Fragment() {

    private val viewModel: DownloadListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, DownloadListViewModel.Factory(activity.application, DownloadStatusRepository.getInstance(activity)))
                .get(DownloadListViewModel::class.java)
    }
    private lateinit var binding: FragmentDownloadListBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDownloadListBinding.inflate(inflater)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        generateRadioGroup()

        binding.buttonDownload.setOnClickListener {
            val rg: RadioGroup = container?.findViewById(R.id.radioGroup) as RadioGroup
            updateDownloadButtonWithStatus(ButtonState.Loading)
            when {
                rg.checkedRadioButtonId != -1 -> {
                    viewModel.download(rg.checkedRadioButtonId)
                }
                binding.edittextUrl.text.toString() != "" -> {
                    viewModel.download(binding.edittextUrl.text.toString())
                }
                else -> {
                    updateDownloadButtonWithStatus(ButtonState.Completed)
                    showToast(getString(R.string.error_message_input))
                }
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

        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        })

        return binding.root

    }
    private fun showToast(message: String) {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.show()
    }

    fun updateDownloadButtonWithStatus(state: ButtonState) {
        GlobalScope.launch(Dispatchers.Main) {
            binding.buttonDownload.setState(state)
        }
    }

    private fun generateRadioGroup() {
        val radioGroup = RadioGroup(this.activity)
        radioGroup.orientation = RadioGroup.VERTICAL
        radioGroup.id = R.id.radioGroup
        radioGroup.layoutParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT,
        )
        val options = viewModel.options.value
        options?.let {
            for ((index, option) in options.withIndex()) {
                val radioButton = RadioButton(this.activity)
                radioButton.text = option.name
                radioButton.setPadding(16,0,0,0)
                radioButton.textSize = 16F
                radioButton.id = index
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
        binding.linearLayoutRadiogroupContainer.addView(radioGroup,0)
    }

    private fun hideKeyboard() {
        binding.edittextUrl.clearFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}