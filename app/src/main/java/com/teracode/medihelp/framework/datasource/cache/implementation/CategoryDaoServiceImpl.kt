package com.teracode.medihelp.framework.datasource.cache.implementation

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.framework.datasource.cache.abstraction.CategoryDaoService
import com.teracode.medihelp.framework.datasource.cache.mappers.CategoryCacheMapper
import com.teracode.medihelp.framework.datasource.database.CategoryDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryDaoServiceImpl
@Inject
constructor(
    private val categoryDao: CategoryDao,
    private val categoryMapper: CategoryCacheMapper

) : CategoryDaoService {
    override suspend fun insertCategory(category: Category): Long {
        return categoryDao.insertCategory(
            category = categoryMapper.mapToEntity(category)
        )
    }

    override suspend fun insertCategories(categories: List<Category>): LongArray {
        return categoryDao.insertCategories(
            categories = categoryMapper.categoryListToEntityList(categories)
        )
    }

    override suspend fun searchCategoryById(primaryKey: String): Category? {
        return categoryDao.searchCategoryById(primaryKey)?.let {
            categoryMapper.mapFromEntity(it)
        }
    }

    override suspend fun searchCategoryByTitle(title: String): Category? {
        return categoryDao.searchCategoryByTitle(title)?.let {
            categoryMapper.mapFromEntity(it)
        }
    }

    override suspend fun deleteCategory(primaryKey: String): Int {
        return categoryDao.deleteCategory(primaryKey)
    }

    override suspend fun deleteCategories(categories: List<Category>): Int {
        val ids = categories.mapIndexed { _, value -> value.id }
        return categoryDao.deleteCategories(ids)
    }


    override suspend fun updateCategory(
        primaryKey: String,
        title: String,
        image: String?,
        url: String?,
        description: String?
    ): Int {
        return categoryDao.updateCategory(
            primaryKey = primaryKey,
            title = title,
            image = image,
            url = url,
            description = description
        )
    }

    override suspend fun getNumCategories(): Int {
        return categoryDao.getNumCategories()
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryMapper.entityListToCategoryList(
            entities = categoryDao.getAllCategories()
        )
    }

}