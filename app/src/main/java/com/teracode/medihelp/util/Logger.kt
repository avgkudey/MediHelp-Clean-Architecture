package com.teracode.medihelp.util

import android.util.Log
import com.teracode.medihelp.util.Constants.DEBUG
import com.teracode.medihelp.util.Constants.TAG

var isUnitTest = false

fun printLogD(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }
}

