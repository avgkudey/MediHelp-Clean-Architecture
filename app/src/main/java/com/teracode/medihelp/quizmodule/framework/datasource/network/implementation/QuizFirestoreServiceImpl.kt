package com.teracode.medihelp.quizmodule.framework.datasource.network.implementation

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.datasource.network.QuizNetworkEntity
import com.teracode.medihelp.quizmodule.framework.datasource.network.abstraction.QuizFirestoreService
import com.teracode.medihelp.quizmodule.framework.datasource.network.mappers.QuizNetworkMapper
import com.teracode.medihelp.util.Constants.QUIZ_VISIBILITY_PROPERTY_NAME
import com.teracode.medihelp.util.Constants.QUIZ_VISIBILITY_PROPERTY_VALUE
import com.teracode.medihelp.util.cLog
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizFirestoreServiceImpl
@Inject
constructor(

    private val firestore: FirebaseFirestore,
    private val mapper: QuizNetworkMapper,

    ) : QuizFirestoreService {
    override suspend fun searchQuiz(quiz: Quiz): Quiz? {
        return firestore.collection(QUIZ_COLLECTION).document(quiz.id)
            .get()
            .addOnFailureListener {
                cLog("${this::class.java} searchQuiz ${it.message}")
            }
            .await()
            .toObject(QuizNetworkEntity::class.java)?.let {
                if (it.visibility == QUIZ_VISIBILITY_PROPERTY_VALUE) {
                    mapper.mapFromEntity(it)
                } else {
                    null
                }
            }
    }

    override suspend fun getAllQuizzes(): List<Quiz> {

        return mapper.entityListToQuizList(
            entities = firestore.collection(QUIZ_COLLECTION)
                .whereEqualTo(QUIZ_VISIBILITY_PROPERTY_NAME,
                    QUIZ_VISIBILITY_PROPERTY_VALUE).get()
                .addOnFailureListener {
                    cLog("${this::class.java} getAllQuizzes ${it.message}")
                }
                .addOnCompleteListener {
                }
                .await()
                .toObjects(QuizNetworkEntity::class.java)
        )
    }

    companion object {
        const val QUIZ_COLLECTION = "quizzes"
    }
}