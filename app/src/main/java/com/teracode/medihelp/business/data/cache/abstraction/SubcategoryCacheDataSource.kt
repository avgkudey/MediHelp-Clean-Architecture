package com.teracode.medihelp.business.data.cache.abstraction

import com.teracode.medihelp.business.domain.model.Subcategory

interface SubcategoryCacheDataSource {

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

    suspend fun getNumSubcategories(): Int

}

















































