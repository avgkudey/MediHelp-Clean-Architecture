package com.teracode.medihelp.business.data.cache.implementation

import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.cache.abstraction.DrugDaoService
import com.teracode.medihelp.util.OrderEnum
import com.teracode.medihelp.util.printLogD
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrugCacheDataSourceImpl
@Inject
constructor(
    private val drugDaoService: DrugDaoService
) : DrugCacheDataSource {
    override suspend fun insertDrug(drug: Drug) = drugDaoService.insertDrug(drug)

    override suspend fun insertDrugs(drugs: List<Drug>) = drugDaoService.insertDrugs(drugs)

    override suspend fun deleteDrug(primaryKey: String) = drugDaoService.deleteDrug(primaryKey)

    override suspend fun deleteDrugs(drugs: List<Drug>) = drugDaoService.deleteDrugs(drugs)

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

        printLogD("SyncDrugs:UPDATING", trade_name.toString())

        return drugDaoService.updateDrug(
            primaryKey,
            title,
            trade_name,
            pharmacological_name,
            action,
            antidote,
            cautions,
            contraindication,
            dosages,
            indications,
            maximum_dose,
            nursing_implications,
            notes,
            preparation,
            side_effects,
            video,
            category_id,
            subcategory_id
        )
    }


    override suspend fun getAllDrugs(): List<Drug> = drugDaoService.getAllDrugs()

    override suspend fun searchDrugById(primaryKey: String) =
        drugDaoService.searchDrugById(primaryKey)

    override suspend fun getNumDrugs(
        categoryId: String?,
        subcategoryId: String?
    ) = drugDaoService.getNumDrugs(
        categoryId = categoryId,
        subcategoryId = subcategoryId,
    )


    override suspend fun searchDrugs(
        query: String,
        categoryId: String?,
        subcategoryId: String?,
        filterAndOrder: OrderEnum,
        page: Int,
        pageSize: Int
    ): List<Drug> {

        return drugDaoService.searchDrugs(
            query = query,
            categoryId = categoryId,
            subcategoryId = subcategoryId,
            filterAndOrder = filterAndOrder,
            page = page,
            pageSize = pageSize,
            )
    }

}