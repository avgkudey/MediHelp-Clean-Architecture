package com.teracode.medihelp.quizmodule.business.data.cache.implementation

import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuestionCacheDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction.QuestionDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionCacheDataSourceImpl
@Inject
constructor(
    private val daoService: QuestionDaoService,
) : QuestionCacheDataSource {
    override suspend fun insertQuestion(question: Question) = daoService.insertQuestion(question)

    override suspend fun insertQuestions(questions: List<Question>) =
        daoService.insertQuestions(questions)

    override suspend fun searchQuestionById(primaryKey: String) =
        daoService.searchQuestionById(primaryKey)

    override suspend fun deleteQuestion(primaryKey: String) = daoService.deleteQuestion(primaryKey)

    override suspend fun deleteQuestions(questions: List<Question>) =
        daoService.deleteQuestions(questions)

    override suspend fun updateQuestion(
        primaryKey: String,
        quizId: String,
        question: String,
        answer: String?,
        option_a: String?,
        option_b: String?,
        option_c: String?,
        option_d: String?,
        timer: Long,
    ) = daoService.updateQuestion(
        primaryKey = primaryKey,
        quizId = quizId,
        question = question,
        answer = answer,
        option_a = option_a,
        option_b = option_b,
        option_c = option_c,
        option_d = option_d,
        timer = timer,
    )

    override suspend fun getNumQuestions() = daoService.getNumQuestions()

    override suspend fun getAllQuestions() = daoService.getAllQuestions()

    override suspend fun getAllQuestionsByQuiz(quizId: String) =
        daoService.getAllQuestionsByQuiz(quizId = quizId)
}