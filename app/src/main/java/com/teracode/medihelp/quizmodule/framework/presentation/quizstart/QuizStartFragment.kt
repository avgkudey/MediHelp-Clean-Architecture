package com.teracode.medihelp.quizmodule.framework.presentation.quizstart

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.framework.presentation.common.*
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizStartViewState
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizState
import com.teracode.medihelp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_start.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


const val START_QUIZ_SELECTED_QUIZ_BUNDLE_KEY =
    "com.teracode.medihelp.quizmodule.framework.presentation.quizstart.selectedquiz"

const val QUIZ_START_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class QuizStartFragment : BaseFragment(R.layout.fragment_quiz_start) {

    private var canAnswer: Boolean = false
    private val viewModel: QuizStartViewModel by viewModels()
    private var currentMillis: Long = 0L

    private lateinit var countdownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSelectedQuizBundle()
        setupUI()


        subscribeObservers()

        questionUiVisibility()
        restoreInstanceState(savedInstanceState)
    }


    private fun getSelectedQuizBundle() {
        arguments?.let { args ->
            (args.getParcelable(START_QUIZ_SELECTED_QUIZ_BUNDLE_KEY) as Quiz?)?.let { quiz ->
                viewModel.setQuiz(quiz)
            } ?: errorRetrievingBundle()
        }
    }


    private fun errorRetrievingBundle() {
    }


    private fun subscribeObservers() {


        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->

            if (viewState != null) {
                viewState.questionList?.let { list ->

                    if (viewModel.isQuizInitialState()) {
                        viewModel.loadFirstQuestion()
                    }
                }

                viewState.quiz?.let { quiz ->
                    setQuizData(quiz)
                }

                viewState.showNextBtn.let { value ->

                    showNextBtn(value)

                }
                viewState.canAnswer.let { value ->
                    canAnswer = value
                }
                viewState.selectedQuestion.let { question ->

                    if (!viewModel.isQuestionLoaded()) {

                        setQuestion(question)
                    }
                }

                viewState.quizState.let { state ->
                    if (state is QuizState.Completed) {
                        showQuizCompleteBtn(true)
                    } else {
                        showQuizCompleteBtn(false)
                    }
                }
            }

        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, {
            uiController.displayProgressBar(it)
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, { stateMessage ->
            stateMessage?.let { message ->

                uiController.onResponseReceived(
                    response = message.response,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )

            }
        })


    }

    private fun setQuestion(question: Question?) {

        if (question != null) {
            viewModel.setQuestionLoaded()
            setupButtonListeners()
            setQuestionData(question)

        } else {
            detachButtonListeners()
        }
    }


    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshSearchQuery()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopTimer()

    }

    override fun onSaveInstanceState(outState: Bundle) {

        val viewState = viewModel.viewState.value
        outState.putParcelable(
            QUIZ_START_STATE_BUNDLE_KEY,
            viewState
        )



        super.onSaveInstanceState(outState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { inState ->
            (inState[QUIZ_START_STATE_BUNDLE_KEY] as QuizStartViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    private fun setupUI() {
        view?.hideKeyboard()
    }

    private fun saveLayoutManagerState() {

    }


    private fun setupButtonListeners() {
        if (viewModel.getCurrentViewStateOrNew().canAnswer) {


            printLogD("setupButtonListeners", "setupButtonListeners")
            quiz_start_option_one.setOnClickListener {
                selectAnswer(quiz_start_option_one)
            }

            quiz_start_option_two.setOnClickListener {
                selectAnswer(quiz_start_option_two)
            }

            quiz_start_option_three.setOnClickListener {
                selectAnswer(quiz_start_option_three)
            }

            quiz_start_option_four.setOnClickListener {
                selectAnswer(quiz_start_option_four)
            }

            quiz_start_next_btn.setOnClickListener {
                questionUiVisibility(false)
                viewModel.loadNextQuestion()
            }


        }
    }

    private fun detachButtonListeners() {
        quiz_start_option_one.setOnClickListener { }

        quiz_start_option_two.setOnClickListener { }

        quiz_start_option_three.setOnClickListener { }

        quiz_start_option_four.setOnClickListener { }

    }

    private fun selectAnswer(answerBtn: Button) {
        if (canAnswer) {

            context?.let {
                answerBtn.setTextColor(ContextCompat.getColor(it, R.color.colorDark))

            }


            if (verifyAnswer(answerBtn.text.toString())) {
                answerBtn.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.correct_answer_btn_bg, null)
            } else {
                answerBtn.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.wrong_answer_btn_bg, null)


                markRightAnswer(viewModel.getRightAnswer())
            }
            detachButtonListeners()
            viewModel.setCanAnswer(false)
            stopTimer()
            viewModel.showNextBtn()
        }
    }


    private fun verifyAnswer(answer: String): Boolean {

        return viewModel.verifyAnswer(answer)
    }


    private fun startTimer(question: Question, startAt: Long? = 0) {


        var timeToAnswer: Long = question.timer

        if (startAt!! > 0) {
            timeToAnswer = currentMillis / 1000
        }

        countdownTimer = object : CountDownTimer(timeToAnswer * 1000, 10) {
            override fun onTick(millisUntilFinished: Long) {
                currentMillis = millisUntilFinished

                val progress: Int =
                    (millisUntilFinished / (question.timer * 10)).toInt()
                setProgressBar(progress)
                setTimeOut(millisUntilFinished / 1000)


            }

            override fun onFinish() {
                viewModel.setCanAnswer(false)
                viewModel.showNextBtn()
            }
        }
        countdownTimer.start()

    }


    private fun stopTimer() {
        countdownTimer?.cancel()
        currentMillis = 0
    }

    fun pauseTimer() {
        countdownTimer.cancel()
    }


    fun resumeTimer() {
        if (currentMillis > 0) {
//            startTimer(currentMillis)

        }
    }


    //    UI Controller
    private fun setQuestionData(question: Question?) {

        question?.let {

            quiz_start_question.text = it.question
            quiz_start_question_number.text = (viewModel.findCurrentQuestionIndex() + 1).toString()

            quiz_start_option_one.text = it.option_a
            quiz_start_option_two.text = it.option_b
            quiz_start_option_three.text = it.option_c
            quiz_start_option_four.text = it.option_d

            quiz_start_question_time.text = it.timer.toString()

            questionUiVisibility(true)
            startTimer(question)
        }
    }

    private fun markRightAnswer(rightAnswer: String) {
        when (rightAnswer) {
            quiz_start_option_one.text -> {
                quiz_start_option_one.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
            quiz_start_option_two.text -> {
                quiz_start_option_two.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
            quiz_start_option_three.text -> {
                quiz_start_option_three.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
            quiz_start_option_four.text -> {
                quiz_start_option_four.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
        }
    }

    private fun showNextBtn(value: Boolean = true) {
        quiz_start_next_btn.visibleOrGone(value)
    }

    private fun setQuizData(quiz: Quiz) {
        quiz_start_title.text = quiz.name
    }

    private fun setProgressBar(value: Int) {
        quiz_start_question_progress.progress = value
    }

    private fun showQuizCompleteBtn(value: Boolean) {
        quiz_start_result_btn.visibleOrGone(value)
        quiz_start_restart_btn.visibleOrGone(value)
    }

    private fun setTimeOut(value: Long) {
        quiz_start_question_time.text = value.toString()
    }

    private fun questionUiVisibility(show: Boolean = false) {

        quiz_start_question_number.visibleOrInvisible(show)

        quiz_start_question.visibleOrInvisible(show)
        quiz_start_option_one.visibleOrInvisible(show)
        quiz_start_option_two.visibleOrInvisible(show)
        quiz_start_option_three.visibleOrInvisible(show)
        quiz_start_option_four.visibleOrInvisible(show)
        quiz_start_question_time.visibleOrInvisible(show)
        quiz_start_question_progress.visibleOrInvisible(show)




        quiz_start_option_one.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)
        quiz_start_option_two.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)
        quiz_start_option_three.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)
        quiz_start_option_four.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)




        context?.let {
            quiz_start_option_one.setTextColor(ContextCompat.getColor(it, R.color.colorLightText))
            quiz_start_option_two.setTextColor(ContextCompat.getColor(it, R.color.colorLightText))
            quiz_start_option_three.setTextColor(ContextCompat.getColor(it, R.color.colorLightText))
            quiz_start_option_four.setTextColor(ContextCompat.getColor(it, R.color.colorLightText))


        }
    }

}