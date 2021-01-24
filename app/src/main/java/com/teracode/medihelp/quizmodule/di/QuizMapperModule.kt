package com.teracode.medihelp.quizmodule.di

import com.teracode.medihelp.business.domain.util.DateUtil
import com.teracode.medihelp.framework.datasource.cache.mappers.CategoryCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.DrugCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.SubcategoryCacheMapper
import com.teracode.medihelp.framework.datasource.network.mappers.CategoryNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.DrugNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.SubcategoryNetworkMapper
import com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers.QuestionCacheMapper
import com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers.QuizCacheMapper
import com.teracode.medihelp.quizmodule.framework.datasource.network.mappers.QuestionNetworkMapper
import com.teracode.medihelp.quizmodule.framework.datasource.network.mappers.QuizNetworkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object QuizMapperModule {


    @Singleton
    @Provides
    fun provideQuizNetworkMapper(): QuizNetworkMapper {
        return QuizNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideQuizCacheMapper(): QuizCacheMapper {
        return QuizCacheMapper()
    }

    @Singleton
    @Provides
    fun provideQuestionNetworkMapper(): QuestionNetworkMapper {
        return QuestionNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideQuestionCacheMapper(): QuestionCacheMapper {
        return QuestionCacheMapper()
    }
}