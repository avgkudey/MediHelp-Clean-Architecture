package com.teracode.medihelp.quizmodule.business.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Quiz(
    val id: String,
    val name: String,
    val description: String?,
    val image: String?,
    val level: String?,
    val visibility: String?,
    val questions: Long,

    ) : Parcelable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Quiz
        if (id != other.id) return false
        if (name != other.name) return false
        if (image != other.image) return false
        if (level != other.level) return false
        if (visibility != other.visibility) return false
        if (questions != other.questions) return false
        return true
    }


    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + level.hashCode()
        result = 31 * result + visibility.hashCode()
        result = 31 * result + questions.hashCode()
        return result
    }
}
