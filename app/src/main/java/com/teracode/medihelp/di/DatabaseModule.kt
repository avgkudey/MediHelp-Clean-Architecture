package com.teracode.medihelp.di

import android.content.Context
import androidx.room.Room
import com.teracode.medihelp.framework.datasource.database.CategoryDao
import com.teracode.medihelp.framework.datasource.database.DrugDao
import com.teracode.medihelp.framework.datasource.database.DrugDatabase
import com.teracode.medihelp.framework.datasource.database.SubcategoryDao
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