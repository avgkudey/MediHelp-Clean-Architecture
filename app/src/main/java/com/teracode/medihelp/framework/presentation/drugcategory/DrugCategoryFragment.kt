package com.teracode.medihelp.framework.presentation.drugcategory

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.StateMessageCallback
import com.teracode.medihelp.databinding.FragmentDrugCategoryBinding
import com.teracode.medihelp.framework.presentation.common.BaseFragment
import com.teracode.medihelp.framework.presentation.common.SpacesItemDecoration
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.framework.presentation.drugcategory.state.DrugCategoryViewState
import com.teracode.medihelp.framework.presentation.druglist.DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY
import com.teracode.medihelp.framework.presentation.subcategorylist.SUBCATEGORY_LIST_SELECTED_CATEGORY_BUNDLE_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TAG = "DrugCategoryFragment"
const val CATEGORY_LIST_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.framework.presentation.drugcategory.state"


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DrugCategoryFragment : BaseFragment<FragmentDrugCategoryBinding>(),
    DrugCategoryAdapter.ItemInteraction {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDrugCategoryBinding =
        FragmentDrugCategoryBinding::inflate

    private val viewModel: DrugCategoryViewModel by viewModels()

    private var listAdapter: DrugCategoryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()


        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ROW_COUNT = 1
        } else {
            ROW_COUNT = 3
        }
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
        viewState?.categoryList = ArrayList()
        outState.putParcelable(
            CATEGORY_LIST_STATE_BUNDLE_KEY,
            viewState
        )



        super.onSaveInstanceState(outState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { inState ->
            (inState[CATEGORY_LIST_STATE_BUNDLE_KEY] as DrugCategoryViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }


    private fun setupUI() {
        view?.hideKeyboard()
    }

    private fun setupRecyclerView() {
        binding.categoryFragmentRecyclerview.apply {
            layoutManager = GridLayoutManager(context, ROW_COUNT)
            listAdapter = DrugCategoryAdapter(
                lifecycleOwner = viewLifecycleOwner,
                itemInteraction = this@DrugCategoryFragment,

                )

            addItemDecoration(SpacesItemDecoration(8))
            adapter = listAdapter

        }
    }

    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->

            if (viewState != null) {
                viewState.categoryList?.let { categoryList ->
                    listAdapter?.submitList(categoryList)
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

    override fun onItemClicked(position: Int, item: Category) {

        navigateToSubcategoryPage(item)
    }

    private fun navigateToSubcategoryPage(item: Category) {

        if (item.subcategoryCount > 0) {
            val bundle = bundleOf(SUBCATEGORY_LIST_SELECTED_CATEGORY_BUNDLE_KEY to item)

            findNavController().navigate(
                R.id.action_drugCategoryFragment_to_subcategoryFragment,
                bundle
            )
        } else {
            val bundle = bundleOf(DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY to item)

            findNavController().navigate(
                R.id.action_drugCategoryFragment_to_drugListFragment,
                bundle
            )
        }


    }

    override fun onItemLongPress(position: Int, item: Category) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }


    }

    override fun restoreListPosition() {

        viewModel.getLayoutManagerState()?.let { lmState ->
            binding.categoryFragmentRecyclerview.layoutManager?.onRestoreInstanceState(lmState)
        }
    }

    private fun saveLayoutManagerState() {
        binding.categoryFragmentRecyclerview.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }


    companion object {
        private var ROW_COUNT = 2
    }

}
