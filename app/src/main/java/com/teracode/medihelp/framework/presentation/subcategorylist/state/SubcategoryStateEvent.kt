package com.teracode.medihelp.framework.presentation.subcategorylist.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage

sealed class SubcategoryStateEvent : StateEvent {

    class GetNumSubcategoriesEvent : SubcategoryStateEvent() {
        override fun errorInfo(): String {
            return "Error getting number of subcategories"
        }

        override fun eventName(): String {
            return "GetNumSubcategoriesEvent"
        }

        override fun shouldDisplayProgressBar() = true

    }

    class SearchSubcategoriesEvent : SubcategoryStateEvent() {
        override fun errorInfo(): String {
            return "Error getting subcategories"
        }

        override fun eventName(): String {
            return "SearchSubcategoriesEvent"
        }

        override fun shouldDisplayProgressBar() = true

    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : SubcategoryStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}