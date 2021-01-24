package com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction

import com.teracode.medihelp.quizmodule.business.domain.model.Quiz

interface QuizDaoService {


    suspend fun insertQuiz(quiz: Quiz): Long

    suspend fun insertQuizzes(quizzes: List<Quiz>): LongArray

    suspend fun searchQuizById(id: String): Quiz?

    suspend fun deleteQuiz(primaryKey: String): Int

    suspend fun deleteQuizzes(quizzes: List<Quiz>): Int

    suspend fun deleteAllQuizzes()

    suspend fun updateQuiz(
        primaryKey: String,
        name: String,
        description: String?,
        image: String?,
        level: String?,
        visibility: String?,
        questions: Long = 0,
    ): Int

    fun getNumQuizzes(): Int

    suspend fun getAllQuizzes(): List<Quiz>
}