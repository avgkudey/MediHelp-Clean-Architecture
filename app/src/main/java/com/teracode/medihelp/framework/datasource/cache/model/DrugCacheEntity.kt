package com.teracode.medihelp.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drugs")
data class DrugCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "trade_name")
    var trade_name: String?,

    @ColumnInfo(name = "pharmacological_name")
    var pharmacological_name: String?,

    @ColumnInfo(name = "actions")
    var action: String?,

    @ColumnInfo(name = "antidote")
    var antidote: String?,

    @ColumnInfo(name = "cautions")
    var cautions: String?,

    @ColumnInfo(name = "contraindication")
    var contraindication: String?,

    @ColumnInfo(name = "dosages")
    var dosages: String?,

    @ColumnInfo(name = "indications")
    var indications: String?,

    @ColumnInfo(name = "maximum_dose")
    var maximum_dose: String?,

    @ColumnInfo(name = "nursing_implications")
    var nursing_implications: String?,

    @ColumnInfo(name = "notes")
    var notes: String?,

    @ColumnInfo(name = "preparation")
    var preparation: String?,

    @ColumnInfo(name = "side_effects")
    var side_effects: String?,

    @ColumnInfo(name = "video")
    var video: String?,

    @ColumnInfo(name = "category_id")
    var category_id: String?,

    @ColumnInfo(name = "subcategory_id")
    var subcategory_id: String?
) {


    companion object {

        fun nullTitleError(): String {
            return "You must enter a title."
        }

        fun nullIdError(): String {
            return "DrugEntity object has a null id. This should not be possible. Check local database."
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DrugCacheEntity

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