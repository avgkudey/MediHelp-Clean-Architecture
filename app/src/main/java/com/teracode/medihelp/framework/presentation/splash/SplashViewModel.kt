package com.teracode.medihelp.framework.presentation.splash

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import javax.inject.Inject
import javax.inject.Singleton

class SplashViewModel
@ViewModelInject
constructor(
    private val drugsNetworkSyncManager: DrugsNetworkSyncManager,
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