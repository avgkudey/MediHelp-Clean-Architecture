package com.teracode.medihelp.framework.presentation.druglist

import android.os.Parcelable
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.framework.datasource.database.DRUG_FILTER_DATE_CREATED
import com.teracode.medihelp.framework.datasource.database.DRUG_ORDER_DESC
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent.*
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListViewState
import com.teracode.medihelp.util.OrderEnum
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList


@ExperimentalCoroutinesApi
@FlowPreview
class DrugListViewModel
@ViewModelInject
constructor(
    private val drugListInteractors: DrugListInteractors,
) : BaseViewModel<DrugListViewState>() {

    override fun handleNewData(data: DrugListViewState) {
        data.let { viewState ->
            Log.d(TAG, "handleNewData: $viewState")

            viewState.drugList?.let { drugList ->
                setDrugListData(drugList)
            }

            viewState.numDrugsInCache?.let { numDrugs ->
                setNumDrugsInCache(numDrugs)
            }

        }
    }

    private fun setNumDrugsInCache(numDrugs: Int) {
        val update = getCurrentViewStateOrNew()
        update.numDrugsInCache = numDrugs
        setViewState(update)
    }

    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }

    fun nextPage() {


        if (!isQueryExhausted()) {
            printLogD(TAG, "attempting to load next page...")
            clearLayoutManagerState()
            incrementPageNumber()
            setStateEvent(SearchDrugsEvent())
            Log.d("DRUGLISTFRAC", "nextPage:  SearchDrugsEvent")

        }
    }

    fun isQueryExhausted(): Boolean {
        printLogD(
            TAG,
            "is query exhasuted? ${getCurrentViewStateOrNew().isQueryExhausted ?: true}"
        )
        return getCurrentViewStateOrNew().isQueryExhausted ?: true
    }

    fun setQueryExhausted(isExhausted: Boolean) {
        val update = getCurrentViewStateOrNew()
        update.isQueryExhausted = isExhausted
        setViewState(update)
    }


    fun isPaginationExhausted() = getDrugListSize() >= getNumDrugsInCache()

    fun getDrugListSize() = getCurrentViewStateOrNew().drugList?.size ?: 0

    fun getNumDrugsInCache() = getCurrentViewStateOrNew().numDrugsInCache ?: 0

    private fun incrementPageNumber() {
        val update = getCurrentViewStateOrNew()
        val page = update.copy().page ?: 1
        update.page = page.plus(1)
        setViewState(update)
    }

    private fun setDrugListData(drugList: ArrayList<Drug>) {
        val update = getCurrentViewStateOrNew()
        update.drugList = drugList
        setViewState(update)

    }

    fun setCategory(category: Category?) {
        val update = getCurrentViewStateOrNew()
        update.category = category
        setViewState(update)
    }

    fun setSubcategory(subcategory: Subcategory?) {
        val update = getCurrentViewStateOrNew()
        update.subcategory = subcategory
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<DrugListViewState>?> = when (stateEvent) {

            is SearchDrugsEvent -> {
                Log.d(TAG, "setStateEvent: SearchDrugsEvent")
                if (stateEvent.clearLayoutManagerState) {
                    clearLayoutManagerState()
                }

                drugListInteractors.searchDrugs.searchDrugs(
                    query = getSearchQuery(),
                    filterAndOrder = getOrder(),
                    page = getPage(),
                    categoryId = getCurrentViewStateOrNew().category?.id,
                    subcategoryId = getCurrentViewStateOrNew().subcategory?.id,
                    stateEvent = stateEvent
                )
            }
            is GetNumDrugsInCacheEvent -> {
                drugListInteractors.getNumDrugs.getNumDrugs(
                    categoryId = getCurrentViewStateOrNew().category?.id,
                    subcategoryId = getCurrentViewStateOrNew().subcategory?.id,
                    stateEvent = stateEvent
                )
            }


            is CreateStateMessageEvent -> {
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

    fun getOrder(): OrderEnum {
        return getCurrentViewStateOrNew().order
            ?: OrderEnum.ORDER_ASC
    }

    fun getSearchQuery(): String {
        return getCurrentViewStateOrNew().searchQuery
            ?: return ""
    }

    private fun getPage(): Int {
        return getCurrentViewStateOrNew().page
            ?: return 1
    }

    override fun initNewViewState(): DrugListViewState {
        return DrugListViewState()
    }

    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }

    fun setQuery(query: String?) {
        val update = getCurrentViewStateOrNew()
        update.searchQuery = query
        setViewState(update)
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

    fun setNoteFilter(filter: String?) {
        filter?.let {
            val update = getCurrentViewStateOrNew()
            update.filter = filter
            setViewState(update)
        }
    }

    fun setNoteOrder(order: OrderEnum?) {
        val update = getCurrentViewStateOrNew()
        update.order = order
        setViewState(update)
    }

    fun clearList() {
        val update = getCurrentViewStateOrNew()
        update.drugList = ArrayList()
        setViewState(update)
    }

    fun loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ")
        setQueryExhausted(false)
        resetPage()

        setStateEvent(SearchDrugsEvent())
        Log.d("DRUGLISTFRAC", "loadFirstPage:  SearchDrugsEvent")

    }

    private fun resetPage() {
        val update = getCurrentViewStateOrNew()
        update.page = 1
        setViewState(update)
    }


    fun retrieveNumDrugsInCache() {
        setStateEvent(GetNumDrugsInCacheEvent())
    }

    fun refreshSearchQuery() {
        setQueryExhausted(false)
        setStateEvent(SearchDrugsEvent(false))
    }

    companion object {
        private const val TAG = "DrugListViewModel"

    }

}

