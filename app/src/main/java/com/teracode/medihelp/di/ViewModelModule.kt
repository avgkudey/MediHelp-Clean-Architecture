package com.teracode.medihelp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object ViewModelModule {
//
//
//    @FlowPreview
//    @Provides
//    fun provideDrugViewModelFactory(
//        drugListInteractors: DrugListInteractors,
//        drugFactory: DrugFactory,
//        drugsNetworkSyncManager: DrugsNetworkSyncManager
//
//    ): ViewModelProvider.Factory {
//        return DrugViewModelFactory(
//            drugListInteractors = drugListInteractors,
//            drugFactory = drugFactory,
//            drugsNetworkSyncManager = drugsNetworkSyncManager
//        )
//    }
}