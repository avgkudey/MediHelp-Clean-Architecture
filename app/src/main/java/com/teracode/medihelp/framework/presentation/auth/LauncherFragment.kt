package com.teracode.medihelp.framework.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_launcher.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class LauncherFragment : BaseFragment(R.layout.fragment_launcher) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        launcher_login.setOnClickListener {
            findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
        }
        launcher_register.setOnClickListener {
            findNavController().navigate(R.id.action_launcherFragment_to_registerFragment)
        }
        launcher_forgot.setOnClickListener {
            findNavController().navigate(R.id.action_launcherFragment_to_forgotPasswordFragment)
        }


    }
}