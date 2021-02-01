package com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent

sealed class QuizStartStateEvent:StateEvent {


    class GetQuestionsEvent(
        val clearLayoutManagerState: Boolean = true
    ):QuizStartStateEvent(){
        override fun errorInfo(): String {
           return "Error retrieving questiond."
        }

        override fun eventName(): String {
           return "GetQuestionsEvent"
        }

        override fun shouldDisplayProgressBar()=true

    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : QuizStartStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}