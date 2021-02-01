package com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.ViewState
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import kotlinx.android.parcel.Parcelize

@Parcelize
class QuizDetailViewState(
    var quiz: Quiz? = null,
    var quizId: String? = null,
    var layoutManagerState: Parcelable? = null,
): ViewState,Parcelable {
}