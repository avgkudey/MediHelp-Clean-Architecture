package com.teracode.medihelp.framework.presentation


import com.teracode.medihelp.business.domain.state.DialogInputCaptureCallback
import com.teracode.medihelp.business.domain.state.Response
import com.teracode.medihelp.business.domain.state.StateMessageCallback


interface UIController {

    fun displayProgressBar(isDisplayed: Boolean)

    fun hideSoftKeyboard()

    fun displayInputCaptureDialog(title: String, callback: DialogInputCaptureCallback)

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )
    fun isStoragePermissionGranted(): Boolean
}


















