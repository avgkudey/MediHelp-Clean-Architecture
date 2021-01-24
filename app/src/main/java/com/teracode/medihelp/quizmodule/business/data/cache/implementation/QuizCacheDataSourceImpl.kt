package com.teracode.medihelp.quizmodule.business.data.cache.implementation

import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuizCacheDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction.QuizDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizCacheDataSourceImpl
@Inject
constructor(
    private val daoService: QuizDaoService,
) : QuizCacheDataSource {
    override suspend fun insertQuiz(quiz: Quiz) = daoService.insertQuiz(quiz)

    override suspend fun insertQuizzes(quizzes: List<Quiz>) = daoService.insertQuizzes(quizzes)

    override suspend fun searchQuizById(primaryKey: String) = daoService.searchQuizById(primaryKey)

    override suspend fun deleteQuiz(primaryKey: String) = daoService.deleteQuiz(primaryKey)

    override suspend fun deleteQuizzes(quizzes: List<Quiz>) = daoService.deleteQuizzes(quizzes)

    override suspend fun updateQuiz(
        primaryKey: String,
        name: String,
        description: String?,
        image: String?,
        level: String?,
        visibility: String?,
        questions: Long,
    ) = daoService.updateQuiz(
        primaryKey = primaryKey,
        name = name,
        description = description,
        image = image,
        level = level,
        visibility = visibility,
        questions = questions,
    )

    override suspend fun getNumQuizzes() = daoService.getNumQuizzes()

    override suspend fun getAllQuizzes() = daoService.getAllQuizzes()
}