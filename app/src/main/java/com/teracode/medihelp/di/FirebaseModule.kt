package com.teracode.medihelp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.teracode.medihelp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FirebaseModule {

//    @Singleton
//    @Provides
//    fun provideFirebaseAuth(): FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
//        return FirebaseAuth.getInstance()
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)


        return remoteConfig

    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        val firestore = FirebaseFirestore.getInstance()

        val settings = firestoreSettings {
            isPersistenceEnabled = true
            cacheSizeBytes=50000000
        }
        firestore.firestoreSettings = settings



        return firestore
//        val firestore = FirebaseFirestore.getInstance()
//        firestore.useEmulator("10.0.2.2", 8080)
//
//        val settings = FirebaseFirestoreSettings.Builder()
//            .setPersistenceEnabled(true)
//            .build()
//
//
//        firestore.firestoreSettings = settings
//
//        return firestore

    }

}