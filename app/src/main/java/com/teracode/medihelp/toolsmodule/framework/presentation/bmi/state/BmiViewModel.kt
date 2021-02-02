package com.teracode.medihelp.toolsmodule.framework.presentation.bmi.state

import androidx.lifecycle.ViewModel
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class BmiViewModel : BaseViewModel<BmiViewState>() {
    override fun handleNewData(data: BmiViewState) {
    }

    override fun setStateEvent(stateEvent: StateEvent) {
    }

    override fun initNewViewState(): BmiViewState {
        return BmiViewState()
    }


    fun setBmiFields(bmiFields: BmiFields) {
        val update = getCurrentViewStateOrNew()
        update.bmiFields = bmiFields
        setViewState(update)
    }

    fun setBmiUnit(bmiUnitStatus: BmiUnitStatus) {
        val update = getCurrentViewStateOrNew()
        update.unitStatus = bmiUnitStatus
        setViewState(update)
    }
}