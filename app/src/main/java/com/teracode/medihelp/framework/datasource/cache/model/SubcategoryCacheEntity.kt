package com.teracode.medihelp.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subcategories")
data class SubcategoryCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "categoryId")
    var categoryId: String,

    @ColumnInfo(name = "image")
    var image: String?,

    @ColumnInfo(name = "url")
    var url: String?,

    @ColumnInfo(name = "description")
    var description: String?


) {


    companion object {

        fun nullTitleError(): String {
            return "You must enter a title."
        }

        fun nullIdError(): String {
            return "CategoryEntity object has a null id. This should not be possible. Check local database."
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubcategoryCacheEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (categoryId != other.categoryId) return false
        if (image != other.image) return false
        if (url != other.url) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + categoryId.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}