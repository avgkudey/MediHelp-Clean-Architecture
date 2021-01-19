package com.teracode.medihelp.framework.presentation.subcategorylist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.framework.presentation.druglist.DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY
import com.teracode.medihelp.framework.presentation.druglist.DRUG_LIST_SELECTED_SUBCATEGORY_BUNDLE_KEY
import com.teracode.medihelp.framework.presentation.subcategorylist.state.SubcategoryListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_drug_list.*
import kotlinx.android.synthetic.main.fragment_subcategory.*
import kotlinx.android.synthetic.main.fragment_subcategory.recycler_view
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.tasks.await


const val SUBCATEGORY_LIST_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.framework.presentation.subcategorylist.state"

const val SUBCATEGORY_LIST_SELECTED_CATEGORY_BUNDLE_KEY =
    "com.teracode.medihelp.framework.presentation.subcategorylist.selectedCategory"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SubcategoryFragment : BaseFragment(R.layout.fragment_subcategory),
    SubcategoryListAdapter.Interaction {

    private val viewModel: SubcategoryViewModel by viewModels()

    private var listAdapter: SubcategoryListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setupChannel()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSelectedCategoryBundle()
        setupUI()
        setupRecyclerView()

        setupSwipeRefresh()
        subscribeObservers()

        restoreInstanceState(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        listAdapter = null

    }


    private fun getSelectedCategoryBundle() {
        arguments?.let { args ->
            (args.getParcelable(SUBCATEGORY_LIST_SELECTED_CATEGORY_BUNDLE_KEY) as Category?)?.let { selectedCategory ->
                viewModel.setCategory(selectedCategory)
            } ?: errorRetrievingBundle()

        }
    }


    private fun errorRetrievingBundle() {

    }


    override fun onItemSelected(position: Int, item: Subcategory) {

        val bundle = bundleOf(
            DRUG_LIST_SELECTED_SUBCATEGORY_BUNDLE_KEY to item,
            DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY to viewModel.getCurrentViewStateOrNew().category
        )

        findNavController().navigate(
            R.id.action_subcategoryFragment_to_drugListFragment,
            bundle
        )

    }

    override fun restoreListPosition() {
        viewModel.getLayoutManagerState()?.let { lmState ->
            recycler_view?.layoutManager?.onRestoreInstanceState(lmState)
        }
    }


    private fun setupUI() {
        view?.hideKeyboard()
    }

    private fun setupRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)

            listAdapter = SubcategoryListAdapter(
                interaction = this@SubcategoryFragment,
                lifecycleOwner = viewLifecycleOwner,

                )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()

                    if (lastPosition == listAdapter?.itemCount?.minus(1)) {
                        viewModel.nextPage()
                    }
                }
            })
            adapter = listAdapter
        }
    }

    private fun setupSwipeRefresh() {

//        drug_list_swipe_refresh.setOnRefreshListener {
//
//            startNewSearch()
//            drug_list_swipe_refresh.isRefreshing = false
//        }

    }


    private fun startNewSearch() {
        viewModel.clearList()
        viewModel.loadFirstPage()

    }


    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->

            if (viewState != null) {
                viewState.subcategoryList?.let { subcategoryList ->

                    if (subcategoryList.isNotEmpty() && viewModel.isPaginationExhausted() && !viewModel.isQueryExhausted()) {
                        viewModel.setQueryExhausted(true)
                    }


                    listAdapter?.submitList(subcategoryList)
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


    override fun onResume() {
        super.onResume()
        viewModel.retrieveNumSubcategoriesInCache()
        viewModel.clearList()
        viewModel.refreshSearchQuery()


    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {

        savedInstanceState?.let { inState ->
            (inState[SUBCATEGORY_LIST_STATE_BUNDLE_KEY] as SubcategoryListViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }

        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        viewState?.subcategoryList = ArrayList()
        outState.putParcelable(
            SUBCATEGORY_LIST_STATE_BUNDLE_KEY,
            viewState
        )


        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    private fun saveLayoutManagerState() {
        recycler_view.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }


}