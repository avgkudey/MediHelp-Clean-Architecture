package com.teracode.medihelp.quizmodule.di

import android.content.SharedPreferences
import com.teracode.medihelp.business.data.cache.abstraction.CategoryCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.DrugCacheDataSource
import com.teracode.medihelp.business.data.cache.abstraction.SubcategoryCacheDataSource
import com.teracode.medihelp.business.data.network.abstraction.CategoryNetworkDataSource
import com.teracode.medihelp.business.data.network.abstraction.DrugNetworkDataSource
import com.teracode.medihelp.business.data.network.abstraction.SubcategoryNetworkDataSource
import com.teracode.medihelp.business.interactors.common.GetNumDrugSubcategories
import com.teracode.medihelp.business.interactors.druglist.SearchDrugs
import com.teracode.medihelp.business.interactors.drugcategory.DrugCategoryInteractors
import com.teracode.medihelp.business.interactors.drugcategory.GetCategories
import com.teracode.medihelp.business.interactors.common.GetNumSubcategories
import com.teracode.medihelp.business.interactors.drugdetail.DrugDetailInteractors
import com.teracode.medihelp.business.interactors.drugdetail.GetDrug
import com.teracode.medihelp.business.interactors.druglist.DrugListInteractors
import com.teracode.medihelp.business.interactors.druglist.GetNumDrugs
import com.teracode.medihelp.business.interactors.splash.SyncCategories
import com.teracode.medihelp.business.interactors.splash.SyncCounts
import com.teracode.medihelp.business.interactors.splash.SyncDrugs
import com.teracode.medihelp.business.interactors.splash.SyncSubcategories
import com.teracode.medihelp.business.interactors.subcategories.SearchSubcategories
import com.teracode.medihelp.business.interactors.subcategories.SubcategoryInteractors
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuestionCacheDataSource
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuizCacheDataSource
import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuestionNetworkDataSource
import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuizNetworkDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.QuestionValidator
import com.teracode.medihelp.quizmodule.business.interactors.splash.SyncQuestions
import com.teracode.medihelp.quizmodule.business.interactors.splash.SyncQuizzes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object QuizInteractorModule {

    @Singleton
    @Provides
    fun provideSyncQuizzes(
        cacheDataSource: QuizCacheDataSource,
        networkDataSource: QuizNetworkDataSource,
        editor: SharedPreferences.Editor,
    ): SyncQuizzes {
        return SyncQuizzes(
            cacheDataSource = cacheDataSource,
            networkDataSource = networkDataSource,
            editor = editor,

            )
    }


    @Singleton
    @Provides
    fun provideSyncQuestions(
        questionCacheDataSource: QuestionCacheDataSource,
        quizCacheDataSource: QuizCacheDataSource,
        networkDataSource: QuestionNetworkDataSource,
        editor: SharedPreferences.Editor,
        validator: QuestionValidator

        ): SyncQuestions {
        return SyncQuestions(
            questionCacheDataSource = questionCacheDataSource,
            quizCacheDataSource = quizCacheDataSource,
            networkDataSource = networkDataSource,
            editor = editor,
            validator=validator

            )
    }
}