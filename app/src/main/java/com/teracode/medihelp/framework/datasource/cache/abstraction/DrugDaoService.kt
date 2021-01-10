package com.teracode.medihelp.framework.datasource.cache.abstraction

import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.database.DRUG_PAGINATION_PAGE_SIZE
import com.teracode.medihelp.util.OrderEnum

interface DrugDaoService {


    suspend fun insertDrug(drug: Drug): Long

    suspend fun insertDrugs(drugs: List<Drug>): LongArray

    suspend fun searchDrugById(primaryKey: String): Drug?

    suspend fun deleteDrug(primaryKey: String): Int

    suspend fun deleteDrugs(drugs: List<Drug>): Int

    suspend fun getAllDrugs(): List<Drug>

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


    suspend fun getNumDrugs(
        categoryId: String?,
        subcategoryId: String?
    ): Int


    suspend fun searchDrugs(
        query: String,
        categoryId: String?,
        subcategoryId: String?,
        filterAndOrder: OrderEnum,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<Drug>
}