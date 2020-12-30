package com.teracode.medihelp.business.interactors.splash

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.network.ApiResponseHandler
import com.teracode.medihelp.business.data.network.abstraction.SubcategoryNetworkDataSource
import com.teracode.medihelp.business.data.util.safeApiCall
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.DataState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncSubcategories(
    private val cacheDataSource: SubcategoryCacheDataSource,
    private val networkDataSource: SubcategoryNetworkDataSource
) {

    suspend fun syncSubcategories() {
        val cachedSubcategoriesList = getCachedSubcategoriesList()


        syncNetworkSubcategoriesWithCachedSubcategories(ArrayList(cachedSubcategoriesList))
    }

    private suspend fun syncNetworkSubcategoriesWithCachedSubcategories(cachedSubcategories: ArrayList<Subcategory>) =
        withContext(IO) {
            val networkResult = safeApiCall(IO) {
                networkDataSource.getAllSubcategories()
            }

            val response = object : ApiResponseHandler<List<Subcategory>, List<Subcategory>>(
                response = networkResult,
                stateEvent = null
            ) {
                override suspend fun handleSuccess(resultObj: List<Subcategory>): DataState<List<Subcategory>>? {

                    return DataState.data(
                        response = null,
                        data = resultObj,
                        stateEvent = null
                    )
                }

            }.getResult()

            val networkSubcategoryList = response?.data ?: ArrayList()

            for (subcategory in networkSubcategoryList) {
                cacheDataSource.searchSubcategoryById(subcategory.id)?.let { cachedSubcategory ->

                    cachedSubcategories.remove(subcategory)
                    checkRequiresUpdate(cachedSubcategory, subcategory)

                } ?: cacheDataSource.insertSubcategory(subcategory)


            }


        }

    private suspend fun checkRequiresUpdate(cachedSubcategory: Subcategory, networkSubcategory: Subcategory) {

        if (networkSubcategory != cachedSubcategory) {


            safeCacheCall(IO) {
                cacheDataSource.updateSubcategory(
                    primaryKey = networkSubcategory.id,
                    title = networkSubcategory.title,
                    categoryId = networkSubcategory.categoryId,
                    image = networkSubcategory.image,
                    url = networkSubcategory.url,
                    description = networkSubcategory.description,
                )
            }


        }
    }

    private suspend fun getCachedSubcategoriesList(): List<Subcategory> {

        val cachedResult = safeCacheCall(IO) {
            cacheDataSource.getAllSubcategories()
        }

        val response = object : CacheResponseHandler<List<Subcategory>, List<Subcategory>>(
            response = cachedResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: List<Subcategory>): DataState<List<Subcategory>>? {

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