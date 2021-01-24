package com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction

import com.teracode.medihelp.quizmodule.business.domain.model.Question

interface QuestionDaoService {


    suspend fun insertQuestion(question: Question): Long

    suspend fun insertQuestions(questions: List<Question>): LongArray

    suspend fun searchQuestionById(id: String): Question?

    suspend fun deleteQuestion(primaryKey: String): Int

    suspend fun deleteQuestions(questions: List<Question>): Int

    suspend fun deleteAllQuestions()

    suspend fun updateQuestion(
        primaryKey: String,
        quizId: String,
        question: String,
        answer: String?,
        option_a: String?,
        option_b: String?,
        option_c: String?,
        option_d: String?,
        timer: Long = 0,
    ): Int

    fun getNumQuestions(): Int

    suspend fun getAllQuestionsByQuiz(quizId: String): List<Question>

    suspend fun getAllQuestions(): List<Question>
}