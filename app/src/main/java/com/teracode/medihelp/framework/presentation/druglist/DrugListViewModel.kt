package com.teracode.medihelp.framework.presentation.druglist

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.model.DrugFactory
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.framework.datasource.database.DRUG_FILTER_DATE_CREATED
import com.teracode.medihelp.framework.datasource.database.DRUG_ORDER_DESC
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent.CreateStateMessageEvent
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent.SearchDrugsEvent
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class DrugListViewModel
@Inject
constructor(
    private val drugListInteractors: DrugListInteractors,
    private val drugFactory: DrugFactory
) : BaseViewModel<DrugListViewState>() {

    override fun handleNewData(data: DrugListViewState) {

        data.let { viewState ->

            viewState.drugList?.let { drugList ->
                setDrugListData(drugList)
            }

        }
    }

    private fun setDrugListData(drugList: ArrayList<Drug>) {

        val update = getCurrentViewStateOrNew()
        update.drugList = drugList
        setViewState(update)

    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<DrugListViewState>?> = when (stateEvent) {

            is SearchDrugsEvent -> {
                if (stateEvent.clearLayoutManagerState) {
                    clearLayoutManagerState()
                }

                drugListInteractors.searchDrugs.searchDrugs(
                    query = getSearchQuery(),
                    filterAndOrder = getOrder() + getFilter(),
                    page = getPage(),
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

    fun getFilter(): String {
        return getCurrentViewStateOrNew().filter
            ?: DRUG_FILTER_DATE_CREATED
    }

    fun getOrder(): String {
        return getCurrentViewStateOrNew().order
            ?: DRUG_ORDER_DESC
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

    fun setNoteOrder(order: String?) {
        val update = getCurrentViewStateOrNew()
        update.order = order
        setViewState(update)
    }



}