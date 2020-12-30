package com.teracode.medihelp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.network.abstraction.DrugNetworkDataSource
import com.teracode.medihelp.business.interactors.common.SearchDrugs
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.business.interactors.splash.SyncDrugs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun provideDrugListInteractors(drugCacheDataSource: DrugCacheDataSource): DrugListInteractors {
        return DrugListInteractors(searchDrugs = SearchDrugs(drugCacheDataSource = drugCacheDataSource))
    }

    @Singleton
    @Provides
    fun provideSyncDrugs(
        drugCacheDataSource: DrugCacheDataSource,
        drugNetworkDataSource: DrugNetworkDataSource
    ): SyncDrugs {
        return SyncDrugs(
            drugCacheDataSource = drugCacheDataSource,
            drugNetworkDataSource =drugNetworkDataSource
        )
    }


}