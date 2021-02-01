package com.teracode.medihelp.quizmodule.framework.presentation.quizdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.framework.presentation.subcategorylist.SUBCATEGORY_LIST_SELECTED_CATEGORY_BUNDLE_KEY
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.state.QuizDetailViewState
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.START_QUIZ_SELECTED_QUIZ_BUNDLE_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


const val QUIZ_DETAIL_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.state"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class QuizDetailFragment : BaseFragment(R.layout.fragment_quiz_detail) {
    private val args: QuizDetailFragmentArgs by navArgs()
    private val viewModel: QuizDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSelectedQuizIdBundle()

        setupUI()
        subscribeObservers()
        setupSwipeRefresh()
//        initData()

        restoreInstanceState(savedInstanceState)
    }

    private fun setupSwipeRefresh() {

        quiz_detail_swipe_container.setOnRefreshListener {
            quiz_detail_swipe_container.isRefreshing = false
            viewModel.refreshSearchQuery()
        }
    }

    private fun getSelectedQuizIdBundle() {
        args.let { arg ->
            viewModel.setQuizId(arg.quizId)
        }
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.clearQuiz()
        viewModel.refreshSearchQuery()

    }


    override fun onSaveInstanceState(outState: Bundle) {

        val viewState = viewModel.viewState.value
        viewState?.quiz = null
        outState.putParcelable(
            QUIZ_DETAIL_STATE_BUNDLE_KEY,
            viewState
        )



        super.onSaveInstanceState(outState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { inState ->
            (inState[QUIZ_DETAIL_STATE_BUNDLE_KEY] as QuizDetailViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    private fun setupUI() {
        view?.hideKeyboard()
    }


    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->

            if (viewState != null) {
                viewState.quiz?.let { quiz ->
                    setQuizDetails(quiz)
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

    private fun setQuizDetails(quiz: Quiz) {
        quiz_detail_title.text = quiz.name
        quiz_detail_description.text = quiz.description
        quiz_detail_difficulty_text.text = quiz.level
        quiz_detail_questions_text.text = quiz.questions.toString()
        context?.let {
            Glide.with(it).load("https://firebase.google.com/images/social.png")
                .placeholder(R.drawable.placeholder_image)
                .into(quiz_detail_image)
        }
        quiz_detail_start_btn.setOnClickListener {
            navToQuizStartFragment()

        }

    }

    private fun navToQuizStartFragment() {
        val bundle =
            bundleOf(START_QUIZ_SELECTED_QUIZ_BUNDLE_KEY to viewModel.getCurrentViewStateOrNew().quiz)
        findNavController().navigate(R.id.action_quizDetailFragment_to_quizStartFragment, bundle)
    }
}