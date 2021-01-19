package com.teracode.medihelp.framework.datasource.cache.abstraction

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.framework.datasource.database.DRUG_PAGINATION_PAGE_SIZE

interface CategoryDaoService {


    suspend fun insertCategory(category: Category): Long

    suspend fun insertCategories(categories: List<Category>): LongArray

    suspend fun searchCategoryById(primaryKey: String): Category?

    suspend fun searchCategoryByTitle(title: String): Category?

    suspend fun deleteCategory(primaryKey: String): Int

    suspend fun deleteCategories(categories: List<Category>): Int

    suspend fun getAllCategories(): List<Category>

    suspend fun updateCategory(
        primaryKey: String,
        title: String,
        image: String? = null,
        url: String? = null,
        description: String? = null,
        subcategoryCount: Int = 0,
        drugsCount: Int = 0,
    ): Int


    suspend fun getNumCategories(): Int

}