package com.teracode.medihelp.di

import android.content.SharedPreferences
import com.teracode.medihelp.business.data.cache.abstraction.CategoryCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.network.abstraction.CategoryNetworkDataSource
import com.teracode.medihelp.business.data.network.abstraction.DrugNetworkDataSource
import com.teracode.medihelp.business.data.network.abstraction.SubcategoryNetworkDataSource
import com.teracode.medihelp.business.interactors.druglist.SearchDrugs
import com.teracode.medihelp.business.interactors.drugcategory.DrugCategoryInteractors
import com.teracode.medihelp.business.interactors.drugcategory.GetCategories
import com.teracode.medihelp.business.interactors.common.GetNumSubcategories
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.business.interactors.druglist.GetNumDrugs
import com.teracode.medihelp.business.interactors.splash.SyncCategories
import com.teracode.medihelp.business.interactors.splash.SyncDrugs
import com.teracode.medihelp.business.interactors.splash.SyncSubcategories
import com.teracode.medihelp.business.interactors.subcategories.SearchSubcategories
import com.teracode.medihelp.business.interactors.subcategories.SubcategoryInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun provideDrugListInteractors(
        drugCacheDataSource: DrugCacheDataSource,
    ): DrugListInteractors {
        return DrugListInteractors(
            searchDrugs = SearchDrugs(drugCacheDataSource = drugCacheDataSource),
            getNumDrugs = GetNumDrugs(drugCacheDataSource = drugCacheDataSource)

        )
    }

    @Singleton
    @Provides
    fun provideDrugCategoryInteractors(
        getCategories: GetCategories,
        getNumSubcategories: GetNumSubcategories
    ): DrugCategoryInteractors {
        return DrugCategoryInteractors(

            getCategories = getCategories,
            getNumSubcategories = getNumSubcategories

        )
    }

    @Singleton
    @Provides
    fun provideSubcategoryInteractors(
        getNumSubcategories: GetNumSubcategories,
        searchSubcategories: SearchSubcategories
    ): SubcategoryInteractors {
        return SubcategoryInteractors(
            getNumSubcategories = getNumSubcategories,
            searchSubcategories = searchSubcategories,
        )
    }

    @Singleton
    @Provides
    fun provideSyncDrugs(
        drugCacheDataSource: DrugCacheDataSource,
        drugNetworkDataSource: DrugNetworkDataSource,
        editor: SharedPreferences.Editor
    ): SyncDrugs {
        return SyncDrugs(
            drugCacheDataSource = drugCacheDataSource,
            drugNetworkDataSource = drugNetworkDataSource,
            editor = editor
        )
    }

    @Singleton
    @Provides
    fun provideSyncCategories(
        categoryCacheDataSource: CategoryCacheDataSource,
        categoryNetworkDataSource: CategoryNetworkDataSource,
        editor: SharedPreferences.Editor
    ): SyncCategories {
        return SyncCategories(
            cacheDataSource = categoryCacheDataSource,
            networkDataSource = categoryNetworkDataSource,
            editor = editor
        )
    }

    @Singleton
    @Provides
    fun provideSyncSubcategories(
        categoryCacheDataSource: SubcategoryCacheDataSource,
        categoryNetworkDataSource: SubcategoryNetworkDataSource,
        editor: SharedPreferences.Editor
    ): SyncSubcategories {
        return SyncSubcategories(
            cacheDataSource = categoryCacheDataSource,
            networkDataSource = categoryNetworkDataSource,
            editor = editor
        )
    }


    @Singleton
    @Provides
    fun provideSearchDrugs(
        drugCacheDataSource: DrugCacheDataSource,
    ): SearchDrugs {
        return SearchDrugs(
            drugCacheDataSource = drugCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideGetNumDrugs(
        drugCacheDataSource: DrugCacheDataSource,
    ): GetNumDrugs {
        return GetNumDrugs(
            drugCacheDataSource = drugCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideGetNumSubcategories(
        subcategoryCacheDataSource: SubcategoryCacheDataSource
    ): GetNumSubcategories {
        return GetNumSubcategories(
            subcategoryCacheDataSource = subcategoryCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideGetCategories(
        categoryCacheDataSource: CategoryCacheDataSource
    ): GetCategories {
        return GetCategories(
            categoryCacheDataSource = categoryCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun provideSearchSubcategories(
        dataSource: SubcategoryCacheDataSource
    ): SearchSubcategories {
        return SearchSubcategories(
            dataSource = dataSource
        )
    }
}