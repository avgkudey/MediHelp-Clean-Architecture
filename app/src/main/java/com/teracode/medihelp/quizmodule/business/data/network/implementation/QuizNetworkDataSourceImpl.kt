package com.teracode.medihelp.quizmodule.business.data.network.implementation

import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuizNetworkDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction.QuizFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizNetworkDataSourceImpl
@Inject
constructor(
    private val firestoreService: QuizFirestoreService,
) : QuizNetworkDataSource {
    override suspend fun searchQuiz(quiz: Quiz) = firestoreService.searchQuiz(quiz)

    override suspend fun getAllQuizzes() = firestoreService.getAllQuizzes()
}