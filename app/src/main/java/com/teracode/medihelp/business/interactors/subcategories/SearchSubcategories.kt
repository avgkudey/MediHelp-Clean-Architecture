package com.teracode.medihelp.business.interactors.subcategories

import android.util.Log
import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.framework.datasource.database.SUBCATEGORY_PAGINATION_PAGE_SIZE
import com.teracode.medihelp.framework.presentation.subcategorylist.state.SubcategoryListViewState
import com.teracode.medihelp.util.OrderEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchSubcategories(
    private val dataSource: SubcategoryCacheDataSource
) {

    fun searchSubcategories(
        query: String,
        categoryId: String? = null,
        filterAndOrder: OrderEnum,
        page: Int,
        pageSize: Int = SUBCATEGORY_PAGINATION_PAGE_SIZE,
        stateEvent: StateEvent
    ): Flow<DataState<SubcategoryListViewState>?> = flow {


        Log.d("DrugListViewModel", "searchDrugs: ")
        var updatedPageNum = page
        if (page <= 0) {
            updatedPageNum = 1
        }

        val cacheResult = safeCacheCall(Dispatchers.IO) {
            dataSource.searchSubcategories(
                query = query,
                categoryId = categoryId,
                filterAndOrder = filterAndOrder,
                page = updatedPageNum,
                pageSize = pageSize,
                )
        }


        val response = object : CacheResponseHandler<SubcategoryListViewState, List<Subcategory>>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<Subcategory>): DataState<SubcategoryListViewState>? {
                var message: String? = SEARCH_SUBCATEGORIES_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()
                if (resultObj.isEmpty()) {
                    message = SEARCH_SUBCATEGORIES_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }


                return DataState.data(
                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType,
                        messageType = MessageType.Success()
                    ),
                    data = SubcategoryListViewState(
                        subcategoryList = ArrayList(resultObj)
                    ),
                    stateEvent = stateEvent
                )
            }

        }.getResult()

        emit(response)

    }

    companion object {
        const val SEARCH_SUBCATEGORIES_SUCCESS = "Successfully retrieved list of subcategories"
        const val SEARCH_SUBCATEGORIES_NO_MATCHING_RESULTS =
            "There are no subcategories that match that query."
        const val SEARCH_SUBCATEGORIES_FAILED = "Failed to retrieve the list of subcategories."
    }
}