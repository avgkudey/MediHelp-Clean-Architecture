package com.teracode.medihelp.business.data.cache.implementation

import com.teracode.medihelp.business.data.cache.abstraction.CategoryCacheDataSource
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.framework.datasource.cache.abstraction.CategoryDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryCacheDataSourceImpl
@Inject
constructor(
    private val daoService: CategoryDaoService,
) : CategoryCacheDataSource {
    override suspend fun insertCategory(category: Category) = daoService.insertCategory(category)


    override suspend fun insertCategories(categories: List<Category>) =
        daoService.insertCategories(categories)


    override suspend fun searchCategoryById(primaryKey: String) =
        daoService.searchCategoryById(primaryKey)


    override suspend fun deleteCategory(primaryKey: String) = daoService.deleteCategory(primaryKey)


    override suspend fun deleteCategories(categories: List<Category>) =
        daoService.deleteCategories(categories)


    override suspend fun getAllCategories() = daoService.getAllCategories()


    override suspend fun getNumCategories() = daoService.getNumCategories()


    override suspend fun updateCategory(
        primaryKey: String,
        title: String,
        image: String?,
        url: String?,
        description: String?
    ) = daoService.updateCategory(
        primaryKey = primaryKey,
        title = title,
        image = image,
        url = url,
        description = description,
    )

}