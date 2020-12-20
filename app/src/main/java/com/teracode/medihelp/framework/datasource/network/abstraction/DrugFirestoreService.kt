package com.teracode.medihelp.framework.datasource.network.abstraction

import com.teracode.medihelp.business.domain.model.Drug

interface DrugFirestoreService {


    suspend fun insertOrUpdateDrug(drug: Drug)

    suspend fun deleteDrug(primaryKey: String)

    suspend fun insertDeletedDrug(drug: Drug)

    suspend fun insertDeletedDrugs(drugs: List<Drug>)

    suspend fun deleteDeletedDrug(drug: Drug)

    suspend fun getDeletedDrugs(): List<Drug>

    suspend fun deleteAllDrugs()

    suspend fun searchDrug(drug: Drug): Drug?

    suspend fun getAllDrugs(): List<Drug>

    suspend fun insertOrUpdateDrugs(drugs: List<Drug>)
}