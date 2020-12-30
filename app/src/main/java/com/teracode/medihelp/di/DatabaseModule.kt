package com.teracode.medihelp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
import com.teracode.medihelp.framework.datasource.cache.implementation.SubcategoryDaoServiceImpl
import com.teracode.medihelp.framework.datasource.database.CategoryDao
import com.teracode.medihelp.framework.datasource.database.DrugDao
import com.teracode.medihelp.framework.datasource.database.DrugDatabase
import com.teracode.medihelp.framework.datasource.database.SubcategoryDao
import com.teracode.medihelp.framework.datasource.network.abstraction.CategoryFirestoreService
import com.teracode.medihelp.framework.datasource.network.abstraction.DrugFirestoreService
import com.teracode.medihelp.framework.datasource.network.abstraction.SubcategoryFirestoreService
import com.teracode.medihelp.framework.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {


    @Singleton
    @Provides
    fun provideDrugDb(@ApplicationContext context: Context): DrugDatabase {
        return Room
            .databaseBuilder(context, DrugDatabase::class.java, DrugDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDrugDao(drugDatabase: DrugDatabase): DrugDao {
        return drugDatabase.drugDao()
    }


    @Singleton
    @Provides
    fun provideCategoryDao(drugDatabase: DrugDatabase): CategoryDao {
        return drugDatabase.categoryDao()
    }


    @Singleton
    @Provides
    fun provideSubcategoryDao(drugDatabase: DrugDatabase): SubcategoryDao {
        return drugDatabase.subcategoryDao()
    }


}