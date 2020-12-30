package com.teracode.medihelp.framework.datasource.cache.mappers

import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.datasource.cache.model.SubcategoryCacheEntity
import javax.inject.Singleton

@Singleton
class SubcategoryCacheMapper : EntityMapper<SubcategoryCacheEntity, Subcategory> {


    fun subcategoryListToEntityList(subcategories: List<Subcategory>): List<SubcategoryCacheEntity> {
        val list: ArrayList<SubcategoryCacheEntity> = ArrayList()
        for (subcategory in subcategories) {
            list.add(mapToEntity(subcategory))
        }
        return list
    }

    fun entityListToSubcategoryList(entities: List<SubcategoryCacheEntity>): List<Subcategory> {
        val list: ArrayList<Subcategory> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }


    override fun mapFromEntity(entity: SubcategoryCacheEntity): Subcategory {

        return Subcategory(
            id = entity.id,
            title = entity.title,
            categoryId = entity.categoryId,
            image = entity.image,
            url = entity.url,
            description = entity.description
        )
    }

    override fun mapToEntity(domainModel: Subcategory): SubcategoryCacheEntity {
        return SubcategoryCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            categoryId = domainModel.categoryId,
            image = domainModel.image,
            url = domainModel.url,
            description = domainModel.description
        )

    }
}