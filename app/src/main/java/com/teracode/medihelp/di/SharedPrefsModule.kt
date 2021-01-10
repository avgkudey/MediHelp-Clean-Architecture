package com.teracode.medihelp.di

import android.content.Context
import android.content.SharedPreferences
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.cache.implementation.DrugCacheDataSourceImpl
import com.teracode.medihelp.framework.datasource.cache.abstraction.DrugDaoService
import com.teracode.medihelp.framework.datasource.preferences.PreferenceKeys
import com.teracode.medihelp.framework.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object SharedPrefsModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context
            .getSharedPreferences(
                PreferenceKeys.DRUG_PREFERENCES,
                Context.MODE_PRIVATE
            )
    }



    @Singleton
    @Provides
    fun provideSharedPrefsEditor(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}