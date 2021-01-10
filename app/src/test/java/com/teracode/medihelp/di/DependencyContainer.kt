package com.teracode.medihelp.di

import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.business.data.CategoryDataFactory
import com.teracode.medihelp.business.data.DrugDataFactory
import com.teracode.medihelp.business.data.cache.FakeDrugCacheDataSourceImpl
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.util.isUnitTest

class DependencyContainer {

    lateinit var drugCacheDataSource: DrugCacheDataSource
    lateinit var drugDataFactory: DrugDataFactory

    lateinit var firestore: FirebaseFirestore

    lateinit var categoryDataFactory: CategoryDataFactory

    init {
        isUnitTest = true
    }

    fun build() {
        this.javaClass.classLoader?.let { classLoader ->
            drugDataFactory = DrugDataFactory(classLoader)
            categoryDataFactory = CategoryDataFactory(classLoader)
        }

        drugCacheDataSource = FakeDrugCacheDataSourceImpl(
            drugData = drugDataFactory.produceHashMapOfDrugs(
                drugDataFactory.produceListOfDrugs()
            ),

            )


        firestore = FirebaseFirestore.getInstance()
        categoryDataFactory
    }

}