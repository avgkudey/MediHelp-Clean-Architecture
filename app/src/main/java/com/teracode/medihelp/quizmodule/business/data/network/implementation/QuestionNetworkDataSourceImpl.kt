package com.teracode.medihelp.quizmodule.business.data.network.implementation

import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuestionNetworkDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction.QuestionFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionNetworkDataSourceImpl
@Inject
constructor(
    private val firestoreService: QuestionFirestoreService,
)

    : QuestionNetworkDataSource {
    override suspend fun searchQuestion(question: Question) =
        firestoreService.searchQuestion(question)

    override suspend fun getAllQuestions(quizId: String) = firestoreService.getAllQuestions(quizId)
}