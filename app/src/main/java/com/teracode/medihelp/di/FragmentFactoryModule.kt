package com.teracode.medihelp.di

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.teracode.medihelp.framework.presentation.common.DrugFragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object FragmentFactoryModule {


    @FlowPreview
    @Provides
    fun provideDrugFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory

    ): FragmentFactory {
        return DrugFragmentFactory(
        )
    }
}