package com.teracode.medihelp.business.interactors.druglist

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNumDrugs(
    private val drugCacheDataSource: DrugCacheDataSource
) {


    fun getNumDrugs(
        categoryId: String?,
        subcategoryId: String?,
        stateEvent: StateEvent

    ): Flow<DataState<DrugListViewState>?> = flow {


        val cacheResult = safeCacheCall(IO) {
            drugCacheDataSource.getNumDrugs(
                categoryId = categoryId,
                subcategoryId = subcategoryId,
            )
        }

        val response = object : CacheResponseHandler<DrugListViewState, Int>(
            response = cacheResult,
            stateEvent = stateEvent

        ) {
            override suspend fun handleSuccess(resultObj: Int): DataState<DrugListViewState> {

                val viewState = DrugListViewState(
                    numDrugsInCache = resultObj
                )

                return DataState.data(
                    response = Response(
                        message = GET_NUM_DRUGS_SUCCESS,
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Success()
                    ),
                    data = viewState,
                    stateEvent = stateEvent
                )

            }

        }.getResult()

        emit(response)


    }

    companion object {
        const val GET_NUM_DRUGS_SUCCESS =
            "Successfully retrieved the number of drugs from the cache."
        const val GET_NUM_DRUGS_FAILED = "Failed to get the number of drugs from the cache."
    }
}