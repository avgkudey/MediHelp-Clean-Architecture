package com.teracode.medihelp.framework.presentation.druglist.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage

sealed class DrugListStateEvent : StateEvent {
    class SearchDrugsEvent(
        val clearLayoutManagerState: Boolean = true
    ) : DrugListStateEvent() {
        override fun errorInfo(): String {
            return "Error getting list of drugs"
        }

        override fun eventName(): String {
            return "SearchDrugsEvent"
        }

        override fun shouldDisplayProgressBar() = true

    }


    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : DrugListStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}
