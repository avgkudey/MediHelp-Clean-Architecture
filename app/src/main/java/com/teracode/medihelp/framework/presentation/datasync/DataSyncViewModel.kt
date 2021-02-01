package com.teracode.medihelp.framework.presentation.datasync

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class DataSyncViewModel
@ViewModelInject
constructor(
    private val drugsNetworkSyncManager: DataNetworkSyncManager,
)

    : ViewModel() {

    private val _feedback: MutableLiveData<String> = MutableLiveData()

    val getFeedback: LiveData<String>
        get() = _feedback

    init {

        _feedback.value = "Syncing data..."
        syncCacheWithNetwork()
    }

    fun hasSyncBeenExecuted() = drugsNetworkSyncManager.hasSyncBeenExecuted

    private fun syncCacheWithNetwork() {


        drugsNetworkSyncManager.executeDataSync(viewModelScope)
    }


}