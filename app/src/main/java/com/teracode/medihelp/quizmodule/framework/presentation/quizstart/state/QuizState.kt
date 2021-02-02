package com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class QuizState : Parcelable {

    @Parcelize
    class Initial:QuizState(),Parcelable{
        override fun toString(): String {
            return "Initial"
        }
    }
    @Parcelize
    class Started:QuizState(),Parcelable{
        override fun toString(): String {
            return "Started"
        }
    }

    @Parcelize
    class Completed:QuizState(),Parcelable{
        override fun toString(): String {
            return "Completed"
        }
    }

}