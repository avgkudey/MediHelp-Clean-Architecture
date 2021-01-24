package com.teracode.medihelp.quizmodule.business.data.cache.abstraction

import com.teracode.medihelp.quizmodule.business.domain.model.Question

interface QuestionCacheDataSource {

    suspend fun insertQuestion(question: Question): Long

    suspend fun insertQuestions(questions: List<Question>): LongArray

    suspend fun searchQuestionById(primaryKey: String): Question?

    suspend fun deleteQuestion(primaryKey: String): Int

    suspend fun deleteQuestions(questions: List<Question>): Int


    suspend fun updateQuestion(
        primaryKey: String,
        quizId: String,
        question: String ,
        answer: String? = null,
        option_a: String? = null,
        option_b: String? = null,
        option_c: String? = null,
        option_d: String? = null,
        timer: Long = 0,
    ): Int


    suspend fun getNumQuestions(): Int

    suspend fun getAllQuestions(): List<Question>

    suspend fun getAllQuestionsByQuiz(quizId: String): List<Question>

}

















































