package com.teracode.medihelp.framework.datasource.cache.implementation

import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.framework.datasource.cache.abstraction.SubcategoryDaoService
import com.teracode.medihelp.framework.datasource.cache.mappers.SubcategoryCacheMapper
import com.teracode.medihelp.framework.datasource.database.SubcategoryDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubcategoryDaoServiceImpl
@Inject
constructor(
    private val dao: SubcategoryDao,
    private val cacheMapper: SubcategoryCacheMapper
) : SubcategoryDaoService {
    override suspend fun insertSubcategory(subcategory: Subcategory): Long {

        return dao.insertSubcategory(
            subcategory = cacheMapper.mapToEntity(subcategory)
        )
    }

    override suspend fun insertSubcategories(subcategories: List<Subcategory>): LongArray {
        return dao.insertSubcategories(
            subcategories = cacheMapper.subcategoryListToEntityList(subcategories)
        )
    }

    override suspend fun searchSubcategoryById(primaryKey: String): Subcategory? {
        return dao.searchSubcategoryById(primaryKey)?.let {
            cacheMapper.mapFromEntity(it)
        }
    }

    override suspend fun deleteSubcategory(primaryKey: String): Int {
        return dao.deleteSubcategory(primaryKey)
    }

    override suspend fun deleteSubcategories(subcategories: List<Subcategory>): Int {
        val ids = subcategories.mapIndexed { _, value -> value.id }
        return dao.deleteSubcategories(ids)
    }

    override suspend fun updateSubcategory(
        primaryKey: String,
        title: String,
        categoryId: String,
        image: String?,
        url: String?,
        description: String?
    ): Int {
        return dao.updateSubcategory(
            primaryKey = primaryKey,
            title = title,
            categoryId = categoryId,
            image = image,
            url = url,
            description = description
        )
    }

    override suspend fun getAllSubcategories(): List<Subcategory> {
        return cacheMapper.entityListToSubcategoryList(
            entities = dao.getAllSubcategories()
        )
    }

    override suspend fun getNumSubcategories(): Int {
        return dao.getNumSubcategories()
    }
}