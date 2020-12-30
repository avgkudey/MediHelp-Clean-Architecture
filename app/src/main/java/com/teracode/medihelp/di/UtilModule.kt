package com.teracode.medihelp.di

import com.teracode.medihelp.business.domain.model.DrugFactory
import com.teracode.medihelp.business.domain.util.DateUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UtilModule {
    @Singleton
    @Provides
    fun provideDateFormat(): SimpleDateFormat {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC-7") // match firestore
        return sdf
    }


    @Singleton
    @Provides
    fun provideDrugFactory(): DrugFactory {
        return DrugFactory()
    }
}