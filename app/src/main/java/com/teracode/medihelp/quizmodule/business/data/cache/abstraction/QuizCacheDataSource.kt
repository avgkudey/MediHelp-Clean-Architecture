package com.teracode.medihelp.quizmodule.business.data.cache.abstraction

import com.teracode.medihelp.quizmodule.business.domain.model.Quiz

interface QuizCacheDataSource {

    suspend fun insertQuiz(quiz: Quiz): Long

    suspend fun insertQuizzes(quizzes: List<Quiz>): LongArray

    suspend fun searchQuizById(primaryKey: String): Quiz?

    suspend fun deleteQuiz(primaryKey: String): Int

    suspend fun deleteQuizzes(quizzes: List<Quiz>): Int


    suspend fun updateQuiz(
        primaryKey: String,
        name: String,
        description: String? = null,
        image: String? = null,
        level: String? = null,
        visibility: String? = null,
        questions: Long = 0,
    ): Int


    suspend fun getNumQuizzes(): Int

    suspend fun getAllQuizzes(): List<Quiz>

}

















































