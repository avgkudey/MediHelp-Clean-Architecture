package com.teracode.medihelp.di

import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.teracode.medihelp.business.domain.model.DrugFactory
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.framework.presentation.common.DrugViewModelFactory
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object ViewModelModule {


    @FlowPreview
    @Provides
    fun provideDrugViewModelFactory(
        drugListInteractors: DrugListInteractors,
        drugFactory: DrugFactory,
        drugsNetworkSyncManager: DrugsNetworkSyncManager

    ): ViewModelProvider.Factory {
        return DrugViewModelFactory(
            drugListInteractors = drugListInteractors,
            drugFactory = drugFactory,
            drugsNetworkSyncManager = drugsNetworkSyncManager
        )
    }
}