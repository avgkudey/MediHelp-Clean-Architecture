package com.teracode.medihelp.framework.presentation.auth

import android.content.Intent
import android.os.Bundle
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.BaseActivity
import com.teracode.medihelp.framework.presentation.datasync.DataSyncActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthActivity : BaseActivity() {
    override fun displayProgressBar(isDisplayed: Boolean) {
        //TODO displayProgressBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        navigateToDataSyncActivity()
    }


    private fun navigateToDataSyncActivity() {
        val intent = Intent(this, DataSyncActivity::class.java)
        startActivity(intent)
        finish()
    }
}