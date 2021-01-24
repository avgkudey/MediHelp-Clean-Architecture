package com.teracode.medihelp.quizmodule.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "quizId")
    val quizId: String,

    @ColumnInfo(name = "question")
    val question: String,

    @ColumnInfo(name = "answer")
    val answer: String?,

    @ColumnInfo(name = "option_a")
    val option_a: String?,

    @ColumnInfo(name = "option_b")
    val option_b: String?,

    @ColumnInfo(name = "option_c")
    val option_c: String?,

    @ColumnInfo(name = "option_d")
    val option_d: String?,

    @ColumnInfo(name = "timer")
    val timer: Long = 0,

    ) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionCacheEntity
        if (id != other.id) return false
        if (quizId != other.quizId) return false
        if (question != other.question) return false
        if (answer != other.answer) return false
        if (option_a != other.option_a) return false
        if (option_b != other.option_b) return false
        if (option_c != other.option_c) return false
        if (option_d != other.option_d) return false
        if (timer != other.timer) return false
        return true
    }


    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + quizId.hashCode()
        result = 31 * result + question.hashCode()
        result = 31 * result + answer.hashCode()
        result = 31 * result + option_a.hashCode()
        result = 31 * result + option_b.hashCode()
        result = 31 * result + option_c.hashCode()
        result = 31 * result + option_d.hashCode()
        result = 31 * result + timer.hashCode()
        return result
    }
}
