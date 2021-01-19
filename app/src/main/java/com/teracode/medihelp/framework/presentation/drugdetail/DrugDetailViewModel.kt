package com.teracode.medihelp.framework.presentation.drugdetail

import android.os.Parcelable
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.interactors.drugdetail.DrugDetailInteractors
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.framework.presentation.drugdetail.state.DrugDetailStateEvent
import com.teracode.medihelp.framework.presentation.drugdetail.state.DrugDetailViewState
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class DrugDetailViewModel
@ViewModelInject
constructor(
    private val drugDetailInteractors: DrugDetailInteractors
) : BaseViewModel<DrugDetailViewState>() {
    override fun handleNewData(data: DrugDetailViewState) {
        data.let { viewState ->

            viewState.selectedDrug?.let { drug ->
                setSelectedDrug(drug)
            }

        }

    }

    private fun setSelectedDrug(drug: Drug) {
        val update = getCurrentViewStateOrNew()
        update.selectedDrug = drug
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {


        val job: Flow<DataState<DrugDetailViewState>?> = when (stateEvent) {

            is DrugDetailStateEvent.GetDrugEvent -> {
                if (stateEvent.clearLayoutManagerState) {
                    clearLayoutManagerState()
                }

                drugDetailInteractors.getDrug.getDrug(
                    getCurrentViewStateOrNew().drugId ?: "",
                    stateEvent
                )
            }


            is DrugDetailStateEvent.CreateStateMessageEvent -> {
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

    override fun initNewViewState(): DrugDetailViewState {
        return DrugDetailViewState()
    }

    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }

    fun setDrugId(drugId: String) {
        val update = getCurrentViewStateOrNew()
        update.drugId = drugId
        setViewState(update)
    }
    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }
    fun refreshSearchQuery() {
        Log.d(TAG, "refreshSearchQuery: ")
        setStateEvent(DrugDetailStateEvent.GetDrugEvent())
    }


}

private const val TAG = "DrugDetailViewModel"