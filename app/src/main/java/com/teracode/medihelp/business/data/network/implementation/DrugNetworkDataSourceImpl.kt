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
    override suspend fun insertOrUpdateDrug(drug: Drug) = firestoreService.insertOrUpdateDrug(drug)

    override suspend fun deleteDrug(primaryKey: String) = firestoreService.deleteDrug(primaryKey)

    override suspend fun insertDeletedDrug(drug: Drug) = firestoreService.insertDeletedDrug(drug)

    override suspend fun insertDeletedDrugs(drugs: List<Drug>) =
        firestoreService.insertDeletedDrugs(drugs)

    override suspend fun deleteDeletedDrug(drug: Drug) = firestoreService.deleteDeletedDrug(drug)

    override suspend fun getDeletedDrugs() = firestoreService.getDeletedDrugs()

    override suspend fun deleteAllDrugs() = firestoreService.deleteAllDrugs()

    override suspend fun searchDrug(drug: Drug) = firestoreService.searchDrug(drug)

    override suspend fun getAllDrugs() = firestoreService.getAllDrugs()

    override suspend fun insertOrUpdateDrugs(drugs: List<Drug>) =
        firestoreService.insertOrUpdateDrugs(drugs)


}