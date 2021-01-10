package com.teracode.medihelp.business.data.cache.abstraction

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug

interface CategoryCacheDataSource {

    suspend fun insertCategory(category: Category): Long

    suspend fun insertCategories(categories: List<Category>): LongArray

    suspend fun searchCategoryById(primaryKey: String): Category?

    suspend fun deleteCategory(primaryKey: String): Int

    suspend fun deleteCategories(categories: List<Category>): Int


    suspend fun updateCategory(
        primaryKey: String,
        title: String,
        image: String? = null,
        url: String? = null,
        description: String? = null,
    ): Int


    suspend fun getNumCategories(): Int

    suspend fun getAllCategories(): List<Category>

}

















































