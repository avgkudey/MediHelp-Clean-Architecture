package com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.ViewState
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizState.Initial
import kotlinx.android.parcel.Parcelize

@Parcelize
class QuizStartViewState(
    var quiz: Quiz? = null,
    var quizId: String? = null,
    var selectedQuestion: Question? = null,
    var canAnswer: Boolean = false,
    var showNextBtn: Boolean = false,
    var questionLoaded: Boolean = false,
    var quizState: QuizState? = Initial(),
    var questionList: ArrayList<Question>? = null,
    var layoutManagerState: Parcelable? = null,
) : ViewState, Parcelable {
}