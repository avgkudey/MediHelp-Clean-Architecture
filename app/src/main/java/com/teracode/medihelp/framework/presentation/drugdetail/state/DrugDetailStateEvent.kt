package com.teracode.medihelp.framework.presentation.drugdetail.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent

sealed class DrugDetailStateEvent:StateEvent {


    class GetDrugEvent(
        val clearLayoutManagerState: Boolean = true
    ):DrugDetailStateEvent(){
        override fun errorInfo(): String {
           return "Error retrieving drug."
        }

        override fun eventName(): String {
           return "GetDrugEvent"
        }

        override fun shouldDisplayProgressBar()=true

    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : DrugDetailStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}