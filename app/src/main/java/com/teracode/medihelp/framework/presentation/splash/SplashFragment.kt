package com.teracode.medihelp.framework.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.util.cLog
import com.teracode.medihelp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SplashFragment : BaseFragment(
    R.layout.fragment_splash
) {

    val viewModel: SplashViewModel by viewModels()
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
                navHomeFragment()
            }
        })
    }


    private fun setFeedback(feedback: String) {
        splash_feedback.text = feedback


    }

    private fun navHomeFragment() {

        setFeedback("Data syncing complete...")

        findNavController().navigate(R.id.action_splashFragment_to_drugCategoryFragment)
        printLogD(
            "DRUGS:SyncDrugs",
            "Sync Complete"
        )

        printLogD("printLogD", "Sync Complete")

    }


}