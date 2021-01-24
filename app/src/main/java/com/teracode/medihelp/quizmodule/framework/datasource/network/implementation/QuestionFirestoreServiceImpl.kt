package com.teracode.medihelp.quizmodule.framework.datasource.network.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.framework.datasource.network.QuestionNetworkEntity
import com.teracode.medihelp.quizmodule.framework.datasource.network.QuizNetworkEntity
import com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction.QuestionFirestoreService
import com.teracode.medihelp.quizmodule.framework.datasource.network.mappers.QuestionNetworkMapper
import com.teracode.medihelp.util.cLog
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionFirestoreServiceImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val mapper: QuestionNetworkMapper,
)

    : QuestionFirestoreService {
    override suspend fun searchQuestion(question: Question): Question? {
        return firestore.collection(QUESTION_COLLECTION).document(question.id)
            .get()
            .addOnFailureListener {
                cLog("${this::class.java} searchQuestion ${it.message}")
                printLogD("SyncQuestions","Error ${it.message}")
            }
            .await()
            .toObject(QuestionNetworkEntity::class.java)?.let {
                mapper.mapFromEntity(it)
            }
    }

    override suspend fun getAllQuestions(quizId: String): List<Question> {


        return mapper.entityListToQuestionList(
            entities = firestore.collection(QuizFirestoreServiceImpl.QUIZ_COLLECTION)
                .document(quizId).collection(
                    QUESTION_COLLECTION).get()
                .addOnFailureListener {
                    cLog("${this::class.java} getAllQuestions ${it.message}")
                }
                .addOnCompleteListener {
                }
                .await()
                .toObjects(QuestionNetworkEntity::class.java)
        )
    }

    companion object {
        const val QUESTION_COLLECTION = "questions"
    }
}