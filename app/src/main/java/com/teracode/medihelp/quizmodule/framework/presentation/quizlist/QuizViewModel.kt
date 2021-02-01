package com.teracode.medihelp.quizmodule.framework.presentation.quizlist

import android.os.Parcelable
import androidx.hilt.lifecycle.ViewModelInject
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.business.interactors.quizlist.QuizListInteractors
import com.teracode.medihelp.quizmodule.framework.presentation.quizlist.state.QuizListStateEvent
import com.teracode.medihelp.quizmodule.framework.presentation.quizlist.state.QuizListViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class QuizViewModel
@ViewModelInject
constructor(
    private val quizListInteractors: QuizListInteractors,
) : BaseViewModel<QuizListViewState>() {
    override fun handleNewData(data: QuizListViewState) {
        data.let { viewState ->

            viewState.quizList?.let { quizzes ->
                setQuizData(quizzes)
            }
        }
    }

    private fun setQuizData(quizzes: ArrayList<Quiz>) {
        val update = getCurrentViewStateOrNew()
        update.quizList = quizzes
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<QuizListViewState>?> = when (stateEvent) {

            is QuizListStateEvent.GetQuizzesEvent -> {
                if (stateEvent.clearLayoutManagerState) {
                    clearLayoutManagerState()
                }

                quizListInteractors.getQuizzes.getQuizzes(stateEvent = stateEvent)

            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }

        }

        launchJob(stateEvent = stateEvent, jobFunction = job)

    }

    override fun initNewViewState(): QuizListViewState {
        return QuizListViewState()
    }

    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }
    fun clearList() {
        val update = getCurrentViewStateOrNew()
        update.quizList = java.util.ArrayList()
        setViewState(update)
    }
    fun refreshData() {

        setStateEvent(QuizListStateEvent.GetQuizzesEvent(false))
    }

    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

}