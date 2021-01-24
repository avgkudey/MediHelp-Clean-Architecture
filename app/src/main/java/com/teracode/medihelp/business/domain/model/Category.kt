package com.teracode.medihelp.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(

    val id: String,
    val title: String,
    val image: String?,
    val url: String?,
    val description: String?,
    val drugCount: Int = 0,
    val subcategoryCount: Int = 0,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category
        if (id != other.id) return false
        if (title != other.title) return false
        if (image != other.image) return false
        if (url != other.url) return false
        if (description != other.description) return false
        return true
    }


    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}
