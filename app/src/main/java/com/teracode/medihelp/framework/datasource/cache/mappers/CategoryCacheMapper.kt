package com.teracode.medihelp.framework.datasource.cache.mappers

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.datasource.cache.model.CategoryCacheEntity
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import javax.inject.Singleton

@Singleton
class CategoryCacheMapper : EntityMapper<CategoryCacheEntity, Category> {


    fun categoryListToEntityList(categories: List<Category>): List<CategoryCacheEntity> {
        val list: ArrayList<CategoryCacheEntity> = ArrayList()
        for (category in categories) {
            list.add(mapToEntity(category))
        }
        return list
    }

    fun entityListToCategoryList(entities: List<CategoryCacheEntity>): List<Category> {
        val list: ArrayList<Category> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }


    override fun mapFromEntity(entity: CategoryCacheEntity): Category {

        return Category(
            id = entity.id,
            title = entity.title.capitalizeWords(),
            image = entity.image,
            url = entity.url,
            description = entity.description
        )
    }

    override fun mapToEntity(domainModel: Category): CategoryCacheEntity {
        return CategoryCacheEntity(
            id = domainModel.id,
            title = domainModel.title.capitalizeWords(),
            image = domainModel.image,
            url = domainModel.url,
            description = domainModel.description
        )

    }
}