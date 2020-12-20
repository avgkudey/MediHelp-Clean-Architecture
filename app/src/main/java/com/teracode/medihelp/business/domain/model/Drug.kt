package com.teracode.medihelp.business.domain.model

import android.os.Parcelable
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
}