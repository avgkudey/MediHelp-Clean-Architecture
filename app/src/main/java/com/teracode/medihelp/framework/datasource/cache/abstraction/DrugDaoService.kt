package com.teracode.medihelp.framework.datasource.cache.abstraction

import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.database.DRUG_PAGINATION_PAGE_SIZE

interface DrugDaoService {


    suspend fun insertDrug(drug: Drug): Long

    suspend fun deleteDrug(primaryKey: String): Int

    suspend fun deleteDrugs(drugs: List<Drug>): Int

    suspend fun updateDrug(
        primaryKey: String,
        title: String,
        trade_name: String? = null,
        pharmacological_name: String? = null,
        action: String? = null,
        antidote: String? = null,
        cautions: String? = null,
        contraindication: String? = null,
        dosages: String? = null,
        indications: String? = null,
        maximum_dose: String? = null,
        nursing_implications: String? = null,
        notes: String? = null,
        preparation: String? = null,
        side_effects: String? = null,
        video: String? = null,
        category_id: String? = null,
        subcategory_id: String? = null
    ): Int

    suspend fun searchDrugs(): List<Drug>

    suspend fun searchDrugsOrderByDateDESC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<Drug>

    suspend fun searchDrugsOrderByDateASC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<Drug>

    suspend fun searchDrugsOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<Drug>

    suspend fun searchDrugsOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<Drug>


    suspend fun searchDrugById(primaryKey: String): Drug?

    suspend fun getNumDrugs(): Int

    suspend fun insertDrugs(drugs: List<Drug>): LongArray

    suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Drug>
}