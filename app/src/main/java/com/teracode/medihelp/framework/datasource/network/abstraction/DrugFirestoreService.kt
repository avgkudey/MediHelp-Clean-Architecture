package com.teracode.medihelp.framework.datasource.network.abstraction

import com.teracode.medihelp.business.domain.model.Drug

interface DrugFirestoreService {
    suspend fun searchDrug(drug: Drug): Drug?

    suspend fun getAllDrugs(): List<Drug>

}