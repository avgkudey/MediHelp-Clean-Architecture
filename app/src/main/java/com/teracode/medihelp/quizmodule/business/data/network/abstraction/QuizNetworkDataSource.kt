package com.teracode.medihelp.quizmodule.business.data.network.abstraction

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz

interface QuizNetworkDataSource {
    suspend fun searchQuiz(quiz: Quiz): Quiz?

    suspend fun getAllQuizzes(): List<Quiz>

}