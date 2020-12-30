package com.teracode.medihelp.framework.presentation.druglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject


@ExperimentalCoroutinesApi
@FlowPreview
class DrugListFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : BaseFragment(R.layout.fragment_drug_list) {

    val viewModel: DrugListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}