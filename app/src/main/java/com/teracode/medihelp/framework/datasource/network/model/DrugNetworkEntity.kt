package com.teracode.medihelp.framework.datasource.network.model

data class DrugNetworkEntity(
    var id: String,
    var title: String,
    var trade_name: String?,
    var pharmacological_name: String?,
    var action: String?,
    var antidote: String?,
    var cautions: String?,
    var contraindication: String?,
    var dosages: String?,
    var indications: String?,
    var maximum_dose: String?,
    var nursing_implications: String?,
    var notes: String?,
    var preparation: String?,
    var side_effects: String?,
    var video: String?,
    var category_id: String?,
    var subcategory_id: String?
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
}
