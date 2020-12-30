package com.teracode.medihelp.business.data.network.abstraction

import com.teracode.medihelp.business.domain.model.Drug

interface DrugNetworkDataSource {
    suspend fun searchDrug(drug: Drug): Drug?
    suspend fun getAllDrugs(): List<Drug>

}