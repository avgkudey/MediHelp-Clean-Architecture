package com.teracode.medihelp.toolsmodule.framework.presentation.toolslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teracode.medihelp.R
import com.teracode.medihelp.databinding.FragmentDataSyncBinding
import com.teracode.medihelp.databinding.FragmentToolListBinding
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@FlowPreview
class ToolListFragment : BaseFragment<FragmentToolListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentToolListBinding =
        FragmentToolListBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



}