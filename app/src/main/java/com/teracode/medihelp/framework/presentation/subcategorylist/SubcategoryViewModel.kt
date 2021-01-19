package com.teracode.medihelp.framework.presentation.subcategorylist

import android.os.Parcelable
import androidx.hilt.lifecycle.ViewModelInject
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.interactors.subcategories.SubcategoryInteractors
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.framework.presentation.subcategorylist.state.SubcategoryListViewState
import com.teracode.medihelp.framework.presentation.subcategorylist.state.SubcategoryStateEvent.*
import com.teracode.medihelp.util.OrderEnum
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList

@ExperimentalCoroutinesApi
@FlowPreview
class SubcategoryViewModel
@ViewModelInject
constructor(
    private val subcategoryInteractors: SubcategoryInteractors
) : BaseViewModel<SubcategoryListViewState>() {


    override fun handleNewData(data: SubcategoryListViewState) {
        data.let {
            data.let { viewState ->
                viewState.subcategoryList?.let { subcategoryList ->
                    setSubcategoryList(subcategoryList)
                }
            }
            data.let { viewState ->
                viewState.numSubcategoriesInCache?.let { numSubcategoriesInCache ->
                    setNumSubcategoryList(numSubcategoriesInCache)
                }
            }
        }
    }

    private fun setNumSubcategoryList(numSubcategoriesInCache: Int) {
        val update = getCurrentViewStateOrNew()
        update.numSubcategoriesInCache = numSubcategoriesInCache
        setViewState(update)
    }

    private fun setSubcategoryList(subcategoryList: ArrayList<Subcategory>) {
        val update = getCurrentViewStateOrNew()
        update.subcategoryList = subcategoryList
        setViewState(update)
    }


    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<SubcategoryListViewState>?> = when (stateEvent) {

            is GetNumSubcategoriesEvent -> {
                subcategoryInteractors.getNumSubcategories.getNumSubcategories(
                    categoryId = getCurrentViewStateOrNew().category?.id,
                    stateEvent = stateEvent
                )
            }
            is SearchSubcategoriesEvent -> {
                subcategoryInteractors.searchSubcategories.searchSubcategories(
                    query = getSearchQuery(),
                    filterAndOrder = getOrder(),
                    page = getPage(),
                    categoryId = getCurrentViewStateOrNew().category?.id,
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


        launchJob(stateEvent = stateEvent, jobFunction = job)
    }


    override fun initNewViewState(): SubcategoryListViewState {

        return SubcategoryListViewState()
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


    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

    fun clearList() {
        val update = getCurrentViewStateOrNew()
        update.subcategoryList = ArrayList()
        setViewState(update)
    }

    fun loadFirstPage() {
        setQueryExhausted(false)
        resetPage()

        setStateEvent(SearchSubcategoriesEvent())
    }

    private fun resetPage() {
        val update = getCurrentViewStateOrNew()
        update.page = 1
        setViewState(update)
    }

    fun retrieveNumSubcategoriesInCache() {
        setStateEvent(GetNumSubcategoriesEvent())
    }

    fun refreshSearchQuery() {
        setQueryExhausted(false)
        setStateEvent(SearchSubcategoriesEvent())
    }

    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }

    fun setCategory(category: Category?) {
        val update = getCurrentViewStateOrNew()
        update.category = category
        setViewState(update)
    }

    private fun incrementPageNumber() {
        val update = getCurrentViewStateOrNew()
        val page = update.copy().page ?: 1
        update.page = page.plus(1)
        setViewState(update)
    }

    fun setQueryExhausted(isExhausted: Boolean) {
        val update = getCurrentViewStateOrNew()
        update.isQueryExhausted = isExhausted
        setViewState(update)
    }

    fun isQueryExhausted(): Boolean {
        printLogD(
            TAG,
            "is query exhausted? ${getCurrentViewStateOrNew().isQueryExhausted ?: true}"
        )
        return getCurrentViewStateOrNew().isQueryExhausted ?: true
    }

    fun nextPage() {


        if (!isQueryExhausted()) {
            printLogD(TAG, "attempting to load next page...")
            clearLayoutManagerState()
            incrementPageNumber()
            setStateEvent(SearchSubcategoriesEvent())
        }
    }


    fun isPaginationExhausted() = getSubcategoryListSize() >= getNumSubcategoriesInCache()

    private fun getSubcategoryListSize() = getCurrentViewStateOrNew().subcategoryList?.size ?: 0

    private fun getNumSubcategoriesInCache() =
        getCurrentViewStateOrNew().numSubcategoriesInCache ?: 0

    companion object {
        private const val TAG = "SubcategoryViewModel"
    }
}