package com.teracode.medihelp.framework.datasource.network.mappers

import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.datasource.network.model.SubcategoryNetworkEntity
import javax.inject.Singleton

@Singleton
class SubcategoryNetworkMapper : EntityMapper<SubcategoryNetworkEntity, Subcategory> {


    fun subcategoryListToEntityList(subcategories: List<Subcategory>): List<SubcategoryNetworkEntity> {
        val list: ArrayList<SubcategoryNetworkEntity> = ArrayList()
        for (subcategory in subcategories) {
            list.add(mapToEntity(subcategory))
        }
        return list
    }

    fun entityListToSubcategoryList(entities: List<SubcategoryNetworkEntity>): List<Subcategory> {
        val list: ArrayList<Subcategory> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }


    override fun mapFromEntity(entity: SubcategoryNetworkEntity): Subcategory {

        return Subcategory(
            id = entity.id,
            title = entity.title,
            categoryId = entity.categoryId,
            image = entity.image,
            url = entity.url,
            description = entity.description
        )
    }

    override fun mapToEntity(domainModel: Subcategory): SubcategoryNetworkEntity {
        return SubcategoryNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            categoryId = domainModel.categoryId,
            image = domainModel.image,
            url = domainModel.url,
            description = domainModel.description
        )

    }
}