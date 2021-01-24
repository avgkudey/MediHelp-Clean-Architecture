package com.teracode.medihelp.framework.presentation.datasync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teracode.medihelp.R
import com.teracode.medihelp.framework.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DataSyncActivity : BaseActivity() {
    override fun displayProgressBar(isDisplayed: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sync)
    }
}