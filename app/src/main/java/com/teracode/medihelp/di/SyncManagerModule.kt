package com.teracode.medihelp.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.teracode.medihelp.business.interactors.splash.SyncCategories
import com.teracode.medihelp.business.interactors.splash.SyncCounts
import com.teracode.medihelp.business.interactors.splash.SyncDrugs
import com.teracode.medihelp.business.interactors.splash.SyncSubcategories
import com.teracode.medihelp.framework.presentation.datasync.DataNetworkSyncManager
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager
import com.teracode.medihelp.quizmodule.business.interactors.splash.SyncQuestions
import com.teracode.medihelp.quizmodule.business.interactors.splash.SyncQuizzes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SyncManagerModule {


    @Provides
    fun provideDrugsNetworkSyncManager(
        syncDrugs: SyncDrugs,
        syncCategories: SyncCategories,
        syncCounts: SyncCounts,
        syncSubcategories: SyncSubcategories,
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor,
        remoteConfig: FirebaseRemoteConfig,
    ): DrugsNetworkSyncManager {
        return DrugsNetworkSyncManager(
            syncDrugs = syncDrugs,
            syncCategory = syncCategories,
            syncSubcategory = syncSubcategories,
            syncCounts = syncCounts,
            sharedPreferences = sharedPreferences,
            remoteConfig = remoteConfig,
            editor = editor
        )
    }

    @Provides
    fun provideDataNetworkSyncManager(
        syncDrugs: SyncDrugs,
        syncCategories: SyncCategories,
        syncCounts: SyncCounts,
        syncSubcategories: SyncSubcategories,
        syncQuizzes: SyncQuizzes,
        syncQuestions: SyncQuestions,
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor,
        remoteConfig: FirebaseRemoteConfig,
    ): DataNetworkSyncManager {
        return DataNetworkSyncManager(
            syncDrugs = syncDrugs,
            syncCategory = syncCategories,
            syncSubcategory = syncSubcategories,
            syncCounts = syncCounts,
            syncQuizzes = syncQuizzes,
            syncQuestions = syncQuestions,
            sharedPreferences = sharedPreferences,
            remoteConfig = remoteConfig,
            editor = editor
        )
    }


}