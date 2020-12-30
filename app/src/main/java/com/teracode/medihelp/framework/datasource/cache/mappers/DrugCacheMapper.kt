package com.teracode.medihelp.framework.datasource.cache.mappers

import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity
import javax.inject.Singleton

@Singleton
class DrugCacheMapper : EntityMapper<DrugCacheEntity, Drug> {


    fun drugListToEntityList(drugs: List<Drug>): List<DrugCacheEntity> {
        val list: ArrayList<DrugCacheEntity> = ArrayList()
        for(drug in drugs){
            list.add(mapToEntity(drug))
        }
        return list
    }
    fun entityListToDrugList(entities: List<DrugCacheEntity>): List<Drug> {
        val list: ArrayList<Drug> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    override fun mapFromEntity(entity: DrugCacheEntity): Drug {
        return Drug(
            id = entity.id,
            title = entity.title,
            trade_name = entity.trade_name,
            pharmacological_name = entity.pharmacological_name,
            action = entity.action,
            antidote = entity.antidote,
            cautions = entity.cautions,
            contraindication = entity.contraindication,
            dosages = entity.dosages,
            indications = entity.indications,
            maximum_dose = entity.maximum_dose,
            nursing_implications = entity.nursing_implications,
            notes = entity.notes,
            preparation = entity.preparation,
            side_effects = entity.side_effects,
            video = entity.video,
            category_id = entity.category_id,
            subcategory_id = entity.subcategory_id,
        )

    }

    override fun mapToEntity(domainModel: Drug): DrugCacheEntity {
        return DrugCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            trade_name = domainModel.trade_name,
            pharmacological_name = domainModel.pharmacological_name,
            action = domainModel.action,
            antidote = domainModel.antidote,
            cautions = domainModel.cautions,
            contraindication = domainModel.contraindication,
            dosages = domainModel.dosages,
            indications = domainModel.indications,
            maximum_dose = domainModel.maximum_dose,
            nursing_implications = domainModel.nursing_implications,
            notes = domainModel.notes,
            preparation = domainModel.preparation,
            side_effects = domainModel.side_effects,
            video = domainModel.video,
            category_id = domainModel.category_id,
            subcategory_id = domainModel.subcategory_id,
        )
    }


}