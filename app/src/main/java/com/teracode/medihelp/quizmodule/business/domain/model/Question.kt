package com.teracode.medihelp.quizmodule.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val id: String,
    val quizId: String,
    val question: String,
    val answer: String?,
    val option_a: String?,
    val option_b: String?,
    val option_c: String?,
    val option_d: String?,
    val timer: Long=0,

    ) : Parcelable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question
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
