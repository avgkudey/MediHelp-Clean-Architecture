package com.teracode.medihelp.quizmodule.framework.presentation.quizstart

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.databinding.FragmentQuizStartBinding
import com.teracode.medihelp.framework.presentation.common.*
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizStartViewState
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizState
import com.teracode.medihelp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


const val START_QUIZ_SELECTED_QUIZ_BUNDLE_KEY =
    "com.teracode.medihelp.quizmodule.framework.presentation.quizstart.selectedquiz"

const val QUIZ_START_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class QuizStartFragment : BaseFragment<FragmentQuizStartBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQuizStartBinding =
        FragmentQuizStartBinding::inflate

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

                showNextBtn(viewState.showNextBtn)
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
            binding.quizStartOptionOne.setOnClickListener {
                selectAnswer(binding.quizStartOptionOne)
            }

            binding.quizStartOptionTwo.setOnClickListener {
                selectAnswer(binding.quizStartOptionTwo)
            }

            binding.quizStartOptionThree.setOnClickListener {
                selectAnswer(binding.quizStartOptionThree)
            }

            binding.quizStartOptionFour.setOnClickListener {
                selectAnswer(binding.quizStartOptionFour)
            }

            binding.quizStartNextBtn.setOnClickListener {
                questionUiVisibility(false)
                viewModel.loadNextQuestion()
            }


        }
    }

    private fun detachButtonListeners() {
        binding.quizStartOptionOne.setOnClickListener { }

        binding.quizStartOptionTwo.setOnClickListener { }

        binding.quizStartOptionThree.setOnClickListener { }

        binding.quizStartOptionFour.setOnClickListener { }

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
        countdownTimer.cancel()
        currentMillis = 0
    }

    fun pauseTimer() {
        countdownTimer.cancel()
    }


    fun resumeTimer() {
        //if (currentMillis > 0) {
//            startTimer(currentMillis)

//        }
    }


    //    UI Controller
    private fun setQuestionData(question: Question?) {

        question?.let {

            binding.quizStartQuestion.text = it.question
            binding.quizStartQuestionNumber.text = (viewModel.findCurrentQuestionIndex() + 1).toString()

            binding.quizStartOptionOne.text = it.option_a
            binding.quizStartOptionTwo.text = it.option_b
            binding.quizStartOptionThree.text = it.option_c
            binding.quizStartOptionFour.text = it.option_d

            binding.quizStartQuestionTime.text = it.timer.toString()

            questionUiVisibility(true)
            startTimer(question)
        }
    }

    private fun markRightAnswer(rightAnswer: String) {
        when (rightAnswer) {
            binding.quizStartOptionOne.text -> {
                binding.quizStartOptionOne.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
            binding.quizStartOptionTwo.text -> {
                binding.quizStartOptionTwo.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
            binding.quizStartOptionThree.text -> {
                binding.quizStartOptionThree.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
            binding.quizStartOptionFour.text -> {
                binding.quizStartOptionFour.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.mark_answer_btn_bg, null)
            }
        }
    }

    private fun showNextBtn(value: Boolean = true) {
        binding.quizStartNextBtn.visibleOrGone(value)
    }

    private fun setQuizData(quiz: Quiz) {
        binding.quizStartTitle.text = quiz.name
    }

    private fun setProgressBar(value: Int) {
        binding.quizStartQuestionProgress.progress = value
    }

    private fun showQuizCompleteBtn(value: Boolean) {
        binding.quizStartResultBtn.visibleOrGone(value)
        binding.quizStartRestartBtn.visibleOrGone(value)
    }

    private fun setTimeOut(value: Long) {
        binding.quizStartQuestionTime.text = value.toString()
    }

    private fun questionUiVisibility(show: Boolean = false) {

        binding.quizStartQuestionNumber.visibleOrInvisible(show)

        binding.quizStartQuestion.visibleOrInvisible(show)
        binding.quizStartOptionOne.visibleOrInvisible(show)
        binding.quizStartOptionTwo.visibleOrInvisible(show)
        binding.quizStartOptionThree.visibleOrInvisible(show)
        binding.quizStartOptionFour.visibleOrInvisible(show)
        binding.quizStartQuestionTime.visibleOrInvisible(show)
        binding.quizStartQuestionProgress.visibleOrInvisible(show)




        binding.quizStartOptionOne.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)
        binding.quizStartOptionTwo.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)
        binding.quizStartOptionThree.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)
        binding.quizStartOptionFour.background =
            ResourcesCompat.getDrawable(resources, R.drawable.outline_light_btn_bg, null)




        context?.let {
            binding.quizStartOptionOne.setTextColor(ContextCompat.getColor(it,
                R.color.colorLightText))
            binding.quizStartOptionTwo.setTextColor(ContextCompat.getColor(it,
                R.color.colorLightText))
            binding.quizStartOptionThree.setTextColor(ContextCompat.getColor(it,
                R.color.colorLightText))
            binding.quizStartOptionFour.setTextColor(ContextCompat.getColor(it,
                R.color.colorLightText))


        }
    }

}