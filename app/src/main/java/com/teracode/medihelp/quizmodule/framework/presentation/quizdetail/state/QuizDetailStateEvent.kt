package com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent

sealed class QuizDetailStateEvent:StateEvent {


    class GetQuizEvent(
        val clearLayoutManagerState: Boolean = true
    ):QuizDetailStateEvent(){
        override fun errorInfo(): String {
           return "Error retrieving quiz."
        }

        override fun eventName(): String {
           return "GetQuizEvent"
        }

        override fun shouldDisplayProgressBar()=true

    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : QuizDetailStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}