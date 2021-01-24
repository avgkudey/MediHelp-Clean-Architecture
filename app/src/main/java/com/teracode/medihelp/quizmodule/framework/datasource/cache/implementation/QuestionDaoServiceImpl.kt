package com.teracode.medihelp.quizmodule.framework.datasource.cache.implementation

import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction.QuestionDaoService
import com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers.QuestionCacheMapper
import com.teracode.medihelp.quizmodule.framework.datasource.database.QuestionDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionDaoServiceImpl
@Inject
constructor(
    private val dao: QuestionDao,
    private val mapper: QuestionCacheMapper,
) : QuestionDaoService {
    override suspend fun insertQuestion(question: Question): Long {
        return dao.insertQuestion(question = mapper.mapToEntity(question))
    }

    override suspend fun insertQuestions(questions: List<Question>): LongArray {
        return dao.insertQuestions(questions = mapper.questionListToEntityList(questions))
    }

    override suspend fun searchQuestionById(id: String): Question? {
        return dao.searchQuestionById(id)?.let {
            mapper.mapFromEntity(it)
        }
    }

    override suspend fun deleteQuestion(primaryKey: String): Int {
        return dao.deleteQuestion(primaryKey = primaryKey)
    }

    override suspend fun deleteQuestions(questions: List<Question>): Int {
        val ids = questions.mapIndexed { _, value -> value.id }
        return dao.deleteQuestions(ids)
    }

    override suspend fun deleteAllQuestions() {
        return dao.deleteAllQuestions()
    }

    override suspend fun updateQuestion(
        primaryKey: String,
        quizId: String,
        question: String,
        answer: String?,
        option_a: String?,
        option_b: String?,
        option_c: String?,
        option_d: String?,
        timer: Long,
    ): Int {

        return dao.updateQuestion(
            primaryKey = primaryKey,
            question = question,
            quizId = quizId,
            answer = answer,
            option_a = option_a,
            option_b = option_b,
            option_c = option_c,
            option_d = option_d,
            timer = timer,
        )
    }

    override fun getNumQuestions(): Int {
        return dao.getNumQuestions()
    }

    override suspend fun getAllQuestionsByQuiz(quizId: String): List<Question> {
        return mapper.entityListToQuestionList(entities = dao.getAllQuestionsByQuiz(quizId = quizId))
    }

    override suspend fun getAllQuestions(): List<Question> {
        return mapper.entityListToQuestionList(entities = dao.getAllQuestions())
    }
}