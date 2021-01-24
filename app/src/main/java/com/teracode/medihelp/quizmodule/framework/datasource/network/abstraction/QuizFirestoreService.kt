package com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction

import com.teracode.medihelp.quizmodule.business.domain.model.Quiz

interface QuizFirestoreService {
    suspend fun searchQuiz(quiz: Quiz): Quiz?

    suspend fun getAllQuizzes(): List<Quiz>

}