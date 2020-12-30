package com.teracode.medihelp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
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