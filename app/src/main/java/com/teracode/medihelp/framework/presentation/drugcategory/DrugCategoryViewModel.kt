package com.teracode.medihelp.framework.presentation.drugcategory

import android.os.Parcelable
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.interactors.drugcategory.DrugCategoryInteractors
import com.teracode.medihelp.business.interactors.drugcategory.GetCategories
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.framework.presentation.drugcategory.state.DrugCategoryStateEvent
import com.teracode.medihelp.framework.presentation.drugcategory.state.DrugCategoryViewState
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList

private const val TAG = "DrugCategoryViewModel"
@ExperimentalCoroutinesApi
@FlowPreview
class DrugCategoryViewModel
@ViewModelInject
constructor(
    private val drugCategoryInteractors: DrugCategoryInteractors
) : BaseViewModel<DrugCategoryViewState>() {


    fun initData() {
        Log.d(TAG, "initData: ")
        setStateEvent(DrugCategoryStateEvent.GetCategoriesEvent())
    }

    fun refreshData() {

        Log.d(TAG, "refreshData: ")
        setStateEvent(DrugCategoryStateEvent.GetCategoriesEvent(false))
    }

    fun clearList() {
        val update = getCurrentViewStateOrNew()
        update.categoryList = ArrayList()
        setViewState(update)
    }

    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

    override fun handleNewData(data: DrugCategoryViewState) {

        printLogD(this::class.java.name, data.toString())
        data.let { viewState ->

            viewState.categoryList?.let { categoryList ->
                setCategoryData(categoryList)
            }

        }

    }

    private fun setCategoryData(categoryList: ArrayList<Category>) {
        val update = getCurrentViewStateOrNew()
        update.categoryList = categoryList
        setViewState(update)

    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<DrugCategoryViewState>?> = when (stateEvent) {

            is DrugCategoryStateEvent.GetCategoriesEvent -> {
                if (stateEvent.clearLayoutManagerState) {
                    clearLayoutManagerState()
                }

                drugCategoryInteractors.getCategories.getCategories(stateEvent = stateEvent)

            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }

        }

        launchJob(stateEvent = stateEvent, jobFunction = job)


    }

    override fun initNewViewState(): DrugCategoryViewState {
        return DrugCategoryViewState()
    }

    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }

}