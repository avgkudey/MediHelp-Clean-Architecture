package com.teracode.medihelp.di

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.business.data.CategoryDataFactory
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


fun main() {
    val vv=SyncToFirestoreTest()
    vv.main1()

}
class SyncToFirestoreTest {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var dependencyContainer: DependencyContainer
    private lateinit var categoryDataFactory: CategoryDataFactory

    fun init() {
        dependencyContainer = DependencyContainer()
        dependencyContainer.build()
        firestore = dependencyContainer.firestore
        categoryDataFactory = dependencyContainer.categoryDataFactory
    }

    fun main1() {
        init()
        deleteNetworkNotes_confirmCacheSync()
    }

    fun deleteNetworkNotes_confirmCacheSync() {
        val categories = categoryDataFactory.produceListOfCategories()

        Log.d("TESTING123", "deleteNetworkNotes_confirmCacheSync: $categories")
    }
}