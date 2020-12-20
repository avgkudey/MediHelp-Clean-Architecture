package com.teracode.medihelp.business.data.cache.abstraction

import com.teracode.medihelp.business.domain.model.Drug

interface DrugCacheDataSource {

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

    suspend fun searchDrugs(query: String, filterAndOrder: String, page: Int): List<Drug>

    suspend fun searchDrugById(primaryKey: String): Drug?

    suspend fun getNumDrugs(): Int

    suspend fun insertDrugs(drugs: List<Drug>): LongArray
}

















































