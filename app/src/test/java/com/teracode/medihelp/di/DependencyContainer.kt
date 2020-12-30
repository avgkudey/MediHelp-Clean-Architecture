package com.teracode.medihelp.di

import com.teracode.medihelp.business.data.DrugDataFactory
import com.teracode.medihelp.business.data.cache.FakeDrugCacheDataSourceImpl
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.util.isUnitTest

class DependencyContainer {

    lateinit var drugCacheDataSource: DrugCacheDataSource
    lateinit var drugDataFactory: DrugDataFactory

    init {
        isUnitTest = true
    }

    fun build(){
        this.javaClass.classLoader?.let { classLoader->
            drugDataFactory= DrugDataFactory(classLoader)
        }

        drugCacheDataSource= FakeDrugCacheDataSourceImpl(
            drugData = drugDataFactory.produceHashMapOfDrugs(
                drugDataFactory.produceListOfDrugs()
            ),

        )
    }

}