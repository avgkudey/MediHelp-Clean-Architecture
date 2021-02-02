package com.teracode.medihelp.framework.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.teracode.medihelp.R
import com.teracode.medihelp.databinding.ActivityAuthBinding
import com.teracode.medihelp.databinding.ActivityMainBinding
import com.teracode.medihelp.framework.presentation.BaseActivity
import com.teracode.medihelp.framework.presentation.datasync.DataSyncActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    override fun getViewBinding() = ActivityAuthBinding.inflate(layoutInflater)




    override fun displayProgressBar(isDisplayed: Boolean) {
        //TODO displayProgressBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.Theme_Medihelp)
        super.onCreate(savedInstanceState)
//        setTheme(R.style.Theme_Medihelp)
        navigateToDataSyncActivity()
    }


    private fun navigateToDataSyncActivity() {
        val intent = Intent(this, DataSyncActivity::class.java)
        startActivity(intent)
        finish()
    }
}