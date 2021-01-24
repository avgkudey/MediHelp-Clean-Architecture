package com.teracode.medihelp.di

import com.teracode.medihelp.business.data.cache.abstraction.CategoryCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.cache.implementation.CategoryCacheDataSourceImpl
import com.teracode.medihelp.business.data.cache.implementation.DrugCacheDataSourceImpl
import com.teracode.medihelp.business.data.cache.implementation.SubcategoryCacheDataSourceImpl
import com.teracode.medihelp.business.data.network.abstraction.CategoryNetworkDataSource
import com.teracode.medihelp.business.data.network.abstraction.DrugNetworkDataSource
import com.teracode.medihelp.business.data.network.abstraction.SubcategoryNetworkDataSource
import com.teracode.medihelp.business.data.network.implementation.CategoryNetworkDataSourceImpl
import com.teracode.medihelp.business.data.network.implementation.DrugNetworkDataSourceImpl
import com.teracode.medihelp.business.data.network.implementation.SubcategoryNetworkDataSourceImpl
import com.teracode.medihelp.framework.datasource.cache.abstraction.CategoryDaoService
import com.teracode.medihelp.framework.datasource.cache.abstraction.DrugDaoService
import com.teracode.medihelp.framework.datasource.cache.abstraction.SubcategoryDaoService
import com.teracode.medihelp.framework.datasource.network.abstraction.CategoryFirestoreService
import com.teracode.medihelp.framework.datasource.network.abstraction.DrugFirestoreService
import com.teracode.medihelp.framework.datasource.network.abstraction.SubcategoryFirestoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideDrugCacheDataSource(drugDaoService: DrugDaoService): DrugCacheDataSource {
        return DrugCacheDataSourceImpl(drugDaoService = drugDaoService)
    }

    @Singleton
    @Provides
    fun provideCategoryCacheDataSource(categoryDaoService: CategoryDaoService): CategoryCacheDataSource {
        return CategoryCacheDataSourceImpl(daoService = categoryDaoService)
    }

    @Singleton
    @Provides
    fun provideSubcategoryCacheDataSource(categoryDaoService: SubcategoryDaoService): SubcategoryCacheDataSource {
        return SubcategoryCacheDataSourceImpl(daoService = categoryDaoService)
    }


    @Singleton
    @Provides
    fun provideDrugNetworkDataSource(firestoreService: DrugFirestoreService): DrugNetworkDataSource {
        return DrugNetworkDataSourceImpl(firestoreService = firestoreService)
    }

    @Singleton
    @Provides
    fun provideCategoryNetworkDataSource(firestoreService: CategoryFirestoreService): CategoryNetworkDataSource {
        return CategoryNetworkDataSourceImpl(firestoreService = firestoreService)
    }

    @Singleton
    @Provides
    fun provideSubcategoryNetworkDataSource(firestoreService: SubcategoryFirestoreService): SubcategoryNetworkDataSource {
        return SubcategoryNetworkDataSourceImpl(firestoreService = firestoreService)
    }


}