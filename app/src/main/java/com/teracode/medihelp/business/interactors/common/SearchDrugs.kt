package com.teracode.medihelp.business.interactors.common

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchDrugs(
    private val drugCacheDataSource: DrugCacheDataSource
) {

    fun searchDrugs(
        query: String,
        filterAndOrder: String,
        page: Int,
        stateEvent: StateEvent
    ): Flow<DataState<DrugListViewState>?> = flow {

        var updatedPageNum = page
        if (page <= 0) {
            updatedPageNum = 1
        }

        val cacheResult = safeCacheCall(IO) {
            drugCacheDataSource.searchDrugs(
                query = query,
                filterAndOrder = filterAndOrder,
                page = updatedPageNum
            )
        }

        val response = object : CacheResponseHandler<DrugListViewState, List<Drug>>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<Drug>): DataState<DrugListViewState>? {
                var message: String? = SEARCH_DRUGS_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()
                if (resultObj.size == 0) {
                    message = SEARCH_DRUGS_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }


                return DataState.data(
                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType,
                        messageType = MessageType.Success()
                    ),
                    data = DrugListViewState(
                        drugList = ArrayList(resultObj)
                    ),
                    stateEvent = stateEvent
                )
            }

        }.getResult()


    }


    companion object {
        const val SEARCH_DRUGS_SUCCESS = "Successfully retrieved list of drugs"
        const val SEARCH_DRUGS_NO_MATCHING_RESULTS = "There are no drugs that match that query."
        const val SEARCH_DRUGS_FAILED = "Failed to retrieve the list of drugs."
    }
}