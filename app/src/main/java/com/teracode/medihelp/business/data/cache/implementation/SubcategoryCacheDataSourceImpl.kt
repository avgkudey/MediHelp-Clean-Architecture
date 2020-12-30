package com.teracode.medihelp.business.data.cache.implementation

import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.framework.datasource.cache.abstraction.SubcategoryDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubcategoryCacheDataSourceImpl
@Inject
constructor(
    private val daoService: SubcategoryDaoService
) : SubcategoryCacheDataSource {
    override suspend fun insertSubcategory(subcategory: Subcategory) =
        daoService.insertSubcategory(subcategory)

    override suspend fun insertSubcategories(subcategories: List<Subcategory>) =
        daoService.insertSubcategories(subcategories)

    override suspend fun searchSubcategoryById(primaryKey: String) =
        daoService.searchSubcategoryById(primaryKey)

    override suspend fun deleteSubcategory(primaryKey: String) =
        daoService.deleteSubcategory(primaryKey)

    override suspend fun deleteSubcategories(subcategories: List<Subcategory>) =
        daoService.deleteSubcategories(subcategories)

    override suspend fun updateSubcategory(
        primaryKey: String,
        title: String,
        categoryId: String,
        image: String?,
        url: String?,
        description: String?
    ) =
        daoService.updateSubcategory(
            primaryKey = primaryKey,
            title = title,
            categoryId = categoryId,
            image = image,
            url = url
        )

    override suspend fun getAllSubcategories() =
        daoService.getAllSubcategories()

    override suspend fun getNumSubcategories() =
        daoService.getNumSubcategories()
}