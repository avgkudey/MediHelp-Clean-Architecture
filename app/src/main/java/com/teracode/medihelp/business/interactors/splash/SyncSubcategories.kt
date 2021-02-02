package com.teracode.medihelp.business.interactors.splash

import android.content.SharedPreferences
import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.network.ApiResponseHandler
import com.teracode.medihelp.business.data.network.abstraction.SubcategoryNetworkDataSource
import com.teracode.medihelp.business.data.util.safeApiCall
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.framework.presentation.datasync.DataNetworkSyncManager.Companion.SUBCATEGORY_LIST_SYNCED
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncSubcategories(
    private val cacheDataSource: SubcategoryCacheDataSource,
    private val networkDataSource: SubcategoryNetworkDataSource,
    private val editor: SharedPreferences.Editor
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
                override suspend fun handleSuccess(resultObj: List<Subcategory>): DataState<List<Subcategory>> {

                    return DataState.data(
                        response = null,
                        data = resultObj,
                        stateEvent = null
                    )
                }

            }.getResult()

            val networkSubcategoryList = response?.data ?: ArrayList()

            for (networkSubcategory in networkSubcategoryList) {
                cacheDataSource.searchSubcategoryById(networkSubcategory.id)?.let { cachedSubcategory ->

                    cachedSubcategories.remove(cachedSubcategory)
                    checkRequiresUpdate(cachedSubcategory, networkSubcategory)

                } ?: cacheDataSource.insertSubcategory(networkSubcategory)


            }


            for (cachedSubcategory in cachedSubcategories) {
                networkDataSource.searchSubcategory(cachedSubcategory)
                    ?: cacheDataSource.deleteSubcategory(
                        cachedSubcategory.id
                    )
            }

            setSynced(SUBCATEGORY_LIST_SYNCED, true)
        }

    private suspend fun checkRequiresUpdate(
        cachedSubcategory: Subcategory,
        networkSubcategory: Subcategory
    ) {

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
            override suspend fun handleSuccess(resultObj: List<Subcategory>): DataState<List<Subcategory>> {

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

        editor.putBoolean(key, value)
        editor.apply()
    }
}