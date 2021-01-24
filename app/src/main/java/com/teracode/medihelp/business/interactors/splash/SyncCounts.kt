package com.teracode.medihelp.business.interactors.splash

import android.content.SharedPreferences
import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.cache.abstraction.CategoryCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.network.ApiResponseHandler
import com.teracode.medihelp.business.data.network.abstraction.CategoryNetworkDataSource
import com.teracode.medihelp.business.data.util.safeApiCall
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.framework.presentation.datasync.DataNetworkSyncManager
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager.Companion.CATEGORY_LIST_SYNCED
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncCounts(
    private val categoryCacheDataSource: CategoryCacheDataSource,
    private val subcategoryCacheDataSource: SubcategoryCacheDataSource,
    private val drugCacheDataSource: DrugCacheDataSource,
    private val editor: SharedPreferences.Editor,
) {

    suspend fun syncCategories() {
        val cachedCategoriesList = getCachedCategoriesList()


        setItemCount(cachedCategoriesList)

    }


    private suspend fun setItemCount(cachedCategoriesList: List<Category>) {


        for (category in cachedCategoriesList) {
            val subcategoryCount =
                getCachedSubcategoriesCount(categoryId = category.id)

            val drugsCount = getCachedDrugsCount(categoryId = category.id)

            if (checkRequireUpdate(category, subcategoryCount, drugsCount)) {
                updateCategory(category, subcategoryCount, drugsCount)
            }

        }

        setSynced(DataNetworkSyncManager.DRUG_COUNT_SYNCED, true)
    }

    private suspend fun updateCategory(category: Category, subcategoryCount: Int, drugsCount: Int) {
        safeCacheCall(IO) {
            categoryCacheDataSource.updateCategory(
                primaryKey = category.id,
                title = category.title,
                image = category.image,
                url = category.url,
                description = category.description,
                subcategoryCount = subcategoryCount,
                drugsCount = drugsCount
            )
        }
    }

    private fun checkRequireUpdate(
        category: Category,
        subcategoryCount: Int,
        drugsCount: Int,
    ): Boolean {

        return !(category.drugCount == drugsCount && category.subcategoryCount == subcategoryCount)

    }


    private suspend fun getCachedCategoriesList(): List<Category> {

        val cachedResult = safeCacheCall(IO) {
            categoryCacheDataSource.getAllCategories()
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

    private suspend fun getCachedSubcategoriesCount(categoryId: String): Int {

        val cachedResult = safeCacheCall(IO) {
            subcategoryCacheDataSource.getNumSubcategories(categoryId)
        }

        val response = object : CacheResponseHandler<Int, Int>(
            response = cachedResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: Int): DataState<Int>? {

                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }

        }.getResult()

        return response?.data ?: 0
    }

    private suspend fun getCachedDrugsCount(categoryId: String): Int {

        val cachedResult = safeCacheCall(IO) {
            drugCacheDataSource.getNumDrugs(categoryId = categoryId, null)
        }

        val response = object : CacheResponseHandler<Int, Int>(
            response = cachedResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: Int): DataState<Int>? {

                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }

        }.getResult()

        return response?.data ?: 0
    }

    private fun setSynced(key: String, value: Boolean) {

        editor.putBoolean(key, value)
        editor.apply()
    }
}