package com.teracode.medihelp.framework.presentation.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class SplashFragment
@Inject
constructor(
  private val viewModelFactory: ViewModelProvider.Factory
) : BaseFragment(
    R.layout.fragment_splash
) {

    val viewModel: SplashViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private fun subscribeObservers() {
        viewModel.hasSyncBeenExecuted().observe(viewLifecycleOwner, { hasSyncBeenExecuted ->

            if (hasSyncBeenExecuted) {
                navHomeFragment()
            }
        })


    }

    private fun navHomeFragment() {

        printLogD(
            "DRUGS:SyncDrugs",
            "Sync Complete"
        )

    }


}