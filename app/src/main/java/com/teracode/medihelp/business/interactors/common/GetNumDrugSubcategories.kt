package com.teracode.medihelp.business.interactors.common

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.framework.presentation.subcategorylist.state.SubcategoryListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNumDrugSubcategories(
    private val subcategoryCacheDataSource: SubcategoryCacheDataSource
) {


    fun getNumSubcategories(
        categoryId: String?,
        stateEvent: StateEvent
    ): Flow<DataState<SubcategoryListViewState>?> =
        flow {


            val cacheResult = safeCacheCall(IO) {
                subcategoryCacheDataSource.getNumSubcategories(categoryId)
            }

            val response = object : CacheResponseHandler<SubcategoryListViewState, Int>(
                response = cacheResult,
                stateEvent = stateEvent

            ) {
                override suspend fun handleSuccess(resultObj: Int): DataState<SubcategoryListViewState>? {

                    val viewState = SubcategoryListViewState(
                        numSubcategoriesInCache = resultObj
                    )

                    return DataState.data(
                        response = Response(
                            message = GET_NUM_SUBCATEGORIES_SUCCESS,
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
        const val GET_NUM_SUBCATEGORIES_SUCCESS =
            "Successfully retrieved the number of subcategories from the cache."
        const val GET_NUM_SUBCATEGORIES_FAILED =
            "Failed to get the number of subcategories from the cache."
    }
}