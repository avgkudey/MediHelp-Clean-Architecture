package com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction

import com.teracode.medihelp.quizmodule.business.domain.model.Question

interface QuestionFirestoreService {
    suspend fun searchQuestion(question: Question): Question?

    suspend fun getAllQuestions(quizId:String): List<Question>

}