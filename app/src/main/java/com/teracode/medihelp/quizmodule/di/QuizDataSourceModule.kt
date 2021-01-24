package com.teracode.medihelp.quizmodule.di

import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.business.domain.util.DateUtil
import com.teracode.medihelp.framework.datasource.cache.mappers.CategoryCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.DrugCacheMapper
import com.teracode.medihelp.framework.datasource.cache.mappers.SubcategoryCacheMapper
import com.teracode.medihelp.framework.datasource.database.DrugDao
import com.teracode.medihelp.framework.datasource.database.DrugDatabase
import com.teracode.medihelp.framework.datasource.network.mappers.CategoryNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.DrugNetworkMapper
import com.teracode.medihelp.framework.datasource.network.mappers.SubcategoryNetworkMapper
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuestionCacheDataSource
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuizCacheDataSource
import com.teracode.medihelp.quizmodule.business.data.cache.implementation.QuestionCacheDataSourceImpl
import com.teracode.medihelp.quizmodule.business.data.cache.implementation.QuizCacheDataSourceImpl
import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuestionNetworkDataSource
import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuizNetworkDataSource
import com.teracode.medihelp.quizmodule.business.data.network.implementation.QuestionNetworkDataSourceImpl
import com.teracode.medihelp.quizmodule.business.data.network.implementation.QuizNetworkDataSourceImpl
import com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction.QuestionDaoService
import com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction.QuizDaoService
import com.teracode.medihelp.quizmodule.framework.datasource.cache.implementation.QuestionDaoServiceImpl
import com.teracode.medihelp.quizmodule.framework.datasource.cache.implementation.QuizDaoServiceImpl
import com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers.QuestionCacheMapper
import com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers.QuizCacheMapper
import com.teracode.medihelp.quizmodule.framework.datasource.database.QuestionDao
import com.teracode.medihelp.quizmodule.framework.datasource.database.QuizDao
import com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction.QuestionFirestoreService
import com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction.QuizFirestoreService
import com.teracode.medihelp.quizmodule.framework.datasource.network.implementation.QuestionFirestoreServiceImpl
import com.teracode.medihelp.quizmodule.framework.datasource.network.implementation.QuizFirestoreServiceImpl
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
object QuizDataSourceModule {


    @Singleton
    @Provides
    fun provideQuizFirestoreService(
        firestore: FirebaseFirestore,
        mapper: QuizNetworkMapper,
    ): QuizFirestoreService {
        return QuizFirestoreServiceImpl(
            firestore = firestore,
            mapper = mapper
        )
    }


    @Singleton
    @Provides
    fun provideQuizNetworkDataSource(firestoreService: QuizFirestoreService): QuizNetworkDataSource {
        return QuizNetworkDataSourceImpl(
            firestoreService = firestoreService
        )
    }


    @Singleton
    @Provides
    fun provideQuizDao(drugDatabase: DrugDatabase): QuizDao {
        return drugDatabase.quizDao()
    }

    @Singleton
    @Provides
    fun provideQuizDaoService(
        dao: QuizDao,
        mapper: QuizCacheMapper,
    ): QuizDaoService {
        return QuizDaoServiceImpl(
            dao = dao,
            mapper = mapper

        )
    }

    @Singleton
    @Provides
    fun provideQuizCacheDataSource(daoService: QuizDaoService): QuizCacheDataSource {
        return QuizCacheDataSourceImpl(daoService = daoService)
    }


//    Question


    @Singleton
    @Provides
    fun provideQuestionFirestoreService(
        firestore: FirebaseFirestore,
        mapper: QuestionNetworkMapper,
    ): QuestionFirestoreService {
        return QuestionFirestoreServiceImpl(
            firestore = firestore,
            mapper = mapper
        )
    }


    @Singleton
    @Provides
    fun provideQuestionNetworkDataSource(firestoreService: QuestionFirestoreService): QuestionNetworkDataSource {
        return QuestionNetworkDataSourceImpl(
            firestoreService = firestoreService
        )
    }


    @Singleton
    @Provides
    fun provideQuestionDao(drugDatabase: DrugDatabase): QuestionDao {
        return drugDatabase.questionDao()
    }

    @Singleton
    @Provides
    fun provideQuestionDaoService(
        dao: QuestionDao,
        mapper: QuestionCacheMapper,
    ): QuestionDaoService {
        return QuestionDaoServiceImpl(
            dao = dao,
            mapper = mapper

        )
    }

    @Singleton
    @Provides
    fun provideQuestionCacheDataSource(daoService: QuestionDaoService): QuestionCacheDataSource {
        return QuestionCacheDataSourceImpl(daoService = daoService)
    }

}