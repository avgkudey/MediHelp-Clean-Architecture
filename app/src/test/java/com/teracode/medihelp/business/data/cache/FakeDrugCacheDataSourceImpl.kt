package com.teracode.medihelp.business.data.cache

import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.database.DRUG_PAGINATION_PAGE_SIZE
import com.teracode.medihelp.util.OrderEnum

const val FORCE_DELETE_DRUG_EXCEPTION = "FORCE_DELETE_DRUG_EXCEPTION"
const val FORCE_DELETES_DRUG_EXCEPTION = "FORCE_DELETES_DRUG_EXCEPTION"
const val FORCE_UPDATE_DRUG_EXCEPTION = "FORCE_UPDATE_DRUG_EXCEPTION"
const val FORCE_NEW_DRUG_EXCEPTION = "FORCE_NEW_DRUG_EXCEPTION"
const val FORCE_SEARCH_DRUGS_EXCEPTION = "FORCE_SEARCH_DRUGS_EXCEPTION"
const val FORCE_GENERAL_FAILURE = "FORCE_GENERAL_FAILURE"

class FakeDrugCacheDataSourceImpl
constructor(
    private val drugData: HashMap<String, Drug>
) : DrugCacheDataSource {
    override suspend fun insertDrug(drug: Drug): Long {
        if (drug.id == FORCE_NEW_DRUG_EXCEPTION) {
            throw Exception("Something went wrong inserting the drug.")
        }
        if (drug.id == FORCE_GENERAL_FAILURE) {
            return -1 // fail
        }
        drugData.put(drug.id, drug)
        return 1 // success

    }

    override suspend fun insertDrugs(drugs: List<Drug>): LongArray {
        val results = LongArray(drugs.size)
        for ((index, drug) in drugs.withIndex()) {
            results[index] = 1
            drugData.put(drug.id, drug)
        }
        return results
    }

    override suspend fun deleteDrug(primaryKey: String): Int {
        if (primaryKey == FORCE_DELETE_DRUG_EXCEPTION) {
            throw Exception("Something went wrong deleting the drug.")
        } else if (primaryKey == FORCE_DELETES_DRUG_EXCEPTION) {
            throw Exception("Something went wrong deleting the drug.")
        }
        return drugData.remove(primaryKey)?.let {
            1 // return 1 for success
        } ?: -1 // -1 for failure
    }

    override suspend fun deleteDrugs(drugs: List<Drug>): Int {
        var failOrSuccess = 1
        for (drug in drugs) {
            if (drugData.remove(drug.id) == null) {
                failOrSuccess = -1 // mark for failure
            }
        }
        return failOrSuccess
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
        if (primaryKey == FORCE_UPDATE_DRUG_EXCEPTION) {
            throw Exception("Something went wrong updating the drug.")
        }
        val updatedDrug = Drug(
            id = primaryKey,
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
            subcategory_id = subcategory_id
        )
        return drugData.get(primaryKey)?.let {
            drugData.put(primaryKey, updatedDrug)
            1 // success
        } ?: -1 // nothing to update
    }


    override suspend fun getAllDrugs(): List<Drug> {
        return ArrayList(drugData.values)
    }

    override suspend fun searchDrugById(primaryKey: String): Drug? {
        return drugData.get(primaryKey)
    }

    override suspend fun getNumDrugs(categoryId: String?, subcategoryId: String?): Int {

        return 0
    }

    override suspend fun searchDrugs(
        query: String,
        categoryId: String?,
        subcategoryId: String?,
        filterAndOrder: OrderEnum,
        page: Int,
        pageSize: Int,
    ): List<Drug> {
        TODO("Not yet implemented")
    }


}