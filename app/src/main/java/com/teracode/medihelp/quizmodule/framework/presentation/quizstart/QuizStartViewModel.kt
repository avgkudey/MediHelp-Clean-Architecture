package com.teracode.medihelp.quizmodule.framework.presentation.quizstart

import android.os.CountDownTimer
import android.os.Parcelable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.business.interactors.quizstart.QuizStartInteractors
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizStartStateEvent
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizStartViewState
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizState
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class QuizStartViewModel
@ViewModelInject
constructor(
    private val quizStartInteractors: QuizStartInteractors,
) : BaseViewModel<QuizStartViewState>() {
    private var showNextBtn: MutableLiveData<Boolean> = MutableLiveData()


    fun showNextBtn() {
        getCurrentViewStateOrNew().questionList?.let { list ->

            val index = findCurrentQuestionIndex()

            printLogD("setupButtonListeners", "index $index")
            if (index != -1) {
                if (index + 1 == list.size) {
                    setQuizState(QuizState.Completed())
                    setShowNextBtn(false)
//                showResultMsg.value = true
                } else {
                    setShowNextBtn(true)
                }
            }

        }


    }


    fun findCurrentQuestionIndex(): Int {
        return getCurrentViewStateOrNew().questionList?.indexOf(getCurrentViewStateOrNew().selectedQuestion)
            ?: -1
    }

    override fun handleNewData(data: QuizStartViewState) {
        data.let { viewState ->

            viewState.questionList?.let { questions ->
                setQuestions(questions)
            }

        }
    }

    private fun setQuestions(questions: ArrayList<Question>) {
        val update = getCurrentViewStateOrNew()

        var updatedQuestions = questions
        if (update.quizState is QuizState.Initial) {
            updatedQuestions = ArrayList(questions.shuffled())
        }


        update.questionList = updatedQuestions
        setViewState(update)
    }

    fun setQuiz(quiz: Quiz) {
        val update = getCurrentViewStateOrNew()
        update.quiz = quiz
        setViewState(update)
        setQuizId(quizId = quiz.id)
    }


    private fun setQuizId(quizId: String?) {
        val update = getCurrentViewStateOrNew()
        update.quizId = quizId
        setViewState(update)
    }

    private fun setQuizState(quizState: QuizState) {
        val update = getCurrentViewStateOrNew()
        update.quizState = quizState
        setViewState(update)
    }

    private fun setShowNextBtn(value: Boolean) {
        val update = getCurrentViewStateOrNew()
        update.showNextBtn = value
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {


        val job: Flow<DataState<QuizStartViewState>?> = when (stateEvent) {

            is QuizStartStateEvent.GetQuestionsEvent -> {
                if (stateEvent.clearLayoutManagerState) {
                    clearLayoutManagerState()
                }

                quizStartInteractors.getQuestions.getQuestions(
                    getCurrentViewStateOrNew().quizId ?: "",
                    stateEvent
                )
            }


            is QuizStartStateEvent.CreateStateMessageEvent -> {
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

    override fun initNewViewState(): QuizStartViewState {
        return QuizStartViewState()
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
        setStateEvent(QuizStartStateEvent.GetQuestionsEvent())
    }

    fun clearQuiz() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        update.quiz = null
        setViewState(update)

    }

    fun setCanAnswer(b: Boolean) {
        val update = getCurrentViewStateOrNew()
        update.canAnswer = b
        setViewState(update)
    }

    fun loadFirstQuestion() {
//        if (getCurrentViewStateOrNew().quizState is QuizState.Initial) {
        val update = getCurrentViewStateOrNew()
        update.selectedQuestion = update.questionList?.get(0)
        update.canAnswer = true
        update.showNextBtn = false
        update.quizState = QuizState.Started()
        setViewState(update)
//        }
    }

    fun setQuestionLoaded(value: Boolean = true) {
        val update = getCurrentViewStateOrNew()
        update.questionLoaded = value
        setViewState(update)
    }

    fun isQuestionLoaded(): Boolean {
        return getCurrentViewStateOrNew().questionLoaded
    }

    fun isQuizStartedState(): Boolean {
        return getCurrentViewStateOrNew().quizState is QuizState.Started
    }

    fun isQuizInitialState(): Boolean {
        return getCurrentViewStateOrNew().quizState is QuizState.Initial
    }


    fun isLastQuestion(): Boolean {
        return getCurrentViewStateOrNew().questionList?.let { list ->
            findCurrentQuestionIndex() + 1 == list.size

        } ?: true
    }


    fun loadNextQuestion() {

        if (!isLastQuestion()) {

            printLogD("loadNextQuestion", "Question !isLastQuestion() ")
            val update = getCurrentViewStateOrNew()
            update.selectedQuestion = update.questionList?.get(findCurrentQuestionIndex() + 1)
            update.canAnswer = true
            update.questionLoaded = false
            update.showNextBtn = false
            printLogD("loadNextQuestion", "Question ${update.selectedQuestion} ")
            setViewState(update)
        }

    }


    fun verifyAnswer(answer: String): Boolean {
        val question = getCurrentViewStateOrNew().selectedQuestion!!
        return when (question.answer) {
            "option_a" -> {
                answer == question.option_a
            }
            "option_b" -> {
                answer == question.option_b
            }
            "option_c" -> {
                answer == question.option_c
            }
            "option_d" -> {
                answer == question.option_d
            }
            else -> false
        }

    }

    fun getRightAnswer(): String {
        val question = getCurrentViewStateOrNew().selectedQuestion!!
        return when (question.answer) {
            "option_a" -> {
                question.option_a!!
            }
            "option_b" -> {
                question.option_b!!
            }
            "option_c" -> {
                question.option_c!!
            }
            "option_d" -> {
                question.option_d!!
            }
            else -> ""
        }
    }

}