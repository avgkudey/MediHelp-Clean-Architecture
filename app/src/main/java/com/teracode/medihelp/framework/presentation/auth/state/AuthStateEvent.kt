package com.teracode.medihelp.framework.presentation.auth.state

import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.business.domain.state.StateMessage
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent

sealed class AuthStateEvent : StateEvent {

    data class LoginAttemptEvent(
        val email: String,
        val password: String
    ) : AuthStateEvent() {
        override fun errorInfo(): String {
            return "Login attempt failed."
        }

        override fun eventName(): String {
            return "LoginAttemptEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }

    data class RegisterAttemptEvent(
        val email: String,
        val username: String,
        val password: String,
        val confirm_password: String
    ) : AuthStateEvent() {
        override fun errorInfo(): String {
            return "Register attempt failed."
        }

        override fun eventName(): String {
            return "RegisterAttemptEvent"
        }

        override fun shouldDisplayProgressBar() = true

    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : AuthStateEvent() {

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}