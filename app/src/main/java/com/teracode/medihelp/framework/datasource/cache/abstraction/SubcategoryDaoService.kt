package com.teracode.medihelp.framework.datasource.cache.abstraction

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.framework.datasource.database.DRUG_PAGINATION_PAGE_SIZE
import com.teracode.medihelp.framework.datasource.database.SUBCATEGORY_PAGINATION_PAGE_SIZE
import com.teracode.medihelp.util.OrderEnum

interface SubcategoryDaoService {
    suspend fun insertSubcategory(subcategory: Subcategory): Long

    suspend fun insertSubcategories(subcategories: List<Subcategory>): LongArray

    suspend fun searchSubcategoryById(primaryKey: String): Subcategory?

    suspend fun deleteSubcategory(primaryKey: String): Int

    suspend fun deleteSubcategories(subcategories: List<Subcategory>): Int


    suspend fun updateSubcategory(
        primaryKey: String,
        title: String,
        categoryId: String,
        image: String? = null,
        url: String? = null,
        description: String? = null,
    ): Int


    suspend fun getAllSubcategories(): List<Subcategory>

    suspend fun getNumSubcategories(categoryId: String?): Int


    suspend fun searchSubcategories(
        query: String,
        categoryId: String?,
        filterAndOrder: OrderEnum,
        page: Int,
        pageSize: Int = SUBCATEGORY_PAGINATION_PAGE_SIZE
    ): List<Subcategory>

}