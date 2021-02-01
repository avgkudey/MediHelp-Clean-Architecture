package com.teracode.medihelp.framework.presentation.datasync.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage
import com.teracode.medihelp.framework.presentation.drugdetail.state.DrugDetailStateEvent

sealed class DataSyncStateEvent : StateEvent {


    class SyncDrugsEvent : DataSyncStateEvent() {
        override fun errorInfo(): String {
            return "Error error syncing drugs."
        }

        override fun eventName(): String {
            return "SyncDrugsEvent"
        }

        override fun shouldDisplayProgressBar() = false

    }


    class CreateStateMessageEvent(
        val stateMessage: StateMessage,
    ) : DataSyncStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}