package com.teracode.medihelp.framework.presentation.auth

import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.business.domain.state.StateEvent
import com.teracode.medihelp.framework.presentation.auth.state.AuthStateEvent
import com.teracode.medihelp.framework.presentation.auth.state.AuthStateEvent.*
import com.teracode.medihelp.framework.presentation.auth.state.AuthViewState
import com.teracode.medihelp.framework.presentation.common.BaseViewModel
import com.teracode.medihelp.framework.presentation.druglist.state.DrugListStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class AuthViewModel : BaseViewModel<AuthViewState>() {
    override fun handleNewData(data: AuthViewState) {
        TODO("Not yet implemented")
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<AuthViewState>> = when (stateEvent) {
            is LoginAttemptEvent -> TODO()
            is RegisterAttemptEvent -> TODO()
            is CreateStateMessageEvent -> {
                emitStateMessageEvent(
                    stateMessage = stateEvent.stateMessage,
                    stateEvent = stateEvent
                )
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }

        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }
}