package com.teracode.medihelp.business.interactors.drugdetail

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.framework.datasource.database.DRUG_PAGINATION_PAGE_SIZE
import com.teracode.medihelp.framework.presentation.drugdetail.state.DrugDetailViewState
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListViewState
import com.teracode.medihelp.util.OrderEnum
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDrug(
    private val drugCacheDataSource: DrugCacheDataSource
) {

    fun getDrug(
        drugId: String,
        stateEvent: StateEvent
    ): Flow<DataState<DrugDetailViewState>?> = flow {


        val cacheResult = safeCacheCall(IO) {
            drugCacheDataSource.searchDrugById(drugId)
        }


        val response = object : CacheResponseHandler<DrugDetailViewState, Drug>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: Drug): DataState<DrugDetailViewState>? {
                var message: String? = SEARCH_DRUGS_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()
                if (resultObj == null) {
                    message = SEARCH_DRUGS_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }


                return DataState.data(
                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType,
                        messageType = MessageType.Success()
                    ),
                    data = DrugDetailViewState(
                        selectedDrug = resultObj
                    ),
                    stateEvent = stateEvent
                )
            }

        }.getResult()

        emit(response)

    }


    companion object {
        const val SEARCH_DRUGS_SUCCESS = "Successfully retrieved list of drugs"
        const val SEARCH_DRUGS_NO_MATCHING_RESULTS = "There are no drugs that match that query."
        const val SEARCH_DRUGS_FAILED = "Failed to retrieve the list of drugs."
    }
}