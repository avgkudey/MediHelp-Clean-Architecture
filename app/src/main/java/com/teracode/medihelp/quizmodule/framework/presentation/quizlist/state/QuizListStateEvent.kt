package com.teracode.medihelp.quizmodule.framework.presentation.quizlist.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent

sealed class QuizListStateEvent:StateEvent {


    class GetQuizzesEvent(
        val clearLayoutManagerState: Boolean = true
    ):QuizListStateEvent(){
        override fun errorInfo(): String {
           return "Error retrieving quizzes."
        }

        override fun eventName(): String {
           return "GetQuizzesEvent"
        }

        override fun shouldDisplayProgressBar()=true

    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : QuizListStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}