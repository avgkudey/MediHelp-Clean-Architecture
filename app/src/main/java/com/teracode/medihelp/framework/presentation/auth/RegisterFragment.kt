package com.teracode.medihelp.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teracode.medihelp.databinding.FragmentRegisterBinding
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}