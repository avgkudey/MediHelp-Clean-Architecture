package com.teracode.medihelp.di

import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.framework.datasource.cache.abstraction.CategoryDaoService
import com.teracode.medihelp.framework.datasource.cache.abstraction.DrugDaoService
import com.teracode.medihelp.framework.datasource.cache.abstraction.SubcategoryDaoService
import com.teracode.medihelp.framework.datasource.cache.implementation.CategoryDaoServiceImpl
import com.teracode.medihelp.framework.datasource.cache.implementation.DrugDaoServiceImpl
import com.teracode.medihelp.framework.datasource.cache.implementation.SubcategoryDaoServiceImpl
import com.teracode.medihelp.framework.datasource.cache.mappers.CategoryCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.DrugCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.SubcategoryCacheMapper
import com.teracode.medihelp.framework.datasource.database.CategoryDao
import com.teracode.medihelp.framework.datasource.database.DrugDao
import com.teracode.medihelp.framework.datasource.database.SubcategoryDao
import com.teracode.medihelp.framework.datasource.network.abstraction.CategoryFirestoreService
import com.teracode.medihelp.framework.datasource.network.abstraction.DrugFirestoreService
import com.teracode.medihelp.framework.datasource.network.abstraction.SubcategoryFirestoreService
import com.teracode.medihelp.framework.datasource.network.implementation.CategoryFirestoreServiceImpl
import com.teracode.medihelp.framework.datasource.network.implementation.DrugFirestoreServiceImpl
import com.teracode.medihelp.framework.datasource.network.implementation.SubcategoryFirestoreServiceImpl
import com.teracode.medihelp.framework.datasource.network.mappers.CategoryNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.DrugNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.SubcategoryNetworkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideDrugDaoService(
        dao: DrugDao,
        cacheMapper: DrugCacheMapper

    ): DrugDaoService {
        return DrugDaoServiceImpl(drugDao = dao, drugMapper = cacheMapper)
    }

    @Singleton
    @Provides
    fun provideCategoryDaoService(
        dao: CategoryDao,
        cacheMapper: CategoryCacheMapper

    ): CategoryDaoService {
        return CategoryDaoServiceImpl(categoryDao = dao, categoryMapper = cacheMapper)
    }

    @Singleton
    @Provides
    fun provideSubcategoryDaoService(
        dao: SubcategoryDao,
        cacheMapper: SubcategoryCacheMapper

    ): SubcategoryDaoService {
        return SubcategoryDaoServiceImpl(dao = dao, cacheMapper = cacheMapper)
    }


    @Singleton
    @Provides
    fun provideDrugFirestoreService(
        firestore: FirebaseFirestore,
        networkMapper: DrugNetworkMapper

    ): DrugFirestoreService {
        return DrugFirestoreServiceImpl(
            firestore = firestore,
            networkMapper = networkMapper
        )
    }

    @Singleton
    @Provides
    fun provideCategoryFirestoreService(
        firestore: FirebaseFirestore,
        networkMapper: CategoryNetworkMapper

    ): CategoryFirestoreService {
        return CategoryFirestoreServiceImpl(
            firestore = firestore,
            networkMapper = networkMapper
        )
    }

    @Singleton
    @Provides
    fun provideSubcategoryFirestoreService(
        firestore: FirebaseFirestore,
        networkMapper: SubcategoryNetworkMapper

    ): SubcategoryFirestoreService {
        return SubcategoryFirestoreServiceImpl(
            firestore = firestore,
            networkMapper = networkMapper
        )
    }

}