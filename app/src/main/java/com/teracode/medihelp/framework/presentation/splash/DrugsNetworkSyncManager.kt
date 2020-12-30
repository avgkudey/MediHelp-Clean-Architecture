package com.teracode.medihelp.framework.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teracode.medihelp.business.interactors.splash.SyncDrugs
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrugsNetworkSyncManager
@Inject
constructor(
    private val syncDrugs: SyncDrugs,
) {

    private val _hasSyncBeenExecuted: MutableLiveData<Boolean> = MutableLiveData(false)

    val hasSyncBeenExecuted: LiveData<Boolean>
        get() = _hasSyncBeenExecuted

    fun executeDataSync(coroutineScope: CoroutineScope) {
        if (_hasSyncBeenExecuted.value!!) {
            return
        }
//        TODO uncomment this

        val syncJob = coroutineScope.launch {


            launch {
                printLogD(
                    "DRUGS:SyncDrugs",
                    "syncing drugs."
                )
                syncDrugs.syncDrugs()
            }
        }
        syncJob.invokeOnCompletion {
            CoroutineScope(Main).launch {
                _hasSyncBeenExecuted.value = true
            }
        }
    }

}





















