package com.teracode.medihelp.framework.presentation.drugdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.common.SpacesItemDecoration
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.framework.presentation.drugdetail.state.DrugDetailViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_drug_detail.*
import kotlinx.android.synthetic.main.fragment_drug_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TAG = "DrugDetailFragment"
const val DRUG_DETAIL_SELECTED_DRUG_ID_BUNDLE_KEY =
    "com.teracode.medihelp.framework.presentation.drugdetail.drugId"

const val DRUG_DETAIL_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.framework.presentation.drugdetail.state"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class DrugDetailFragment : BaseFragment(R.layout.fragment_drug_detail),
    DrugDetailAdapter.Interaction {
    val args: DrugDetailFragmentArgs by navArgs()

    private var listAdapter: DrugDetailAdapter? = null
    private val viewModel: DrugDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setupChannel()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSelectedDrugIdBundle()

        setupUI()
        setupRecyclerView()

        setupSwipeRefresh()
        subscribeObservers()

        restoreInstanceState(savedInstanceState)

    }


    private fun getSelectedDrugIdBundle() {
        args.let { arg ->
            viewModel.setDrugId(arg.drugId)
        }

    }

    private fun setupUI() {
        view?.hideKeyboard()
    }

    private fun setupSwipeRefresh() {

        drug_detail_swipe_refresh_layout.setOnRefreshListener {

            drug_detail_swipe_refresh_layout.isRefreshing = false
            viewModel.refreshSearchQuery()
        }

    }


    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->

            if (viewState != null) {


                viewState.selectedDrug?.let { drug ->
                    listAdapter?.submitList(drug)
                    listAdapter?.notifyDataSetChanged()
                    Log.d(TAG, "subscribeObservers: $drug")
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


    private fun restoreInstanceState(savedInstanceState: Bundle?) {

        savedInstanceState?.let { inState ->
            (inState[DRUG_DETAIL_STATE_BUNDLE_KEY] as DrugDetailViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }

        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        outState.putParcelable(
            DRUG_DETAIL_STATE_BUNDLE_KEY,
            viewState
        )


        super.onSaveInstanceState(outState)
    }


    override fun onResume() {
        super.onResume()
//        viewModel.retrieveNumDrugsInCache()
//        viewModel.clearList()
        viewModel.refreshSearchQuery()


    }


    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    private fun saveLayoutManagerState() {
        drug_detail_recyclerview.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    private fun setupRecyclerView() {
        drug_detail_recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)

//            addItemDecoration(SpacesItemDecoration(8))
            listAdapter = DrugDetailAdapter(
                interaction = this@DrugDetailFragment,
                lifecycleOwner = viewLifecycleOwner,

                )

            adapter = listAdapter

        }
    }

    override fun onItemSelected(position: Int, item: Drug) {
    }

    override fun restoreListPosition() {
        viewModel.getLayoutManagerState()?.let { lmState ->
            drug_detail_recyclerview?.layoutManager?.onRestoreInstanceState(lmState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        listAdapter=null
    }

}