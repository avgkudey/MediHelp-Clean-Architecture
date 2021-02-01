package com.teracode.medihelp.quizmodule.framework.presentation.quizlist.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.ViewState
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import kotlinx.android.parcel.Parcelize

@Parcelize
class QuizListViewState(
    var quizList: ArrayList<Quiz>? = null,
    var layoutManagerState: Parcelable? = null,
    var numQuizzesInCache: Int? = null
): ViewState,Parcelable {
}