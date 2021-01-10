package com.teracode.medihelp.framework.presentation.druglist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_drug_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

const val DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY = "selectedCategory"
const val DRUG_LIST_SELECTED_SUBCATEGORY_BUNDLE_KEY = "selectedSubcategory"
const val DRUG_LIST_STATE_BUNDLE_KEY = "com.teracode.medihelp.framework.presentation.druglist.state"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class DrugListFragment : BaseFragment(R.layout.fragment_drug_list), ItemTouchHelperAdapter,
    DrugListAdapter.Interaction {

    val viewModel: DrugListViewModel by viewModels()
    private var listAdapter: DrugListAdapter? = null
    private var itemTouchHelper: ItemTouchHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    private fun clearArgs() {
        arguments?.clear()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerView()

        setupSwipeRefresh()
        subscribeObservers()

        restoreInstanceState(savedInstanceState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {

        savedInstanceState?.let { inState ->
            (inState[DRUG_LIST_STATE_BUNDLE_KEY] as DrugListViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }

        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        viewState?.drugList = ArrayList()
        outState.putParcelable(
            DRUG_LIST_STATE_BUNDLE_KEY,
            viewState
        )


        super.onSaveInstanceState(outState)
    }

    private fun getSelectedCategoryBundle() {
        arguments?.let { args ->
            (args.getParcelable(DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY) as Category?)?.let { selectedCategory ->
                viewModel.setCategory(selectedCategory)
            } ?: errorRetrievingBundle()

        }
    }

    private fun getSelectedSubcategoryBundle() {
        arguments?.let { args ->
            (args.getParcelable(DRUG_LIST_SELECTED_SUBCATEGORY_BUNDLE_KEY) as Subcategory?)?.let { selectedSubcategory ->
                viewModel.setSubcategory(selectedSubcategory)
            }
        }
    }

    private fun errorRetrievingBundle() {

    }

    private fun setupSwipeRefresh() {

        drug_list_swipe_refresh.setOnRefreshListener {

            startNewSearch()
            drug_list_swipe_refresh.isRefreshing = false
        }

    }

    private fun startNewSearch() {
        viewModel.clearList()
        viewModel.loadFirstPage()

    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->

            if (viewState != null) {
                viewState.drugList?.let { drugList ->
                    Log.d("DRUGLISTFRAC", "subscribeObservers: drugList ${drugList}")
                    Log.d(
                        "DRUGLISTFRAC",
                        "subscribeObservers: isPaginationExhausted ${viewModel.isPaginationExhausted()}"
                    )
                    Log.d(
                        "DRUGLISTFRAC",
                        "subscribeObservers: getDrugListSize ${viewModel.getDrugListSize()}"
                    )
                    Log.d(
                        "DRUGLISTFRAC",
                        "subscribeObservers: getNumDrugsInCache ${viewModel.getNumDrugsInCache()}"
                    )

                    if (drugList.isNotEmpty() && viewModel.isPaginationExhausted() && !viewModel.isQueryExhausted()) {
                        Log.d(
                            "DRUGLISTFRAC",
                            "subscribeObservers: isQueryExhausted ${viewModel.isQueryExhausted()}"
                        )
                        viewModel.setQueryExhausted(true)
                        Log.d(
                            "DRUGLISTFRAC",
                            "subscribeObservers: viewModel.setQueryExhausted(true)"
                        )
                    }


                    listAdapter?.submitList(drugList)
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
        viewModel.retrieveNumDrugsInCache()
        viewModel.clearList()
        viewModel.refreshSearchQuery()


    }

    private fun setupRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            itemTouchHelper = ItemTouchHelper(
                DrugItemTouchHelperCallback(
                    this@DrugListFragment
                )
            )
            listAdapter = DrugListAdapter(
                interaction = this@DrugListFragment,
                lifecycleOwner = viewLifecycleOwner,

                )

            itemTouchHelper?.attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()

                    Log.d("DRUGLISTFRAC", "listAdapter?.itemCount ${listAdapter?.itemCount}")
                    Log.d("DRUGLISTFRAC", "lastPosition ${lastPosition}")

                    if (lastPosition == listAdapter?.itemCount?.minus(1)) {
                        viewModel.nextPage()
                    }
                }
            })
            adapter = listAdapter
        }
    }


    private fun setupUI() {
        view?.hideKeyboard()
    }

    override fun onItemSwiped(position: Int) {
    }

    override fun onItemSelected(position: Int, item: Drug) {
    }

    override fun restoreListPosition() {
        viewModel.getLayoutManagerState()?.let { lmState ->
            recycler_view?.layoutManager?.onRestoreInstanceState(lmState)
        }

    }

    override fun isMultiSelectionModeEnabled(): Boolean {
        return false
    }

    override fun activateMultiSelectionMode() {
    }

    override fun isNoteSelected(note: Drug): Boolean {
        return false
    }


    private fun saveLayoutManagerState() {
        recycler_view.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

}