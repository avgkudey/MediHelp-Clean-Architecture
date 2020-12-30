package com.teracode.medihelp.framework.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teracode.medihelp.business.domain.model.DrugFactory
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.framework.presentation.druglist.DrugListViewModel
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class DrugViewModelFactory
constructor(
    private val drugListInteractors: DrugListInteractors,
    private val drugFactory: DrugFactory,
    private val drugsNetworkSyncManager: DrugsNetworkSyncManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            DrugListViewModel::class.java -> {
                DrugListViewModel(
                    drugListInteractors = drugListInteractors,
                    drugFactory = drugFactory
                ) as T
            }

            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }
}