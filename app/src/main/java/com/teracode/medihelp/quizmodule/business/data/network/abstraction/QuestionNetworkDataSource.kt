package com.teracode.medihelp.quizmodule.business.data.network.abstraction

import com.teracode.medihelp.quizmodule.business.domain.model.Question

interface QuestionNetworkDataSource {
    suspend fun searchQuestion(question: Question): Question?

    suspend fun getAllQuestions(quizId:String): List<Question>

}