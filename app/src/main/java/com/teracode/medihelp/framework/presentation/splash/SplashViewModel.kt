package com.teracode.medihelp.framework.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashViewModel
@Inject
constructor(
    private val drugsNetworkSyncManager: DrugsNetworkSyncManager
) : ViewModel() {

    init {
        syncCacheWithNetwork()
    }

    fun hasSyncBeenExecuted() = drugsNetworkSyncManager.hasSyncBeenExecuted

    private fun syncCacheWithNetwork(){
        drugsNetworkSyncManager.executeDataSync(viewModelScope)
    }
}