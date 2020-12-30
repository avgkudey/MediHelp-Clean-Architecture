package com.teracode.medihelp.business.domain.model

import android.os.Parcelable
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Drug(
    val id: String,
    val title: String,
    val trade_name: String? = null,
    val pharmacological_name: String? = null,
    val action: String? = null,
    val antidote: String? = null,
    val cautions: String? = null,
    val contraindication: String? = null,
    val dosages: String? = null,
    val indications: String? = null,
    val maximum_dose: String? = null,
    val nursing_implications: String? = null,
    val notes: String? = null,
    val preparation: String? = null,
    val side_effects: String? = null,
    val video: String? = null,
    val category_id: String? = null,
    val subcategory_id: String? = null
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Drug

        if (id != other.id) return false
        if (title != other.title) return false
        if (trade_name != other.trade_name) return false
        if (pharmacological_name != other.pharmacological_name) return false
        if (action != other.action) return false
        if (antidote != other.antidote) return false
        if (cautions != other.cautions) return false
        if (contraindication != other.contraindication) return false
        if (dosages != other.dosages) return false
        if (indications != other.indications) return false
        if (maximum_dose != other.maximum_dose) return false
        if (nursing_implications != other.nursing_implications) return false
        if (notes != other.notes) return false
        if (preparation != other.preparation) return false
        if (side_effects != other.side_effects) return false
        if (video != other.video) return false
        if (category_id != other.category_id) return false
        if (subcategory_id != other.subcategory_id) return false
        return true
    }




    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + trade_name.hashCode()
        result = 31 * result + pharmacological_name.hashCode()
        result = 31 * result + action.hashCode()
        result = 31 * result + antidote.hashCode()
        result = 31 * result + cautions.hashCode()
        result = 31 * result + contraindication.hashCode()
        result = 31 * result + dosages.hashCode()
        result = 31 * result + indications.hashCode()
        result = 31 * result + maximum_dose.hashCode()
        result = 31 * result + nursing_implications.hashCode()
        result = 31 * result + notes.hashCode()
        result = 31 * result + preparation.hashCode()
        result = 31 * result + side_effects.hashCode()
        result = 31 * result + video.hashCode()
        result = 31 * result + category_id.hashCode()
        result = 31 * result + subcategory_id.hashCode()
        return result
    }
}