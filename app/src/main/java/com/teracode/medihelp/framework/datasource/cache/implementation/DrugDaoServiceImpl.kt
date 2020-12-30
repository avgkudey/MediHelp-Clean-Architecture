package com.teracode.medihelp.framework.datasource.cache.implementation

import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.cache.abstraction.DrugDaoService
import com.teracode.medihelp.framework.datasource.cache.mappers.DrugCacheMapper
import com.teracode.medihelp.framework.datasource.database.DrugDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrugDaoServiceImpl
@Inject
constructor(
    private val drugDao: DrugDao,
    private val drugMapper: DrugCacheMapper
) : DrugDaoService {
    override suspend fun insertDrug(drug: Drug): Long {
        return drugDao.insertDrug(
            drug = drugMapper.mapToEntity(drug)
        )
    }

    override suspend fun insertDrugs(drugs: List<Drug>): LongArray {
        return drugDao.insertDrugs(
            drugs = drugMapper.drugListToEntityList(drugs)
        )
    }

    override suspend fun searchDrugById(primaryKey: String): Drug? {
        return drugDao.searchDrugById(primaryKey)?.let { drugEntity ->
            drugMapper.mapFromEntity(drugEntity)
        }
    }

    override suspend fun deleteDrug(primaryKey: String): Int {
        return drugDao.deleteDrug(primaryKey)
    }

    override suspend fun deleteDrugs(drugs: List<Drug>): Int {
        val ids = drugs.mapIndexed { _, value -> value.id }
        return drugDao.deleteDrugs(ids = ids)
    }

    override suspend fun getAllDrugs(): List<Drug> {
        return drugMapper.entityListToDrugList(entities = drugDao.getAllDrugs())
    }

    override suspend fun updateDrug(
        primaryKey: String,
        title: String,
        trade_name: String?,
        pharmacological_name: String?,
        action: String?,
        antidote: String?,
        cautions: String?,
        contraindication: String?,
        dosages: String?,
        indications: String?,
        maximum_dose: String?,
        nursing_implications: String?,
        notes: String?,
        preparation: String?,
        side_effects: String?,
        video: String?,
        category_id: String?,
        subcategory_id: String?
    ): Int {
        return drugDao.updateDrug(
            primaryKey = primaryKey,
            title = title,
            trade_name = trade_name,
            pharmacological_name = pharmacological_name,
            action = action,
            antidote = antidote,
            cautions = cautions,
            contraindication = contraindication,
            dosages = dosages,
            indications = indications,
            maximum_dose = maximum_dose,
            nursing_implications = nursing_implications,
            notes = notes,
            preparation = preparation,
            side_effects = side_effects,
            video = video,
            category_id = category_id,
            subcategory_id = subcategory_id,


            )
    }

    override suspend fun searchDrugs(): List<Drug> {
        return drugMapper.entityListToDrugList(
            entities = drugDao.searchDrugs()
        )
    }


    override suspend fun searchDrugsOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int
    ): List<Drug> {
        return drugMapper.entityListToDrugList(
            entities = drugDao.searchDrugsOrderByTitleASC(
                query = query,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun searchDrugsWhereCategoryOrderByTitleASC(
        categoryId: String,
        page: Int,
        pageSize: Int
    ): List<Drug> {
        return drugMapper.entityListToDrugList(
            entities = drugDao.searchDrugsWhereCategoryOrderByTitleASC(
                categoryId = categoryId,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun searchDrugsWhereCategoryOrderByTitleDESC(
        categoryId: String,
        page: Int,
        pageSize: Int
    ): List<Drug> {

        return drugMapper.entityListToDrugList(
            entities = drugDao.searchDrugsWhereCategoryOrderByTitleDESC(
                categoryId = categoryId,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun searchDrugsWhereSubcategoryOrderByTitleASC(
        subcategoryId: String,
        page: Int,
        pageSize: Int
    ): List<Drug> {
        return drugMapper.entityListToDrugList(
            entities = drugDao.searchDrugsWhereSubcategoryOrderByTitleASC(
                subcategoryId = subcategoryId,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun searchDrugsWhereSubcategoryOrderByTitleDESC(
        subcategoryId: String,
        page: Int,
        pageSize: Int
    ): List<Drug> {
        return drugMapper.entityListToDrugList(
            entities = drugDao.searchDrugsWhereSubcategoryOrderByTitleDESC(
                subcategoryId = subcategoryId,
                page = page,
                pageSize = pageSize
            )
        )
    }


    override suspend fun searchDrugsOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int
    ): List<Drug> {
        return drugMapper.entityListToDrugList(
            entities = drugDao.searchDrugsOrderByTitleDESC(
                query = query,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun getNumDrugs(): Int {
        return drugDao.getNumDrugs()
    }

    override suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Drug> {
        return drugMapper.entityListToDrugList(
            drugDao.returnOrderedQuery(
                query = query,
                filterAndOrder = filterAndOrder,
                page = page
            )
        )
    }
}