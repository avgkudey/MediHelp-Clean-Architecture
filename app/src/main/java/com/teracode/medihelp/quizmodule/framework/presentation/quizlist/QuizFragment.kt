package com.teracode.medihelp.quizmodule.framework.presentation.quizlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.databinding.FragmentQuizBinding
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.common.SpacesItemDecoration
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.presentation.quizlist.state.QuizListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

const val QUIZ_LIST_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.quizmodule.framework.presentation.quizlist.state"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>(), QuizAdapter.ItemInteraction {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQuizBinding =
        FragmentQuizBinding::inflate

    private val viewModel: QuizViewModel by viewModels()

    private var listAdapter: QuizAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerView()
        subscribeObservers()
//        initData()

        restoreInstanceState(savedInstanceState)


    }


    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.clearList()
        viewModel.refreshData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        listAdapter = null
    }

    override fun onSaveInstanceState(outState: Bundle) {

        val viewState = viewModel.viewState.value
        viewState?.quizList = ArrayList()
        outState.putParcelable(
            QUIZ_LIST_STATE_BUNDLE_KEY,
            viewState
        )



        super.onSaveInstanceState(outState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { inState ->
            (inState[QUIZ_LIST_STATE_BUNDLE_KEY] as QuizListViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    private fun setupUI() {
        view?.hideKeyboard()
    }

    private fun setupRecyclerView() {
        binding.quizFragmentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            listAdapter = QuizAdapter(
                lifecycleOwner = viewLifecycleOwner,
                itemInteraction = this@QuizFragment,
            )
            addItemDecoration(SpacesItemDecoration(8))
            adapter = listAdapter

        }
    }


    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->

            if (viewState != null) {
                viewState.quizList?.let { list ->
                    listAdapter?.submitList(list)
                    listAdapter?.notifyDataSetChanged()
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


    override fun onItemClicked(position: Int, item: Quiz) {

        val action = QuizFragmentDirections.actionQuizFragmentToQuizDetailFragment(quizId = item.id)
        findNavController().navigate(action)

    }

    override fun onItemLongPress(position: Int, item: Quiz) {
    }

    override fun restoreListPosition() {

        viewModel.getLayoutManagerState()?.let { lmState ->
            binding.quizFragmentRecyclerView.layoutManager?.onRestoreInstanceState(lmState)
        }
    }

    private fun saveLayoutManagerState() {
        binding.quizFragmentRecyclerView.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }
}