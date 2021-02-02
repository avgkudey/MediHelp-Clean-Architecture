package com.teracode.medihelp.business.domain.model

import com.teracode.medihelp.business.domain.util.DateUtil
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class DrugFactory
 {

    fun createSingleDrug(
        id: String? = null,
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
    ): Drug {
        return Drug(
            id = id ?: UUID.randomUUID().toString(),
            title = title,
            trade_name = trade_name ?: UUID.randomUUID().toString(),
            pharmacological_name = pharmacological_name ?: UUID.randomUUID().toString(),
            action = action ?: UUID.randomUUID().toString(),
            antidote = antidote ?: UUID.randomUUID().toString(),
            cautions = cautions ?: UUID.randomUUID().toString(),
            contraindication = contraindication ?: UUID.randomUUID().toString(),
            dosages = dosages ?: UUID.randomUUID().toString(),
            indications = indications ?: UUID.randomUUID().toString(),
            maximum_dose = maximum_dose ?: UUID.randomUUID().toString(),
            nursing_implications = nursing_implications ?: UUID.randomUUID().toString(),
            notes = notes ?: UUID.randomUUID().toString(),
            preparation = preparation ?: UUID.randomUUID().toString(),
            side_effects = side_effects ?: UUID.randomUUID().toString(),
            video = video ?: UUID.randomUUID().toString(),
            category_id = category_id ?: UUID.randomUUID().toString(),
            subcategory_id = subcategory_id ?: UUID.randomUUID().toString()

        )
    }


    fun createDrugList(numDrugs: Int): List<Drug> {
        val list: ArrayList<Drug> = ArrayList()

        for (i in 0 until numDrugs) {
            list.add(
                createSingleDrug(title = UUID.randomUUID().toString())
            )
        }

        return list
    }
}