package com.teracode.medihelp.framework.presentation.drugcategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.R
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.framework.datasource.cache.abstraction.CategoryDaoService
import com.teracode.medihelp.framework.datasource.cache.implementation.CategoryDaoServiceImpl
import com.teracode.medihelp.framework.datasource.network.implementation.CategoryFirestoreServiceImpl
import com.teracode.medihelp.framework.datasource.network.implementation.CategoryFirestoreServiceImpl.Companion.CATEGORY_COLLECTION
import com.teracode.medihelp.framework.datasource.network.implementation.DrugFirestoreServiceImpl.Companion.DRUGS_COLLECTION
import com.teracode.medihelp.framework.datasource.network.model.CategoryNetworkEntity
import com.teracode.medihelp.framework.datasource.network.model.DrugNetworkEntity
import com.teracode.medihelp.framework.presentation.common.hideKeyboard
import com.teracode.medihelp.framework.presentation.drugcategory.state.DrugCategoryViewState
import com.teracode.medihelp.framework.presentation.druglist.DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY
import com.teracode.medihelp.framework.presentation.druglist.DrugListAdapter
import com.teracode.medihelp.util.cLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_drug_category.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "DrugCategoryFragment"
const val CATEGORY_LIST_STATE_BUNDLE_KEY =
    "com.teracode.medihelp.framework.presentation.drugcategory.state"


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DrugCategoryFragment : Fragment(), DrugCategoryAdapter.ItemInteraction {

    private val viewModel: DrugCategoryViewModel by viewModels()

    private var listAdapter: DrugCategoryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drug_category, container, false)
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

    override fun onDestroy() {
        super.onDestroy()
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

    private fun initData() {
        viewModel.clearList()
        viewModel.initData()
    }


    private fun setupUI() {
        view?.hideKeyboard()
    }

    private fun setupRecyclerView() {
        category_fragment_recyclerview.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            listAdapter = DrugCategoryAdapter(
                lifecycleOwner = viewLifecycleOwner,
                itemInteraction = this@DrugCategoryFragment,

                )

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

    }

    override fun onItemClicked(position: Int, item: Category) {

        navigateToSubcategoryPage(item)
    }

    private fun navigateToSubcategoryPage(item: Category) {

//        val bundle = bundleOf(DRUG_LIST_SELECTED_CATEGORY_BUNDLE_KEY to item)

        findNavController().navigate(R.id.action_drugCategoryFragment_to_drugListFragment)
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
            category_fragment_recyclerview?.layoutManager?.onRestoreInstanceState(lmState)
        }
    }

    private fun saveLayoutManagerState() {
        category_fragment_recyclerview.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }



}
