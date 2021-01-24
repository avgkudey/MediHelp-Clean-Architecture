package com.teracode.medihelp.quizmodule.framework.datasource.cache.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "quizzes")
data class QuizCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "level")
    val level: String?,

    @ColumnInfo(name = "visibility")
    val visibility: String?,

    @ColumnInfo(name = "questions")
    val questions: Long,

    ) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuizCacheEntity
        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
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
