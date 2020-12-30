package com.teracode.medihelp.di

import com.teracode.medihelp.business.domain.util.DateUtil
import com.teracode.medihelp.framework.datasource.cache.mappers.DrugCacheMapper
import com.teracode.medihelp.framework.datasource.network.mappers.DrugNetworkMapper
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
}