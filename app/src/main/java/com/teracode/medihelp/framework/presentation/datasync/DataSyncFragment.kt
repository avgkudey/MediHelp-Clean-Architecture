package com.teracode.medihelp.framework.presentation.datasync

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.framework.presentation.MainActivity
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_data_sync.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class DataSyncFragment : BaseFragment(R.layout.fragment_data_sync) {
    private val viewModel: DataSyncViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.getFeedback.observe(viewLifecycleOwner, { feedback ->
            feedback?.let {
                setFeedback(it)
            }

        })

        viewModel.hasSyncBeenExecuted().observe(viewLifecycleOwner, { hasSyncBeenExecuted ->

            if (hasSyncBeenExecuted) {
                setFeedback("Data syncing complete...")
                navHomeFragment()
            }
        })


    }

    private fun navHomeFragment() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun setFeedback(feedback: String) {
        data_sync_feedback.text = feedback
    }
}