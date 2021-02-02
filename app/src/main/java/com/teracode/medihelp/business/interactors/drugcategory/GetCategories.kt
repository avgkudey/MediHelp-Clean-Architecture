package com.teracode.medihelp.business.interactors.drugcategory

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.CategoryCacheDataSource
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.framework.presentation.drugcategory.state.DrugCategoryViewState
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategories(
    private val categoryCacheDataSource: CategoryCacheDataSource
) {


    fun getCategories(stateEvent: StateEvent): Flow<DataState<DrugCategoryViewState>?> = flow {

        val cacheResult = safeCacheCall(IO) {
            categoryCacheDataSource.getAllCategories()
        }

        val response = object : CacheResponseHandler<DrugCategoryViewState, List<Category>>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<Category>): DataState<DrugCategoryViewState> {

                var message = GET_CATEGORIES_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()


                if (resultObj.isEmpty()) {
                    message = GET_CATEGORIES_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }

                printLogD(this::class.java.name, resultObj.toString())

                return DataState.data(
                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType,
                        messageType = MessageType.Success()
                    ), data = DrugCategoryViewState(
                        categoryList = ArrayList(resultObj)
                    ),
                    stateEvent = stateEvent
                )

            }

        }.getResult()

        emit(response)



    }

    companion object {
        const val GET_CATEGORIES_SUCCESS = "Successfully retrieved list of categories"
        const val GET_CATEGORIES_NO_MATCHING_RESULTS =
            "There are no categories that match that query."
        const val GET_CATEGORIES_FAILED = "Failed to retrieve the list of categories."
    }

}