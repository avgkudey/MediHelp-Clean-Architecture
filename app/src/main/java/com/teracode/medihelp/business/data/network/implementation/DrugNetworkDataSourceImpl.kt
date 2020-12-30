package com.teracode.medihelp.business.data.network.implementation

import com.teracode.medihelp.business.data.network.abstraction.DrugNetworkDataSource
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.network.abstraction.DrugFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrugNetworkDataSourceImpl
@Inject
constructor(
    private val firestoreService: DrugFirestoreService
) : DrugNetworkDataSource {


    override suspend fun searchDrug(drug: Drug) = firestoreService.searchDrug(drug)

    override suspend fun getAllDrugs() = firestoreService.getAllDrugs()


}