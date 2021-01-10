package com.teracode.medihelp.framework.presentation.drugcategory.state

import com.teracode.medihelp.business.domain.state.StateEvent

sealed class DrugCategoryStateEvent : StateEvent {

    class GetCategoriesEvent(
        val clearLayoutManagerState: Boolean = true
    ) : DrugCategoryStateEvent() {
        override fun errorInfo(): String {

            return "Error getting drug list"
        }

        override fun eventName(): String {
            return "DrugCategoryStateEvent"
        }

        override fun shouldDisplayProgressBar() = true

    }
}