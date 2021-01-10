package com.teracode.medihelp.business.interactors.splash

import android.content.SharedPreferences
import android.util.Log
import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.network.ApiResponseHandler
import com.teracode.medihelp.business.data.network.abstraction.DrugNetworkDataSource
import com.teracode.medihelp.business.data.util.safeApiCall
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager.Companion.DRUG_LIST_SYNCED
import com.teracode.medihelp.util.cLog
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncDrugs(
    private val drugCacheDataSource: DrugCacheDataSource,
    private val drugNetworkDataSource: DrugNetworkDataSource,
    private val editor: SharedPreferences.Editor
) {

    private var exception: Exception? = null
    suspend fun syncDrugs() {
        val cachedDrugsList = getCachedDrugList()
        syncNetworkDrugsWithCachedDrugs(ArrayList(cachedDrugsList))
    }

    private suspend fun syncNetworkDrugsWithCachedDrugs(cachedDrugs: ArrayList<Drug>) =
        withContext(IO) {
            val networkResult = safeApiCall(IO) {
                drugNetworkDataSource.getAllDrugs()
            }


            val response = object : ApiResponseHandler<List<Drug>, List<Drug>>(
                response = networkResult,
                stateEvent = null
            ) {
                override suspend fun handleSuccess(resultObj: List<Drug>): DataState<List<Drug>>? {

                    return DataState.data(
                        response = null,
                        data = resultObj,
                        stateEvent = null
                    )
                }

            }.getResult()


            val networkDrugList = response?.data ?: ArrayList()

            for (drug in networkDrugList) {
                drugCacheDataSource.searchDrugById(drug.id)?.let { cachedDrug ->

                    cachedDrugs.remove(drug)
                    checkRequiresUpdate(cachedDrug, drug)

                } ?: drugCacheDataSource.insertDrug(drug)
            }

            for (cachedDrug in cachedDrugs) {
                drugNetworkDataSource.searchDrug(cachedDrug) ?: drugCacheDataSource.deleteDrug(
                    cachedDrug.id
                )
            }
            setSynced(DRUG_LIST_SYNCED, true)
        }

    private suspend fun checkRequiresUpdate(cachedDrug: Drug, networkDrug: Drug) {

        if (networkDrug != cachedDrug) {


            safeCacheCall(IO) {
                drugCacheDataSource.updateDrug(
                    primaryKey = networkDrug.id,
                    title = networkDrug.title,
                    trade_name = networkDrug.trade_name,
                    pharmacological_name = networkDrug.pharmacological_name,
                    action = networkDrug.action,
                    antidote = networkDrug.antidote,
                    cautions = networkDrug.cautions,
                    contraindication = networkDrug.contraindication,
                    dosages = networkDrug.dosages,
                    indications = networkDrug.indications,
                    maximum_dose = networkDrug.maximum_dose,
                    nursing_implications = networkDrug.nursing_implications,
                    notes = networkDrug.notes,
                    preparation = networkDrug.preparation,
                    side_effects = networkDrug.side_effects,
                    video = networkDrug.video,
                    category_id = networkDrug.category_id,
                    subcategory_id = networkDrug.subcategory_id
                )
            }


        }
    }

    private suspend fun getCachedDrugList(): List<Drug> {

        val cachedResult = safeCacheCall(IO) {
            drugCacheDataSource.getAllDrugs()
        }

        val response = object : CacheResponseHandler<List<Drug>, List<Drug>>(
            response = cachedResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: List<Drug>): DataState<List<Drug>>? {

                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }

        }.getResult()
        return response?.data ?: ArrayList()
    }


    private fun setSynced(key: String, value: Boolean) {

        if (exception == null) {
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

}