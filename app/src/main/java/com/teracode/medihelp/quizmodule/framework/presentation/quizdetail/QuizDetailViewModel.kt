package com.teracode.medihelp.quizmodule.framework.presentation.quizdetail

import android.os.Parcelable
import androidx.hilt.lifecycle.ViewModelInject
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.business.interactors.quizdetail.QuizDetailInteractors
import com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.state.QuizDetailStateEvent
import com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.state.QuizDetailViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class QuizDetailViewModel
@ViewModelInject
constructor(
    private val quizDetailInteractors: QuizDetailInteractors,
) : BaseViewModel<QuizDetailViewState>() {
    override fun handleNewData(data: QuizDetailViewState) {
        data.let { viewState ->

            viewState.quiz?.let { quiz ->
                setQuiz(quiz)
            }

        }

    }

    private fun setQuiz(quiz: Quiz) {
        val update = getCurrentViewStateOrNew()
        update.quiz = quiz
        setViewState(update)
    }
     fun setQuizId(quizId:String?) {
        val update = getCurrentViewStateOrNew()
        update.quizId = quizId
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {


        val job: Flow<DataState<QuizDetailViewState>?> = when (stateEvent) {

            is QuizDetailStateEvent.GetQuizEvent -> {
                if (stateEvent.clearLayoutManagerState) {
                    clearLayoutManagerState()
                }

                quizDetailInteractors.getQuiz.getQuiz(
                    getCurrentViewStateOrNew().quizId ?: "",
                    stateEvent
                )
            }


            is QuizDetailStateEvent.CreateStateMessageEvent -> {
                emitStateMessageEvent(
                    stateMessage = stateEvent.stateMessage,
                    stateEvent = stateEvent
                )
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }

        }

        launchJob(stateEvent, job)

    }

    override fun initNewViewState(): QuizDetailViewState {
        return QuizDetailViewState()
    }


    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }

    fun refreshSearchQuery() {
        setStateEvent(QuizDetailStateEvent.GetQuizEvent())
    }

    fun clearQuiz() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        update.quiz = null
        setViewState(update)

    }
}