package com.teracode.medihelp.framework.presentation.datasync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.teracode.medihelp.R
import com.teracode.medihelp.databinding.ActivityAuthBinding
import com.teracode.medihelp.databinding.ActivityDataSyncBinding
import com.teracode.medihelp.databinding.ActivityMainBinding
import com.teracode.medihelp.framework.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DataSyncActivity : BaseActivity<ActivityDataSyncBinding>() {


    override fun getViewBinding() = ActivityDataSyncBinding.inflate(layoutInflater)

    override fun displayProgressBar(isDisplayed: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.Theme_Medihelp)
        super.onCreate(savedInstanceState)

    }
}