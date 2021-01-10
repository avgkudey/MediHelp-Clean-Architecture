package com.teracode.medihelp.di

import com.teracode.medihelp.business.domain.util.DateUtil
import com.teracode.medihelp.framework.datasource.cache.mappers.CategoryCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.DrugCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.SubcategoryCacheMapper
import com.teracode.medihelp.framework.datasource.network.mappers.CategoryNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.DrugNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.SubcategoryNetworkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MapperModule {


    @Singleton
    @Provides
    fun provideDrugNetworkMapper(): DrugNetworkMapper {
        return DrugNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideDrugCacheMapper(): DrugCacheMapper {
        return DrugCacheMapper()
    }



    @Singleton
    @Provides
    fun provideCategoryNetworkMapper(): CategoryNetworkMapper {
        return CategoryNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideCategoryCacheMapper(): CategoryCacheMapper {
        return CategoryCacheMapper()
    }



    @Singleton
    @Provides
    fun provideSubcategoryNetworkMapper(): SubcategoryNetworkMapper {
        return SubcategoryNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideSubcategoryCacheMapper(): SubcategoryCacheMapper {
        return SubcategoryCacheMapper()
    }



}