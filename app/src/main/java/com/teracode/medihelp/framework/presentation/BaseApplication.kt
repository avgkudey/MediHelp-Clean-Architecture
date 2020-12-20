package com.teracode.medihelp.framework.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
    }


}