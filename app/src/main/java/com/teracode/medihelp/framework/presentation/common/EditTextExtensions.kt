package com.teracode.medihelp.framework.presentation.common

import android.view.inputmethod.EditorInfo
import android.widget.EditText


fun EditText.onDone(callback: () -> Unit) {

    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
            true
        }
        false
    }
}


