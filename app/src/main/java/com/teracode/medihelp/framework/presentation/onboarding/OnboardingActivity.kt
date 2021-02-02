package com.teracode.medihelp.framework.presentation.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.teracode.medihelp.R
import com.teracode.medihelp.databinding.ActivityMainBinding
import com.teracode.medihelp.databinding.ActivityOnboardingBinding
import com.teracode.medihelp.framework.presentation.BaseActivity
import com.teracode.medihelp.framework.presentation.auth.AuthActivity
import com.teracode.medihelp.framework.presentation.datasync.DataSyncActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>() {

    override fun getViewBinding() = ActivityOnboardingBinding.inflate(layoutInflater)

    override fun displayProgressBar(isDisplayed: Boolean) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.Theme_Medihelp)
        super.onCreate(savedInstanceState)
//        setTheme(R.style.Theme_Medihelp)
        navigateToAuthActivity()
    }


    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}