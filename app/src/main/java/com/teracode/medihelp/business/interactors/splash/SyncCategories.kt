package com.teracode.medihelp.business.interactors.splash

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.CategoryCacheDataSource
import com.teracode.medihelp.business.data.network.ApiResponseHandler
import com.teracode.medihelp.business.data.network.abstraction.CategoryNetworkDataSource
import com.teracode.medihelp.business.data.util.safeApiCall
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.DataState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncCategories(
    private val cacheDataSource: CategoryCacheDataSource,
    private val networkDataSource: CategoryNetworkDataSource
) {

    suspend fun syncCategories() {
        val cachedCategoriesList = getCachedCategoriesList()


        syncNetworkCategoriesWithCachedCategories(ArrayList(cachedCategoriesList))
    }

    private suspend fun syncNetworkCategoriesWithCachedCategories(cachedCategories: ArrayList<Category>) =
        withContext(IO) {
            val networkResult = safeApiCall(IO) {
                networkDataSource.getAllCategories()
            }

            val response = object : ApiResponseHandler<List<Category>, List<Category>>(
                response = networkResult,
                stateEvent = null
            ) {
                override suspend fun handleSuccess(resultObj: List<Category>): DataState<List<Category>>? {

                    return DataState.data(
                        response = null,
                        data = resultObj,
                        stateEvent = null
                    )
                }

            }.getResult()

            val networkCategoryList = response?.data ?: ArrayList()

            for (category in networkCategoryList) {
                cacheDataSource.searchCategoryById(category.id)?.let { cachedCategory ->

                    cachedCategories.remove(category)
                    checkRequiresUpdate(cachedCategory, category)

                } ?: cacheDataSource.insertCategory(category)


            }


        }

    private suspend fun checkRequiresUpdate(cachedCategory: Category, networkCategory: Category) {

        if (networkCategory != cachedCategory) {


            safeCacheCall(IO) {
                cacheDataSource.updateCategory(
                    primaryKey = networkCategory.id,
                    title = networkCategory.title,
                    image = networkCategory.image,
                    url = networkCategory.url,
                    description = networkCategory.description,
                )
            }


        }
    }

    private suspend fun getCachedCategoriesList(): List<Category> {

        val cachedResult = safeCacheCall(IO) {
            cacheDataSource.getAllCategories()
        }

        val response = object : CacheResponseHandler<List<Category>, List<Category>>(
            response = cachedResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: List<Category>): DataState<List<Category>>? {

                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }

        }.getResult()

        return response?.data ?: ArrayList()
    }

}