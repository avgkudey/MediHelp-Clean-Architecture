package com.teracode.medihelp.framework.datasource.network.mappers

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.datasource.cache.model.CategoryCacheEntity
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity
import com.teracode.medihelp.framework.datasource.network.model.CategoryNetworkEntity
import javax.inject.Singleton

@Singleton
class CategoryNetworkMapper : EntityMapper<CategoryNetworkEntity, Category> {


    fun categoryListToEntityList(categories: List<Category>): List<CategoryNetworkEntity> {
        val list: ArrayList<CategoryNetworkEntity> = ArrayList()
        for (category in categories) {
            list.add(mapToEntity(category))
        }
        return list
    }

    fun entityListToCategoryList(entities: List<CategoryNetworkEntity>): List<Category> {
        val list: ArrayList<Category> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }


    override fun mapFromEntity(entity: CategoryNetworkEntity): Category {

        return Category(
            id = entity.id,
            title = entity.title,
            image = entity.image,
            url = entity.url,
            description = entity.description
        )
    }

    override fun mapToEntity(domainModel: Category): CategoryNetworkEntity {
        return CategoryNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            image = domainModel.image,
            url = domainModel.url,
            description = domainModel.description
        )

    }
}