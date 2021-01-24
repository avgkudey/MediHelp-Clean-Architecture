package com.teracode.medihelp.framework.presentation.splash

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.teracode.medihelp.business.interactors.splash.SyncCategories
import com.teracode.medihelp.business.interactors.splash.SyncCounts
import com.teracode.medihelp.business.interactors.splash.SyncDrugs
import com.teracode.medihelp.business.interactors.splash.SyncSubcategories
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "DrugsNetworkSyncManager"

@Singleton
class DrugsNetworkSyncManager
@Inject
constructor(
    private val syncDrugs: SyncDrugs,
    private val syncCategory: SyncCategories,
    private val syncSubcategory: SyncSubcategories,
    private val syncCounts: SyncCounts,
    private val sharedPreferences: SharedPreferences,
    private val remoteConfig: FirebaseRemoteConfig,
    private val editor: SharedPreferences.Editor,
) {

    private val _hasSyncBeenExecuted: MutableLiveData<Boolean> = MutableLiveData(false)

    val hasSyncBeenExecuted: LiveData<Boolean>
        get() = _hasSyncBeenExecuted

    fun executeDataSync(coroutineScope: CoroutineScope) {
        if (_hasSyncBeenExecuted.value!!) {
            return
        }

        var remoteVersion: Int = 0


        val syncJob = coroutineScope.launch {


            launch {


                remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        remoteVersion = remoteConfig.getValue(DATABASE_VERSION).asLong().toInt()
                    }
                }.await()
                awaitAll(
                    async {
                        if (checkAlreadyNotSynced(DRUG_LIST_SYNCED, false) || isUpdateRequired(
                                remoteVersion
                            )
                        ) {
                            printLogD("DrugsNetworkSyncManager","syncDrugs.syncDrugs()")
                            syncDrugs.syncDrugs()

                        }
                    },
                    async {
                        if (checkAlreadyNotSynced(CATEGORY_LIST_SYNCED, false) || isUpdateRequired(
                                remoteVersion
                            )
                        ) {
                            printLogD("DrugsNetworkSyncManager","syncDrugs.syncCategories()")
                            syncCategory.syncCategories()
                        }
                    },
                    async {
                        if (checkAlreadyNotSynced(
                                SUBCATEGORY_LIST_SYNCED,
                                false
                            ) || isUpdateRequired(remoteVersion)
                        ) {
                            printLogD("DrugsNetworkSyncManager","syncDrugs.syncSubcategories()")

                            syncSubcategory.syncSubcategories()
                        }
                    }
                )




                if (
                    checkAlreadyNotSynced(DRUG_LIST_SYNCED, false) ||
                    checkAlreadyNotSynced(CATEGORY_LIST_SYNCED, false) ||
                    checkAlreadyNotSynced(SUBCATEGORY_LIST_SYNCED, false) ||
                    isUpdateRequired(remoteVersion)
                ) {
                    printLogD("DrugsNetworkSyncManager","syncCounts.syncCategories()")
                    syncCounts.syncCategories()
                }


                setDatabaseVersion(remoteVersion)
            }
        }






        syncJob.invokeOnCompletion {
            CoroutineScope(Main).launch {
                _hasSyncBeenExecuted.value = true
            }
        }
    }

    private fun checkAlreadyNotSynced(key: String, defVal: Boolean = false): Boolean {
//        return true
        return !sharedPreferences.getBoolean(key, defVal)
    }

    private fun isUpdateRequired(remote_version: Int): Boolean {
        val local_version = getExistingDatabaseVersion()

        printLogD("printLogD", "local db version $local_version")
        printLogD("printLogD", "remote db version $remote_version")

        return getExistingDatabaseVersion() != remote_version
    }

    private fun getExistingDatabaseVersion(): Int {


        return sharedPreferences.getInt(DATABASE_VERSION, 0)
    }

    private fun setDatabaseVersion(version: Int) {
        editor.putInt(DATABASE_VERSION, version)
        editor.apply()
    }

    companion object {
        const val DRUG_LIST_SYNCED = "drugListSynced"
        const val CATEGORY_LIST_SYNCED = "categoryListSynced"
        const val SUBCATEGORY_LIST_SYNCED = "subcategoryListSynced"
        const val DATABASE_VERSION = "database_version"
        const val QUIZ_LIST_SYNCED = "quizListSynced"
        const val QUESTION_LIST_SYNCED = "questionListSynced"
    }
}





















