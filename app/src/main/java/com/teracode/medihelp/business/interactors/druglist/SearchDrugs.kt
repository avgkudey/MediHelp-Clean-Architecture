package com.teracode.medihelp.business.interactors.druglist

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.framework.datasource.database.DRUG_PAGINATION_PAGE_SIZE
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListViewState
import com.teracode.medihelp.util.OrderEnum
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchDrugs(
    private val drugCacheDataSource: DrugCacheDataSource
) {

    fun searchDrugs(
        query: String,
        categoryId: String? = null,
        subcategoryId: String? = null,
        filterAndOrder: OrderEnum,
        page: Int,

        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE,


        stateEvent: StateEvent
    ): Flow<DataState<DrugListViewState>?> = flow {

        var updatedPageNum = page
        if (page <= 0) {
            updatedPageNum = 1
        }

        val cacheResult = safeCacheCall(IO) {
            drugCacheDataSource.searchDrugs(
                query = query,
                categoryId = categoryId,
                subcategoryId = subcategoryId,
                filterAndOrder = filterAndOrder,
                page = updatedPageNum,
                pageSize = pageSize
            )
        }


        val response = object : CacheResponseHandler<DrugListViewState, List<Drug>>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<Drug>): DataState<DrugListViewState> {
                var message: String? = SEARCH_DRUGS_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()
                if (resultObj.isEmpty()) {
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

        emit(response)

    }


    companion object {
        const val SEARCH_DRUGS_SUCCESS = "Successfully retrieved list of drugs"
        const val SEARCH_DRUGS_NO_MATCHING_RESULTS = "There are no drugs that match that query."
        const val SEARCH_DRUGS_FAILED = "Failed to retrieve the list of drugs."
    }
}